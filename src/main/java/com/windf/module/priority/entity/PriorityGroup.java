package com.windf.module.priority.entity;

import com.windf.core.general.bean.AbstractBean;

/**
 * 权限分组
 * 
 * @author chenyafeng
 *
 */
public class PriorityGroup extends AbstractBean {

	private static final long serialVersionUID = 4333038898160940125L;

	private PriorityGroup priorityGroup;
	private String name;
	private Long serialNumber;

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

	public Long getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Long serialNumber) {
		this.serialNumber = serialNumber;
	}

}