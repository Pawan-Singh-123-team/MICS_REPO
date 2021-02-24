package com.nsn.telkomsel.mics.util;

public class DaySelection {
	private int id;
	private String dayName;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the dayName
	 */
	public String getDayName() {
		return dayName;
	}
	/**
	 * @param dayName the dayName to set
	 */
	public void setDayName(String dayName) {
		this.dayName = dayName;
	}
	
	public DaySelection(int id,String dayName){
		this.id = id;
		this.dayName = dayName;
	}
}
