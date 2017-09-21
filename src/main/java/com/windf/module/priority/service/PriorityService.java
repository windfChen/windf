package com.windf.module.priority.service;

import java.util.List;

import com.windf.module.priority.entity.Priority;
import com.windf.plugins.manage.service.ManageGirdService;

public interface PriorityService extends ManageGirdService{
	/**
	 * 查询用户的所有权限
	 * @param userId
	 * @return
	 */
	List<Priority> getPrioritiesByUser(String userId);
}
