package com.windf.module.development.modle.java;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.windf.core.exception.EntityException;
import com.windf.module.development.file.JavaFileUtil;
import com.windf.module.development.file.JavaFileUtil.LineReader;
import com.windf.module.development.pojo.ExceptionType;
import com.windf.module.development.pojo.Parameter;
import com.windf.module.development.pojo.Return;

public class JavaCoder extends AbstractType{

	public static void main(String[] args) {
		JavaCoder j = new JavaCoder("F:/temp/UrlControler.java");
		try {
			List<Parameter> p = new ArrayList<Parameter>();
			p.add(new Parameter("java.lang.String", "userId"));
			p.add(new Parameter("String", "username"));
			j.createMethod("test", new Return("String"), p, new ExceptionType("EntityException"));
			
			j.createAttribute("Integer", "bb");
			
		} catch (EntityException e) {
			e.printStackTrace();
		}
		 j.write();
	}
	
	private String classPath;
	private String packageInfo;
	private Imports imports = new Imports();
	private String className;
	private List<Attribute> attributes = new ArrayList<Attribute>();
	private List<Method> methods = new ArrayList<Method>();
	private String classEnd;
	
	public JavaCoder(String classPath) {
		this.classPath = classPath;
		readCodes();
	}

	private void readCodes() {
		File javaFile = new File(classPath);
		
		JavaFileUtil.readLine(javaFile, new LineReader() {
			
			List<String> annotations = new ArrayList<String>();
			boolean isInMethod = false;
			Method method = null;
			boolean inComments = false;
			Comment comment = null;
			
			@Override
			public String readLine(List<String> oldLines, String lineContent, int lineNo) {
				
				// 统一制表符
				lineContent = lineContent.replace("\t", CodeConst.TAB);
				
				if (lineContent.startsWith("package ")) {
					packageInfo = lineContent;
				} else if (lineContent.startsWith("import ")) {
					imports.addLine(lineContent);
				} else if (lineContent.startsWith("public class ")) {
					className = lineContent;
					setComment(comment);
					setAnnotations(annotations);
					reset();
				} else if (Method.isMethodEnd(lineContent)) {
					isInMethod = false;
					
					method.setComment(comment);
					method.setAnnotations(annotations);
					method.initCodeBlocks();
					reset();
				} else if (isInMethod) {
					method.addLine(lineContent);
				} else if (Comment.isCommentEnd(lineContent)) {
					inComments = false;
					comment.end(lineContent);
				} else if (inComments) {
					comment.addLine(lineContent);
				} else if (Comment.isCommentStart(lineContent)) {
					inComments = true;
					comment = new Comment(lineContent);
				} else if (Annotation.isAnnotationLine(lineContent)) {
					annotations.add(lineContent);
				} else if (Method.isMethodStart(lineContent)) {
					isInMethod = true;
					method = new Method(lineContent);
					methods.add(method);
				} else if (Attribute.isAttributeLine(lineContent)) {
					Attribute attribute = new Attribute(lineContent);
					attributes.add(attribute);
					attribute.setAnnotations(annotations);
					attribute.setComment(comment);
					reset();
				} else if (isClassEnd(lineContent)) {
					classEnd = lineContent;
				}
				
				return null;
			}
			
			private void reset() {
				annotations = new ArrayList<String>();
				comment = null;
			}
		}, true);
	}

	/**
	 * 类是否存在
	 * @return
	 */
	public boolean isExist() {
		return methods != null && methods.size() > 0;
	}
	
	/**
	 * 创建一个方法
	 * @param methodStart
	 * @return
	 */
	public Method createMethod(String methodName, Return ret, List<Parameter> parameters, ExceptionType exceptionType) throws EntityException{
		if (this.getMethod(methodName) != null) {
			throw new EntityException("方法已存在");
		}
		
		Method method = new Method(methodName, ret, parameters, exceptionType);
		this.methods.add(method);
		
		return method;
	}

	/**
	 * 根据方法名，查询一个方法
	 * @param name
	 * @return
	 */
	public Method getMethod(String name) {
		Method result = null;
		
		if (!CollectionUtils.isEmpty(methods)) {
			for (Method method : methods) {
				if (method.methodName.equals(name)) {
					result = method;
					break;
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 创建一个属性
	 * @param name
	 * @return
	 */
	public Attribute createAttribute(String type, String name) throws EntityException {
		if (this.getAttribute(name) != null) {
			throw new EntityException("属性已存在");
		}
		
		Attribute attribute = new Attribute(type, name);
		this.attributes.add(attribute);
		
		return attribute;
	}
	
	/**
	 * 获得一个属性
	 * @param name
	 * @return
	 */
	public Attribute getAttribute(String name) {
		Attribute result = null;
		
		if (!CollectionUtils.isEmpty(attributes)) {
			for (Attribute attribute : attributes) {
				if (attribute.name.equals(name)) {
					result = attribute;
					break;
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 写类
	 */
	public List<String> write() {
		List<String> result = new ArrayList<String>();
		
		result.add(packageInfo);
		result.add("");
		
		result.addAll(imports.write());
		result.add("");
		
		result.addAll(getComment());
		result.addAll(getAnnotations(0));
		result.add(className);
		result.add("");
		
		for (int i = 0; i < attributes.size(); i++) {
			Attribute attribute = attributes.get(i);
			result.addAll(attribute.write());
		}
		result.add("");
		
		for (int i = 0; i < methods.size(); i++) {
			Method method = methods.get(i);
			result.addAll(method.write());
			result.add("");
		}
		
		result.add(classEnd);
		
		for (int i = 0; i < result.size(); i++) {
			System.out.println(result.get(i));
		}
		
		return result;
	}

	/**
	 * 是否是类的结尾
	 * @param lineContent
	 * @return
	 */
	protected boolean isClassEnd(String lineContent) {
		boolean result = false;
		
		if (CodeConst.lineStartTabCount(lineContent) == 0 && lineContent.equals("}")) {
			result = true; 
		}
		
		return result;
	}
	
}


