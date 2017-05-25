package com.windf.module.development.pojo;

import java.util.List;
import java.util.Map;

public class Module {
	private String code;
	private String name;
	private String basePath;
	private String info;
	private Map<String, String> path;
	private List<String>	dependent;
	
	// TODO servicves:List<Service>  urls:List<URL>
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBasePath() {
		return basePath;
	}
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Map<String, String> getPath() {
		return path;
	}
	public void setPath(Map<String, String> path) {
		this.path = path;
	}
	public List<String> getDependent() {
		return dependent;
	}
	public void setDependent(List<String> dependent) {
		this.dependent = dependent;
	}
	
	
}
