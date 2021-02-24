package com.nsn.telkomsel.mics.orahbm;



import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author mulia
 * @version $Id: Micspermission.java,v 1.1.4.3 2019/03/22 10:06:19 cvsuser Exp $
 */
public class Micspermission implements java.io.Serializable {

	private static final long serialVersionUID = -3017067632956478014L;
	private BigDecimal micspermissionkey;
	private String micsmenukey;
	private String rolekey;
	private String updateuser;
	private Timestamp updatetimestamp;
	private BigDecimal accessflag;

	public Micspermission() {
	}

	public Micspermission(BigDecimal micspermissionkey, String micsmenukey,
			String rolekey, String updateuser, Timestamp updatetimestamp) {
		this.micspermissionkey = micspermissionkey;
		this.micsmenukey = micsmenukey;
		this.rolekey = rolekey;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
	}

	public Micspermission(BigDecimal micspermissionkey, String micsmenukey,
			String rolekey, String updateuser, Timestamp updatetimestamp,
			BigDecimal accessflag) {
		this.micspermissionkey = micspermissionkey;
		this.micsmenukey = micsmenukey;
		this.rolekey = rolekey;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
		this.accessflag = accessflag;
	}

	public BigDecimal getMicspermissionkey() {
		return this.micspermissionkey;
	}

	public void setMicspermissionkey(BigDecimal micspermissionkey) {
		this.micspermissionkey = micspermissionkey;
	}

	public String getMicsmenukey() {
		return this.micsmenukey;
	}

	public void setMicsmenukey(String micsmenukey) {
		this.micsmenukey = micsmenukey;
	}

	public String getRolekey() {
		return this.rolekey;
	}

	public void setRolekey(String rolekey) {
		this.rolekey = rolekey;
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

	public BigDecimal getAccessflag() {
		return this.accessflag;
	}

	public void setAccessflag(BigDecimal accessflag) {
		this.accessflag = accessflag;
	}

}
