package com.windf.module.development.modle.controler;

import java.util.ArrayList;
import java.util.List;

import com.windf.core.exception.UserException;
import com.windf.module.development.Constant;
import com.windf.module.development.modle.java.Annotation;
import com.windf.module.development.modle.java.JavaCoder;
import com.windf.module.development.modle.java.Method;
import com.windf.module.development.pojo.Return;

public class ControlerCoder {
	private JavaCoder javaCoder;
	
	public ControlerCoder(String moduleCode, String className) throws UserException {
		javaCoder = new JavaCoder(Constant.JAVA_MODULE_BASE_PACKAGE + "/" + moduleCode + "/controler", className);
	}
	
	public void setWebPath(String webPath) {
		Annotation controllerAnnotation = new Annotation("Controller");
		javaCoder.setAnnotation(controllerAnnotation);
		
		Annotation scopeAnnotation = new Annotation("Scope", "prototype");
		javaCoder.addAnnotation(scopeAnnotation);
		
		Annotation requestMappingAnnotation = new Annotation("RequestMapping");
		requestMappingAnnotation.addValue("value", webPath);
		javaCoder.addAnnotation(requestMappingAnnotation);
	}
	
	public void addSubPath(String subPath, String methodName, boolean ajaxReturn, boolean get) throws UserException {
		Return ret =  null;
		if (ajaxReturn) {
			ret = new Return(Return.MAP_STRING_OBJECT);
		} else {
			ret = new Return(Return.STRING);
		}
		
		Method method = new Method(methodName, ret, null, null);
		Annotation requestMappingAnnotation = new Annotation("RequestMapping");
		requestMappingAnnotation.addValue("value", subPath);
		if (get) {
			requestMappingAnnotation.addValue("method", "{RequestMethod.GET}");
		} else {
			requestMappingAnnotation.addValue("method", "{RequestMethod.POST}");
		}
		method.setAnnotation(requestMappingAnnotation);
		
		javaCoder.createMethod(method);
	}
	
	public List<UrlInfo> listAllSubPath() {
		List<UrlInfo> result = new ArrayList<UrlInfo>();
		
		List<Method> methods = javaCoder.getAllMethods();
		if (methods != null) {
			for (Method method : methods) {
				UrlInfo urlInfo = new UrlInfo();
				
				urlInfo.setMethodName(method.getMethodName());
				
				if (Return.STRING.equals(method.getRet().getType())) {
					urlInfo.setAjaxReturn(false);
				} else {
					urlInfo.setAjaxReturn(true);
				}
				
				Annotation  requestMappingAnnotation = method.getAnnotationByName("RequestMapping");
				if (requestMappingAnnotation != null) {
					if ("{RequestMethod.GET}".equals(requestMappingAnnotation.getValue("method"))) {
						urlInfo.setRequestMethod("get");
					}
					if ("{RequestMethod.POST}".equals(requestMappingAnnotation.getValue("method"))) {
						urlInfo.setRequestMethod("post");
					}
					urlInfo.setSubPath(requestMappingAnnotation.getValue("value"));
				}
				
				result.add(urlInfo);
			}
		}
		
		return result;
	}
	
	public void write() {
		javaCoder.write();
	}
}
