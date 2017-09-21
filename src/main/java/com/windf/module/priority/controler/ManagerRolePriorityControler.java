package com.windf.module.priority.controler;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.windf.module.priority.service.RolePriorityService;
import com.windf.plugins.manage.service.ManageGirdService;
import com.windf.plugins.manage.web.controler.ManagerGridControler;

@Controller
@Scope("prototype")
@RequestMapping(value = ManagerRolePriorityControler.CONTROLER_PATH)
public class ManagerRolePriorityControler extends ManagerGridControler {
	protected final static String CONTROLER_PATH = "/role/priority";

	@Resource
	private RolePriorityService rolePriorityService;
	
	@Override
	protected ManageGirdService getManagerGridService() {
		return rolePriorityService;
	}
	
	
}
