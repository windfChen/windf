package com.windf.module.development.web.controler;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.windf.core.exception.UserException;
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
	
	@Override
	protected String getModulePath() {
		return Constant.WEB_BASE_PATH;
	}

	@RequestMapping(value = "/create", method = {RequestMethod.GET})
	public String toCreate() {
		
		// 验证参数
		String moduleCode = this.getParameter("code");
		if (!ParameterUtil.hasEmpty(moduleCode)) {

			// 调用服务
			try {
				Module module = moduleManageService.getModuleByCode(moduleCode);
				this.setValue("bean", module);
			} catch (UserException e) {
				e.printStackTrace();
			}
		}
		
		return Constant.WEB_BASE_VIEW + "create";
	}
	
	@ResponseBody
	@RequestMapping(value = "/create", method = {RequestMethod.POST})
	public Map<String, Object> create() {
		// 验证参数
		String moduleCode = this.getParameter("code");
		String moduleName = this.getParameter("name");
		String moduleInfo = this.getParameter("info");
		String moduleBasePath = this.getParameter("basePath");
		if (ParameterUtil.hasEmpty(moduleCode, moduleName, moduleInfo, moduleBasePath)) {
			return jsonReturn.paramErrorMap();
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
		} catch (UserException e) {
			return jsonReturn.errorMap(e.getMessage());
		}
		
		return jsonReturn.returnMap(true, "创建成功");
	}
	
	@ResponseBody
	@RequestMapping(value = "/modify", method = {RequestMethod.POST})
	public Map<String, Object> modify() {
		// 验证参数
		String moduleCode = this.getParameter("code");
		String moduleName = this.getParameter("name");
		String moduleInfo = this.getParameter("info");
		String moduleBasePath = this.getParameter("basePath");
		if (ParameterUtil.hasEmpty(moduleCode, moduleName, moduleInfo, moduleBasePath)) {
			return jsonReturn.paramErrorMap();
		}
		
		// 构建参数
		ModuleDto moduleDto = new ModuleDto();
		moduleDto.setCode(moduleCode);
		moduleDto.setName(moduleName);
		moduleDto.setInfo(moduleInfo);
		moduleDto.setBasePath(moduleBasePath);
		
		// 调用服务
		try {
			moduleManageService.modifyModule(moduleDto);
		} catch (UserException e) {
			return jsonReturn.errorMap(e.getMessage());
		}
		
		return jsonReturn.returnMap(true, "修改成功");
	}
	
	@RequestMapping(value = "/", method = {RequestMethod.GET})
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
			return pageReturn.parameterError();
		}
		
		Page<Module> page = moduleManageService.listAllModule(null, pageIndex, pageSize);
		this.setValue("page", page);
		
		return Constant.WEB_BASE_VIEW + "index" ;
	}

}
