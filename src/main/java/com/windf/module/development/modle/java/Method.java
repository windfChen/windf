package com.windf.module.development.modle.java;

import java.util.ArrayList;
import java.util.List;

import com.windf.core.util.CollectionUtil;
import com.windf.module.development.entity.ExceptionType;
import com.windf.module.development.entity.Parameter;
import com.windf.module.development.entity.Return;

@SuppressWarnings({"rawtypes", "unchecked"})
public class Method extends AbstractType {
	
	/**
	 * 判断这一行是否是方法声明
	 * @param lineContent
	 * @return
	 */
	static boolean isMethodStart(String lineContent) {
		boolean result = false;
		
		if (CodeConst.lineStartTabCount(lineContent) == 1 ) {
			if (lineContent.trim().endsWith("{")) {
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
	
	private static final String INTERFACE_METHOD_PATTERN = "(public )?(abstract )?(\\w*) (\\w*)\\((\\w* \\w*(, )?)*\\)( throws (\\w*(, )?)*)?;";
	
	/**
	 * 是否是接口中的方法或者抽象方法
	 * @param lineContent
	 * @return    public String getNameById(String name, Integer age) throws UserException, CodeException;"
	 */
	public static boolean isInterfaceMethod(String lineContent) {
		return CodeConst.verify(lineContent.trim(), INTERFACE_METHOD_PATTERN);
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
	String modifier;
	boolean isAbstract;
	boolean isFinal;
	boolean isStatic;
	boolean isSynchronized;
	String t;
	Return ret;
	String methodName;
	List<Parameter> parameters = new ArrayList<Parameter>();
	ExceptionType exceptionType;
	boolean unImplement;

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
	 * @param b 
	 */
	public Method(String methodName, Return ret, List<Parameter> parameters, ExceptionType exception, boolean unImplement) {
		this.methodName = methodName;
		this.ret = ret;
		this.parameters = parameters;
		this.exceptionType = exception;
		this.unImplement = unImplement;
		
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
		if (index > codeBlocks.size()) {
			index = codeBlocks.size();
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
			for (int no = 0; no < lines.size(); no ++) {
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
						
						coderLines = new ArrayList<String>();
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
		
		if (!unImplement) {
			for (int i = 0; i < codeBlocks.size(); i++) {
				CodeBlock codeBlock = codeBlocks.get(i);
				result.addAll(codeBlock.write());
				result.add("");
			}
			
			result.add(methodEnd);
		}
		
		return result;
	}
	
	/*
	 * 截取方法申明行代码，获取方法的信息
	 */
	private void initInfoFromMethodStart() {

		if (methodStart.trim().endsWith(";")) {
			unImplement = true;
		}
		
		String methodLinePattern = "(public |private |protected )?([^\\(]*)(\\([^\\)]*\\))(\\s?throws[^\\{;]*)?[\\{;]";
		String[] ss = CodeConst.getInnerString(this.methodStart, methodLinePattern);
		modifier = ss[0];
		if (modifier == null) {
			modifier = "package";
		}
		
		String[] names = ss[1].split("\\s");
		names = mergin(names);
		for (int i = 0; i < names.length; i++) {
			String s = names[i].trim();
			
			if ("abstract".equals(s)) {
				isAbstract = true;
			} else if ("final".equals(s)) {
				isFinal = true;
			} else if ("static".equals(s)) {
				isStatic = true;
			} else if ("synchronized".equals(s)) {
				isSynchronized = true;
			} else {
				if (s.startsWith("<")) {
					t = s;
				} else if (ret == null) {
					this.ret = new Return(s);
				} else {
					methodName = s;
				}
			}
		}

		String parameterStr = ss[2];
		parameterStr = parameterStr.substring(1, parameterStr.length() - 1);
		String[] parameterStrs = parameterStr.split(",\\s?");
		parameterStrs = mergin(parameterStrs);
		for (int i = 0; i < parameterStrs.length; i++) {
			String s = parameterStrs[i];
			String[] ss2 = s.split("\\s");
			
			Parameter parameter = new Parameter();
			parameter.setType(ss2[0]);
			parameter.setName(ss2[1]);
			parameters.add(parameter);
		}	
		
		String exceptionTypeStr = ss[3];
		if (exceptionTypeStr != null) {
			this.exceptionType = new ExceptionType(exceptionTypeStr);
		}
		
//		System.out.println("modifier-" + modifier);
//		System.out.println("isAbstract-" + isAbstract);
//		System.out.println("isFinal-" + isFinal);
//		System.out.println("isStatic-" + isStatic);
//		System.out.println("isSynchronized-" + isSynchronized);
//		System.out.println("t-" + t);
//		System.out.println("ret-" + ret.getType());
//		System.out.println("methodName-" + methodName);
//		System.out.println("parameterStr-" + parameterStr);
//		for (int i = 0; i < parameters.size(); i++) {
//			System.out.println("parameters-" + parameters.get(i).getType() + "," + parameters.get(i).getName());
//		}
//		System.out.println("exceptionTypeStr-" + exceptionTypeStr);
		
	}
	
	private String[] mergin(String[] ss) {
		List<String> list = new ArrayList<String>();

		int leftCount = 0;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ss.length; i++) {
			String s = ss[i];
			
			boolean left = s.indexOf("<") > -1;
			boolean right = s.indexOf(">") > -1;
			
			if (left && !right) {
				sb.append(s);
				leftCount++;
			} else if (sb.length() > 0 && !right) {
				sb.append(s);
			} else if (right) {
				sb.append(s);
				if (leftCount > 0) {
					leftCount--;
				}
				
				if (leftCount == 0) {
					list.add(sb.toString());
					sb.setLength(0);
				}
			} else {
				list.add(s);
			}
		}	
		
		String[] result = new String[list.size()];
		list.toArray(result);
		return result;
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
			methodCodes.append(CodeConst.WORD_SPLIT + exceptionType.write());
		}
		if (unImplement) {
			methodCodes.append(";");
		} else {
			methodCodes.append(CodeConst.WORD_SPLIT + "{");
		}
	
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

	public boolean isUnImplement() {
		return unImplement;
	}

	public void setUnImplement(boolean unImplement) {
		this.unImplement = unImplement;
	}
	
	
}
