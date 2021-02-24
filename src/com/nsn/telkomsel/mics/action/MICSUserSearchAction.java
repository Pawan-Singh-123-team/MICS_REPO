package com.nsn.telkomsel.mics.action;

import java.util.Map;
import java.util.List;
import org.apache.log4j.Logger;

import org.apache.struts2.interceptor.SessionAware;

import com.nsn.telkomsel.mics.handler.MICSCommonSessionHandler;
import com.nsn.telkomsel.mics.util.MICSConstant;
import com.opensymphony.xwork2.ActionSupport;

public class MICSUserSearchAction extends ActionSupport implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5673369381880408881L;

	public String search(){
		logger.info("search()");
		String result = MICSConstant.MICS_ERROR;
		try {
			String currSearchUser = this.userName;
			String currSearchCompany = this.companyName;
			String currSearchRole = this.userRole;
			String currSearchNik = this.userNik;
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			//Get available Roles
			role = micsCommonSessionHandler.getRoleList();
			webuser = null;
			if (searchCnt>0) {
				webuser = micsCommonSessionHandler.getMICSUserList(currSearchUser,currSearchCompany,currSearchNik,currSearchRole);
			}
			searchCnt++;
			
			session.put(MICSConstant.MICS_SESSION_WEBUSERLIST, webuser);
			result = MICSConstant.MICS_SUCCESS;
		} catch (Exception e) {
			logger.error("Failed to initialize MICS user admin",e);
			addActionError("Failed to process your MICS User admin request");
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	

	/**
	 * @return the userNik
	 */
	public String getUserNik() {
		return userNik;
	}

	/**
	 * @param userNik the userNik to set
	 */
	public void setUserNik(String userNik) {
		this.userNik = userNik;
	}

	/**
	 * @return the userRole
	 */
	public String getUserRole() {
		return userRole;
	}

	/**
	 * @param userRole the userRole to set
	 */
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	

	/**
	 * @return the role
	 */
	public List getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(List role) {
		this.role = role;
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
	
	@SuppressWarnings("rawtypes")
	public List getWebuser() {
		return webuser;
	}

	@SuppressWarnings("rawtypes")
	public void setWebuser(List webuser) {
		this.webuser = webuser;
	}
	
	
	
	/**
	 * @return the searchCnt
	 */
	public int getSearchCnt() {
		return searchCnt;
	}

	/**
	 * @param searchCnt the searchCnt to set
	 */
	public void setSearchCnt(int searchCnt) {
		this.searchCnt = searchCnt;
	}



	private String userName;
	private String companyName;
	private String userNik;
	private String userRole;
	private List role;
	@SuppressWarnings("rawtypes")
	private List webuser;
	private int searchCnt;

	private Map<String,Object> session;
	private static final Logger logger = Logger.getLogger(MICSUserSearchAction.class);

}
