package com.windf.module.development.modle.java;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

public class Comment {
	
	/**
	 * 判断是否是注释开始标志
	 * @param lineContent
	 * @return
	 */
	static boolean isCommentStart(String lineContent) {
		return lineContent.trim().startsWith("/*");
	}
	
	/**
	 * 判断是否是注释结束标志
	 */
	static boolean isCommentEnd(String lineContent) {
		return lineContent.trim().equals("*/");
	}
	
	String commentStrat;
	List<String> comment = new ArrayList<String>();
	String commentEnd;
	
	private int tabCount = 0;
	
	public Comment(int tabCount, boolean document) {
		this.tabCount = tabCount;
		this.commentStrat = CodeConst.getTabString(tabCount) + (document? "/**": "/*");
		this.commentEnd = CodeConst.getTabString(tabCount) + " */";
	}
	
	Comment (String commentStrat) {
		this.commentStrat = commentStrat;
	}
	
	void end(String commentEnd) {
		this.commentEnd = commentEnd;
	}
	
	
	/**
	 * 添加一行注释
	 * @param lineContent
	 */
	public void addLine(String lineContent) {
		comment.add( (tabCount > 0?CodeConst.getTabString(tabCount) + " * ": "") + lineContent);
	}

	/**
	 * 获得注释的代码形式
	 * 如果注释为空，返回长度为0的list
	 * @return
	 */
	List<String> write() {
		List<String> result = new ArrayList<String>();
		
		if (!CollectionUtils.isEmpty(comment)) {
			result.add(commentStrat);
			result.addAll(comment);
			result.add(commentEnd);
		}
		
		return result;
	}
	
}
