package com.nsn.telkomsel.mics.orahbm;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Micsgroup.java
 * 
 * @author mulia
 * @version $Id: Micsgroup.java,v 1.1.4.3 2019/03/22 10:06:16 cvsuser Exp $
 */
public class Micsgroup implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3769728929412131671L;
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

	public Micsgroup() {
	}

	/**
	 * @param micsgroupkey
	 * @param companykey
	 * @param groupid
	 * @param groupname
	 * @param groupcli
	 * @param incs
	 * @param incsdesc
	 * @param bypassincs
	 * @param outcs
	 * @param outcsdesc
	 * @param bypassoutcs
	 * @param updateuser
	 * @param updatetimestamp
	 */
	public Micsgroup(String micsgroupkey, String companykey,
			BigDecimal groupid, String groupname, String groupcli, String incs,
			String incsdesc, String bypassincs, String outcs, String outcsdesc,
			String bypassoutcs, String updateuser, Timestamp updatetimestamp) {
		super();
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

	/**
	 * @param micsgroupkey
	 * @param companykey
	 * @param groupid
	 * @param groupname
	 * @param groupcli
	 * @param updateuser
	 * @param updatetimestamp
	 */
	public Micsgroup(String micsgroupkey, String companykey,
			BigDecimal groupid, String groupname, String groupcli,
			String updateuser, Timestamp updatetimestamp) {
		super();
		this.micsgroupkey = micsgroupkey;
		this.companykey = companykey;
		this.groupid = groupid;
		this.groupname = groupname;
		this.groupcli = groupcli;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
	}

	/**
	 * @return the micsgroupkey
	 */
	public String getMicsgroupkey() {
		return micsgroupkey;
	}

	/**
	 * @param micsgroupkey the micsgroupkey to set
	 */
	public void setMicsgroupkey(String micsgroupkey) {
		this.micsgroupkey = micsgroupkey;
	}

	/**
	 * @return the companykey
	 */
	public String getCompanykey() {
		return companykey;
	}

	/**
	 * @param companykey the companykey to set
	 */
	public void setCompanykey(String companykey) {
		this.companykey = companykey;
	}

	/**
	 * @return the groupid
	 */
	public BigDecimal getGroupid() {
		return groupid;
	}

	/**
	 * @param groupid the groupid to set
	 */
	public void setGroupid(BigDecimal groupid) {
		this.groupid = groupid;
	}

	/**
	 * @return the groupname
	 */
	public String getGroupname() {
		return groupname;
	}

	/**
	 * @param groupname the groupname to set
	 */
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	/**
	 * @return the groupcli
	 */
	public String getGroupcli() {
		return groupcli;
	}

	/**
	 * @param groupcli the groupcli to set
	 */
	public void setGroupcli(String groupcli) {
		this.groupcli = groupcli;
	}

	/**
	 * @return the incs
	 */
	public String getIncs() {
		return incs;
	}

	/**
	 * @param incs the incs to set
	 */
	public void setIncs(String incs) {
		this.incs = incs;
	}

	/**
	 * @return the incsdesc
	 */
	public String getIncsdesc() {
		return incsdesc;
	}

	/**
	 * @param incsdesc the incsdesc to set
	 */
	public void setIncsdesc(String incsdesc) {
		this.incsdesc = incsdesc;
	}

	/**
	 * @return the bypassincs
	 */
	public String getBypassincs() {
		return bypassincs;
	}

	/**
	 * @param bypassincs the bypassincs to set
	 */
	public void setBypassincs(String bypassincs) {
		this.bypassincs = bypassincs;
	}

	/**
	 * @return the outcs
	 */
	public String getOutcs() {
		return outcs;
	}

	/**
	 * @param outcs the outcs to set
	 */
	public void setOutcs(String outcs) {
		this.outcs = outcs;
	}

	/**
	 * @return the outcsdesc
	 */
	public String getOutcsdesc() {
		return outcsdesc;
	}

	/**
	 * @param outcsdesc the outcsdesc to set
	 */
	public void setOutcsdesc(String outcsdesc) {
		this.outcsdesc = outcsdesc;
	}

	/**
	 * @return the bypassoutcs
	 */
	public String getBypassoutcs() {
		return bypassoutcs;
	}

	/**
	 * @param bypassoutcs the bypassoutcs to set
	 */
	public void setBypassoutcs(String bypassoutcs) {
		this.bypassoutcs = bypassoutcs;
	}

	/**
	 * @return the updateuser
	 */
	public String getUpdateuser() {
		return updateuser;
	}

	/**
	 * @param updateuser the updateuser to set
	 */
	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
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
