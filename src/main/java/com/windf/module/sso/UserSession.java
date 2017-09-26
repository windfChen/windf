package com.windf.module.sso;

import com.windf.core.exception.CodeException;
import com.windf.core.frame.session.SessionContext;
import com.windf.module.sso.entity.SsoUser;

public class UserSession {

	/**
	 * 获取当前会话的登录用户
	 * @return
	 * @throws CodeException 
	 */
	public static SsoUser getCurrentUser() throws CodeException {
		// TODO 获取当前登录用户
		return (SsoUser) SessionContext.get(Constant.SESSION_USER);
	}

	/**
	 * 是否已经登录过
	 * @return
	 * @throws CodeException 
	 */
	public static boolean isLogined() throws CodeException {
		return getCurrentUser() != null;
	}
	
}
