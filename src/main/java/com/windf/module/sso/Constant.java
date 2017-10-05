package com.windf.module.sso;

import com.windf.module.sso.entity.Sso;
import com.windf.plugins.web.WebContext;

public class Constant {
	public static final String WEB_BASE_VIEW = "/module/sso/";
	public static final String DEFAULT_USER_PASSWORD = "1";
	
	/**
	 * 当前 session中当前用户的 key
	 */
	public static final String SESSION_SSO = "current_sso";	
	
	/**
	 * 获得session的当前用户
	 * @return
	 */
	public static Sso getCurrentUser() {
		return (Sso) WebContext.getSession().getAttribute(SESSION_SSO);
	}

}
