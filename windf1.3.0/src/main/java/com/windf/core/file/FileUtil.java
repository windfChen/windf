package com.windf.core.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import com.windf.plugins.log.Logger;
import com.windf.plugins.log.LogFactory;

public class FileUtil {
	private static Logger logger = LogFactory.getLogger(FileUtil.class); 
	
	/**
	 * 获取类文件路径
	 * e: 项目路径/WEB-INF/classes
	 * @return
	 */
	public static String getClassPath() {
		return FileUtil.class.getClassLoader().getResource("").getPath();
	}
	
	/**
	 * 获取配置文件路径
	 * e: 项目路径/WEB-INF/config
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
		return classPath.substring(0, classPath.lastIndexOf("WEB-INF"));
	}
	
	 /**
	  * 复制文件夹
	  * 
	  * 如果目标目录不存在创建
	  * 复制文件、递归复制文件夹
	  * @param source 源目录
	  * @param target 目标目录
	  */
	 public static void copyFolder(String source, String target) {
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
		for (File file : sourceFiles) {
			if (file.isFile()) {
				copyFile(file.getPath(), target + File.pathSeparator + file.getName()); 
			} else if (file.isDirectory()) {
				copyFolder(file.getPath(), target + File.pathSeparator + file.getName());
			}
		}

	}

	/**
	 * 复制文件
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
}
