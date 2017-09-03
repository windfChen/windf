package com.windf.module.development.modle.controler;

import java.util.List;

import com.windf.module.development.modle.java.Annotation;
import com.windf.module.development.modle.java.Method;
import com.windf.module.development.pojo.Parameter;
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
	
	private ControlerReturn controlerReturn;
	private List<Parameter> parameters;
	
	private Controler controler;
	
	/**
	 * 获得完成url
	 * @return
	 */
	public String getUrl() {
		String result = null;
		if (controler != null && controler.getModule() != null) {
			result = controler.getModule().getPath() + controler.getUrlPath() + this.subPath;
		}
		return result;
	}

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

	public ControlerReturn getControlerReturn() {
		return controlerReturn;
	}

	public void setControlerReturn(ControlerReturn controlerReturn) {
		this.controlerReturn = controlerReturn;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public Controler getControler() {
		return controler;
	}

	public void setControler(Controler controler) {
		this.controler = controler;
	}

}
