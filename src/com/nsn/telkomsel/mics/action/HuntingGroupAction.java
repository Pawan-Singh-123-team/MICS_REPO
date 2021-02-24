package com.nsn.telkomsel.mics.action;

import java.math.BigDecimal;
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
import com.nsn.telkomsel.mics.orahbm.Announcement;
import com.nsn.telkomsel.mics.orahbm.Company;
import com.nsn.telkomsel.mics.orahbm.Huntinggroup;
import com.nsn.telkomsel.mics.orahbm.Huntingschedule;
import com.nsn.telkomsel.mics.orahbm.Timeband;
import com.nsn.telkomsel.mics.orahbm.Webuser;
import com.nsn.telkomsel.mics.util.AddHuntingSchedule;
import com.nsn.telkomsel.mics.util.AddNumberRequest;
import com.nsn.telkomsel.mics.util.AddUserRequest;
import com.nsn.telkomsel.mics.util.MICSConstant;
import com.nsn.telkomsel.mics.util.Micslogger;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
/**
 * HuntingGroupAction.java
 * 
 * @author mulia
 * @version $Id: HuntingGroupAction.java,v 1.1.4.3 2019/03/22 10:05:54 cvsuser Exp $
 */
public class HuntingGroupAction extends ActionSupport implements SessionAware,Preparable {


	private static final long serialVersionUID = -5357218144338908860L;
	
