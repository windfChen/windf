package com.windf.plugins.manage.service;

import java.util.Map;

import com.windf.core.exception.CodeException;
import com.windf.core.exception.DataAccessException;
import com.windf.core.exception.UserException;
import com.windf.core.util.Page;
import com.windf.plugins.manage.bean.GridConfig;

/**
 * 管理端服务
 * 
 * @author chenyafeng
 *
 */
public interface ManageGirdService {
	/**
	 * 获得表格配置
	 * @param moduleCode
	 * @param code
	 * @param roleId
	 * @param condition
	 * @return
	 * @throws UserException
	 * @throws CodeException 
	 */
	GridConfig getGridConfig(String moduleCode, String code, String roleId, Map<String, Object> condition) throws UserException, CodeException;

	/**
	 * 分页搜索
	 * @param moduleCode
	 * @param code
	 * @param condition
	 * @param pageNo
	 * @param PageSize
	 * @return
	 * @throws UserException
	 * @throws CodeException 
	 * @throws DataAccessException 
	 */
	Page<Map<String, Object>> list(String moduleCode, String code, Map<String, Object> condition, Integer pageNo, Integer PageSize) throws UserException, CodeException, DataAccessException;
}
