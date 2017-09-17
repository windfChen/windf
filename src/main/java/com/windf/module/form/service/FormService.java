package com.windf.module.form.service;

import com.windf.module.form.pojo.bean.Form;

public interface FormService {

	/**
	 * 获取表单信息
	 * @param formId
	 * @return
	 */
	Form getFormById(String formId);

}
