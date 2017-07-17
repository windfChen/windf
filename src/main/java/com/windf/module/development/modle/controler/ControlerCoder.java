package com.windf.module.development.modle.controler;

import java.util.ArrayList;
import java.util.List;

import com.windf.core.exception.UserException;
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
				result.add(UrlInfo.fromMethod(method));
			}
		}
		
		return result;
	}

	public void addParameterVeriry(String subPath, List<Parameter> parameters) {
		Method method = this.getMethodBySubPath(subPath);
		
		CodeBlock<List<Parameter>> codeBlock = new  CodeBlock<List<Parameter>>();
		codeBlock.setCodeable(new ParameterVerifyCoder());
		codeBlock.setTabCount(2);
		codeBlock.serialize(parameters);
		Comment comment =  new Comment(2, false);
		comment.addLine("验证参数");
		codeBlock.setComment(comment);
		method.addCodeBlock(0, codeBlock);
		
	}
	
	public List<Parameter> getParameterVeriry(String subPath) {
		Method method = this.getMethodBySubPath(subPath);
		@SuppressWarnings("unchecked")
		CodeBlock<List<Parameter>> codeBlock = method.getCodeBlock(0);
		codeBlock.setCodeable(new ParameterVerifyCoder());
		return codeBlock.build();
	}
	
	protected Method getMethodBySubPath(String subPath) {
		Method result = null;
		
		List<Method> methods = javaCoder.getAllMethods();
		if (methods != null) {
			for (Method method : methods) {
				UrlInfo urlInfo = UrlInfo.fromMethod(method);
				if (urlInfo.getSubPath().equals(subPath)) {
					result = method;
					break;
				}
			}
		}
		
		return result;
	}
	

}
