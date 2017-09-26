package com.windf.module.priority.service;

import java.util.Map;

import com.windf.core.bean.Page;
import com.windf.plugins.manage.service.ManageGirdService;

public interface RolePriorityService extends ManageGirdService{

	/**
	 * 建立角色和权限的关系
	 * @param roleId 角色id
	 * @param priorityIds 权限ids，id用逗号隔开
	 */
	void save(String roleId, String priorityIds);

	/**
	 * 查询角色没有的权限
	 * @param condition
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Page<Map<String, Object>> listOtherPriority(Map<String, Object> condition, Integer pageNo, Integer pageSize);

}
