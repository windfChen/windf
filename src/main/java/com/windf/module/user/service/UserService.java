package com.windf.module.user.service;

import com.windf.module.user.entity.User;
import com.windf.plugins.manage.service.ManageGirdService;

public interface UserService extends ManageGirdService{

	/**
	 * 根据ssoUser获得id
	 * @param id
	 * @return
	 */
	User getUserBySsoUserId(int id);
	
}
