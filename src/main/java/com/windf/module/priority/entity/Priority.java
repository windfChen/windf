package com.windf.module.priority.entity;

import com.windf.core.general.bean.AbstractBean;

/**
 * 权限
 * 
 * @author chenyafeng
 *
 */
public class Priority extends AbstractBean {

	private static final long serialVersionUID = 8417902924316536994L;

	private String id;
	private PriorityGroup priorityGroup;
	private String name;
	private String url;
	private String method; // 请求的方法：get、post

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PriorityGroup getPriorityGroup() {
		return priorityGroup;
	}

	public void setPriorityGroup(PriorityGroup priorityGroup) {
		this.priorityGroup = priorityGroup;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

}