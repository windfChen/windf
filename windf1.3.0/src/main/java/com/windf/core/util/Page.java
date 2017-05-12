package com.windf.core.util;

import java.io.Serializable;
import java.util.List;
/**
 * 分页
 */
public class Page<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * 搜索内容
	 */
	private List<T> results;
	
	/*
	 * 总计
	 */
	private Long total ;
	
	/*
	 * 页码
	 */
	private Integer pageIndex ;
	
	/*
	 * 页大小
	 */
	private Integer pageSize ;

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
}
