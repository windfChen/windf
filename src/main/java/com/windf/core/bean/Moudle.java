package com.windf.core.bean;

import com.windf.core.util.file.FileUtil;

public class Moudle {
	
	private static ThreadLocal<Moudle> currentMoudle = new ThreadLocal<Moudle>();
	
	public static Moudle setCurrentMoudle(Object obj) {
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
		
		Moudle result = null;
		result = new Moudle(code);
		currentMoudle.set(result);
		return result;
	}
	
	/**
	 * 获取当前线程的模块
	 * @return
	 */
	public static Moudle getCurrentMoudle() {
		return currentMoudle.get();
	}
	
	private String code;
	// TODO 设置模块basePath
	
	/**
	 * 获得模块的配置文件路径
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
	
	private Moudle(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
