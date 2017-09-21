package com.windf.module.user.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.windf.core.exception.CodeException;
import com.windf.core.exception.UserException;
import com.windf.core.frame.session.SessionContext;
import com.windf.module.user.Constant;
import com.windf.module.user.dao.SsoUserDao;
import com.windf.module.user.entity.SsoUser;
import com.windf.module.user.modle.LoginSubject;
import com.windf.module.user.service.SsoUserService;

@Service
public class SsoUserServiceImpl implements SsoUserService {

	@Resource
	private SsoUserDao ssoUserAccess;

	@Override
	public void addUser(SsoUser ssoUser) throws UserException {
		ssoUserAccess.insert(ssoUser);
	}

	@Override
	public SsoUser login(String username, String password, String loginIp) throws UserException {
		SsoUser ssoUserDB = ssoUserAccess.getByUsername(username);
		
		if (ssoUserDB == null) {
			throw new UserException("用户不存在");
		} else if (!ssoUserDB.getPassword().equals(password)){
			throw new UserException("密码错误");
		}
		
		ssoUserDB.setLastLoginIp(loginIp);
		ssoUserAccess.updateLogin(ssoUserDB);
		
		try {
			SessionContext.set(Constant.SESSION_USER, ssoUserDB);
			// 登录通知
			LoginSubject.getInstance().login();
		} catch (CodeException e) {
			e.printStackTrace();
		}
		
		return ssoUserDB;
	}

	@Override
	public void logout() throws UserException {
		// 退出通知
		LoginSubject.getInstance().loginOut();
		
		try {
			SessionContext.invalidate();
		} catch (CodeException e) {
			e.printStackTrace();
		}
	}

	@Override
	public SsoUser loginOrCreatUser(SsoUser ssoUser) throws UserException {
		SsoUser result = null;
		// 如果用户不存在，则创建
		SsoUser ssoUserDB = ssoUserAccess.getByUsername(ssoUser.getUsername());
		if (ssoUserDB == null) {
			this.addUser(ssoUser);
		}
		
		result = this.login(ssoUser.getUsername(), ssoUser.getPassword(), ssoUser.getLastLoginIp());
		return result;
		
	}
	
}
