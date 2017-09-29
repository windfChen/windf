package com.windf.module.form.dao;

import java.util.List;

import com.windf.module.form.entity.FormItem;

public interface FormItemDao {

	/**
	 * 根据实体Id查询旗下的实体项
	 * @param formId
	 * @return
	 */
	public List<FormItem> getByFormId(Integer formId);
}
