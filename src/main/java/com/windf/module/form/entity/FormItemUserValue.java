package com.windf.module.form.entity;

import java.util.Date;

import com.windf.core.general.bean.AbstractBean;
import com.windf.module.user.entity.User;

public class FormItemUserValue extends AbstractBean {
	private static final long serialVersionUID = 60338476825966995L;

	private User user;
	private FormItem formItem;
	private String value;
	private Date createDate;
	private Date updateDate;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public FormItem getFormItem() {
		return formItem;
	}

	public void setFormItem(FormItem formItem) {
		this.formItem = formItem;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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
