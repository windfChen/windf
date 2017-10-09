package com.windf.module.priority.controler;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.windf.module.priority.entity.vo.MenuVO;
import com.windf.module.priority.service.MenuService;
import com.windf.plugins.web.BaseControler;

@Controller
@Scope("prototype")
@RequestMapping(value = MenuControler.CONTROLER_PATH)
public class MenuControler extends BaseControler{
	protected final static String CONTROLER_PATH = "/menu";

	@Resource
	private MenuService menuService;
	
	/**
	 * 获得所有孩子
	 * @return
	 */
	@RequestMapping(value = "")
	public String getChildrenTree() {
		Integer id = paramenter.getInteger("id", 1);
		
		List<MenuVO> data = menuService.findChildrenTree(id);
		
		return responseReturn.returnData(data);
	}
}
