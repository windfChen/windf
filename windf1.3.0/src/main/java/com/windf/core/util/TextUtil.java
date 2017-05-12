package com.windf.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 空置判断
 * 
 * @author huze
 *
 */
public class TextUtil {

	/**
	 * 处理空字符串 'null' 会被处理成 ''
	 * 
	 * @param text
	 * @return
	 */
	public static String fixNull(String text) {
		if (null == text || "null".equalsIgnoreCase(text)) {
			return "";
		}
		return text;
	}

	/**
	 * 初始化数字
	 * @param text
	 * @return
	 */
	public static Integer fixNumber(String text) {
		return fixNumber( text, 0);
	}

	/**
	 * 初始化数字
	 * @param text
	 * @param defaultValue
	 * @return
	 */
	public static Integer fixNumber(String text,Integer defaultValue) {
		Integer i = defaultValue;
		if(isNumeric(text)) {
			i = Integer.valueOf(text) ;
		}
		return i;
	}
	public static void main(String[] args) {
		System.out.println(fixNumber("asd"));
	}

	/**
	 * 判断是否是数字
	 * @param str
	 * @return
	 */
	private static boolean isNumeric(String str) {
		if(null == str) {
			return false;
		}
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	private final static String REGXP_FOR_Html = "<([^>]*)>"; // 过滤所有以<开头以>结尾的标签   
	
	/**  
     *   
     * 基本功能：过滤所有以"<"开头以">"结尾的标签  
     * <p>  
     *   
     * @param str  
     * @return String  
     */  
    public static String filterHtml(String str) {   
        Pattern pattern = Pattern.compile(REGXP_FOR_Html);   
        Matcher matcher = pattern.matcher(str);   
        StringBuffer sb = new StringBuffer();   
        boolean result1 = matcher.find();   
        while (result1) {   
            matcher.appendReplacement(sb, "");   
            result1 = matcher.find();   
        }   
        matcher.appendTail(sb);   
        return sb.toString();   
    }   
}
