package com.windf.module.priority.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.windf.core.general.dao.GridDao;
import com.windf.module.priority.dao.RoleDao;
import com.windf.module.priority.service.RoleService;
import com.windf.plugins.manage.service.impl.ManagerGirdiServiceImpl;

/**
 * 角色服务
 * @author chenyafeng
 *
 */
@Service
public class RoleServiceImpl extends ManagerGirdiServiceImpl implements RoleService {
	
	@Resource
	private RoleDao roleDao;

	@Override
	public GridDao getGridDao() {
		return roleDao;
	}
	
}
