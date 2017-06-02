package com.windf.module.development.web.controler;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.windf.core.exception.EntityException;
import com.windf.core.util.Page;
import com.windf.core.util.ParameterUtil;
import com.windf.module.development.Constant;
import com.windf.module.development.pojo.Module;
import com.windf.module.development.pojo.ModuleDto;
import com.windf.module.development.service.ModuleManageService;
import com.windf.plugins.web.BaseControler;

@Controller
@Scope("prototype")
@RequestMapping(value = Constant.WEB_BASE_PATH + "/module")
public class ModuleManageControler extends BaseControler{
	
	@Resource
	private ModuleManageService moduleManageService ;
	
	@ResponseBody
	@RequestMapping(value = "/create", method = {RequestMethod.GET})
	public Map<String, Object> create(HttpServletRequest request) {
		// 验证参数
		String moduleCode = this.getParameter("code");
		String moduleName = this.getParameter("name");
		String moduleInfo = this.getParameter("info");
		String moduleBasePath = this.getParameter("basePath");
		if (ParameterUtil.hasEmpty(moduleCode, moduleName, moduleInfo, moduleBasePath)) {
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
	@RequestMapping(value = "", method = {RequestMethod.GET})
	public Map<String, Object> get() {
		// 验证参数
		String moduleCode = this.getParameter("code");
		if (ParameterUtil.hasEmpty(moduleCode)) {
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
		/*
		 * 获取参数
		 */
		String pageIndexStr = ParameterUtil.defaultValue(this.getParameter("pageIndex"), "1");
		String pageSizeStr = ParameterUtil.defaultValue(this.getParameter("pageSize"), Page.DEFAULT_PAGE_SIZE.toString());
		
		/*
		 * 验证参数
		 */
		Integer pageIndex = ParameterUtil.getInteger(pageIndexStr);
		Integer pageSize = ParameterUtil.getInteger(pageSizeStr);
		if (ParameterUtil.hasEmpty(pageIndex, pageSize)) {
			return PARAMETER_ERROR_PAGE;
		}
		
		Page<Module> page = moduleManageService.listAllModule(null, pageIndex, pageSize);
		this.setValue("page", page);
		
		return Constant.WEB_BASE_VIEW + "index" ;
	}
	
	@RequestMapping(value = "/remove", method = {RequestMethod.GET})
	public String remove() {
		return returnSuccessPage("删除成功", this.getBasePath() + "/dev/module/index", "");
	}

}
