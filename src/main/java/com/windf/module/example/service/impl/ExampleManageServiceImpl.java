package com.windf.module.example.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.windf.core.general.dao.GridDao;
import com.windf.module.example.dao.ExampleDao;
import com.windf.module.example.service.ExampleManageService;
import com.windf.plugins.manage.service.impl.ManagerGirdiServiceImpl;

@Service
public class ExampleManageServiceImpl extends ManagerGirdiServiceImpl implements ExampleManageService {
	
	@Resource
	private ExampleDao exampleDao;

	@Override
	public GridDao getGridDao() {
		return exampleDao;
	}

}
