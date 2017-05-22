package com.windf.module.module.service;

import com.windf.core.exception.EntityException;
import com.windf.module.module.pojo.ModuleDto;

public interface ModuleManageService {
	/**
	 * 创建模块
	 * @throws EntityException 
	 */
	void createModule(ModuleDto moduleDto) throws EntityException;

}
