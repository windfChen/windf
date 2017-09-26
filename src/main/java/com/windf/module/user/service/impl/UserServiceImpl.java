package com.windf.module.user.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.windf.core.general.dao.CrudDao;
import com.windf.core.general.dao.WritableDao;
import com.windf.module.sso.entity.SsoUser;
import com.windf.module.sso.service.SsoUserService;
import com.windf.module.user.dao.UserDao;
import com.windf.module.user.entity.User;
import com.windf.module.user.service.UserService;
import com.windf.plugins.manage.service.impl.ManagerGirdiServiceImpl;

@Service
public class UserServiceImpl extends ManagerGirdiServiceImpl implements UserService {
	
	@Resource
	private UserDao userDao;
	@Resource
	private SsoUserService ssoUserService;

	@Override
	public CrudDao getGridDao() {
		return userDao;
	}
	
	@Override
	public int save(Object bean) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		/*
		 * 先保存一个ssoUser
		 */
		SsoUser ssoUser = new SsoUser();
		ssoUser.setUsername((String) map.get("ssoUser.username"));
		ssoUser.setPhone((String) map.get("ssoUser.phone"));
		ssoUser.setEmail((String) map.get("ssoUser.email"));
		ssoUser.setLastLoginIp((String) map.get("ip"));
		int id = ssoUserService.addUser(ssoUser);
		ssoUser.setId(id);
		
		/**
		 * 保存用户
		 */
		map.put("ssoUser.id", id);
		WritableDao writableDao = this.getGridDao();
		return writableDao.insert(bean);
	}

	@Override
	public User getUserBySsoUserId(int id) {
		return userDao.getUserBySsoUserId(id);
	} 

}
 