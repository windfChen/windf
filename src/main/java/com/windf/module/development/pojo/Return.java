package com.windf.module.development.pojo;

public class Return {
	public static final String STRING = "String";
	public static final String MAP_STRING_OBJECT = "Map<String, Object>";
	
	private String type;
	
	public Return() {
		
	}
	
	public Return(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
