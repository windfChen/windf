package com.windf.module.development.modle.service;

import java.util.ArrayList;
import java.util.List;

import com.windf.core.exception.UserException;
import com.windf.core.util.StringUtil;
import com.windf.module.development.Constant;
import com.windf.module.development.modle.java.Annotation;
import com.windf.module.development.modle.java.JavaCoder;
import com.windf.module.development.modle.java.Method;
import com.windf.module.development.pojo.ExceptionType;
import com.windf.module.development.pojo.Parameter;
import com.windf.module.development.pojo.Return;

public class ServiceCoder {
	
	private JavaCoder javaCoder;
	private JavaCoder javaImplCoder;
	
	/**
	 * 创建service，和实现类
	 * eg：ModuleService 、ModuleServiceSqlImpl：对应className为：module、implId为：sql
	 * @param moduleCode 模块code
	 * @param className service类名称
	 * @param implId 实现类id，如果为null 默认加Impl
	 * @throws UserException
	 */
	public ServiceCoder(String moduleCode, String className, String implId) throws UserException {
		className = StringUtil.firstLetterUppercase(className) + "Service";
		
		javaCoder = new JavaCoder(Constant.JAVA_MODULE_BASE_PACKAGE + "/" + moduleCode + "/service", className);
		javaImplCoder = new JavaCoder(Constant.JAVA_MODULE_BASE_PACKAGE + "/" + moduleCode + "/service/impl", 
				className + StringUtil.firstLetterUppercase(StringUtil.fixNull(implId)) + "Impl");
		
		Annotation serviceAnnotation = javaImplCoder.getAnnotationByName("Service");
		if (serviceAnnotation == null) {
			serviceAnnotation = new Annotation("Service");
			javaImplCoder.setAnnotation(serviceAnnotation);
		}
	}
	
	/**
	 * 获取service所有接口方法
	 * @return
	 */
	public List<ServiceMethod> listAllMethod() {
		List<ServiceMethod> result = new ArrayList<ServiceMethod>();
		
		List<Method> methods = javaCoder.getAllMethods();
		if (methods != null) {
			for (Method method : methods) {
				result.add(ServiceMethod.fromMethod(method));
			}
		}
		return result;
	}
	
	/**
	 * 创建一个方法
	 * @param ret
	 * @param name
	 * @param parameters 所有的参数
	 * @param exceptionName 所有抛出的异常
	 * @return
	 * @throws UserException 
	 */
	public ServiceMethod createMethod(Return ret, String name, List<Parameter> parameters, ExceptionType exceptionType) throws UserException {
		ServiceMethod result = null;
		
		Method javaCoderMethod = new Method(name, ret, parameters, exceptionType, true);
		Method javaImplCoderMethod = new Method(name, ret, parameters, exceptionType, false);
		
		javaCoderMethod = javaCoder.createMethod(javaCoderMethod);
		javaImplCoderMethod = javaImplCoder.createMethod(javaImplCoderMethod);
		
		if (javaImplCoderMethod != null) {
			result = ServiceMethod.fromMethod(javaImplCoderMethod);
		}
		return result;
	}
	
	/**
	 * 持久化
	 */
	public void write() {
		javaCoder.write();
		javaImplCoder.write();
	}
}
