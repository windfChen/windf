package com.windf.core.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

import com.windf.plugins.log.LogFactory;
import com.windf.plugins.log.Logger;

public class FileUtil {
	private static Logger logger = LogFactory.getLogger(FileUtil.class);

	/**
	 * 获取类文件路径 e: 项目路径/WEB-INF/classes
	 * 
	 * @return
	 */
	public static String getClassPath() {
		return FileUtil.class.getClassLoader().getResource("").getPath();
	}

	/**
	 * 获取配置文件路径 e: 项目路径/WEB-INF/config
	 * 
	 * @return 配置文件路径
	 */
	public static String getConfigPath() {
		String classPath = getClassPath();
		String webinfoPath = classPath.substring(0, classPath.lastIndexOf("classes"));
		String configPath = webinfoPath + "config";
		return configPath;
	}

	/**
	 * 获取项目的部署路径
	 */
	public static String getWebappPath() {
		String classPath = getClassPath();
		return classPath.substring(0, classPath.lastIndexOf("/WEB-INF"));
	}

	/**
	 * 复制web目录下的文件夹
	 * 
	 * @param source
	 * @param target
	 */
	public static List<File> copyWebFolder(String source, String target) {
		return copyFolder(getWebappPath() + source, getWebappPath() + target);
	}

	/**
	 * 复制文件夹
	 * 
	 * 如果目标目录不存在创建 复制文件、递归复制文件夹
	 * 
	 * @param source
	 *            源目录
	 * @param target
	 *            目标目录
	 */
	public static List<File> copyFolder(String source, String target) {

		List<File> list = new LinkedList<File>();

		/*
		 * 获取目标目录，如果目标目录不存在创建
		 */
		File targetFolder = new File(target);
		if (!targetFolder.exists()) {
			targetFolder.mkdirs();
		}

		/*
		 * 遍历源目录，复制文件、递归复制文件夹
		 */
		File sourceFolder = new File(source);
		File[] sourceFiles = sourceFolder.listFiles();
		if (sourceFiles != null) {
			for (File file : sourceFiles) {
				// 不遍历隐藏文件
				if (file.getName().startsWith(".")) {
					continue;
				}

				if (file.isFile()) {
					String targetFilePath = target + File.separator + file.getName();
					boolean success = copyFile(file.getPath(), targetFilePath);

					/*
					 * 添加复制的文件到文件列表
					 */
					if (success) {
						File targetFile = new File(targetFilePath);
						list.add(targetFile);
					}
				} else if (file.isDirectory()) {
					String targetDirectoryPath = target + File.separator + file.getName();
					List<File> tempList = copyFolder(file.getPath(), targetDirectoryPath);

					/*
					 * 添加复制的文件夹到文件列表
					 */
					if (tempList != null && tempList.size() > 0) {
						File targetDirectory = new File(targetDirectoryPath);
						list.add(targetDirectory);
					}

					/*
					 * 添加复制的所有文件到文件列表
					 */
					list.addAll(tempList);

				}
			}
		}

		return list;

	}

	/**
	 * 复制文件
	 * 
	 * @param source
	 * @param target
	 */
	public static boolean copyFile(String source, String target) {
		boolean result = false;

		/*
		 * 复制文件
		 */
		BufferedReader bufferedReader = null;
		PrintStream printStream = null;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(source)));
			printStream = new PrintStream(new FileOutputStream(target));
			String s = null;
			while ((s = bufferedReader.readLine()) != null) {
				printStream.println(s);
				printStream.flush();
			}
			result = true;
		} catch (FileNotFoundException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			try {
				if (bufferedReader != null)
					bufferedReader.close();
				if (printStream != null)
					printStream.close();
			} catch (IOException e) {
				logger.error(e);
			}

		}

		return result;

	}

	/**
	 * 给文件重命名
	 * 
	 * @param file
	 * @param newName
	 * @return
	 */
	public static void rename(File file, String newName) {
		String directory = file.getParentFile().getAbsolutePath();
		file.renameTo(new File(directory + File.separator + newName));
	}
}
