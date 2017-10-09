package com.windf.module.user.dao;

import com.windf.core.general.dao.CrudDao;
import com.windf.module.user.entity.User;

public interface UserDao extends CrudDao{

	User getUserBySsoUserId(int id);

}
