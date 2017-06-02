package com.windf.module.development.pojo;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.windf.core.exception.EntityException;
import com.windf.core.file.FileUtil;
import com.windf.core.util.reflect.ReflectUtil;
import com.windf.module.development.file.XmlFileUtil;

/**
 * Module Master
 * @author chenyafeng
 *
 */
public class ModuleMaster implements Cloneable {
	
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
	private List<Module> modules;
	
	public ModuleMaster() {
		
	}
	
	public Module addModule(Module module) {
		if (modules == null) {
			modules = new ArrayList<Module>();
		}
		
		/*
		 * 遍历是否已经存在，存在的要替换
		 */
		int index = -1;
		for (int i = 0; i < modules.size(); i++) {
			Module m = modules.get(i);
			if (m.getCode().equals(module.getCode())) {
				index = i;
			}
		}
		if (index == -1) {
			modules.add(module);
		} else {
			modules.remove(index);
			modules.add(index, module);
		}
		return module;
	}
	
	public void write() {
		/*
		 * 只保留需要持久化的module属性
		 */
		ModuleMaster writeModuleMaster = null;
		try {
			writeModuleMaster = (ModuleMaster) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		if (writeModuleMaster != null && writeModuleMaster.getModules() != null) {
			List<Module> writeModules = new ArrayList<Module>();
			for (int i = 0; i < writeModuleMaster.getModules().size(); i++) {
				Module module = writeModuleMaster.getModules().get(i);
				Module newModule = (Module) ReflectUtil.cloneOnlyBaseType(module);
				writeModules.add(newModule);
			}
			writeModuleMaster.setModules(writeModules);
		}
		
		File file = new File(FileUtil.getWebappPath() + DEFAULT_MASTER_CONFIG_FILE);
		XmlFileUtil.writeObject2Xml(writeModuleMaster, file);
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

	public static ModuleMaster getModuleMaster() {
		return moduleMaster;
	}

	public static void setModuleMaster(ModuleMaster moduleMaster) {
		ModuleMaster.moduleMaster = moduleMaster;
	}

	public List<Module> getModules() {
		return modules;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
	}
	
}
