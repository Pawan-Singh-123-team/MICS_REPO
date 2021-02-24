package com.nsn.telkomsel.mics.orahbm;


import java.sql.Timestamp;
import java.math.BigDecimal;

/**
 * @author mulia
 * @version $Id: MicsgroupId.java,v 1.1.4.3 2019/03/22 10:06:17 cvsuser Exp $
 */
public class MicsgroupId implements java.io.Serializable {

	private static final long serialVersionUID = 2851877150836015262L;
	private String micsgroupkey;
	private String companykey;
	private BigDecimal groupid;
	private String groupname;
	private String groupcli;
	private String incs;
	private String incsdesc;
	private String bypassincs;
	private String outcs;
	private String outcsdesc;
	private String bypassoutcs;
	private String updateuser;
	private Timestamp updatetimestamp;

	public MicsgroupId() {
	}

	public MicsgroupId(String updateuser) {
		this.updateuser = updateuser;
	}

	public MicsgroupId(String micsgroupkey, String companykey,
			BigDecimal groupid, String groupname, String groupcli, String incs,
			String incsdesc, String bypassincs, String outcs, String outcsdesc,
			String bypassoutcs, String updateuser, Timestamp updatetimestamp) {
		this.micsgroupkey = micsgroupkey;
		this.companykey = companykey;
		this.groupid = groupid;
		this.groupname = groupname;
		this.groupcli = groupcli;
		this.incs = incs;
		this.incsdesc = incsdesc;
		this.bypassincs = bypassincs;
		this.outcs = outcs;
		this.outcsdesc = outcsdesc;
		this.bypassoutcs = bypassoutcs;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
	}

	public String getMicsgroupkey() {
		return this.micsgroupkey;
	}

	public void setMicsgroupkey(String micsgroupkey) {
		this.micsgroupkey = micsgroupkey;
	}

	public String getCompanykey() {
		return this.companykey;
	}

	public void setCompanykey(String companykey) {
		this.companykey = companykey;
	}

	public BigDecimal getGroupid() {
		return this.groupid;
	}

	public void setGroupid(BigDecimal groupid) {
		this.groupid = groupid;
	}

	public String getGroupname() {
		return this.groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getGroupcli() {
		return this.groupcli;
	}

	public void setGroupcli(String groupcli) {
		this.groupcli = groupcli;
	}

	public String getIncs() {
		return this.incs;
	}

	public void setIncs(String incs) {
		this.incs = incs;
	}

	public String getIncsdesc() {
		return this.incsdesc;
	}

	public void setIncsdesc(String incsdesc) {
		this.incsdesc = incsdesc;
	}

	public String getBypassincs() {
		return this.bypassincs;
	}

	public void setBypassincs(String bypassincs) {
		this.bypassincs = bypassincs;
	}

	public String getOutcs() {
		return this.outcs;
	}

	public void setOutcs(String outcs) {
		this.outcs = outcs;
	}

	public String getOutcsdesc() {
		return this.outcsdesc;
	}

	public void setOutcsdesc(String outcsdesc) {
		this.outcsdesc = outcsdesc;
	}

	public String getBypassoutcs() {
		return this.bypassoutcs;
	}

	public void setBypassoutcs(String bypassoutcs) {
		this.bypassoutcs = bypassoutcs;
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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof MicsgroupId))
			return false;
		MicsgroupId castOther = (MicsgroupId) other;

