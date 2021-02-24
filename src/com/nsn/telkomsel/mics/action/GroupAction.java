package com.nsn.telkomsel.mics.action;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.nsn.telkomsel.mics.MICSException;
import com.nsn.telkomsel.mics.handler.MICSCommonSessionHandler;
import com.nsn.telkomsel.mics.orahbm.Company;
import com.nsn.telkomsel.mics.orahbm.Micsgroup;
import com.nsn.telkomsel.mics.orahbm.MicsgroupId;
import com.nsn.telkomsel.mics.orahbm.Webuser;
import com.nsn.telkomsel.mics.util.MICSConstant;
import com.nsn.telkomsel.mics.util.Micslogger;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * GroupAction.java
 * 
 * @author mulia
 * @version $Id: GroupAction.java,v 1.1.4.3 2019/03/22 10:05:53 cvsuser Exp $
 */
public class GroupAction extends ActionSupport implements SessionAware,Preparable {

	
	private static final long serialVersionUID = 753459531081317030L;
	
	public String initCreate(){
		logger.info("initCreate()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(groupCreate, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
						Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
						if (currCompany == null) {
							throw new Exception("Currently no company selected!, select company first using company search menu before create group");
						}
						this.companyId = currCompany.getCompanykey();
						this.companykey =currCompany.getCompanykey();
						this.companyname = currCompany.getCompanyname();
						logger.debug("companyList: " + companyList);
					} else {
						this.tselAdmin = false;
						
						Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
						if (currCompany == null) {
							throw new Exception("No company found, user not authorized");
						}
						this.companyId = currCompany.getCompanykey();
						this.companykey = currCompany.getCompanykey();
						this.companyname = currCompany.getCompanyname();
						
					}
					logger.debug("tselAdmin: " + tselAdmin);
					outgoingProfiles = micsCommonSessionHandler.getMICSProfileList("", MICSConstant.MICS_SCREENING_TYPE_OUTGOING, MICSConstant.MICS_SCREENING_LEVEL_GROUP);
					session.put(MICSConstant.MICS_SESSION_OUTGOINGLIST, outgoingProfiles);
					incomingProfiles = micsCommonSessionHandler.getMICSProfileList("", MICSConstant.MICS_SCREENING_TYPE_INCOMING, MICSConstant.MICS_SCREENING_LEVEL_GROUP);
					session.put(MICSConstant.MICS_SESSION_INCOMINGLIST, incomingProfiles);
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
			logger.error("Failed to initialize Group admin",e);
			addActionError("Failed to process your Group administration request " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public String create(){
		logger.info("create()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(groupCreate, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
						Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
						if (currCompany == null) {
							throw new Exception("Currently no company selected!, select company first using company search menu before create group");
						}
						this.companyId = currCompany.getCompanykey();
						this.companykey = currCompany.getCompanykey();
						this.companyname = currCompany.getCompanyname();
						logger.debug("companyList: " + companyList);
					} else {
						this.tselAdmin = false;
						this.companyId = loginWebUser.getCompanykey();
						Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
						if (currCompany == null) {
							throw new Exception("No company found, user not authorized");
						}
						this.companykey = currCompany.getCompanykey();
						this.companyname = currCompany.getCompanyname();
					}
					logger.debug("tselAdmin: " + tselAdmin);
					outgoingProfiles = (List) session.get(MICSConstant.MICS_SESSION_OUTGOINGLIST);
					incomingProfiles = (List)session.get(MICSConstant.MICS_SESSION_INCOMINGLIST);
					this.micsgroupkey = this.companykey.trim() + this.groupid;
					//Group can be duplicate (based on uat 28 Oct 2013)
					//revision group can not be duplicate since it will effect on cli (Meeting on 31 Oct 2013)
					Micsgroup oldMicsGroup = micsCommonSessionHandler.getGroupsByKey(this.micsgroupkey);
					if (oldMicsGroup != null) {
						addActionError("Sorry, Group with ID " + this.groupid + " already exist!");
						result = MICSConstant.MICS_INPUT;
					} else {
						Micsgroup newMicsgroup = new Micsgroup(this.micsgroupkey, this.companykey, this.groupid, this.groupname, this.groupcli,loginWebUser.getWebusername(), new Timestamp(System.currentTimeMillis()));
						if (!this.incs.equals("")) {
							if (boolBypassInCS) {
								this.bypassincs = "1";
							} else {
								this.bypassincs = "0";
							}
							newMicsgroup.setIncs(this.incs);
							newMicsgroup.setIncsdesc(this.incsdesc);
							newMicsgroup.setBypassincs(this.bypassincs);
						}
					
						if (!this.outcs.equals("")) {
							if (boolBypassOutCS) {
								this.bypassoutcs = "1";
							} else {
								this.bypassoutcs = "0";
							}
							newMicsgroup.setOutcs(this.outcs);
							newMicsgroup.setOutcsdesc(this.outcsdesc);
							newMicsgroup.setBypassoutcs(this.bypassoutcs);
						}
					
//					MicsgroupId newMicsgroupId = new MicsgroupId(this.micsgroupkey, this.companykey, this.groupid, this.groupname, this.groupcli, this.incs, this.incsdesc, this.bypassincs, this.outcs, this.outcsdesc, this.bypassoutcs,loginWebUser.getWebusername(), new Timestamp(System.currentTimeMillis()));

						micsCommonSessionHandler.createGroup(newMicsgroup);
						addActionMessage("Group " + newMicsgroup.getGroupname() + " successfully created");
						Micslogger.log("Create","Group",  "Group Name " + newMicsgroup.getGroupname() + " ID: " + newMicsgroup.getGroupid() + " CLI: " + newMicsgroup.getGroupcli() +" created", loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"SUCCESS");
						result = MICSConstant.MICS_SUCCESS;
					}
					
				} else {
					Micslogger.log("Create","Group",  "Failed to create Group Name " + this.groupname + " ID: " + this.groupid + " CLI: " + this.groupcli + ", user not authorized!", loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"FAILED");
					addActionError("User not authorized");
					result = MICSConstant.MICS_ERROR_NOT_AUTH;
				}
			} else {
				addActionError("User not logged in");
				Micslogger.log("Create","Group",  "Failed to create Group Name " + this.groupname + " ID: " + this.groupid + " CLI: " + this.groupcli + ", user not logged in!", "", "NoLog","FAILED");
				result = MICSConstant.MICS_ERROR_NOT_LOGGEDIN;
			}
		} catch (Exception e) {
			logger.error("Failed to create Group admin",e);
			addActionError("Failed to process create Group administration request " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
	}
	
	public String search(){
		logger.info("search()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(groupSearch, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
						Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
						if (currCompany == null) {
							throw new Exception("Currently no company selected!, select company first using company search menu");
						}
						this.companyId = currCompany.getCompanykey();
						logger.debug("companyList: " + companyList);
					} else {
						this.tselAdmin = false;
						this.companyId = loginWebUser.getCompanykey();
					}
					if (micsCommonSessionHandler.isMenuAuthorized(groupDelete, loginWebUser.getRolekey())) {
						this.deleteAllowed = true;
					} else {
						this.deleteAllowed = false;
					}
					if (micsCommonSessionHandler.isMenuAuthorized(groupEdit, loginWebUser.getRolekey())) {
						this.editAllowed = true;
					} else {
						this.editAllowed = false;
					}
					logger.debug("tselAdmin: " + tselAdmin);
					String strGroupId = null;
					if (groupid != null) {
						strGroupId = groupid + "";
					}
					if (strGroupId == null) {
						strGroupId = "";
					}
					groups = null;
					if (searchCnt>0) {
						groups = micsCommonSessionHandler.searchGroups(companyId,groupname,strGroupId,groupcli);
					}
					searchCnt++;
					
					logger.debug("groups: " + groups);
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
			logger.error("Failed to create Group admin",e);
			addActionError("Failed to process create Group administration request " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
	}
	
	public String initEdit(){
		logger.info("initEdit()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(groupEdit, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
						Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
						if (currCompany == null) {
							throw new Exception("Currently no company selected!, select company first using company search menu");
						}
						this.companykey = currCompany.getCompanykey();
						this.companyname = currCompany.getCompanyname();
						logger.debug("companyList: " + companyList);
					} else {
						this.tselAdmin = false;
						
						Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
						if (currCompany == null) {
							throw new Exception("No company found, user not authorized");
						}
						this.companykey = currCompany.getCompanykey();
						this.companyname = currCompany.getCompanyname();
						
					}
					logger.debug("tselAdmin: " + tselAdmin);
					Micsgroup currMicsgroup = micsCommonSessionHandler.getGroupsByKey(micsgroupkey);
					this.micsgroupkey = micsgroupkey;
					this.groupid = currMicsgroup.getGroupid();
					this.groupname = currMicsgroup.getGroupname();
					this.groupcli = currMicsgroup.getGroupcli();
					if (currMicsgroup.getOutcs() != null && !currMicsgroup.getOutcs().equals("")) {
						this.outcs = currMicsgroup.getOutcs();
						this.outcsdesc = currMicsgroup.getOutcsdesc();
						if (currMicsgroup.getBypassoutcs().equals("1")) {
							this.boolBypassOutCS = true;
						} else {
							this.boolBypassOutCS = false;
						}
					}
					if (currMicsgroup.getIncs() != null && !currMicsgroup.getIncs().equals("")) {
						this.incs = currMicsgroup.getIncs();
						this.incsdesc = currMicsgroup.getIncsdesc();
						if (currMicsgroup.getBypassincs().equals("1")) {
							this.boolBypassInCS = true;
						} else {
							this.boolBypassInCS = false;
						}
					}
					outgoingProfiles = micsCommonSessionHandler.getMICSProfileList("", MICSConstant.MICS_SCREENING_TYPE_OUTGOING, MICSConstant.MICS_SCREENING_LEVEL_GROUP);
					session.put(MICSConstant.MICS_SESSION_OUTGOINGLIST, outgoingProfiles);
					incomingProfiles = micsCommonSessionHandler.getMICSProfileList("", MICSConstant.MICS_SCREENING_TYPE_INCOMING, MICSConstant.MICS_SCREENING_LEVEL_GROUP);
					session.put(MICSConstant.MICS_SESSION_INCOMINGLIST, incomingProfiles);
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
			logger.error("Failed to initialize Group edit",e);
			addActionError("Failed to process your Group edit request " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
	}
	
	public String initView(){
		logger.info("initView()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(groupView, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
						Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
						if (currCompany == null) {
							throw new Exception("Currently no company selected!, select company first using company search menu");
						}
						this.companykey = currCompany.getCompanykey();
						this.companyname = currCompany.getCompanyname();
						logger.debug("companyList: " + companyList);
					} else {
						this.tselAdmin = false;
						
						Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
						if (currCompany == null) {
							throw new Exception("No company found, user not authorized");
						}
						this.companykey = currCompany.getCompanykey();
						this.companyname = currCompany.getCompanyname();
						
					}
					logger.debug("tselAdmin: " + tselAdmin);
					Micsgroup currMicsgroup = micsCommonSessionHandler.getGroupsByKey(micsgroupkey);
					this.micsgroupkey = micsgroupkey;
					this.groupid = currMicsgroup.getGroupid();
					this.groupname = currMicsgroup.getGroupname();
					this.groupcli = currMicsgroup.getGroupcli();
					if (currMicsgroup.getOutcs() != null && !currMicsgroup.getOutcs().equals("")) {
						this.outcs = currMicsgroup.getOutcs();
						this.outcsdesc = currMicsgroup.getOutcsdesc();
						if (currMicsgroup.getBypassoutcs().equals("1")) {
							this.boolBypassOutCS = true;
						} else {
							this.boolBypassOutCS = false;
						}
					}
					if (currMicsgroup.getIncs() != null && !currMicsgroup.getIncs().equals("")) {
						this.incs = currMicsgroup.getIncs();
						this.incsdesc = currMicsgroup.getIncsdesc();
						if (currMicsgroup.getBypassincs().equals("1")) {
							this.boolBypassInCS = true;
						} else {
							this.boolBypassInCS = false;
						}
					}
					outgoingProfiles = micsCommonSessionHandler.getMICSProfileList("", MICSConstant.MICS_SCREENING_TYPE_OUTGOING, MICSConstant.MICS_SCREENING_LEVEL_GROUP);
					session.put(MICSConstant.MICS_SESSION_OUTGOINGLIST, outgoingProfiles);
					incomingProfiles = micsCommonSessionHandler.getMICSProfileList("", MICSConstant.MICS_SCREENING_TYPE_INCOMING, MICSConstant.MICS_SCREENING_LEVEL_GROUP);
					session.put(MICSConstant.MICS_SESSION_INCOMINGLIST, incomingProfiles);
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
			logger.error("Failed to initialize Group view",e);
			addActionError("Failed to process your Group view request " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public String edit(){
		logger.info("edit()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(groupEdit, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
						Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
						if (currCompany == null) {
							throw new Exception("Currently no company selected!, select company first using company search menu");
						}
						this.companyId = currCompany.getCompanykey();
						logger.debug("companyList: " + companyList);
					} else {
						this.tselAdmin = false;
						this.companyId = loginWebUser.getCompanykey();
					}
					logger.debug("tselAdmin: " + tselAdmin);
					outgoingProfiles = (List) session.get(MICSConstant.MICS_SESSION_OUTGOINGLIST);
					incomingProfiles = (List)session.get(MICSConstant.MICS_SESSION_INCOMINGLIST);
					Micsgroup newMicsgroup = new Micsgroup(this.micsgroupkey, this.companyId, this.groupid, this.groupname, this.groupcli, loginWebUser.getWebusername(), new Timestamp(System.currentTimeMillis()));
					if (!this.incs.equals("")) {
						if (boolBypassInCS) {
							this.bypassincs = "1";
						} else {
							this.bypassincs = "0";
						}
						newMicsgroup.setIncs(this.incs);
						newMicsgroup.setIncsdesc(this.incsdesc);
						newMicsgroup.setBypassincs(this.bypassincs);
					} else {
						newMicsgroup.setIncs("");
						newMicsgroup.setIncsdesc("");
						newMicsgroup.setBypassincs("0");
					}
					
					if (!this.outcs.equals("")) {
						if (boolBypassOutCS) {
							this.bypassoutcs = "1";
						} else {
							this.bypassoutcs = "0";
						}
						newMicsgroup.setOutcs(this.outcs);
						newMicsgroup.setOutcsdesc(this.outcsdesc);
						newMicsgroup.setBypassoutcs(this.bypassoutcs);
					} else {
						newMicsgroup.setOutcs("");
						newMicsgroup.setOutcsdesc("");
						newMicsgroup.setBypassoutcs("0");
					}
//					MicsgroupId newMicsgroupId = new MicsgroupId(this.companyId + this.groupid, this.companyId, this.groupid, this.groupname, this.groupcli, this.incs, this.incsdesc, this.bypassincs, this.outcs, this.outcsdesc, this.bypassoutcs,loginWebUser.getWebusername(), new Timestamp(System.currentTimeMillis()));
					
					micsCommonSessionHandler.editGroup(newMicsgroup);
					addActionMessage("Group " + newMicsgroup.getGroupname() + " successfully edited");
					Micslogger.log("Create","Group",  "Group Name " + newMicsgroup.getGroupname() + " ID: " + newMicsgroup.getGroupid() + " CLI: " + newMicsgroup.getGroupcli() +" edited", loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"SUCCESS");
					result = MICSConstant.MICS_SUCCESS;
				} else {
					addActionError("User not authorized");
					Micslogger.log("Create","Group",  "Failed to create Group Name " + this.groupname + " ID: " + this.groupid + " CLI: " + this.groupcli + ", user not authorized!", loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"FAILED");
					result = MICSConstant.MICS_ERROR_NOT_AUTH;
				}
			} else {
				addActionError("User not logged in");
				Micslogger.log("Create","Group",  "Failed to create Group Name " + this.groupname + " ID: " + this.groupid + " CLI: " + this.groupcli + ", user not logged in!", "", "NoLog","FAILED");
				result = MICSConstant.MICS_ERROR_NOT_LOGGEDIN;
			}
		} catch (Exception e) {
			logger.error("Failed to edit Group admin",e);
			addActionError("Failed to process edit Group administration request " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
	}
	
	public String initDelete(){
		logger.info("initDelete()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(groupDelete, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
						Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
						if (currCompany == null) {
							throw new Exception("Currently no company selected!, select company first using company search menu");
						}
						this.companykey = currCompany.getCompanykey();
						this.companyname = currCompany.getCompanyname();
						logger.debug("companyList: " + companyList);
					} else {
						this.tselAdmin = false;
						Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
						if (currCompany == null) {
							throw new Exception("No company found, user not authorized");
						}
						this.companykey = currCompany.getCompanykey();
						this.companyname = currCompany.getCompanyname();
						
					}
					logger.debug("tselAdmin: " + tselAdmin);
					Micsgroup currMicsgroup = micsCommonSessionHandler.getGroupsByKey(micsgroupkey);
					this.micsgroupkey = currMicsgroup.getMicsgroupkey();
					this.groupid = currMicsgroup.getGroupid();
					this.groupname = currMicsgroup.getGroupname();
					this.groupcli = currMicsgroup.getGroupcli();
					if (currMicsgroup.getOutcs() != null && !currMicsgroup.getOutcs().equals("")) {
						this.outcs = currMicsgroup.getOutcs();
						this.outcsdesc = currMicsgroup.getOutcsdesc();
						if (currMicsgroup.getBypassoutcs().equals("1")) {
							this.boolBypassOutCS = true;
						} else {
							this.boolBypassOutCS = false;
						}
					}
					if (currMicsgroup.getIncs() != null && !currMicsgroup.getIncs().equals("")) {
						this.incs = currMicsgroup.getIncs();
						this.incsdesc = currMicsgroup.getIncsdesc();
						if (currMicsgroup.getBypassincs().equals("1")) {
							this.boolBypassInCS = true;
						} else {
							this.boolBypassInCS = false;
						}
					}
					outgoingProfiles = micsCommonSessionHandler.getMICSProfileList("", MICSConstant.MICS_SCREENING_TYPE_OUTGOING, MICSConstant.MICS_SCREENING_LEVEL_GROUP);
					session.put(MICSConstant.MICS_SESSION_OUTGOINGLIST, outgoingProfiles);
					incomingProfiles = micsCommonSessionHandler.getMICSProfileList("", MICSConstant.MICS_SCREENING_TYPE_INCOMING, MICSConstant.MICS_SCREENING_LEVEL_GROUP);
					session.put(MICSConstant.MICS_SESSION_INCOMINGLIST, incomingProfiles);
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
			logger.error("Failed to initialize Group delete ",e);
			addActionError("Failed to process your Group delete request " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
	}
	
	public String delete(){
		logger.info("delete()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(groupDelete, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
						Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
						if (currCompany == null) {
							throw new Exception("Currently no company selected!, select company first using company search menu");
						}
						this.companyId = currCompany.getCompanykey();
						logger.debug("companyList: " + companyList);
					} else {
						this.tselAdmin = false;
						this.companyId = loginWebUser.getCompanykey();
					}
					logger.debug("tselAdmin: " + tselAdmin);
					Micsgroup currMicsgroup = micsCommonSessionHandler.getGroupsByKey(micsgroupkey);
					if (currMicsgroup == null) {
						throw new MICSException("Group with key " + micsgroupkey + " not found");
						
					}
					micsCommonSessionHandler.deleteGroup(currMicsgroup);
					addActionMessage("Group " + currMicsgroup.getGroupname() + " successfully deleted");
					Micslogger.log("Create","Group",  "Group Name " + currMicsgroup.getGroupname() + " ID: " + currMicsgroup.getGroupid() + " CLI: " + currMicsgroup.getGroupcli() +" deleted", loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"SUCCESS");
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
			logger.error("Failed to delete Group admin",e);
			addActionError("Failed to process delete Group administration request " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
	}
	
	/**
	 * @return the companyId
	 */
	public String getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	/**
	 * @return the companyname
	 */
	public String getCompanyname() {
		return companyname;
	}

	/**
	 * @param companyname the companyname to set
	 */
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	/**
	 * @return the companyList
	 */
	public List getCompanyList() {
		return companyList;
	}

	/**
	 * @param companyList the companyList to set
	 */
	public void setCompanyList(List companyList) {
		this.companyList = companyList;
	}



	/**
	 * @return the tselAdmin
	 */
	public boolean isTselAdmin() {
		return tselAdmin;
	}

	/**
	 * @param tselAdmin the tselAdmin to set
	 */
	public void setTselAdmin(boolean tselAdmin) {
		this.tselAdmin = tselAdmin;
	}

	/**
	 * @return the micsgroupkey
	 */
	public String getMicsgroupkey() {
		return micsgroupkey;
	}
	/**
	 * @param micsgroupkey the micsgroupkey to set
	 */
	public void setMicsgroupkey(String micsgroupkey) {
		this.micsgroupkey = micsgroupkey;
	}
	/**
	 * @return the companykey
	 */
	public String getCompanykey() {
		return companykey;
	}
	/**
	 * @param companykey the companykey to set
	 */
	public void setCompanykey(String companykey) {
		this.companykey = companykey;
	}
	/**
	 * @return the groupid
	 */
	public BigDecimal getGroupid() {
		return groupid;
	}
	/**
	 * @param groupid the groupid to set
	 */
	public void setGroupid(BigDecimal groupid) {
		this.groupid = groupid;
	}
	/**
	 * @return the groupname
	 */
	public String getGroupname() {
		return groupname;
	}
	/**
	 * @param groupname the groupname to set
	 */
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	/**
	 * @return the groupcli
	 */
	public String getGroupcli() {
		return groupcli;
	}
	/**
	 * @param groupcli the groupcli to set
	 */
	public void setGroupcli(String groupcli) {
		this.groupcli = groupcli;
	}
	/**
	 * @return the incs
	 */
	public String getIncs() {
		return incs;
	}
	/**
	 * @param incs the incs to set
	 */
	public void setIncs(String incs) {
		this.incs = incs;
	}
	/**
	 * @return the incsdesc
	 */
	public String getIncsdesc() {
		return incsdesc;
	}
	/**
	 * @param incsdesc the incsdesc to set
	 */
	public void setIncsdesc(String incsdesc) {
		this.incsdesc = incsdesc;
	}
	/**
	 * @return the bypassincs
	 */
	public String getBypassincs() {
		return bypassincs;
	}
	/**
	 * @param bypassincs the bypassincs to set
	 */
	public void setBypassincs(String bypassincs) {
		this.bypassincs = bypassincs;
	}
	/**
	 * @return the boolBypassInCS
	 */
	public boolean isBoolBypassInCS() {
		return boolBypassInCS;
	}
	/**
	 * @param boolBypassInCS the boolBypassInCS to set
	 */
	public void setBoolBypassInCS(boolean boolBypassInCS) {
		this.boolBypassInCS = boolBypassInCS;
	}
	/**
	 * @return the outcs
	 */
	public String getOutcs() {
		return outcs;
	}
	/**
	 * @param outcs the outcs to set
	 */
	public void setOutcs(String outcs) {
		this.outcs = outcs;
	}
	/**
	 * @return the outcsdesc
	 */
	public String getOutcsdesc() {
		return outcsdesc;
	}
	/**
	 * @param outcsdesc the outcsdesc to set
	 */
	public void setOutcsdesc(String outcsdesc) {
		this.outcsdesc = outcsdesc;
	}
	/**
	 * @return the bypassoutcs
	 */
	public String getBypassoutcs() {
		return bypassoutcs;
	}
	/**
	 * @param bypassoutcs the bypassoutcs to set
	 */
	public void setBypassoutcs(String bypassoutcs) {
		this.bypassoutcs = bypassoutcs;
	}
	/**
	 * @return the boolBypassOutCS
	 */
	public boolean isBoolBypassOutCS() {
		return boolBypassOutCS;
	}
	/**
	 * @param boolBypassOutCS the boolBypassOutCS to set
	 */
	public void setBoolBypassOutCS(boolean boolBypassOutCS) {
		this.boolBypassOutCS = boolBypassOutCS;
	}
	/**
	 * @return the incomingProfiles
	 */
	public List getIncomingProfiles() {
		return incomingProfiles;
	}
	/**
	 * @param incomingProfiles the incomingProfiles to set
	 */
	public void setIncomingProfiles(List incomingProfiles) {
		this.incomingProfiles = incomingProfiles;
	}
	/**
	 * @return the outgoingProfiles
	 */
	public List getOutgoingProfiles() {
		return outgoingProfiles;
	}
	/**
	 * @param outgoingProfiles the outgoingProfiles to set
	 */
	public void setOutgoingProfiles(List outgoingProfiles) {
		this.outgoingProfiles = outgoingProfiles;
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
	 * @return the session
	 */
	public Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;

	}
	
	
	/**
	 * @return the deleteAllowed
	 */
	public boolean isDeleteAllowed() {
		return deleteAllowed;
	}

	/**
	 * @param deleteAllowed the deleteAllowed to set
	 */
	public void setDeleteAllowed(boolean deleteAllowed) {
		this.deleteAllowed = deleteAllowed;
	}

	/**
	 * @return the editAllowed
	 */
	public boolean isEditAllowed() {
		return editAllowed;
	}

	/**
	 * @param editAllowed the editAllowed to set
	 */
	public void setEditAllowed(boolean editAllowed) {
		this.editAllowed = editAllowed;
	}

	/**
	 * @return the groups
	 */
	public List getGroups() {
		return groups;
	}

	/**
	 * @param groups the groups to set
	 */
	public void setGroups(List groups) {
		this.groups = groups;
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




	private String groupCreate = "2017050201";
	private String groupSearch = "2017050202";
	private String groupEdit = "2017050203";
	private String groupDelete = "2017050204";
	private String groupView = "2017050205";
	private String micsgroupkey;
	private String companykey;
	private String companyname;
	private String companyId;
	private BigDecimal groupid;
	private String groupname;
	private String groupcli;
	private String incs;
	private String incsdesc;
	private String bypassincs;
	private boolean boolBypassInCS;
	private String outcs;
	private String outcsdesc;
	private String bypassoutcs;
	private boolean boolBypassOutCS;
	private List incomingProfiles;
	private List outgoingProfiles;
	private List companyList;
	private List groups;
	private String updateuser;
	private Timestamp updatetimestamp;
	private boolean tselAdmin;
	private boolean deleteAllowed;
	private boolean editAllowed;
	private int searchCnt;
	private Map<String, Object> session;
	public static final Logger logger = Logger.getLogger(GroupAction.class);

	@Override
	public void prepare() throws Exception {
		MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
		outgoingProfiles = micsCommonSessionHandler.getMICSProfileList("", MICSConstant.MICS_SCREENING_TYPE_OUTGOING, MICSConstant.MICS_SCREENING_LEVEL_GROUP);
		session.put(MICSConstant.MICS_SESSION_OUTGOINGLIST, outgoingProfiles);
		incomingProfiles = micsCommonSessionHandler.getMICSProfileList("", MICSConstant.MICS_SCREENING_TYPE_INCOMING, MICSConstant.MICS_SCREENING_LEVEL_GROUP);
		session.put(MICSConstant.MICS_SESSION_INCOMINGLIST, incomingProfiles);
		
	}

}
