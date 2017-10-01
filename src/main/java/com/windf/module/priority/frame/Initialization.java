package com.windf.module.priority.frame;

import com.windf.core.frame.Initializationable;
import com.windf.module.sso.modle.LoginSubject;

public class Initialization implements Initializationable{

	@Override
	public void init() {
		// 注册通知调用
		LoginSubject.getInstance().attach(new PriorityLoginSession());
	}

	@Override
	public int getOrder() {
		return NORMAL;
	}

}
