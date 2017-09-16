package com.windf.module.form.pojo.vo;

import java.util.Map;

public class FormItemUserValueVO {
	private String formId;
	private Map<String, Object> data;
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	
}
