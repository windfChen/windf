package com.windf.module.user.controler;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.windf.module.user.Constant;
import com.windf.module.user.service.UserService;
import com.windf.plugins.manage.service.ManageGirdService;
import com.windf.plugins.manage.web.controler.ManagerGridControler;
import com.windf.plugins.web.WebContext;

@Controller
@Scope("prototype")
@RequestMapping(value = ManageUserControler.CONTROLER_PATH)
public class ManageUserControler extends ManagerGridControler{
	protected final static String CONTROLER_PATH = Constant.MODULE_WEB_PATH + "/manage";
	
	@Resource
	private UserService userService;
	
	@Override
	protected ManageGirdService getManagerGridService() {
		return userService;
	}

	@RequestMapping(value = "/save", method = {RequestMethod.POST})
	public String save() {
		Map<String, Object> entity = paramenter.getMap("entity");	
		entity = this.filterMapValue(entity);
		entity.put("ip", WebContext.getIpAddr(request));
		
		try {
			this.getManagerGridService().save(entity);
			return responseReturn.success();
		} catch (Exception e) {
			e.printStackTrace();
			return responseReturn.error(e.getMessage());
		}
	}
}
