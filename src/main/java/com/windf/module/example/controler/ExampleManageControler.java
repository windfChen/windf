package com.windf.module.example.controler;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.windf.module.example.Constant;
import com.windf.plugins.manage.web.controler.ManagerGridControler;

@Controller
@Scope("prototype")
@RequestMapping(value = ExampleManageControler.CONTROLER_PATH)
public class ExampleManageControler extends ManagerGridControler {
	
	protected final static String CONTROLER_PATH = Constant.MODULE_WEB_PATH + "";

	@ResponseBody
	@RequestMapping(value = "/test", method = {RequestMethod.GET})
	public Map<String, Object> test() {;
		
		return jsonReturn.successMap("哈哈：" + getControlerPath());
	}

	
}
