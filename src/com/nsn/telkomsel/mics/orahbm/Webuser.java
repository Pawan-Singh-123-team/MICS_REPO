package com.nsn.telkomsel.mics.orahbm;


import java.sql.Timestamp;

/**
 * @author mulia
 * @version $Id: Webuser.java,v 1.1.4.3 2019/03/22 10:06:33 cvsuser Exp $
 */
public class Webuser implements java.io.Serializable {


	private static final long serialVersionUID = 2547380020850445642L;
	private String webuserkey;
	private String webusername;
	private String webuserpassword;
	private String ssotoken;
	private String companykey;
	private String rolekey;
	private String email;
	private String msisdn;
	private String updateuser;
	private Timestamp updatetimestamp;

	public Webuser() {
	}

	public Webuser(String webuserkey, String webusername,
			String webuserpassword, String companykey, String rolekey,
			String updateuser, Timestamp updatetimestamp) {
		this.webuserkey = webuserkey;
		this.webusername = webusername;
		this.webuserpassword = webuserpassword;
		this.companykey = companykey;
		this.rolekey = rolekey;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
	}

	public Webuser(String webuserkey, String webusername,
			String webuserpassword, String ssotoken, String companykey,
			String rolekey, String email, String updateuser,
			Timestamp updatetimestamp) {
		this.webuserkey = webuserkey;
		this.webusername = webusername;
		this.webuserpassword = webuserpassword;
		this.ssotoken = ssotoken;
		this.companykey = companykey;
		this.rolekey = rolekey;
		this.email = email;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
	}
	
	

	/**
	 * @param webuserkey
	 * @param webusername
	 * @param webuserpassword
	 * @param ssotoken
	 * @param companykey
	 * @param rolekey
	 * @param email
	 * @param msisdn
	 * @param updateuser
	 * @param updatetimestamp
	 */
	public Webuser(String webuserkey, String webusername,
			String webuserpassword, String ssotoken, String companykey,
			String rolekey, String email, String msisdn, String updateuser,
			Timestamp updatetimestamp) {
		super();
		this.webuserkey = webuserkey;
		this.webusername = webusername;
		this.webuserpassword = webuserpassword;
		this.ssotoken = ssotoken;
		this.companykey = companykey;
		this.rolekey = rolekey;
		this.email = email;
		this.msisdn = msisdn;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
	}

	public String getWebuserkey() {
		return this.webuserkey;
	}

	public void setWebuserkey(String webuserkey) {
		this.webuserkey = webuserkey;
	}

	public String getWebusername() {
		return this.webusername;
	}

	public void setWebusername(String webusername) {
		this.webusername = webusername;
	}

	public String getWebuserpassword() {
		return this.webuserpassword;
	}

	public void setWebuserpassword(String webuserpassword) {
		this.webuserpassword = webuserpassword;
	}

	public String getSsotoken() {
		return this.ssotoken;
	}

	public void setSsotoken(String ssotoken) {
		this.ssotoken = ssotoken;
	}

	public String getCompanykey() {
		return this.companykey;
	}

	public void setCompanykey(String companykey) {
		this.companykey = companykey;
	}

	public String getRolekey() {
		return this.rolekey;
	}

	public void setRolekey(String rolekey) {
		this.rolekey = rolekey;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

	/**
	 * @return the msisdn
	 */
	public String getMsisdn() {
		return msisdn;
	}

	/**
	 * @param msisdn the msisdn to set
	 */
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
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
