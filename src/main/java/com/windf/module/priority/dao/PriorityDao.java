package com.windf.module.priority.dao;

import java.util.List;

import com.windf.core.general.dao.CrudDao;
import com.windf.module.priority.entity.Priority;

public interface PriorityDao extends CrudDao {
	/**
	 * 查询角色的所有权限
	 * @param userId
	 * @return
	 */
	List<Priority> getPrioritiesByRoleId(Integer roleId);

}
