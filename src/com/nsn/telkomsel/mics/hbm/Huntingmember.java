package com.nsn.telkomsel.mics.hbm;

// Generated Mar 28, 2013 6:12:03 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * Huntingmember generated by hbm2java
 */
public class Huntingmember implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -559146920950683289L;
	private String huntingMemberKey;
	private String huntingScheduleKey;
	private String targetNumber;
	private Integer orderIndex;
	private String lastAnswer;
	private String updateUser;
	private Date updateTimestamp;

	public Huntingmember() {
	}

	public Huntingmember(String huntingMemberKey, String huntingScheduleKey,
			String updateUser, Date updateTimestamp) {
		this.huntingMemberKey = huntingMemberKey;
		this.huntingScheduleKey = huntingScheduleKey;
		this.updateUser = updateUser;
		this.updateTimestamp = updateTimestamp;
	}

	public Huntingmember(String huntingMemberKey, String huntingScheduleKey,
			String targetNumber, Integer orderIndex, String lastAnswer,
			String updateUser, Date updateTimestamp) {
		this.huntingMemberKey = huntingMemberKey;
		this.huntingScheduleKey = huntingScheduleKey;
		this.targetNumber = targetNumber;
		this.orderIndex = orderIndex;
		this.lastAnswer = lastAnswer;
		this.updateUser = updateUser;
		this.updateTimestamp = updateTimestamp;
	}

	public String getHuntingMemberKey() {
		return this.huntingMemberKey;
	}

	public void setHuntingMemberKey(String huntingMemberKey) {
		this.huntingMemberKey = huntingMemberKey;
	}

	public String getHuntingScheduleKey() {
		return this.huntingScheduleKey;
	}

	public void setHuntingScheduleKey(String huntingScheduleKey) {
		this.huntingScheduleKey = huntingScheduleKey;
	}

	public String getTargetNumber() {
		return this.targetNumber;
	}

	public void setTargetNumber(String targetNumber) {
		this.targetNumber = targetNumber;
	}

	public Integer getOrderIndex() {
		return this.orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

	public String getLastAnswer() {
		return this.lastAnswer;
	}

	public void setLastAnswer(String lastAnswer) {
		this.lastAnswer = lastAnswer;
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
