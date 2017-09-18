package com.windf.module.form.service;

import java.util.List;
import java.util.Map;

import com.windf.module.form.entity.FormItemUserValue;

public interface FormItemUserValueService {

	/**
	 * 保存用户填写的表单信息
	 * @param vo
	 * @return
	 */
	List<FormItemUserValue> saveUserValue(String formId, Map<String, Object> data);
	
	/**
	 * 获取用户的填写的信息
	 * @param formId
	 * @return
	 */
	List<FormItemUserValue> getUserValue(String formId);

}
