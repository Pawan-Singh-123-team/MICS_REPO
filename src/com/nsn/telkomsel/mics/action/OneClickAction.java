package com.nsn.telkomsel.mics.action;

import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.nsn.telkomsel.mics.handler.MICSCommonSessionHandler;
import com.nsn.telkomsel.mics.orahbm.Webuser;
import com.nsn.telkomsel.mics.util.MICSConstant;
import com.opensymphony.xwork2.ActionSupport;

/**
 * OneClickAction.java
 * 
 * @author mulia
 * @version $Id: OneClickAction.java,v 1.1.4.3 2019/03/22 10:05:57 cvsuser Exp $
 */

public class OneClickAction extends ActionSupport implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3603200480972896162L;
	
	public String forward(){
		logger.debug("forward()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(oneClickAccess, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug(loginWebUser.getWebusername() + " authorized to use OneClick menu ");
					//Set Header
					ServletActionContext.getResponse().setHeader(MICSConstant.MICS_REQ_HEADER_LOGIN_USERKEY, loginWebUser.getWebuserkey());
					ServletActionContext.getResponse().setHeader(MICSConstant.MICS_REQ_HEADER_LOGIN_USERNAME, loginWebUser.getWebusername());
					
					this.ukey = loginWebUser.getWebuserkey();
					logger.debug("set header for OneClick success");
					
					
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
			logger.error("Failed to forward to OneClick",e);
			addActionError("Failed to forward to OneClick " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		}
		return result;
	}
	
	
	
	/**
	 * @return the ukey
	 */
	public String getUkey() {
		return ukey;
	}



	/**
	 * @param ukey the ukey to set
	 */
	public void setUkey(String ukey) {
		this.ukey = ukey;
	}



	public void setSession(Map<String, Object> session) {
		this.session = session;

	}
	
	private String oneClickAccess = "2017051700";
	private String ukey;
	
	private static final Logger logger = Logger.getLogger(OneClickAction.class);
	private Map<String,Object> session;

}
