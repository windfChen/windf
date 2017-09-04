package com.windf.plugins.database;

import java.util.List;
import java.util.Map;

import com.windf.core.exception.DataAccessException;

public interface ListDao {
	/**
	 * 查询符合条件的总数
	 * @param condition
	 * @return
	 * @throws DataAccessException
	 */
	Long count(Map<String, Object> condition) throws DataAccessException;
	/**
	 * 查询符合条件的列表
	 * @param condition
	 * @param pageNo
	 * @param PageSize
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("rawtypes")
	List list(Map<String, Object> condition, Integer pageNo, Integer PageSize) throws DataAccessException;
}
