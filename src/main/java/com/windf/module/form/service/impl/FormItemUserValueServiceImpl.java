package com.windf.module.form.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.windf.core.util.CollectionUtil;
import com.windf.module.form.dao.FormItemDao;
import com.windf.module.form.dao.FormItemUserValueDao;
import com.windf.module.form.entity.FormItem;
import com.windf.module.form.entity.FormItemUserValue;
import com.windf.module.form.service.FormItemUserValueService;

@Service
public class FormItemUserValueServiceImpl implements FormItemUserValueService{

	
	@Resource
	private FormItemDao formItemDao;
	@Resource
	private FormItemUserValueDao formItemUserValueDao;

	@Override
	public List<FormItemUserValue> saveUserValue(String formId, Map<String, Object> data) {
		/*
		 * 查询实体项
		 */
		List<FormItem> formItems = formItemDao.getByFormId(formId);
		
		/*
		 * 删除已经存在的
		 */
		formItemUserValueDao.deleteByUser(formId, "test");
		
		/*
		 * 批量设置数据，用于保存
		 */
		List<FormItemUserValue> formItemUserValues = new ArrayList<FormItemUserValue>();
		for (FormItem formItem : formItems) {
			String value = (String) data.get(formItem.getCode());
			
			FormItemUserValue formItemUserValue = new FormItemUserValue();
			
			formItemUserValue = new FormItemUserValue();
			formItemUserValue.setFormItem(formItem);
			formItemUserValue.setUserId("test");
			formItemUserValue.setValue(value);
			
			formItemUserValues.add(formItemUserValue);
		}
		
		/*
		 * 保存
		 */
		if (CollectionUtil.isNotEmpty(formItemUserValues)) {
			formItemUserValueDao.saveList(formItemUserValues);
		}
		
		return formItemUserValues;
	}

	@Override
	public List<FormItemUserValue> getUserValue(String formId) {
		
		return formItemUserValueDao.getByFormId(formId);
	}

}
