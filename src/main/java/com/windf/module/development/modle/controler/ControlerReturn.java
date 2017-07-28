package com.windf.module.development.modle.controler;

import com.windf.module.development.pojo.Return;

public class ControlerReturn extends Return {

	private boolean success;
	private String message;
	private String dataName;
	private String viewPath;
	private String sureHref;
	private String sureButtonWord;
	
	public ControlerReturn(String type) {
		super(type);
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public String getViewPath() {
		return viewPath;
	}

	public void setViewPath(String viewPath) {
		this.viewPath = viewPath;
	}

	public String getSureHref() {
		return sureHref;
	}

	public void setSureHref(String sureHref) {
		this.sureHref = sureHref;
	}

	public String getSureButtonWord() {
		return sureButtonWord;
	}

	public void setSureButtonWord(String sureButtonWord) {
		this.sureButtonWord = sureButtonWord;
	}
}
