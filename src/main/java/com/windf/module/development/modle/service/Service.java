package com.windf.module.development.modle.service;

import java.util.ArrayList;
import java.util.List;

import com.windf.module.development.pojo.Module;

public class Service {

	private String serviceName;
	private String path;
	private List<ServiceMethod> methods = new ArrayList<ServiceMethod>();
	
	/**
	 * 根据方法名称获得方法
	 * @param name
	 * @return
	 */
	public ServiceMethod getServiceMethodByName(String name) {
		ServiceMethod result = null;
		
		for (ServiceMethod method : methods) {
			if (method.getName().equals(name)) {
				result = method;
			}
		}
		
		return result;
	}
	
	/**
	 * 添加Method
	 * @param ServiceMethod
	 */
	public void addServiceMethod(ServiceMethod ServiceMethod) {
		// TODO 验证方法是否存在
		methods.add(ServiceMethod);
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<ServiceMethod> getMethods() {
		return methods;
	}

	public void setMethods(List<ServiceMethod> methods) {
		this.methods = methods;
	}

}
