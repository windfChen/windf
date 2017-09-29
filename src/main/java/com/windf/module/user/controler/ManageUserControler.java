package com.windf.module.user.controler;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.windf.module.organization.entity.Organization;
import com.windf.module.priority.entity.Role;
import com.windf.module.sso.entity.SsoUser;
import com.windf.module.user.Constant;
import com.windf.module.user.entity.User;
import com.windf.module.user.service.UserService;
import com.windf.plugins.manage.service.ManageGirdService;
import com.windf.plugins.manage.web.controler.ManagerGridControler;
import com.windf.plugins.web.WebContext;

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

	@RequestMapping(value = "/save", method = {RequestMethod.POST})
	public String save() {
		Map<String, Object> map = paramenter.getMap("entity");
		
		User user = new User();
		user.setName((String) map.get("ssoUser.username"));
		SsoUser ssoUser  = new SsoUser();
		ssoUser.setUsername((String) map.get("ssoUser.username"));
		ssoUser.setTurename((String) map.get("ssoUser.truename"));
		ssoUser.setEmail((String) map.get("ssoUser.email"));
		ssoUser.setPhone((String) map.get("ssoUser.phone"));
		ssoUser.setLastLoginIp(WebContext.getIpAddr(request));
		user.setSsoUser(ssoUser);
		user.setSex((String) map.get("sex"));
		Role role = new Role();
		role.setId(paramenter.getInteger("entity.role.id"));
		user.setRole(role);
		Organization org = new Organization();
		org.setId(paramenter.getInteger("entity.organization.id"));
		user.setOrganization(org);
		
		try {
			this.getManagerGridService().save(user);
			return responseReturn.success();
		} catch (Exception e) {
			e.printStackTrace();
			return responseReturn.error(e.getMessage());
		}
	}
}
