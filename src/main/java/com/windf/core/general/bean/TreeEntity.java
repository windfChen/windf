package com.windf.core.general.bean;

/**
 * 树形结构
 * 
 * @author chenyafeng
 *
 * @param <T>
 */
public class TreeEntity<T> extends AbstractBean {
	private static final long serialVersionUID = -7400493087521518099L;

	private T parent; // 父级id
	private String parentIds; // 所有父级id
	private String name; // 名称
	private Integer sort; // 排序

	public T getParent() {
		return parent;
	}

	public void setParent(T parent) {
		this.parent = parent;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
