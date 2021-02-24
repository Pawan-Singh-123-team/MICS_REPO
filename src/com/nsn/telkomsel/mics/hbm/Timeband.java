package com.nsn.telkomsel.mics.hbm;

// Generated Mar 28, 2013 6:12:03 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * Timeband generated by hbm2java
 */
public class Timeband implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2262344504297737973L;
	private String timeBandKey;
	private Integer daySelection;
	private Boolean allDay;
	private Integer beginHour;
	private Integer beginMinute;
	private Integer endHour;
	private Integer endMinute;
	private Boolean invertTimeSelection;
	private Boolean forever;
	private String effectiveStartDate;
	private String effectiveEndDate;
	private String updateUser;
	private Date updateTimestamp;

	public Timeband() {
	}

	public Timeband(String timeBandKey, String updateUser, Date updateTimestamp) {
		this.timeBandKey = timeBandKey;
		this.updateUser = updateUser;
		this.updateTimestamp = updateTimestamp;
	}

	public Timeband(String timeBandKey, Integer daySelection, Boolean allDay,
			Integer beginHour, Integer beginMinute, Integer endHour,
			Integer endMinute, Boolean invertTimeSelection, Boolean forever,
			String effectiveStartDate, String effectiveEndDate,
			String updateUser, Date updateTimestamp) {
		this.timeBandKey = timeBandKey;
		this.daySelection = daySelection;
		this.allDay = allDay;
		this.beginHour = beginHour;
		this.beginMinute = beginMinute;
		this.endHour = endHour;
		this.endMinute = endMinute;
		this.invertTimeSelection = invertTimeSelection;
		this.forever = forever;
		this.effectiveStartDate = effectiveStartDate;
		this.effectiveEndDate = effectiveEndDate;
		this.updateUser = updateUser;
		this.updateTimestamp = updateTimestamp;
	}

	public String getTimeBandKey() {
		return this.timeBandKey;
	}

	public void setTimeBandKey(String timeBandKey) {
		this.timeBandKey = timeBandKey;
	}

	public Integer getDaySelection() {
		return this.daySelection;
	}

	public void setDaySelection(Integer daySelection) {
		this.daySelection = daySelection;
	}

	public Boolean getAllDay() {
		return this.allDay;
	}

	public void setAllDay(Boolean allDay) {
		this.allDay = allDay;
	}

	public Integer getBeginHour() {
		return this.beginHour;
	}

	public void setBeginHour(Integer beginHour) {
		this.beginHour = beginHour;
	}

	public Integer getBeginMinute() {
		return this.beginMinute;
	}

	public void setBeginMinute(Integer beginMinute) {
		this.beginMinute = beginMinute;
	}

	public Integer getEndHour() {
		return this.endHour;
	}

	public void setEndHour(Integer endHour) {
		this.endHour = endHour;
	}

	public Integer getEndMinute() {
		return this.endMinute;
	}

	public void setEndMinute(Integer endMinute) {
		this.endMinute = endMinute;
	}

	public Boolean getInvertTimeSelection() {
		return this.invertTimeSelection;
	}

	public void setInvertTimeSelection(Boolean invertTimeSelection) {
		this.invertTimeSelection = invertTimeSelection;
	}

	public Boolean getForever() {
		return this.forever;
	}

	public void setForever(Boolean forever) {
		this.forever = forever;
	}

	public String getEffectiveStartDate() {
		return this.effectiveStartDate;
	}

	public void setEffectiveStartDate(String effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public String getEffectiveEndDate() {
		return this.effectiveEndDate;
	}

	public void setEffectiveEndDate(String effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
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
