package com.windf.module.priority.entity;

import com.windf.core.general.entity.AbstractBean;

/**
 * PePriRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PriorityRole extends AbstractBean {

	private static final long serialVersionUID = -5206736284133401356L;

	private String code;
	private String name;

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