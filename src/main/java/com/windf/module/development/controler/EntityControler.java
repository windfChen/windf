package com.windf.module.development.controler;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.windf.module.development.Constant;
import com.windf.module.development.entity.Entity;
import com.windf.module.development.service.EntityService;
import com.windf.plugins.manage.service.ManageGirdService;
import com.windf.plugins.manage.web.controler.ManagerGridControler;

@Controller
@Scope("prototype")
@RequestMapping(value = EntityControler.CONTROLER_PATH)
public class EntityControler extends ManagerGridControler{
	protected final static String CONTROLER_PATH = Constant.MODULE_WEB_PATH + "/entity";

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
