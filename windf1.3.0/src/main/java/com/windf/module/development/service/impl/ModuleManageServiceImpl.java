package com.windf.module.development.service.impl;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.windf.core.constant.ModuleConstant;
import com.windf.core.exception.EntityException;
import com.windf.core.file.FileUtil;
import com.windf.core.file.JavaFileUtil;
import com.windf.core.file.XmlFileUtil;
import com.windf.core.file.JavaFileUtil.LineReader;
import com.windf.core.util.StringUtil;
import com.windf.module.development.Constant;
import com.windf.module.development.pojo.Module;
import com.windf.module.development.pojo.ModuleDto;
import com.windf.module.development.service.ModuleManageService;

@Service
public class ModuleManageServiceImpl  implements ModuleManageService {

	@Override
	public void createModule(ModuleDto moduleDto) throws EntityException {
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
			//throw new EntityException("模块已存在");
		}

		/*
		 * 读取模块配置文件
		 */
		String templateModuleCode = Constant.DEFAULT_EXAMPLE_PATH;
		String exampleDescriptPath = ModuleConstant.DEFAULT_MODULE_DESCRIPT_PATH + File.separator + 
				templateModuleCode + File.separator  + ModuleConstant.DEFAULT_MODULE_DESCRIPT_FILE_NAME;
		File exampleDescriptFile = FileUtil.getWebappFile(exampleDescriptPath);
		if (!exampleDescriptFile.exists()) {
			throw new EntityException("模板模块：[" + templateModuleCode + "]的配置文件不存在");
		}
		
		/*
		 * 解析模板，获得模板配置
		 */
		 Module exampleModule = XmlFileUtil.readXml2Object(exampleDescriptFile, Module.class);
		
		/*
		 * 复制不同类型的文件
		 */
		 Map<String, String> pathMap = exampleModule.getPath(); 
		 Iterator<String> pathMapKeyIterator = pathMap.keySet().iterator();
		 while (pathMapKeyIterator.hasNext()) {
			String key = (String) pathMapKeyIterator.next();
			String value = pathMap.get(key);
			
//			copyFolder(exampleConfigPath, newModuleConfigPath, moduleDto.getCode());
		}
		 
		
		String exampleConfigPath = Constant.DEVELOPMENT_JAVA_PATH + File.separator + Constant.DEFAULT_EXAMPLE_PATH;
		String newModuleConfigPath = Constant.DEVELOPMENT_JAVA_PATH;
		copyFolder(exampleConfigPath, newModuleConfigPath, moduleDto.getCode());
		
	}

	@Override
	public Module getModule(String code) throws EntityException {
		
		Module result = null;
		
		/*
		 * 获取模块
		 */
		String moduleConfigPath = Constant.DEVELOPMENT_JAVA_PATH + File.separator + code;
		File file = new File(moduleConfigPath);
		if (file.exists()) {
			result = new Module();
			result.setCode(code);
		}
		
		return result;
	}

	/**
	 * 复制文件
	 * @param path
	 * @param newPath
	 */
	private void copyFolder(String path, String newPath, String moduleCode) {
		List<File> list = FileUtil.copyFolder(path, newPath + File.separator + moduleCode);
		
		/*
		 * 修改每个java文件的文件名和内容
		 */
		if (!CollectionUtils.isEmpty(list)) {
			for (int i = 0; i < list.size(); i++) {
				File file = list.get(i);
				
				if (file.isDirectory()) {
					continue;
				}
				
				// 修改文件内容
				modifyFile(file, moduleCode);
				
				// 修改文件名
				String fileName = file.getName();
				String newFileName = fileName.replace(StringUtil.firstLetterUppercase(Constant.DEFAULT_EXAMPLE_PATH), 
						StringUtil.firstLetterUppercase(moduleCode));
				FileUtil.rename(file, newFileName);
				
				
			}
		}
		
	}

	/**
	 * 修改文件内容
	 * @param file
	 * @param newModuleName
	 */
	private void modifyFile(File file, final String newModuleName) {
	        JavaFileUtil.readLine(file, new LineReader() {
				@Override
				public String readLine(List<String> oldLines, String lineContent, int lineNo) {
					lineContent = lineContent.replace(StringUtil.firstLetterUppercase(Constant.DEFAULT_EXAMPLE_PATH), 
							StringUtil.firstLetterUppercase(newModuleName));
					lineContent = lineContent.replace(Constant.DEFAULT_EXAMPLE_PATH, newModuleName);
					return lineContent;
				}
			});
	}

}
