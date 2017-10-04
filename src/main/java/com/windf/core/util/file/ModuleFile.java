package com.windf.core.util.file;

import java.io.File;

import com.windf.core.exception.CodeException;

public class ModuleFile {

	private String prefix;
	private String[] paths;
	private String fileName;
	private File currentFile;
	private String relativePath;

	public ModuleFile(String startPath, File file) {
		/*
		 * 解析路径 TODO 待优化，这里有点乱
		 */
		String absolutePath = file.getAbsolutePath(); // 绝对路径
		
		int lastSeparator = absolutePath.lastIndexOf(File.separator);
		if (lastSeparator > startPath.length() + 1) {
			relativePath = absolutePath.substring(startPath.length() + 1, lastSeparator); // 相对路径
			paths = relativePath.replace(File.separator, ".").split("\\."); // 相对路径数组
		}
		String fileFullName = file.getName();
		fileName = fileFullName.substring(0, fileFullName.lastIndexOf(".")); // 文件名，不带拓展名
		prefix = FileUtil.getPrefix(file.getName()); // 拓展名
		currentFile = file;
		
	}

	/**
	 * 验证是合法
	 * @return
	 */
	public boolean verifyPath() {
		boolean result = true;
		// 目录路径必须大于4
		if (paths == null || paths.length < 4) {
			result = false;
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public Class getFileClass() {
		Class result = null;
		String className = relativePath.replace(File.separator, ".") + "." + fileName;
		try {
			result = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new CodeException(e);
		}
		return result;
	}
	
	public String getModuleType() {
		return paths[2];
	}

	public String getModuleCode() {
		return paths[3];
	}

	public String getPackageName() {
		return paths[paths.length - 1];
	}

	public String getPrefix() {
		return prefix;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public String getFileName() {
		return fileName;
	}

	public File getCurrentFile() {
		return currentFile;
	}

}
