package com.windf.module.form.pojo.bean;

import java.util.Date;

import com.windf.core.general.bean.AbstractBean;

public class Form extends AbstractBean {
	private static final long serialVersionUID = 4935401778993342311L;
	
	private String id;
	private String name;
	private String code;
	private String note;
	// TODO 创建人
	private Date createDate;
	private Date updateDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
