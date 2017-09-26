package com.windf.module.priority.entity;

import com.windf.core.general.bean.AbstractBean;

/**
 * PePriRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Role extends AbstractBean {

	private static final long serialVersionUID = -5206736284133401356L;
	
	private String id;
	private String code;
	private String name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}