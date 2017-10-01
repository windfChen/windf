package com.windf.module.development.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.windf.core.exception.UserException;
import com.windf.module.development.util.file.XmlFileUtil;

public class Module {
	
	private String code;
	private String name;
	private String basePath;	// 现在写在配置文件中，以后可以从中读取
	private String info;
	
	private Map<String, Controler> controlerMap = new HashMap<String, Controler>();
	private Map<String, Service> serviceMap = new HashMap<String, Service>();
	
	// 标记变量
	protected boolean initializationed = false;
	
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
	
	public void load(String code) {
		
	}
	
	public void write() throws UserException {
		File file = com.windf.core.bean.Module.getMoudleConfigFileByCode(this.getCode());
		XmlFileUtil.writeObject2Xml(this, file);
	}

	/**
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
