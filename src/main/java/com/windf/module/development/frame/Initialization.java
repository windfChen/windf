package com.windf.module.development.frame;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.sun.jndi.url.corbaname.corbanameURLContextFactory;
import com.windf.core.frame.Initializationable;
import com.windf.core.util.file.ModuleFile;
import com.windf.core.util.reflect.Scanner;
import com.windf.core.util.reflect.ScannerHandler;
import com.windf.module.development.entity.Entity;
import com.windf.module.development.entity.Field;
import com.windf.module.development.entity.Module;
import com.windf.module.development.entity.ModuleMaster;
import com.windf.module.development.modle.java.Attribute;
import com.windf.module.development.modle.java.JavaCoder;
import com.windf.module.development.util.file.SourceFileUtil;

public class Initialization implements Initializationable, ScannerHandler{

	private Map<String, Module> modules = new HashMap<String, Module>(); // 模块
	
	@Override
	public void init() {
		Scanner scanner = new Scanner(SourceFileUtil.getJavaPath(), this);
		scanner.run();
		
		// TODO 待排序
		List<Module> moduleList = new ArrayList<Module>(modules.values());
		
		ModuleMaster.getInstance().setModules(moduleList);
	}

	@Override
	public int getOrder() {
		return NORMAL;
	}

	@Override
	public void handle(File file) {
		ModuleFile moduleFile = new ModuleFile(SourceFileUtil.getJavaPath(), file);
		if (moduleFile.verifyPath()) {
			if ("java".equals(moduleFile.getPrefix())) { // 如果是java的解析
				
				/*
				 *  初始化模块 // TODO 先不处理插件
				 */
				Module currentModule = null;
				if ("module".equals(moduleFile.getModuleType())) {
					currentModule = modules.get(moduleFile.getModuleCode());
					if (currentModule == null) {
						currentModule = ModuleMaster.getInstance().loadModule(moduleFile.getModuleCode());
						modules.put(moduleFile.getModuleCode(), currentModule);
					}
				}
				
				// 处理各个类
				if ("entity".equals(moduleFile.getPackageName())) {
					JavaCoder javaCode = new JavaCoder(moduleFile.getRelativePath(), moduleFile.getFileName());
					Entity e = new Entity();
					e.setName(javaCode.getClassName());
					e.setId(moduleFile.getClassName());
					
					for (int i = 0; i < javaCode.getAllAttributes().size(); i++) {
						Attribute b = javaCode.getAllAttributes().get(i);
						Field f = new Field();
						f.setName(b.getName());
						f.setId(moduleFile.getClassName() + "." + b.getName());

						e.getFields().add(f);
					}
					
					currentModule.getEntitys().add(e);
				}
				
				
			}
		}
		
	}

}
