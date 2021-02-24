package com.nsn.telkomsel.mics.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.nsn.telkomsel.mics.handler.MICSCommonSessionHandler;
import com.nsn.telkomsel.mics.orahbm.Webuser;
import com.nsn.telkomsel.mics.util.MICSConstant;
import com.nsn.telkomsel.mics.util.Micslogger;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class AuditTrailAction extends ActionSupport implements SessionAware {

	public String search(){
		logger.info("search()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(auditTrailView, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug(loginWebUser.getWebusername() + "authorized to view audit trail ");
					//Search Company
					logList = micsCommonSessionHandler.getMicslog();
					logger.debug("search micslog success");
					result = MICSConstant.MICS_SUCCESS;
				} else {
					addActionError("User not authorized");
					result = MICSConstant.MICS_ERROR_NOT_AUTH;
				}
			} else {
				addActionError("User not logged in");
				result = MICSConstant.MICS_ERROR_NOT_LOGGEDIN;
			}
		} catch (Exception e) {
			logger.error("Failed to search micslog",e);
			addActionError("Failed to search micslog");
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
	}
	
	
	
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}



	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}



	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}



	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}



	/**
	 * @return the auditTrailView
	 */
	public String getAuditTrailView() {
		return auditTrailView;
	}



	/**
	 * @param auditTrailView the auditTrailView to set
	 */
	public void setAuditTrailView(String auditTrailView) {
		this.auditTrailView = auditTrailView;
	}

	

	/**
	 * @return the logList
	 */
	public List getLogList() {
		return logList;
	}



	/**
	 * @param logList the logList to set
	 */
	public void setLogList(List logList) {
		this.logList = logList;
	}



	/**
	 * @return the session
	 */
	public Map<String, Object> getSession() {
		return session;
	}



	public void setSession(Map<String, Object> session) {
		this.session = session;

	}
	
	private List logList;
	private Date startDate;
	private Date endDate;
	private String auditTrailView = "2017052000";
	private Map<String, Object> session;
	private static final Logger logger = Logger.getLogger(AuditTrailAction.class);

}
