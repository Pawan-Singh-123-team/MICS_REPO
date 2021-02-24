package com.nsn.telkomsel.mics.orahbm;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author mulia
 * @version $Id: Micsmenu.java,v 1.1.4.3 2019/03/22 10:06:18 cvsuser Exp $
 */
public class Micsmenu implements java.io.Serializable {


	private static final long serialVersionUID = 9199895422951267329L;
	private String micsmenukey;
	private String micsmenuname;
	private String micsmenudescription;
	private String link;
	private String parentmenu;
	private String updateuser;
	private Timestamp updatetimestamp;
	private BigDecimal display;

	public Micsmenu() {
	}

	public Micsmenu(String micsmenukey, String micsmenuname,
			String micsmenudescription) {
		this.micsmenukey = micsmenukey;
		this.micsmenuname = micsmenuname;
		this.micsmenudescription = micsmenudescription;
	}

	public Micsmenu(String micsmenukey, String micsmenuname,
			String micsmenudescription, String link, String parentmenu,
			String updateuser, Timestamp updatetimestamp) {
		this.micsmenukey = micsmenukey;
		this.micsmenuname = micsmenuname;
		this.micsmenudescription = micsmenudescription;
		this.link = link;
		this.parentmenu = parentmenu;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
	}
	

	/**
	 * @param micsmenukey
	 * @param micsmenuname
	 * @param micsmenudescription
	 * @param link
	 * @param parentmenu
	 * @param updateuser
	 * @param updatetimestamp
	 * @param display
	 */
	public Micsmenu(String micsmenukey, String micsmenuname,
			String micsmenudescription, String link, String parentmenu,
			String updateuser, Timestamp updatetimestamp, BigDecimal display) {
		super();
		this.micsmenukey = micsmenukey;
		this.micsmenuname = micsmenuname;
		this.micsmenudescription = micsmenudescription;
		this.link = link;
		this.parentmenu = parentmenu;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
		this.display = display;
	}

	public String getMicsmenukey() {
		return this.micsmenukey;
	}

	public void setMicsmenukey(String micsmenukey) {
		this.micsmenukey = micsmenukey;
	}

	public String getMicsmenuname() {
		return this.micsmenuname;
	}

	public void setMicsmenuname(String micsmenuname) {
		this.micsmenuname = micsmenuname;
	}

	public String getMicsmenudescription() {
		return this.micsmenudescription;
	}

	public void setMicsmenudescription(String micsmenudescription) {
		this.micsmenudescription = micsmenudescription;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getParentmenu() {
		return this.parentmenu;
	}

	public void setParentmenu(String parentmenu) {
		this.parentmenu = parentmenu;
	}

	public String getUpdateuser() {
		return this.updateuser;
	}

	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}

	public Timestamp getUpdatetimestamp() {
		return this.updatetimestamp;
	}

	public void setUpdatetimestamp(Timestamp updatetimestamp) {
		this.updatetimestamp = updatetimestamp;
	}

	/**
	 * @return the display
	 */
	public BigDecimal getDisplay() {
		return display;
	}

	/**
	 * @param display the display to set
	 */
	public void setDisplay(BigDecimal display) {
		this.display = display;
	}
	

}
