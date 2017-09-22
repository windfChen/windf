package com.windf.module.priority.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.windf.core.bean.Page;
import com.windf.core.general.dao.CrudDao;
import com.windf.module.priority.dao.RolePriorityDao;
import com.windf.module.priority.service.RolePriorityService;
import com.windf.plugins.manage.service.impl.ManagerGirdiServiceImpl;

@Service
public class RolePriorityServiceImpl extends ManagerGirdiServiceImpl implements RolePriorityService{
	
	@Resource
	private RolePriorityDao rolePriorityDao;

	@Override
	public CrudDao getGridDao() {
		return rolePriorityDao;
	}

	@Override
	public void save(String roleId, String priorityIds) {
		String[] ids = priorityIds.split(",");
		List<String> idList = Arrays.asList(ids);
		rolePriorityDao.insert(roleId, idList);
	}

	@Override
	public Page<Map<String, Object>> listOtherPriority(Map<String, Object> condition, Integer pageNo, Integer pageSize) {
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(Long.valueOf(pageNo), pageSize);
		
		page.setTotal(rolePriorityDao.countOtherPriority(condition));
		page.setData(rolePriorityDao.listOtherPriority(condition, page.getStartIndex(), page.getPageSize()));
		
		return page;
	}

}
