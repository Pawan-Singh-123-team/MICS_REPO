package com.nsn.telkomsel.mics.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.nsn.telkomsel.mics.MICSException;
import com.nsn.telkomsel.mics.handler.MICSCommonSessionHandler;
import com.nsn.telkomsel.mics.orahbm.Communitymember;
import com.nsn.telkomsel.mics.orahbm.Community;
import com.nsn.telkomsel.mics.orahbm.Micsuser;
import com.nsn.telkomsel.mics.orahbm.Micsusernumber;
import com.nsn.telkomsel.mics.orahbm.Webuser;
import com.nsn.telkomsel.mics.util.AddNumberRequest;
import com.nsn.telkomsel.mics.util.AddUserRequest;
import com.nsn.telkomsel.mics.util.MICSConstant;
import com.nsn.telkomsel.mics.util.Micslogger;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
/**
 * CommunityAction.java
 * 
 * @author mulia
 * @version $Id: CommunityAction.java,v 1.1.4.3 2019/03/22 10:05:45 cvsuser Exp $
 */
public class CommunityAction extends ActionSupport implements SessionAware,Preparable {

	public String initCreate(){
		logger.info("initCreate()");
		String result = MICSConstant.MICS_ERROR;
		try {
			session.remove(MICSConstant.MICS_SESSION_MEMBERREQ);
			memberReq = null;
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(communityCreate, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS) || 
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION) ||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
					} else {
						this.tselAdmin = false;
					}
					logger.debug("initialization complete");
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
			logger.error("Failed to initialize Community admin",e);
			addActionError("Failed to process your Community administration request " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		logger.debug("result: " + result);
		return result;
	}
	
