package com.windf.module.priority.entity;

import com.windf.core.general.entity.AbstractBean;

public class PriorityRolePriority extends AbstractBean {
	private static final long serialVersionUID = 1661437607513229617L;

	private PriorityRole pePriRole;
	private Priority pePriority;
	private String flagIsvalid;

	public PriorityRole getPePriRole() {
		return pePriRole;
	}

	public void setPePriRole(PriorityRole pePriRole) {
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