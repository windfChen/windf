package com.windf.module.priority.frame;

import com.windf.core.frame.Initializationable;

public class Initialization implements Initializationable{

	@Override
	public void init() {
		// 观察者注册
		new PriorityLoginSession();
	}

	@Override
	public int getOrder() {
		return NORMAL;
	}

}
