package com.windf.module.module;

import java.io.File;

public class Constant {
	/*
	 * web 相关
	 */
	public static final String WEB_BASE_PATH = "/module";
	public static final String WEB_BASE_VIEW = "module";
	
	
	public static final String DEFAULT_EXAMPLE_PATH = "example";
	
	public static String  DEVELOPMENT_BASE_PATH= null;
	public static String DEVELOPMENT_JAVA_PATH = null;
	public static String DEVELOPMENT_RESOURCE_PATH = null;
	
	static {
		String classPath = Constant.class.getClassLoader().getResource("").getPath();
		String basePath = classPath.substring(0, classPath.lastIndexOf("src"));
		DEVELOPMENT_BASE_PATH = new File(basePath).getPath();
		DEVELOPMENT_JAVA_PATH = DEVELOPMENT_BASE_PATH + "/src/main/java/com/windf/module";
		DEVELOPMENT_RESOURCE_PATH = DEVELOPMENT_BASE_PATH + "/src/main/resource";
	}
	
}
