package com.windf.module.development.modle.java;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.windf.core.exception.UserException;
import com.windf.core.util.CollectionUtil;
import com.windf.module.development.util.file.JavaFileUtil;
import com.windf.module.development.util.file.JavaFileUtil.LineReader;
import com.windf.module.development.util.file.SourceFileUtil;

public class JavaCoder extends AbstractType{

	private String classPath;
	private String packageInfo;
	private Imports imports = new Imports();
	private String className;
	private List<Attribute> attributes = new ArrayList<Attribute>();
	private List<Method> methods = new ArrayList<Method>();
	private String classEnd;
	
	public JavaCoder(String packagePath, String className) {
		this.classPath = SourceFileUtil.getJavaPath() + "/" + packagePath + "/" + className + ".java";
		
		File javaFile = new File(this.classPath);
		
		/*
		 * 如果不存在，创建java 
		 */
		if (javaFile.exists()) {
			readCodes(javaFile);
		} else {
			String newPackagePath = packagePath.replace("/", ".");
			if (newPackagePath.startsWith(".")) {
				newPackagePath = newPackagePath.substring(1);
			}
			this.packageInfo = "package" + CodeConst.WORD_SPLIT + newPackagePath;
			this.className = "public" + CodeConst.WORD_SPLIT + "class" + CodeConst.WORD_SPLIT + className + CodeConst.WORD_SPLIT + "{";
			this.classEnd = "}";
			this.write();
		}
	}

	private void readCodes(File javaFile) {
		
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
			System.out.println(lineContent);
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
				} else if (Method.isInterfaceMethod(lineContent)) {
					method = new Method(lineContent);
					methods.add(method);
					method.setComment(comment);
					method.setAnnotations(annotations);
					method.initCodeBlocks();
					reset();
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
	public Method createMethod(Method method) throws UserException{
		if (this.getMethod(method.methodName) != null) {
			throw new UserException("方法已存在");
		}
		
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
		
		if (CollectionUtil.isNotEmpty(methods)) {
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
	public Attribute createAttribute(String type, String name) throws UserException {
		if (this.getAttribute(name) != null) {
			throw new UserException("属性已存在");
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
		
		if (CollectionUtil.isNotEmpty(attributes)) {
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
		result.addAll(getAnnotationsString(0));
		result.add(className);
		result.add("");
		
		for (int i = 0; i < attributes.size(); i++) {
			Attribute attribute = attributes.get(i);
			result.addAll(attribute.write());
		}
		if (attributes.size() > 0) {
			result.add("");
		}
		
		for (int i = 0; i < methods.size(); i++) {
			Method method = methods.get(i);
			result.addAll(method.write());
			result.add("");
		}
		
		result.add(classEnd);
		
		JavaFileUtil.writeFile(new File(this.classPath), result);
		
		return result;
	}
	
	public List<Method> getAllMethods() {
		return this.methods;
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


