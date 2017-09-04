package com.windf.plugins.manage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.windf.core.exception.CodeException;
import com.windf.core.exception.UserException;
import com.windf.core.file.FileReadUtil;
import com.windf.core.util.JSONUtil;
import com.windf.core.util.Page;
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

	@Override
	public Page<Map<String, Object>> list(String code, Map<String, Object> condition, Integer pageNo, Integer PageSize)
			throws UserException {
		Page<Map<String, Object>> page = new Page<Map<String, Object>>();
		page.setTotal(5L);
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("id", "1");
		m.put("name", "华为");
		m.put("age", 9);
		data.add(m);
		m = new HashMap<String, Object>();
		m.put("id", "2");
		m.put("name", "三心");
		m.put("age", 10);
		data.add(m);
		m = new HashMap<String, Object>();
		m.put("id", "3");
		m.put("name", "八两");
		m.put("age", 6);
		data.add(m);
		m = new HashMap<String, Object>();
		m.put("id", "4");
		m.put("name", "六色");
		m.put("age", 19);
		data.add(m);
		m = new HashMap<String, Object>();
		m.put("id", "5");
		m.put("name", "九九");
		m.put("age", 11);
		data.add(m);
		page.setData(data);
				
		return page;
	}
	
	/**
	 * 加载GridConfig
	 * @param code
	 * @return
	 * @throws CodeException 
	 */
	private GridConfig loadGridConfigByCode(String code) throws CodeException {
		GridConfig result = null;
		try {
			String gridConfigFilePath = Constant.MANAGE_JSON_CONFIG_PATH + code + ".json";
			String gridConfigJsonStr = FileReadUtil.readFileAsString(gridConfigFilePath);
			result = JSONUtil.pasrseJSONStr(gridConfigJsonStr, GridConfig.class);
		} catch (Throwable e) {
			throw new CodeException("json 获取错误");
		}
		
		return result;
	}

}