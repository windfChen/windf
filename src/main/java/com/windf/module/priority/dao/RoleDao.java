package com.windf.module.priority.dao;

import com.windf.core.general.dao.CrudDao;
import com.windf.module.priority.entity.Role;

public interface RoleDao  extends CrudDao{
	/**
	 * 根据用户id查询角色
	 * @return
	 */
	Role getByUserId();
}
