package com.windf.module.development.modle.controler;

import java.util.ArrayList;
import java.util.List;

import com.windf.core.exception.UserException;
import com.windf.core.util.StringUtil;
import com.windf.module.development.Constant;
import com.windf.module.development.entity.Controler;
import com.windf.module.development.entity.Module;
import com.windf.module.development.entity.ModuleMaster;
import com.windf.module.development.entity.Parameter;
import com.windf.module.development.entity.Return;
import com.windf.module.development.entity.UrlInfo;
import com.windf.module.development.modle.java.Annotation;
import com.windf.module.development.modle.java.CodeBlock;
import com.windf.module.development.modle.java.Comment;
import com.windf.module.development.modle.java.JavaCoder;
import com.windf.module.development.modle.java.Method;
import com.windf.module.development.modle.java.code.ControlerReturnCoder;
import com.windf.module.development.modle.java.code.ParameterVerifyCoder;

public class ControlerCoder {
	public static final String RETURN_AJAX = "ajax";
	public static final String RETURN_PAGE = "page";
	
	private JavaCoder javaCoder;
	private Module module;
	private Controler controler;
	
	public ControlerCoder(String moduleCode, String className) throws UserException {
		className = StringUtil.firstLetterUppercase(className) + "Controler";
		javaCoder = new JavaCoder(Constant.JAVA_MODULE_BASE_PACKAGE + "/" + moduleCode + "/controler", className);
		
		module = ModuleMaster.getInstance().findModuleByCode(moduleCode);
		controler = module.getControler(className);
		if (controler == null) {
			controler = new Controler();
			controler.setName(className);
			module.addControler(controler);
		}
	}
	
	public void write() {
		javaCoder.write();
	}
	
	/**
	 * 设置path
	 * @param webPath
	 * @throws UserException
	 */
	public void setWebPath(String webPath) throws UserException {
		Annotation controllerAnnotation = new Annotation("Controller");
		javaCoder.setAnnotation(controllerAnnotation);
		
		Annotation scopeAnnotation = new Annotation("Scope", "prototype");
		javaCoder.addAnnotation(scopeAnnotation);
		
		Annotation requestMappingAnnotation = new Annotation("RequestMapping");
		requestMappingAnnotation.addValue("value", webPath);
		javaCoder.addAnnotation(requestMappingAnnotation);
		
		controler.setUrlPath(webPath);
		module.write();
	}
	
	/**
	 * 添加urlInfo
	 * @param subPath
	 * @param methodName
	 * @param ajaxReturn
	 * @param get
	 * @throws UserException
	 */
	public void addSubPath(UrlInfo urlInfo) throws UserException {
		String subPath = urlInfo.getSubPath();
		String methodName = urlInfo.getName();
		boolean ajaxReturn = urlInfo.isAjaxReturn();
		String requestMethod = urlInfo.getRequestMethod();
		
		/*
		 * 写代码
		 */
		Return ret =  null;
		if (ajaxReturn) {
			ret = new Return(Return.MAP_STRING_OBJECT);
		} else {
			ret = new Return(Return.STRING);
		}
		
		Method method = new Method(methodName, ret, null, null, false);
		if (ajaxReturn) {
			Annotation responseBodyAnnotation = new Annotation("ResponseBody");
			method.addAnnotation(responseBodyAnnotation);
		}
		Annotation requestMappingAnnotation = new Annotation("RequestMapping");
		requestMappingAnnotation.addValue("value", subPath);
		if ("get".equals(requestMethod)) {
			requestMappingAnnotation.addValue("method", "{RequestMethod.GET}");
		} else {
			requestMappingAnnotation.addValue("method", "{RequestMethod.POST}");
		}
		method.addAnnotation(requestMappingAnnotation);
		
		javaCoder.createMethod(method);
		
		/*
		 * 更行controler
		 */
		controler.addUrlInfo(urlInfo);
		module.write();
	}
	
	/**
	 * 获得所有url信息
	 * @return
	 */
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

	/**
	 * 添加参数验证
	 * @param subPath
	 * @param parameters
	 * @throws UserException
	 */
	public void addParameterVeriry(String subPath, List<Parameter> parameters) throws UserException {
		Method method = this.getMethodBySubPath(subPath);
		
		/*
		 * 更新代码
		 */
		CodeBlock<List<Parameter>> codeBlock = new CodeBlock<List<Parameter>>();
		codeBlock.setCodeable(new ParameterVerifyCoder());
		codeBlock.setTabCount(2);
		codeBlock.serialize(parameters);
		Comment comment =  new Comment(2, false);
		comment.addLine("验证参数");
		codeBlock.setComment(comment);
		method.addCodeBlock(0, codeBlock);
		
		/*
		 * 更新control
		 */
		UrlInfo urlInfo = controler.findUrlInfo(method.getMethodName());
		urlInfo.setParameters(parameters);
		controler.addUrlInfo(urlInfo);
	}
	
	/**
	 * 获得参数验证
	 * @param subPath
	 * @return
	 */
	public List<Parameter> getParameterVeriry(String subPath) {
		Method method = this.getMethodBySubPath(subPath);
		@SuppressWarnings("unchecked")
		CodeBlock<List<Parameter>> codeBlock = method.getCodeBlock(0);
		codeBlock.setCodeable(new ParameterVerifyCoder());
		return codeBlock.build();
	}
	
	/**
	 * 根据访问路径获得方法
	 * @param subPath
	 * @return
	 */
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

	/**
	 * 为方法设置返回类型
	 * @param subPath
	 * @param returnType
	 * @throws UserException 
	 */
	public void setReturn(String subPath, ControlerReturn ret) throws UserException {
		Method method = this.getMethodBySubPath(subPath);
		
		/*
		 * 更新代码
		 */
		CodeBlock<ControlerReturn> codeBlock = new  CodeBlock<ControlerReturn>();
		codeBlock.setCodeable(new ControlerReturnCoder());
		codeBlock.setTabCount(2);
		codeBlock.serialize(ret);
		Comment comment = new Comment(2, false);
		comment.addLine("返回参数");
		codeBlock.setComment(comment);
		method.addCodeBlock(100, codeBlock);
		
		/*
		 * 更新control
		 */
		UrlInfo urlInfo = controler.findUrlInfo(method.getMethodName());
		urlInfo.setControlerReturn(ret);
		controler.addUrlInfo(urlInfo);
	}
	
	/**
	 * 获得返回值
	 * @param subPath
	 * @return
	 */
	public ControlerReturn getRetrun(String subPath) {
		Method method = this.getMethodBySubPath(subPath);
		@SuppressWarnings("unchecked")
		CodeBlock<ControlerReturn> codeBlock = method.getCodeBlock(1);
		codeBlock.setCodeable(new ControlerReturnCoder());
		return codeBlock.build();
	}
	

}
