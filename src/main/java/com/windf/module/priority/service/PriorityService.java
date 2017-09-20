package com.windf.module.priority.service;

import java.util.List;

import com.windf.module.priority.entity.Priority;

public interface PriorityService {
	/**
	 * 查询用户的所有权限
	 * @param userId
	 * @return
	 */
	List<Priority> getPrioritiesByUser(String userId);
}
