package com.windf.module.organization.controler;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.windf.core.general.entity.NameBean;
import com.windf.module.organization.Constant;
import com.windf.module.organization.service.OrganizationService;
import com.windf.plugins.manage.service.ManageGirdService;
import com.windf.plugins.manage.web.controler.ManagerGridControler;

@Controller
@Scope("prototype")
@RequestMapping(value = OrganizationControler.CONTROLER_PATH)
public class OrganizationControler extends ManagerGridControler{
	protected final static String CONTROLER_PATH = Constant.MODULE_WEB_PATH + "";
	
	@Resource
	private OrganizationService organizationService;

	@Override
	protected ManageGirdService getManagerGridService() {
		return organizationService;
	}
	
	@RequestMapping(value = "/myList", method = {RequestMethod.GET})
	public String myList() {
		List<NameBean> data = organizationService.getMyList();
		// TODO 为了ext手动修改key为字符串
		List<String[]> result = new ArrayList<String[]>();
		for (int i = 0; i < data.size(); i++) {
			NameBean nameBean = data.get(i);
			result.add(new String[]{nameBean.getId().toString(), nameBean.getName()});
		}
		return responseReturn.returnData(result);
	}

}
