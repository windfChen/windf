package com.windf.module.priority.controler;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.windf.core.bean.Page;
import com.windf.core.util.ParameterUtil;
import com.windf.module.priority.service.RolePriorityService;
import com.windf.plugins.manage.service.ManageGirdService;
import com.windf.plugins.manage.web.controler.ManagerGridControler;

@Controller
@Scope("prototype")
@RequestMapping(value = ManagerRolePriorityAddControler.CONTROLER_PATH)
public class ManagerRolePriorityAddControler extends ManagerGridControler {
	protected final static String CONTROLER_PATH = "/role/priority/add";

	@Resource
	private RolePriorityService rolePriorityService;
	
	@Override
	protected ManageGirdService getManagerGridService() {
		return rolePriorityService;
	}

	@RequestMapping(value = "/save", method = {RequestMethod.POST})
	public String save() {
		String roleId = paramenter.getString("roleId");
		String priorityId = paramenter.getString("ids");
		if (ParameterUtil.hasEmpty(roleId, priorityId)) {
			return responseReturn.parameterError();
		}
		
		try {
			rolePriorityService.save(roleId, priorityId);
			return responseReturn.success();
		} catch (Exception e) {
			e.printStackTrace();
			return responseReturn.error(e.getMessage());
		}
	}

	@RequestMapping(value = "/list", method = {RequestMethod.GET})
	public String list() {
		Map<String, Object> condition = paramenter.getMap("condition");
		condition = this.filterMapValue(condition);
		String pageNoStr = paramenter.getString("page");
		String pageSizeStr = paramenter.getString("limit");
		Integer pageNo = 1;
		Integer pageSize = 10;
		try {
			pageNo = Integer.parseInt(pageNoStr);
			pageSize = Integer.parseInt(pageSizeStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (ParameterUtil.hasEmpty(condition.get("roleId"))) {
			responseReturn.parameterError();
		}
		
		Map<String, Object> result = null;
		try {
			Page<Map<String, Object>> page = rolePriorityService.listOtherPriority(condition, pageNo, pageSize);
			result = new HashMap<String, Object>();
			result.put("models", page.getData());
			result.put("totalCount", page.getTotal());
		} catch (Exception e) {
			e.printStackTrace();
			return responseReturn.error(e.getMessage());
		}
		
		return responseReturn.returnData(result);
	}
	
	
}
