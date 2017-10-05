package com.windf.module.user.entity;

import com.windf.core.general.bean.BaseEntity;
import com.windf.module.organization.entity.Organization;
import com.windf.module.priority.entity.Role;
import com.windf.module.sso.entity.Sso;

public class User extends BaseEntity {
	private static final long serialVersionUID = 6026904399528051304L;
	
	private Sso ssoUser;
	private Role role;
	private Organization organization; // 所属机构
	private String sex; // 性别

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Sso getSsoUser() {
		return ssoUser;
	}

	public void setSsoUser(Sso ssoUser) {
		this.ssoUser = ssoUser;
	}

}
