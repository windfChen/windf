package com.windf.module.development.modle.java;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeConst {
	static final String TAB = "    ";
	static final String WORD_SPLIT = " ";
	public static final String MODIFY_PUBLIC = "public";
	public static final String MODIFY_PRIVATE = "private";
	public static final String MODIFY_PROTECTED = "protected";
	public static final String MODIFY_PACKAGE = "pacakge";
	public static final String TYPE_CLASS = "class";
	public static final String TYPE_INTERFACE = "interface";
	public static final String TYPE_AT_INTERFACE = "@interface";
	
	/**
	 * 获取开始时空格的数量
	 * @param lineContent
	 * @return
	 */
	public static int lineStartTabCount(String lineContent) {
		int count = 0;
		lineContent = lineContent.replace("\t", "    ");
		while (lineContent.startsWith("    ")) {	// 4个空格开始
			count ++;
			lineContent = lineContent.substring(4);
		}
		return count;
	}
	
	/**
	 * 设置开始时空格的数量
	 * @param lineContent
	 * @return
	 */
	public static String getTabString(int count) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < count; i++) {
			result.append("    ");
		}
		return result.toString();
	}
	
	/**
	 * 获得一句话中指定的部分
	 * @eg：line = if (!RegularUtil.match(ageStr, RegularUtil.NUMBER)) {
	 * 	    template = if (!RegularUtil.match(*, *)) {	
	 *         reutrn ：["ageStr", "RegularUtil.NUMBER"]
	 * @param line
	 * @return
	 */
	public static String[] getInnerString(String line, String patternStr) {
		List<String> result = new ArrayList<String>();
		
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(line);
		while(matcher.find()) {
			for (int i = 0; i < matcher.groupCount(); i++) {
				result.add(matcher.group(i + 1));
			}
		}
		
		return result.toArray(new String[result.size()]);
	}
	
	/**
	 * 验证改行是否和表达式相匹配
	 * @param line
	 * @param patternStr
	 * @return
	 */
	public static boolean verify(String line, String patternStr) {
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(line);
		return matcher.matches();
	}

	/**
	 * 获得字符串内容中的字符串
	 * eg: "hello world" --> hello world
	 * @param str
	 * @return
	 */
	public static String getStringContent(String str) {
		String result = null;
		if (str.startsWith("\"") && str.endsWith("\"")) {
			result = str.substring(1, str.length() - 1);
		}
		return result;
	}
	
}
