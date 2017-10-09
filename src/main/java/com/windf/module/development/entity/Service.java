package com.windf.module.development.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Service {

	private String serviceName;
	private String path;

	private Map<String, ServiceMethod> methodMap = new HashMap<String, ServiceMethod>();

	private Module module;
	private Controler controler;
	
	// 标记变量
	private boolean initializationed = false;
	
	public void init() {
		/*
		 * 只初始化一次
		 */
		if (initializationed) {
			return;
		}
		
		/*
		 *  初始化反向调用
		 */
		Iterator<ServiceMethod> serviceMethodIterator = methodMap.values().iterator();
		while (serviceMethodIterator.hasNext()) {
			ServiceMethod serviceMethod = (ServiceMethod) serviceMethodIterator.next();
			serviceMethod.setService(this);
		}
		
		initializationed = true;
	}

	/**
	 * 根据方法名称获得方法
	 * 
	 * @param name
	 * @return
	 */
	public ServiceMethod getServiceMethodByName(String name) {
		ServiceMethod result = null;

		result = methodMap.get(name);

		return result;
	}

	/**
	 * 添加Method
	 * 
	 * @param serviceMethod
	 */
	public void addServiceMethod(ServiceMethod serviceMethod) {
		methodMap.put(serviceMethod.getName(), serviceMethod);
	}

	public List<ServiceMethod> getMethods() {
		return new ArrayList<ServiceMethod>(methodMap.values());
	}

	public void setMethods(List<ServiceMethod> methods) {
		for (ServiceMethod serviceMethod : methods) {
			methodMap.put(serviceMethod.getName(), serviceMethod);
		}
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

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}
	
	public Controler getControler() {
		return controler;
	}

	public void setControler(Controler controler) {
		this.controler = controler;
	}

}
