package com.nsn.telkomsel.mics.orahbm;


import java.sql.Timestamp;

/**
 * @author mulia
 * @version $Id: Partnership.java,v 1.1.4.3 2019/03/22 10:06:26 cvsuser Exp $
 */
public class Partnership implements java.io.Serializable {


	private static final long serialVersionUID = 449347458319104202L;
	private String partnershipkey;
	private String partnershipname;
	private String companykey1;
	private String companykey2;
	private String updateuser;
	private Timestamp updatetimestamp;

	public Partnership() {
	}

	public Partnership(String partnershipkey, String partnershipname,
			String companykey1, String companykey2, String updateuser,
			Timestamp updatetimestamp) {
		this.partnershipkey = partnershipkey;
		this.partnershipname = partnershipname;
		this.companykey1 = companykey1;
		this.companykey2 = companykey2;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
	}

	public String getPartnershipkey() {
		return this.partnershipkey;
	}

	public void setPartnershipkey(String partnershipkey) {
		this.partnershipkey = partnershipkey;
	}

	public String getPartnershipname() {
		return this.partnershipname;
	}

	public void setPartnershipname(String partnershipname) {
		this.partnershipname = partnershipname;
	}

	public String getCompanykey1() {
		return this.companykey1;
	}

	public void setCompanykey1(String companykey1) {
		this.companykey1 = companykey1;
	}

	public String getCompanykey2() {
		return this.companykey2;
	}

	public void setCompanykey2(String companykey2) {
		this.companykey2 = companykey2;
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
