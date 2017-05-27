package com.windf.module.development.web.controler;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.windf.core.exception.EntityException;
import com.windf.core.util.StringUtil;
import com.windf.module.development.Constant;
import com.windf.module.development.pojo.Module;
import com.windf.module.development.pojo.ModuleDto;
import com.windf.module.development.service.ModuleManageService;
import com.windf.plugins.web.ParentControler;

@Controller
@Scope("prototype")
@RequestMapping(value = Constant.WEB_BASE_PATH + "/module")
public class ModuleManageControler extends ParentControler{
	
	@Resource
	private ModuleManageService moduleManageService ;
	
	@ResponseBody
	@RequestMapping(value = "/create", method = {RequestMethod.GET})
	public Map<String, Object> create(HttpServletRequest request) {
		// 验证参数
		String moduleCode = request.getParameter("code");
		String moduleName = request.getParameter("name");
		String moduleInfo = request.getParameter("info");
		String moduleBasePath = request.getParameter("basePath");
		if (StringUtil.hasEmpty(moduleCode, moduleName, moduleInfo, moduleBasePath)) {
			return paramErrorMap();
		}
		
		// 构建参数
		ModuleDto moduleDto = new ModuleDto();
		moduleDto.setCode(moduleCode);
		moduleDto.setName(moduleName);
		moduleDto.setInfo(moduleInfo);
		moduleDto.setBasePath(moduleBasePath);
		
		// 调用服务
		try {
			moduleManageService.createModule(moduleDto);
		} catch (EntityException e) {
			return errorMessageMap(e.getMessage());
		}
		
		return successMap("创建成功");
	}
	
	@ResponseBody
	@RequestMapping(value = "/", method = {RequestMethod.GET})
	public Map<String, Object> get(HttpServletRequest request) {
		// 验证参数
		String moduleCode = request.getParameter("code");
		if (StringUtil.isEmpty(moduleCode)) {
			return paramErrorMap();
		}
		
		// 调用服务
		Module module = null;
		try {
			module = moduleManageService.getModule(moduleCode);
		} catch (EntityException e) {
			return errorMessageMap(e.getMessage());
		}
		
		return successMap(module);
	}

	@RequestMapping(value = "/index", method = {RequestMethod.GET})
	public String index() {
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
