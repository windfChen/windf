package com.windf.module.user.frame;

import com.windf.core.frame.Initializationable;

public class Initialization implements Initializationable {

	@Override
	public void init() {
		// 过滤器注册
		new UserLoginSession();
	}

	@Override
	public int getOrder() {
		return NORMAL;
	}

}
