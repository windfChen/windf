package com.windf.plugins.manage.web.controler;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.windf.plugins.manage.Constant;

@Controller
@Scope("prototype")
@RequestMapping(value = Constant.WEB_BASE_PATH)
public class TestManageControler extends ManagerGridControler {

	@ResponseBody
	@RequestMapping(value = "/test", method = {RequestMethod.GET})
	public Map<String, Object> test() {
		
		return jsonReturn.successMap(getMapParameter("a"));
	}
	
}
