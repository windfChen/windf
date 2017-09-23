package com.windf.module.user.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.windf.core.general.dao.CrudDao;
import com.windf.module.user.dao.UserDao;
import com.windf.module.user.service.UserService;
import com.windf.plugins.manage.service.impl.ManagerGirdiServiceImpl;

@Service
public class UserServiceImpl extends ManagerGirdiServiceImpl implements UserService {
	
	@Resource
	private UserDao userDao;

	@Override
	public CrudDao getGridDao() {
		return userDao;
	}

}
 