package com.windf.core.util;

import java.util.Date;

public class ParameterUtil {
	/**
	 * 获取int类型的参数
	 * @param parameter
	 * @return
	 */
	public static Integer getInteger(String parameter) {
		Integer result = null;
		if (!StringUtil.isEmpty(parameter)) {
			result = Integer.parseInt(parameter);
		}
		return result;
	}
	
	/**
	 * 获取long类型的参数
	 * @param parameter
	 * @return
	 */
	public static Long getLong(String parameter) {
		Long result = null;
		if (!StringUtil.isEmpty(parameter)) {
			result = Long.parseLong(parameter);
		}
		return result;
	}
	
	/**
	 * 获取date类型的参数
	 * @param parameter
	 * @return
	 */
	public static Date getDate(String parameter) {
		Date result = null;
		if (!StringUtil.isEmpty(parameter)) {
			result = DateUtil.parseDate(parameter);
		}
		return result;
	}
	
	/**
	 * 设置默认值
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static String defaultValue(String str, String defaultValue) {
		String result = str;
		if (StringUtil.isEmpty(str)) {
			result = defaultValue;
		}
		return result;
	}
	
	/**
	 * 判断字符串中有没有空字符串
	 * @param obj
	 * @return
	 */
	public static boolean hasEmpty(Object... obj) {
		boolean result = false;
		if (obj.length > 0) {
			for (int i = 0; i < obj.length; i++) {
				if (obj[i] == null || StringUtil.isEmpty(obj[i].toString())) {
					result = true;
					break;
				}
			}
		}
		return result;
	}
}
