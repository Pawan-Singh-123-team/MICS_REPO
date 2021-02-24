package com.nsn.telkomsel.mics.util;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * AddCallScreening.java
 * 
 * @author mulia
 * @version $Id: AddCallScreening.java,v 1.1.4.3 2019/03/22 10:06:34 cvsuser Exp $
 */

public class AddCallScreening {
	
	
	
	

	public AddCallScreening() {
		
	}


	/**
	 * @param rulekey
	 * @param rulename
	 * @param orderindex
	 * @param timebandkey
	 * @param vlrprefix
	 * @param globalciprefix
	 * @param callpartytype
	 * @param callpartynumber
	 * @param allow
	 * @param rejectionannoid
	 * @param updateuser
	 * @param updatetimestamp
	 * @param profileName
	 * @param profileDesc
	 * @param screeningType
	 * @param screeningLevel
	 * @param actionType
	 */
	public AddCallScreening(String rulekey, String rulename,
			BigDecimal orderindex, String timebandkey, String vlrprefix,
			String globalciprefix, BigDecimal callpartytype,
			String callpartynumber, boolean allow, BigDecimal rejectionannoid,
			String updateuser, Timestamp updatetimestamp, String profileName,
			String profileDesc, int screeningType, int screeningLevel,
			String actionType) {
		super();
		this.rulekey = rulekey;
		this.rulename = rulename;
		this.orderindex = orderindex;
		this.timebandkey = timebandkey;
		this.vlrprefix = vlrprefix;
		this.globalciprefix = globalciprefix;
		this.callpartytype = callpartytype;
		this.callpartynumber = callpartynumber;
		this.allow = allow;
		this.rejectionannoid = rejectionannoid;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
		this.profileName = profileName;
		this.profileDesc = profileDesc;
		this.screeningType = screeningType;
		this.screeningLevel = screeningLevel;
		this.actionType = actionType;
	}


	/**
	 * @return the rulekey
	 */
	public String getRulekey() {
		return rulekey;
	}

	/**
	 * @param rulekey the rulekey to set
	 */
	public void setRulekey(String rulekey) {
		this.rulekey = rulekey;
	}

	/**
	 * @return the rulename
	 */
	public String getRulename() {
		return rulename;
	}

	/**
	 * @param rulename the rulename to set
	 */
	public void setRulename(String rulename) {
		this.rulename = rulename;
	}

	/**
	 * @return the orderindex
	 */
	public BigDecimal getOrderindex() {
		return orderindex;
	}

	/**
	 * @param orderindex the orderindex to set
	 */
	public void setOrderindex(BigDecimal orderindex) {
		this.orderindex = orderindex;
	}

	/**
	 * @return the timebandkey
	 */
	public String getTimebandkey() {
		return timebandkey;
	}

	/**
	 * @param timebandkey the timebandkey to set
	 */
	public void setTimebandkey(String timebandkey) {
		this.timebandkey = timebandkey;
	}

	/**
	 * @return the vlrprefix
	 */
	public String getVlrprefix() {
		return vlrprefix;
	}

	/**
	 * @param vlrprefix the vlrprefix to set
	 */
	public void setVlrprefix(String vlrprefix) {
		this.vlrprefix = vlrprefix;
	}

	/**
	 * @return the globalciprefix
	 */
	public String getGlobalciprefix() {
		return globalciprefix;
	}

	/**
	 * @param globalciprefix the globalciprefix to set
	 */
	public void setGlobalciprefix(String globalciprefix) {
		this.globalciprefix = globalciprefix;
	}



	/**
	 * @return the allow
	 */
	public boolean isAllow() {
		return allow;
	}

	/**
	 * @param allow the allow to set
	 */
	public void setAllow(boolean allow) {
		this.allow = allow;
	}

	/**
	 * @return the bearerType
	 */
	public BigDecimal getBearerType() {
		return bearerType;
	}


	/**
	 * @param bearerType the bearerType to set
	 */
	public void setBearerType(BigDecimal bearerType) {
		this.bearerType = bearerType;
	}


	/**
	 * @return the rejectionannoid
	 */
	public BigDecimal getRejectionannoid() {
		return rejectionannoid;
	}

	/**
	 * @param rejectionannoid the rejectionannoid to set
	 */
	public void setRejectionannoid(BigDecimal rejectionannoid) {
		this.rejectionannoid = rejectionannoid;
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

	/**
	 * @return the actionType
	 */
	public String getActionType() {
		return actionType;
	}

	/**
	 * @param actionType the actionType to set
	 */
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	

	/**
	 * @return the callpartytype
	 */
	public BigDecimal getCallpartytype() {
		return callpartytype;
	}

	/**
	 * @param callpartytype the callpartytype to set
	 */
	public void setCallpartytype(BigDecimal callpartytype) {
		this.callpartytype = callpartytype;
	}

	/**
	 * @return the callpartynumber
	 */
	public String getCallpartynumber() {
		return callpartynumber;
	}

	/**
	 * @param callpartynumber the callpartynumber to set
	 */
	public void setCallpartynumber(String callpartynumber) {
		this.callpartynumber = callpartynumber;
	}

	/**
	 * @return the profileName
	 */
	public String getProfileName() {
		return profileName;
	}

	/**
	 * @param profileName the profileName to set
	 */
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	/**
	 * @return the profileDesc
	 */
	public String getProfileDesc() {
		return profileDesc;
	}

	/**
	 * @param profileDesc the profileDesc to set
	 */
	public void setProfileDesc(String profileDesc) {
		this.profileDesc = profileDesc;
	}

	/**
	 * @return the screeningType
	 */
	public int getScreeningType() {
		return screeningType;
	}

	/**
	 * @param screeningType the screeningType to set
	 */
	public void setScreeningType(int screeningType) {
		this.screeningType = screeningType;
	}

	/**
	 * @return the screeningLevel
	 */
	public int getScreeningLevel() {
		return screeningLevel;
	}

	/**
	 * @param screeningLevel the screeningLevel to set
	 */
	public void setScreeningLevel(int screeningLevel) {
		this.screeningLevel = screeningLevel;
	}

	
	/**
	 * @return the remove
	 */
	public boolean isRemove() {
		return remove;
	}


	/**
	 * @param remove the remove to set
	 */
	public void setRemove(boolean remove) {
		this.remove = remove;
	}


	private String rulekey;
	private String rulename;
	private BigDecimal orderindex;
	private String timebandkey;
	private String vlrprefix;
	private String globalciprefix;
	private BigDecimal callpartytype;
	private String callpartynumber;
	private boolean allow;
	
	private BigDecimal bearerType;
	private BigDecimal rejectionannoid;
	private String updateuser;
	private Timestamp updatetimestamp;
	private String profileName;
	private String profileDesc;
	private int screeningType;
	private int screeningLevel;
	
	private boolean remove;

	
	//CRUD
	private String actionType;

}
