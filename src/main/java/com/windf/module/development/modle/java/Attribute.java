package com.windf.module.development.modle.java;

import java.util.ArrayList;
import java.util.List;

public class Attribute extends AbstractType {
	/**
	 * 判断是否是属性，所有属性都是一行
	 * @param lineContent
	 * @return
	 */
	static boolean isAttributeLine(String lineContent) {
		boolean result = false;
		
		if (CodeConst.lineStartTabCount(lineContent) == 1 && lineContent.trim().length() > 0 && lineContent.trim().endsWith(";")) {
			result = true; 
		}
		
		return result;
	}
	
	/*
	 * codes
	 */
	String codes;
	/*
	 * info
	 */
	String type;
	String name;

	Attribute(String lineContent) {
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
		result.addAll(this.getAnnotationsString(1));
		result.add(codes);
		
		return result;
	}

	public String getCodes() {
		return codes;
	}

	public void setCodes(String codes) {
		this.codes = codes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
