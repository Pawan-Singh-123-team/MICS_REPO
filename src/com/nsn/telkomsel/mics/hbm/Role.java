package com.nsn.telkomsel.mics.hbm;

// Generated Mar 28, 2013 6:12:03 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * Role generated by hbm2java
 */
public class Role implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6518039074212215107L;
	private int roleKey;
	private String roleName;
	private String updateUser;
	private Date updateTimestamp;

	public Role() {
	}

	public Role(int roleKey, String updateUser, Date updateTimestamp) {
		this.roleKey = roleKey;
		this.updateUser = updateUser;
		this.updateTimestamp = updateTimestamp;
	}

	public Role(int roleKey, String roleName, String updateUser,
			Date updateTimestamp) {
		this.roleKey = roleKey;
		this.roleName = roleName;
		this.updateUser = updateUser;
		this.updateTimestamp = updateTimestamp;
	}

	public int getRoleKey() {
		return this.roleKey;
	}

	public void setRoleKey(int roleKey) {
		this.roleKey = roleKey;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTimestamp() {
		return this.updateTimestamp;
	}

	public void setUpdateTimestamp(Date updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}

}
