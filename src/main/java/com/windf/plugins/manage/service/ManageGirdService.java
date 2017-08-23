package com.windf.plugins.manage.service;

import java.util.Map;

import com.windf.core.exception.CodeException;
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
	 * @param roleId
	 * @param condition
	 * @return
	 * @throws UserException
	 * @throws CodeException 
	 */
	GridConfig getGridConfig(String code, String roleId, Map<String, Object> condition) throws UserException, CodeException;

	/**
	 * 分页搜索
	 * @param condition
	 * @param pageNo
	 * @param PageSize
	 * @return
	 * @throws UserException
	 */
	Page<Map<String, Object>> list(String code, Map<String, Object> condition, Integer pageNo, Integer PageSize) throws UserException;
}
