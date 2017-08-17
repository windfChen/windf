package com.windf.module.development.pojo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.windf.core.exception.UserException;
import com.windf.core.file.FileUtil;
import com.windf.module.development.file.XmlFileUtil;

/**
 * Module Master
 * @author chenyafeng
 *
 */
public class ModuleMaster implements Cloneable {
	
	private static final String DEFAULT_MASTER_CONFIG_FILE = "/WEB-INF/module/master.xml";
	
	private static ModuleMaster moduleMaster = null;
	
	public static ModuleMaster getInstance() throws UserException {
		
		if (moduleMaster == null) {
			File moduleMasterFile = FileUtil.getWebappFile(DEFAULT_MASTER_CONFIG_FILE);
			
			moduleMaster = XmlFileUtil.readXml2Object(moduleMasterFile, ModuleMaster.class);
			
			moduleMaster.loadAllModule();
		}
		
		return moduleMaster;
	}
	
	private String moduleConfigPath;
	private Map<String, String> sourcePath;
	private Map<String, String> classPath;
	private Map<String, String> webPath;
	private List<String> moduleCodes = new ArrayList<String>();
	
	private Map<String, Module> modules = new HashMap<String, Module>();
	
	public ModuleMaster() {
		
	}
	
	public void addModule(Module module) {
		/*
		 * 添加code
		 */
		if (!moduleCodes.contains(module.getCode())) {
			moduleCodes.add(module.getCode());
		}
		
		/*
		 * 添加module 
		 */
		modules.put(module.getCode(), module);
	}
	
	/**
	 * 根据code获得模块
	 * @param code
	 * @return
	 * @throws UserException 
	 */
	public Module findModuleByCode(String code) throws UserException {
		Module result = modules.get(code);
		return result;
	}
	
	/**
	 * 获得所有模块
	 * @return
	 */
	public List<Module> listAllModules() {
		return new ArrayList<Module>(modules.values());
	}
	
	/**
	 * 把类的内容写入master.xml
	 */
	public void write() {
		File file = new File(FileUtil.getWebappPath() + DEFAULT_MASTER_CONFIG_FILE);
		XmlFileUtil.writeObject2Xml(this, file);
	}
	
	/**
	 * 加载所有模块
	 * @throws UserException
	 */
	protected void loadAllModule() throws UserException {
		for (int i = 0; i < moduleCodes.size(); i++) {
			String code = moduleCodes.get(i);
			
			Module module = Module.loadModule(code);
			modules.put(code, module);
		}
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

	public static void setModuleMaster(ModuleMaster moduleMaster) {
		ModuleMaster.moduleMaster = moduleMaster;
	}

	public List<String> getModuleCodes() {
		return moduleCodes;
	}

	public void setModuleCodes(List<String> moduleCodes) {
		this.moduleCodes = moduleCodes;
	}
	
}
