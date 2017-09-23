package com.windf.module.organization.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.windf.core.general.dao.CrudDao;
import com.windf.module.organization.dao.OrganizationDao;
import com.windf.module.organization.service.OrganizationService;
import com.windf.plugins.manage.service.impl.ManagerGirdiServiceImpl;

@Service
public class OrganizationServiceImpl extends ManagerGirdiServiceImpl implements OrganizationService {

	@Resource
	private OrganizationDao organizationDao;
	
	@Override
	public CrudDao getGridDao() {
		return organizationDao;
	}

}
