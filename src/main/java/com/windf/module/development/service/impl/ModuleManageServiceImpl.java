package com.windf.module.development.service.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.windf.core.exception.EntityException;
import com.windf.core.file.FileUtil;
import com.windf.core.file.JavaFileUtil;
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
		 * 验证条件
		 */
		if (getModule(moduleDto.getCode()) != null) {
			throw new EntityException("模块已存在");
		}

		/*
		 * 复制配置文件
		 */
		String exampleConfigPath = Constant.DEVELOPMENT_JAVA_PATH + File.separator + Constant.DEFAULT_EXAMPLE_PATH;
		String newModuleConfigPath = Constant.DEVELOPMENT_JAVA_PATH + File.separator + moduleDto.getCode();
		List<File> list = FileUtil.copyFolder(exampleConfigPath, newModuleConfigPath);
		
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
				modifyFile(file, moduleDto.getCode());
				
				// 修改文件名
				String fileName = file.getName();
				String newFileName = fileName.replace(StringUtil.firstLetterUppercase(Constant.DEFAULT_EXAMPLE_PATH), 
						StringUtil.firstLetterUppercase(moduleDto.getCode()));
				FileUtil.rename(file, newFileName);
				
				
			}
		}
		
		
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
