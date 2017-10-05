package com.windf.module.sso.entity;

import java.util.Date;

import com.windf.core.general.bean.AbstractBean;

public class Sso extends AbstractBean {
	private static final long serialVersionUID = 4132177381683804504L;
	
	private String username;
	private String turename;
	private String password;
	private String email;
	private String phone;
	private String lastLoginIp;
	private Date lastLoginTime;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTurename() {
		return turename;
	}
	public void setTurename(String turename) {
		this.turename = turename;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
}
