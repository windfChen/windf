package com.windf.module.priority.controler;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.windf.module.priority.Constant;
import com.windf.module.priority.service.PriorityService;
import com.windf.plugins.manage.service.ManageGirdService;
import com.windf.plugins.manage.web.controler.ManagerGridControler;

@Controller
@Scope("prototype")
@RequestMapping(value = ManagerPriorityControler.CONTROLER_PATH)
public class ManagerPriorityControler extends ManagerGridControler {
	protected final static String CONTROLER_PATH = Constant.MODULE_WEB_PATH + "";
	
	@Resource
	private PriorityService priorityService;
	
	@Override
	protected ManageGirdService getManagerGridService() {
		return priorityService;
	}

}
