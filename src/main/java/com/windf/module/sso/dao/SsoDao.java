package com.windf.module.sso.dao;

import com.windf.module.sso.entity.Sso;

public interface SsoDao  {
	/**
	 * 插入用户
	 * id自增
	 * @param ssoUser
	 */
	Integer insert(Sso ssoUser);
	/**
	 * 修改用户电话信息
	 * @param ssoUser
	 */
	Integer updatePhone(Sso ssoUser);
	/**
	 * 修改登录信息 
	 * @param ssoUser
	 * @return
	 */
	Integer updateLogin(Sso ssoUser);
	/**
	 * 修改密码
	 * @param ssoUser
	 * @return
	 */
	Integer updatePassword(Sso ssoUser);
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
	Sso getById(int id);
	/**
	 * 根据用户名获得用户
	 * @param username
	 */
	Sso getByUsername(String username);
}
