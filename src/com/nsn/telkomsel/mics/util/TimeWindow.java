package com.nsn.telkomsel.mics.util;
/**
 * TimeWindow.java
 * 
 * @author mulia
 * @version $Id: TimeWindow.java,v 1.1.4.3 2019/03/22 10:06:40 cvsuser Exp $
 */
public class TimeWindow {
	
	
	

	public TimeWindow() {
	}
	
	
	/**
	 * @param id
	 * @param name
	 */
	public TimeWindow(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	


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




	private int id;
	private String name;

}
