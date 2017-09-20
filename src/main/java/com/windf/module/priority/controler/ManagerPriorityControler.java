package com.windf.module.priority.controler;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.windf.module.priority.Constant;
import com.windf.plugins.manage.web.controler.ManagerGridControler;

@Controller
@Scope("prototype")
@RequestMapping(value = ManagerPriorityControler.CONTROLER_PATH)
public class ManagerPriorityControler extends ManagerGridControler {
	protected final static String CONTROLER_PATH = Constant.MODULE_WEB_PATH + "";

}
