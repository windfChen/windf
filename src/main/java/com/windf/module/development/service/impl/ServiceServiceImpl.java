package com.windf.module.development.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.windf.core.exception.UserException;
import com.windf.module.development.entity.Module;
import com.windf.module.development.entity.ModuleMaster;
import com.windf.module.development.entity.Service;
import com.windf.module.development.entity.ServiceMethod;
import com.windf.module.development.modle.service.ServiceCoder;
import com.windf.module.development.service.ServiceService;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService{
	
	private ModuleMaster moduleMaster;
	
	public ServiceServiceImpl() {
		try {
			moduleMaster = ModuleMaster.getInstance();
		} catch (UserException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Service> getAllService(String moduleCode) throws UserException {
		List<Service> result = new ArrayList<Service>();
		Module module = moduleMaster.findModuleByCode(moduleCode);
		if (module != null) {
			result = module.getServices();
		} else {
			throw new UserException("模块[code:" + moduleCode + "]不存在");
		}
		return result;
	}

	@Override
	public void createServiceMethod(String moduleCode, String serviceName, ServiceMethod serviceMethod) throws UserException {
		ServiceCoder serviceCoder = new ServiceCoder(moduleCode, serviceName, null);
		
		serviceCoder.createMethod(serviceMethod);
		
		serviceCoder.write();
		
	}
}
