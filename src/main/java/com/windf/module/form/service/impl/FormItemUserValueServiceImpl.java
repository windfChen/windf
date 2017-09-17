package com.windf.module.form.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.windf.core.util.CollectionUtil;
import com.windf.module.form.dao.FormItemDao;
import com.windf.module.form.dao.FormItemUserValueDao;
import com.windf.module.form.pojo.bean.FormItem;
import com.windf.module.form.pojo.bean.FormItemUserValue;
import com.windf.module.form.pojo.vo.FormItemUserValueVO;
import com.windf.module.form.service.FormItemUserValueService;

@Service
public class FormItemUserValueServiceImpl implements FormItemUserValueService{

	
	@Resource
	private FormItemDao formItemDao;
	@Resource
	private FormItemUserValueDao formItemUserValueDao;

	@Override
	public List<FormItemUserValue> saveUserValue(FormItemUserValueVO vo) {
		/*
		 * 查询实体项
		 */
		List<FormItem> formItems = formItemDao.getByFormId(vo.getFormId());
		
		/*
		 * 删除已经存在的
		 */
		formItemUserValueDao.deleteByUser(vo.getFormId(), "test");
		
		/*
		 * 批量设置数据，用于保存
		 */
		List<FormItemUserValue> formItemUserValues = new ArrayList<FormItemUserValue>();
		for (FormItem formItem : formItems) {
			String value = (String) vo.getData().get(formItem.getCode());
			
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
