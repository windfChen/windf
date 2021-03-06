package com.windf.module.sso.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.windf.core.exception.UserException;
import com.windf.core.frame.SessionContext;
import com.windf.core.util.Encrypt;
import com.windf.core.util.StringUtil;
import com.windf.module.sso.Constant;
import com.windf.module.sso.dao.SsoUserDao;
import com.windf.module.sso.entity.SsoUser;
import com.windf.module.sso.modle.LoginSubject;
import com.windf.module.sso.service.SsoUserService;

@Service
public class SsoUserServiceImpl implements SsoUserService {

	@Resource
	private SsoUserDao ssoUserDao;

	@Override
	public int addUser(SsoUser ssoUser) throws UserException {
		
		/*
		 * 设置默认密码
		 */
		if(StringUtil.isEmpty(ssoUser.getPassword())) {
			ssoUser.setPassword(Encrypt.MD5(Constant.DEFAULT_USER_PASSWORD));
		}
		
		return ssoUserDao.insert(ssoUser);
	}

	@Override
	public SsoUser login(String username, String password, String loginIp) throws UserException {
		SsoUser ssoUserDB = ssoUserDao.getByUsername(username);
		
		if (ssoUserDB == null) {
			throw new UserException("用户不存在");
		} else if (!ssoUserDB.getPassword().equals(password)){
			throw new UserException("密码错误");
		}
		
		ssoUserDB.setLastLoginIp(loginIp);
		ssoUserDao.updateLogin(ssoUserDB);
		
		SessionContext.set(Constant.SESSION_SSO, ssoUserDB);
		// 登录通知
		LoginSubject.getInstance().login();
		
		return ssoUserDB;
	}

	@Override
	public void logout() throws UserException {
		// 退出通知
		LoginSubject.getInstance().loginOut();
		
		SessionContext.invalidate();
	}

	@Override
	public SsoUser loginOrCreatUser(SsoUser ssoUser) throws UserException {
		SsoUser result = null;
		// 如果用户不存在，则创建
		SsoUser ssoUserDB = ssoUserDao.getByUsername(ssoUser.getUsername());
		if (ssoUserDB == null) {
			this.addUser(ssoUser);
		}
		
		result = this.login(ssoUser.getUsername(), ssoUser.getPassword(), ssoUser.getLastLoginIp());
		return result;
		
	}
	
}