	public String initCreate(){
		logger.info("initCreate()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(huntinggroupCreate, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
						Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
						if(currCompany == null) throw new Exception ("Currently no company selected! please select company first.");
						this.companykey = currCompany.getCompanykey();
						logger.debug("currCompany: " + currCompany.getCompanyname());
						this.huntingoptionList = micsCommonSessionHandler.getHuntingOptions();
						this.timewindowList = micsCommonSessionHandler.getTimeWindows();
						this.annoList = micsCommonSessionHandler.getCompanyAnno(currCompany.getCompanykey());
						session.put(MICSConstant.MICS_SESSION_ANNOLIST, this.annoList);
						session.put(MICSConstant.MICS_SESSION_TIMEWINDOWLIST, this.timewindowList);
						session.put(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST, this.huntingoptionList);
					} else {
						this.tselAdmin = false;
						this.companykey = loginWebUser.getCompanykey();
						this.annoList = micsCommonSessionHandler.getCompanyAnno(companykey);
						this.huntingoptionList = micsCommonSessionHandler.getHuntingOptions();
						this.timewindowList = micsCommonSessionHandler.getTimeWindows();
						logger.debug("annoList: " + annoList);
						session.put(MICSConstant.MICS_SESSION_ANNOLIST, annoList);
						session.put(MICSConstant.MICS_SESSION_TIMEWINDOWLIST, this.timewindowList);
						session.put(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST, this.huntingoptionList);
					}
					this.huntingscheduleReg = null;
					session.remove(MICSConstant.MICS_SESSION_HUNTINGSCHEDULEREQ);
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
			logger.error("Failed to initialize HuntingGroup admin",e);
			addActionError("Failed to process your HuntingGroup administration request "+ e.getMessage());
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
				if (micsCommonSessionHandler.isMenuAuthorized(huntinggroupCreate, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
						Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
						if(currCompany == null) throw new Exception ("Currently no company selected! please select company first.");
						this.companykey = currCompany.getCompanykey();
						logger.debug("currCompany: " + currCompany.getCompanyname());
						this.huntingoptionList = (List) session.get(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST);
						this.timewindowList = (List) session.get(MICSConstant.MICS_SESSION_TIMEWINDOWLIST);
						this.annoList = (List) session.get(MICSConstant.MICS_SESSION_ANNOLIST);
						
					} else {
						this.tselAdmin = false;
						this.companykey = loginWebUser.getCompanykey();
						this.huntingoptionList = (List) session.get(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST);
						this.timewindowList = (List) session.get(MICSConstant.MICS_SESSION_TIMEWINDOWLIST);
						this.annoList = (List) session.get(MICSConstant.MICS_SESSION_ANNOLIST);
					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
					sdf.format(new Date());
					String awalKey = sdf.format(new Date());
					String micsHGAvailableKey = micsCommonSessionHandler.getAvailableKey(awalKey, "huntinggroupkey", "Huntinggroup");
					logger.debug("micsHGAvailableKey: "+micsHGAvailableKey);
					Huntinggroup newHuntinggroup = new Huntinggroup();
					newHuntinggroup.setCompanykey(this.companykey);
					newHuntinggroup.setHuntingcliprefix(this.huntingcliprefix);
					newHuntinggroup.setHuntinggroupkey(micsHGAvailableKey);
					newHuntinggroup.setHuntinggroupname(this.huntinggroupname);
					newHuntinggroup.setHuntingoption(new BigDecimal(this.huntingoption));
					if (boolReverseCharging) {
						newHuntinggroup.setReverseCharging(new BigDecimal(1));
					} else {
						newHuntinggroup.setReverseCharging(new BigDecimal(0));
					}
					newHuntinggroup.setMembernrannoid(new BigDecimal(this.membernrannoid));
					newHuntinggroup.setMembernaannoid(new BigDecimal(this.membernaannoid));
					newHuntinggroup.setWelcomeannoid(new BigDecimal(this.welcomeannoid));
					newHuntinggroup.setPrivatenumber(this.privatenumber);
					newHuntinggroup.setPublicnumber(this.publicnumber);
					newHuntinggroup.setUpdateuser(loginWebUser.getWebusername());
					newHuntinggroup.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
					//Add create to micshandler
					huntingscheduleReg = (List<AddHuntingSchedule>) session.get(MICSConstant.MICS_SESSION_HUNTINGSCHEDULEREQ);
					logger.debug("huntingscheduleReg: " + huntingscheduleReg);
					micsCommonSessionHandler.createHuntinggroup(newHuntinggroup,huntingscheduleReg);
					addActionMessage("Huntinggroup " + newHuntinggroup.getHuntinggroupname() + " successfully created");
					Micslogger.log("Create","Hunting Group",  newHuntinggroup.getHuntinggroupname() + " created", loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"SUCCESS");
					logger.debug("create complete");
					result = MICSConstant.MICS_SUCCESS;
				} else {
					Micslogger.log("Create","Hunting Group", "Failed to create " +  this.huntinggroupname, loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"FAILED");
					addActionError("User not authorized");
					result = MICSConstant.MICS_ERROR_NOT_AUTH;
				}
			} else {
				Micslogger.log("Create","Hunting Group", "Failed to create " +  this.huntinggroupname, "", "NoLog","FAILED");
				addActionError("User not logged in");
				result = MICSConstant.MICS_ERROR_NOT_LOGGEDIN;
			}
		} catch (Exception e) {
			logger.error("Failed to initialize HuntingGroup admin",e);
			addActionError("Failed to process your HuntingGroup administration request "+ e.getMessage());
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
				if (micsCommonSessionHandler.isMenuAuthorized(huntinggroupSearch, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
						Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
						if(currCompany == null) throw new Exception ("Currently no company selected! please select company first.");
						this.companykey = currCompany.getCompanykey();
						logger.debug("currCompany: " + currCompany.getCompanyname());
						this.huntingoptionList = (List) session.get(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST);
						this.timewindowList = (List) session.get(MICSConstant.MICS_SESSION_TIMEWINDOWLIST);
						this.annoList = (List) session.get(MICSConstant.MICS_SESSION_ANNOLIST);
						
					} else {
						this.tselAdmin = false;
						this.companykey = loginWebUser.getCompanykey();
						this.huntingoptionList = (List) session.get(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST);
						this.timewindowList = (List) session.get(MICSConstant.MICS_SESSION_TIMEWINDOWLIST);
						this.annoList = (List) session.get(MICSConstant.MICS_SESSION_ANNOLIST);
					}
					huntinggroups = null;
					if (searchCnt>0) {
						huntinggroups =  micsCommonSessionHandler.searchHuntinggroups(companykey,huntinggroupname,publicnumber,privatenumber);
					}
					searchCnt++;
					
					
					if (micsCommonSessionHandler.isMenuAuthorized(huntinggroupDelete, loginWebUser.getRolekey())) {
						this.deleteAllowed = true;
					} else {
						this.deleteAllowed = false;
					}
					if (micsCommonSessionHandler.isMenuAuthorized(huntinggroupEdit, loginWebUser.getRolekey())) {
						this.editAllowed = true;
					} else {
						this.editAllowed = false;
					}
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
			logger.error("Failed to search HuntingGroup",e);
			addActionError("Failed to process your HuntingGroup search request "+ e.getMessage());
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
				if (micsCommonSessionHandler.isMenuAuthorized(huntinggroupDelete, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
						Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
						if(currCompany == null) throw new Exception ("Currently no company selected! please select company first.");
						this.companykey = currCompany.getCompanykey();
						logger.debug("currCompany: " + currCompany.getCompanyname());
						this.huntingoptionList = micsCommonSessionHandler.getHuntingOptions();
						this.timewindowList = micsCommonSessionHandler.getTimeWindows();
						this.annoList = micsCommonSessionHandler.getCompanyAnno(currCompany.getCompanykey());
						session.put(MICSConstant.MICS_SESSION_ANNOLIST, this.annoList);
						session.put(MICSConstant.MICS_SESSION_TIMEWINDOWLIST, this.timewindowList);
						session.put(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST, this.huntingoptionList);
					} else {
						this.tselAdmin = false;
						this.companykey = loginWebUser.getCompanykey();
						this.annoList = micsCommonSessionHandler.getCompanyAnno(companykey);
						this.huntingoptionList = micsCommonSessionHandler.getHuntingOptions();
						this.timewindowList = micsCommonSessionHandler.getTimeWindows();
						logger.debug("annoList: " + annoList);
						session.put(MICSConstant.MICS_SESSION_ANNOLIST, annoList);
						session.put(MICSConstant.MICS_SESSION_TIMEWINDOWLIST, this.timewindowList);
						session.put(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST, this.huntingoptionList);
					}
					Huntinggroup dbHuntingGroup = micsCommonSessionHandler.getHuntinggroupByKey(huntinggroupkey);
					if (dbHuntingGroup == null) {
						throw new MICSException("Sorry unable to process your request, Huntinggroup with key " + huntinggroupkey + " not found!" );
					}
					if (!dbHuntingGroup.getCompanykey().equals(this.companykey)) {
						throw new MICSException("Sorry you are unauthorized to delete this huntinggroup!");
					}
					//Set Hunting Group properties to display
					huntinggroupkey = dbHuntingGroup.getHuntinggroupkey();
					logger.debug("huntinggroupkey: " + huntinggroupkey);
					huntinggroupname = dbHuntingGroup.getHuntinggroupname();
					privatenumber = dbHuntingGroup.getPrivatenumber();
					publicnumber = dbHuntingGroup.getPublicnumber();
					welcomeannoid = dbHuntingGroup.getWelcomeannoid().intValue();
					membernaannoid = dbHuntingGroup.getMembernaannoid().intValue();
					membernrannoid = dbHuntingGroup.getMembernrannoid().intValue();
					huntingcliprefix = dbHuntingGroup.getHuntingcliprefix();
					huntingoption = dbHuntingGroup.getHuntingoption().intValue();
					BigDecimal bdReverseCharging = dbHuntingGroup.getReverseCharging();
					if (bdReverseCharging != null) {
						if (bdReverseCharging.intValue() == 1) {
							this.boolReverseCharging = true;
						} else {
							this.boolReverseCharging = false;
						}
					} else {
						this.boolReverseCharging = false;
					}
					List dbHuntingschedules = micsCommonSessionHandler.getHuntingscheduleWithTimebandByHuntinggroupKey(huntinggroupkey);
					if (dbHuntingschedules != null && dbHuntingschedules.size() > 0) {
						huntingscheduleReg = new ArrayList<AddHuntingSchedule>();
						for (Iterator iterHuntingSchedule = dbHuntingschedules.iterator(); iterHuntingSchedule
								.hasNext();) {
							Object[] currScheduleTimeband = (Object[]) iterHuntingSchedule.next();
							Huntingschedule currHS = (Huntingschedule) currScheduleTimeband[0];
							Timeband currTimeband = (Timeband) currScheduleTimeband[1];
							AddHuntingSchedule editHS = new AddHuntingSchedule();
							editHS.setHuntinggroupkey(currHS.getHuntinggroupkey());
							editHS.setHuntingschedulekey(currHS.getHuntingschedulekey());
							editHS.setOrderindex(currHS.getOrderindex().intValue());
							editHS.setTimebandkey(currHS.getTimebandkey());
							editHS.setTimebandname(currTimeband.getId().getTimebandname());
							editHS.setTimebanddesc(currTimeband.getTimebanddescription());
							//Get Member
							List dbHSMember = micsCommonSessionHandler.getHuntingmemberByHuntingscheduleKey(currHS.getHuntingschedulekey());
							if (dbHSMember != null && dbHSMember.size() > 0) {
								memberReq = new ArrayList<AddUserRequest>();
								for (Iterator iterHSMember = dbHSMember.iterator(); iterHSMember
										.hasNext();) {
									Object[] currObject = (Object[]) iterHSMember.next();
									logger.debug("currObject: " + currObject);
									String currMicsUserKey = currObject[0].toString();
									String currMicsFirstName = currObject[1].toString();
									String currMicsLastName = currObject[2].toString();
									String currMicsPubNumber = currObject[3].toString();
									String currMicsPrivNumber = currObject[4].toString();
									
									AddUserRequest currAddUser = new AddUserRequest();
									currAddUser.setMicsUserKey(currMicsUserKey);
									currAddUser.setMicsFirstName(currMicsFirstName);
									currAddUser.setMicsLastName(currMicsLastName);
									currAddUser.setMicsPubNumber(currMicsPubNumber);
									currAddUser.setMicsPrivNumber(currMicsPrivNumber);
									memberReq.add(currAddUser);
									
								}
							}
							editHS.setMemberReq(memberReq);
							huntingscheduleReg.add(editHS);
							
							
						}
						
					}
					session.put(MICSConstant.MICS_SESSION_HUNTINGSCHEDULEREQ, huntingscheduleReg);
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
			logger.error("Failed to initialize HuntingGroup delete",e);
			addActionError("Failed to process your HuntingGroup delete request "+ e.getMessage());
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
				logger.debug("loginWebUser "+ loginWebUser.getWebuserkey() + " " +loginWebUser.getRolekey());
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(huntinggroupEdit, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					Huntinggroup dbHuntingGroup = micsCommonSessionHandler.getHuntinggroupByKey(huntinggroupkey);
					if (dbHuntingGroup == null) {
						throw new MICSException("Sorry unable to process your request, Huntinggroup with key " + huntinggroupkey + " not found!" );
					}
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
						Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
						if(currCompany == null) throw new Exception ("Currently no company selected! please select company first.");
						this.companykey = currCompany.getCompanykey();
						logger.debug("currCompany: " + currCompany.getCompanyname());
						this.huntingoptionList = micsCommonSessionHandler.getHuntingOptions();
						this.timewindowList = micsCommonSessionHandler.getTimeWindows();
						this.annoList = micsCommonSessionHandler.getCompanyAnno(currCompany.getCompanykey());
						session.put(MICSConstant.MICS_SESSION_ANNOLIST, this.annoList);
						session.put(MICSConstant.MICS_SESSION_TIMEWINDOWLIST, this.timewindowList);
						session.put(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST, this.huntingoptionList);
					} else {
						this.tselAdmin = false;
						this.companykey = loginWebUser.getCompanykey();
						this.annoList = micsCommonSessionHandler.getCompanyAnno(companykey);
						this.huntingoptionList = micsCommonSessionHandler.getHuntingOptions();
						this.timewindowList = micsCommonSessionHandler.getTimeWindows();
						if (!dbHuntingGroup.getCompanykey().equals(this.companykey)) {
							throw new MICSException("Sorry you are unauthorized to edit this huntinggroup!");
						}
						logger.debug("annoList: " + annoList);
						session.put(MICSConstant.MICS_SESSION_ANNOLIST, annoList);
						session.put(MICSConstant.MICS_SESSION_TIMEWINDOWLIST, this.timewindowList);
						session.put(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST, this.huntingoptionList);
					}
					
					
					//Set Hunting Group properties to display
					huntinggroupkey = dbHuntingGroup.getHuntinggroupkey();
					logger.debug("huntinggroupkey: " + huntinggroupkey+ " "+ dbHuntingGroup.toString());
					huntinggroupname = dbHuntingGroup.getHuntinggroupname();
					privatenumber = dbHuntingGroup.getPrivatenumber();
					publicnumber = dbHuntingGroup.getPublicnumber();
					welcomeannoid = dbHuntingGroup.getWelcomeannoid().intValue();
					membernaannoid = dbHuntingGroup.getMembernaannoid().intValue();
					membernrannoid = dbHuntingGroup.getMembernrannoid().intValue();
					huntingcliprefix = dbHuntingGroup.getHuntingcliprefix();
					huntingoption = dbHuntingGroup.getHuntingoption().intValue();
					BigDecimal bdReverseCharging = dbHuntingGroup.getReverseCharging();
					if (bdReverseCharging != null) {
						if (bdReverseCharging.intValue() == 1) {
							this.boolReverseCharging = true;
						} else {
							this.boolReverseCharging = false;
						}
					} else {
						this.boolReverseCharging = false;
					}
					List dbHuntingschedules = micsCommonSessionHandler.getHuntingscheduleWithTimebandByHuntinggroupKey(huntinggroupkey);
					if (dbHuntingschedules != null && dbHuntingschedules.size() > 0) {
						huntingscheduleReg = new ArrayList<AddHuntingSchedule>();
						for (Iterator iterHuntingSchedule = dbHuntingschedules.iterator(); iterHuntingSchedule
								.hasNext();) {
							Object[] currScheduleTimeband = (Object[]) iterHuntingSchedule.next();
							Huntingschedule currHS = (Huntingschedule) currScheduleTimeband[0];
							Timeband currTimeband = (Timeband) currScheduleTimeband[1];
							AddHuntingSchedule editHS = new AddHuntingSchedule();
							editHS.setHuntinggroupkey(currHS.getHuntinggroupkey());
							editHS.setHuntingschedulekey(currHS.getHuntingschedulekey());
							editHS.setOrderindex(currHS.getOrderindex().intValue());
							editHS.setTimebandkey(currHS.getTimebandkey());
							editHS.setTimebandname(currTimeband.getId().getTimebandname());
							editHS.setTimebanddesc(currTimeband.getTimebanddescription());
							editHS.setActionType("");
							//Get Member
							List dbHSMember = micsCommonSessionHandler.getHuntingmemberByHuntingscheduleKey(currHS.getHuntingschedulekey());
							if (dbHSMember != null && dbHSMember.size() > 0) {
								memberReq = null;
								memberReq = new ArrayList<AddUserRequest>();
								
								for (Iterator iterHSMember = dbHSMember.iterator(); iterHSMember
										.hasNext();) {
									Object[] currObject = (Object[]) iterHSMember.next();
									logger.debug("currObject: " + currObject);
									Object objMicsUserKey = currObject[0];
									if (objMicsUserKey == null) {
										objMicsUserKey = "";
									}
									String currMicsUserKey = objMicsUserKey.toString();
									
									Object objMicsFirstName = currObject[1];
									if (objMicsFirstName == null) {
										objMicsFirstName = "";
									}
									String currMicsFirstName = objMicsFirstName.toString();
									
									Object objMicsLastName = currObject[2];
									if (objMicsLastName == null) {
										objMicsLastName = "";
									}
									String currMicsLastName =objMicsLastName.toString();
									
									Object objMicsPubNumber = currObject[3];
									if (objMicsPubNumber == null) {
										objMicsPubNumber = "";
									}
									String currMicsPubNumber = objMicsPubNumber.toString();
									
									Object objMicsPrivNumber = currObject[4];
									if (objMicsPrivNumber == null) {
										objMicsPrivNumber = "";
									}
									String currMicsPrivNumber = objMicsPrivNumber.toString();
									
									Object objMemberKey = currObject[5];
									if (objMemberKey == null) {
										objMemberKey = "";
									}
									String currMemberKey = objMemberKey.toString();
									
									Object objOrderIdx = currObject[6];
									if (objOrderIdx == null) {
										objOrderIdx = -1;
									}
									BigDecimal currOrderIdx = (BigDecimal)objOrderIdx;
									AddUserRequest currAddUser = new AddUserRequest();
									currAddUser.setMemberKey(currMemberKey);
									currAddUser.setMicsUserKey(currMicsUserKey);
									currAddUser.setMicsFirstName(currMicsFirstName);
									currAddUser.setMicsLastName(currMicsLastName);
									currAddUser.setMicsPubNumber(currMicsPubNumber);
									currAddUser.setMicsPrivNumber(currMicsPrivNumber);
									currAddUser.setOrderindex(currOrderIdx.intValue());
									currAddUser.setActionType("");
									memberReq.add(currAddUser);
									
								}
							}
							editHS.setMemberReq(memberReq);
							huntingscheduleReg.add(editHS);
							
							
						}
						
					}
					session.put(MICSConstant.MICS_SESSION_HUNTINGSCHEDULEREQ, huntingscheduleReg);
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
			logger.error("Failed to initialize HuntingGroup edit",e);
			addActionError("Failed to process your HuntingGroup edit request "+ e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		logger.debug("result: " + result);
		return result;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String edit(){
		logger.info("edit()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(huntinggroupEdit, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
						Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
						if(currCompany == null) throw new Exception ("Currently no company selected! please select company first.");
						this.companykey = currCompany.getCompanykey();
						logger.debug("currCompany: " + currCompany.getCompanyname());
						this.huntingoptionList = (List) session.get(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST);
						this.timewindowList = (List) session.get(MICSConstant.MICS_SESSION_TIMEWINDOWLIST);
						this.annoList = (List) session.get(MICSConstant.MICS_SESSION_ANNOLIST);
					} else {
						this.tselAdmin = false;
						this.companykey = loginWebUser.getCompanykey();
						this.huntingoptionList = (List) session.get(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST);
						this.timewindowList = (List) session.get(MICSConstant.MICS_SESSION_TIMEWINDOWLIST);
						this.annoList = (List) session.get(MICSConstant.MICS_SESSION_ANNOLIST);
					}
					logger.debug("huntinggroupkey: " + huntinggroupkey);
					Huntinggroup newHuntinggroup = micsCommonSessionHandler.getHuntinggroupByKey(huntinggroupkey);
					if (newHuntinggroup == null) {
						throw new MICSException("Sorry unable to process your request, Huntinggroup with key " + huntinggroupkey + " not found!" );
					}
					newHuntinggroup.setCompanykey(this.companykey);
					newHuntinggroup.setHuntingcliprefix(this.huntingcliprefix);
					newHuntinggroup.setHuntinggroupname(this.huntinggroupname);
					newHuntinggroup.setHuntingoption(new BigDecimal(this.huntingoption));
					if (boolReverseCharging) {
						newHuntinggroup.setReverseCharging(new BigDecimal(1));
					} else {
						newHuntinggroup.setReverseCharging(new BigDecimal(0));
					}
					newHuntinggroup.setMembernrannoid(new BigDecimal(this.membernrannoid));
					newHuntinggroup.setMembernaannoid(new BigDecimal(this.membernaannoid));
					newHuntinggroup.setWelcomeannoid(new BigDecimal(this.welcomeannoid));
					newHuntinggroup.setPrivatenumber(this.privatenumber);
					newHuntinggroup.setPublicnumber(this.publicnumber);
					newHuntinggroup.setUpdateuser(loginWebUser.getWebusername());
					newHuntinggroup.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));

					huntingscheduleReg = (List<AddHuntingSchedule>) session.get(MICSConstant.MICS_SESSION_HUNTINGSCHEDULEREQ);
					delHuntingscheduleReq =  (List<AddHuntingSchedule>) session.get(MICSConstant.MICS_SESSION_DELHUNTINGSCHEDULEREQ);
					logger.debug("huntingscheduleReg: " + huntingscheduleReg);
					List oldScheduleDB = micsCommonSessionHandler.getHuntingscheduleByHuntinggroupKey(huntinggroupkey);
					List oldHuntingSchedules = new ArrayList();
					if (oldScheduleDB != null && oldScheduleDB.size() > 0) {
						for (Iterator iterOldScheduleDB = oldScheduleDB.iterator(); iterOldScheduleDB
								.hasNext();) {
							Huntingschedule currOldHuntingSchedule = (Huntingschedule) iterOldScheduleDB.next();
							List oldScheduleMembers = micsCommonSessionHandler.getMemberByHuntingscheduleKey(currOldHuntingSchedule.getHuntingschedulekey());
							Object[] currOldHuntingScheduleObj = new Object[2];
							currOldHuntingScheduleObj[0] = currOldHuntingSchedule;
							currOldHuntingScheduleObj[1] = oldScheduleMembers;
							oldHuntingSchedules.add(currOldHuntingScheduleObj);
						}
					} else {
						logger.debug("No hunting schedules defines for this huntinggroup " + huntinggroupkey);
					}
					List tempList = new ArrayList<AddHuntingSchedule>();
					
					for (Iterator iterAddUpdate = huntingscheduleReg.iterator(); iterAddUpdate
							.hasNext();) {
						AddHuntingSchedule currAddUpdate = (AddHuntingSchedule) iterAddUpdate.next();
						tempList.add(currAddUpdate);
					}
					if (delHuntingscheduleReq != null) {
						for (Iterator iterDelete = delHuntingscheduleReq.iterator(); iterDelete
								.hasNext();) {
							AddHuntingSchedule currDelete = (AddHuntingSchedule) iterDelete.next();
							tempList.add(currDelete);
						}
					}
					micsCommonSessionHandler.editHuntinggroup(newHuntinggroup,oldHuntingSchedules,tempList);
					addActionMessage("Huntinggroup " + newHuntinggroup.getHuntinggroupname() + " successfully edited");
					Micslogger.log("Edit","Hunting Group",  newHuntinggroup.getHuntinggroupname() + " edited", loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"SUCCESS");
					logger.debug("edit complete");
					result = MICSConstant.MICS_SUCCESS;
				} else {
					addActionError("User not authorized");
					Micslogger.log("Edit","Hunting Group",  "Failed to edit " + this.huntinggroupname , loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"FAILED");
					result = MICSConstant.MICS_ERROR_NOT_AUTH;
				}
			} else {
				addActionError("User not logged in");
				Micslogger.log("Create","Hunting Group",  "Failed to edit " + this.huntinggroupname , "", "NoLog","FAILED");
				result = MICSConstant.MICS_ERROR_NOT_LOGGEDIN;
			}
		} catch (Exception e) {
			logger.error("Failed to edit HuntingGroup",e);
			addActionError("Failed to edit your HuntingGroup request "+ e.getMessage());
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
				if (micsCommonSessionHandler.isMenuAuthorized(huntinggroupView, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
						Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
						if(currCompany == null) throw new Exception ("Currently no company selected! please select company first.");
						this.companykey = currCompany.getCompanykey();
						logger.debug("currCompany: " + currCompany.getCompanyname());
						this.huntingoptionList = micsCommonSessionHandler.getHuntingOptions();
						this.timewindowList = micsCommonSessionHandler.getTimeWindows();
						this.annoList = micsCommonSessionHandler.getCompanyAnno(currCompany.getCompanykey());
						session.put(MICSConstant.MICS_SESSION_ANNOLIST, this.annoList);
						session.put(MICSConstant.MICS_SESSION_TIMEWINDOWLIST, this.timewindowList);
						session.put(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST, this.huntingoptionList);
					} else {
						this.tselAdmin = false;
						this.companykey = loginWebUser.getCompanykey();
						this.annoList = micsCommonSessionHandler.getCompanyAnno(companykey);
						this.huntingoptionList = micsCommonSessionHandler.getHuntingOptions();
						this.timewindowList = micsCommonSessionHandler.getTimeWindows();
						logger.debug("annoList: " + annoList);
						session.put(MICSConstant.MICS_SESSION_ANNOLIST, annoList);
						session.put(MICSConstant.MICS_SESSION_TIMEWINDOWLIST, this.timewindowList);
						session.put(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST, this.huntingoptionList);
					}
					Huntinggroup dbHuntingGroup = micsCommonSessionHandler.getHuntinggroupByKey(huntinggroupkey);
					if (dbHuntingGroup == null) {
						throw new MICSException("Sorry unable to process your request, Huntinggroup with key " + huntinggroupkey + " not found!" );
					}
					if (!dbHuntingGroup.getCompanykey().equals(this.companykey)) {
						throw new MICSException("Sorry you are unauthorized to view this huntinggroup!");
					}
					//Set Hunting Group properties to display
					huntinggroupkey = dbHuntingGroup.getHuntinggroupkey();
					logger.debug("huntinggroupkey: " + huntinggroupkey);
					huntinggroupname = dbHuntingGroup.getHuntinggroupname();
					privatenumber = dbHuntingGroup.getPrivatenumber();
					publicnumber = dbHuntingGroup.getPublicnumber();
					welcomeannoid = dbHuntingGroup.getWelcomeannoid().intValue();
					membernaannoid = dbHuntingGroup.getMembernaannoid().intValue();
					membernrannoid = dbHuntingGroup.getMembernrannoid().intValue();
					huntingcliprefix = dbHuntingGroup.getHuntingcliprefix();
					huntingoption = dbHuntingGroup.getHuntingoption().intValue();
					
					BigDecimal bdReverseCharging = dbHuntingGroup.getReverseCharging();
					if (bdReverseCharging != null) {
						if (bdReverseCharging.intValue() == 1) {
							this.boolReverseCharging = true;
						} else {
							this.boolReverseCharging = false;
						}
					} else {
						this.boolReverseCharging = false;
					}
					List dbHuntingschedules = micsCommonSessionHandler.getHuntingscheduleWithTimebandByHuntinggroupKey(huntinggroupkey);
					if (dbHuntingschedules != null && dbHuntingschedules.size() > 0) {
						huntingscheduleReg = new ArrayList<AddHuntingSchedule>();
						for (Iterator iterHuntingSchedule = dbHuntingschedules.iterator(); iterHuntingSchedule
								.hasNext();) {
							Object[] currScheduleTimeband = (Object[]) iterHuntingSchedule.next();
							Huntingschedule currHS = (Huntingschedule) currScheduleTimeband[0];
							Timeband currTimeband = (Timeband) currScheduleTimeband[1];
							AddHuntingSchedule editHS = new AddHuntingSchedule();
							editHS.setHuntinggroupkey(currHS.getHuntinggroupkey());
							editHS.setHuntingschedulekey(currHS.getHuntingschedulekey());
							editHS.setOrderindex(currHS.getOrderindex().intValue());
							editHS.setTimebandkey(currHS.getTimebandkey());
							editHS.setTimebandname(currTimeband.getId().getTimebandname());
							editHS.setTimebanddesc(currTimeband.getTimebanddescription());
							editHS.setActionType("");
							//Get Member
							List dbHSMember = micsCommonSessionHandler.getHuntingmemberByHuntingscheduleKey(currHS.getHuntingschedulekey());
							if (dbHSMember != null && dbHSMember.size() > 0) {
								memberReq = null;
								memberReq = new ArrayList<AddUserRequest>();
								
								for (Iterator iterHSMember = dbHSMember.iterator(); iterHSMember
										.hasNext();) {
									Object[] currObject = (Object[]) iterHSMember.next();
									logger.debug("currObject: " + currObject);
									String currMicsUserKey = currObject[0].toString();
									String currMicsFirstName = currObject[1].toString();
									String currMicsLastName = currObject[2].toString();
									String currMicsPubNumber = currObject[3].toString();
									String currMicsPrivNumber = currObject[4].toString();
									String currMemberKey = currObject[5].toString();
									BigDecimal currOrderIdx = (BigDecimal)currObject[6];
									AddUserRequest currAddUser = new AddUserRequest();
									currAddUser.setMemberKey(currMemberKey);
									currAddUser.setMicsUserKey(currMicsUserKey);
									currAddUser.setMicsFirstName(currMicsFirstName);
									currAddUser.setMicsLastName(currMicsLastName);
									currAddUser.setMicsPubNumber(currMicsPubNumber);
									currAddUser.setMicsPrivNumber(currMicsPrivNumber);
									currAddUser.setOrderindex(currOrderIdx.intValue());
									currAddUser.setActionType("");
									memberReq.add(currAddUser);
									
								}
							}
							editHS.setMemberReq(memberReq);
							huntingscheduleReg.add(editHS);
							
							
						}
						
					}
					session.put(MICSConstant.MICS_SESSION_HUNTINGSCHEDULEREQ, huntingscheduleReg);
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
			logger.error("Failed to initialize HuntingGroup view",e);
			addActionError("Failed to process your HuntingGroup view request "+ e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		logger.debug("result: " + result);
		return result;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String delete(){
		logger.info("delete()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(huntinggroupDelete, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
						Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
						if(currCompany == null) throw new Exception ("Currently no company selected! please select company first.");
						this.companykey = currCompany.getCompanykey();
						logger.debug("currCompany: " + currCompany.getCompanyname());
						this.huntingoptionList = (List) session.get(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST);
						this.timewindowList = (List) session.get(MICSConstant.MICS_SESSION_TIMEWINDOWLIST);
						this.annoList = (List) session.get(MICSConstant.MICS_SESSION_ANNOLIST);
						
					} else {
						this.tselAdmin = false;
						this.companykey = loginWebUser.getCompanykey();
						this.huntingoptionList = (List) session.get(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST);
						this.timewindowList = (List) session.get(MICSConstant.MICS_SESSION_TIMEWINDOWLIST);
						this.annoList = (List) session.get(MICSConstant.MICS_SESSION_ANNOLIST);
					}
					logger.debug("huntinggroupkey: " + huntinggroupkey);
					Huntinggroup newHuntinggroup = micsCommonSessionHandler.getHuntinggroupByKey(this.huntinggroupkey);
					logger.debug("newHuntinggroup: " + newHuntinggroup);
					List oldScheduleDB = micsCommonSessionHandler.getHuntingscheduleByHuntinggroupKey(newHuntinggroup.getHuntinggroupkey());
					logger.debug("oldScheduleDB: "+ oldScheduleDB);
					List oldHuntingSchedules = new ArrayList();
					if (oldScheduleDB != null && oldScheduleDB.size() > 0) {
						for (Iterator iterOldScheduleDB = oldScheduleDB.iterator(); iterOldScheduleDB
								.hasNext();) {
							Huntingschedule currOldHuntingSchedule = (Huntingschedule) iterOldScheduleDB.next();
							List oldScheduleMembers = micsCommonSessionHandler.getMemberByHuntingscheduleKey(currOldHuntingSchedule.getHuntingschedulekey());
							logger.debug("oldScheduleMembers: "+oldScheduleMembers);
							Object[] currOldHuntingScheduleObj = new Object[2];
							currOldHuntingScheduleObj[0] = currOldHuntingSchedule;
							currOldHuntingScheduleObj[1] = oldScheduleMembers;
							oldHuntingSchedules.add(currOldHuntingScheduleObj);
						}
					} else {
						logger.debug("No hunting schedules defines for this huntinggroup " + huntinggroupkey);
					}
					micsCommonSessionHandler.deleteHuntinggroup(newHuntinggroup,oldHuntingSchedules);
					addActionMessage("Huntinggroup " + newHuntinggroup.getHuntinggroupname() + " successfully deleted");
					Micslogger.log("Delete","Hunting Group",    newHuntinggroup.getHuntinggroupname() + " deleted" , loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"SUCCESS");
					logger.debug("delete complete");
					result = MICSConstant.MICS_SUCCESS;
				} else {
					addActionError("User not authorized");
					Micslogger.log("Delete","Hunting Group",    "Fail to delete "+this.huntinggroupkey , loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"FAILED");
					result = MICSConstant.MICS_ERROR_NOT_AUTH;
				}
			} else {
				addActionError("User not logged in");
				Micslogger.log("Delete","Hunting Group",    "Fail to delete "+this.huntinggroupkey , "", "NoLog","FAILED");
				result = MICSConstant.MICS_ERROR_NOT_LOGGEDIN;
			}
		} catch (Exception e) {
			logger.error("Failed to delete HuntingGroup",e);
			addActionError("Failed to delete your HuntingGroup request "+ e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		logger.debug("result: " + result);
		return result;
	}
	
	public String addTimeband(){
		logger.debug("addTimeband() " + this.timebandkey);
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			huntingscheduleReg = (List)session.get(MICSConstant.MICS_SESSION_HUNTINGSCHEDULEREQ);
			int timebandIdx = 0;
			if (huntingscheduleReg == null) {
				huntingscheduleReg = new ArrayList<AddHuntingSchedule>();
				timebandIdx = 1;
			} else {
				//Check if timeband already used
				logger.debug("Check if timeband already use");
				for (Iterator iterCurrSchedule = huntingscheduleReg.iterator(); iterCurrSchedule
						.hasNext();) {
					AddHuntingSchedule currTempHS = (AddHuntingSchedule) iterCurrSchedule.next();
					if (currTempHS.getTimebandkey().trim().equals(this.timebandkey.trim())) {
						logger.debug("Timeband with key " + this.timebandkey + " already exist");
						throw new MICSException("Timeband " + currTempHS.getTimebandkey() +" - " +currTempHS.getTimebandname() + "already exist" );
					}
					
				}
				logger.debug("Timeband is ok");
				timebandIdx = huntingscheduleReg.size() + 1;
			}
			logger.debug("timebandIdx: "+ timebandIdx);
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
//			if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
			if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
				this.tselAdmin = true;
				Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
				if(currCompany == null) throw new Exception ("Currently no company selected! please select company first.");
				this.companykey = currCompany.getCompanykey();
				logger.debug("currCompany: " + currCompany.getCompanyname());
				logger.debug("companykey: " + companykey);
			} else {
				this.tselAdmin = false;
				this.companykey = loginWebUser.getCompanykey();
			}
			huntingoptionList =(List)session.get(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST);
			if (huntingoptionList == null) {
				logger.debug("huntingoptionList: " + huntingoptionList);
				this.huntingoptionList = micsCommonSessionHandler.getHuntingOptions();
			}
			annoList = (List)session.get(MICSConstant.MICS_SESSION_ANNOLIST);
			if (annoList == null) {
				logger.debug("annoList: " + annoList);
				this.annoList = micsCommonSessionHandler.getCompanyAnno(this.companykey);
			}
			timewindowList=(List) session.get(MICSConstant.MICS_SESSION_TIMEWINDOWLIST);
			if (timewindowList==null) {
				logger.debug("timewindowList: " + timewindowList);
				this.timewindowList = micsCommonSessionHandler.getTimeWindows();
			}
			
			
			logger.debug("Set session");
			session.put(MICSConstant.MICS_SESSION_ANNOLIST, this.annoList);
			session.put(MICSConstant.MICS_SESSION_TIMEWINDOWLIST, this.timewindowList);
			session.put(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST, this.huntingoptionList);
			
			logger.debug("Init huntingschedule request");
			AddHuntingSchedule currAddHuntingScheduleRequest = new AddHuntingSchedule();
			currAddHuntingScheduleRequest.setTimebandkey(this.timebandkey);
			Timeband currTimeband = micsCommonSessionHandler.getTimebandByKey(this.timebandkey);
			currAddHuntingScheduleRequest.setTimebandname(currTimeband.getId().getTimebandname());
			currAddHuntingScheduleRequest.setTimebanddesc(currTimeband.getTimebanddescription());
			if (huntinggroupkey != null && !huntinggroupkey.trim().equals("")) {
				currAddHuntingScheduleRequest.setHuntinggroupkey(huntinggroupkey);
			}
			currAddHuntingScheduleRequest.setHuntinggroupkey(huntinggroupkey);
			currAddHuntingScheduleRequest.setRemove(false);
			currAddHuntingScheduleRequest.setOrderindex(timebandIdx);
			currAddHuntingScheduleRequest.setActionType("ADD");
			huntingscheduleReg.add(currAddHuntingScheduleRequest);
			this.maxTimeband = huntingscheduleReg.size();
			session.put(MICSConstant.MICS_SESSION_HUNTINGSCHEDULEREQ, huntingscheduleReg);
			logger.debug("New Hunting Schedule " + currAddHuntingScheduleRequest.getTimebandname() + " added" );
			result = MICSConstant.MICS_SUCCESS;
			logger.debug("result: " + result);
		} catch (Exception e) {
			logger.error("Failed to add hunting schedule",e);
			addActionError("Failed to add hunting schedule "+ e.getMessage());
			result = MICSConstant.MICS_INPUT;
			logger.debug("result: " + result);
		}
		return result;
	}
	
