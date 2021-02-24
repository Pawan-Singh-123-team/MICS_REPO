package com.nsn.telkomsel.mics.hbm;

// Generated Mar 28, 2013 6:12:03 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * Outgoingcallscreening generated by hbm2java
 */
public class Outgoingcallscreening implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5206733912694551178L;
	private String ruleKey;
	private String callScreeningKey;
	private Integer orderIndex;
	private String timeBandKey;
	private String vlrprefix;
	private String globalCiprefix;
	private Integer calledPartyType;
	private String calledPartyNumber;
	private Boolean allow;
	private Integer rejectionAnnoId;
	private String updateUser;
	private Date updateTimestamp;

	public Outgoingcallscreening() {
	}

	public Outgoingcallscreening(String ruleKey, String updateUser,
			Date updateTimestamp) {
		this.ruleKey = ruleKey;
		this.updateUser = updateUser;
		this.updateTimestamp = updateTimestamp;
	}

	public Outgoingcallscreening(String ruleKey, String callScreeningKey,
			Integer orderIndex, String timeBandKey, String vlrprefix,
			String globalCiprefix, Integer calledPartyType,
			String calledPartyNumber, Boolean allow, Integer rejectionAnnoId,
			String updateUser, Date updateTimestamp) {
		this.ruleKey = ruleKey;
		this.callScreeningKey = callScreeningKey;
		this.orderIndex = orderIndex;
		this.timeBandKey = timeBandKey;
		this.vlrprefix = vlrprefix;
		this.globalCiprefix = globalCiprefix;
		this.calledPartyType = calledPartyType;
		this.calledPartyNumber = calledPartyNumber;
		this.allow = allow;
		this.rejectionAnnoId = rejectionAnnoId;
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

	public String getGlobalCiprefix() {
		return this.globalCiprefix;
	}

	public void setGlobalCiprefix(String globalCiprefix) {
		this.globalCiprefix = globalCiprefix;
	}

	public Integer getCalledPartyType() {
		return this.calledPartyType;
	}

	public void setCalledPartyType(Integer calledPartyType) {
		this.calledPartyType = calledPartyType;
	}

	public String getCalledPartyNumber() {
		return this.calledPartyNumber;
	}

	public void setCalledPartyNumber(String calledPartyNumber) {
		this.calledPartyNumber = calledPartyNumber;
	}

	public Boolean getAllow() {
		return this.allow;
	}

	public void setAllow(Boolean allow) {
		this.allow = allow;
	}

	public Integer getRejectionAnnoId() {
		return this.rejectionAnnoId;
	}

	public void setRejectionAnnoId(Integer rejectionAnnoId) {
		this.rejectionAnnoId = rejectionAnnoId;
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