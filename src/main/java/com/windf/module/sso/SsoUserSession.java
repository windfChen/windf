package com.windf.module.sso;

import com.windf.core.frame.SessionContext;
import com.windf.module.sso.entity.SsoUser;

public class SsoUserSession {

	/**
	 * 获取当前会话的登录用户
	 * @return
	 */
	public static SsoUser getCurrentUser() {
		// TODO 获取当前登录用户
		return (SsoUser) SessionContext.get(Constant.SESSION_SSO);
	}

	/**
	 * 是否已经登录过
	 * @return
	 */
	public static boolean isLogined() {
		return getCurrentUser() != null;
	}
	
}
