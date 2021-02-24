package com.nsn.telkomsel.mics.orahbm;


import java.sql.Timestamp;

/**
 * @author mulia
 * @version $Id: Role.java,v 1.1.4.3 2019/03/22 10:06:27 cvsuser Exp $
 */
public class Role implements java.io.Serializable {


	private static final long serialVersionUID = -3413707894179909241L;
	private String rolekey;
	private String rolename;
	private String updateuser;
	private Timestamp updatetimestamp;

	public Role() {
	}

	public Role(String rolekey, String rolename, String updateuser,
			Timestamp updatetimestamp) {
		this.rolekey = rolekey;
		this.rolename = rolename;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
	}

	public String getRolekey() {
		return this.rolekey;
	}

	public void setRolekey(String rolekey) {
		this.rolekey = rolekey;
	}

	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getUpdateuser() {
		return this.updateuser;
	}

	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}

	public Timestamp getUpdatetimestamp() {
		return this.updatetimestamp;
	}

	public void setUpdatetimestamp(Timestamp updatetimestamp) {
		this.updatetimestamp = updatetimestamp;
	}

}
