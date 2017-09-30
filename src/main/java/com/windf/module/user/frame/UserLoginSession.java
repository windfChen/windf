package com.windf.module.user.frame;

import com.windf.core.frame.SessionContext;
import com.windf.core.spring.SpringUtil;
import com.windf.module.sso.SsoUserSession;
import com.windf.module.sso.modle.AbstractLoginObserver;
import com.windf.module.user.Constant;
import com.windf.module.user.entity.User;
import com.windf.module.user.service.UserService;

/**
 * 权限
 * @author chenyafeng
 *
 */
public class UserLoginSession extends AbstractLoginObserver {

	@Override
	public void login() {
		UserService userService = (UserService) SpringUtil.getBean("userService");
		
		User user = userService.getUserBySsoUserId(SsoUserSession.getCurrentUser().getId());
		SessionContext.set(Constant.SESSION_USER, user);
	}

	@Override
	public void logout() {
		
	}

}
