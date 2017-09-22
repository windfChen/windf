package com.windf.module.form.dao;

import com.windf.core.general.dao.CrudDao;
import com.windf.module.form.entity.Form;

public interface FormDao extends  CrudDao{

	/**
	 * 根据Id查询实体
	 * @param formId
	 * @return
	 */
	Form find(String formId);

}
