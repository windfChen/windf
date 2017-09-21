package com.windf.plugins.manage.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.windf.core.bean.Page;
import com.windf.core.exception.CodeException;
import com.windf.core.exception.DataAccessException;
import com.windf.core.exception.UserException;
import com.windf.core.general.dao.GridDao;
import com.windf.core.general.dao.WritableDao;
import com.windf.plugins.manage.bean.GridConfig;
import com.windf.plugins.manage.service.ManageGirdService;

public abstract class ManagerGirdiServiceImpl implements ManageGirdService{
	
	/**
	 * 获取gridDao
	 * @return
	 */
	public abstract GridDao getGridDao() ;

	@Override
	public GridConfig getGridConfig(String code, String roleId, Map<String, Object> condition) throws UserException, CodeException {
		// TODO Auto-generated method stub
		
		/*
		 * 加载表格结构
		 */
		GridConfig gridConfig = GridConfig.loadGridConfigByCode(code, condition);
		
		/*
		 * 根据权限，过滤表格功能
		 */
		
		return gridConfig;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<Map<String, Object>> list(String code, Map<String, Object> condition, Integer pageNo, Integer pageSize)
			throws UserException, CodeException, DataAccessException {
		
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(Long.valueOf(pageNo), pageSize);
		
		page.setTotal(this.getGridDao().count(condition));
		page.setData(this.getGridDao().list(condition, page.getStartIndex(), page.getPageSize()));
		
		return page;
	}

	@Override
	public int save(String code, Object bean) throws Exception {
		WritableDao writableDao = this.getGridDao();
		return writableDao.insert(bean);
	}

	@Override
	public Object detail(String code, Serializable id) throws Exception {
		WritableDao writableDao = this.getGridDao();
		return writableDao.find(id);
	}

	@Override
	public int update(String code, Object bean) throws Exception {
		WritableDao writableDao = this.getGridDao();
		return writableDao.update(bean);
		
	}

	@Override
	public int delete(String code, List<? extends Serializable> id) throws Exception {
		WritableDao writableDao = this.getGridDao();
		return writableDao.delete(id);
	}

}
