package com.windf.plugins.solr.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.windf.core.bean.Page;
import com.windf.core.exception.UserException;
import com.windf.plugins.solr.modle.SolrAPI;
import com.windf.plugins.solr.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService{
	
	@Resource
	private SolrAPI solrAPI;

	@Override
	public void add(Map<String, Object> document, String code) throws UserException {
		this.setCode(document, code);
		solrAPI.addDocument(document);
	}

	@Override
	public void addAll(List<Map<String, Object>> documents, String code) throws UserException {
		for (Map<String, Object> document : documents) {
			this.setCode(document, code);
		}
		solrAPI.addDocuments(documents);
	}

	@Override
	public Page<Map<String, Object>> search(Map<String, Object> condition, Map<String, String> orders, 
			Integer pageSize, Integer pageIndex, String code) throws UserException {
		this.setCode(condition, code);
		return solrAPI.search(condition, orders, pageSize, pageIndex);
	}

	@Override
	public void delelteById(String id, String code) throws UserException {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("id", id);
		deleteByCondition(condition, code);
	}

	@Override
	public void deleteByCondition(Map<String, Object> condition, String code) throws UserException {
		this.setCode(condition, code);
		solrAPI.deleteByCondition(condition);
	}

	@Override
	public void deleteAll(String code) throws UserException {
		Map<String, Object> condition = new HashMap<String, Object>();
		deleteByCondition(condition, code);
	}
	
	/**
	 * 为搜索设置code
	 * @param document
	 * @param code
	 */
	private void setCode(Map<String, Object> document, String code) {
		if (code != null) {
			if (document == null) {
				document = new HashMap<String, Object>();
			}
			
			document.put("code", code);
		}
	}

}
