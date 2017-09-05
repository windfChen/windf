package com.windf.plugins.manage.web.controler;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.windf.core.exception.CodeException;
import com.windf.core.exception.DataAccessException;
import com.windf.core.exception.UserException;
import com.windf.core.util.Page;
import com.windf.core.util.StringUtil;
import com.windf.plugins.manage.Constant;
import com.windf.plugins.manage.bean.GridConfig;
import com.windf.plugins.manage.service.ManageGirdService;
import com.windf.plugins.web.BaseControler;

public abstract class ManagerGridControler extends BaseControler {
	
	@Resource
	private ManageGirdService managerGridService;
	
	@RequestMapping(value = "", method = {RequestMethod.GET})
	public String index() {
		return Constant.WEB_BASE_VIEW + "grid";
	}
	
	@ResponseBody
	@RequestMapping(value = "/grid", method = {RequestMethod.GET})
	public Map<String, Object> grid() {
		String code = getRequestCode("grid");
		String roleId = "";
		Map<String, Object> condition = this.getMapParameter("condition");
		
		GridConfig gridConfig = null;
		try {
			gridConfig = managerGridService.getGridConfig(module.getCode(), code, roleId, condition);
		} catch (UserException e) {
			e.printStackTrace();
		} catch (CodeException e) {
			e.printStackTrace();
		}
		return jsonReturn.successMap(gridConfig);
	}
	
	@ResponseBody
	@RequestMapping(value = "/list", method = {RequestMethod.GET})
	public Map<String, Object> list() {
		String code = getRequestCode("list");
		Map<String, Object> condition = this.getMapParameter("condition");
		String pageNoStr = this.getParameter("page");
		String pageSizeStr = this.getParameter("limit");
		Integer pageNo = 1;
		Integer pageSize = 10;
		try {
			pageNo = Integer.parseInt(pageNoStr);
			pageSize = Integer.parseInt(pageSizeStr);
		} catch (Exception e) {
		}
		
		Map<String, Object> result = null;
		try {
			Page<Map<String, Object>> page = managerGridService.list(module.getCode(), code, condition, pageNo, pageSize);
			result = new HashMap<String, Object>();
			result.put("models", page.getData());
			result.put("totalCount", page.getTotal());
		} catch (UserException e) {
			e.printStackTrace();
		} catch (CodeException e) {
			e.printStackTrace();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Map<String, Object> detail() {
		Map<String, Object> result = null;
		return result;
	}
	
	public Map<String, Object> add() {
		Map<String, Object> result = null;
		return result;
	}
	
	public Map<String, Object> update() {
		Map<String, Object> result = null;
		return result;
	}

	/**
	 * 获得请求地址，生成的code
	 * eg：http://localhost/m/test/grid.do?r=1 --> testGrid
	 * @return
	 */
	protected String getRequestCode(String methodName) {
		String requestPath = getRequestPath();
		int index = requestPath.lastIndexOf('.');
		if (index > 0) {
			requestPath = requestPath.substring(0, index);
		}
		
		String result = StringUtil.toCamelCase(requestPath, "/");
		if (StringUtil.isNotEmpty(methodName)) {
			methodName = StringUtil.firstLetterUppercase(methodName);
			if (result.endsWith(methodName)) {
				result = result.substring(0, result.length() - methodName.length());
			}
		}
		return result;
	}
}