	public String delTimeband(){
		logger.debug("delTimeband()");
		String result = MICSConstant.MICS_ERROR;
		try {
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
//			if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
			if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
				this.tselAdmin = true;
				Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
				if(currCompany == null) throw new Exception ("Currently no company selected! please select company first.");
				this.companykey = currCompany.getCompanykey();
			} else {
				this.tselAdmin = false;
				this.companykey = loginWebUser.getCompanykey();
			}
			huntingoptionList =(List)session.get(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST);
			annoList = (List)session.get(MICSConstant.MICS_SESSION_ANNOLIST);
			timewindowList=(List) session.get(MICSConstant.MICS_SESSION_TIMEWINDOWLIST);
			huntingscheduleReg = (List)session.get(MICSConstant.MICS_SESSION_HUNTINGSCHEDULEREQ);
			List tempHuntingScheduleReq = new ArrayList<AddHuntingSchedule>();
			logger.debug("huntingscheduleReg: " + huntingscheduleReg);
			for (Iterator iterHuntingSchedReq = huntingscheduleReg.iterator();iterHuntingSchedReq.hasNext();) {
				AddHuntingSchedule currHuntingScheduleReq = (AddHuntingSchedule) iterHuntingSchedReq.next();
					logger.debug("Timeband " + currHuntingScheduleReq.getTimebandkey() + " - " + currHuntingScheduleReq.getTimebandname()+ " remove " + currHuntingScheduleReq.isRemove());
					
					if (currHuntingScheduleReq.isRemove()) {
						logger.debug("Timeband removed");
						//Remove Number don't add to the list
					} else {
						logger.debug("Timeband added");
						tempHuntingScheduleReq.add(currHuntingScheduleReq);
					}
			}
			//ReIndex
			int intReindex = 1;
			huntingscheduleReg = null;
			huntingscheduleReg = new ArrayList<AddHuntingSchedule>();
			for (Iterator iterNewHS = tempHuntingScheduleReq.iterator(); iterNewHS
					.hasNext();) {
				AddHuntingSchedule currNewHS = (AddHuntingSchedule) iterNewHS.next();
				currNewHS.setOrderindex(intReindex);
				huntingscheduleReg.add(currNewHS);
				intReindex++;
			}
			session.put(MICSConstant.MICS_SESSION_HUNTINGSCHEDULEREQ, huntingscheduleReg);
			result = MICSConstant.MICS_SUCCESS;
		} catch (Exception e) {
			logger.error("Failed to del hunting schedule",e);
			addActionError("Failed to del hunting schedule");
			result = MICSConstant.MICS_ERROR;
		}
		return result;
	}
	
	public String editDelTimeband(){
		logger.debug("editDelTimeband()");
		String result = MICSConstant.MICS_ERROR;
		try {
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
//			if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
			if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
				this.tselAdmin = true;
				Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
				if(currCompany == null) throw new Exception ("Currently no company selected! please select company first.");
				this.companykey = currCompany.getCompanykey();
			} else {
				this.tselAdmin = false;
				this.companykey = loginWebUser.getCompanykey();
			}
			huntingoptionList =(List)session.get(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST);
			annoList = (List)session.get(MICSConstant.MICS_SESSION_ANNOLIST);
			timewindowList=(List) session.get(MICSConstant.MICS_SESSION_TIMEWINDOWLIST);
			huntingscheduleReg = (List)session.get(MICSConstant.MICS_SESSION_HUNTINGSCHEDULEREQ);
			logger.debug("huntingscheduleReg: " + huntingscheduleReg);
			List tempHuntingScheduleReq = new ArrayList<AddHuntingSchedule>();
			delHuntingscheduleReq = (List) session.get(MICSConstant.MICS_SESSION_DELHUNTINGSCHEDULEREQ);
			logger.debug("delHuntingscheduleReq: " + delHuntingscheduleReq);
			if (delHuntingscheduleReq== null) {
				delHuntingscheduleReq = new ArrayList<AddHuntingSchedule>();
			}
			for (Iterator iterHuntingSchedReq = huntingscheduleReg.iterator();iterHuntingSchedReq.hasNext();) {
				AddHuntingSchedule currHuntingScheduleReq = (AddHuntingSchedule) iterHuntingSchedReq.next();
					logger.debug("Timeband " + currHuntingScheduleReq.getTimebandkey() + " - " + currHuntingScheduleReq.getTimebandname()+ " remove " + currHuntingScheduleReq.isRemove());
					
					if (currHuntingScheduleReq.isRemove()) {
						logger.debug("Timeband removed");
						currHuntingScheduleReq.setActionType("DELETE");
						delHuntingscheduleReq.add(currHuntingScheduleReq);
						//Remove Number don't add to the list
					} else {
						logger.debug("Timeband added");
						tempHuntingScheduleReq.add(currHuntingScheduleReq);
					}
			}
			//ReIndex
			int intReindex = 1;
			huntingscheduleReg = null;
			huntingscheduleReg = new ArrayList<AddHuntingSchedule>();
			for (Iterator iterNewHS = tempHuntingScheduleReq.iterator(); iterNewHS
					.hasNext();) {
				AddHuntingSchedule currNewHS = (AddHuntingSchedule) iterNewHS.next();
				if (currNewHS.getOrderindex() != intReindex) {
					currNewHS.setOrderindex(intReindex);
					if (currNewHS.getActionType()==null || currNewHS.getActionType().trim().equals("")) {
						currNewHS.setActionType("UPDATE");
					}
				}
				huntingscheduleReg.add(currNewHS);
				intReindex++;
			}
			session.put(MICSConstant.MICS_SESSION_HUNTINGSCHEDULEREQ, huntingscheduleReg);
			session.put(MICSConstant.MICS_SESSION_DELHUNTINGSCHEDULEREQ, delHuntingscheduleReq);
			result = MICSConstant.MICS_SUCCESS;
		} catch (Exception e) {
			logger.error("Failed to del hunting schedule",e);
			addActionError("Failed to del hunting schedule");
			result = MICSConstant.MICS_ERROR;
		}
		return result;
	}
	
	public String viewHuntingSchedule(){
		logger.debug("viewHuntingSchedule()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			huntingscheduleReg = (List<AddHuntingSchedule>) session.get(MICSConstant.MICS_SESSION_HUNTINGSCHEDULEREQ);
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
//			if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
			if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
				this.tselAdmin = true;
				Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
				if(currCompany == null) throw new Exception ("Currently no company selected! please select company first.");
				this.companykey = currCompany.getCompanykey();
			} else {
				this.tselAdmin = false;
				this.companykey = loginWebUser.getCompanykey();
			}
			huntingoptionList =(List)session.get(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST);
			annoList = (List)session.get(MICSConstant.MICS_SESSION_ANNOLIST);
			timewindowList=(List) session.get(MICSConstant.MICS_SESSION_TIMEWINDOWLIST);
			logger.debug("targetTimebandIdx: " + targetTimebandIdx);
			logger.debug("huntingScheduleIdx: " + huntingScheduleIdx);
			logger.debug("companykey: " + companykey);
			logger.debug("huntinggroupkey: " + huntinggroupkey);
			logger.debug("huntinggroupname: " + huntinggroupname);
			AddHuntingSchedule currAddHuntingScheduleRequest = (AddHuntingSchedule) huntingscheduleReg.get(targetTimebandIdx);
			logger.debug("New Hunting Schedule " + currAddHuntingScheduleRequest.getTimebandname() + " viewed" );
			this.huntingScheduleName = currAddHuntingScheduleRequest.getTimebandname();
			memberReq = currAddHuntingScheduleRequest.getMemberReq();
			if (memberReq == null) {
				logger.debug("memberReq 1: " + memberReq);
			} else {
				logger.debug("memberReq 1: " + memberReq.size());
			}
			session.put(MICSConstant.MICS_SESSION_MEMBERREQ, memberReq);
			result = MICSConstant.MICS_SUCCESS;
		} catch (Exception e) {
			logger.error("Failed to view hunting schedule",e);
			addActionError("Failed to view hunting schedule " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		}
		return result;
	}
	
	public String moveTimebandUp(){
		logger.debug("moveTimebandUp: " + this.targetTimebandIdx);
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler= new MICSCommonSessionHandler();
			huntingscheduleReg = (List<AddHuntingSchedule>) session.get(MICSConstant.MICS_SESSION_HUNTINGSCHEDULEREQ);
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
//			if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
			if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
				this.tselAdmin = true;
				Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
				if(currCompany == null) throw new Exception ("Currently no company selected! please select company first.");
				this.companykey = currCompany.getCompanykey();
			} else {
				this.tselAdmin = false;
				this.companykey = loginWebUser.getCompanykey();
			}
			this.maxTimeband = maxTimeband;
			this.huntingoptionList = micsCommonSessionHandler.getHuntingOptions();
			this.timewindowList = micsCommonSessionHandler.getTimeWindows();
			this.annoList = micsCommonSessionHandler.getCompanyAnno(this.companykey);
			session.put(MICSConstant.MICS_SESSION_ANNOLIST, this.annoList);
			session.put(MICSConstant.MICS_SESSION_TIMEWINDOWLIST, this.timewindowList);
			session.put(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST, this.huntingoptionList);
			if (huntingscheduleReg != null) {
				AddHuntingSchedule destHS = (AddHuntingSchedule)huntingscheduleReg.get(targetTimebandIdx-1);
				int intDestOrderIdx = destHS.getOrderindex();
				AddHuntingSchedule targetHS = (AddHuntingSchedule)huntingscheduleReg.get(targetTimebandIdx);
				int intTargetOrderIdx = targetHS.getOrderindex();
				targetHS.setOrderindex(intDestOrderIdx);
				if (targetHS.getActionType() == null|| targetHS.getActionType().trim().equals("")) {
					targetHS.setActionType("UPDATE");
				}
				destHS.setOrderindex(intTargetOrderIdx);
				if (destHS.getActionType() == null|| destHS.getActionType().trim().equals("")) {
					destHS.setActionType("UPDATE");
				}
				huntingscheduleReg.set(targetTimebandIdx-1,targetHS);
				huntingscheduleReg.set(targetTimebandIdx, destHS);
				session.put(MICSConstant.MICS_SESSION_HUNTINGSCHEDULEREQ, huntingscheduleReg);
				result = MICSConstant.MICS_SUCCESS;
			} else {
				throw new MICSException("No Timeband available for edit");
			}
		} catch (Exception e) {
			logger.error("Failed to move up Timeband",e);
			addActionError("Failed to move up Timeband " + e.getMessage());
			result = MICSConstant.MICS_INPUT;
		}
		return result;
	}
	
	public String moveTimebandDown(){
		logger.debug("moveTimebandDown: " + this.targetTimebandIdx);
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler= new MICSCommonSessionHandler();
			huntingscheduleReg = (List<AddHuntingSchedule>) session.get(MICSConstant.MICS_SESSION_HUNTINGSCHEDULEREQ);
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
//			if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
			if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
				this.tselAdmin = true;
				Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
				if(currCompany == null) throw new Exception ("Currently no company selected! please select company first.");
				this.companykey = currCompany.getCompanykey();
			} else {
				this.tselAdmin = false;
				this.companykey = loginWebUser.getCompanykey();
			}
			this.maxTimeband = maxTimeband;
			this.huntingoptionList = micsCommonSessionHandler.getHuntingOptions();
			this.timewindowList = micsCommonSessionHandler.getTimeWindows();
			this.annoList = micsCommonSessionHandler.getCompanyAnno(this.companykey);
			session.put(MICSConstant.MICS_SESSION_ANNOLIST, this.annoList);
			session.put(MICSConstant.MICS_SESSION_TIMEWINDOWLIST, this.timewindowList);
			session.put(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST, this.huntingoptionList);
			if (huntingscheduleReg != null) {
				AddHuntingSchedule destHS = (AddHuntingSchedule)huntingscheduleReg.get(targetTimebandIdx+1);
				int intDestOrderIdx = destHS.getOrderindex();
				AddHuntingSchedule targetHS = (AddHuntingSchedule)huntingscheduleReg.get(targetTimebandIdx);
				int intTargetOrderIdx = targetHS.getOrderindex();
				targetHS.setOrderindex(intDestOrderIdx);
				if (targetHS.getActionType() == null|| targetHS.getActionType().trim().equals("")) {
					targetHS.setActionType("UPDATE");
				}
				destHS.setOrderindex(intTargetOrderIdx);
				if (destHS.getActionType() == null|| destHS.getActionType().trim().equals("")) {
					destHS.setActionType("UPDATE");
				}
				huntingscheduleReg.set(targetTimebandIdx+1,targetHS);
				huntingscheduleReg.set(targetTimebandIdx, destHS);
				session.put(MICSConstant.MICS_SESSION_HUNTINGSCHEDULEREQ, huntingscheduleReg);
				result = MICSConstant.MICS_SUCCESS;
			} else {
				throw new MICSException("No Timeband available for edit");
			}
		} catch (Exception e) {
			logger.error("Failed to move down Timeband",e);
			addActionError("Failed to move down Timeband " + e.getMessage());
			result = MICSConstant.MICS_INPUT;
		}
		return result;
	}
	
	public String moveMemberUp(){
		logger.debug("moveMemberUp: " + this.targetMemberIdx);
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler= new MICSCommonSessionHandler();
			memberReq = (List<AddUserRequest>) session.get(MICSConstant.MICS_SESSION_MEMBERREQ);
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
//			if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
			if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
				this.tselAdmin = true;
				Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
				if(currCompany == null) throw new Exception ("Currently no company selected! please select company first.");
				this.companykey = currCompany.getCompanykey();
			} else {
				this.tselAdmin = false;
				this.companykey = loginWebUser.getCompanykey();
			}
			this.maxTimeband = maxTimeband;
			this.huntingoptionList = micsCommonSessionHandler.getHuntingOptions();
			this.timewindowList = micsCommonSessionHandler.getTimeWindows();
			this.annoList = micsCommonSessionHandler.getCompanyAnno(this.companykey);
			session.put(MICSConstant.MICS_SESSION_ANNOLIST, this.annoList);
			session.put(MICSConstant.MICS_SESSION_TIMEWINDOWLIST, this.timewindowList);
			session.put(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST, this.huntingoptionList);
			if (memberReq != null) {
				AddUserRequest destMember = (AddUserRequest) memberReq.get(targetMemberIdx-1);
				int intDestOrderIdx = destMember.getOrderindex();
				AddUserRequest targetMember = (AddUserRequest)memberReq.get(targetMemberIdx);
				int intTargetOrderIdx = targetMember.getOrderindex();
				targetMember.setOrderindex(intDestOrderIdx);
				if (targetMember.getActionType() == null|| targetMember.getActionType().trim().equals("")) {
					targetMember.setActionType("UPDATE");
				}
				destMember.setOrderindex(intTargetOrderIdx);
				if (destMember.getActionType() == null|| destMember.getActionType().trim().equals("")) {
					destMember.setActionType("UPDATE");
				}
				memberReq.set(targetMemberIdx-1,targetMember);
				memberReq.set(targetMemberIdx, destMember);
				session.put(MICSConstant.MICS_SESSION_MEMBERREQ, memberReq);
				result = MICSConstant.MICS_SUCCESS;
			} else {
				throw new MICSException("No Member available for edit");
			}
		} catch (Exception e) {
			logger.error("Failed to move up Member",e);
			addActionError("Failed to move up Member " + e.getMessage());
			result = MICSConstant.MICS_INPUT;
		}
		return result;
	}
	
	public String moveMemberDown(){
		logger.debug("moveMemberDown: " + this.targetMemberIdx);
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler= new MICSCommonSessionHandler();
			memberReq = (List<AddUserRequest>) session.get(MICSConstant.MICS_SESSION_MEMBERREQ);
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
//			if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
			if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
				this.tselAdmin = true;
				Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
				if(currCompany == null) throw new Exception ("Currently no company selected! please select company first.");
				this.companykey = currCompany.getCompanykey();
			} else {
				this.tselAdmin = false;
				this.companykey = loginWebUser.getCompanykey();
			}
			this.maxTimeband = maxTimeband;
			this.huntingoptionList = micsCommonSessionHandler.getHuntingOptions();
			this.timewindowList = micsCommonSessionHandler.getTimeWindows();
			this.annoList = micsCommonSessionHandler.getCompanyAnno(this.companykey);
			session.put(MICSConstant.MICS_SESSION_ANNOLIST, this.annoList);
			session.put(MICSConstant.MICS_SESSION_TIMEWINDOWLIST, this.timewindowList);
			session.put(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST, this.huntingoptionList);
			if (memberReq != null) {
				AddUserRequest destMember = (AddUserRequest) memberReq.get(targetMemberIdx+1);
				int intDestOrderIdx = destMember.getOrderindex();
				AddUserRequest targetMember = (AddUserRequest)memberReq.get(targetMemberIdx);
				int intTargetOrderIdx = targetMember.getOrderindex();
				targetMember.setOrderindex(intDestOrderIdx);
				if (targetMember.getActionType() == null|| targetMember.getActionType().trim().equals("")) {
					targetMember.setActionType("UPDATE");
				}
				destMember.setOrderindex(intTargetOrderIdx);
				if (destMember.getActionType() == null|| destMember.getActionType().trim().equals("")) {
					destMember.setActionType("UPDATE");
				}
				memberReq.set(targetMemberIdx+1,targetMember);
				memberReq.set(targetMemberIdx, destMember);
				session.put(MICSConstant.MICS_SESSION_MEMBERREQ, memberReq);
				result = MICSConstant.MICS_SUCCESS;
			} else {
				throw new MICSException("No Member available for edit");
			}
		} catch (Exception e) {
			logger.error("Failed to move down Member",e);
			addActionError("Failed to move down Member " + e.getMessage());
			result = MICSConstant.MICS_INPUT;
		}
		return result;
	}
	
	public String createHuntingSchedule(){
		logger.debug("createHuntingSchedule()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			huntingscheduleReg = (List<AddHuntingSchedule>) session.get(MICSConstant.MICS_SESSION_HUNTINGSCHEDULEREQ);
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
//			if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
			if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
				this.tselAdmin = true;
				Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
				if(currCompany == null) throw new Exception ("Currently no company selected! please select company first.");
				this.companykey = currCompany.getCompanykey();
			} else {
				this.tselAdmin = false;
				this.companykey = loginWebUser.getCompanykey();
			}
			huntingoptionList =(List)session.get(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST);
			annoList = (List)session.get(MICSConstant.MICS_SESSION_ANNOLIST);
			timewindowList=(List) session.get(MICSConstant.MICS_SESSION_TIMEWINDOWLIST);
			logger.debug("targetTimebandIdx: " + targetTimebandIdx);
			logger.debug("companykey: " + companykey);
			logger.debug("huntinggroupname: " + huntinggroupname);
			AddHuntingSchedule currAddHuntingScheduleRequest = (AddHuntingSchedule) huntingscheduleReg.get(targetTimebandIdx);
			logger.debug("memberReq: "+memberReq);
			List<AddUserRequest> tempList = new ArrayList<AddUserRequest>();
			if (memberReq != null && memberReq.size() > 0) {
				logger.debug("member not null, put on another list");
				for (Iterator iterMember = memberReq.iterator(); iterMember.hasNext();) {
					AddUserRequest currAddUserRequest = (AddUserRequest) iterMember.next();
					tempList.add(currAddUserRequest);
				}
			} 
			currAddHuntingScheduleRequest.setMemberReq(tempList);
			if (currAddHuntingScheduleRequest.getActionType()==null || currAddHuntingScheduleRequest.getActionType().trim().equals("")) {
				currAddHuntingScheduleRequest.setActionType("UPDATE");
			}
			huntingscheduleReg.set(targetTimebandIdx, currAddHuntingScheduleRequest);
			session.put(MICSConstant.MICS_SESSION_HUNTINGSCHEDULEREQ,huntingscheduleReg);
			//Clean session for memberreq;
			session.remove(MICSConstant.MICS_SESSION_MEMBERREQ);
			logger.debug("New Hunting Schedule " + currAddHuntingScheduleRequest.getTimebandname() + " create" );
			result = MICSConstant.MICS_SUCCESS;
		} catch (Exception e) {
			logger.error("Failed to create hunting schedule",e);
			addActionError("Failed to create hunting schedule " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		}
		return result;
	}
	
	public String addTimebandMember(){
		logger.debug("addTimebandMember()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			if (huntingscheduleReg == null) {
				huntingscheduleReg = new ArrayList<AddHuntingSchedule>();
			}
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
//			if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
			if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
				this.tselAdmin = true;
				Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
				if(currCompany == null) throw new Exception ("Currently no company selected! please select company first.");
				this.companykey = currCompany.getCompanykey();
			} else {
				this.tselAdmin = false;
				this.companykey = loginWebUser.getCompanykey();
			}
			huntingoptionList =(List)session.get(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST);
			annoList = (List)session.get(MICSConstant.MICS_SESSION_ANNOLIST);
			timewindowList=(List) session.get(MICSConstant.MICS_SESSION_TIMEWINDOWLIST);
			
			AddHuntingSchedule currAddHuntingScheduleRequest = new AddHuntingSchedule();
			currAddHuntingScheduleRequest.setTimebandkey(this.timebandkey);
			Timeband currTimeband = micsCommonSessionHandler.getTimebandByKey(this.timebandkey);
			currAddHuntingScheduleRequest.setTimebandname(currTimeband.getId().getTimebandname());
			currAddHuntingScheduleRequest.setTimebanddesc(currTimeband.getTimebanddescription());
			currAddHuntingScheduleRequest.setRemove(false);
			currAddHuntingScheduleRequest.setActionType("ADD");
			huntingscheduleReg.add(currAddHuntingScheduleRequest);
			logger.debug("New Hunting Schedule " + currAddHuntingScheduleRequest.getTimebandname() + " added" );
			result = MICSConstant.MICS_SUCCESS;
		} catch (Exception e) {
			logger.error("Failed to add hunting schedule",e);
			addActionError("Failed to add hunting schedule");
			result = MICSConstant.MICS_ERROR;
		}
		return result;
	}
	
	public String initSearchMember(){
		logger.info("initSearchMember()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(huntinggroupCreate, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
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
			logger.error("Failed to initialize add search member HuntingGroupSchedule admin",e);
			addActionError("Failed to process add search member HuntingGroupSchedule administration request");
			result = MICSConstant.MICS_ERROR;
		} 
		logger.debug("result: " + result);
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
				if (micsCommonSessionHandler.isMenuAuthorized(huntinggroupCreate, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
						employeeList = micsCommonSessionHandler.getEmployeesByCriteriaCompany(this.searchFirstName, this.searchLastName, this.searchPrivNumber, this.searchPubNumber,this.companykey);
						
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
									if (currSelectedMember.getMicsUserKey().equals(currMicsUserKey)&& currSelectedMember.getMicsPubNumber().equals(currMicsPubNumber)) {
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
			logger.error("Failed to search add member HuntingGroupSchedule admin",e);
			addActionError("Failed to process your search add member HuntingGroupSchedule administration request");
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
//			if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
			if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
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

			for (Iterator iterMember = searchMemberRes.iterator(); iterMember
					.hasNext();) {
				AddUserRequest currAddMember = (AddUserRequest) iterMember.next();
				logger.debug("Member " + currAddMember.getMicsUserKey() + " - " + currAddMember.getMicsFirstName() +","+ currAddMember.getMicsLastName()+" add " + currAddMember.isSelected());
				if (currAddMember.isSelected()) {
					//Check if Member already in the list
					logger.debug("Check if " + currAddMember.getMicsFirstName() + " already in the list");
					if (memberReq.contains(currAddMember)) {
						logger.debug("Member "+ currAddMember.getMicsFirstName() + ","+ currAddMember.getMicsLastName()+"already in the selected list, skipped");

					} else {
						logger.debug("New Member, Member added");
						currAddMember.setActionType("ADD");
						currAddMember.setOrderindex(memberReq.size() + 1);
						memberReq.add(currAddMember);
					}
				}else {
					logger.debug("Member not selected, skipped");
				}
				
			}
			
			session.put(MICSConstant.MICS_SESSION_MEMBERREQ, memberReq);
			result = MICSConstant.MICS_SUCCESS;
		} catch (Exception e) {
			logger.error("Failed to add HuntingGroupSchedule member",e);
			addActionError("Failed to add HuntingGroupSchedule member");
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
	}
	
	public String delMember(){
		logger.debug("delMember");
		String result = MICSConstant.MICS_ERROR;
		try {
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
//			if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
			if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
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
			//ReIndex Member
			int intReindex = 1;
			memberReq = null;
			memberReq = new ArrayList<AddUserRequest>();
			for (Iterator iterNewMemberReq = tempMemberReq.iterator(); iterNewMemberReq
					.hasNext();) {
				AddUserRequest currAddNewMember = (AddUserRequest) iterNewMemberReq.next();
				currAddNewMember.setOrderindex(intReindex);
				memberReq.add(currAddNewMember);	
				intReindex++;
			}
			session.put(MICSConstant.MICS_SESSION_MEMBERREQ, memberReq);
			result = MICSConstant.MICS_SUCCESS;
		} catch (Exception e) {
			logger.error("Failed to del HuntingGroupSchedule member",e);
			addActionError("Failed to del HuntingGroupSchedule member");
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
	}
	
	public String editDelMember(){
		logger.debug("editDelMember");
		String result = MICSConstant.MICS_ERROR;
		try {
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
//			if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
			if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
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
			//ReIndex Member
			int intReindex = 1;
			memberReq = null;
			memberReq = new ArrayList<AddUserRequest>();
			for (Iterator iterNewMemberReq = tempMemberReq.iterator(); iterNewMemberReq
					.hasNext();) {
				AddUserRequest currAddNewMember = (AddUserRequest) iterNewMemberReq.next();
				currAddNewMember.setOrderindex(intReindex);
				memberReq.add(currAddNewMember);	
				intReindex++;
			}
			session.put(MICSConstant.MICS_SESSION_MEMBERREQ, memberReq);
			result = MICSConstant.MICS_SUCCESS;
		} catch (Exception e) {
			logger.error("Failed to del HuntingGroupSchedule member",e);
			addActionError("Failed to del HuntingGroupSchedule member");
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;

	}
	
	
	/**
	 * @return the huntinggroupkey
	 */
	public String getHuntinggroupkey() {
		return huntinggroupkey;
	}

	/**
	 * @param huntinggroupkey the huntinggroupkey to set
	 */
	public void setHuntinggroupkey(String huntinggroupkey) {
		this.huntinggroupkey = huntinggroupkey;
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
	 * @return the huntinggroupname
	 */
	public String getHuntinggroupname() {
		return huntinggroupname;
	}

	/**
	 * @param huntinggroupname the huntinggroupname to set
	 */
	public void setHuntinggroupname(String huntinggroupname) {
		this.huntinggroupname = huntinggroupname;
	}

	/**
	 * @return the publicnumber
	 */
	public String getPublicnumber() {
		return publicnumber;
	}

	/**
	 * @param publicnumber the publicnumber to set
	 */
	public void setPublicnumber(String publicnumber) {
		this.publicnumber = publicnumber;
	}

	/**
	 * @return the privatenumber
	 */
	public String getPrivatenumber() {
		return privatenumber;
	}

	/**
	 * @param privatenumber the privatenumber to set
	 */
	public void setPrivatenumber(String privatenumber) {
		this.privatenumber = privatenumber;
	}

	/**
	 * @return the welcomeannoid
	 */
	public int getWelcomeannoid() {
		return welcomeannoid;
	}

	/**
	 * @param welcomeannoid the welcomeannoid to set
	 */
	public void setWelcomeannoid(int welcomeannoid) {
		this.welcomeannoid = welcomeannoid;
	}

	/**
	 * @return the membernaannoid
	 */
	public int getMembernaannoid() {
		return membernaannoid;
	}

	/**
	 * @param membernaannoid the membernaannoid to set
	 */
	public void setMembernaannoid(int membernaannoid) {
		this.membernaannoid = membernaannoid;
	}

	/**
	 * @return the membernrannoid
	 */
	public int getMembernrannoid() {
		return membernrannoid;
	}

	/**
	 * @param membernrannoid the membernrannoid to set
	 */
	public void setMembernrannoid(int membernrannoid) {
		this.membernrannoid = membernrannoid;
	}

	/**
	 * @return the huntingcliprefix
	 */
	public String getHuntingcliprefix() {
		return huntingcliprefix;
	}

	/**
	 * @param huntingcliprefix the huntingcliprefix to set
	 */
	public void setHuntingcliprefix(String huntingcliprefix) {
		this.huntingcliprefix = huntingcliprefix;
	}

	/**
	 * @return the huntingoption
	 */
	public int getHuntingoption() {
		return huntingoption;
	}

	/**
	 * @param huntingoption the huntingoption to set
	 */
	public void setHuntingoption(int huntingoption) {
		this.huntingoption = huntingoption;
	}

	/**
	 * @return the timetype
	 */
	public int getTimetype() {
		return timetype;
	}

	/**
	 * @param timetype the timetype to set
	 */
	public void setTimetype(int timetype) {
		this.timetype = timetype;
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
	 * @return the lastmemberorderindex
	 */
	public int getLastmemberorderindex() {
		return lastmemberorderindex;
	}

	/**
	 * @param lastmemberorderindex the lastmemberorderindex to set
	 */
	public void setLastmemberorderindex(int lastmemberorderindex) {
		this.lastmemberorderindex = lastmemberorderindex;
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
	 * @return the annoList
	 */
	public List getAnnoList() {
		return annoList;
	}

	/**
	 * @param annoList the annoList to set
	 */
	public void setAnnoList(List annoList) {
		this.annoList = annoList;
	}

	/**
	 * @return the huntingoptionList
	 */
	public List getHuntingoptionList() {
		return huntingoptionList;
	}

	/**
	 * @param huntingoptionList the huntingoptionList to set
	 */
	public void setHuntingoptionList(List huntingoptionList) {
		this.huntingoptionList = huntingoptionList;
	}

	/**
	 * @return the timewindowList
	 */
	public List getTimewindowList() {
		return timewindowList;
	}

	/**
	 * @param timewindowList the timewindowList to set
	 */
	public void setTimewindowList(List timewindowList) {
		this.timewindowList = timewindowList;
	}

	/**
	 * @return the huntinggroupCreate
	 */
	public String getHuntinggroupCreate() {
		return huntinggroupCreate;
	}

	/**
	 * @param huntinggroupCreate the huntinggroupCreate to set
	 */
	public void setHuntinggroupCreate(String huntinggroupCreate) {
		this.huntinggroupCreate = huntinggroupCreate;
	}

	/**
	 * @return the huntinggroupSearch
	 */
	public String getHuntinggroupSearch() {
		return huntinggroupSearch;
	}

	/**
	 * @param huntinggroupSearch the huntinggroupSearch to set
	 */
	public void setHuntinggroupSearch(String huntinggroupSearch) {
		this.huntinggroupSearch = huntinggroupSearch;
	}

	/**
	 * @return the huntinggroupEdit
	 */
	public String getHuntinggroupEdit() {
		return huntinggroupEdit;
	}

	/**
	 * @param huntinggroupEdit the huntinggroupEdit to set
	 */
	public void setHuntinggroupEdit(String huntinggroupEdit) {
		this.huntinggroupEdit = huntinggroupEdit;
	}

	/**
	 * @return the huntinggroupDelete
	 */
	public String getHuntinggroupDelete() {
		return huntinggroupDelete;
	}
	/**
	 * @param huntinggroupDelete the huntinggroupDelete to set
	 */
	public void setHuntinggroupDelete(String huntinggroupDelete) {
		this.huntinggroupDelete = huntinggroupDelete;
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
	 * @return the huntingscheduleReg
	 */
	public List<AddHuntingSchedule> getHuntingscheduleReg() {
		return huntingscheduleReg;
	}

	/**
	 * @param huntingscheduleReg the huntingscheduleReg to set
	 */
	public void setHuntingscheduleReg(List<AddHuntingSchedule> huntingscheduleReg) {
		this.huntingscheduleReg = huntingscheduleReg;
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
	 * @return the huntingScheduleIdx
	 */
	public int getHuntingScheduleIdx() {
		return huntingScheduleIdx;
	}

	/**
	 * @param huntingScheduleIdx the huntingScheduleIdx to set
	 */
	public void setHuntingScheduleIdx(int huntingScheduleIdx) {
		this.huntingScheduleIdx = huntingScheduleIdx;
	}
	
	

	/**
	 * @return the huntingScheduleName
	 */
	public String getHuntingScheduleName() {
		return huntingScheduleName;
	}

	/**
	 * @param huntingScheduleName the huntingScheduleName to set
	 */
	public void setHuntingScheduleName(String huntingScheduleName) {
		this.huntingScheduleName = huntingScheduleName;
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
	 * @return the huntinggroups
	 */
	public List getHuntinggroups() {
		return huntinggroups;
	}

	/**
	 * @param huntinggroups the huntinggroups to set
	 */
	public void setHuntinggroups(List huntinggroups) {
		this.huntinggroups = huntinggroups;
	}

	
	/**
	 * @return the targetTimebandIdx
	 */
	public int getTargetTimebandIdx() {
		return targetTimebandIdx;
	}

	/**
	 * @param targetTimebandIdx the targetTimebandIdx to set
	 */
	public void setTargetTimebandIdx(int targetTimebandIdx) {
		this.targetTimebandIdx = targetTimebandIdx;
	}

	/**
	 * @return the targetMemberIdx
	 */
	public int getTargetMemberIdx() {
		return targetMemberIdx;
	}

	/**
	 * @param targetMemberIdx the targetMemberIdx to set
	 */
	public void setTargetMemberIdx(int targetMemberIdx) {
		this.targetMemberIdx = targetMemberIdx;
	}

	/**
	 * @return the maxTimeband
	 */
	public int getMaxTimeband() {
		return maxTimeband;
	}

	/**
	 * @param maxTimeband the maxTimeband to set
	 */
	public void setMaxTimeband(int maxTimeband) {
		this.maxTimeband = maxTimeband;
	}

	/**
	 * @return the maxMember
	 */
	public int getMaxMember() {
		return maxMember;
	}

	/**
	 * @param maxMember the maxMember to set
	 */
	public void setMaxMember(int maxMember) {
		this.maxMember = maxMember;
	}

	/**
	 * @return the session
	 */
	public Map<String, Object> getSession() {
		return session;
	}
	
	

	/**
	 * @return the delHuntingscheduleReq
	 */
	public List<AddHuntingSchedule> getDelHuntingscheduleReq() {
		return delHuntingscheduleReq;
	}

	/**
	 * @param delHuntingscheduleReq the delHuntingscheduleReq to set
	 */
	public void setDelHuntingscheduleReq(
			List<AddHuntingSchedule> delHuntingscheduleReq) {
		this.delHuntingscheduleReq = delHuntingscheduleReq;
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
	 * @return the huntinggroupView
	 */
	public String getHuntinggroupView() {
		return huntinggroupView;
	}

	/**
	 * @param huntinggroupView the huntinggroupView to set
	 */
	public void setHuntinggroupView(String huntinggroupView) {
		this.huntinggroupView = huntinggroupView;
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




	private String huntinggroupkey;
	private String companykey;
	private String huntinggroupname;
	private String publicnumber;
	private String privatenumber;
	private int welcomeannoid;
	private int membernaannoid;
	private int membernrannoid;
	private String huntingcliprefix;
	private int huntingoption;
	private BigDecimal reverseCharging;
	private boolean boolReverseCharging;
	private int timetype;
	private String timebandkey;
	private int lastmemberorderindex;
	private String updateuser;
	private Timestamp updatetimestamp;
	private List companyList;
	private List annoList;
	private List huntingoptionList;
	private List timewindowList;
	private List employeeList;
	private List<AddHuntingSchedule> huntingscheduleReg;
	private List<AddHuntingSchedule> delHuntingscheduleReq;
	private List<AddUserRequest> memberReq;
	private String searchFirstName;
	private String searchLastName;
	private String searchPubNumber;
	private String searchPrivNumber;
	private List<AddUserRequest> searchMemberRes;
	private int huntingScheduleIdx;
	private String huntingScheduleName;
	private List huntinggroups;
	private int targetTimebandIdx;
	private int targetMemberIdx;
	private int maxTimeband;
	private int maxMember;
	private boolean deleteAllowed;
	private boolean editAllowed;
	private int searchCnt;
	
	
	private String huntinggroupCreate = "2017050601";
	private String huntinggroupSearch = "2017050602";
	private String huntinggroupEdit = "2017050603";
	private String huntinggroupDelete = "2017050604";
	private String huntinggroupView = "2017050605";
	private boolean tselAdmin; 
	private Map<String, Object> session;
	private static final Logger logger = Logger.getLogger(HuntingGroupAction.class);

	@Override
	public void prepare() throws Exception {
		MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
		Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
		if (loginWebUser != null) {
//			if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
			if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
					loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
					this.tselAdmin = true;
					Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
					if (currCompany != null) {
						this.companykey = currCompany.getCompanykey();
						this.annoList = micsCommonSessionHandler.getCompanyAnno(currCompany.getCompanykey());
					} else {
						this.companykey = null;
						this.annoList = new ArrayList();
					}
					
					this.huntingoptionList = micsCommonSessionHandler.getHuntingOptions();
					this.timewindowList = micsCommonSessionHandler.getTimeWindows();
					
					session.put(MICSConstant.MICS_SESSION_ANNOLIST, this.annoList);
					session.put(MICSConstant.MICS_SESSION_TIMEWINDOWLIST, this.timewindowList);
					session.put(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST, this.huntingoptionList);
				} else {
					this.tselAdmin = false;
					this.companykey = loginWebUser.getCompanykey();
					this.annoList = micsCommonSessionHandler.getCompanyAnno(companykey);
					this.huntingoptionList = micsCommonSessionHandler.getHuntingOptions();
					this.timewindowList = micsCommonSessionHandler.getTimeWindows();
					session.put(MICSConstant.MICS_SESSION_ANNOLIST, annoList);
					session.put(MICSConstant.MICS_SESSION_TIMEWINDOWLIST, this.timewindowList);
					session.put(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST, this.huntingoptionList);
				}
		} else {
			this.huntingoptionList = micsCommonSessionHandler.getHuntingOptions();
			this.timewindowList = micsCommonSessionHandler.getTimeWindows();
			session.put(MICSConstant.MICS_SESSION_TIMEWINDOWLIST, this.timewindowList);
			session.put(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST, this.huntingoptionList);
			this.annoList = new ArrayList();
			session.put(MICSConstant.MICS_SESSION_ANNOLIST, this.annoList);
			
		}
		
	}

	/**
	 * @return the reverseCharging
	 */
	public BigDecimal getReverseCharging() {
		return reverseCharging;
	}

	/**
	 * @param reverseCharging the reverseCharging to set
	 */
	public void setReverseCharging(BigDecimal reverseCharging) {
		this.reverseCharging = reverseCharging;
	}
	
	/**
	 * @return the boolReverseCharging
	 */
	public boolean isBoolReverseCharging() {
		return boolReverseCharging;
	}
	/**
	 * @param boolReverseCharging the boolReverseCharging to set
	 */
	public void setBoolReverseCharging(boolean boolReverseCharging) {
		this.boolReverseCharging = boolReverseCharging;
	}
}
