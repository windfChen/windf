package com.windf.module.development.modle;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.windf.core.exception.EntityException;
import com.windf.module.development.pojo.ExceptionType;
import com.windf.module.development.pojo.Parameter;
import com.windf.module.development.pojo.Return;

class Method extends ClassContent{
	/*
	 * 代码
	 */
	String methodStart;
	List<CodeBlock> codeBlocks;
	String methodEnd;
	
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
	Method (List<String> lines, Comment comment) {
		this.codeBlocks = new ArrayList<CodeBlock>();
		this.comment = comment;
		
		initFromCodes(lines);
		initFromNameCodes();
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
		
		initFromParams();
	}

	/**
	 * 解析方法，代码，解析方法块
	 * @param lines
	 */
	private void initFromCodes(List<String> lines) {

		if (!CollectionUtils.isEmpty(lines)) {
			boolean inComments = false;
			
			methodStart = lines.get(0);

			List<String> commentLines = new ArrayList<String>();
			List<String> coderLines = new ArrayList<String>();
			for (int no = 1; no < lines.size() - 1; no ++) {
				String line = lines.get(no);
				
				if (Comment.isCommentStart(line)) {
					inComments = true;
					
					if (!CollectionUtils.isEmpty(coderLines)) {
						Comment comment = new Comment(commentLines);
						CodeBlock codeBlock = new CodeBlock(coderLines, comment);
						codeBlocks.add(codeBlock);
					}
				}
				
				if (inComments) {
					commentLines.add(line);
				} else {
					coderLines.add(line);
				}
				
				if (Comment.isCommentEnd(line)) {
					inComments = false;
				}
				
			}
			
			if (!CollectionUtils.isEmpty(coderLines)) {
				Comment comment = new Comment(commentLines);
				CodeBlock codeBlock = new CodeBlock(coderLines, comment);
				codeBlocks.add(codeBlock);
			}
			
			this.methodEnd = CodeConst.TAB + "}";
		}
	}
	
	/**
	 * 截取方法申明行代码，获取方法的信息
	 */
	private void initFromNameCodes() {
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

	/**
	 * 根据方法信息，创建方法代码
	 */
	private void initFromParams() {
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
		this.codeBlocks = new ArrayList<CodeBlock>();
		this.methodEnd = CodeConst.TAB + "}";
	}

	List<String> write() {
		List<String> result = new ArrayList<String>();
		
		result.addAll(this.getComment());
		result.addAll(this.getAnnotations());
		
		result.add(methodStart);
		for (int i = 0; i < codeBlocks.size(); i++) {
			CodeBlock codeBlock = codeBlocks.get(i);
			result.addAll(codeBlock.write());
		}
		result.add(methodEnd);
		
		return result;
	}
}
