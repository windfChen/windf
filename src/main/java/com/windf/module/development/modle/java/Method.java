package com.windf.module.development.modle.java;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.windf.core.exception.EntityException;
import com.windf.module.development.pojo.ExceptionType;
import com.windf.module.development.pojo.Parameter;
import com.windf.module.development.pojo.Return;

class Method extends AbstractType{
	

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
	Method (String methodStart) {
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
	Method(String methodName, Return ret, List<Parameter> parameters, ExceptionType exception) {
		this.methodName = methodName;
		this.ret = ret;
		this.parameters = parameters;
		this.exceptionType = exception;
		
		initMethodStartFromInfo();
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

		if (!CollectionUtils.isEmpty(lines)) {
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
					if (!CollectionUtils.isEmpty(coderLines)) {
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
			
			if (!CollectionUtils.isEmpty(coderLines)) {
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
		result.addAll(this.getAnnotations(1));
		
		result.add(methodStart);
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
		String parameterStr = methodStart.substring(methodStart.indexOf("("), methodStart.indexOf(")"));
		String nameStrs = methodStart.substring(0, methodStart.indexOf("(")) + methodStart.substring(methodStart.indexOf(")"));
		String[] titles = nameStrs.split(CodeConst.WORD_SPLIT);
		this.ret = new Return(titles[1]);
		this.methodName = titles[2];
		String[] parameterStrs = parameterStr.split(", ");
		for (int i = 1; i < parameterStrs.length; i++) {
			String[] ss = parameterStrs[i].split(CodeConst.WORD_SPLIT);
			
			Parameter parameter = new Parameter();
			parameter.setType(ss[0]);
			parameter.setName(ss[1]);
			parameters.add(parameter);
		}
		if (titles.length > 4) {
			exceptionType = new ExceptionType();
			exceptionType.setType(titles[4]);
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
		if (!CollectionUtils.isEmpty(parameters)) {
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
			methodCodes.append(CodeConst.WORD_SPLIT + "throws" + CodeConst.WORD_SPLIT + EntityException.class.getSimpleName());
		}
		methodCodes.append(CodeConst.WORD_SPLIT + "{");
	
		this.methodStart = methodCodes.toString();
	}
}
