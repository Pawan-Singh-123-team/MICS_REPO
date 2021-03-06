package com.nsn.telkomsel.mics.hbm;

// Generated Mar 28, 2013 6:12:03 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * Switchboardsubmenu generated by hbm2java
 */
public class Switchboardsubmenu implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1128412006869325085L;
	private String switchboardSubMenuKey;
	private String switchboardMenuKey;
	private Integer dialDigit;
	private String description;
	private String destinationNumber;
	private String updateUser;
	private Date updateTimestamp;

	public Switchboardsubmenu() {
	}

	public Switchboardsubmenu(String switchboardSubMenuKey, String updateUser,
			Date updateTimestamp) {
		this.switchboardSubMenuKey = switchboardSubMenuKey;
		this.updateUser = updateUser;
		this.updateTimestamp = updateTimestamp;
	}

	public Switchboardsubmenu(String switchboardSubMenuKey,
			String switchboardMenuKey, Integer dialDigit, String description,
			String destinationNumber, String updateUser, Date updateTimestamp) {
		this.switchboardSubMenuKey = switchboardSubMenuKey;
		this.switchboardMenuKey = switchboardMenuKey;
		this.dialDigit = dialDigit;
		this.description = description;
		this.destinationNumber = destinationNumber;
		this.updateUser = updateUser;
		this.updateTimestamp = updateTimestamp;
	}

	public String getSwitchboardSubMenuKey() {
		return this.switchboardSubMenuKey;
	}

	public void setSwitchboardSubMenuKey(String switchboardSubMenuKey) {
		this.switchboardSubMenuKey = switchboardSubMenuKey;
	}

	public String getSwitchboardMenuKey() {
		return this.switchboardMenuKey;
	}

	public void setSwitchboardMenuKey(String switchboardMenuKey) {
		this.switchboardMenuKey = switchboardMenuKey;
	}

	public Integer getDialDigit() {
		return this.dialDigit;
	}

	public void setDialDigit(Integer dialDigit) {
		this.dialDigit = dialDigit;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDestinationNumber() {
		return this.destinationNumber;
	}

	public void setDestinationNumber(String destinationNumber) {
		this.destinationNumber = destinationNumber;
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
