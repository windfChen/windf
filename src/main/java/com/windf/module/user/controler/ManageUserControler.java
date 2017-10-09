package com.windf.module.user.controler;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.windf.module.user.Constant;
import com.windf.module.user.entity.User;
import com.windf.module.user.service.UserService;
import com.windf.plugins.manage.service.ManageGirdService;
import com.windf.plugins.manage.web.controler.ManagerGridControler;

@Controller
@Scope("prototype")
@RequestMapping(value = ManageUserControler.CONTROLER_PATH)
public class ManageUserControler extends ManagerGridControler{
	protected final static String CONTROLER_PATH = MANAGE_PATH + Constant.MODULE_WEB_PATH;
	
	@Resource
	private UserService userService;
	
	@Override
	protected ManageGirdService getManagerGridService() {
		return userService;
	}
	
	@Override
	protected Class<? extends Object> getEntity() {
		return User.class;
	}
}
