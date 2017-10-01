package com.windf.module.development.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.windf.core.exception.UserException;
import com.windf.core.util.CollectionUtil;
import com.windf.core.util.StringUtil;
import com.windf.core.util.file.FileUtil;
import com.windf.module.development.Constant;
import com.windf.module.development.util.file.JavaFileUtil;
import com.windf.module.development.util.file.XmlFileUtil;
import com.windf.module.development.util.file.JavaFileUtil.LineReader;

public class Module {

	/**
	 * 加载模块
	 * @param moduleCode
	 * @return
	 * @throws UserException
	 */
	public static Module loadModule(String moduleCode) throws UserException {
		File exampleDescriptFile = com.windf.core.bean.Module.getMoudleConfigFileByCode(moduleCode);
		if (!exampleDescriptFile.exists()) {
			throw new UserException("模板模块：[" + moduleCode + "]的配置文件不存在");
		}

		Module module = XmlFileUtil.readXml2Object(exampleDescriptFile, Module.class);
		//module.init();

		return module;
	}

	private String code;
	private String name;
	private String basePath;
	private String info;
	
	private Map<String, Controler> controlerMap = new HashMap<String, Controler>();
	private Map<String, Service> serviceMap = new HashMap<String, Service>();
	
	// 标记变量
	private boolean initializationed = false;
	
	public void init() {

		/*
		 * 只初始化一次
		 */
		if (initializationed) {
			return;
		}
		
		/*
		 *  初始化反向调用
		 */
		Iterator<Controler> controlerIterator = controlerMap.values().iterator();
		while (controlerIterator.hasNext()) {
			Controler controler = (Controler) controlerIterator.next();
			controler.setModule(this);
			controler.init();
		}
		
		Iterator<Service> serviceIterator = serviceMap.values().iterator();
		while (serviceIterator.hasNext()) {
			Service service = (Service) serviceIterator.next();
			service.setModule(this);
			service.init();
		}
		
		initializationed = true;
	}

	public Module clone(String newCode) throws UserException {
		ModuleMaster moduleMaster = ModuleMaster.getInstance();

		this.cloneSourceDirectorys(moduleMaster, newCode);

		this.cloneWebDirectorys(moduleMaster, newCode);

		this.cloneConfigFile(moduleMaster, newCode);

		return loadModule(newCode);
	}
	
	public void write() throws UserException {
		File file = com.windf.core.bean.Module.getMoudleConfigFileByCode(this.getCode());
		XmlFileUtil.writeObject2Xml(this, file);
	}

	private void cloneSourceDirectorys(ModuleMaster moduleMaster, String newCode) {
		Map<String, String> sourcePath = moduleMaster.getSourcePath();
		cloneDirectorys(sourcePath, Constant.DEVELOPMENT_BASE_PATH, newCode);
	}

	private void cloneWebDirectorys(ModuleMaster moduleMaster, String newCode) {
		Map<String, String> webPath = moduleMaster.getWebPath();
		cloneDirectorys(webPath, FileUtil.getWebappPath(), newCode);
	}

	private void cloneDirectorys(Map<String, String> pathMap, String basePath, String newCode) {

		Iterator<String> webPathsKeyIterator = pathMap.keySet().iterator();
		while (webPathsKeyIterator.hasNext()) {
			String key = webPathsKeyIterator.next();
			String path = basePath + pathMap.get(key);

			copyDirectory(path + this.getCode(), path, newCode);
		}
	}

	private void cloneConfigFile(ModuleMaster moduleMaster, String newCode) {
		String templateModuleConfigFilePath =  com.windf.core.bean.Module.getMoudleConfigFileByCode(this.getCode()).getAbsolutePath();
		String newModuleConfigFilePath =com.windf.core.bean.Module.getMoudleConfigFileByCode(newCode).getAbsolutePath();
		FileUtil.copyFile(templateModuleConfigFilePath, newModuleConfigFilePath);

		this.modifyFileContent(new File(newModuleConfigFilePath), newCode);
	}

	private void copyDirectory(String path, String newPath, String moduleCode) {
		List<File> list = FileUtil.copyFolder(path, newPath + moduleCode);

		if (CollectionUtil.isNotEmpty(list)) {
			for (int i = 0; i < list.size(); i++) {
				File file = list.get(i);

				if (file.isDirectory()) {
					continue;
				}

				modifyFileContent(file, moduleCode);

				String fileName = file.getName();
				String newFileName = fileName.replace(StringUtil.firstLetterUppercase(Constant.DEFAULT_EXAMPLE_PATH),
						StringUtil.firstLetterUppercase(moduleCode));
				FileUtil.rename(file, newFileName);

			}
		}

	}

	private void modifyFileContent(File file, final String newModuleName) {
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
	
	/**
	 * 获取所有controler
	 * @return
	 */
	public List<Controler> getControlers() {
		return new ArrayList<Controler>(controlerMap.values());
	}

	/**
	 * 设置controler
	 * @param controlers
	 */
	public void setControlers(List<Controler> controlers) {
		for (Controler controler : controlers) {
			controler.setModule(this);
			controlerMap.put(controler.getName(), controler);
		}
	}

	/**
	 * 获取所有service
	 * @return
	 */
	public List<Service> getServices() {
		return new ArrayList<Service>(serviceMap.values());
	}

	/**
	 * 设置service
	 * @param services
	 */
	public void setServices(List<Service> services) {
		for (Service service : services) {
			serviceMap.put(service.getServiceName(), service);
		}
	}

	
	/**
	 * 根据service名获得service
	 * @param serviceName
	 * @return
	 */
	public Service getServiceByName(String serviceName) {
		return serviceMap.get(serviceName);
	}
	
	/**
	 * 添加一个Service
	 * @param service
	 * @throws UserException 
	 */
	public void addService(Service service) throws UserException {
		serviceMap.put(service.getServiceName(), service);
		this.write();
	}
	
	/**
	 * 根据名称获得控制器
	 * @param controlerName
	 * @return
	 */
	public Controler getControler(String controlerName) {
		Controler result = controlerMap.get(controlerName);
		return result;
	}
	
	/**
	 * 添加一个Controler
	 * @param controler
	 * @throws UserException 
	 */
	public void addControler(Controler controler) throws UserException {
		// 初始化scotroler的module对象
		if (controler.getModule() == null) {
			controler.setModule(this);
		}
		controlerMap.put(controler.getName(), controler);
		this.write();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
