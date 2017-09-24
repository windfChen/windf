package com.windf.module.priority.controler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.windf.core.general.bean.NameBean;
import com.windf.module.priority.service.RoleService;
import com.windf.plugins.web.BaseControler;

@Controller
@Scope("prototype")
@RequestMapping(value = RoleControler.CONTROLER_PATH)
public class RoleControler extends BaseControler {
	protected final static String CONTROLER_PATH = "/role";
	
	@Resource
	private RoleService roleService;
	

	@RequestMapping(value = "/myList", method = {RequestMethod.GET})
	public String myList() {
		List<NameBean> data = roleService.getMyList();
		// TODO 为了ext手动修改key为字符串
		List<String[]> result = new ArrayList<String[]>();
		for (int i = 0; i < data.size(); i++) {
			NameBean nameBean = data.get(i);
			result.add(new String[]{nameBean.getId().toString(), nameBean.getName()});
		}
		return responseReturn.returnData(result);
	}
}
