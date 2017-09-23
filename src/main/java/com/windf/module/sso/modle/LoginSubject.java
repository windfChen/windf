package com.windf.module.sso.modle;

import java.util.ArrayList;
import java.util.List;

/**
 * 登录通知
 * @author chenyafeng
 *
 */
public class LoginSubject {
	public static LoginSubject loginSubject = new LoginSubject(); // 单例

	/**
	 * 获取实例
	 */
	public static LoginSubject getInstance() {
		return loginSubject;
	}

	public List<LoginObserver> observers = new ArrayList<LoginObserver>(); // 注册过来的观察者

	private LoginSubject() {

	}
	
	/**
	 * 注册观察者
	 * @param observer
	 */
	public void attach(LoginObserver observer) {
		observers.add(observer);
	}

	/**
	 * 取消观察者
	 * @param observer
	 */
	public void detach(LoginObserver observer) {
		observers.remove(observer);
	}
	
	/**
	 * 登录触发
	 */
	public void login() {
		for (LoginObserver observer : observers) {
			observer.login();
		}
	}

	/**
	 * 退出触发
	 */
	public void loginOut() {
		for (LoginObserver observer : observers) {
			observer.logout();
		}
	}

}
