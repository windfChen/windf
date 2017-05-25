package com.windf.module.development.pojo;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.windf.core.constant.ModuleConstant;
import com.windf.core.exception.EntityException;
import com.windf.core.file.FileUtil;
import com.windf.core.file.XmlFileUtil;

public class Module {
	/**
	 * 加载模块
	 * @param code 模块的code
	 * @return
	 * @throws EntityException
	 */
	public static Module loadModule(String code) throws EntityException {
		String exampleDescriptPath = ModuleConstant.DEFAULT_MODULE_DESCRIPT_PATH + File.separator + 
				code + File.separator  + ModuleConstant.DEFAULT_MODULE_DESCRIPT_FILE_NAME;
		File exampleDescriptFile = FileUtil.getWebappFile(exampleDescriptPath);
		if (!exampleDescriptFile.exists()) {
			try {
				throw new EntityException("模板模块：[" + code + "]的配置文件不存在");
			} catch (EntityException e) {
				e.printStackTrace();
			}
		}
		
		/*
		 *	 解析模板，获得模板配置
		 */		
		Module module = XmlFileUtil.readXml2Object(exampleDescriptFile, Module.class);
		
		return module;
	}
	
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
