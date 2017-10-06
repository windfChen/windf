package com.windf.module.development.controler;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.windf.module.development.Constant;
import com.windf.module.development.entity.Module;
import com.windf.module.development.frame.Initialization;
import com.windf.module.development.service.ModuleService;
import com.windf.plugins.manage.service.ManageGirdService;
import com.windf.plugins.manage.web.controler.ManagerGridControler;

@Controller
@Scope("prototype")
@RequestMapping(value = ModuleControler.CONTROLER_PATH)
public class ModuleControler extends ManagerGridControler{
	protected final static String CONTROLER_PATH = Constant.MODULE_WEB_PATH + "";
	
	@Resource
	private ModuleService moduleService ;
	
	@Override
	protected ManageGirdService getManagerGridService() {
		return moduleService;
	}
	
	@Override
	protected Class<? extends Object> getEntity() {
		return Module.class;
	}

	@RequestMapping(value = "/test", method = {RequestMethod.GET})
	public String test() {
		Initialization a = new Initialization(); 
		a.init();
		
		return responseReturn.success();
	}

}
