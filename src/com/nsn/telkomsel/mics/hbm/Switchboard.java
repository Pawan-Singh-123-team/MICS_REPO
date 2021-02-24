package com.nsn.telkomsel.mics.hbm;

// Generated Mar 28, 2013 6:12:03 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * Switchboard generated by hbm2java
 */
public class Switchboard implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1794140125261628796L;
	private String switchboardKey;
	private String companyId;
	private String switchboardName;
	private String publicNumber;
	private String privateNumber;
	private Integer annoId;
	private String updateUser;
	private Date updateTimestamp;

	public Switchboard() {
	}

	public Switchboard(String switchboardKey, String companyId,
			String switchboardName, String updateUser, Date updateTimestamp) {
		this.switchboardKey = switchboardKey;
		this.companyId = companyId;
		this.switchboardName = switchboardName;
		this.updateUser = updateUser;
		this.updateTimestamp = updateTimestamp;
	}

	public Switchboard(String switchboardKey, String companyId,
			String switchboardName, String publicNumber, String privateNumber,
			Integer annoId, String updateUser, Date updateTimestamp) {
		this.switchboardKey = switchboardKey;
		this.companyId = companyId;
		this.switchboardName = switchboardName;
		this.publicNumber = publicNumber;
		this.privateNumber = privateNumber;
		this.annoId = annoId;
		this.updateUser = updateUser;
		this.updateTimestamp = updateTimestamp;
	}

	public String getSwitchboardKey() {
		return this.switchboardKey;
	}

	public void setSwitchboardKey(String switchboardKey) {
		this.switchboardKey = switchboardKey;
	}

	public String getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getSwitchboardName() {
		return this.switchboardName;
	}

	public void setSwitchboardName(String switchboardName) {
		this.switchboardName = switchboardName;
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

	public Integer getAnnoId() {
		return this.annoId;
	}

	public void setAnnoId(Integer annoId) {
		this.annoId = annoId;
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