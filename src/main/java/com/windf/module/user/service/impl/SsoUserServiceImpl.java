package com.windf.module.user.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.windf.core.exception.UserException;
import com.windf.module.user.Constant;
import com.windf.module.user.dao.SsoUserDao;
import com.windf.module.user.entity.SsoUser;
import com.windf.module.user.service.SsoUserService;
import com.windf.plugins.web.WebContext;

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
		
		HttpSession session = WebContext.getSession();
		if (session != null) {
			session.setAttribute(Constant.SESSION_USER, ssoUserDB);
		}
		
		return ssoUserDB;
	}

	@Override
	public void logout() throws UserException {
		HttpSession session = WebContext.getSession();
		if (session != null) {
			session.invalidate();
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
