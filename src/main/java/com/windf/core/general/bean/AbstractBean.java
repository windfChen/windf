package com.windf.core.general.bean;

import java.io.Serializable;

public abstract class AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
