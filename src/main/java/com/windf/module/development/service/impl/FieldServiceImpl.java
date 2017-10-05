package com.windf.module.development.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.windf.core.bean.Page;
import com.windf.module.development.service.FieldService;

@Service
public class FieldServiceImpl extends BaseManageGridServiceImpl implements FieldService{

	@Override
	public Page<? extends Object> list(Map<String, Object> condition, Integer pageNo, Integer PageSize)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int save(Object bean) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object detail(Serializable id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Object bean) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(List<? extends Serializable> id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
