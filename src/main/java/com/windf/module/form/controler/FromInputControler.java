package com.windf.module.form.controler;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.windf.core.util.ParameterUtil;
import com.windf.module.form.Constant;
import com.windf.module.form.pojo.bean.Form;
import com.windf.module.form.pojo.bean.FormItemUserValue;
import com.windf.module.form.pojo.vo.FormItemUserValueVO;
import com.windf.module.form.service.FormItemUserValueService;
import com.windf.module.form.service.FormService;
import com.windf.plugins.web.BaseControler;

@Controller
@Scope("prototype")
@RequestMapping(value = FromInputControler.CONTROLER_PATH)
public class FromInputControler extends BaseControler{
	protected final static String CONTROLER_PATH = Constant.MODULE_WEB_PATH + "";

	@Resource
	private FormItemUserValueService formItemUserValueService;
	@Resource
	private FormService formService;

	@RequestMapping(value = "{formId}", method = {RequestMethod.GET})
	public String index(@PathVariable String formId) {
		Form form = formService.getFormById(formId);
		
		responseReturn.page(Constant.WEB_BASE_VIEW + "/index");
		return responseReturn.successData(form);
	}
	
	@RequestMapping(value = "/saveUserValue", method = {RequestMethod.POST})
	public String saveUserValue(FormItemUserValueVO vo) {
		if (ParameterUtil.hasEmpty(vo.getFormId(), vo.getData())) {
			return responseReturn.parameterError();
		}
		
		formItemUserValueService.saveUserValue(vo);
		
		return responseReturn.success();
	}

	@RequestMapping(value = "/user/value", method = {RequestMethod.GET})
	public String getUserValue(String formId) {
		if (ParameterUtil.hasEmpty(formId)) {
			return responseReturn.parameterError();
		}
		
		List<FormItemUserValue> data = formItemUserValueService.getUserValue(formId);
		
		return responseReturn.successData(data);
	}
}
