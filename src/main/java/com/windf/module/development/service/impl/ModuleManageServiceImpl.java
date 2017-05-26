package com.windf.module.development.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.windf.core.exception.EntityException;
import com.windf.module.development.Constant;
import com.windf.module.development.pojo.Module;
import com.windf.module.development.pojo.ModuleDto;
import com.windf.module.development.service.ModuleManageService;

@Service
public class ModuleManageServiceImpl  implements ModuleManageService {

	@Override
	public void createModule(ModuleDto moduleDto) throws EntityException {
		/*
		 * 验证参数
		 */
		if (moduleDto == null || StringUtils.isEmpty(moduleDto.getCode())) {
			throw new EntityException("参数错误");
		}
		
		/*
		 * 验证前置条件
		 */
		if (getModule(moduleDto.getCode()) != null) {
			throw new EntityException("模块已存在");
		}

		/*
		 * 读取模板模块
		 */
		String templateModuleCode = Constant.DEFAULT_EXAMPLE_PATH;
		Module exampleModule = Module.loadModule(templateModuleCode);
		
		
		/*
		 * 复制创建新模块
		 */
		Module newModule = exampleModule.clone(moduleDto.getCode());
		
	}

	@Override
	public Module getModule(String code) throws EntityException {
		Module result = null;
		
		try {
			result = Module.loadModule(code);
		} catch (EntityException e) {
		}
		
		return result;
	}


}
