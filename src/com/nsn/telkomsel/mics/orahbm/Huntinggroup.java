package com.nsn.telkomsel.mics.orahbm;



import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author mulia
 * @version $Id: Huntinggroup.java,v 1.1.4.3 2019/03/22 10:06:11 cvsuser Exp $
 */
public class Huntinggroup implements java.io.Serializable {

	@Override
	public String toString() {
		return "Huntinggroup [huntinggroupkey=" + huntinggroupkey + ", companykey=" + companykey + ", huntinggroupname="
				+ huntinggroupname + ", publicnumber=" + publicnumber + ", privatenumber=" + privatenumber
				+ ", welcomeannoid=" + welcomeannoid + ", membernaannoid=" + membernaannoid + ", membernrannoid="
				+ membernrannoid + ", huntingcliprefix=" + huntingcliprefix + ", huntingoption=" + huntingoption
				+ ", reversecharging=" + reverseCharging + ", lastmemberorderindex=" + lastmemberorderindex
				+ ", updateuser=" + updateuser + ", updatetimestamp=" + updatetimestamp + "]";
	}

	private static final long serialVersionUID = 5378292020740877970L;
	private String huntinggroupkey;
	private String companykey;
	private String huntinggroupname;
	private String publicnumber;
	private String privatenumber;
	private BigDecimal welcomeannoid;
	private BigDecimal membernaannoid;
	private BigDecimal membernrannoid;
	private String huntingcliprefix;
	private BigDecimal huntingoption;
	private BigDecimal reverseCharging;
	private BigDecimal lastmemberorderindex;
	private String updateuser;
	private Timestamp updatetimestamp;

	public Huntinggroup() {
	}

	public Huntinggroup(String huntinggroupkey, String companykey,
			String huntinggroupname, String updateuser,
			Timestamp updatetimestamp) {
		this.huntinggroupkey = huntinggroupkey;
		this.companykey = companykey;
		this.huntinggroupname = huntinggroupname;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
	}

	public Huntinggroup(String huntinggroupkey, String companykey,
			String huntinggroupname, String publicnumber, String privatenumber,
			BigDecimal welcomeannoid, BigDecimal membernaannoid,
			BigDecimal membernrannoid, String huntingcliprefix,
			BigDecimal huntingoption, BigDecimal lastmemberorderindex,
			String updateuser, Timestamp updatetimestamp,BigDecimal reverseCharging) {
		this.huntinggroupkey = huntinggroupkey;
		this.companykey = companykey;
		this.huntinggroupname = huntinggroupname;
		this.publicnumber = publicnumber;
		this.privatenumber = privatenumber;
		this.welcomeannoid = welcomeannoid;
		this.membernaannoid = membernaannoid;
		this.membernrannoid = membernrannoid;
		this.huntingcliprefix = huntingcliprefix;
		this.huntingoption = huntingoption;
		this.lastmemberorderindex = lastmemberorderindex;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
		this.reverseCharging = reverseCharging;
	}

	public String getHuntinggroupkey() {
		return this.huntinggroupkey;
	}

	public void setHuntinggroupkey(String huntinggroupkey) {
		this.huntinggroupkey = huntinggroupkey;
	}

	public String getCompanykey() {
		return this.companykey;
	}

	public void setCompanykey(String companykey) {
		this.companykey = companykey;
	}

	public String getHuntinggroupname() {
		return this.huntinggroupname;
	}

	public void setHuntinggroupname(String huntinggroupname) {
		this.huntinggroupname = huntinggroupname;
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

	public BigDecimal getWelcomeannoid() {
		return this.welcomeannoid;
	}

	public void setWelcomeannoid(BigDecimal welcomeannoid) {
		this.welcomeannoid = welcomeannoid;
	}

	public BigDecimal getMembernaannoid() {
		return this.membernaannoid;
	}

	public void setMembernaannoid(BigDecimal membernaannoid) {
		this.membernaannoid = membernaannoid;
	}

	public BigDecimal getMembernrannoid() {
		return this.membernrannoid;
	}

	public void setMembernrannoid(BigDecimal membernrannoid) {
		this.membernrannoid = membernrannoid;
	}

	public String getHuntingcliprefix() {
		return this.huntingcliprefix;
	}

	public void setHuntingcliprefix(String huntingcliprefix) {
		this.huntingcliprefix = huntingcliprefix;
	}

	public BigDecimal getHuntingoption() {
		return this.huntingoption;
	}

	public void setHuntingoption(BigDecimal huntingoption) {
		this.huntingoption = huntingoption;
	}

	public BigDecimal getLastmemberorderindex() {
		return this.lastmemberorderindex;
	}

	public void setLastmemberorderindex(BigDecimal lastmemberorderindex) {
		this.lastmemberorderindex = lastmemberorderindex;
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

	public BigDecimal getReverseCharging() {
		return this.reverseCharging;
	}

	public void setReverseCharging(BigDecimal revCharging) {
		this.reverseCharging= revCharging;
	}
}
