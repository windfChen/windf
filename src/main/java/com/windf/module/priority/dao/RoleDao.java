package com.windf.module.priority.dao;

import com.windf.core.general.dao.GridDao;
import com.windf.module.priority.entity.Role;

public interface RoleDao  extends GridDao{
	/**
	 * 根据用户id查询角色
	 * @return
	 */
	Role getByUserId();
}
