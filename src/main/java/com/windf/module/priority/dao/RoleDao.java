package com.windf.module.priority.dao;

import com.windf.core.general.dao.CrudDao;
import com.windf.core.general.dao.MyListDao;
import com.windf.module.priority.entity.PriorityRole;

public interface RoleDao  extends CrudDao, MyListDao {
	/**
	 * 根据用户id查询角色
	 * @return
	 */
	PriorityRole getByUserId();
}
