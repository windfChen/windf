package com.windf.module.development.modle.java;

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
}
