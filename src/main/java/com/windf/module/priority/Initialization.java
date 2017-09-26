package com.windf.module.priority;

import com.windf.core.frame.init.Initializationable;
import com.windf.module.priority.filter.PriorityFilter;
import com.windf.module.priority.provider.PriorityLoginObserver;
import com.windf.plugins.web.filter.FilterControler;

public class Initialization implements Initializationable{

	@Override
	public boolean init() {
		// 观察者注册
		new PriorityLoginObserver();
		
		// 过滤器注册
		FilterControler.getInstance().addFilter(new PriorityFilter());
		
		return true;
	}

}
