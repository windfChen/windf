package com.windf.module.development.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.windf.module.development.util.file.XmlFileUtil;

/**
 * Module Master
 * 
 * @author chenyafeng
 *
 */
/**
 * @author chenyafeng
 *
 */
public class ModuleMaster implements Cloneable {

	private static ModuleMaster moduleMaster = new ModuleMaster();

	public static ModuleMaster getInstance() {
		return moduleMaster;
	}

	private List<Module> modules = new ArrayList<Module>();
	
	/**
	 * 加载模块
	 * @param moduleCode
	 * @return
	 * @throws UserException
	 */
	public Module loadModule(String moduleCode) {
		File exampleDescriptFile = com.windf.core.bean.Module.getMoudleConfigFileByCode(moduleCode);
		if (!exampleDescriptFile.exists()) {
			// throw new CodeException("模板模块：[" + moduleCode + "]的配置文件不存在");
			return null;	// TODO 先创建，有了格式再复制到各个模块下修改
		}

		Module module = XmlFileUtil.readXml2Object(exampleDescriptFile, Module.class);
		//module.init(); // TODO 好像有用

		return module;
	}

	public Module findModuleByCode(String moduleCode) {
		return null;
	}
	
	public List<Module> getModules() {
		return modules;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
	}

}
