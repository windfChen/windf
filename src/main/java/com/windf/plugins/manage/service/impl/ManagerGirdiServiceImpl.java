package com.windf.plugins.manage.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.windf.core.exception.CodeException;
import com.windf.core.exception.DataAccessException;
import com.windf.core.exception.UserException;
import com.windf.core.file.FileReadUtil;
import com.windf.core.spring.SpringUtil;
import com.windf.core.util.JSONUtil;
import com.windf.core.util.ModuleUtil;
import com.windf.core.util.Page;
import com.windf.plugins.database.ListDao;
import com.windf.plugins.manage.Constant;
import com.windf.plugins.manage.bean.GridConfig;
import com.windf.plugins.manage.service.ManageGirdService;

@Service
public class ManagerGirdiServiceImpl implements ManageGirdService{

	@Override
	public GridConfig getGridConfig(String moduleCode, String code, String roleId, Map<String, Object> condition) throws UserException, CodeException {
		// TODO Auto-generated method stub
		
		/*
		 * 加载表格结构
		 */
		GridConfig gridConfig = this.loadGridConfigByCode(moduleCode, code);
		
		/*
		 * 根据权限，过滤表格功能
		 */
		
		/*
		 * 替代参数
		 */
		
		return gridConfig;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<Map<String, Object>> list(String moduleCode, String code, Map<String, Object> condition, Integer pageNo, Integer pageSize)
			throws UserException, CodeException, DataAccessException {
		
		GridConfig gridConfig = this.loadGridConfigByCode(moduleCode, code);
		ListDao listDao = (ListDao) SpringUtil.getBean(gridConfig.getDataSource());
		
		Page<Map<String, Object>> page = new Page<Map<String, Object>>();
		
		page.setTotal(listDao.count(condition));
		page.setData(listDao.list(condition, pageNo, pageSize));
		
		return page;
	}
	
	/**
	 * 加载GridConfig
	 * @param code
	 * @return
	 * @throws CodeException 
	 */
	private GridConfig loadGridConfigByCode(String moduleCode, String code) throws CodeException {
		GridConfig result = null;
		try {
			String gridConfigFilePath = ModuleUtil.getConfigFilePath(moduleCode, Constant.MANAGE_JSON_CONFIG_PATH + code + ".json");
			String gridConfigJsonStr = FileReadUtil.readFileAsString(gridConfigFilePath);
			result = JSONUtil.pasrseJSONStr(gridConfigJsonStr, GridConfig.class);
		} catch (Throwable e) {
			throw new CodeException("json 获取错误");
		}
		
		return result;
	}

}
