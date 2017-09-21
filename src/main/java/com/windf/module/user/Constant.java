package com.windf.module.user;

import com.windf.module.user.entity.SsoUser;
import com.windf.plugins.web.WebContext;

public class Constant {
	public static final String WEB_BASE_VIEW = "/module/user/";
	
	/**
	 * 当前 session中当前用户的 key
	 */
	public static final String SESSION_USER = "current_session_user";	
	
	/**
	 * 获得session的当前用户
	 * @return
	 */
	public static SsoUser getCurrentUser() {
		return (SsoUser) WebContext.getSession().getAttribute(SESSION_USER);
	}

}