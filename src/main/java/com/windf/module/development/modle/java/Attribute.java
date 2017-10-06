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
		lineContent = lineContent.split("//")[0];
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
	String modifier;
	boolean isFinal;
	boolean isStatic;
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
		String[] ss = CodeConst.getInnerString(codes.split("=")[0], "^\\s*(public|private|protected)?( static)?( final)?\\s+(\\w*)\\s+(\\w*)\\s*;?\\s*(//(.*))?$");
		if (ss.length > 0) {
			modifier = ss[0];
			if (modifier == null) {
				modifier = "package";
			}
			isStatic = ss[1] != null;
			isFinal = ss[2] != null;
			type = ss[3];
			name = ss[4];
			name = name.trim();
			if (name.endsWith(";")) {
				name = name.substring(0, name.length() - 1);
				name = name.trim();
			}
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

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	
	public boolean isFinal() {
		return isFinal;
	}

	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

}
