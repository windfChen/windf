package com.windf.module.user.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.windf.core.general.dao.CrudDao;
import com.windf.core.general.dao.WritableDao;
import com.windf.module.sso.service.SsoService;
import com.windf.module.user.dao.UserDao;
import com.windf.module.user.entity.User;
import com.windf.module.user.service.UserService;
import com.windf.plugins.manage.service.impl.ManagerGirdiServiceImpl;

@Service
public class UserServiceImpl extends ManagerGirdiServiceImpl implements UserService {
	
	@Resource
	private UserDao userDao;
	@Resource
	private SsoService ssoUserService;

	@Override
	public CrudDao getGridDao() {
		return userDao;
	}
	
	@Override
	public int save(Object bean) throws Exception {
		User user = (User) bean;
		/*
		 * 先保存一个ssoUser
		 */
		ssoUserService.addUser(user.getSsoUser());
		
		/**
		 * 保存用户
		 */
		WritableDao writableDao = this.getGridDao();
		return writableDao.insert(user);
	}

	@Override
	public User getUserBySsoUserId(int id) {
		return userDao.getUserBySsoUserId(id);
	} 

}
 