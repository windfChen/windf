package com.windf.module.development.modle.controler;

import java.util.List;

public class Controler {
	private String name;
	private String urlPath;
	private String classPath;
	private List<UrlInfo> urlInfos;

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

	public List<UrlInfo> getUrlInfos() {
		return urlInfos;
	}

	public void setUrlInfos(List<UrlInfo> urlInfos) {
		this.urlInfos = urlInfos;
	}

}
