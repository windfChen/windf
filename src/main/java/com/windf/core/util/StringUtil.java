package com.windf.core.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

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
	 * 判断字符串是否不为空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return org.apache.commons.lang.StringUtils.isNotEmpty(str);
	}
	
	/**
	 * 如果字符串为null返回空字符串，否则返回该字符串
	 * @param str
	 * @return
	 */
	public static String fixNull(String str) {
		return str == null? "": str;
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
    
    /**
     * 将字符中指定字符作为分隔符，分隔符后首字母大写
     * @param str
     * @param separator
     * @return
     */
    public static String toCamelCase(String str, String separator) {
    	if (ParameterUtil.hasEmpty(str, separator)) {
			return null;
		}
    	
    	StringBuffer result = new StringBuffer();

    	String[] ss = str.split(separator);
    	for (int i = 0; i < ss.length; i++) {
    		if (result.length() > 0) {
    			result.append(firstLetterUppercase(ss[i]));
			} else {
				result.append(ss[i]);
			}
		}
    	
    	return result.toString();
    }
    
    /**
     * 将集合转换为字符串，中间用指定字符隔开
     * @param collection
     * @param separator
     * @return
     */
    public static String join(Collection<? extends Object> collection, String separator) {
    	return StringUtils.join(collection, separator);
    }
    
    
}
