package com.windf.module.form.controler;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.windf.module.form.Constant;
import com.windf.module.form.service.FormItemUserValueService;
import com.windf.plugins.web.BaseControler;

@Controller
@Scope("prototype")
@RequestMapping(value = FromControler.CONTROLER_PATH)
public class FromControler extends BaseControler{
	protected final static String CONTROLER_PATH = Constant.MODULE_WEB_PATH + "";

	@Resource
	private FormItemUserValueService formItemUserValueService;
	
	@RequestMapping(value = "", method = {RequestMethod.GET})
	public String test() {
		return responseReturn.page(Constant.WEB_BASE_VIEW + "/index");
	}

	@RequestMapping(value = "/saveUserValue", method = {RequestMethod.POST})
	public String saveUserValue() {
		String formId = paramenter.getString("formId");
		Map<String, Object> data = paramenter.getMap("data");
		
//		formItemUserValueService.saveUserValue();
		
		return responseReturn.success(Constant.WEB_BASE_VIEW + "/index");
	}
}
