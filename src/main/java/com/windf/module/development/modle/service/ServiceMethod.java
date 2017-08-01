package com.windf.module.development.modle.service;

import java.util.List;

import com.windf.module.development.modle.java.Method;
import com.windf.module.development.pojo.Parameter;
import com.windf.module.development.pojo.Return;

public class ServiceMethod {
	
	/**
	 * 转换Method 为 ServiceMethod
	 * @param method
	 * @return
	 */
	public static ServiceMethod fromMethod(Method method) {
		ServiceMethod result = new ServiceMethod();

		result.setName(method.getMethodName());
		result.setParameters(method.getParameters());
		result.setRet(method.getRet());

		return null;
	}

	private String name;
	private List<Parameter> parameters;
	private Return ret;

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
}
