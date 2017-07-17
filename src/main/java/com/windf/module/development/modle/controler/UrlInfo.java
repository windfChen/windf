package com.windf.module.development.modle.controler;

import com.windf.module.development.modle.java.Annotation;
import com.windf.module.development.modle.java.Method;
import com.windf.module.development.pojo.Return;

public class UrlInfo {

	/**
	 * 将一个Method对象，转换为UrlInfo对象
	 * @param method
	 * @return
	 */
	public static UrlInfo fromMethod(Method method) {
		UrlInfo result = new UrlInfo();
		
		result.setMethodName(method.getMethodName());
		
		if (Return.STRING.equals(method.getRet().getType())) {
			result.setAjaxReturn(false);
		} else {
			result.setAjaxReturn(true);
		}
		
		Annotation  requestMappingAnnotation = method.getAnnotationByName("RequestMapping");
		if (requestMappingAnnotation != null) {
			if ("{RequestMethod.GET}".equals(requestMappingAnnotation.getValue("method"))) {
				result.setRequestMethod("get");
			}
			if ("{RequestMethod.POST}".equals(requestMappingAnnotation.getValue("method"))) {
				result.setRequestMethod("post");
			}
			result.setSubPath(requestMappingAnnotation.getValue("value"));
		}
		
		return result;
	}
	
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
