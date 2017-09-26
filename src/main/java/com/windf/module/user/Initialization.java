package com.windf.module.user;

import com.windf.core.frame.init.Initializationable;
import com.windf.module.user.provider.UserLoginObserver;

public class Initialization implements Initializationable{

	@Override
	public boolean init() {
		// 过滤器注册
		new UserLoginObserver();
		
		return true;
	}

}
