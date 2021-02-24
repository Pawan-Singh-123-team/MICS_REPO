package com.nsn.telkomsel.mics.action;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.nsn.telkomsel.mics.handler.MICSCommonSessionHandler;
import com.nsn.telkomsel.mics.util.MICSConstant;
import com.opensymphony.xwork2.ActionSupport;

public class ProfileSearchAction extends ActionSupport implements SessionAware {

	
	public void setSession(Map<String, Object> session) {
		this.session = session;

	}
	
	public String search(){
		logger.info("search()");
		String result = MICSConstant.MICS_ERROR;
		try {
			String currSearchProfile = this.profileKey;
			String currSearchCompany = this.companyKey;
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			profiles = null;
			if (searchCnt>0) {
				profiles = micsCommonSessionHandler.getMICSProfileList(currSearchProfile,currSearchCompany);
			}
			searchCnt++;
			
			session.put(MICSConstant.MICS_SESSION_PROFILELIST, profiles);
			result = MICSConstant.MICS_SUCCESS;
		} catch (Exception e) {
			logger.error("Failed to search MICS Profile",e);
			addActionError("Failed to process your MICS Profile search request");
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
	}
	
	/**
	 * @return the profileKey
	 */
	public String getProfileKey() {
		return profileKey;
	}
	/**
	 * @param profileKey the profileKey to set
	 */
	public void setProfileKey(String profileKey) {
		this.profileKey = profileKey;
	}
	/**
	 * @return the companyKey
	 */
	public String getCompanyKey() {
		return companyKey;
	}
	/**
	 * @param companyKey the companyKey to set
	 */
	public void setCompanyKey(String companyKey) {
		this.companyKey = companyKey;
	}


	/**
	 * @return the profiles
	 */
	public List getProfiles() {
		return profiles;
	}

	/**
	 * @param profiles the profiles to set
	 */
	public void setProfiles(List profiles) {
		this.profiles = profiles;
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




	private int searchCnt;
	private String profileKey;
	private String companyKey;
	private List profiles;
	private Map<String, Object> session;
	private static final Logger logger = Logger.getLogger(ProfileSearchAction.class);
}
