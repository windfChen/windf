package com.windf.core.bean;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.windf.core.frame.Filter;
import com.windf.core.frame.Initializationable;
import com.windf.core.frame.Session;
import com.windf.core.util.file.FileUtil;
import com.windf.module.development.Constant;

public class Module {

	private static ThreadLocal<Module> currentMoudle = new ThreadLocal<Module>();

	public static Module setCurrentMoudle(Object obj) {
		if (obj == null) {
			return null;
		}

		/*
		 * 根据类的路径获取模块code
		 */
		String code = null;
		String className = obj.getClass().getName();
		code = className.substring("com.windf.moudle.".length());
		code = code.substring(0, code.indexOf("."));

		if (code == null) {
			return null;
		}

		Module result = null;
		result = new Module(code);
		currentMoudle.set(result);
		return result;
	}

	/**
	 * 获得模块配置文件
	 * 
	 * @param code
	 * @return
	 */
	public static File getMoudleConfigFileByCode(String code) {
		String configFilePath = FileUtil.getConfigPath() + Constant.DEFAULT_MODULE_DESCRIPT_PATH + File.separator + code + ".xml";
		return FileUtil.getFile(configFilePath);
	}

	/**
	 * 获取当前线程的模块
	 * 
	 * @return
	 */
	public static Module getCurrentMoudle() {
		return currentMoudle.get();
	}

	private String code;
	// TODO 设置模块basePath

	private List<Initializationable> initializationables;
	private List<Session> sessions;
	private List<Filter> filters;
	
	private Map<String, String> dependent;

	/**
	 * 获得模块的配置文件路径
	 * 
	 * @param moduleCode
	 * @param yourPath
	 * @return
	 */
	public String getConfigFilePath() {
		String configPath = FileUtil.getConfigPath();
		String result = configPath + "/module/" + this.getCode();
		result = result.replace("//", "/");
		return result;
	}
	
	public Module(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Initializationable> getInitializationables() {
		return initializationables;
	}

	public void setInitializationables(List<Initializationable> initializationables) {
		this.initializationables = initializationables;
	}

	public List<Session> getSessions() {
		return sessions;
	}

	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}

	public List<Filter> getFilters() {
		return filters;
	}

	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}

	public Map<String, String> getDependent() {
		return dependent;
	}

	public void setDependent(Map<String, String> dependent) {
		this.dependent = dependent;
	}

}
