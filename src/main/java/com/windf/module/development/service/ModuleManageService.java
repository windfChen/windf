package com.windf.module.development.service;

import com.windf.core.exception.UserException;
import com.windf.core.util.Page;
import com.windf.module.development.pojo.Module;
import com.windf.module.development.pojo.ModuleDto;
import com.windf.module.development.pojo.ModuleSearch;

public interface ModuleManageService {
	/**
	 * 创建模块
	 * @return 
	 * @throws UserException 
	 */
	Module createModule(ModuleDto moduleDto) throws UserException;

	/**
	 * 修改模块
	 * @param moduleDto
	 * @throws UserException
	 */
	Module modifyModule(ModuleDto moduleDto) throws UserException;

	/**
	 * 获得模块
	 * @param code
	 * @return
	 * @throws UserException
	 */
	Module getModule(String code) throws UserException;

	/**
	 * 查询模块
	 * @param moduleSearch	 搜素条件
	 * @param pageNum	 页号
	 * @param pageSize	每页大小
	 * @return
	 */
	Page<Module> listAllModule(ModuleSearch moduleSearch, Integer pageNum, Integer pageSize);
	
	/**
	 * 根据basePath获取模块
	 * @param basePath
	 * @return
	 */
	Module getModuleByPath(String basePath);
}
