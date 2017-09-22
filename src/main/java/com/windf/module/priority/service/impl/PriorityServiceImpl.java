package com.windf.module.priority.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.windf.core.general.dao.CrudDao;
import com.windf.module.priority.dao.PriorityDao;
import com.windf.module.priority.entity.Priority;
import com.windf.module.priority.service.PriorityService;
import com.windf.plugins.manage.service.impl.ManagerGirdiServiceImpl;

/**
 * 权限服务
 * @author chenyafeng
 *
 */
@Service
public class PriorityServiceImpl extends ManagerGirdiServiceImpl implements PriorityService {
	
	@Resource
	private PriorityDao priorityDao;

	@Override
	public List<Priority> getPrioritiesByUser(String userId) {
		return null;
	}

	@Override
	public CrudDao getGridDao() {
		return priorityDao;
	}
	
}
