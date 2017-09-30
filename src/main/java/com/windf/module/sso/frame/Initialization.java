package com.windf.module.sso.frame;

import com.windf.core.frame.Initializationable;
import com.windf.plugins.web.filter.FilterControler;

public class Initialization implements Initializationable{

	@Override
	public boolean init() {
		// 过滤器注册
		FilterControler.getInstance().addFilter(new LoginFilter());
		
		return true;
	}

}
