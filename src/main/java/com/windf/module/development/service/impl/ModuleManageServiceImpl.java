package com.windf.module.development.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.windf.core.exception.UserException;
import com.windf.core.util.CollectionUtil;
import com.windf.core.util.Page;
import com.windf.module.development.Constant;
import com.windf.module.development.pojo.Module;
import com.windf.module.development.pojo.ModuleMaster;
import com.windf.module.development.pojo.dto.ModuleDto;
import com.windf.module.development.pojo.dto.ModuleSearch;
import com.windf.module.development.service.ModuleManageService;

@Service
public class ModuleManageServiceImpl  implements ModuleManageService {

	@Override
	public Module createModule(ModuleDto moduleDto) throws UserException {
		/*
		 * 验证参数
		 */
		if (moduleDto == null || StringUtils.isEmpty(moduleDto.getCode())) {
			throw new UserException("参数错误");
		}
		
		/*
		 * 验证前置条件
		 */
		if (this.getModuleByCode(moduleDto.getCode()) != null) {
			throw new UserException("模块已存在");
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
	public Module modifyModule(ModuleDto moduleDto) throws UserException {
		/*
		 * 验证参数
		 */
		if (moduleDto == null || StringUtils.isEmpty(moduleDto.getCode())) {
			throw new UserException("参数错误");
		}
		
		/*
		 * 验证前置条件
		 */
		Module module = this.getModuleByCode(moduleDto.getCode());
		if (module == null) {
			throw new UserException("模块不存在");
		}
		
		/*
		 * 设置、保存模块属性
		 */
		module.setName(moduleDto.getName());
		module.setInfo(moduleDto.getInfo());
		module.setBasePath(moduleDto.getBasePath());
		module.write();
		
		/*
		 * 修改统计文件
		 */
		ModuleMaster moduleMaster = ModuleMaster.getInstance();
		moduleMaster.addModule(module);
		moduleMaster.write();
		
		return module;
	}

	@Override
	public Module getModuleByCode(String code) throws UserException {
		Module result = null;
		
		ModuleMaster moduleMaster = ModuleMaster.getInstance();
		result = moduleMaster.findModuleByCode(code);
		
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
			modules = moduleMaster.listAllModules();
		} catch (UserException e) {
			e.printStackTrace();
		}
		
		/*
		 * 遍历模块，获得符合条件的数据
		 */
		List<Module> resultList = new ArrayList<Module>();
		if (modules != null) {
			
			for (Module module : modules) {
				/*
				 *  条件筛选
				 */
				if (moduleSearch != null) {
					if (StringUtils.isNotEmpty(moduleSearch.getBasePath()) && !moduleSearch.getBasePath().equals(module.getBasePath())) {
						continue;
					}
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
			page.setData(resultList.subList(page.getStartIndex().intValue(), page.getEndIndex().intValue()));
		}
		
		return page;
	}

	@Override
	public Module getModuleByPath(String basePath) {
		Module result = null;
		
		ModuleSearch moduleSearch = new ModuleSearch();
		moduleSearch.setBasePath(basePath);
		
		Page<Module> page = listAllModule(moduleSearch, 1, 1);
		if (CollectionUtil.isNotEmpty(page.getData())) {
			result = page.getData().get(0);
		}
		return result;
	}


}
