package com.windf.module.development.modle.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.windf.core.exception.UserException;
import com.windf.core.util.CollectionUtil;
import com.windf.module.development.pojo.ExceptionType;
import com.windf.module.development.pojo.Parameter;
import com.windf.module.development.pojo.Return;

public class Method extends AbstractType{
	

	/**
	 * 判断这一行是否是方法声明
	 * @param lineContent
	 * @return
	 */
	static boolean isMethodStart(String lineContent) {
		boolean result = false;
		
		if (CodeConst.lineStartTabCount(lineContent) == 1 ) {
			if (lineContent.contains("abstract") || lineContent.trim().endsWith("{")) {
				result = true; 
			}
		}
		
		return result;
	}
	
	/**
	 * 是否是方法的结尾
	 * @param lineContent
	 * @return
	 */
	static boolean isMethodEnd(String lineContent) {
		boolean result = false;
		
		if (CodeConst.lineStartTabCount(lineContent) == 1 && lineContent.trim().equals("}")) {
			result = true; 
		}
		
		return result;
	}
	
	/*
	 * 代码
	 */
	String methodStart;
	List<CodeBlock> codeBlocks = new ArrayList<CodeBlock>();
	List<String> codes = new ArrayList<String>();
	String methodEnd = CodeConst.TAB + "}";
	
	/*
	 * 方法信息
	 */
	String methodName;
	List<Parameter> parameters = new ArrayList<Parameter>();
	Return ret;
	ExceptionType exceptionType;

	/**
	 * 由代码构造方法
	 * @param lines
	 * @param comment
	 */
	public Method (String methodStart) {
		this.methodStart = methodStart;
		this.codeBlocks = new ArrayList<CodeBlock>();
		
		initInfoFromMethodStart();
	}
	
	/**
	 * 由方法信息构造方法（方法体为空）
	 * @param methodName
	 * @param ret
	 * @param parameters
	 * @param exception
	 */
	public Method(String methodName, Return ret, List<Parameter> parameters, ExceptionType exception) {
		this.methodName = methodName;
		this.ret = ret;
		this.parameters = parameters;
		this.exceptionType = exception;
		
		initMethodStartFromInfo();
	}
	
	/**
	 * 添加参数
	 * @param parameter
	 * @return 如果已存在参数，返回false，表示为添加成功
	 */
	public boolean addParameter(Parameter parameter) {
		Parameter oldParameter = this.getParameter(parameter.getName());
		
		if (oldParameter != null) {
			return false;
		}
		
		this.parameters.add(parameter);
		return true;
	}
	
	protected Parameter getParameter(String name) {
		Parameter result = null;
		
		for (Parameter parameter : parameters) {
			if (name.equals(parameter.getName())) {
				result = parameter;
			}
		}
		
		return result;
	}
	
	public void addCodeBlock(int index, CodeBlock codeBlock) {
		if (codeBlocks.size() > index) {
			codeBlocks.remove(index);
		}
		codeBlocks.add(index, codeBlock);
	}
	
	public void appendCodeBlock(CodeBlock codeBlock) {
		codeBlocks.add(codeBlock);
	}
	
	public CodeBlock getCodeBlock(int index) {
		CodeBlock result = null;
		if (index < codeBlocks.size()) {
			result = codeBlocks.get(index);
		}
		return result;
	}
	
	/**
	 * 向方法中添加一行
	 * @param lineContent
	 */
	void addLine(String lineContent) {
		codes.add(lineContent);
	}

	/**
	 * 解析方法，代码，解析方法块
	 * @param lines
	 */
	void initCodeBlocks() {
		List<String> lines = codes;

		if (CollectionUtil.isNotEmpty(lines)) {
			boolean inComments = false;
			Comment comment = null;
			List<String> coderLines = new ArrayList<String>();
			for (int no = 0; no < lines.size() - 1; no ++) {
				String lineContent = lines.get(no);
				
				if (Comment.isCommentEnd(lineContent)) {
					inComments = false;
					comment.end(lineContent);
				} else if (inComments) {
					comment.addLine(lineContent);
				} else if (Comment.isCommentStart(lineContent)) {
					if (CollectionUtil.isNotEmpty(coderLines)) {
						CodeBlock codeBlock = new CodeBlock(coderLines);
						codeBlock.setComment(comment);
						codeBlocks.add(codeBlock);
					}

					inComments = true;
					comment = new Comment(lineContent);
				} else {
					coderLines.add(lineContent);
				}
			}
			
			if (CollectionUtil.isNotEmpty(coderLines)) {
				CodeBlock codeBlock = new CodeBlock(coderLines);
				codeBlock.setComment(comment);
				codeBlocks.add(codeBlock);
			}
			
		}
	}
	
