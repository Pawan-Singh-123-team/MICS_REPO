package com.nsn.telkomsel.mics.orahbm;


import java.sql.Timestamp;
import java.math.BigDecimal;

/**
 * @author mulia
 * @version $Id: Numbertranslation.java,v 1.1.4.3 2019/03/22 10:06:24 cvsuser Exp $
 */
public class Numbertranslation implements java.io.Serializable {

	private static final long serialVersionUID = -6393003893793701472L;
	private String numbertranslationkey;
	private String companykey;
	private String inprefix;
	private BigDecimal minlength;
	private BigDecimal maxlength;
	private BigDecimal trimdigits;
	private String outprefix;
	private BigDecimal orderindex;
	private String updateuser;
	private Timestamp updatetimestamp;

	public Numbertranslation() {
	}

	public Numbertranslation(String numbertranslationkey, String companykey,
			String updateuser, Timestamp updatetimestamp) {
		this.numbertranslationkey = numbertranslationkey;
		this.companykey = companykey;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
	}

	public Numbertranslation(String numbertranslationkey, String companykey,
			String inprefix, BigDecimal minlength, BigDecimal maxlength,
			BigDecimal trimdigits, String outprefix, BigDecimal orderindex,
			String updateuser, Timestamp updatetimestamp) {
		this.numbertranslationkey = numbertranslationkey;
		this.companykey = companykey;
		this.inprefix = inprefix;
		this.minlength = minlength;
		this.maxlength = maxlength;
		this.trimdigits = trimdigits;
		this.outprefix = outprefix;
		this.orderindex = orderindex;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
	}

	public String getNumbertranslationkey() {
		return this.numbertranslationkey;
	}

	public void setNumbertranslationkey(String numbertranslationkey) {
		this.numbertranslationkey = numbertranslationkey;
	}

	public String getCompanykey() {
		return this.companykey;
	}

	public void setCompanykey(String companykey) {
		this.companykey = companykey;
	}

	public String getInprefix() {
		return this.inprefix;
	}

	public void setInprefix(String inprefix) {
		this.inprefix = inprefix;
	}

	public BigDecimal getMinlength() {
		return this.minlength;
	}

	public void setMinlength(BigDecimal minlength) {
		this.minlength = minlength;
	}

	public BigDecimal getMaxlength() {
		return this.maxlength;
	}

	public void setMaxlength(BigDecimal maxlength) {
		this.maxlength = maxlength;
	}

	public BigDecimal getTrimdigits() {
		return this.trimdigits;
	}

	public void setTrimdigits(BigDecimal trimdigits) {
		this.trimdigits = trimdigits;
	}

	public String getOutprefix() {
		return this.outprefix;
	}

	public void setOutprefix(String outprefix) {
		this.outprefix = outprefix;
	}

	public BigDecimal getOrderindex() {
		return this.orderindex;
	}

	public void setOrderindex(BigDecimal orderindex) {
		this.orderindex = orderindex;
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