		return ((this.getMicsgroupkey() == castOther.getMicsgroupkey()) || (this
				.getMicsgroupkey() != null
				&& castOther.getMicsgroupkey() != null && this
				.getMicsgroupkey().equals(castOther.getMicsgroupkey())))
				&& ((this.getCompanykey() == castOther.getCompanykey()) || (this
						.getCompanykey() != null
						&& castOther.getCompanykey() != null && this
						.getCompanykey().equals(castOther.getCompanykey())))
				&& ((this.getGroupid() == castOther.getGroupid()) || (this
						.getGroupid() != null && castOther.getGroupid() != null && this
						.getGroupid().equals(castOther.getGroupid())))
				&& ((this.getGroupname() == castOther.getGroupname()) || (this
						.getGroupname() != null
						&& castOther.getGroupname() != null && this
						.getGroupname().equals(castOther.getGroupname())))
				&& ((this.getGroupcli() == castOther.getGroupcli()) || (this
						.getGroupcli() != null
						&& castOther.getGroupcli() != null && this
						.getGroupcli().equals(castOther.getGroupcli())))
				&& ((this.getIncs() == castOther.getIncs()) || (this.getIncs() != null
						&& castOther.getIncs() != null && this.getIncs()
						.equals(castOther.getIncs())))
				&& ((this.getIncsdesc() == castOther.getIncsdesc()) || (this
						.getIncsdesc() != null
						&& castOther.getIncsdesc() != null && this
						.getIncsdesc().equals(castOther.getIncsdesc())))
				&& ((this.getBypassincs() == castOther.getBypassincs()) || (this
						.getBypassincs() != null
						&& castOther.getBypassincs() != null && this
						.getBypassincs().equals(castOther.getBypassincs())))
				&& ((this.getOutcs() == castOther.getOutcs()) || (this
						.getOutcs() != null && castOther.getOutcs() != null && this
						.getOutcs().equals(castOther.getOutcs())))
				&& ((this.getOutcsdesc() == castOther.getOutcsdesc()) || (this
						.getOutcsdesc() != null
						&& castOther.getOutcsdesc() != null && this
						.getOutcsdesc().equals(castOther.getOutcsdesc())))
				&& ((this.getBypassoutcs() == castOther.getBypassoutcs()) || (this
						.getBypassoutcs() != null
						&& castOther.getBypassoutcs() != null && this
						.getBypassoutcs().equals(castOther.getBypassoutcs())))
				&& ((this.getUpdateuser() == castOther.getUpdateuser()) || (this
						.getUpdateuser() != null
						&& castOther.getUpdateuser() != null && this
						.getUpdateuser().equals(castOther.getUpdateuser())))
				&& ((this.getUpdatetimestamp() == castOther
						.getUpdatetimestamp()) || (this.getUpdatetimestamp() != null
						&& castOther.getUpdatetimestamp() != null && this
						.getUpdatetimestamp().equals(
								castOther.getUpdatetimestamp())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getMicsgroupkey() == null ? 0 : this.getMicsgroupkey()
						.hashCode());
		result = 37
				* result
				+ (getCompanykey() == null ? 0 : this.getCompanykey()
						.hashCode());
		result = 37 * result
				+ (getGroupid() == null ? 0 : this.getGroupid().hashCode());
		result = 37 * result
				+ (getGroupname() == null ? 0 : this.getGroupname().hashCode());
		result = 37 * result
				+ (getGroupcli() == null ? 0 : this.getGroupcli().hashCode());
		result = 37 * result
				+ (getIncs() == null ? 0 : this.getIncs().hashCode());
		result = 37 * result
				+ (getIncsdesc() == null ? 0 : this.getIncsdesc().hashCode());
		result = 37
				* result
				+ (getBypassincs() == null ? 0 : this.getBypassincs()
						.hashCode());
		result = 37 * result
				+ (getOutcs() == null ? 0 : this.getOutcs().hashCode());
		result = 37 * result
				+ (getOutcsdesc() == null ? 0 : this.getOutcsdesc().hashCode());
		result = 37
				* result
				+ (getBypassoutcs() == null ? 0 : this.getBypassoutcs()
						.hashCode());
		result = 37
				* result
				+ (getUpdateuser() == null ? 0 : this.getUpdateuser()
						.hashCode());
		result = 37
				* result
				+ (getUpdatetimestamp() == null ? 0 : this.getUpdatetimestamp()
						.hashCode());
		return result;
	}

}
