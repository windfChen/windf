package com.windf.module.priority.frame;

import com.windf.core.frame.Initializationable;
import com.windf.plugins.web.filter.FilterControler;

public class Initialization implements Initializationable{

	@Override
	public boolean init() {
		// 观察者注册
		new PriorityLoginSession();
		
		// 过滤器注册
		FilterControler.getInstance().addFilter(new PriorityFilter());
		
		return true;
	}

}
