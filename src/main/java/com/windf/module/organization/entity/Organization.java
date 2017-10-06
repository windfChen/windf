package com.windf.module.organization.entity;

import com.windf.core.general.entity.TreeEntity;

public class Organization extends TreeEntity<Organization>{
	private static final long serialVersionUID = -595344647876115988L;

	private String note;

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
