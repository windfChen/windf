package com.windf.module.development.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.windf.core.bean.Page;
import com.windf.module.development.entity.Module;
import com.windf.module.development.entity.ModuleMaster;
import com.windf.module.development.service.ModuleService;
import com.windf.plugins.manage.bean.GridConfig;

@Service
public class ModuleServiceImpl  implements ModuleService {

	@Override
	public GridConfig getGridConfig(String code, String roleId, Map<String, Object> condition) {
		GridConfig gridConfig = GridConfig.loadGridConfigByCode(code, condition);
		return gridConfig;
	}

	@Override
	public Page<Map<String, Object>> list(Map<String, Object> condition, Integer pageNo, Integer PageSize) {
		
		ModuleMaster moduleMaster = ModuleMaster.getInstance();
		
		Page<Module> page = new Page<Module>(Long.valueOf(pageNo) , PageSize);
		if (moduleMaster.getModules().size() > 0) {
			page.setTotal(Long.valueOf(moduleMaster.getModules().size()));
			page.setData(moduleMaster.getModules().subList(page.getStartIndex().intValue(), page.getEndIndex().intValue()));
		}
		
		return null;
	}

	@Override
	public int save(Object entity) throws Exception {
		Module module = (Module) entity;
		
		module.write();
		
		return 0;
	}

	@Override
	public Object detail(Serializable id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Object bean) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(List<? extends Serializable> id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
