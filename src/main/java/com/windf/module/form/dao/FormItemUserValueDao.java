package com.windf.module.form.dao;

import java.util.List;

import com.windf.module.form.entity.FormItemUserValue;

public interface FormItemUserValueDao {

	/**
	 * 保存列表
	 * @param formItemUserValues
	 */
	void saveList(List<FormItemUserValue> formItemUserValues);

	/**
	 * 根据实体id获取用户填写的项信息
	 * @param formId
	 * @return
	 */
	List<FormItemUserValue> getByFormId(Integer formId, Integer userId);

	/**
	 * 根据用户Id和实体Id批量删除
	 * @param formId
	 * @param string
	 * @return
	 */
	int deleteByUser(Integer formId, Integer userId);

}
