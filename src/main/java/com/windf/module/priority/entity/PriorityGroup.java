package com.windf.module.priority.entity;

import com.windf.core.general.entity.AbstractBean;

/**
 * 权限分组
 * 
 * @author chenyafeng
 *
 */
public class PriorityGroup extends AbstractBean {

	private static final long serialVersionUID = 4333038898160940125L;

	private PriorityGroup group;
	private String name;
	private Long serialNumber;

	public PriorityGroup getGroup() {
		return group;
	}

	public void setGroup(PriorityGroup group) {
		this.group = group;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Long serialNumber) {
		this.serialNumber = serialNumber;
	}

}