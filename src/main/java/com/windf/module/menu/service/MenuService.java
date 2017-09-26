package com.windf.module.menu.service;

import java.util.List;

import com.windf.core.general.service.CrudService;
import com.windf.module.menu.entity.vo.MenuVO;

public interface MenuService extends CrudService {

	/**
	 * 查询所有直接点，返回树的形式
	 * @param id
	 * @return
	 */
	List<MenuVO> findChildrenTree(Integer id);

}
