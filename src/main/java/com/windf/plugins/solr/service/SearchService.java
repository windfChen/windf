package com.windf.plugins.solr.service;

import java.util.List;
import java.util.Map;

import com.windf.core.bean.Page;
import com.windf.core.exception.UserException;

public interface SearchService {
	/**
	 * 添加或者修改数据
	 * 修改时，以id作为修改依据
	 * @param document
	 * @param code 编号，用于分割业务
	 * @throws UserException
	 */
	void add(Map<String, Object> document, String code) throws UserException;
	/**
	 * 批量添加或者修改数据
	 * 修改时，以id作为修改依据
	 * @param documents
	 * @param code 编号，用于分割业务
	 * @throws UserException
	 */
	void addAll(List<Map<String, Object>> documents, String code) throws UserException;
	/**
	 * 搜索
	 * @param condition 条件，单个小条件之间为and关系；单个小条件支持OR，用__分割
	 * @param orders 排序，key为排序字段，value为正(asc)、逆(desc)序
	 * @param pageSize
	 * @param pageIndex
	 * @param code 编号，用于分割业务
	 * @throws UserException
	 */
	Page<Map<String, Object>> search(Map<String, Object> condition, Map<String, String> orders, Integer pageSize, Integer pageIndex, String code) throws UserException;
	/**
	 * 根据id删除
	 * @param id
	 * @param code 编号，用于分割业务
	 * @throws UserException
	 */
	void delelteById(String id, String code) throws UserException;
	/**
	 * 根据条件删除
	 * @param condition
	 * @param code 编号，用于分割业务
	 * @throws UserException
	 */
	void deleteByCondition(Map<String, Object> condition, String code) throws UserException;
	/**
	 * 删除全部
	 * @param code 编号，用于分割业务
	 * @throws UserException
	 */
	void deleteAll(String code) throws UserException;
}
