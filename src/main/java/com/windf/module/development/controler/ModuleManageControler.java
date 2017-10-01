package com.windf.module.development.controler;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.windf.core.bean.Page;
import com.windf.core.exception.UserException;
import com.windf.core.util.ParameterUtil;
import com.windf.module.development.Constant;
import com.windf.module.development.entity.Module;
import com.windf.module.development.entity.dto.ModuleDto;
import com.windf.module.development.service.ModuleManageService;
import com.windf.plugins.web.BaseControler;

@Controller
@Scope("prototype")
@RequestMapping(value = Constant.WEB_BASE_PATH + "/module")
public class ModuleManageControler extends BaseControler{
	
	@Resource
	private ModuleManageService moduleManageService ;

	@RequestMapping(value = "/", method = {RequestMethod.GET})
	public String index() {
		/*
		 * 获取参数
		 */
		String pageIndexStr = ParameterUtil.defaultValue(paramenter.getString("pageIndex"), "1");
		String pageSizeStr = ParameterUtil.defaultValue(paramenter.getString("pageSize"), Page.DEFAULT_PAGE_SIZE.toString());
		
		/*
		 * 验证参数
		 */
		Integer pageIndex = ParameterUtil.getInteger(pageIndexStr);
		Integer pageSize = ParameterUtil.getInteger(pageSizeStr);
		if (ParameterUtil.hasEmpty(pageIndex, pageSize)) {
			return responseReturn.parameterError();
		}
		
		Page<Module> page = moduleManageService.listAllModule(null, pageIndex, pageSize);
		this.paramenter.setValue("page", page);
		
		return Constant.WEB_BASE_VIEW + "index" ;
	}

	@RequestMapping(value = "/create", method = {RequestMethod.GET})
	public String toCreate() {
		
		// 验证参数
		String moduleCode = paramenter.getString("code");
		if (!ParameterUtil.hasEmpty(moduleCode)) {

			// 调用服务
			try {
				Module module = moduleManageService.getModuleByCode(moduleCode);
				this.paramenter.setValue("bean", module);
			} catch (UserException e) {
				e.printStackTrace();
			}
		}
		
		return Constant.WEB_BASE_VIEW + "create";
	}
	
	@RequestMapping(value = "/create", method = {RequestMethod.POST})
	public String create() {
		// 验证参数
		String moduleCode = paramenter.getString("code");
		String moduleName = paramenter.getString("name");
		String moduleInfo = paramenter.getString("info");
		String moduleBasePath = paramenter.getString("basePath");
		if (ParameterUtil.hasEmpty(moduleCode, moduleName, moduleInfo, moduleBasePath)) {
			return responseReturn.parameterError();
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
			return responseReturn.error(e.getMessage());
		}
		
		return responseReturn.returnData(true, "创建成功");
	}
	
	@RequestMapping(value = "/modify", method = {RequestMethod.POST})
	public String modify() {
		// 验证参数
		String moduleCode = paramenter.getString("code");
		String moduleName = paramenter.getString("name");
		String moduleInfo = paramenter.getString("info");
		String moduleBasePath = paramenter.getString("basePath");
		if (ParameterUtil.hasEmpty(moduleCode, moduleName, moduleInfo, moduleBasePath)) {
			return responseReturn.parameterError();
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
			return responseReturn.error(e.getMessage());
		}
		
		return responseReturn.returnData(true, "修改成功");
	}

}
