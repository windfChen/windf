package com.windf.module.priority.entity;

import com.windf.core.general.bean.AbstractBean;

public class RolePriority extends AbstractBean {
	private static final long serialVersionUID = 1661437607513229617L;

	private Integer id;
	private Role pePriRole;
	private Priority pePriority;
	private String flagIsvalid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Role getPePriRole() {
		return pePriRole;
	}

	public void setPePriRole(Role pePriRole) {
		this.pePriRole = pePriRole;
	}

	public Priority getPePriority() {
		return pePriority;
	}

	public void setPePriority(Priority pePriority) {
		this.pePriority = pePriority;
	}

	public String getFlagIsvalid() {
		return flagIsvalid;
	}

	public void setFlagIsvalid(String flagIsvalid) {
		this.flagIsvalid = flagIsvalid;
	}
}