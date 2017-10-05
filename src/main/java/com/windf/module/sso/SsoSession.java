package com.windf.module.sso;

import com.windf.core.frame.SessionContext;
import com.windf.module.sso.entity.Sso;

public class SsoSession {

	/**
	 * 获取当前会话的登录用户
	 * @return
	 */
	public static Sso getCurrentUser() {
		// TODO 获取当前登录用户
		return (Sso) SessionContext.get(Constant.SESSION_SSO);
	}

	/**
	 * 是否已经登录过
	 * @return
	 */
	public static boolean isLogined() {
		return getCurrentUser() != null;
	}
	
}
