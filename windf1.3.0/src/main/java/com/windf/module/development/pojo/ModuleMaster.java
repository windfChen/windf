package com.windf.module.development.pojo;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.windf.core.exception.EntityException;
import com.windf.core.file.FileUtil;
import com.windf.module.development.file.XmlFileUtil;

/**
 * Module Master
 * @author chenyafeng
 *
 */
public class ModuleMaster {
	
	private static final String DEFAULT_MASTER_CONFIG_FILE = "/WEB-INF/module/master.xml";
	
	private static ModuleMaster moduleMaster = null;
	
	public static ModuleMaster getInstance() throws EntityException {
		
		if (moduleMaster == null) {
			File moduleMasterFile = FileUtil.getWebappFile(DEFAULT_MASTER_CONFIG_FILE);
			
			moduleMaster = XmlFileUtil.readXml2Object(moduleMasterFile, ModuleMaster.class);
		}
		
		return moduleMaster;
	}
	
	private String moduleConfigPath;
	private Map<String, String> sourcePath;
	private Map<String, String> classPath;
	private Map<String, String> webPath;
	
	public ModuleMaster() {
		
	}

	public static ModuleMaster getModuleMaster() {
		return moduleMaster;
	}

	public static void setModuleMaster(ModuleMaster moduleMaster) {
		ModuleMaster.moduleMaster = moduleMaster;
	}

	public String getModuleConfigPath() {
		return moduleConfigPath;
	}

	public void setModuleConfigPath(String moduleConfigPath) {
		this.moduleConfigPath = moduleConfigPath;
	}

	public Map<String, String> getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(Map<String, String> sourcePath) {
		this.sourcePath = sourcePath;
	}

	public Map<String, String> getClassPath() {
		return classPath;
	}

	public void setClassPath(Map<String, String> classPath) {
		this.classPath = classPath;
	}

	public Map<String, String> getWebPath() {
		return webPath;
	}

	public void setWebPath(Map<String, String> webPath) {
		this.webPath = webPath;
	}
	
}
