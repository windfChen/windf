package com.windf.module.development.modle;

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

public class JavaCoder {

	public static void main(String[] args) {
		JavaCoder j = new JavaCoder("F:/temp/UrlControler.java");
		try {
			List<Parameter> p = new ArrayList<Parameter>();
			p.add(new Parameter("String", "userId"));
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
	private List<String> imports = new ArrayList<String>();
	private List<String> classAnnotations;
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
			List<String> methodLines = new ArrayList<String>();
			boolean inComments = false;
			List<String> commentLines = new ArrayList<String>();
			
			@Override
			public String readLine(List<String> oldLines, String lineContent, int lineNo) {
				
				// 统一制表符
				lineContent = lineContent.replace("\t", CodeConst.TAB);

				if (inComments || Comment.isCommentStart(lineContent)) {
					if (!inComments) {
						inComments = true;
					}
					commentLines.add(lineContent);
					
					if (Comment.isCommentEnd(lineContent)) {
						inComments = false;
					}
					return lineContent;
				}
				
				if (isInMethod || isMethodStart(lineContent)) {
					if (!isInMethod) {
						isInMethod = true;
					}
					methodLines.add(lineContent);
					
					if (isMethodEnd(lineContent)) {
						isInMethod = false;
						Method method = new Method(methodLines, new Comment(commentLines));
						methodLines = new ArrayList<String>();
						methods.add(method);
						commentLines = new ArrayList<String>();
						if (!CollectionUtils.isEmpty(annotations)) {
							method.setAnnotations(annotations);
							annotations = new ArrayList<String>();
						}
					}
					return lineContent;
				}
				
				if (lineContent.startsWith("package ")) {
					packageInfo = lineContent;
					return lineContent;
				}
				
				if (lineContent.startsWith("import ")) {
					imports.add(lineContent);
					return lineContent;
				}
				
				if (lineContent.startsWith("public class ")) {
					if (annotations.size() > 0) {
						classAnnotations = annotations;
						annotations = new ArrayList<String>();
					}
					className = lineContent;
					return lineContent;
				}
				
				if (lineContent.trim().startsWith("@")) {
					annotations.add(lineContent);
					return lineContent;
				}
				
				if (isAttribute(lineContent)) {
					Attribute attribute = new Attribute(lineContent, new Comment(commentLines));
					attributes.add(attribute);
					commentLines = new ArrayList<String>();
					if (!CollectionUtils.isEmpty(annotations)) {
						attribute.setAnnotations(annotations);
						annotations = new ArrayList<String>();
					}
					return lineContent;
				}
				
				if (isClassEnd(lineContent)) {
					classEnd = lineContent;
				}
				
				return null;
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
	public void write() {
		List<String> result = new ArrayList<String>();
		
		result.add(packageInfo);
		result.add("");
		
		result.addAll(imports);
		result.add("");
		
		if (!CollectionUtils.isEmpty(classAnnotations)) {
			result.addAll(classAnnotations);
		}
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
	}
	
	/**
	 * 判断这一行是否是方法声明
	 * @param lineContent
	 * @return
	 */
	protected boolean isMethodStart(String lineContent) {
		boolean result = false;
		
		if (lineStartTabCount(lineContent) == 1 ) {
			if (lineContent.contains("abstract") || lineContent.trim().endsWith("{")) {
				result = true; 
			}
		}
		
		return result;
	}

	/**
	 * 判断是否是属性，所有属性都是一行
	 * @param lineContent
	 * @return
	 */
	protected boolean isAttribute(String lineContent) {
		boolean result = false;
		
		if (lineStartTabCount(lineContent) == 1 && lineContent.trim().length() > 0 && lineContent.trim().endsWith(";")) {
			result = true; 
		}
		
		return result;
	}

	/**
	 * 是否是方法的结尾
	 * @param lineContent
	 * @return
	 */
	protected boolean isMethodEnd(String lineContent) {
		boolean result = false;
		
		if (lineStartTabCount(lineContent) == 1 && lineContent.trim().equals("}")) {
			result = true; 
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
		
		if (lineStartTabCount(lineContent) == 0 && lineContent.equals("}")) {
			result = true; 
		}
		
		return result;
	}
	
	/**
	 * 开始时空格的数量
	 * @param lineContent
	 * @return
	 */
	protected int lineStartTabCount(String lineContent) {
		int count = 0;
		lineContent = lineContent.replace("\t", "    ");
		while (lineContent.startsWith("    ")) {	// 4个空格开始
			count ++;
			lineContent = lineContent.substring(4);
		}
		return count;
	}
	
}


