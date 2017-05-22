package com.windf.module.module.web.controler;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.windf.core.exception.EntityException;
import com.windf.module.module.Constant;
import com.windf.module.module.pojo.ModuleDto;
import com.windf.module.module.service.ModuleManageService;
import com.windf.plugins.web.ParentControler;

@Controller
@Scope("prototype")
@RequestMapping(value = Constant.WEB_BASE_PATH)
public class ModuleManageControler extends ParentControler{
	
	@Resource
	private ModuleManageService moduleManageService ;
	
	@ResponseBody
	@RequestMapping(value = "/", method = {RequestMethod.GET})
	public Map<String, Object> index(HttpServletRequest request) {
		// 验证参数
		String moduleName = request.getParameter("name");
		if (StringUtils.isEmpty(moduleName)) {
			return paramErrorMap();
		}
		
		// 构建参数
		ModuleDto moduleDto = new ModuleDto();
		moduleDto.setName(moduleName);
		
		// 调用服务
		try {
			moduleManageService.createModule(moduleDto);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return successMap("");
	}
	
	@RequestMapping(value = "/index", method = {RequestMethod.GET})
	public String index2() {
		return Constant.WEB_BASE_VIEW + "/index" ;
	}
	
	@ResponseBody
	@RequestMapping(value = "/login", method = {RequestMethod.POST})
	public Map<String, Object> login(ModelMap map, HttpServletRequest request) {
//		map.put("platform", platform);
//		map.put("resourceType", resourceType);
//		map.put("keyword", keyword) ;
//		map.put("pageSize", pageSize) ;
//		map.put("pageIndex", pageIndex) ;
		
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "/register", method = {RequestMethod.POST })
	public Map<String, Object> register(HttpServletRequest request) {
//		String username = request.getParameter("u");
//		String password = request.getParameter("p");
//		String truename = request.getParameter("n");
		
		
		Map<String, Object> result = null;
		result = successMap();
		return result;
	}

}
