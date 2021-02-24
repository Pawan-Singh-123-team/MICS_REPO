package com.nsn.telkomsel.mics.hbm;

// Generated Mar 28, 2013 6:12:03 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * Company generated by hbm2java
 */
public class Company implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1568856418772421634L;
	private String companyKey;
	private String companyId;
	private String companyName;
	private String contactPerson;
	private String address;
	private String chargeString;
	private Boolean locked;
	private Boolean enableForceOnNet;
	private Boolean enableGroupHunting;
	private Boolean enablePrivateCall;
	private String companyCli;
	private String useMainCliprefix;
	private Integer clioption;
	private String forceIncomingCallScreening;
	private String forceOutgoingCallScreening;
	private String defaultIncomingCallScreening;
	private String defaultOutgoingCallScreening;
	private String updateUser;
	private Date updateTimestamp;

	public Company() {
	}

	public Company(String companyKey, String companyId, String updateUser,
			Date updateTimestamp) {
		this.companyKey = companyKey;
		this.companyId = companyId;
		this.updateUser = updateUser;
		this.updateTimestamp = updateTimestamp;
	}

	public Company(String companyKey, String companyId, String companyName,
			String contactPerson, String address, String chargeString,
			Boolean locked, Boolean enableForceOnNet,
			Boolean enableGroupHunting, Boolean enablePrivateCall,
			String companyCli, String useMainCliprefix, Integer clioption,
			String forceIncomingCallScreening,
			String forceOutgoingCallScreening,
			String defaultIncomingCallScreening,
			String defaultOutgoingCallScreening, String updateUser,
			Date updateTimestamp) {
		this.companyKey = companyKey;
		this.companyId = companyId;
		this.companyName = companyName;
		this.contactPerson = contactPerson;
		this.address = address;
		this.chargeString = chargeString;
		this.locked = locked;
		this.enableForceOnNet = enableForceOnNet;
		this.enableGroupHunting = enableGroupHunting;
		this.enablePrivateCall = enablePrivateCall;
		this.companyCli = companyCli;
		this.useMainCliprefix = useMainCliprefix;
		this.clioption = clioption;
		this.forceIncomingCallScreening = forceIncomingCallScreening;
		this.forceOutgoingCallScreening = forceOutgoingCallScreening;
		this.defaultIncomingCallScreening = defaultIncomingCallScreening;
		this.defaultOutgoingCallScreening = defaultOutgoingCallScreening;
		this.updateUser = updateUser;
		this.updateTimestamp = updateTimestamp;
	}

	public String getCompanyKey() {
		return this.companyKey;
	}

	public void setCompanyKey(String companyKey) {
		this.companyKey = companyKey;
	}

	public String getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getContactPerson() {
		return this.contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getChargeString() {
		return this.chargeString;
	}

	public void setChargeString(String chargeString) {
		this.chargeString = chargeString;
	}

	public Boolean getLocked() {
		return this.locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public Boolean getEnableForceOnNet() {
		return this.enableForceOnNet;
	}

	public void setEnableForceOnNet(Boolean enableForceOnNet) {
		this.enableForceOnNet = enableForceOnNet;
	}

	public Boolean getEnableGroupHunting() {
		return this.enableGroupHunting;
	}

	public void setEnableGroupHunting(Boolean enableGroupHunting) {
		this.enableGroupHunting = enableGroupHunting;
	}

	public Boolean getEnablePrivateCall() {
		return this.enablePrivateCall;
	}

	public void setEnablePrivateCall(Boolean enablePrivateCall) {
		this.enablePrivateCall = enablePrivateCall;
	}

	public String getCompanyCli() {
		return this.companyCli;
	}

	public void setCompanyCli(String companyCli) {
		this.companyCli = companyCli;
	}

	public String getUseMainCliprefix() {
		return this.useMainCliprefix;
	}

	public void setUseMainCliprefix(String useMainCliprefix) {
		this.useMainCliprefix = useMainCliprefix;
	}

	public Integer getClioption() {
		return this.clioption;
	}

	public void setClioption(Integer clioption) {
		this.clioption = clioption;
	}

	public String getForceIncomingCallScreening() {
		return this.forceIncomingCallScreening;
	}

	public void setForceIncomingCallScreening(String forceIncomingCallScreening) {
		this.forceIncomingCallScreening = forceIncomingCallScreening;
	}

	public String getForceOutgoingCallScreening() {
		return this.forceOutgoingCallScreening;
	}

	public void setForceOutgoingCallScreening(String forceOutgoingCallScreening) {
		this.forceOutgoingCallScreening = forceOutgoingCallScreening;
	}

	public String getDefaultIncomingCallScreening() {
		return this.defaultIncomingCallScreening;
	}

	public void setDefaultIncomingCallScreening(
			String defaultIncomingCallScreening) {
		this.defaultIncomingCallScreening = defaultIncomingCallScreening;
	}

	public String getDefaultOutgoingCallScreening() {
		return this.defaultOutgoingCallScreening;
	}

	public void setDefaultOutgoingCallScreening(
			String defaultOutgoingCallScreening) {
		this.defaultOutgoingCallScreening = defaultOutgoingCallScreening;
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