	public String create(){
		logger.info("create()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(communityCreate, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS) || 
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION) ||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
					} else {
						this.tselAdmin = false;
					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
					sdf.format(new Date());
					String awalKey = sdf.format(new Date());
					String micsCommunityAvailableKey = micsCommonSessionHandler.getAvailableKey(awalKey, "communitykey", "Community");
					logger.error("micsCommunityAvailableKey: " + micsCommunityAvailableKey);
					Community newCommunity = new Community();
					newCommunity.setCommunitykey(micsCommunityAvailableKey);
					newCommunity.setCommunityname(this.communityname);
					newCommunity.setContactperson(this.contactperson);
					newCommunity.setAddress(this.address);
					newCommunity.setEmail(this.email);
					newCommunity.setUpdateuser(loginWebUser.getWebusername());
					newCommunity.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
					micsCommonSessionHandler.createCommunity(newCommunity, memberReq);
					logger.debug("create complete");
					addActionMessage("Successfully create community "+ newCommunity.getCommunityname());
					Micslogger.log("Create","Community", "Create " + newCommunity.getCommunityname(), loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"SUCCESS");
					result = MICSConstant.MICS_SUCCESS;
				} else {
					Micslogger.log("Create","Community", "Create " + this.communityname +", user not authorized", loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"FAILED");
					addActionError("User not authorized");
					result = MICSConstant.MICS_ERROR_NOT_AUTH;
				}
			} else {
				addActionError("User not logged in");
				result = MICSConstant.MICS_ERROR_NOT_LOGGEDIN;
			}
		} catch (Exception e) {
			logger.error("Failed to create Community",e);
			addActionError("Failed to create Community " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		logger.debug("result: " + result);
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
				if (micsCommonSessionHandler.isMenuAuthorized(communitySearch, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS) || 
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION) ||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
					} else {
						this.tselAdmin = false;
					}
					communities = null;
					
					if (searchCnt > 0) {
						communities = micsCommonSessionHandler.searchCommunities(communityname);
					}
					searchCnt++;
							
					if (micsCommonSessionHandler.isMenuAuthorized(communityDelete, loginWebUser.getRolekey())) {
						this.deleteAllowed = true;
					} else {
						this.deleteAllowed = false;
					}
					if (micsCommonSessionHandler.isMenuAuthorized(communityEdit, loginWebUser.getRolekey())) {
						this.editAllowed = true;
					} else {
						this.editAllowed = false;
					}
					logger.debug("search complete");
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
			logger.error("Failed to search Community",e);
			addActionError("Failed to search Community " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		logger.debug("result: " + result);
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
				if (micsCommonSessionHandler.isMenuAuthorized(communityDelete, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS) || 
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION) ||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
					} else {
						this.tselAdmin = false;
					}
					Community delCommunity = micsCommonSessionHandler.getCommunityByKey(communitykey);
					if (delCommunity == null) {
						throw new MICSException("Failed to delete community with key " + communitykey + ", community not found");
					}
					this.communityname = delCommunity.getCommunityname();
					this.contactperson = delCommunity.getContactperson();
					this.address = delCommunity.getAddress();
					this.email = delCommunity.getEmail();
					List delCommunityMember = micsCommonSessionHandler.getUserCommunityMemberByKey(communitykey);
					if (delCommunityMember != null && delCommunityMember.size() > 0) {
						memberReq = new ArrayList<AddUserRequest>();
						for (Iterator iterCommunityMember = delCommunityMember.iterator(); iterCommunityMember
								.hasNext();) {
							Micsuser currMember = (Micsuser) iterCommunityMember.next();
							AddUserRequest currAddUserRequest = new AddUserRequest();
							currAddUserRequest.setMicsCompanyKey(currMember.getCompanykey());
							currAddUserRequest.setMicsFirstName(currMember.getMicsfirstname());
							currAddUserRequest.setMicsLastName(currMember.getMicslastname());
							currAddUserRequest.setMicsUserKey(currMember.getMicsuserkey());
							memberReq.add(currAddUserRequest);
						}
					}
					logger.debug("initialization complete");
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
			logger.error("Failed to initialize delete Community",e);
			addActionError("Failed to process your Community delete request " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		logger.debug("result: " + result);
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
				if (micsCommonSessionHandler.isMenuAuthorized(communityDelete, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS) || 
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION) ||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
					} else {
						this.tselAdmin = false;
					}
					
					Community delCommunity = micsCommonSessionHandler.getCommunityByKey(communitykey);
					if (delCommunity == null) {
						throw new MICSException("Failed to delete community with " + communitykey + ", community not exist");
					}
					List delCommunityMember = micsCommonSessionHandler.getCommunityMemberByKey(communitykey);
					micsCommonSessionHandler.deleteCommunity(delCommunity, delCommunityMember);
					logger.debug("create complete");
					addActionMessage("Successfully delete community "+ delCommunity.getCommunityname());
					Micslogger.log("Delete","Community", "Delete Communinty" + delCommunity.getCommunityname(), loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"SUCCESS");
					result = MICSConstant.MICS_SUCCESS;
				} else {
					addActionError("User not authorized");
					Micslogger.log("Delete","Community", "Delete Communinty" + this.communitykey+". user not authorized", loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"FAILED");
					result = MICSConstant.MICS_ERROR_NOT_AUTH;
				}
			} else {
				addActionError("User not logged in");
				result = MICSConstant.MICS_ERROR_NOT_LOGGEDIN;
			}
		} catch (Exception e) {
			logger.error("Failed to delete Community",e);
			addActionError("Failed to delete Community " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		logger.debug("result: " + result);
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
				if (micsCommonSessionHandler.isMenuAuthorized(communityEdit, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS) || 
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION) ||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
					} else {
						this.tselAdmin = false;
					}
					Community editCommunity = micsCommonSessionHandler.getCommunityByKey(communitykey);
					if (editCommunity == null) {
						throw new MICSException("Failed to edit community with key " + communitykey +", community not found");
					}
					this.communityname = editCommunity.getCommunityname();
					this.contactperson = editCommunity.getContactperson();
					this.address = editCommunity.getAddress();
					this.email = editCommunity.getEmail();
					
					List editCommunityMember = micsCommonSessionHandler.getUserCommunityMemberByKey(communitykey);
					if (editCommunityMember != null && editCommunityMember.size() > 0) {
						memberReq = new ArrayList<AddUserRequest>();
						for (Iterator iterCommunityMember = editCommunityMember.iterator(); iterCommunityMember
								.hasNext();) {
						//	Object[] obj = (Object[]) iterCommunityMember.next();
						//	Micsuser currMember = (Micsuser)obj[0];
							//Micsusernumber currMicsNumberReq = (Micsusernumber)obj[1];
							Micsuser currMember = (Micsuser) iterCommunityMember.next();
							//Micsusernumber currMicsNumberReq = (Micsusernumber) iterCommunityMember.next();							
							AddUserRequest currAddUserRequest = new AddUserRequest();
							currAddUserRequest.setMicsCompanyKey(currMember.getCompanykey());
							currAddUserRequest.setMicsFirstName(currMember.getMicsfirstname());
							currAddUserRequest.setMicsLastName(currMember.getMicslastname());
							currAddUserRequest.setMicsUserKey(currMember.getMicsuserkey());
							//currAddUserRequest.setMicsPrivNumber(currMicsNumberReq.getPrivatenumber());
							//currAddUserRequest.setMicsPubNumber(currMicsNumberReq.getPublicnumber());							
							memberReq.add(currAddUserRequest);
						}
					}
					logger.debug("initialization complete");
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
			logger.error("Failed to initialize edit Community",e);
			addActionError("Failed to process your Community edit request " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		logger.debug("result: " + result);
		return result;
	}
	
	public String edit(){
		logger.info("edit()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(communityEdit, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS) || 
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION) ||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
					} else {
						this.tselAdmin = false;
					}
					
					Community editCommunity = micsCommonSessionHandler.getCommunityByKey(communitykey);
					if (editCommunity == null) {
						throw new MICSException("Failed to edit community with key " + communitykey + ", community not found");
					}
					editCommunity.setCommunityname(this.communityname);
					editCommunity.setContactperson(this.contactperson);
					editCommunity.setAddress(this.address);
					editCommunity.setEmail(this.email);
					editCommunity.setUpdateuser(loginWebUser.getWebusername());
					editCommunity.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
					List needToDelCommunityMember = micsCommonSessionHandler.getCommunityMemberByKey(communitykey);
					
					micsCommonSessionHandler.editCommunity(editCommunity, needToDelCommunityMember, memberReq);
					logger.debug("edit complete");
					addActionMessage("Successfully edit community "+ editCommunity.getCommunityname());
					Micslogger.log("Edit","Community", "Edit Communinty" + editCommunity.getCommunityname(), loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"SUCCESS");
					result = MICSConstant.MICS_SUCCESS;
				} else {
					addActionError("User not authorized");
					Micslogger.log("Edit","Community", "Edit Community" + this.communityname+ " user not authorized", loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"FAILED");
					result = MICSConstant.MICS_ERROR_NOT_AUTH;
				}
			} else {
				addActionError("User not logged in");
				result = MICSConstant.MICS_ERROR_NOT_LOGGEDIN;
			}
		} catch (Exception e) {
			logger.error("Failed to edit Community",e);
			addActionError("Failed to edit Community " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		logger.debug("result: " + result);
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
				if (micsCommonSessionHandler.isMenuAuthorized(communityView, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS) || 
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION) ||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
					} else {
						this.tselAdmin = false;
					}
					Community editCommunity = micsCommonSessionHandler.getCommunityByKey(communitykey);
					if (editCommunity == null) {
						throw new MICSException("Failed to view community with key " + communitykey +", community not found");
					}
					this.communityname = editCommunity.getCommunityname();
					this.contactperson = editCommunity.getContactperson();
					this.address = editCommunity.getAddress();
					this.email = editCommunity.getEmail();
					
					List editCommunityMember = micsCommonSessionHandler.getUserCommunityMemberByKey(communitykey);
					if (editCommunityMember != null && editCommunityMember.size() > 0) {
						memberReq = new ArrayList<AddUserRequest>();
						for (Iterator iterCommunityMember = editCommunityMember.iterator(); iterCommunityMember
								.hasNext();) {
							Micsuser currMember = (Micsuser) iterCommunityMember.next();
							AddUserRequest currAddUserRequest = new AddUserRequest();
							currAddUserRequest.setMicsCompanyKey(currMember.getCompanykey());
							currAddUserRequest.setMicsFirstName(currMember.getMicsfirstname());
							currAddUserRequest.setMicsLastName(currMember.getMicslastname());
							currAddUserRequest.setMicsUserKey(currMember.getMicsuserkey());
							memberReq.add(currAddUserRequest);
						}
					}
					logger.debug("initialization complete");
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
			logger.error("Failed to initialize view Community",e);
			addActionError("Failed to process your Community view request " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		logger.debug("result: " + result);
		return result;
	}
	public String addMember(){
		logger.debug("addMember");
		String result = MICSConstant.MICS_ERROR;
		try {
			if (memberReq == null) {
				memberReq = new ArrayList<AddUserRequest>();
			}
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS) || 
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION) ||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
				this.tselAdmin = true;
			} else {
				this.tselAdmin = false;
			}
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			Micsuser currMicsuserInfo = micsCommonSessionHandler.getEmployeeByKey(employee);
			AddUserRequest currAddMember = new AddUserRequest();
			currAddMember.setMicsUserKey(employee);
			currAddMember.setMicsCompanyKey(currMicsuserInfo.getCompanykey());
			currAddMember.setMicsFirstName(currMicsuserInfo.getMicsfirstname());
			currAddMember.setMicsLastName(currMicsuserInfo.getMicslastname());
			currAddMember.setMicsMemberType(""+currMicsuserInfo.getUsertype());
			memberReq.add(currAddMember);
			session.put(MICSConstant.MICS_SESSION_MEMBERREQ, memberReq);
			result = MICSConstant.MICS_SUCCESS;
		} catch (Exception e) {
			logger.error("Failed to add Community member",e);
			addActionError("Failed to add Community member");
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public String delMember(){
		logger.debug("delMember");
		String result = MICSConstant.MICS_ERROR;
		try {
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS) || 
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION) ||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
				this.tselAdmin = true;
			} else {
				this.tselAdmin = false;
			}
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			List tempMemberReq = new ArrayList<AddUserRequest>();
			for (Iterator iterMember = memberReq.iterator(); iterMember
					.hasNext();) {
				AddUserRequest currDelMember = (AddUserRequest) iterMember.next();
				logger.debug("Member " + currDelMember.getMicsUserKey() + " delete " + currDelMember.isRemove());
				if (currDelMember.isRemove()) {
					logger.debug("Member removed");
				}else {
					logger.debug("Member added");
					tempMemberReq.add(currDelMember);
				}
				
			}
			memberReq = tempMemberReq;
			session.put(MICSConstant.MICS_SESSION_MEMBERREQ, memberReq);
			result = MICSConstant.MICS_SUCCESS;
		} catch (Exception e) {
			logger.error("Failed to del Community member",e);
			addActionError("Failed to del Community member");
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
	}
	
	public String searchMember(){
		logger.info("searchMember()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(communityCreate, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS) || 
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION) ||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
						employeeList = micsCommonSessionHandler.getEmployeesByCriteria(this.searchFirstName, this.searchLastName, this.searchPubNumber, this.searchPrivNumber);
						
						memberReq = (List) session.get(MICSConstant.MICS_SESSION_MEMBERREQ);
						searchMemberRes = new ArrayList<AddUserRequest>();
						for (Iterator iterEmployee = employeeList.iterator(); iterEmployee
								.hasNext();) {
							
							Object[] currObject = (Object[]) iterEmployee.next();
							logger.debug("currObject: " + currObject);
							String currMicsUserKey = currObject[0].toString();
							String currMicsCompanyKey = currObject[1].toString();
							String currMicsFirstName = currObject[2].toString();
							String currMicsLastName = currObject[3].toString();
							String currMicsPubNumber = currObject[4].toString();
							String currMicsPrivNumber = currObject[5].toString();
							
							logger.debug("1 -->" +currMicsUserKey);
							logger.debug("2 -->" +currMicsCompanyKey);
							logger.debug("3 -->" +currMicsFirstName);
							logger.debug("4 -->" +currMicsLastName);
							logger.debug("5 -->" +currMicsPubNumber);
							logger.debug("6 -->" +currMicsPrivNumber);
							AddUserRequest currAddUserReq = new AddUserRequest();
							currAddUserReq.setMicsUserKey(currMicsUserKey);
							currAddUserReq.setMicsCompanyKey(currMicsCompanyKey);
							currAddUserReq.setMicsFirstName(currMicsFirstName);
							currAddUserReq.setMicsLastName(currMicsLastName);
							currAddUserReq.setMicsPubNumber(currMicsPubNumber);
							currAddUserReq.setMicsPrivNumber(currMicsPrivNumber) ;
							if (memberReq != null && !memberReq.isEmpty()) {
								for (Iterator iterSelectedMember = memberReq.iterator(); iterSelectedMember
										.hasNext();) {
									AddUserRequest currSelectedMember = (AddUserRequest) iterSelectedMember.next();
									if (currSelectedMember.getMicsUserKey().equals(currMicsUserKey) ) {
										currAddUserReq.setSelected(true);
									}
								}
							}
							searchMemberRes.add(currAddUserReq);
						}
						session.put(MICSConstant.MICS_SESSION_EMPLOYEERESULT, searchMemberRes);
					} else {
						this.tselAdmin = false;
					}
					logger.debug("initialization complete");
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
			logger.error("Failed to search add member Community admin",e);
			addActionError("Failed to process your search add member Community administration request");
			result = MICSConstant.MICS_ERROR;
		} 
		logger.debug("result: " + result);
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	public String initSearchMember(){
		logger.info("initSearchMember()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(communityCreate, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS) || 
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION) ||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
					} else {
						this.tselAdmin = false;
					}
					memberReq = (List) session.get(MICSConstant.MICS_SESSION_MEMBERREQ);
					if (memberReq == null) {
						logger.debug("memberReq is null, create new list");
						memberReq = new ArrayList<AddUserRequest>();
					} else {
						logger.debug("memberReq is exist use the exisiting");
					}
					logger.debug("initialization complete");
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
			logger.error("Failed to initialize add search member Community admin",e);
			addActionError("Failed to process add search member Community administration request");
			result = MICSConstant.MICS_ERROR;
		} 
		logger.debug("result: " + result);
		return result;
	}
	
	public String searchAddMember(){
		logger.debug("searchAddMember");
		String result = MICSConstant.MICS_ERROR;
		try {
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS) || 
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION) ||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
				this.tselAdmin = true;
			} else {
				this.tselAdmin = false;
			}
