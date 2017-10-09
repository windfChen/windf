package com.windf.module.development.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Controler {
	// 基本信息
	private String name;
	private String urlPath;
	private String classPath;
	
	// 引用
	private Map<String, Service> serviceMap = new HashMap<String, Service>();
	private Map<String, UrlInfo> urlInfoMap = new HashMap<String, UrlInfo>();
	
	// 反向引用
	private Module module;
	
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
		Iterator<UrlInfo> urlInfoIterator = urlInfoMap.values().iterator();
		while (urlInfoIterator.hasNext()) {
			UrlInfo urlInfo = (UrlInfo) urlInfoIterator.next();
			urlInfo.setControler(this);
		}
		
		Iterator<Service> serviceIterator = serviceMap.values().iterator();
		while (serviceIterator.hasNext()) {
			Service service = (Service) serviceIterator.next();
			service.setControler(this);
		}
		
		initializationed = true;
	}
	
	/**
	 * 添加url信息
	 * @param urlInfo
	 */
	public void addUrlInfo(UrlInfo urlInfo) {
		urlInfoMap.put(urlInfo.getName(), urlInfo);
	}
	
	/**
	 * 获得url信息
	 * @param methodName
	 * @return
	 */
	public UrlInfo findUrlInfo(String methodName) {
		return urlInfoMap.get(methodName);
	}
	
	/**
	 * 获得urlInfo，用于持久化
	 * @return
	 */
	public List<UrlInfo> getUrlInfos() {
		return new ArrayList<UrlInfo>(urlInfoMap.values());
	}

	/**
	 * 持久化设置urlInfo信息
	 * @param urlInfos
	 */
	public void setUrlInfos(List<UrlInfo> urlInfos) {
		for (UrlInfo urlInfo : urlInfos) {
			urlInfoMap.put(urlInfo.getName(), urlInfo);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	public String getClassPath() {
		return classPath;
	}

	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}

	public Map<String, UrlInfo> getUrlInfoMap() {
		return urlInfoMap;
	}

	public void setUrlInfoMap(Map<String, UrlInfo> urlInfoMap) {
		this.urlInfoMap = urlInfoMap;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public Map<String, Service> getServiceMap() {
		return serviceMap;
	}

	public void setServiceMap(Map<String, Service> serviceMap) {
		this.serviceMap = serviceMap;
	}

}
