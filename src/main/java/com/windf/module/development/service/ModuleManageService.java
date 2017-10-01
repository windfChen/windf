package com.windf.module.development.service;

import com.windf.core.bean.Page;
import com.windf.core.exception.UserException;
import com.windf.module.development.entity.Module;
import com.windf.module.development.entity.dto.ModuleDto;
import com.windf.module.development.entity.dto.ModuleSearch;

public interface ModuleManageService {
	/**
	 * 创建模块
	 * 
	 * @return
	 * @throws UserException
	 */
	Module createModule(ModuleDto moduleDto) throws UserException;

	/**
	 * 修改模块
	 * 
	 * @param moduleDto
	 * @throws UserException
	 */
	Module modifyModule(ModuleDto moduleDto) throws UserException;

	/**
	 * 根据code获得模块
	 * 
	 * @param code
	 * @return
	 * @throws UserException
	 */
	Module getModuleByCode(String code) throws UserException;

	/**
	 * 查询所有模块
	 * 
	 * @param moduleSearch
	 *            搜素条件
	 * @param pageNum
	 *            页号
	 * @param pageSize
	 *            每页大小
	 * @return
	 */
	Page<Module> listAllModule(ModuleSearch moduleSearch, Integer pageNum, Integer pageSize);

	/**
	 * 根据路径获得模块
	 * @param basePath
	 * @return
	 */
	Module getModuleByPath(String basePath);

}
