package com.windf.module.user.controler;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.windf.core.exception.UserException;
import com.windf.module.user.Constant;
import com.windf.module.user.service.SsoUserService;
import com.windf.plugins.web.BaseControler;
import com.windf.plugins.web.WebContext;

@Controller
@Scope("prototype")
@RequestMapping(value = "")
public class LoginControler extends BaseControler{
	
	@Resource
	private SsoUserService ssoUserService ;
		
	@RequestMapping(value = {"/login", ""}, method = {RequestMethod.GET})
	public String loginPage() {
		return responseReturn.page(Constant.WEB_BASE_VIEW + "/login");
	}
	
	@RequestMapping(value = "/login", method = {RequestMethod.POST})
	public String login() {
		String username = paramenter.getString("username");
		String password = paramenter.getString("password");
		String loginIp = WebContext.getIpAddr(request);
		
		try {
			ssoUserService.login(username, password, loginIp);
			return responseReturn.success();
		} catch (UserException e) {
			return responseReturn.error(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
	public String logout() {
		try {
			ssoUserService.logout();
			return responseReturn.success();
		} catch (UserException e) {
			return responseReturn.error(e.getMessage());
		}
	}

}
