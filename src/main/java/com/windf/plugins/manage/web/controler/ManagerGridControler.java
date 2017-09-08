package com.windf.plugins.manage.web.controler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.fabric.xmlrpc.base.Array;
import com.windf.core.exception.CodeException;
import com.windf.core.exception.DataAccessException;
import com.windf.core.exception.UserException;
import com.windf.core.util.CollectionUtil;
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/detail", method = {RequestMethod.GET})
	public Map<String, Object> detail() {
		String code = getRequestCode("detail");
		String id = this.getParameter("id");
		
		Object data = null;
		try {
			Object d = managerGridService.detail(module.getCode(), code, id);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("entity", d);
			List<Object> list = new ArrayList<Object>();
			list.add(map);
			data = list;
		} catch (Exception e) {
			e.printStackTrace();
			return jsonReturn.errorMap(e.getMessage());
		}
		
		return jsonReturn.successMap(data);
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/save", method = {RequestMethod.POST})
	public Map<String, Object> save() {
		String code = getRequestCode("save");
		Object bean = this.getMapParameter("bean");
		if (bean == null) {
			bean = this.getMapParameter("entity");
		}
		
		try {
			managerGridService.save(module.getCode(), code, bean);
			return jsonReturn.successMap();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonReturn.errorMap(e.getMessage());
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/update", method = {RequestMethod.POST})
	public Map<String, Object> update() {
		String code = getRequestCode("update");
		Object bean = this.getMapParameter("bean");
		if (bean == null) {
			bean = this.getMapParameter("entity");
		}
		
		try {
			managerGridService.update(module.getCode(), code, bean);
			return jsonReturn.successMap();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonReturn.errorMap(e.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "/delete", method = {RequestMethod.POST})
	public Map<String, Object> delete() {
		String code = getRequestCode("delete");
		String ids = this.getParameter("ids");
		
		List<String> idList = null;
		if (StringUtil.isNotEmpty(ids)) {
			idList = Arrays.asList(ids.split(","));
		}
		
		if (CollectionUtil.isEmpty(idList)) {
			return jsonReturn.paramErrorMap();
		}
		
		try {
			managerGridService.delete(module.getCode(), code, idList);
			return jsonReturn.successMap();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonReturn.errorMap(e.getMessage());
		}
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
