package com.nsn.telkomsel.mics.orahbm;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * Micsprop.java
 * 
 * @author mulia
 * @version $Id: Micsprop.java,v 1.1.4.3 2019/03/22 10:06:20 cvsuser Exp $
 */
public class Micsprop implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2918365466637983159L;
	private String name;
	private String value;
	private Timestamp updatetimestamp;
	
	public Micsprop(){
		
	}
	/**
	 * @param name
	 * @param value
	 * @param updatetimestamp
	 */
	public Micsprop(String name, String value, Timestamp updatetimestamp) {
		super();
		this.name = name;
		this.value = value;
		this.updatetimestamp = updatetimestamp;
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
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the updatetimestamp
	 */
	public Timestamp getUpdatetimestamp() {
		return updatetimestamp;
	}
	/**
	 * @param updatetimestamp the updatetimestamp to set
	 */
	public void setUpdatetimestamp(Timestamp updatetimestamp) {
		this.updatetimestamp = updatetimestamp;
	}
	
	
	
	
	

}