	@Override
	List<String> write() {
		List<String> result = new ArrayList<String>();
		
		result.addAll(this.getComment());
		result.addAll(this.getAnnotationsString(1));
		
		result.add(methodStart);
		Collections.sort(codeBlocks, new Comparator<CodeBlock>() {

			@Override
			public int compare(CodeBlock c1, CodeBlock c2) {
				return c1.getSerial() - c2.getSerial();
			}
			
		});
		for (int i = 0; i < codeBlocks.size(); i++) {
			CodeBlock codeBlock = codeBlocks.get(i);
			result.addAll(codeBlock.write());
		}
		result.add(methodEnd);
		
		return result;
	}
	
	/*
	 * 截取方法申明行代码，获取方法的信息
	 */
	private void initInfoFromMethodStart() {
		String parameterStr = methodStart.substring(methodStart.indexOf("(") + 1, methodStart.indexOf(")"));
		String nameStrs = methodStart.substring(0, methodStart.indexOf("(")) + methodStart.substring(methodStart.indexOf(")") + 1);
		
		String[] titles = nameStrs.trim().split(CodeConst.WORD_SPLIT);
		int index = 1;
		String retStr = titles[index++];
		if (retStr.contains("<") && !retStr.contains(">")) {
			do {
				retStr += CodeConst.WORD_SPLIT + titles[index++];
			} while (!retStr.contains(">"));
		}
		String methodNameStr = titles[index++];
		String exceptionTypeStr = null;
		if (titles.length > index + 1) {
			exceptionTypeStr = titles[index];
		}
		
		this.ret = new Return(retStr);
		this.methodName = methodNameStr;
		String[] parameterStrs = parameterStr.split(", ");
		for (int i = 1; i < parameterStrs.length; i++) {
			String[] ss = parameterStrs[i].split(CodeConst.WORD_SPLIT);
			
			Parameter parameter = new Parameter();
			parameter.setType(ss[0]);
			parameter.setName(ss[1]);
			parameters.add(parameter);
		}
		if (exceptionTypeStr != null) {
			exceptionType = new ExceptionType();
			exceptionType.setType(exceptionTypeStr);
		}
	}

	/*
	 * 根据方法信息，创建方法代码
	 */
	private void initMethodStartFromInfo() {
		StringBuffer methodCodes = new StringBuffer();
		methodCodes.append(CodeConst.TAB);
		methodCodes.append("public");
		methodCodes.append(CodeConst.WORD_SPLIT + ret.getType());
		methodCodes.append(CodeConst.WORD_SPLIT + methodName);
		methodCodes.append("(");
		if (CollectionUtil.isNotEmpty(parameters)) {
			for (int i = 0; i < parameters.size(); i++) {
				Parameter parameter = parameters.get(i);
				if (i != 0) {
					methodCodes.append("," + CodeConst.WORD_SPLIT);
				}
				methodCodes.append(parameter.getType());
				methodCodes.append(CodeConst.WORD_SPLIT + parameter.getName());
			}
		}
		methodCodes.append(")");
		if (exceptionType != null) {
			methodCodes.append(CodeConst.WORD_SPLIT + "throws" + CodeConst.WORD_SPLIT + UserException.class.getSimpleName());
		}
		methodCodes.append(CodeConst.WORD_SPLIT + "{");
	
		this.methodStart = methodCodes.toString();
	}

	public String getMethodStart() {
		return methodStart;
	}

	public void setMethodStart(String methodStart) {
		this.methodStart = methodStart;
	}

	public List<CodeBlock> getCodeBlocks() {
		return codeBlocks;
	}

	public void setCodeBlocks(List<CodeBlock> codeBlocks) {
		this.codeBlocks = codeBlocks;
	}

	public List<String> getCodes() {
		return codes;
	}

	public void setCodes(List<String> codes) {
		this.codes = codes;
	}

	public String getMethodEnd() {
		return methodEnd;
	}

	public void setMethodEnd(String methodEnd) {
		this.methodEnd = methodEnd;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public Return getRet() {
		return ret;
	}

	public void setRet(Return ret) {
		this.ret = ret;
	}

	public ExceptionType getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(ExceptionType exceptionType) {
		this.exceptionType = exceptionType;
	}
	
	
}
