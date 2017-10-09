package com.windf.core.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
	/**
	 * 根据路径获取配置文件，获取配置文件中的值
	 * @param classfilePath
	 * @param key
	 * @return
	 */
	public static String get(String classfilePath, String key) {
		Properties properties = new Properties();
		try {
		    ClassLoader loader = PropertiesUtil.class.getClassLoader();
		    properties.load(loader.getResourceAsStream(classfilePath));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		return properties.getProperty(key);

	}
}
