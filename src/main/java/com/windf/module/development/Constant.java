package com.windf.module.development;

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
	public static final String MODULE_WEB_PATH = "/dev";
	public static final String WEB_BASE_VIEW = "/module/development/";
	public static final String JAVA_MODULE_BASE_PACKAGE = "/com/windf/module";
	
	public static final String DEFAULT_EXAMPLE_PATH = "example";
	

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
