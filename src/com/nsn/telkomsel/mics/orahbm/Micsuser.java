package com.nsn.telkomsel.mics.orahbm;


import java.sql.Timestamp;
import java.math.BigDecimal;

/**
 * Micsuser.java
 * 
 * @author mulia
 * @version $Id: Micsuser.java,v 1.1.4.3 2019/03/22 10:06:22 cvsuser Exp $
 */

public class Micsuser implements java.io.Serializable {

	private static final long serialVersionUID = -2662593512138556710L;
	private String micsuserkey;
	private String micsfirstname;
	private String companykey;
	private String groupkey;
	private BigDecimal subgroupid;
	private BigDecimal usergroupid;
	private BigDecimal vpngroupid;
	private String mainpublicnumber;
	private String mainprivatenumber;
	private BigDecimal usertype;
	private BigDecimal locked;
	private BigDecimal mainnumberascli;
	private BigDecimal mainnumberaschargedparty;
	private BigDecimal huntingoption;
	private BigDecimal keephuntingonbusy;
	private BigDecimal reverseCharging;
	private String incs;
	private String incsdesc;
	private String bypassincs;
	private String outcs;
	private String outcsdesc;
	private String bypassoutcs;
	private BigDecimal enableannorecording;
	private String pin;
	private String language;
	private String updateuser;
	private Timestamp updatetimestamp;
	private String micslastname;
	private String initialmainpublicnumber;
	private String initialmainprivatenumber;

	public Micsuser() {
	}

	public Micsuser(String micsuserkey, String micsfirstname,
			String companykey, String groupkey, BigDecimal subgroupid,
			String updateuser, Timestamp updatetimestamp) {
		this.micsuserkey = micsuserkey;
		this.micsfirstname = micsfirstname;
		this.companykey = companykey;
		this.groupkey = groupkey;
		this.subgroupid = subgroupid;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
	}

	public Micsuser(String micsuserkey, String micsfirstname,
			String companykey, String groupkey, BigDecimal subgroupid,
			BigDecimal usergroupid,BigDecimal vpngroupid, String mainpublicnumber,
			String mainprivatenumber, BigDecimal usertype, BigDecimal locked,
			BigDecimal mainnumberascli, BigDecimal mainnumberaschargedparty,
			BigDecimal huntingoption, BigDecimal keephuntingonbusy,
			String incs, String incsdesc, String bypassincs, String outcs,
			String outcsdesc, String bypassoutcs,
			BigDecimal enableannorecording, String pin, String language,
			String updateuser, Timestamp updatetimestamp, String micslastname,BigDecimal reverseCharging) {
		this.micsuserkey = micsuserkey;
		this.micsfirstname = micsfirstname;
		this.companykey = companykey;
		this.groupkey = groupkey;
		this.subgroupid = subgroupid;
		this.usergroupid = usergroupid;
		this.vpngroupid = vpngroupid;
		this.mainpublicnumber = mainpublicnumber;
		this.mainprivatenumber = mainprivatenumber;
		this.usertype = usertype;
		this.locked = locked;
		this.mainnumberascli = mainnumberascli;
		this.mainnumberaschargedparty = mainnumberaschargedparty;
		this.huntingoption = huntingoption;
		this.keephuntingonbusy = keephuntingonbusy;
		this.incs = incs;
		this.incsdesc = incsdesc;
		this.bypassincs = bypassincs;
		this.outcs = outcs;
		this.outcsdesc = outcsdesc;
		this.bypassoutcs = bypassoutcs;
		this.enableannorecording = enableannorecording;
		this.pin = pin;
		this.language = language;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
		this.micslastname = micslastname;
		this.reverseCharging = reverseCharging;
	}
	
	

	/**
	 * @param micsuserkey
	 * @param micsfirstname
	 * @param companykey
	 * @param groupkey
	 * @param subgroupid
	 * @param usergroupid
	 * @param vpngroupid
	 * @param mainpublicnumber
	 * @param mainprivatenumber
	 * @param usertype
	 * @param locked
	 * @param mainnumberascli
	 * @param mainnumberaschargedparty
	 * @param huntingoption
	 * @param keephuntingonbusy
	 * @param reverseCharging
	 * @param incs
	 * @param incsdesc
	 * @param bypassincs
	 * @param outcs
	 * @param outcsdesc
	 * @param bypassoutcs
	 * @param enableannorecording
	 * @param pin
	 * @param language
	 * @param updateuser
	 * @param updatetimestamp
	 * @param micslastname
	 * @param initialmainpublicnumber
	 * @param initialmainprivatenumber
	 */
	public Micsuser(String micsuserkey, String micsfirstname,
			String companykey, String groupkey, BigDecimal subgroupid,
			BigDecimal usergroupid, BigDecimal vpngroupid,
			String mainpublicnumber, String mainprivatenumber,
			BigDecimal usertype, BigDecimal locked, BigDecimal mainnumberascli,
			BigDecimal mainnumberaschargedparty, BigDecimal huntingoption,
			BigDecimal keephuntingonbusy, String incs, String incsdesc,
			String bypassincs, String outcs, String outcsdesc,
			String bypassoutcs, BigDecimal enableannorecording, String pin,
			String language, String updateuser, Timestamp updatetimestamp,
			String micslastname, String initialmainpublicnumber,
			String initialmainprivatenumber,BigDecimal reverseCharging) {
		super();
		this.micsuserkey = micsuserkey;
		this.micsfirstname = micsfirstname;
		this.companykey = companykey;
		this.groupkey = groupkey;
		this.subgroupid = subgroupid;
		this.usergroupid = usergroupid;
		this.vpngroupid = vpngroupid;
		this.mainpublicnumber = mainpublicnumber;
		this.mainprivatenumber = mainprivatenumber;
		this.usertype = usertype;
		this.locked = locked;
		this.mainnumberascli = mainnumberascli;
		this.mainnumberaschargedparty = mainnumberaschargedparty;
		this.huntingoption = huntingoption;
		this.keephuntingonbusy = keephuntingonbusy;
		this.incs = incs;
		this.incsdesc = incsdesc;
		this.bypassincs = bypassincs;
		this.outcs = outcs;
		this.outcsdesc = outcsdesc;
		this.bypassoutcs = bypassoutcs;
		this.enableannorecording = enableannorecording;
		this.pin = pin;
		this.language = language;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
		this.micslastname = micslastname;
		this.initialmainpublicnumber = initialmainpublicnumber;
		this.initialmainprivatenumber = initialmainprivatenumber;
		this.reverseCharging = reverseCharging;
	}

