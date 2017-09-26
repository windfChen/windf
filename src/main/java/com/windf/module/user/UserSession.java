package com.windf.module.user;

import com.windf.core.frame.session.SessionContext;
import com.windf.module.user.entity.User;

public class UserSession {

	/**
	 * 获取当前会话的登录用户
	 * @return
	 */
	public static User getCurrentUser() {
		// TODO 获取当前登录用户的用户信息
		return (User) SessionContext.get(Constant.SESSION_USER);
	}
	
}
