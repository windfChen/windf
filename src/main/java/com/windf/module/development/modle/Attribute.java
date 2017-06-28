package com.windf.module.development.modle;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

class Attribute {
	Comment comment;
	String codes;
	String name;
	String type;
	List<String> annotations;

	Attribute(String lineContent, Comment comment) {
		this.comment = comment;
		this.codes = lineContent;
	}

	/**
	 * setAnnotations
	 * @param annotations
	 */
	void setAnnotations(List<String> annotations) {
		this.annotations = annotations;
	}

	/**
	 * write codes
	 * @return
	 */
	List<String> write() {
		List<String> result = new ArrayList<String>();
		
		result.addAll(comment.write());
		if (!CollectionUtils.isEmpty(annotations)) {
			result.addAll(annotations);
		}
		result.add(codes);
		
		return result;
	}
}
