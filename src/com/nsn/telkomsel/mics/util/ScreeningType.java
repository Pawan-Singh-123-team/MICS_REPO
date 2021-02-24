package com.nsn.telkomsel.mics.util;

public class ScreeningType {
	
	
	/**
	 * 
	 */
	public ScreeningType() {
		
	}
	/**
	 * @param id
	 * @param name
	 */
	public ScreeningType(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	private Integer id;
	private String name;
}
