package com.windf.module.priority.service;

import java.util.List;

import com.windf.core.general.entity.NameBean;
import com.windf.plugins.manage.service.ManageGirdService;

public interface RoleService extends ManageGirdService{

	/**
	 * 查询的所有列表
	 * @return
	 */
	List<NameBean> getMyList();

}
