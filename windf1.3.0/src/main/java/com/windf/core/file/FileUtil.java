package com.windf.core.file;

public class FileUtil {
	public static String getClassPath() {
		return FileUtil.class.getClassLoader().getResource("").getPath();
	}
	
	public static String getConfigPath() {
		String classPath = getClassPath();
		String webinfoPath = classPath.substring(0, classPath.lastIndexOf("classes"));
		String configPath = webinfoPath + "config";
		return configPath;
	}
}
