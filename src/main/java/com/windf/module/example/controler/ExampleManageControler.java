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
@RequestMapping(value = Constant.WEB_BASE_PATH)
public class ExampleManageControler extends ManagerGridControler {

	@ResponseBody
	@RequestMapping(value = "/test", method = {RequestMethod.GET})
	public Map<String, Object> test() {
		logger.info(module);
		return jsonReturn.successMap(getMapParameter("a"));
	}

	@Override
	protected String getModulePath() {
		return Constant.WEB_BASE_PATH;
	}
	
}
