package com.windf.module.priority.entity;

import com.windf.module.user.entity.SsoUser;

public class User extends SsoUser {

	private static final long serialVersionUID = 4069922047286306128L;

	private Role role;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
