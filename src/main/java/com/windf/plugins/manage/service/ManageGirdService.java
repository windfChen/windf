package com.windf.plugins.manage.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.windf.core.bean.Page;
import com.windf.core.exception.CodeException;
import com.windf.core.exception.DataAccessException;
import com.windf.core.exception.UserException;
import com.windf.core.general.service.CrudService;
import com.windf.plugins.manage.bean.GridConfig;

/**
 * 管理端服务
 * 
 * @author chenyafeng
 *
 */
public interface ManageGirdService extends CrudService {
	/**
	 * 获得表格配置
	 * @param code
	 * @param roleId
	 * @param condition
	 * @return
	 * @throws UserException
	 * @throws CodeException 
	 */
	GridConfig getGridConfig(String code, String roleId, Map<String, Object> condition) throws Exception;

	/**
	 * 分页搜索
	 * @param condition
	 * @param pageNo
	 * @param PageSize
	 * @return
	 * @throws UserException
	 * @throws CodeException 
	 * @throws DataAccessException 
	 */
	Page<Map<String, Object>> list(Map<String, Object> condition, Integer pageNo, Integer PageSize) throws Exception;

	/**
	 * 添加
	 * @param code
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	int save(Object bean)  throws Exception;

	/**
	 * 详情
	 * @param code
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Object detail(Serializable id)  throws Exception;
	
	/**
	 * 修改
	 * @param code
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	int update(Object bean)  throws Exception;

	/**
	 * 删除
	 * @param code
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	int delete(List<? extends Serializable> id)  throws Exception;
}
