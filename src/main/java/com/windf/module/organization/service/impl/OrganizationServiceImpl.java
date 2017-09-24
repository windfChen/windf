package com.windf.module.organization.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.windf.core.general.bean.NameBean;
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

	@Override
	public List<NameBean> getMyList() {
		return organizationDao.getMyList();
	}

}
