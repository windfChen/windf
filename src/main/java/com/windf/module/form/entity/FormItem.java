package com.windf.module.form.entity;

import java.util.Date;

import com.windf.core.general.entity.AbstractBean;

public class FormItem extends AbstractBean {
	private static final long serialVersionUID = -8776339158900342309L;

	private Form form;
	private String name;
	private String code;
	private String note;
	private Date createDate;
	private Date updateDate;

	public Form getForm() {
		return form;
	}

	public void setForm(Form form) {
		this.form = form;
	}

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

}
