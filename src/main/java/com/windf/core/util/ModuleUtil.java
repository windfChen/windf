package com.windf.core.util;

import com.windf.core.file.FileUtil;

public class ModuleUtil {
	/**
	 * 获得模块的配置文件路径
	 * @param moduleCode
	 * @param yourPath
	 * @return
	 */
	public static String getConfigFilePath(String moduleCode, String yourPath) {
		String configPath = FileUtil.getConfigPath();
		String result = configPath + "/module/" + moduleCode + yourPath;
		result = result.replace("//", "/");
		return result;
	}
}
