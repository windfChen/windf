package com.windf.core.frame;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.windf.core.bean.Module;
import com.windf.core.exception.CodeException;
import com.windf.core.util.CollectionUtil;
import com.windf.core.util.file.FileUtil;

public class ProjectStart {
	private static ProjectStart projectStart = new ProjectStart();
	
	public static ProjectStart getInstance() {
		return projectStart;
	}
	
	private String basePackageName;
	private Map<String, Module> modules = new HashMap<String, Module>(); // 模块
	private Map<String, Module> plugins = new HashMap<String, Module>(); // 插件
	
	private ProjectStart() {
		
	}
	
	/**
	 * 项目开始的时候执行，用于初始化模块，和注册信息
	 */
	public void start() {
		/*
		 * 读取classes，初始化模块
		 */
		initModules();
		/*
		 * 注册各个模块
		 */
		initRegister();
		/*
		 * 执行各个模块的初始化操作
		 */
		InitializationControler.getInstance().doInit();
	}
	
	/**
	 * 读取各个模块，寻找模块的描述信息
	 *寻找初始化类
	 * 寻找过滤器
	 * session注册
	 * 读取各个插件，寻找插件的描述信息
	 */
	private void initModules() {
		/*
		 * 获取遍历目录
		 */
		String classPath = FileUtil.getClassPath();
		basePackageName = InitializationControler.class.getName().substring(0,InitializationControler.class.getName().indexOf(".core")).replace(".", File.separator);
		File modulePackage = FileUtil.getFile(classPath + File.separator + basePackageName);

		/*
		 *  递归目录，初始化模块
		 */
		this.filePathIterator(modulePackage);
		
		/*
		 * TODO 模块间排序
		 */
	}
	
	private void initRegister() {
		/*
		 * 遍历模块和插件，注册服务 
		 */
		Iterator<Module> iterator = modules.values().iterator();
		while (iterator.hasNext()) {
			Module module = (Module) iterator.next();
			this.registerModule(module);
		}
		iterator = plugins.values().iterator();
		while (iterator.hasNext()) {
			Module module = (Module) iterator.next();
			this.registerModule(module);
		}
	}
	
	/**
	 * 遍历classes目录
	 * 
	 * @param file
	 */
	@SuppressWarnings("rawtypes")
	private void filePathIterator(File file) {

		if (file.isDirectory()) { // 如果是目录继续遍历
			File[] subFiles = file.listFiles();
			for (int i = 0; i < subFiles.length; i++) {
				filePathIterator(subFiles[i]);
			}
		} else {
			String prefix = FileUtil.getPrefix(file.getName());
			if ("class".equals(prefix)) { // 如果是java的解析

				/*
				 * 解析路径
				 */
				String filePath = file.getAbsolutePath();
				String classPackagePath = filePath.substring(FileUtil.getClassPath().length() + 1).replace(File.separator, "."); // 包全名
				String classPath = classPackagePath.substring(0, classPackagePath.lastIndexOf(".")); // 类路径
				String packageName = classPath.substring(0, classPath.lastIndexOf("."));
				packageName = packageName.substring(packageName.lastIndexOf(".") + 1); // 最后一级目录

				/*
				 * 获取模块code
				 */
				String[] subClassPath = classPackagePath.substring((basePackageName).length() + 1).split("\\.");
				String moduleType = subClassPath[0];
				String moduleCode = subClassPath[1];
				
				/*
				 *  初始化模块
				 */
				Module currentModule = null;
				if ("module".equals(moduleType)) {
					currentModule = modules.get(moduleCode);
					if (currentModule == null) {
						currentModule = new Module(moduleCode);
						modules.put(moduleCode, currentModule);
					}
				} else if ("plugins".equals(moduleType)){
					currentModule = plugins.get(moduleCode);
					if (currentModule == null) {
						currentModule = new Module(moduleCode);
						plugins.put(moduleCode, currentModule);
					}
				} else {
					return;
				}

				try {
					Class clazz = Class.forName(classPath);

					if ("frame".equals(packageName)) {
						if (Initializationable.class.isAssignableFrom(clazz)) { // 添加初始化
							if (CollectionUtil.isEmpty(currentModule.getInitializationables())) {
								currentModule.setInitializationables(new ArrayList<Initializationable>());
							}
							currentModule.getInitializationables().add((Initializationable) clazz.newInstance());
						} else if (Filter.class.isAssignableFrom(clazz)) { // 添加拦截器
							if (CollectionUtil.isEmpty(currentModule.getFilters())) {
								currentModule.setFilters(new ArrayList<Filter>());
							}
							currentModule.getFilters().add((Filter) clazz.newInstance());
						} else if (Session.class.isAssignableFrom(clazz)) { // 添加会话
							if (CollectionUtil.isEmpty(currentModule.getSessions())) {
								currentModule.setSessions(new ArrayList<Session>());
							}
							currentModule.getSessions().add((Session) clazz.newInstance());
						}
					}

				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
					throw new CodeException("Initializationable 初始化类启动错误");
				}
			}
		}

	}

	/**
	 * 模块的各个服务注册
	 * @param module
	 */
	private void registerModule(Module module) {
		if (CollectionUtil.isNotEmpty(module.getFilters())) {
			for (Filter filter : module.getFilters()) {
				FilterControler.getInstance().addFilter(filter);
			}
		}
		
		if (CollectionUtil.isNotEmpty(module.getInitializationables())) {
			for (Initializationable initializationable : module.getInitializationables()) {
				InitializationControler.getInstance().addInitializationable(initializationable);
			}
		}
		
	}
}
