package com.windf.plugins.manage.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.windf.core.bean.Page;
import com.windf.core.exception.CodeException;
import com.windf.core.exception.DataAccessException;
import com.windf.core.exception.UserException;
import com.windf.core.frame.Moudle;
import com.windf.core.general.dao.ListDao;
import com.windf.core.general.dao.WritableDao;
import com.windf.core.spring.SpringUtil;
import com.windf.core.util.JSONUtil;
import com.windf.core.util.file.FileReadUtil;
import com.windf.plugins.manage.Constant;
import com.windf.plugins.manage.bean.GridConfig;
import com.windf.plugins.manage.service.ManageGirdService;

@Service
public class ManagerGirdiServiceImpl implements ManageGirdService{

	@Override
	public GridConfig getGridConfig(String code, String roleId, Map<String, Object> condition) throws UserException, CodeException {
		// TODO Auto-generated method stub
		
		/*
		 * 加载表格结构
		 */
		GridConfig gridConfig = this.loadGridConfigByCode(code);
		
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
	public Page<Map<String, Object>> list(String code, Map<String, Object> condition, Integer pageNo, Integer pageSize)
			throws UserException, CodeException, DataAccessException {
		
		GridConfig gridConfig = this.loadGridConfigByCode(code);
		ListDao listDao = (ListDao) SpringUtil.getBean(gridConfig.getDataSource());
		
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(Long.valueOf(pageNo), pageSize);
		
		page.setTotal(listDao.count(condition));
		page.setData(listDao.list(condition, page.getStartIndex(), page.getPageSize()));
		
		return page;
	}

	@Override
	public int save(String code, Object bean) throws Exception {
		GridConfig gridConfig = this.loadGridConfigByCode(code);
		WritableDao writableDao = (WritableDao) SpringUtil.getBean(gridConfig.getDataSource());
		
		return writableDao.insert(bean);
	}

	@Override
	public Object detail(String code, Serializable id) throws Exception {
		GridConfig gridConfig = this.loadGridConfigByCode(code);
		WritableDao writableDao = (WritableDao) SpringUtil.getBean(gridConfig.getDataSource());
		return writableDao.find(id);
	}

	@Override
	public int update(String code, Object bean) throws Exception {
		GridConfig gridConfig = this.loadGridConfigByCode(code);
		WritableDao writableDao = (WritableDao) SpringUtil.getBean(gridConfig.getDataSource());
		return writableDao.update(bean);
		
	}

	@Override
	public int delete(String code, List<? extends Serializable> id) throws Exception {
		GridConfig gridConfig = this.loadGridConfigByCode(code);
		WritableDao writableDao = (WritableDao) SpringUtil.getBean(gridConfig.getDataSource());
		return writableDao.delete(id);
	}

	/**
	 * 加载GridConfig
	 * @param code
	 * @return
	 * @throws CodeException 
	 */
	protected GridConfig loadGridConfigByCode(String code) throws CodeException {
		GridConfig result = null;
		try {
			Moudle module = Moudle.getCurrentMoudle(); 
			String gridConfigFilePath = module.getConfigFilePath() + Constant.MANAGE_JSON_CONFIG_PATH + code + ".json";
			String gridConfigJsonStr = FileReadUtil.readFileAsString(gridConfigFilePath);
			result = JSONUtil.pasrseJSONStr(gridConfigJsonStr, GridConfig.class);
		} catch (Throwable e) {
			throw new CodeException("json 获取错误");
		}
		
		return result;
	}
}
