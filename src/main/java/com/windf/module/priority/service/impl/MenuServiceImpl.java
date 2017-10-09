package com.windf.module.priority.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.windf.core.util.CollectionUtil;
import com.windf.module.priority.dao.MenuDao;
import com.windf.module.priority.entity.PriorityMenu;
import com.windf.module.priority.entity.vo.MenuVO;
import com.windf.module.priority.service.MenuService;

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
		List<PriorityMenu> menuList = menuDao.findChildren(id);
		
		/*
		 * 构建树,递归查询
		 */
		List<MenuVO> result = new ArrayList<MenuVO>();
		for (PriorityMenu m : menuList) {
			MenuVO menuVO = new MenuVO();
			menuVO.setId(m.getId());
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
