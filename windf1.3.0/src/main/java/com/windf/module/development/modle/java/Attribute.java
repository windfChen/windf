package com.windf.module.development.modle.java;

import java.util.ArrayList;
import java.util.List;

class Attribute extends AbstractType {
	/*
	 * codes
	 */
	String codes;
	/*
	 * info
	 */
	String type;
	String name;

	Attribute(String lineContent, Comment comment) {
		this.comment = comment;
		this.codes = lineContent;
		
		initInfoByCodes();
	}

	Attribute(String type, String name) {
		this.type = type;
		this.name = name;
		
		initCodesByInfo();
	}

	private void initInfoByCodes() {
		String[] ss = codes.split("=")[0].split(CodeConst.WORD_SPLIT);
		if (ss.length >= 2) {
			type = ss[ss.length - 2];
			name = ss[ss.length - 1];
		}
	}
	
	private void initCodesByInfo() {
		this.codes = CodeConst.TAB + "private" + CodeConst.WORD_SPLIT + (type == null? "void": type) + CodeConst.WORD_SPLIT + name + ";";
	}

	/**
	 * write codes
	 * @return
	 */
	List<String> write() {
		List<String> result = new ArrayList<String>();
		
		result.addAll(this.getComment());
		result.addAll(this.getAnnotations(1));
		result.add(codes);
		
		return result;
	}

}
