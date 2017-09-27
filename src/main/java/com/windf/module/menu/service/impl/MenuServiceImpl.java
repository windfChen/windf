package com.windf.module.menu.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.windf.core.util.CollectionUtil;
import com.windf.module.menu.dao.MenuDao;
import com.windf.module.menu.entity.Menu;
import com.windf.module.menu.entity.vo.MenuVO;
import com.windf.module.menu.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

	@Resource
	private MenuDao menuDao;
	
	@Override
	public List<MenuVO> findChildrenTree(Integer id) {
		return findChildrenSubTree(id);
	}
	
	/**
	 * 递归查询
	 * @param id
	 * @return
	 */
	private List<MenuVO> findChildrenSubTree(Integer id) {
		// 查询直接子节点
		List<Menu> menuList = menuDao.findChildren(id);
		
		/*
		 * 构建树,递归查询
		 */
		List<MenuVO> result = new ArrayList<MenuVO>();
		for (Menu m : menuList) {
			MenuVO menuVO = new MenuVO();
			menuVO.setId(m.getId().toString());
			menuVO.setCode(m.getCode());
			menuVO.setText(m.getName());
			menuVO.setSort(m.getSort());
			menuVO.setUrl(m.getUrl());
			
			List<MenuVO> menuVoList = findChildrenSubTree(m.getId());
			if (CollectionUtil.isEmpty(menuVoList)) {
				menuVO.setLeaf(true);
			} else {
				menuVO.setChildren(menuVoList);
				menuVO.setLeaf(false);
			}
			
			result.add(menuVO);
			
		}
		
		return result;
	}
	
}
