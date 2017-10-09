package com.windf.module.priority.service;

import java.util.List;

import com.windf.core.general.service.CrudService;
import com.windf.module.priority.entity.vo.MenuVO;

public interface MenuService extends CrudService {

	/**
	 * 查询所有直接点，返回树的形式
	 * @param id
	 * @return
	 */
	List<MenuVO> findChildrenTree(Integer id);

}
