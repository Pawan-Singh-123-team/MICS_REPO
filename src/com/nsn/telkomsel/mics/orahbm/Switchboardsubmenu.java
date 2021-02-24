package com.nsn.telkomsel.mics.orahbm;


import java.sql.Timestamp;
import java.math.BigDecimal;

/**
 * @author mulia
 * @version $Id: Switchboardsubmenu.java,v 1.1.4.3 2019/03/22 10:06:30 cvsuser Exp $
 */
public class Switchboardsubmenu implements java.io.Serializable {


	private static final long serialVersionUID = -998168622103364332L;
	private String switchboardsubmenukey;
	private String switchboardsubmenuname;
	private String switchboardmenukey;
	private BigDecimal dialdigit;
	private String description;
	private String destinationnumber;
	private String updateuser;
	private Timestamp updatetimestamp;

	public Switchboardsubmenu() {
	}

	public Switchboardsubmenu(String switchboardsubmenukey,
			String switchboardmenukey, String updateuser,
			Timestamp updatetimestamp) {
		this.switchboardsubmenukey = switchboardsubmenukey;
		this.switchboardmenukey = switchboardmenukey;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
	}

	public Switchboardsubmenu(String switchboardsubmenukey,
			String switchboardsubmenuname, String switchboardmenukey,
			BigDecimal dialdigit, String description, String destinationnumber,
			String updateuser, Timestamp updatetimestamp) {
		this.switchboardsubmenukey = switchboardsubmenukey;
		this.switchboardsubmenuname = switchboardsubmenuname;
		this.switchboardmenukey = switchboardmenukey;
		this.dialdigit = dialdigit;
		this.description = description;
		this.destinationnumber = destinationnumber;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
	}

	public String getSwitchboardsubmenukey() {
		return this.switchboardsubmenukey;
	}

	public void setSwitchboardsubmenukey(String switchboardsubmenukey) {
		this.switchboardsubmenukey = switchboardsubmenukey;
	}

	public String getSwitchboardsubmenuname() {
		return this.switchboardsubmenuname;
	}

	public void setSwitchboardsubmenuname(String switchboardsubmenuname) {
		this.switchboardsubmenuname = switchboardsubmenuname;
	}

	public String getSwitchboardmenukey() {
		return this.switchboardmenukey;
	}

	public void setSwitchboardmenukey(String switchboardmenukey) {
		this.switchboardmenukey = switchboardmenukey;
	}

	public BigDecimal getDialdigit() {
		return this.dialdigit;
	}

	public void setDialdigit(BigDecimal dialdigit) {
		this.dialdigit = dialdigit;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDestinationnumber() {
		return this.destinationnumber;
	}

	public void setDestinationnumber(String destinationnumber) {
		this.destinationnumber = destinationnumber;
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

}
