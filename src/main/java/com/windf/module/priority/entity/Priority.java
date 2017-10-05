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

	private Group group;
	private String name;
	private String url;


	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
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

}