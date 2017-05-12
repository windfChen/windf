package com.windf.core.util;

import java.util.HashMap;
import java.util.Map;

public class StringUtil {
    public static final String UTF8 = "UTF-8";
	
	/**
	 * 判断字符串中有没有空字符串
	 * @param obj
	 * @return
	 */
	public static boolean hasEmpty(String... str) {
		boolean result = false;
		if (str.length > 0) {
			for (int i = 0; i < str.length; i++) {
				if (isEmpty(str[i])) {
					result = true;
					break;
				}
			}
		}
		return result;
	}
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return org.apache.commons.lang.StringUtils.isEmpty(str);
	}
	
	  /**
     * 将以长串压缩
     *
     * @param data
     * @return
     */
    public static int getHashCode(String data) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("key", data);
        System.out.println(data + "\t=====hashcode=" + map.hashCode());
        return map.hashCode();
    }
    
}
