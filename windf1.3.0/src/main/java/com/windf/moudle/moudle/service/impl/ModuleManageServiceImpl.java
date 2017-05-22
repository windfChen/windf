package com.windf.moudle.moudle.service.impl;

import com.windf.core.file.FileUtil;
import com.windf.moudle.moudle.Constant;
import com.windf.moudle.moudle.pojo.Module;

public class ModuleManageServiceImpl {
	public void create(Module module) {
		String exampleConfigPath = Constant.DEFAULT_MODULE_CONFIG_PATH +  Constant.DEFAULT_EXAMPLE_PATH;
		String newModuleConfigPath = Constant.DEFAULT_MODULE_CONFIG_PATH +  Constant.DEFAULT_EXAMPLE_PATH;
		FileUtil.copyFolder(exampleConfigPath, newModuleConfigPath);
	}
}
