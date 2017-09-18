package com.windf.module.form.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.windf.module.form.dao.FormDao;
import com.windf.module.form.entity.Form;
import com.windf.module.form.service.FormService;

@Service
public class FormServiceImpl implements FormService {
	
	@Resource
	private FormDao formDao;

	@Override
	public Form getFormById(String formId) {
		return formDao.find(formId);
	}

}
