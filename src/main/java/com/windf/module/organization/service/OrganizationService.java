package com.windf.module.organization.service;

import java.util.List;

import com.windf.core.general.bean.NameBean;
import com.windf.plugins.manage.service.ManageGirdService;

public interface OrganizationService extends ManageGirdService{

	/**
	 * 查询的所有列表
	 * @return
	 */
	List<NameBean> getMyList();
}
