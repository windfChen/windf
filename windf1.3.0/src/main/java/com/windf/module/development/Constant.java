package com.windf.module.development;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.windf.core.util.CollectionUtil;
import com.windf.core.util.RegularUtil;
import com.windf.core.util.reflect.ReflectUtil;

public class Constant extends com.windf.core.Constant{
	/*
	 * web 相关
	 */
	public static final String WEB_BASE_PATH = "/dev";
	public static final String WEB_BASE_VIEW = "/module/development/";
	public static final String JAVA_MODULE_BASE_PACKAGE = "/com/windf/module";
	
	public static final String DEFAULT_EXAMPLE_PATH = "example";
	
	public static String  DEVELOPMENT_BASE_PATH = null;
	public static String JAVA_SOURCE_BASE_PATH = null;
	
	static {
		String classPath = Constant.class.getClassLoader().getResource("").getPath();
		String basePath = classPath.substring(0, classPath.lastIndexOf("src"));
		DEVELOPMENT_BASE_PATH = new File(basePath).getPath() + File.separator;
		
		JAVA_SOURCE_BASE_PATH =  DEVELOPMENT_BASE_PATH + "/src/main/java/";
	}
	

	/**
	 * 反射获得ReflectUtil中所有正则表达式的常量
	 * @return 常量备注-变量名
	 */
	public static Map<String, String> getAllPattern() {
		Map<String, String> result = new HashMap<String, String>();
		
		Map<String, Object> constantValues = ReflectUtil.getAllConstantValue(RegularUtil.class);
		if (CollectionUtil.isNotEmpty(constantValues)) {
			Iterator<String> iterator = constantValues.keySet().iterator();
			while (iterator.hasNext()) {
				 String noteName = iterator.next();
				 Object noteValue = constantValues.get(noteName);
				 
				 if (noteName.endsWith("_NOTE") && noteValue != null) {
					String patternName = noteName.substring(0, noteName.length() -"_NOTE".length() );
					if (constantValues.get(patternName) != null) {
						result.put((String) noteValue, patternName);
					}
				}
				
			}
		}
		
		return result;
	}
	
	
}
