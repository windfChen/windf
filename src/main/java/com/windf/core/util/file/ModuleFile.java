package com.windf.core.util.file;

import java.io.File;

public class ModuleFile {

	private String startPath;
	private String prefix;
	private String[] paths;
	private String fileName;
	private File currentFile;
	private String relativePath;

	public ModuleFile(File file) {
		/*
		 * 解析路径 TODO 待优化，这里有点乱
		 */
		String absolutePath = file.getAbsolutePath(); // 绝对路径
		relativePath = absolutePath.substring(startPath.length() + 1, startPath.lastIndexOf(File.separator)); // 相对路径
		String fileFullName = file.getName();
		fileName = fileFullName.substring(0, fileFullName.lastIndexOf(".")); // 文件名，不带拓展名
		prefix = FileUtil.getPrefix(file.getName()); // 拓展名
		paths = relativePath.replace(File.separator, ".").split("\\."); // 相对路径数组
		currentFile = file;
		
	}

	/**
	 * 验证是合法
	 * @return
	 */
	public boolean verify() {
		boolean result = true;
		// 目录路径必须大于4
		if (paths.length >= 4) {
			result = false;
		}
		return result;
	}
	
	public String getModuleType() {
		return paths[2];
	}

	public String getModuleCode() {
		return paths[3];
	}

	public String getParentPath() {
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
