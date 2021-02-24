package com.nsn.telkomsel.mics.orahbm;


import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author mulia
 * @version $Id: Callscreening.java,v 1.1.4.3 2019/03/22 10:06:07 cvsuser Exp $
 */
public class Callscreening implements java.io.Serializable {


	
	private static final long serialVersionUID = 5568313908030462997L;
	private String callscreeningkey;
	private String callscreeningname;
	private String callscreeningdesc;
	private String companykey;
	private BigDecimal screeningtype;
	private BigDecimal screeninglevel;
	private String updateuser;
	private Timestamp updatetimestamp;

	public Callscreening() {
	}

	public Callscreening(String callscreeningkey, String callscreeningname,
			String companykey, BigDecimal screeningtype,
			BigDecimal screeninglevel, String updateuser,
			Timestamp updatetimestamp) {
		this.callscreeningkey = callscreeningkey;
		this.callscreeningname = callscreeningname;
		this.companykey = companykey;
		this.screeningtype = screeningtype;
		this.screeninglevel = screeninglevel;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
	}
	
	

	/**
	 * @param callscreeningkey
	 * @param callscreeningname
	 * @param callscreeningdesc
	 * @param companykey
	 * @param screeningtype
	 * @param screeninglevel
	 * @param updateuser
	 * @param updatetimestamp
	 */
	public Callscreening(String callscreeningkey, String callscreeningname,
			String callscreeningdesc, String companykey,
			BigDecimal screeningtype, BigDecimal screeninglevel,
			String updateuser, Timestamp updatetimestamp) {
		super();
		this.callscreeningkey = callscreeningkey;
		this.callscreeningname = callscreeningname;
		this.callscreeningdesc = callscreeningdesc;
		this.companykey = companykey;
		this.screeningtype = screeningtype;
		this.screeninglevel = screeninglevel;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
	}

	public String getCallscreeningkey() {
		return this.callscreeningkey;
	}

	public void setCallscreeningkey(String callscreeningkey) {
		this.callscreeningkey = callscreeningkey;
	}

	public String getCallscreeningname() {
		return this.callscreeningname;
	}

	public void setCallscreeningname(String callscreeningname) {
		this.callscreeningname = callscreeningname;
	}
	
	
	/**
	 * @return the callscreeningdesc
	 */
	public String getCallscreeningdesc() {
		return callscreeningdesc;
	}

	/**
	 * @param callscreeningdesc the callscreeningdesc to set
	 */
	public void setCallscreeningdesc(String callscreeningdesc) {
		this.callscreeningdesc = callscreeningdesc;
	}

	public String getCompanykey() {
		return this.companykey;
	}

	public void setCompanykey(String companykey) {
		this.companykey = companykey;
	}

	public BigDecimal getScreeningtype() {
		return this.screeningtype;
	}

	public void setScreeningtype(BigDecimal screeningtype) {
		this.screeningtype = screeningtype;
	}

	public BigDecimal getScreeninglevel() {
		return this.screeninglevel;
	}

	public void setScreeninglevel(BigDecimal screeninglevel) {
		this.screeninglevel = screeninglevel;
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
