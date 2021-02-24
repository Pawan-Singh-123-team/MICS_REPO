package com.nsn.telkomsel.mics.orahbm;


import java.sql.Timestamp;
import java.math.BigDecimal;

/**
 * @author mulia
 * @version $Id: Switchboardmenu.java,v 1.1.4.3 2019/03/22 10:06:29 cvsuser Exp $
 */
public class Switchboardmenu implements java.io.Serializable {


	private static final long serialVersionUID = -3423844384945666806L;
	private String switchboardmenukey;
	private String switchboardmenuname;
	private String switchboardkey;
	private BigDecimal dialdigit;
	private String description;
	private BigDecimal menutype;
	private String destinationnumber;
	private BigDecimal annoid;
	private BigDecimal upmenudialdigit;
	private String updateuser;
	private Timestamp updatetimestamp;

	public Switchboardmenu() {
	}

	public Switchboardmenu(String switchboardmenukey, String switchboardkey) {
		this.switchboardmenukey = switchboardmenukey;
		this.switchboardkey = switchboardkey;
	}

	public Switchboardmenu(String switchboardmenukey,
			String switchboardmenuname, String switchboardkey,
			BigDecimal dialdigit, String description, BigDecimal menutype,
			String destinationnumber, BigDecimal annoid,
			BigDecimal upmenudialdigit, String updateuser,
			Timestamp updatetimestamp) {
		this.switchboardmenukey = switchboardmenukey;
		this.switchboardmenuname = switchboardmenuname;
		this.switchboardkey = switchboardkey;
		this.dialdigit = dialdigit;
		this.description = description;
		this.menutype = menutype;
		this.destinationnumber = destinationnumber;
		this.annoid = annoid;
		this.upmenudialdigit = upmenudialdigit;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
	}

	public String getSwitchboardmenukey() {
		return this.switchboardmenukey;
	}

	public void setSwitchboardmenukey(String switchboardmenukey) {
		this.switchboardmenukey = switchboardmenukey;
	}

	public String getSwitchboardmenuname() {
		return this.switchboardmenuname;
	}

	public void setSwitchboardmenuname(String switchboardmenuname) {
		this.switchboardmenuname = switchboardmenuname;
	}

	public String getSwitchboardkey() {
		return this.switchboardkey;
	}

	public void setSwitchboardkey(String switchboardkey) {
		this.switchboardkey = switchboardkey;
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

	public BigDecimal getMenutype() {
		return this.menutype;
	}

	public void setMenutype(BigDecimal menutype) {
		this.menutype = menutype;
	}

	public String getDestinationnumber() {
		return this.destinationnumber;
	}

	public void setDestinationnumber(String destinationnumber) {
		this.destinationnumber = destinationnumber;
	}

	public BigDecimal getAnnoid() {
		return this.annoid;
	}

	public void setAnnoid(BigDecimal annoid) {
		this.annoid = annoid;
	}

	public BigDecimal getUpmenudialdigit() {
		return this.upmenudialdigit;
	}

	public void setUpmenudialdigit(BigDecimal upmenudialdigit) {
		this.upmenudialdigit = upmenudialdigit;
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
