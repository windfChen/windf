package com.windf.module.form.controler;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.windf.module.form.Constant;
import com.windf.plugins.web.BaseControler;

@Controller
@Scope("prototype")
@RequestMapping(value = FromControler.CONTROLER_PATH)
public class FromControler extends BaseControler{
	protected final static String CONTROLER_PATH = Constant.MODULE_WEB_PATH + "";

	@RequestMapping(value = "", method = {RequestMethod.GET})
	public String test() {
		return responseReturn.page(Constant.WEB_BASE_VIEW + "/index");
	}

	@RequestMapping(value = "/json")
	public String json() {
		return responseReturn.success(Constant.WEB_BASE_VIEW + "/index");
	}
}
