package com.windf.module.development.modle.java;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.windf.core.exception.UserException;
import com.windf.core.util.CollectionUtil;
import com.windf.core.util.StringUtil;
import com.windf.module.development.util.file.TextFileUtil;
import com.windf.module.development.util.file.LineReader;
import com.windf.module.development.util.file.SourceFileUtil;

public class JavaCoder extends AbstractType{
	
	private static Map<String, JavaCoder> allJavaCoders = new HashMap<String, JavaCoder>();
	public static JavaCoder getJavaCoderByName(String name) {
		return allJavaCoders.get(name);
	}
	
	/*
	 * 类信息
	 */
	private Imports imports = new Imports();
	private String modifier;
	private String classType;
	private String className;
	private boolean isAbstract;
	private String classGenre;
	private List<Attribute> attributes = new ArrayList<Attribute>();
	private List<Method> methods = new ArrayList<Method>();
	/*
	 * 类文件行字符串信息
	 */
	private String packageInfo;
	private String extendsStr;
	private String implementsStr;
	private String classEnd;
	/*
	 * 类文件信息
	 */
	private String classPath;
	
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
			this.packageInfo = "package" + CodeConst.WORD_SPLIT + newPackagePath + ";";
			this.className = className;
			this.modifier = CodeConst.MODIFY_PUBLIC;
			this.classType = CodeConst.TYPE_CLASS;
			this.classEnd = "}";
			this.write();
		}
		
		/*
		 * 统一存储
		 */
		allJavaCoders.put(className, this);
	}

	private void readCodes(File javaFile) {
		
		TextFileUtil.readLine(javaFile, new LineReader() {
			
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
				} else if (CodeConst.verify(lineContent, "^\\s*(public|private|protected)?\\s*(abstract)?\\s*(class|interface|@interface){1}\\s*(\\w*)(<(\\w*)>)?\\s*(extends \\S*)?\\s*(implements\\s*[^\\{]*)?\\s*\\{\\s*$")) {
					String[] ss = CodeConst.getInnerString(lineContent, "^\\s*(public|private|protected)?\\s*(abstract)?\\s*(class|interface|@interface){1}\\s*(\\w*)(<(\\w*)>)?\\s*(extends \\S*)?\\s*(implements\\s*[^\\{]*)?\\s*\\{\\s*$");
					modifier = ss[0];
					if (ss[1] != null) {
						isAbstract = true;
					}
					classType = ss[2];
					className = ss[3];
					classGenre = ss[5];
					extendsStr = ss[6];
					implementsStr = ss[7];
					
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
		Attribute attribute = new Attribute(type, name);
		createAttribute(attribute);
		return attribute;
	}
	
	public Attribute createAttribute(Attribute attribute) throws UserException {
		if (this.getAttribute(attribute.getName()) != null) {
			throw new UserException("属性已存在");
		}
		
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
		result.add(this.getClassNameLine());
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
		
		TextFileUtil.writeFile(new File(this.classPath), result);
		
		return result;
	}
	
	public List<Method> getAllMethods() {
		return this.methods;
	}
	
	public List<Attribute> getAllAttributes() {
		JavaCoder parent = this.getParent();
		
		List<Attribute> result = null;
		if (parent != null) {
			result = new ArrayList<Attribute>();
			result.addAll(parent.getAllAttributes());
			result.addAll(this.attributes);
		} else {
			result = this.attributes;
		}
		
		return result;
	}
	
	protected String getClassNameLine() {
		StringBuffer classNameLine = new StringBuffer();
		if (!"package".equals(modifier)) {
			classNameLine.append(modifier + CodeConst.WORD_SPLIT);
		}
		classNameLine.append(classType + CodeConst.WORD_SPLIT);
		classNameLine.append(className + CodeConst.WORD_SPLIT);
		if (StringUtil.isNotEmpty(extendsStr)) {
			classNameLine.append(extendsStr + CodeConst.WORD_SPLIT);
		}
		if (StringUtil.isNotEmpty(implementsStr)) {
			classNameLine.append(implementsStr + CodeConst.WORD_SPLIT);
		}
		classNameLine.append("{");
		return classNameLine.toString();
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
	
	protected JavaCoder getParent() {
		JavaCoder result = null;
		if (StringUtil.isNotEmpty(extendsStr)) {
			String parentName = extendsStr.trim();
			String[] ss = CodeConst.getInnerString(parentName, "extends (\\w+)(<(\\w*)>)?");
			if (ss.length > 0) {
				parentName = ss[0];
			}
			result = allJavaCoders.get(parentName);
		}
		return result;
	}

	public String getClassPath() {
		return classPath;
	}

	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}

	public String getPackageInfo() {
		return packageInfo;
	}

	public void setPackageInfo(String packageInfo) {
		this.packageInfo = packageInfo;
	}

	public Imports getImports() {
		return imports;
	}

	public void setImports(Imports imports) {
		this.imports = imports;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public List<Method> getMethods() {
		return methods;
	}

	public void setMethods(List<Method> methods) {
		this.methods = methods;
	}

	public String getClassEnd() {
		return classEnd;
	}

	public void setClassEnd(String classEnd) {
		this.classEnd = classEnd;
	}

	public String getExtendsStr() {
		return extendsStr;
	}

	public void setExtendsStr(String extendsStr) {
		this.extendsStr = extendsStr;
	}

	public String getImplementsStr() {
		return implementsStr;
	}

	public void setImplementsStr(String implementsStr) {
		this.implementsStr = implementsStr;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public void setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

	public String getClassGenre() {
		return classGenre;
	}

	public void setClassGenre(String classGenre) {
		this.classGenre = classGenre;
	}
	
}