//			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			memberReq = (List) session.get(MICSConstant.MICS_SESSION_MEMBERREQ);
			logger.debug("memberReq from session: " + memberReq);
			if (memberReq == null) {
				logger.debug("memberReq is null, create new list");
				memberReq = new ArrayList<AddUserRequest>();
			} else {
				logger.debug("memberReq is exist use the exisiting");
				logger.debug("List member req");
				for (Iterator iterListMemberReqCheck = memberReq.iterator(); iterListMemberReqCheck
						.hasNext();) {
					AddUserRequest checkListMemberReq = (AddUserRequest) iterListMemberReqCheck.next();
					logger.debug("--> " + checkListMemberReq.getMicsUserKey()+" - " + checkListMemberReq.getMicsFirstName() + " " + checkListMemberReq.getMicsLastName() + " " + checkListMemberReq.getMicsPubNumber());
					
				}
			}
			List<AddUserRequest> tempMemberReq = new ArrayList<AddUserRequest>();
			for (Iterator iterMember = searchMemberRes.iterator(); iterMember
					.hasNext();) {
				AddUserRequest currAddMember = (AddUserRequest) iterMember.next();
				logger.debug("Member " + currAddMember.getMicsUserKey() + " - " + currAddMember.getMicsFirstName() +","+ currAddMember.getMicsLastName()+" add " + currAddMember.isSelected());
				if (currAddMember.isSelected()) {
					//Check if Member already in the list
					logger.debug("Check if " + currAddMember.getMicsFirstName() + " already in the list");
					if (memberReq.contains(currAddMember)) {
						logger.debug("Member "+ currAddMember.getMicsFirstName() + ","+ currAddMember.getMicsLastName()+"already in the selected list, skipped");
//						logger.debug("memberReq size : " + memberReq.size());
//						for (Iterator iterSelectedMember = memberReq.iterator(); iterSelectedMember
//								.hasNext();) {
//							AddUserRequest currSelectedMemberReq = (AddUserRequest) iterSelectedMember.next();
//							if (currAddMember.getMicsUserKey().equals(currSelectedMemberReq.getMicsUserKey()) &&
//									currAddMember.getMicsPubNumber().equals(currSelectedMemberReq.getMicsPubNumber()) &&
//									currAddMember.getMicsPrivNumber().equals(currSelectedMemberReq.getMicsPrivNumber())) {
//								 logger.debug("Already in the list skipped");
//							} else {
//								logger.debug("Member added");
//								memberReq.add(currAddMember);
//							}
//						}
					} else {
						logger.debug("New Member, Member added");
						memberReq.add(currAddMember);
					}
				}else {
					logger.debug("Member not selected, skipped");
				}
				
			}
			
			session.put(MICSConstant.MICS_SESSION_MEMBERREQ, memberReq);
			result = MICSConstant.MICS_SUCCESS;
		} catch (Exception e) {
			logger.error("Failed to add Community member",e);
			addActionError("Failed to add Community member");
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
	}
	
	/**
	 * @return the communitykey
	 */
	public String getCommunitykey() {
		return communitykey;
	}
	/**
	 * @param communitykey the communitykey to set
	 */
	public void setCommunitykey(String communitykey) {
		this.communitykey = communitykey;
	}
	/**
	 * @return the communityname
	 */
	public String getCommunityname() {
		return communityname;
	}
	/**
	 * @param communityname the communityname to set
	 */
	public void setCommunityname(String communityname) {
		this.communityname = communityname;
	}
	/**
	 * @return the contactperson
	 */
	public String getContactperson() {
		return contactperson;
	}
	/**
	 * @param contactperson the contactperson to set
	 */
	public void setContactperson(String contactperson) {
		this.contactperson = contactperson;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * @return the employee
	 */
	public String getEmployee() {
		return employee;
	}

	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(String employee) {
		this.employee = employee;
	}

	/**
	 * @return the employeeList
	 */
	public List getEmployeeList() {
		return employeeList;
	}

	/**
	 * @param employeeList the employeeList to set
	 */
	public void setEmployeeList(List employeeList) {
		this.employeeList = employeeList;
	}
	
	

	/**
	 * @return the memberReq
	 */
	public List<AddUserRequest> getMemberReq() {
		return memberReq;
	}

	/**
	 * @param memberReq the memberReq to set
	 */
	public void setMemberReq(List<AddUserRequest> memberReq) {
		this.memberReq = memberReq;
	}
	
	

	/**
	 * @return the searchFirstName
	 */
	public String getSearchFirstName() {
		return searchFirstName;
	}

	/**
	 * @param searchFirstName the searchFirstName to set
	 */
	public void setSearchFirstName(String searchFirstName) {
		this.searchFirstName = searchFirstName;
	}

	/**
	 * @return the searchLastName
	 */
	public String getSearchLastName() {
		return searchLastName;
	}

	/**
	 * @param searchLastName the searchLastName to set
	 */
	public void setSearchLastName(String searchLastName) {
		this.searchLastName = searchLastName;
	}

	/**
	 * @return the searchPrivNumber
	 */
	public String getSearchPrivNumber() {
		return searchPrivNumber;
	}

	/**
	 * @param searchPrivNumber the searchPrivNumber to set
	 */
	public void setSearchPrivNumber(String searchPrivNumber) {
		this.searchPrivNumber = searchPrivNumber;
	}

	/**
	 * @return the searchPubNumber
	 */
	public String getSearchPubNumber() {
		return searchPubNumber;
	}

	/**
	 * @param searchPubNumber the searchPubNumber to set
	 */
	public void setSearchPubNumber(String searchPubNumber) {
		this.searchPubNumber = searchPubNumber;
	}

	/**
	 * @return the searchCompany
	 */
	public String getSearchCompany() {
		return searchCompany;
	}

	/**
	 * @param searchCompany the searchCompany to set
	 */
	public void setSearchCompany(String searchCompany) {
		this.searchCompany = searchCompany;
	}

	
	/**
	 * @return the searchCommunityName
	 */
	public String getSearchCommunityName() {
		return searchCommunityName;
	}

	/**
	 * @param searchCommunityName the searchCommunityName to set
	 */
	public void setSearchCommunityName(String searchCommunityName) {
		this.searchCommunityName = searchCommunityName;
	}

	/**
	 * @return the searchMemberRes
	 */
	public List<AddUserRequest> getSearchMemberRes() {
		return searchMemberRes;
	}

	/**
	 * @param searchMemberRes the searchMemberRes to set
	 */
	public void setSearchMemberRes(List<AddUserRequest> searchMemberRes) {
		this.searchMemberRes = searchMemberRes;
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
	 * @return the communities
	 */
	public List getCommunities() {
		return communities;
	}

	/**
	 * @param communities the communities to set
	 */
	public void setCommunities(List communities) {
		this.communities = communities;
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
	 * @return the session
	 */
	public Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;

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



	private String communitykey;
	private String communityname;
	private String contactperson;
	private String address;
	private String email;
	private String updateuser;
	private Timestamp updatetimestamp;
	private String employee;
	private List employeeList;
	private List<AddUserRequest> memberReq;
	
	private String searchFirstName;
	private String searchLastName;
	private String searchPrivNumber;
	private String searchPubNumber;
	private String searchCompany;
	private String searchCommunityName;
	private List<AddUserRequest> searchMemberRes;
	private List communities;
	private boolean deleteAllowed;
	private boolean editAllowed;
	
	private String communityCreate = "2017050401";
	private String communitySearch = "2017050402";
	private String communityEdit = "2017050403";
	private String communityDelete = "2017050404";
	private String communityView = "2017050405";
	private boolean tselAdmin;
	private Map<String, Object> session;
	private int searchCnt;
	private static final Logger logger = Logger.getLogger(CommunityAction.class);
	@Override
	public void prepare() throws Exception {
		memberReq = (List<AddUserRequest>) session.get(MICSConstant.MICS_SESSION_MEMBERREQ);
		
	}
}
