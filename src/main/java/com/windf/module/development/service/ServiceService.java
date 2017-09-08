package com.windf.module.development.service;

import java.util.List;

import com.windf.core.exception.UserException;
import com.windf.module.development.pojo.Service;
import com.windf.module.development.pojo.ServiceMethod;

public interface ServiceService {

	/**
	 * 获得module的所有service
	 * @param moduleCode
	 * @return
	 * @throws UserException
	 */
	List<Service> getAllService(String moduleCode) throws UserException;
	
	void createServiceMethod(String moduleCode, String serviceName, ServiceMethod serviceMethod) throws UserException;
}
