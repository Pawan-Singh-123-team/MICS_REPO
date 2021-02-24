package com.nsn.telkomsel.mics.orahbm;


import java.sql.Timestamp;
import java.math.BigDecimal;

/**
 * @author mulia
 * @version $Id: Incomingcallscreening.java,v 1.1.4.3 2019/03/22 10:06:13 cvsuser Exp $
 */
public class Incomingcallscreening implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8726023207650283842L;
	private String rulekey;
	private String rulename;
	private String callscreeningkey;
	private BigDecimal orderindex;
	private String timebandkey;
	private String vlrprefix;
	private BigDecimal callingpartytype;
	private String callingpartynumber;
	private BigDecimal allow;
	private BigDecimal bearertype;
	private BigDecimal rejectionannoid;
	private String updateuser;
	private Timestamp updatetimestamp;

	public Incomingcallscreening() {
	}

	public Incomingcallscreening(String rulekey, String rulename,
			String callscreeningkey, String timebandkey, String updateuser,
			Timestamp updatetimestamp) {
		this.rulekey = rulekey;
		this.rulename = rulename;
		this.callscreeningkey = callscreeningkey;
		this.timebandkey = timebandkey;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
	}

	public Incomingcallscreening(String rulekey, String rulename,
			String callscreeningkey, BigDecimal orderindex, String timebandkey,
			String vlrprefix, BigDecimal callingpartytype,
			String callingpartynumber, BigDecimal allow, BigDecimal bearertype,
			BigDecimal rejectionannoid, String updateuser,
			Timestamp updatetimestamp) {
		this.rulekey = rulekey;
		this.rulename = rulename;
		this.callscreeningkey = callscreeningkey;
		this.orderindex = orderindex;
		this.timebandkey = timebandkey;
		this.vlrprefix = vlrprefix;
		this.callingpartytype = callingpartytype;
		this.callingpartynumber = callingpartynumber;
		this.allow = allow;
		this.bearertype = bearertype;
		this.rejectionannoid = rejectionannoid;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
	}

	public String getRulekey() {
		return this.rulekey;
	}

	public void setRulekey(String rulekey) {
		this.rulekey = rulekey;
	}

	public String getRulename() {
		return this.rulename;
	}

	public void setRulename(String rulename) {
		this.rulename = rulename;
	}

	public String getCallscreeningkey() {
		return this.callscreeningkey;
	}

	public void setCallscreeningkey(String callscreeningkey) {
		this.callscreeningkey = callscreeningkey;
	}

	public BigDecimal getOrderindex() {
		return this.orderindex;
	}

	public void setOrderindex(BigDecimal orderindex) {
		this.orderindex = orderindex;
	}

	public String getTimebandkey() {
		return this.timebandkey;
	}

	public void setTimebandkey(String timebandkey) {
		this.timebandkey = timebandkey;
	}

	public String getVlrprefix() {
		return this.vlrprefix;
	}

	public void setVlrprefix(String vlrprefix) {
		this.vlrprefix = vlrprefix;
	}

	public BigDecimal getCallingpartytype() {
		return this.callingpartytype;
	}

	public void setCallingpartytype(BigDecimal callingpartytype) {
		this.callingpartytype = callingpartytype;
	}

	public String getCallingpartynumber() {
		return this.callingpartynumber;
	}

	public void setCallingpartynumber(String callingpartynumber) {
		this.callingpartynumber = callingpartynumber;
	}

	public BigDecimal getAllow() {
		return this.allow;
	}

	public void setAllow(BigDecimal allow) {
		this.allow = allow;
	}
	
	/**
	 * @return the bearertype
	 */
	public BigDecimal getBearertype() {
		return bearertype;
	}

	/**
	 * @param bearertype the bearertype to set
	 */
	public void setBearertype(BigDecimal bearertype) {
		this.bearertype = bearertype;
	}

	public BigDecimal getRejectionannoid() {
		return this.rejectionannoid;
	}

	public void setRejectionannoid(BigDecimal rejectionannoid) {
		this.rejectionannoid = rejectionannoid;
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
