package com.sun.erpbackend.entity.user;

import java.io.Serializable;

public class UserRolePK implements Serializable {
	private static final long serialVersionUID = -1158141803682305656L;
	private Integer userId;
	private Integer roleId;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
}
