package com.windf.module.development.modle.controler;

import java.util.ArrayList;
import java.util.List;

import com.windf.core.exception.UserException;
import com.windf.core.util.CollectionUtil;
import com.windf.module.development.Constant;
import com.windf.module.development.modle.java.Annotation;
import com.windf.module.development.modle.java.CodeBlock;
import com.windf.module.development.modle.java.Comment;
import com.windf.module.development.modle.java.JavaCoder;
import com.windf.module.development.modle.java.Method;
import com.windf.module.development.modle.java.ParameterVerifyCoder;
import com.windf.module.development.pojo.Parameter;
import com.windf.module.development.pojo.Return;

public class ControlerCoder {
	
	private JavaCoder javaCoder;
	public ControlerCoder(String moduleCode, String className) throws UserException {
		javaCoder = new JavaCoder(Constant.JAVA_MODULE_BASE_PACKAGE + "/" + moduleCode + "/controler", className);
	}
	
	public void write() {
		javaCoder.write();
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
				result.add(changeMethod2UrlInfo(method));
			}
		}
		
		return result;
	}

	public void addParameterVeriry(String subPath, List<Parameter> parameters) {
		Method method = this.getMethodBySubPath(subPath);
		
		ParameterVerifyCoder parameterVerifyCoder = new ParameterVerifyCoder(parameters, 2);
		List<String> codes = parameterVerifyCoder.toCodes();
		if (CollectionUtil.isNotEmpty(codes)) {
			CodeBlock codeBlock = new  CodeBlock(codes);
			Comment comment =  new Comment(2, false);
			comment.addLine("验证参数");
			codeBlock.setComment(comment);
			method.addCodeBlock(0, codeBlock);
		}
		
	}
	
	public List<Parameter> getParameterVeriry(String subPath) {
		Method method = this.getMethodBySubPath(subPath);
		ParameterVerifyCoder parameterVerifyCoder = new ParameterVerifyCoder();
		return parameterVerifyCoder.toObject(method.getCodeBlock(0).getCodes());
	}
	
	protected Method getMethodBySubPath(String subPath) {
		Method result = null;
		
		List<Method> methods = javaCoder.getAllMethods();
		if (methods != null) {
			for (Method method : methods) {
				UrlInfo urlInfo = this.changeMethod2UrlInfo(method);
				if (urlInfo.getSubPath().equals(subPath)) {
					result = method;
					break;
				}
			}
		}
		
		return result;
	}
	
	private UrlInfo changeMethod2UrlInfo(Method method) {
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

}
