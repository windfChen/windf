package com.windf.module.development.modle.controler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.windf.module.development.pojo.Module;

public class Controler {
	private String name;
	private String urlPath;
	private String classPath;
	
	private Map<String, UrlInfo> urlInfoMap = new HashMap<String, UrlInfo>();
	
	private Module module;
	
	/**
	 * 添加url信息
	 * @param urlInfo
	 */
	public void addUrlInfo(UrlInfo urlInfo) {
		urlInfoMap.put(urlInfo.getMethodName(), urlInfo);
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
			urlInfoMap.put(urlInfo.getMethodName(), urlInfo);
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

}
