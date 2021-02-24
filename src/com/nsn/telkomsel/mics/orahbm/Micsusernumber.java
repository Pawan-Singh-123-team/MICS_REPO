package com.nsn.telkomsel.mics.orahbm;


import java.sql.Timestamp;
import java.math.BigDecimal;

/**
 * @author mulia
 * @version $Id: Micsusernumber.java,v 1.1.4.3 2019/03/22 10:06:23 cvsuser Exp $
 */
public class Micsusernumber implements java.io.Serializable {


	private static final long serialVersionUID = 1060849131500344685L;
	private String micsusernumber;
	private String companykey;
	private String micsuserkey;
	private BigDecimal orderindex;
	private String publicnumber;
	private String privatenumber;
	private String imsi;
	private BigDecimal numbertype;
	private String lastanswer;
	private String sipuri;
	private BigDecimal prov_req_id;
	private BigDecimal prov_req_status;
	private String ocsid;
	private String updateuser;
	private Timestamp updatetimestamp;

	public Micsusernumber() {
	}

	public Micsusernumber(String micsusernumber, String companykey,
			String micsuserkey, String publicnumber,String sipuri,String updateuser,
			Timestamp updatetimestamp) {
		this.micsusernumber = micsusernumber;
		this.companykey = companykey;
		this.micsuserkey = micsuserkey;
		this.publicnumber = publicnumber;
		this.sipuri = sipuri;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
	}

	public Micsusernumber(String micsusernumber, String companykey,
			String micsuserkey, BigDecimal orderindex, String publicnumber,
			String privatenumber, String imsi,BigDecimal numbertype, String lastanswer,String sipuri,
			BigDecimal prov_req_id,BigDecimal prov_req_status,String updateuser, Timestamp updatetimestamp) {
		this.micsusernumber = micsusernumber;
		this.companykey = companykey;
		this.micsuserkey = micsuserkey;
		this.orderindex = orderindex;
		this.publicnumber = publicnumber;
		this.privatenumber = privatenumber;
		this.imsi = imsi;
		this.numbertype = numbertype;
		this.lastanswer = lastanswer;
		this.sipuri = sipuri;
		this.prov_req_id = prov_req_id;
		this.prov_req_status = prov_req_status;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
	}
	

	/**
	 * @param micsusernumber
	 * @param companykey
	 * @param micsuserkey
	 * @param orderindex
	 * @param publicnumber
	 * @param privatenumber
	 * @param imsi
	 * @param numbertype
	 * @param lastanswer
	 * @param sipuri
	 * @param prov_req_id
	 * @param prov_req_status
	 * @param ocsid
	 * @param updateuser
	 * @param updatetimestamp
	 */
	public Micsusernumber(String micsusernumber, String companykey,
			String micsuserkey, BigDecimal orderindex, String publicnumber,
			String privatenumber, String imsi, BigDecimal numbertype,
			String lastanswer, String sipuri, BigDecimal prov_req_id,
			BigDecimal prov_req_status, String ocsid, String updateuser,
			Timestamp updatetimestamp) {
		super();
		this.micsusernumber = micsusernumber;
		this.companykey = companykey;
		this.micsuserkey = micsuserkey;
		this.orderindex = orderindex;
		this.publicnumber = publicnumber;
		this.privatenumber = privatenumber;
		this.imsi = imsi;
		this.numbertype = numbertype;
		this.lastanswer = lastanswer;
		this.sipuri = sipuri;
		this.prov_req_id = prov_req_id;
		this.prov_req_status = prov_req_status;
		this.ocsid = ocsid;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
	}

	public String getMicsusernumber() {
		return this.micsusernumber;
	}

	public void setMicsusernumber(String micsusernumber) {
		this.micsusernumber = micsusernumber;
	}

	public String getCompanykey() {
		return this.companykey;
	}

	public void setCompanykey(String companykey) {
		this.companykey = companykey;
	}

	public String getMicsuserkey() {
		return this.micsuserkey;
	}

	public void setMicsuserkey(String micsuserkey) {
		this.micsuserkey = micsuserkey;
	}

	public BigDecimal getOrderindex() {
		return this.orderindex;
	}

	public void setOrderindex(BigDecimal orderindex) {
		this.orderindex = orderindex;
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
	
	/**
	 * @return the imsi
	 */
	public String getImsi() {
		return imsi;
	}

	/**
	 * @param imsi the imsi to set
	 */
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public BigDecimal getNumbertype() {
		return this.numbertype;
	}

	public void setNumbertype(BigDecimal numbertype) {
		this.numbertype = numbertype;
	}

	public String getLastanswer() {
		return this.lastanswer;
	}

	public void setLastanswer(String lastanswer) {
		this.lastanswer = lastanswer;
	}
	

	/**
	 * @return the sipuri
	 */
	public String getSipuri() {
		return sipuri;
	}

	/**
	 * @param sipuri the sipuri to set
	 */
	public void setSipuri(String sipuri) {
		this.sipuri = sipuri;
	}
	
	
	/**
	 * @return the prov_req_id
	 */
	public BigDecimal getProv_req_id() {
		return prov_req_id;
	}

	/**
	 * @param prov_req_id the prov_req_id to set
	 */
	public void setProv_req_id(BigDecimal prov_req_id) {
		this.prov_req_id = prov_req_id;
	}

	/**
	 * @return the prov_req_status
	 */
	public BigDecimal getProv_req_status() {
		return prov_req_status;
	}

	/**
	 * @param prov_req_status the prov_req_status to set
	 */
	public void setProv_req_status(BigDecimal prov_req_status) {
		this.prov_req_status = prov_req_status;
	}

	
	/**
	 * @return the ocsid
	 */
	public String getOcsid() {
		return ocsid;
	}

	/**
	 * @param ocsid the ocsid to set
	 */
	public void setOcsid(String ocsid) {
		this.ocsid = ocsid;
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
