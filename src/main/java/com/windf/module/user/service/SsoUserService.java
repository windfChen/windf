package com.windf.module.user.service;

import com.windf.core.exception.UserException;
import com.windf.module.user.entity.SsoUser;

public interface SsoUserService {
	/**
	 * 新增用户
	 * @param ssoUser
	 * @throws UserMessageException
	 */
	void addUser(SsoUser ssoUser) throws UserException;
	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @param loginIp
	 * @return
	 * @throws UserMessageException
	 */
	SsoUser login(String username, String password, String loginIp) throws UserException;
	
	/**
	 * 退出登录
	 * @throws UserMessageException
	 */
	void logout() throws UserException;
	
	/**
	 * 获得用户，如果没有用户新增
	 * @return
	 * @throws UserMessageException
	 */
	SsoUser loginOrCreatUser(SsoUser ssoUser) throws UserException;
}
