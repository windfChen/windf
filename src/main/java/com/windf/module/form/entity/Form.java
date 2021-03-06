package com.windf.module.form.entity;

import java.util.Date;

import com.windf.core.general.entity.AbstractBean;

public class Form extends AbstractBean {
	private static final long serialVersionUID = 4935401778993342311L;

	private String name;
	private String code;
	private String note;
	// TODO 创建人
	private Date createDate;
	private Date updateDate;
	private String gridView;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getGridView() {
		return gridView;
	}

	public void setGridView(String gridView) {
		this.gridView = gridView;
	}
}
