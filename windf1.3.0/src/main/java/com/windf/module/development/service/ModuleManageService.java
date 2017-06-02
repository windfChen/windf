package com.windf.module.development.service;

import com.windf.core.exception.EntityException;
import com.windf.core.util.Page;
import com.windf.module.development.pojo.Module;
import com.windf.module.development.pojo.ModuleDto;
import com.windf.module.development.pojo.ModuleSearch;

public interface ModuleManageService {
	/**
	 * 创建模块
	 * @return 
	 * @throws EntityException 
	 */
	Module createModule(ModuleDto moduleDto) throws EntityException;

	/**
	 * 获得模块
	 * @param code
	 * @return
	 * @throws EntityException
	 */
	Module getModule(String code) throws EntityException;

	/**
	 * 查询模块
	 * @param moduleSearch	 搜素条件
	 * @param pageNum	 页号
	 * @param pageSize	每页大小
	 * @return
	 */
	Page<Module> listAllModule(ModuleSearch moduleSearch, Integer pageNum, Integer pageSize);
}
