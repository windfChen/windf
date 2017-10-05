package com.windf.module.development.frame;

import com.windf.core.frame.Initializationable;
import com.windf.module.development.modle.JavaInitialization;
import com.windf.module.development.modle.SqlInitialization;

public class Initialization implements Initializationable{
	
	@Override
	public void init() {
		/*
		 * 初始化java文件
		 */
		new JavaInitialization();
		
		new SqlInitialization();
	}

	@Override
	public int getOrder() {
		return NORMAL;
	}

	

}
