package com.windf.module.module.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.windf.core.exception.EntityException;
import com.windf.core.file.FileUtil;
import com.windf.module.module.Constant;
import com.windf.module.module.pojo.ModuleDto;
import com.windf.module.module.service.ModuleManageService;

@Service
public class ModuleManageServiceImpl  implements ModuleManageService {

	@Override
	public void createModule(ModuleDto moduleDto) throws EntityException {
		/*
		 * 验证参数
		 */
		if (moduleDto == null || StringUtils.isEmpty(moduleDto.getName())) {
			throw new EntityException("参数错误");
		}

		/*
		 * 复制配置文件
		 */
		String exampleConfigPath = Constant.DEFAULT_MODULE_CLASS_PATH +  Constant.DEFAULT_EXAMPLE_PATH;
		String newModuleConfigPath = Constant.DEFAULT_MODULE_CLASS_PATH + moduleDto.getName();
		FileUtil.copyFolder(exampleConfigPath, newModuleConfigPath);
		
	}
}
