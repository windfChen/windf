package com.windf.module.priority.entity;

import com.windf.core.general.entity.TreeEntity;

public class PriorityMenu extends TreeEntity<PriorityMenu> {
	private static final long serialVersionUID = -1971900433702039341L;

	private String url; // 地址
	private String code;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
