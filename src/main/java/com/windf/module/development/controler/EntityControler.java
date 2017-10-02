package com.windf.module.development.controler;

import javax.annotation.Resource;

import com.windf.module.development.entity.Entity;
import com.windf.module.development.service.EntityService;
import com.windf.plugins.manage.service.ManageGirdService;
import com.windf.plugins.manage.web.controler.ManagerGridControler;

public class EntityControler extends ManagerGridControler{

	@Resource
	private EntityService entityService;
	
	@Override
	protected ManageGirdService getManagerGridService() {
		return entityService;
	}

	@Override
	protected Class<? extends Object> getEntity() {
		return Entity.class;
	}
}
