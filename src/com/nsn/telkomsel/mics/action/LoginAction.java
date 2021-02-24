package com.nsn.telkomsel.mics.action;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.ServletActionContext;

import com.nsn.telkomsel.mics.MICSException;
import com.nsn.telkomsel.mics.handler.MICSCommonSessionHandler;
import com.nsn.telkomsel.mics.orahbm.Company;
import com.nsn.telkomsel.mics.orahbm.Webuser;
import com.nsn.telkomsel.mics.orahbm.Role;
import com.nsn.telkomsel.mics.util.AddHuntingSchedule;
import com.nsn.telkomsel.mics.util.MICSConstant;
import com.nsn.telkomsel.mics.util.MICSPasswordEnc;
import com.nsn.telkomsel.mics.util.Micslogger;

import com.opensymphony.xwork2.ActionSupport;

/**
 * LoginAction.java
 * 
 * @author mulia
 * @version $Id: LoginAction.java,v 1.1.4.3 2019/03/22 10:05:55 cvsuser Exp $
 */

public class LoginAction extends ActionSupport implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2785651618248804829L;
	@SuppressWarnings("rawtypes")
	public String index() {
		logger.info("index()");
		String result = "error";
		try{
			headerInfo = ServletActionContext.getRequest().getHeader(MICSConstant.MICS_REQ_HEADER_USER);
			
			logger.debug("headerInfo: " + headerInfo);
			if (headerInfo == null) {
				//Check all header avail in the request
				logger.debug("List avail headers, just for checking");
				Enumeration headerNames = ServletActionContext.getRequest().getHeaderNames();
				while (headerNames.hasMoreElements()) {
					String strName = (String) headerNames.nextElement();
					logger.debug(strName + ": " + ServletActionContext.getRequest().getHeader(strName));
				}
			}
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			Webuser user = null;
			if (headerInfo != null && !headerInfo.trim().equals("")) {
				logger.debug("SSO Login");
				user = micsCommonSessionHandler.getActiveSSOUser(headerInfo);
			} 
//			To remove login for SSO
//			else {
//				throw new MICSException("You are not authenticated by SSO, Please authenticate with SSO first.");
//			}
			
			logger.debug("user: " + user);
			if (user != null){
				Role role = micsCommonSessionHandler.getRoleByKey(user.getRolekey());
				List menuList = micsCommonSessionHandler.getMenusByRole(role.getRolekey());
				
				if (user.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
						user.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
						user.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
						user.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
						user.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
					this.tselAdmin = true;
					session.remove(MICSConstant.MICS_SESSION_CURRCOMPANY);
				} else {
					this.tselAdmin = false;
					Company currCompany = micsCommonSessionHandler.getCompanyByKey(user.getCompanykey());
					session.put(MICSConstant.MICS_SESSION_CURRCOMPANY, currCompany);
					
				}
				session.put(MICSConstant.MICS_SESSION_ROLE, role);
				session.put(MICSConstant.MICS_SESSION_USER, user);
				session.put(MICSConstant.MICS_SESSION_MENUS, menuList);
				ServletActionContext.getResponse().setHeader(MICSConstant.MICS_REQ_HEADER_LOGIN_USERKEY, user.getWebuserkey());
				ServletActionContext.getResponse().setHeader(MICSConstant.MICS_REQ_HEADER_LOGIN_USERNAME, user.getWebusername());
				result = MICSConstant.MICS_SSO;
				String addrHostPort = ServletActionContext.getRequest().getRemoteAddr()+" - "+ServletActionContext.getRequest().getRemoteHost()+" - " + ServletActionContext.getRequest().getRemotePort();
				Micslogger.log("Login","Login", user.getWebusername()+" login using SSO " + addrHostPort, user.getCompanykey(), user.getWebusername(),"SUCCESS");
			} else {
				if (headerInfo != null && !headerInfo.trim().equals("")) {
					String addrHostPort = ServletActionContext.getRequest().getRemoteAddr()+" - "+ServletActionContext.getRequest().getRemoteHost()+" - " + ServletActionContext.getRequest().getRemotePort();
					addActionError("SSO Login with " + headerInfo + " from " +addrHostPort+" failed, no account mapped for this user!");
				}
				result = MICSConstant.MICS_SUCCESS;
			}
		}catch (Exception ex){
			logger.error("Failed to prepare index",ex);
			addActionError("Failed to process your request "+ ex.getMessage());
			result = MICSConstant.MICS_ERROR;
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public String login() {
		logger.info("Login()");
		String result = "error";
		try{
			String headerInfo = ServletActionContext.getRequest().getHeader(MICSConstant.MICS_REQ_HEADER_USER);
			logger.debug("headerInfo: " + headerInfo);
			if (headerInfo == null) {
				//Check all header avail in the request
				logger.debug("List avail headers, just for checking");
				Enumeration headerNames = ServletActionContext.getRequest().getHeaderNames();
				while (headerNames.hasMoreElements()) {
					String strName = (String) headerNames.nextElement();
					logger.debug(strName + ": " + ServletActionContext.getRequest().getHeader(strName));
				}
			}
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			Webuser user = null;
			if (headerInfo != null && !headerInfo.trim().equals("")) {
				logger.debug("SSO Login");
				user = micsCommonSessionHandler.getActiveSSOUser(headerInfo);
				this.username = "SSO Login " + headerInfo;
			} else {
				logger.debug("Normal Login");
				MICSPasswordEnc mpe = new MICSPasswordEnc();
				user = micsCommonSessionHandler.getActiveUser(this.username, mpe.getEncryptedPassword(this.password.trim()));
			}
			
			logger.debug("user: " + user);
			if (user != null){
				Role role = micsCommonSessionHandler.getRoleByKey(user.getRolekey());
				List menuList = micsCommonSessionHandler.getMenusByRole(role.getRolekey());
				
				if (user.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
						user.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
						user.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
						user.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
						user.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
					this.tselAdmin = true;
					session.remove(MICSConstant.MICS_SESSION_CURRCOMPANY);
				} else {
					this.tselAdmin = false;
					Company currCompany = micsCommonSessionHandler.getCompanyByKey(user.getCompanykey());
					session.put(MICSConstant.MICS_SESSION_CURRCOMPANY, currCompany);
					
				}
				session.put(MICSConstant.MICS_SESSION_ROLE, role);
				session.put(MICSConstant.MICS_SESSION_USER, user);
				session.put(MICSConstant.MICS_SESSION_MENUS, menuList);
				ServletActionContext.getResponse().setHeader(MICSConstant.MICS_REQ_HEADER_LOGIN_USERKEY, user.getWebuserkey());
				ServletActionContext.getResponse().setHeader(MICSConstant.MICS_REQ_HEADER_LOGIN_USERNAME, user.getWebusername());
				result = MICSConstant.MICS_SUCCESS;
				String addrHostPort = ServletActionContext.getRequest().getRemoteAddr()+" - "+ServletActionContext.getRequest().getRemoteHost()+" - " + ServletActionContext.getRequest().getRemotePort();
				Micslogger.log("Login","Login", user.getWebusername()+" login from " + addrHostPort, user.getCompanykey(), user.getWebusername(),"SUCCESS");
			} else {
				String addrHostPort = ServletActionContext.getRequest().getRemoteAddr()+" - "+ServletActionContext.getRequest().getRemoteHost()+" - " + ServletActionContext.getRequest().getRemotePort();
				Micslogger.log("Login","Login", this.username+" login with wrong password from " + addrHostPort, "", this.username,"FAILED");
				addActionError(MICSConstant.MICS_ERROR_WRONG_USER_PASSWORD);
				result = MICSConstant.MICS_ERROR_NOT_LOGGEDIN;
			}
		}catch (Exception ex){
			String addrHostPort = ServletActionContext.getRequest().getRemoteAddr()+" - "+ServletActionContext.getRequest().getRemoteHost()+" - " + ServletActionContext.getRequest().getRemotePort();
			Micslogger.log("Login","Login", this.username +" failed to login from "+addrHostPort+" cause by " + ex.getMessage(), "", this.username,"FAILED");
			logger.error("Failed to login",ex);
			addActionError("Failed to process your login request");
			result = "error";
		}
		return result;
	}
	
	public String logout() {
		logger.info("Logout()"+ this.username);
		String result = "error";
		String userName = "Unknown";
		String companyKey = "Unknown";
		try{
			Webuser user = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (user!= null) {
				userName = user.getWebusername();
				companyKey = user.getCompanykey();
			}
			session.remove(MICSConstant.MICS_SESSION_USER);
			session.remove(MICSConstant.MICS_SESSION_ROLE);
			session.remove(MICSConstant.MICS_SESSION_CURRCOMPANY);
			try {
				session.clear();
			} catch (Exception e) {
				//No Need to catch just want to clear the session
			}
			result = "logout";
			addActionMessage("You're logged out, thank you " + userName );
			Micslogger.log("Logout","Login", userName +" logged out", companyKey, userName,"SUCCESS");
		}catch (Exception ex){
			logger.error("Failed to logout",ex);
			addActionError("You're logged out, but an error occured "+ ex.getMessage());
			result = "logout";
			Micslogger.log("Logout","Login",userName+" logged out with an error " + ex.getMessage(), companyKey, userName,"SUCCESS");
		}
		return result;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * @return the tselAdmin
	 */
	public boolean isTselAdmin() {
		return tselAdmin;
	}
	
	
	

	/**
	 * @return the headerInfo
	 */
	public String getHeaderInfo() {
		return headerInfo;
	}

	/**
	 * @param headerInfo the headerInfo to set
	 */
	public void setHeaderInfo(String headerInfo) {
		this.headerInfo = headerInfo;
	}

	/**
	 * @param tselAdmin the tselAdmin to set
	 */
	public void setTselAdmin(boolean tselAdmin) {
		this.tselAdmin = tselAdmin;
	}

	public Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;

	}
	private static final Logger logger = Logger.getLogger(LoginAction.class);
	private String username;
	private String password;
	private String headerInfo;
	private boolean tselAdmin;
	private Map<String,Object> session;

}
