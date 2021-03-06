package com.nsn.telkomsel.mics.hbm;

// Generated Mar 28, 2013 6:12:03 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * Huntinggroup generated by hbm2java
 */
public class Huntinggroup implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4035332563409984894L;
	private String huntingGroupKey;
	private String companyId;
	private String huntingGroupName;
	private String publicNumber;
	private String privateNumber;
	private Integer welcomeAnnoId;
	private Integer memberNotAvailableAnno;
	private Integer memberNotReachableAnno;
	private String huntingCliprefix;
	private Integer huntingOption;
	private Boolean reverseCharging;
	private Integer lastMemberOrderIndex;
	private String updateUser;
	private Date updateTimestamp;

	public Huntinggroup() {
	}

	public Huntinggroup(String huntingGroupKey, String companyId,
			String huntingGroupName, String updateUser, Date updateTimestamp) {
		this.huntingGroupKey = huntingGroupKey;
		this.companyId = companyId;
		this.huntingGroupName = huntingGroupName;
		this.updateUser = updateUser;
		this.updateTimestamp = updateTimestamp;
	}

	public Huntinggroup(String huntingGroupKey, String companyId,
			String huntingGroupName, String publicNumber, String privateNumber,
			Integer welcomeAnnoId, Integer memberNotAvailableAnno,
			Integer memberNotReachableAnno, String huntingCliprefix,
			Integer huntingOption, Integer lastMemberOrderIndex,
			String updateUser, Date updateTimestamp,Boolean reverseCharging) {
		this.huntingGroupKey = huntingGroupKey;
		this.companyId = companyId;
		this.huntingGroupName = huntingGroupName;
		this.publicNumber = publicNumber;
		this.privateNumber = privateNumber;
		this.welcomeAnnoId = welcomeAnnoId;
		this.memberNotAvailableAnno = memberNotAvailableAnno;
		this.memberNotReachableAnno = memberNotReachableAnno;
		this.huntingCliprefix = huntingCliprefix;
		this.huntingOption = huntingOption;
		this.lastMemberOrderIndex = lastMemberOrderIndex;
		this.updateUser = updateUser;
		this.updateTimestamp = updateTimestamp;
		this.reverseCharging= reverseCharging;;
	}

	public String getHuntingGroupKey() {
		return this.huntingGroupKey;
	}

	public void setHuntingGroupKey(String huntingGroupKey) {
		this.huntingGroupKey = huntingGroupKey;
	}

	public String getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getHuntingGroupName() {
		return this.huntingGroupName;
	}

	public void setHuntingGroupName(String huntingGroupName) {
		this.huntingGroupName = huntingGroupName;
	}

	public String getPublicNumber() {
		return this.publicNumber;
	}

	public void setPublicNumber(String publicNumber) {
		this.publicNumber = publicNumber;
	}

	public String getPrivateNumber() {
		return this.privateNumber;
	}

	public void setPrivateNumber(String privateNumber) {
		this.privateNumber = privateNumber;
	}

	public Integer getWelcomeAnnoId() {
		return this.welcomeAnnoId;
	}

	public void setWelcomeAnnoId(Integer welcomeAnnoId) {
		this.welcomeAnnoId = welcomeAnnoId;
	}

	public Integer getMemberNotAvailableAnno() {
		return this.memberNotAvailableAnno;
	}

	public void setMemberNotAvailableAnno(Integer memberNotAvailableAnno) {
		this.memberNotAvailableAnno = memberNotAvailableAnno;
	}

	public Integer getMemberNotReachableAnno() {
		return this.memberNotReachableAnno;
	}

	public void setMemberNotReachableAnno(Integer memberNotReachableAnno) {
		this.memberNotReachableAnno = memberNotReachableAnno;
	}

	public String getHuntingCliprefix() {
		return this.huntingCliprefix;
	}

	public void setHuntingCliprefix(String huntingCliprefix) {
		this.huntingCliprefix = huntingCliprefix;
	}

	public Integer getHuntingOption() {
		return this.huntingOption;
	}

	public void setHuntingOption(Integer huntingOption) {
		this.huntingOption = huntingOption;
	}

	public Integer getLastMemberOrderIndex() {
		return this.lastMemberOrderIndex;
	}

	public void setLastMemberOrderIndex(Integer lastMemberOrderIndex) {
		this.lastMemberOrderIndex = lastMemberOrderIndex;
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

	/**
	 * @return the reverseCharging
	 */
	public Boolean getReverseCharging() {
		return reverseCharging;
	}

	/**
	 * @param reverseCharging the reverseCharging to set
	 */
	public void setReverseCharging(Boolean reverseCharging) {
		this.reverseCharging = reverseCharging;
	}

}
