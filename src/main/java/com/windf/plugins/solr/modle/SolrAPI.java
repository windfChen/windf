package com.windf.plugins.solr.modle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Component;

import com.windf.core.bean.Page;
import com.windf.core.exception.CodeException;
import com.windf.core.util.PropertiesUtil;
import com.windf.core.util.TextUtil;
import com.windf.plugins.solr.Constant;

/**
 * 搜索工具类
 * 
 * @author huze
 *
 */
@Component
public class SolrAPI {

	Logger logger = Logger.getLogger(SolrAPI.class);

	/**
	 * 添加索引
	 * 
	 * @param map
	 * @return
	 */
	public Boolean addDocument(Map<String, Object> map) {
		Boolean success = false;
		if (null == map || map.size() == 0) {
			return success;
		}
		try {
			SolrClient client = getClient();
			SolrInputDocument doc = createDocument(map);
			client.add(doc);
			client.commit();
			close(client);
		} catch (SolrServerException e) {
			logger.error("Failed to add index.", e);
		} catch (IOException e) {
			logger.error("Failed to add index.", e);
		}
		return success;
	}

	/**
	 * 删除索引
	 * 
	 * @param ids
	 * @return
	 */
	public Boolean deleteByIds(List<String> ids) {
		Boolean resultValue = false;
		try {
			SolrClient client = getClient();
			client.deleteById(ids);
			resultValue = true;
			client.commit();
			close(client);
		} catch (SolrServerException e) {
			logger.error("Failed to add index.", e);
		} catch (IOException e) {
			logger.error("Failed to add index.", e);
		}
		return resultValue;
	}

	/**
	 * 清除所有索引
	 * 
	 * @return
	 */
	public boolean deleteByCondition(Map<String, Object> condition) {
		boolean resultValue = false;
		try {
			SolrClient client = getClient();
			client.deleteByQuery("*:*");
			resultValue = true;
			client.commit();
			close(client);
		} catch (SolrServerException e) {
			logger.error("Failed to add index.", e);
		} catch (IOException e) {
			logger.error("Failed to add index.", e);
		}
		return resultValue;
	}

	/**
	 * 按照关键字获取搜索结果
	 * 
	 * @param platform
	 * @param resourceType
	 * @param keyword
	 * @param condition
	 * @param pageSize
	 *            页大小
	 * @param pageIndex
	 *            页码（从0开始）
	 * @return
	 */
	public Page<Map<String, Object>> search(Map<String, Object> condition, Map<String, String> orders, Integer pageSize,
			Integer pageIndex) {

		if (pageSize == null) {
			pageIndex = 0;
		} else {
			pageIndex = pageIndex - 1;
		}

		Page<Map<String, Object>> results = new Page<Map<String, Object>>();
		List<Map<String, Object>> searchResults = new ArrayList<Map<String, Object>>();
		try {
			SolrClient client = getClient();

			/*
			 * 搜索字段
			 */
			StringBuilder queryStr = new StringBuilder();
			if (condition != null) {
				Iterator<String> iterator = condition.keySet().iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					Object value = condition.get(key);

					if (StringUtils.isNotBlank(key) && value != null && StringUtils.isNotBlank(value.toString())) {
						// 简单表达式解析
						if (key.contains("__")) { // __表示或操作
							String[] keys = key.split("__");
							queryStr.append(" AND (");
							for (int i = 0; i < keys.length; i++) {
								if (i != 0) {
									queryStr.append(" OR ");
								}
								queryStr.append(keys[i] + ":" + value + "");
							}
							queryStr.append(")");
						} else {
							queryStr.append(" AND " + key + ":" + value + "");
						}
					}
				}

			}
			if (queryStr.length() == 0) {
				queryStr.append("*:*");
			}

			/*
			 * 排序字段
			 */
			List<SortClause> sortClauses = new ArrayList<SortClause>();
			if (orders != null) {
				Iterator<String> iterator = orders.keySet().iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					String value = orders.get(key);

					SortClause s = new SortClause(key, ORDER.valueOf(value));
					sortClauses.add(s);
				}
			}

			SolrQuery query = new SolrQuery();
			query.setQuery(queryStr.toString());
			query.setRows(pageSize);
			query.setStart(pageIndex * pageSize);
			query.setSorts(sortClauses);
			QueryResponse response = client.query(query);
			SolrDocumentList docs = response.getResults();
			results.setTotal(docs.getNumFound());
			for (SolrDocument solrDocument : docs) {
				searchResults.add(solrDocument.getFieldValueMap());
			}
			close(client);
		} catch (SolrServerException e) {
			logger.error("Failed to add index.", e);
		} catch (IOException e) {
			logger.error("Failed to add index.", e);
		}
		results.setData(searchResults);
		results.setPageIndex(Long.parseLong(pageIndex.toString()));
		results.setPageSize(pageSize);
		return results;
	}

	/**
	 * 批量添加索引
	 * 
	 * @param map
	 * @return
	 */
	public Boolean addDocuments(List<Map<String, Object>> maps) {
		Boolean success = false;
		if (null == maps || maps.size() == 0) {
			return success;
		}
		try {
			SolrClient client = getClient();
			for (Map<String, Object> map : maps) {
				SolrInputDocument doc = this.createDocument(map);
				try {
					client.add(doc);
				} catch (Throwable t) {
					logger.error("Failed to add index.", t);
				}
			}

			client.commit();
			close(client);
		} catch (SolrServerException e) {
			logger.error("Failed to add index.", e);
		} catch (IOException e) {
			logger.error("Failed to add index.", e);
		}
		return success;
	}

	/**
	 * 获得solr连接，如果没有
	 * 
	 * @return
	 */
	private SolrClient getClient() {
		String zkNodes = this.getZkNodes();
		String collectionName = this.getCollectionName();
		String solrUrl = this.getSolrUrl();
		
		if ((StringUtils.isEmpty(zkNodes) || StringUtils.isEmpty(collectionName)) && StringUtils.isEmpty(solrUrl)) {
			throw new CodeException("Failed to initial the CloudSolrClient or SolrClient");
		}

		SolrClient client = null;
		if (StringUtils.isNotEmpty(solrUrl)) {
			client = new HttpSolrClient(solrUrl);
		} else {
			@SuppressWarnings("resource")
			CloudSolrClient cloudSolrClient = new CloudSolrClient(zkNodes);
			cloudSolrClient.setDefaultCollection(collectionName);
			client = cloudSolrClient;
		}

		return client;
	}

	/**
	 * 销毁连接
	 */
	private void close(SolrClient client) {
		if (null != client) {
			try {
				client.close();
			} catch (IOException e) {
				logger.error("close SolrClient failed", e);
			}
		}
	}

	/**
	 * 添加Document的封装方法
	 * 
	 * @param client
	 * @param map
	 */
	private SolrInputDocument createDocument(Map<String, Object> map) {
		if (null == map || map.size() == 0) {
			return null;
		}
		SolrInputDocument doc = new SolrInputDocument();
		for (Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() instanceof String) {
				doc.addField(entry.getKey(), TextUtil.filterHtml(entry.getValue().toString()));
			} else {
				doc.addField(entry.getKey(), entry.getValue());
			}
		}

		return doc;
	}

	private String getZkNodes() {
		return PropertiesUtil.get(Constant.PROPERTIES_FILE_PATH, "zkNodes");
	}

	private String getCollectionName() {
		return PropertiesUtil.get(Constant.PROPERTIES_FILE_PATH, "collectionName");
	}

	private String getSolrUrl() {
		return PropertiesUtil.get(Constant.PROPERTIES_FILE_PATH, "solrUrl");
	}

}
