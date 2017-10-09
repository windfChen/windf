package com.windf.plugins.solr.entity;

import java.util.Date;

public class SearchBean {
	private String code;
	private boolean canPull;
	private Date lastUpdateDate;
	private Long count;
	private String type;
	private String address;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isCanPull() {
		return canPull;
	}

	public void setCanPull(boolean canPull) {
		this.canPull = canPull;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
