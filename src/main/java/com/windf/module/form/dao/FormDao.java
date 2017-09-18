package com.windf.module.form.dao;

import com.windf.core.general.dao.GridDao;
import com.windf.module.form.entity.Form;

public interface FormDao extends  GridDao{

	/**
	 * 根据Id查询实体
	 * @param formId
	 * @return
	 */
	Form find(String formId);

}
