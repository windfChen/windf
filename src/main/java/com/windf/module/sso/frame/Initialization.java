package com.windf.module.sso.frame;

import com.windf.core.frame.Initializationable;

public class Initialization implements Initializationable{

	@Override
	public void init() {
		System.out.println(33);
	}

	@Override
	public int getOrder() {
		return NORMAL;
	}

}
