package com.windf.module.user.controler;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.windf.core.exception.UserException;
import com.windf.core.util.ParameterUtil;
import com.windf.module.user.Constant;
import com.windf.module.user.entity.SsoUser;
import com.windf.module.user.service.SsoUserService;
import com.windf.plugins.web.BaseControler;

@Controller
@Scope("prototype")
@RequestMapping(value = "/register")
public class RegisterControler extends BaseControler {

	@Resource
	private SsoUserService ssoUserService ;
	
	@RequestMapping(value = "", method = {RequestMethod.GET })
	public String registerPage() {
		return responseReturn.page(Constant.WEB_BASE_VIEW + "/register");
	}
	
	@RequestMapping(value = "", method = {RequestMethod.POST })
	public String register() {
		String username = paramenter.getString("username");
		String password = paramenter.getString("password");
		String truename = paramenter.getString("truename");
		if (ParameterUtil.hasEmpty(username, password, truename)) {
			return responseReturn.parameterError();
		}
		
		SsoUser ssoUser = new SsoUser();
		ssoUser.setUsername(username);
		ssoUser.setPassword(password);
		ssoUser.setTurename(truename);
		
		try {
			ssoUserService.addUser(ssoUser);
			return responseReturn.success();
		} catch (UserException e) {
			return responseReturn.error(e.getMessage());
		}
	}
}
