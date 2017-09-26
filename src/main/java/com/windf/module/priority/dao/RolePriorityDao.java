package com.windf.module.priority.dao;

import java.util.List;
import java.util.Map;

import com.windf.core.general.dao.CrudDao;

public interface RolePriorityDao extends CrudDao {
	/**
	 * 建立角色和权限的关系
	 * @param roleId 角色id
	 * @param priorityIds 权限id
	 */
	void insert(String roleId, List<String> priorityIds);

	/**
	 * 查询符合条件的其他权限
	 * @param roleId
	 * @param condition
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<Map<String, Object>> listOtherPriority(Map<String, Object> condition, Long start, Integer pageSize);

	/**
	 * 查询符合条件的其他权限的个数
	 * @param condition
	 * @return
	 */
	Long countOtherPriority(Map<String, Object> condition);

}
