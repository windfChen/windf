package com.windf.module.development.modle.controler;

public class UrlInfo {
	private String subPath;
	private String methodName;
	private boolean ajaxReturn;
	private String requestMethod;

	public String getSubPath() {
		return subPath;
	}

	public void setSubPath(String subPath) {
		this.subPath = subPath;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public boolean isAjaxReturn() {
		return ajaxReturn;
	}

	public void setAjaxReturn(boolean ajaxReturn) {
		this.ajaxReturn = ajaxReturn;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

}
