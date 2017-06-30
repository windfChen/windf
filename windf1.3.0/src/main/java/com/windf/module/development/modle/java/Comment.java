package com.windf.module.development.modle.java;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

class Comment {
	
	/**
	 * isCommentStart
	 * @param lineContent
	 * @return
	 */
	static boolean isCommentStart(String lineContent) {
		return lineContent.trim().startsWith("/*");
	}
	
	/**
	 * isCommentEnd
	 */
	static boolean isCommentEnd(String lineContent) {
		return lineContent.trim().equals("*/");
	}
	
	List<String> comment;
	
	Comment (List<String> comment) {
		this.comment = comment;
	}

	/**
	 * write codes
	 * @return
	 */
	List<String> write() {
		List<String> result = new ArrayList<String>();
		
		if (!CollectionUtils.isEmpty(comment)) {
			result.addAll(result);
		}
		
		return result;
	}
	
}
