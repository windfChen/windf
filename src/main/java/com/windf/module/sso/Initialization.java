package com.windf.module.sso;

import com.windf.core.frame.init.Initializationable;
import com.windf.module.sso.filter.LoginFilter;
import com.windf.plugins.web.filter.FilterControler;

public class Initialization implements Initializationable{

	@Override
	public boolean init() {
		// 过滤器注册
		FilterControler.getInstance().addFilter(new LoginFilter());
		
		return true;
	}

}
