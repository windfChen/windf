package com.windf.module.development;

import java.io.File;

public class Constant extends com.windf.core.Constant{
	/*
	 * web 相关
	 */
	public static final String WEB_BASE_PATH = "/dev";
	public static final String WEB_BASE_VIEW = "/module/development/";
	
	
	public static final String DEFAULT_EXAMPLE_PATH = "example";
	
	public static String  DEVELOPMENT_BASE_PATH = null;
	
	static {
		String classPath = Constant.class.getClassLoader().getResource("").getPath();
		String basePath = classPath.substring(0, classPath.lastIndexOf("src"));
		DEVELOPMENT_BASE_PATH = new File(basePath).getPath() + File.separator;
	}
	
}
