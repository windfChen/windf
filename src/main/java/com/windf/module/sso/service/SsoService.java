package com.windf.module.sso.service;

import com.windf.core.exception.UserException;
import com.windf.module.sso.entity.Sso;

public interface SsoService {
	/**
	 * 新增用户
	 * 
	 * @param ssoUser
	 * @throws UserMessageException
	 */
	int addUser(Sso ssoUser) throws UserException;

	/**
	 * 用户登录
	 * 
	 * @param username
	 * @param password
	 * @param loginIp
	 * @return
	 * @throws UserMessageException
	 */
	Sso login(String username, String password, String loginIp) throws UserException;

	/**
	 * 退出登录
	 * 
	 * @throws UserMessageException
	 */
	void logout() throws UserException;

	/**
	 * 获得用户，如果没有用户新增
	 * 
	 * @return
	 * @throws UserMessageException
	 */
	Sso loginOrCreatUser(Sso ssoUser) throws UserException;
}
