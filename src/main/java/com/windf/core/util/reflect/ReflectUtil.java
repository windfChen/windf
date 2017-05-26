package com.windf.core.util.reflect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReflectUtil {
	
	/**
	 * 判断类是否是基本类型
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isBaseType(Class clazz) {
		boolean result = true;
		if (!clazz.isPrimitive()) {
			if (!clazz.getName().startsWith("java.lang")) {
				result = false;
			}
		}
		return result;
	}
	
	/**
	 * 判断类是否是基本类型
	 * @param clazz
	 * @return
	 */
	public static boolean isBaseType(Object obj) {
		return isBaseType(obj.getClass());
	}
	
	/**
	 * 判断类，是否是集合类型
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean isCollection(Class clazz) {
		boolean result = false;
		if (clazz.isAssignableFrom(List.class) || clazz.isAssignableFrom(Set.class)) {
			result = true;
		}
		return result;
	}
	
	/**
	 * 根据集合类型，创建集合
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Collection createCollection(Class clazz) {
		Collection result = null;
		if (clazz.isAssignableFrom(List.class)) {
			result = new ArrayList();
		} else if (clazz.isAssignableFrom(Set.class)) {
			result = new HashSet();
		}
		return result;
	}
	
	/**
	 * 判断对象是map
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean isMap(Class clazz) {
		boolean result = false;
		if (clazz.isAssignableFrom(Map.class)) {
			result = true;
		}
		return result;
	}
	
	/**
	 * 根据class，创建一个map
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings({"rawtypes" })
	public static Map createMap(Class clazz) {
		return new HashMap();
	}
	
	/**
	 * 根据class，转换字符串类型
	 * @param clazz
	 * @param value
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T getValue(Class clazz, String value) {
		T result = null;
		if (clazz.isAssignableFrom(Integer.class)) {
			result = (T) new Integer(value);
		} else if (clazz.isAssignableFrom(String.class)){
			result = (T) value;
		}
		
		return result;
	}

}
