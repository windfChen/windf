package com.windf.core.util;

import java.util.HashMap;
import java.util.Map;

public class StringUtil {
    public static final String UTF8 = "UTF-8";
	
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
        return map.hashCode();
    }
    
    /**
     * 首字母大写
     * @param str
     * @return
     */
    public static String firstLetterUppercase(String str) {
    	if (str == null || str.length() <= 1) {
			return str;
		} else {
			return str.substring(0, 1).toUpperCase() + str.substring(1); 
		}
    }
    
}
