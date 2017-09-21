package com.windf.plugins.manage.web.controler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.windf.core.bean.Page;
import com.windf.core.exception.CodeException;
import com.windf.core.exception.UserException;
import com.windf.core.util.CollectionUtil;
import com.windf.core.util.StringUtil;
import com.windf.plugins.manage.Constant;
import com.windf.plugins.manage.bean.GridConfig;
import com.windf.plugins.manage.service.ManageGirdService;
import com.windf.plugins.web.BaseControler;

public abstract class ManagerGridControler extends BaseControler {
		
	@RequestMapping(value = "", method = {RequestMethod.GET})
	public String index() {
		return responseReturn.page(Constant.WEB_BASE_VIEW + "grid");
	}
	
	@RequestMapping(value = "/grid", method = {RequestMethod.GET})
	public Object grid() {
		String code = getRequestCode();
		String roleId = "";
		Map<String, Object> condition = paramenter.getMap("condition");
		
		GridConfig gridConfig = null;
		try {
			gridConfig = this.getManagerGridService().getGridConfig(code, roleId, condition);
		} catch (UserException e) {
			e.printStackTrace();
		} catch (CodeException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseReturn.successData(gridConfig);
	}
	
	@RequestMapping(value = "/list", method = {RequestMethod.GET})
	public String list() {
		String code = getRequestCode();
		Map<String, Object> condition = paramenter.getMap("condition");
		String pageNoStr = paramenter.getString("page");
		String pageSizeStr = paramenter.getString("limit");
		Integer pageNo = 1;
		Integer pageSize = 10;
		try {
			pageNo = Integer.parseInt(pageNoStr);
			pageSize = Integer.parseInt(pageSizeStr);
		} catch (Exception e) {
		}
		
		Map<String, Object> result = null;
		try {
			Page<Map<String, Object>> page = this.getManagerGridService().list(code, condition, pageNo, pageSize);
			result = new HashMap<String, Object>();
			result.put("models", page.getData());
			result.put("totalCount", page.getTotal());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return responseReturn.error(e.getMessage());
		}
		
		return responseReturn.returnData(result);
	}
	
	@RequestMapping(value = "/detail", method = {RequestMethod.GET})
	public String detail() {
		String code = getRequestCode();
		String id = paramenter.getString("id");
		
		Object data = null;
		try {
			Object d = this.getManagerGridService().detail(code, id);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("entity", d);
			List<Object> list = new ArrayList<Object>();
			list.add(map);
			data = list;
		} catch (Exception e) {
			e.printStackTrace();
			return responseReturn.error(e.getMessage());
		}
		
		return responseReturn.successData(data);
		
	}
	
	@RequestMapping(value = "/save", method = {RequestMethod.POST})
	public String save() {
		String code = getRequestCode();
		Object bean = paramenter.getMap("bean");
		if (bean == null) {
			bean = paramenter.getMap("entity");
		}
		
		try {
			this.getManagerGridService().save(code, bean);
			return responseReturn.success();
		} catch (Exception e) {
			e.printStackTrace();
			return responseReturn.error(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/update", method = {RequestMethod.POST})
	public String update() {
		String code = getRequestCode();
		Object bean = paramenter.getMap("bean");
		if (bean == null) {
			bean = paramenter.getMap("entity");
		}
		
		try {
			this.getManagerGridService().update(code, bean);
			return responseReturn.success();
		} catch (Exception e) {
			e.printStackTrace();
			return responseReturn.error(e.getMessage());
		}
	}

	@RequestMapping(value = "/delete", method = {RequestMethod.POST})
	public String delete() {
		String code = getRequestCode();
		String ids = paramenter.getString("ids");
		
		List<String> idList = null;
		if (StringUtil.isNotEmpty(ids)) {
			idList = Arrays.asList(ids.split(","));
		}
		
		if (CollectionUtil.isEmpty(idList)) {
			return responseReturn.parameterError();
		}
		
		try {
			this.getManagerGridService().delete(code, idList);
			return responseReturn.success();
		} catch (Exception e) {
			e.printStackTrace();
			return responseReturn.error(e.getMessage());
		}
	}
	
	/**
	 * 获取管理表格服务
	 * @return
	 */
	protected abstract ManageGirdService getManagerGridService();

	/**
	 * 获得请求地址，生成的code
	 * eg：http://localhost/m/test/grid.do?r=1 --> testGrid
	 * @return
	 */
	protected String getRequestCode() {
		String requestPath = path.getControlerPath();
		
		int index = requestPath.lastIndexOf('.');
		if (index > 0) {
			requestPath = requestPath.substring(0, index);
		}
		
		String result = StringUtil.toCamelCase(requestPath, "/");
		return result;
	}
}
