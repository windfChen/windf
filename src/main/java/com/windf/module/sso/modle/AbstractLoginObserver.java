package com.windf.module.sso.modle;

/**
 * 注册抽象类
 * @author chenyafeng
 *
 */
public abstract class AbstractLoginObserver implements LoginObserver {
	// 初始化时注册
	public AbstractLoginObserver() {
		LoginSubject.getInstance().attach(this);
	}
}
