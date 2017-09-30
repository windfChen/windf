package com.windf.module.user.frame;

import com.windf.core.frame.Initializationable;

public class Initialization implements Initializationable{

	@Override
	public boolean init() {
		// 过滤器注册
		new UserLoginSession();
		
		return true;
	}

}
