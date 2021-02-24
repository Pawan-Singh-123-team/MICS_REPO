package com.nsn.telkomsel.mics.orahbm;


import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author mulia
 * @version $Id: Outgoingcallscreening.java,v 1.1.4.3 2019/03/22 10:06:25 cvsuser Exp $
 */
public class Outgoingcallscreening implements java.io.Serializable {


	private static final long serialVersionUID = -1650492929618215303L;
	private String rulekey;
	private String rulename;
	private String callscreeningkey;
	private BigDecimal orderindex;
	private String timebandkey;
	private String vlrprefix;
	private String globalciprefix;
	private BigDecimal calledpartytype;
	private String calledpartynumber;
	private BigDecimal allow;
	private BigDecimal bearertype;
	private BigDecimal rejectionannoid;
	private String updateuser;
	private Timestamp updatetimestamp;

	public Outgoingcallscreening() {
	}

	public Outgoingcallscreening(String rulekey, String rulename,
			String callscreeningkey, String timebandkey, BigDecimal allow) {
		this.rulekey = rulekey;
		this.rulename = rulename;
		this.callscreeningkey = callscreeningkey;
		this.timebandkey = timebandkey;
		this.allow = allow;
	}

	public Outgoingcallscreening(String rulekey, String rulename,
			String callscreeningkey, BigDecimal orderindex, String timebandkey,
			String vlrprefix, String globalciprefix,
			BigDecimal calledpartytype, String calledpartynumber,
			BigDecimal allow, BigDecimal bearertype, BigDecimal rejectionannoid, String updateuser,
			Timestamp updatetimestamp) {
		this.rulekey = rulekey;
		this.rulename = rulename;
		this.callscreeningkey = callscreeningkey;
		this.orderindex = orderindex;
		this.timebandkey = timebandkey;
		this.vlrprefix = vlrprefix;
		this.globalciprefix = globalciprefix;
		this.calledpartytype = calledpartytype;
		this.calledpartynumber = calledpartynumber;
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

	public String getGlobalciprefix() {
		return this.globalciprefix;
	}

	public void setGlobalciprefix(String globalciprefix) {
		this.globalciprefix = globalciprefix;
	}

	public BigDecimal getCalledpartytype() {
		return this.calledpartytype;
	}

	public void setCalledpartytype(BigDecimal calledpartytype) {
		this.calledpartytype = calledpartytype;
	}

	public String getCalledpartynumber() {
		return this.calledpartynumber;
	}

	public void setCalledpartynumber(String calledpartynumber) {
		this.calledpartynumber = calledpartynumber;
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
