package com.windf.module.development.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.windf.core.util.RegularUtil;
import com.windf.module.development.file.JavaFileUtil.LineReader;
import com.windf.module.development.pojo.Parameter;
import com.windf.module.development.pojo.Return;

public class JavaCoder {

	public static void main(String[] args) {
		JavaCoder j = new JavaCoder("F:/temp/UrlControler.java");
		System.out.println(j.packageInfo);
		System.out.println(j.className);
	}
	
	private String classPath;
	
	private String packageInfo;
	private List<String> imports = new ArrayList<String>();
	private String className;
	private List<Method> methods;
	private List<Attribute> attribute;
	
	public JavaCoder(String classPath) {
		this.classPath = classPath;
		init();
	}

	private void init() {
		File javaFile = new File(classPath);
		
		JavaFileUtil.readLine(javaFile, new LineReader() {
			
			List<String> annotations = new ArrayList<String>();
			
			@Override
			public String readLine(List<String> oldLines, String lineContent, int lineNo) {
				if (lineContent.startsWith("package ")) {
					packageInfo = lineContent;
				}
				
				if (lineContent.startsWith("import ")) {
					imports.add(lineContent);
				}
				
				if (lineContent.startsWith("public class ")) {
					className = lineContent;
				}
				
				if (RegularUtil.match(lineContent, "^\t[^\t]*$")) {
					if (RegularUtil.match(lineContent, "^\t@.*$")) {
						annotations.add(lineContent);
					}
					
					if (RegularUtil.match(lineContent, "^\t@.*$")) {
						annotations.add(lineContent);
					}
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
	 * @param name
	 * @return
	 */
	public Method create(String name) {
		return null;
	}
	
	/**
	 * 根据方法名，查询一个方法
	 * @param name
	 * @return
	 */
	public Method getMethod(String name) {
		return null;
	}
	
	/**
	 * 创建一个属性
	 * @param name
	 * @return
	 */
	public Attribute createAttribute(String name) {
		return null;
	}
	
	/**
	 * 获得一个属性
	 * @param name
	 * @return
	 */
	public Attribute getAttribute(String name) {
		return null;
	}
	
	/**
	 * 写类
	 */
	public void write() {
		
	}
}

class Method {
	String name;
	List<Parameter> parameters;
	Return ret;
	Exception exception;
	List<CodeBlock> codeBlocks;

}

class Attribute {
	String name;
	String type;
}

class CodeBlock {
	String comments;
	List<String> codes;
}
