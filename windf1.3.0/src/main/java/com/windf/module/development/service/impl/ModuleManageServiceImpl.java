package com.windf.module.development.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.windf.core.exception.EntityException;
import com.windf.core.util.Page;
import com.windf.module.development.Constant;
import com.windf.module.development.pojo.Module;
import com.windf.module.development.pojo.ModuleDto;
import com.windf.module.development.pojo.ModuleMaster;
import com.windf.module.development.pojo.ModuleSearch;
import com.windf.module.development.service.ModuleManageService;

@Service
public class ModuleManageServiceImpl  implements ModuleManageService {

	@Override
	public Module createModule(ModuleDto moduleDto) throws EntityException {
		/*
		 * 验证参数
		 */
		if (moduleDto == null || StringUtils.isEmpty(moduleDto.getCode())) {
			throw new EntityException("参数错误");
		}
		
		/*
		 * 验证前置条件
		 */
		if (getModule(moduleDto.getCode()) != null) {
			throw new EntityException("模块已存在");
		}

		/*
		 * 读取模板模块
		 */
		String templateModuleCode = Constant.DEFAULT_EXAMPLE_PATH;
		Module exampleModule = Module.loadModule(templateModuleCode);
		
		/*
		 * 复制创建新模块
		 */
		Module newModule = exampleModule.clone(moduleDto.getCode());
		
		/*
		 * 设置、保存模块属性
		 */
		newModule.setName(moduleDto.getName());
		newModule.setInfo(moduleDto.getInfo());
		newModule.setBasePath(moduleDto.getBasePath());
		newModule.write();
		
		/*
		 * 修改统计文件
		 */
		ModuleMaster moduleMaster = ModuleMaster.getInstance();
		moduleMaster.addModule(newModule);
		moduleMaster.write();
		
		return newModule;
	}

	@Override
	public Module getModule(String code) throws EntityException {
		Module result = null;
		
		try {
			result = Module.loadModule(code);
		} catch (EntityException e) {
		}
		
		return result;
	}

	@Override
	public Page<Module> listAllModule(ModuleSearch moduleSearch, Integer pageIndex, Integer pageSize) {
		
		/*
		 *  获取所有模块
		 */
		List<Module> modules = null;
		try {
			ModuleMaster moduleMaster = ModuleMaster.getInstance();
			modules = moduleMaster.getModules();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		/*
		 * 遍历模块，获得符合条件的数据
		 */
		List<Module> resultList = new ArrayList<>();
		if (modules != null) {
			
			for (Module module : modules) {
				/*
				 *  条件筛选
				 */
				if (moduleSearch != null) {
					continue;
				}
				
				resultList.add(module);
				
			}
			
		}
		
		/*
		 *  分页筛选
		 */
		Page<Module> page = new Page<Module>(Long.valueOf(pageIndex) , pageSize);
		if (resultList.size() > 0) {
			page.setTotal(Long.valueOf(resultList.size()));
			page.setData(modules.subList(page.getStartIndex().intValue(), page.getEndIndex().intValue()));
		}
		
		return page;
	}


}