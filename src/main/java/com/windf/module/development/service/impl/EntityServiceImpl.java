package com.windf.module.development.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.windf.core.bean.Page;
import com.windf.core.exception.ParameterException;
import com.windf.module.development.entity.Entity;
import com.windf.module.development.entity.Module;
import com.windf.module.development.entity.ModuleMaster;
import com.windf.module.development.service.EntityService;

@Service
public class EntityServiceImpl extends BaseManageGridServiceImpl implements EntityService{

	@Override
	public Page<? extends Object> list(Map<String, Object> condition, Integer pageNo, Integer pageSize) throws Exception {
		
		ModuleMaster moduleMaster = ModuleMaster.getInstance();
		Module module = moduleMaster.findModuleByCode((String) condition.get("moduleCode"));
		if (module == null) {
			throw new ParameterException("模块不存在！");
		}
		
		Page<Entity> page = new Page<Entity>(Long.valueOf(pageNo) , pageSize);
		if (module.getEntitys().size() > 0) {
			page.setTotal(Long.valueOf(module.getEntitys().size()));
			page.setData(module.getEntitys().subList(page.getStartIndex().intValue(), page.getEndIndex().intValue()));
		}
		
		return page;
	}

	@Override
	public int save(Object bean) throws Exception {
		// TODO Auto-generated method stub
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
