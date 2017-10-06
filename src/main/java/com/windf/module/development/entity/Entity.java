package com.windf.module.development.entity;

import java.util.ArrayList;
import java.util.List;

public class Entity extends AbstractBaseCodeBean {
	private String tableName;
	private String comment;
	private List<Field> fields = new ArrayList<Field>();

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
