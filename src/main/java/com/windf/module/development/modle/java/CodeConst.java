package com.windf.module.development.modle.java;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeConst {
	static final String TAB = "    ";
	static final String WORD_SPLIT = " ";
	
	/**
	 * 开始时空格的数量
	 * @param lineContent
	 * @return
	 */
	static int lineStartTabCount(String lineContent) {
		int count = 0;
		lineContent = lineContent.replace("\t", "    ");
		while (lineContent.startsWith("    ")) {	// 4个空格开始
			count ++;
			lineContent = lineContent.substring(4);
		}
		return count;
	}
	
	/**
	 * 开始时空格的数量
	 * @param lineContent
	 * @return
	 */
	static String getTabString(int count) {
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
	static String[] getInnerString(String line, String patternStr) {
		List<String> result = new ArrayList<String>();
		
//		Pattern pattern = Pattern.compile("String (.*) = this\\.getParameter\\(\"(.*)\"\\);");
//		Matcher matcher = pattern.matcher("String name = this.getParameter(\"name\");");
//		while(matcher.find()) {
//			System.out.println(matcher.groupCount());
//			System.out.println(matcher.group());
//			System.out.println(matcher.group(1));
//			System.out.println(matcher.group(2));
//		}
		
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(line);
		while(matcher.find()) {
			for (int i = 0; i < matcher.groupCount(); i++) {
				result.add(matcher.group(i + 1));
			}
		}
		
//		String[] ss = template.split("\\*");
//		
//		if (ss.length > 0) {
//			line = line.substring(line.indexOf(ss[0]) + ss[0].length());
//			for (int i = 1; i < ss.length; i++) {
//				String end = ss[i];
//				
//				result.add(line.substring(0, line.indexOf(end)));
//				line = line.substring(line.indexOf(end) + end.length());
//			}
//		}
		
		return result.toArray(new String[result.size()]);
	}
}
