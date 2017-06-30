package com.windf.module.development.modle.java;

import java.util.ArrayList;
import java.util.List;

class CodeBlock {

	Comment comment;
	List<String> codes;
	
	CodeBlock (List<String> codes, Comment comment) {
		this.codes = codes;
		this.comment = comment;
	}

	/**
	 * write code
	 * @return
	 */
	List<String> write() {
		List<String> result = new ArrayList<String>();
		
		result.addAll(comment.write());
		result.addAll(codes);
		
		return result;
	}
}
