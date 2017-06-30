package com.windf.module.development.modle.java;

import java.util.ArrayList;
import java.util.List;

class CodeBlock extends AbstractType {

	List<String> codes;
	
	CodeBlock (List<String> codes) {
		this.codes = codes;
	}

	/**
	 * write code
	 * @return
	 */
	List<String> write() {
		List<String> result = new ArrayList<String>();
		
		if (comment != null) {
			result.addAll(comment.write());
		}
		result.addAll(codes);
		
		return result;
	}
}
