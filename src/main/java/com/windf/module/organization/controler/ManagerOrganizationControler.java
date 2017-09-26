package com.windf.module.organization.controler;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.windf.module.organization.Constant;
import com.windf.module.organization.service.OrganizationService;
import com.windf.plugins.manage.service.ManageGirdService;
import com.windf.plugins.manage.web.controler.ManagerGridControler;

@Controller
@Scope("prototype")
@RequestMapping(value = ManagerOrganizationControler.CONTROLER_PATH)
public class ManagerOrganizationControler extends ManagerGridControler{
	protected final static String CONTROLER_PATH = Constant.MODULE_WEB_PATH + "/manage";
	
	@Resource
	private OrganizationService organizationService;

	@Override
	protected ManageGirdService getManagerGridService() {
		return organizationService;
	}

}
