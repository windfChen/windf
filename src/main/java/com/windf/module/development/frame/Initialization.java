package com.windf.module.development.frame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.windf.core.frame.Initializationable;
import com.windf.core.util.file.FileUtil;
import com.windf.core.util.reflect.Scanner;
import com.windf.core.util.reflect.ScannerHandler;
import com.windf.module.development.entity.Module;
import com.windf.module.development.entity.ModuleMaster;

public class Initialization implements Initializationable, ScannerHandler{

	private Map<String, Module> modules = new HashMap<String, Module>(); // 模块
	
	@Override
	public void init() {
		String classPath = FileUtil.getClassPath();
		Scanner scanner = new Scanner(classPath, this);
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
	public void handle(Scanner scanner) {
		String prefix = scanner.getPrefix();
		if ("class".equals(prefix)) { // 如果是java的解析

			/*
			 * 解析路径
			 */
			// TODO 现在是写死的，以后需要优化
			String moduleType = scanner.getCurrentRelativePathByIndex(3);
			String moduleCode = scanner.getCurrentRelativePathByIndex(4);
			
			/*
			 *  初始化模块 // TODO 先不处理插件
			 */
			Module currentModule = null;
			if ("module".equals(moduleType)) {
				currentModule = modules.get(moduleCode);
				if (currentModule == null) {
					currentModule = ModuleMaster.getInstance().loadModule(moduleCode);
					modules.put(moduleCode, currentModule);
				}
			}
		}
	}

}
