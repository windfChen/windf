package com.windf.module.user.dao;

import com.windf.module.user.entity.SsoUser;

public interface SsoUserDao  {
	/**
	 * 插入用户
	 * id自增
	 * @param ssoUser
	 */
	Integer insert(SsoUser ssoUser);
	/**
	 * 修改用户电话信息
	 * @param ssoUser
	 */
	Integer updatePhone(SsoUser ssoUser);
	/**
	 * 修改登录信息 
	 * @param ssoUser
	 * @return
	 */
	Integer updateLogin(SsoUser ssoUser);
	/**
	 * 修改密码
	 * @param ssoUser
	 * @return
	 */
	Integer updatePassword(SsoUser ssoUser);
	/**
	 * 根据id删除用户
	 * @param id
	 */
	Integer deleteById(int id);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	SsoUser getById(int id);
	/**
	 * 根据用户名获得用户
	 * @param username
	 */
	SsoUser getByUsername(String username);
}
