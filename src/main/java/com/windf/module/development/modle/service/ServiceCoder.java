package com.windf.module.development.modle.service;

import java.util.ArrayList;
import java.util.List;

import com.windf.core.exception.UserException;
import com.windf.core.util.StringUtil;
import com.windf.module.development.Constant;
import com.windf.module.development.entity.ExceptionType;
import com.windf.module.development.entity.Module;
import com.windf.module.development.entity.ModuleMaster;
import com.windf.module.development.entity.Parameter;
import com.windf.module.development.entity.Return;
import com.windf.module.development.entity.Service;
import com.windf.module.development.entity.ServiceMethod;
import com.windf.module.development.modle.java.Annotation;
import com.windf.module.development.modle.java.JavaCoder;
import com.windf.module.development.modle.java.Method;

public class ServiceCoder {
	
	private JavaCoder javaCoder;
	private JavaCoder javaImplCoder;
	private Service service;
	private Module module;
	
	/**
	 * 创建service，和实现类
	 * eg：ModuleService 、ModuleServiceSqlImpl：对应className为：module、implId为：sql
	 * @param moduleCode 模块code
	 * @param className service类名称
	 * @param implId 实现类id，如果为null 默认加Impl
	 * @throws UserException
	 */
	public ServiceCoder(String moduleCode, String className, String implId) throws UserException {
		String serviceName = StringUtil.firstLetterUppercase(className) + "Service";
		
		javaCoder = new JavaCoder(Constant.JAVA_MODULE_BASE_PACKAGE + "/" + moduleCode + "/service", serviceName);
		javaImplCoder = new JavaCoder(Constant.JAVA_MODULE_BASE_PACKAGE + "/" + moduleCode + "/service/impl", 
				serviceName + StringUtil.firstLetterUppercase(StringUtil.fixNull(implId)) + "Impl");
		
		Annotation serviceAnnotation = javaImplCoder.getAnnotationByName("Service");
		if (serviceAnnotation == null) {
			serviceAnnotation = new Annotation("Service");
			javaImplCoder.setAnnotation(serviceAnnotation);
		}
		
		module = ModuleMaster.getInstance().findModuleByCode(moduleCode);
//		service = module.getServiceByName(serviceName);
		if (service == null) {
			service = new Service();
			service.setServiceName(serviceName);
//			module.addService(service);
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
	public ServiceMethod createMethod(ServiceMethod serviceMethod) throws UserException {
		ServiceMethod result = null;
		
		Return ret = serviceMethod.getRet();
		String name = serviceMethod.getName();
		List<Parameter> parameters = serviceMethod.getParameters();
		ExceptionType exceptionType = serviceMethod.getExceptionType();
		
		Method javaCoderMethod = new Method(name, ret, parameters, exceptionType, true);
		Method javaImplCoderMethod = new Method(name, ret, parameters, exceptionType, false);
		
		javaCoderMethod = javaCoder.createMethod(javaCoderMethod);
		javaImplCoderMethod = javaImplCoder.createMethod(javaImplCoderMethod);
		
		if (javaImplCoderMethod != null) {
			result = ServiceMethod.fromMethod(javaImplCoderMethod);
			service.addServiceMethod(result);
			module.write();
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