	public String getMicsuserkey() {
		return this.micsuserkey;
	}

	public void setMicsuserkey(String micsuserkey) {
		this.micsuserkey = micsuserkey;
	}

	public String getMicsfirstname() {
		return this.micsfirstname;
	}

	public void setMicsfirstname(String micsfirstname) {
		this.micsfirstname = micsfirstname;
	}

	public String getCompanykey() {
		return this.companykey;
	}

	public void setCompanykey(String companykey) {
		this.companykey = companykey;
	}

	public String getGroupkey() {
		return this.groupkey;
	}

	public void setGroupkey(String groupkey) {
		this.groupkey = groupkey;
	}

	public BigDecimal getSubgroupid() {
		return this.subgroupid;
	}

	public void setSubgroupid(BigDecimal subgroupid) {
		this.subgroupid = subgroupid;
	}

	public BigDecimal getUsergroupid() {
		return this.usergroupid;
	}

	public void setUsergroupid(BigDecimal usergroupid) {
		this.usergroupid = usergroupid;
	}
	
	
	/**
	 * @return the vpngroupid
	 */
	public BigDecimal getVpngroupid() {
		return vpngroupid;
	}

	/**
	 * @param vpngroupid the vpngroupid to set
	 */
	public void setVpngroupid(BigDecimal vpngroupid) {
		this.vpngroupid = vpngroupid;
	}

	public String getMainpublicnumber() {
		return this.mainpublicnumber;
	}

	public void setMainpublicnumber(String mainpublicnumber) {
		this.mainpublicnumber = mainpublicnumber;
	}

	public String getMainprivatenumber() {
		return this.mainprivatenumber;
	}

	public void setMainprivatenumber(String mainprivatenumber) {
		this.mainprivatenumber = mainprivatenumber;
	}

	public BigDecimal getUsertype() {
		return this.usertype;
	}

	public void setUsertype(BigDecimal usertype) {
		this.usertype = usertype;
	}

	public BigDecimal getLocked() {
		return this.locked;
	}

	public void setLocked(BigDecimal locked) {
		this.locked = locked;
	}

	public BigDecimal getMainnumberascli() {
		return this.mainnumberascli;
	}

	public void setMainnumberascli(BigDecimal mainnumberascli) {
		this.mainnumberascli = mainnumberascli;
	}

	public BigDecimal getMainnumberaschargedparty() {
		return this.mainnumberaschargedparty;
	}

	public void setMainnumberaschargedparty(BigDecimal mainnumberaschargedparty) {
		this.mainnumberaschargedparty = mainnumberaschargedparty;
	}

	public BigDecimal getHuntingoption() {
		return this.huntingoption;
	}

	public void setHuntingoption(BigDecimal huntingoption) {
		this.huntingoption = huntingoption;
	}

	public BigDecimal getKeephuntingonbusy() {
		return this.keephuntingonbusy;
	}

	public void setKeephuntingonbusy(BigDecimal keephuntingonbusy) {
		this.keephuntingonbusy = keephuntingonbusy;
	}

	public BigDecimal getReverseCharging() {
		return this.reverseCharging;
	}

	public void setReverseCharging(BigDecimal revCharging) {
		this.reverseCharging= revCharging;
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

	public BigDecimal getEnableannorecording() {
		return this.enableannorecording;
	}

	public void setEnableannorecording(BigDecimal enableannorecording) {
		this.enableannorecording = enableannorecording;
	}

	public String getPin() {
		return this.pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
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
	 * @return the initialmainpublicnumber
	 */
	public String getInitialmainpublicnumber() {
		return initialmainpublicnumber;
	}

	/**
	 * @param initialmainpublicnumber the initialmainpublicnumber to set
	 */
	public void setInitialmainpublicnumber(String initialmainpublicnumber) {
		this.initialmainpublicnumber = initialmainpublicnumber;
	}

	/**
	 * @return the initialmainprivatenumber
	 */
	public String getInitialmainprivatenumber() {
		return initialmainprivatenumber;
	}

	/**
	 * @param initialmainprivatenumber the initialmainprivatenumber to set
	 */
	public void setInitialmainprivatenumber(String initialmainprivatenumber) {
		this.initialmainprivatenumber = initialmainprivatenumber;
	}

	public String getMicslastname() {
		return this.micslastname;
	}

	public void setMicslastname(String micslastname) {
		this.micslastname = micslastname;
	}
	
	

}
