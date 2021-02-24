package com.nsn.telkomsel.mics.util;
/**
 * BearerType.java
 * 
 * @author mulia
 * @version $Id: BearerType.java,v 1.1.4.3 2019/03/22 10:06:36 cvsuser Exp $
 */
public class BearerType {
	
	public BearerType(){
		
	}
	/**
	 * @param id
	 * @param name
	 */
	public BearerType(int id, String name) {
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
