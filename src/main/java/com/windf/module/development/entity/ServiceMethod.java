package com.windf.module.development.entity;

import java.util.List;

import com.windf.module.development.entity.ExceptionType;
import com.windf.module.development.entity.Parameter;
import com.windf.module.development.entity.Return;
import com.windf.module.development.modle.java.Method;

public class ServiceMethod {

	/**
	 * 转换Method 为 ServiceMethod
	 * 
	 * @param method
	 * @return
	 */
	public static ServiceMethod fromMethod(Method method) {
		ServiceMethod result = new ServiceMethod();

		result.setName(method.getMethodName());
		result.setParameters(method.getParameters());
		result.setRet(method.getRet());
		result.setExceptionType(method.getExceptionType());

		return result;
	}

	private String name;
	private List<Parameter> parameters;
	private Return ret;
	private ExceptionType exceptionType;

	private Service service;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public Return getRet() {
		return ret;
	}

	public void setRet(Return ret) {
		this.ret = ret;
	}

	public ExceptionType getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(ExceptionType exceptionType) {
		this.exceptionType = exceptionType;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

}
