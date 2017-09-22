package com.windf.module.menu.controler;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.windf.core.util.ParameterUtil;
import com.windf.module.menu.Constant;
import com.windf.module.menu.entity.vo.MenuVO;
import com.windf.module.menu.service.MenuService;
import com.windf.plugins.web.BaseControler;

@Controller
@Scope("prototype")
@RequestMapping(value = MenuViewControler.CONTROLER_PATH)
public class MenuViewControler extends BaseControler{
	protected final static String CONTROLER_PATH = Constant.MODULE_WEB_PATH + "";

	@Resource
	private MenuService menuService;
	
	/**
	 * 获得所有孩子
	 * @return
	 */
	@RequestMapping(value = "/children/g")
	public String getChildrenTree() {
		Integer id = paramenter.getInteger("id");
		if (ParameterUtil.hasEmpty(id)) {
			return responseReturn.parameterError();
		}
		
		List<MenuVO> data = menuService.findChildrenTree(id);
		
		return responseReturn.returnData(data);
	}
}
