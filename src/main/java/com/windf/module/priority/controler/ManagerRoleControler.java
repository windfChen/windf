package com.windf.module.priority.controler;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.windf.module.priority.service.RoleService;
import com.windf.plugins.manage.service.ManageGirdService;
import com.windf.plugins.manage.web.controler.ManagerGridControler;

@Controller
@Scope("prototype")
@RequestMapping(value = ManagerRoleControler.CONTROLER_PATH)
public class ManagerRoleControler extends ManagerGridControler {
	protected final static String CONTROLER_PATH = "/role";

	@Resource
	private RoleService roleService;
	
	@Override
	protected ManageGirdService getManagerGridService() {
		return roleService;
	}

}
