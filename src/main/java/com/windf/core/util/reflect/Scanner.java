package com.windf.core.util.reflect;

import java.io.File;

import com.windf.core.util.file.FileUtil;

public class Scanner {
	
	private ScannerHandler handler;
	private String startPath;
	private String prefix;
	private String[] paths;
	private String fileName;
	private File file;
	private String relativePath;
	
	public Scanner(String startPath, ScannerHandler handler) {
		this.startPath = startPath;
		this.handler = handler;
	}

	public void run() {
		/*
		 * 创建文件
		 */
		File file = FileUtil.getFile(startPath);
		
		/*
		 *  递归目录
		 */
		if (file.exists()) {
			this.filePathIterator(file);
		}
	}
	
	/**
	 * 遍历目录
	 * 
	 * @param file
	 */
	private void filePathIterator(File file) {

		if (file.isDirectory()) { // 如果是目录继续遍历
			File[] subFiles = file.listFiles();
			for (int i = 0; i < subFiles.length; i++) {
				filePathIterator(subFiles[i]);
			}
		} else {
			
			/*
			 * 解析路径 TODO 待优化，这里有点乱
			 */
			String absolutePath = file.getAbsolutePath();	// 绝对路径
			String fullPath = absolutePath.substring(startPath.length() + 1); // 相对路径+文件名
			if (fullPath.lastIndexOf(File.separator) > -1) {
				relativePath = fullPath.substring(0, fullPath.lastIndexOf(File.separator)); // 相对路径
				fileName = fullPath.substring(fullPath.lastIndexOf(File.separator) + 1, fullPath.lastIndexOf("."));	// 文件名，不带拓展名
				paths = relativePath.replace(File.separator, ".").split("\\.");	// 相对路径数组
			}
			prefix = FileUtil.getPrefix(file.getName());	// 拓展名
			this.file = file;

			handler.handle(this);
		}

	}

	public String getPrefix() {
		return prefix;
	}
	
	public String getRelativePath() {
		return relativePath;
	}

	public String[] getCurrentRelativePaths() {
		return paths;
	}
	
	public String getCurrentRelativePathByIndex(int index) {
		if (paths != null && index - 1 < paths.length &&  index - 1 >= 0) {
			return paths[index - 1];
		}
		return null;
	}

	public String getFileName() {
		return fileName;
	}

	public File getCurrentFile() {
		return file;
	}

	
	
}
