package com.nsn.telkomsel.mics.orahbm;


import java.sql.Timestamp;
import java.math.BigDecimal;

/**
 * @author mulia
 * @version $Id: Switchboard.java,v 1.1.4.3 2019/03/22 10:06:28 cvsuser Exp $
 */
public class Switchboard implements java.io.Serializable {


	private static final long serialVersionUID = -7489401707817682483L;
	private String switchboardkey;
	private String companykey;
	private String switchboardname;
	private String publicnumber;
	private String privatenumber;
	private BigDecimal annoid;
	private String updateuser;
	private Timestamp updatetimestamp;

	public Switchboard() {
	}

	public Switchboard(String switchboardkey, String companykey,
			String switchboardname, String publicnumber) {
		this.switchboardkey = switchboardkey;
		this.companykey = companykey;
		this.switchboardname = switchboardname;
		this.publicnumber = publicnumber;
	}

	public Switchboard(String switchboardkey, String companykey,
			String switchboardname, String publicnumber, String privatenumber,
			BigDecimal annoid, String updateuser, Timestamp updatetimestamp) {
		this.switchboardkey = switchboardkey;
		this.companykey = companykey;
		this.switchboardname = switchboardname;
		this.publicnumber = publicnumber;
		this.privatenumber = privatenumber;
		this.annoid = annoid;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
	}

	public String getSwitchboardkey() {
		return this.switchboardkey;
	}

	public void setSwitchboardkey(String switchboardkey) {
		this.switchboardkey = switchboardkey;
	}

	public String getCompanykey() {
		return this.companykey;
	}

	public void setCompanykey(String companykey) {
		this.companykey = companykey;
	}

	public String getSwitchboardname() {
		return this.switchboardname;
	}

	public void setSwitchboardname(String switchboardname) {
		this.switchboardname = switchboardname;
	}

	public String getPublicnumber() {
		return this.publicnumber;
	}

	public void setPublicnumber(String publicnumber) {
		this.publicnumber = publicnumber;
	}

	public String getPrivatenumber() {
		return this.privatenumber;
	}

	public void setPrivatenumber(String privatenumber) {
		this.privatenumber = privatenumber;
	}

	public BigDecimal getAnnoid() {
		return this.annoid;
	}

	public void setAnnoid(BigDecimal annoid) {
		this.annoid = annoid;
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
