package com.nsn.telkomsel.mics.hbm;

// Generated Mar 28, 2013 6:12:03 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * Loggedoffhuntingmember generated by hbm2java
 */
public class Loggedoffhuntingmember implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1047016913968556694L;
	private String loggedOfHuntingMemberKey;
	private Date loggedOfTimestamp;
	private String updateUser;

	public Loggedoffhuntingmember() {
	}

	public Loggedoffhuntingmember(String loggedOfHuntingMemberKey,
			Date loggedOfTimestamp, String updateUser) {
		this.loggedOfHuntingMemberKey = loggedOfHuntingMemberKey;
		this.loggedOfTimestamp = loggedOfTimestamp;
		this.updateUser = updateUser;
	}

	public String getLoggedOfHuntingMemberKey() {
		return this.loggedOfHuntingMemberKey;
	}

	public void setLoggedOfHuntingMemberKey(String loggedOfHuntingMemberKey) {
		this.loggedOfHuntingMemberKey = loggedOfHuntingMemberKey;
	}

	public Date getLoggedOfTimestamp() {
		return this.loggedOfTimestamp;
	}

	public void setLoggedOfTimestamp(Date loggedOfTimestamp) {
		this.loggedOfTimestamp = loggedOfTimestamp;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

}
