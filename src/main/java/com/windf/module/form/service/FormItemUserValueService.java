package com.windf.module.form.service;

import java.util.List;

import com.windf.module.form.pojo.bean.FormItemUserValue;
import com.windf.module.form.pojo.vo.FormItemUserValueVO;

public interface FormItemUserValueService {

	/**
	 * 保存用户填写的表单信息
	 * @param vo
	 * @return
	 */
	List<FormItemUserValue> saveUserValue(FormItemUserValueVO vo);
	
	/**
	 * 获取用户的填写的信息
	 * @param formId
	 * @return
	 */
	List<FormItemUserValue> getUserValue(String formId);

}
