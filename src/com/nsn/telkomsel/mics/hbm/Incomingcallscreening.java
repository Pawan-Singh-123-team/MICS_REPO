package com.nsn.telkomsel.mics.hbm;

// Generated Mar 28, 2013 6:12:03 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * Incomingcallscreening generated by hbm2java
 */
public class Incomingcallscreening implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5881201910696111909L;
	private String ruleKey;
	private String callScreeningKey;
	private Integer orderIndex;
	private String timeBandKey;
	private String vlrprefix;
	private Integer callingPartyType;
	private String callingPartyNumber;
	private Boolean allow;
	private Integer rejectedAnnoId;
	private String updateUser;
	private Date updateTimestamp;

	public Incomingcallscreening() {
	}

	public Incomingcallscreening(String ruleKey, String updateUser) {
		this.ruleKey = ruleKey;
		this.updateUser = updateUser;
	}

	public Incomingcallscreening(String ruleKey, String callScreeningKey,
			Integer orderIndex, String timeBandKey, String vlrprefix,
			Integer callingPartyType, String callingPartyNumber, Boolean allow,
			Integer rejectedAnnoId, String updateUser, Date updateTimestamp) {
		this.ruleKey = ruleKey;
		this.callScreeningKey = callScreeningKey;
		this.orderIndex = orderIndex;
		this.timeBandKey = timeBandKey;
		this.vlrprefix = vlrprefix;
		this.callingPartyType = callingPartyType;
		this.callingPartyNumber = callingPartyNumber;
		this.allow = allow;
		this.rejectedAnnoId = rejectedAnnoId;
		this.updateUser = updateUser;
		this.updateTimestamp = updateTimestamp;
	}

	public String getRuleKey() {
		return this.ruleKey;
	}

	public void setRuleKey(String ruleKey) {
		this.ruleKey = ruleKey;
	}

	public String getCallScreeningKey() {
		return this.callScreeningKey;
	}

	public void setCallScreeningKey(String callScreeningKey) {
		this.callScreeningKey = callScreeningKey;
	}

	public Integer getOrderIndex() {
		return this.orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

	public String getTimeBandKey() {
		return this.timeBandKey;
	}

	public void setTimeBandKey(String timeBandKey) {
		this.timeBandKey = timeBandKey;
	}

	public String getVlrprefix() {
		return this.vlrprefix;
	}

	public void setVlrprefix(String vlrprefix) {
		this.vlrprefix = vlrprefix;
	}

	public Integer getCallingPartyType() {
		return this.callingPartyType;
	}

	public void setCallingPartyType(Integer callingPartyType) {
		this.callingPartyType = callingPartyType;
	}

	public String getCallingPartyNumber() {
		return this.callingPartyNumber;
	}

	public void setCallingPartyNumber(String callingPartyNumber) {
		this.callingPartyNumber = callingPartyNumber;
	}

	public Boolean getAllow() {
		return this.allow;
	}

	public void setAllow(Boolean allow) {
		this.allow = allow;
	}

	public Integer getRejectedAnnoId() {
		return this.rejectedAnnoId;
	}

	public void setRejectedAnnoId(Integer rejectedAnnoId) {
		this.rejectedAnnoId = rejectedAnnoId;
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
