package com.windf.module.development.service;

import com.windf.core.exception.EntityException;
import com.windf.module.development.pojo.Module;
import com.windf.module.development.pojo.ModuleDto;

public interface ModuleManageService {
	/**
	 * 创建模块
	 * @throws EntityException 
	 */
	void createModule(ModuleDto moduleDto) throws EntityException;

	/**
	 * 获得模块
	 * @param code
	 * @return
	 * @throws EntityException
	 */
	Module getModule(String code) throws EntityException;
}
