package com.nsn.telkomsel.mics.action;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.nsn.telkomsel.mics.MICSException;
import com.nsn.telkomsel.mics.handler.MICSCommonSessionHandler;
import com.nsn.telkomsel.mics.orahbm.Micsgroup;
import com.nsn.telkomsel.mics.orahbm.MicsgroupId;
import com.nsn.telkomsel.mics.orahbm.Webuser;
import com.nsn.telkomsel.mics.orahbm.Company;
import com.nsn.telkomsel.mics.util.MICSConstant;
import com.nsn.telkomsel.mics.util.MICSPasswordEnc;
import com.nsn.telkomsel.mics.util.Micslogger;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * CompanyAction.java
 * 
 * @author mulia
 * @version $Id: CompanyAction.java,v 1.1.4.3 2019/03/22 10:05:46 cvsuser Exp $
 */
public class CompanyAction extends ActionSupport implements SessionAware,Preparable {


	private static final long serialVersionUID = -7891588446903022422L;
	
	public String initCreate(){
		logger.info("initCreate()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(companyCreate, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						tselAdmin = true;
					} else {
						tselAdmin = false;
					}
					outgoingProfiles = micsCommonSessionHandler.getMICSProfileList("", MICSConstant.MICS_SCREENING_TYPE_OUTGOING, MICSConstant.MICS_SCREENING_LEVEL_COMPANY);
					session.put(MICSConstant.MICS_SESSION_OUTGOINGLIST, outgoingProfiles);
					incomingProfiles = micsCommonSessionHandler.getMICSProfileList("", MICSConstant.MICS_SCREENING_TYPE_INCOMING, MICSConstant.MICS_SCREENING_LEVEL_COMPANY);
					session.put(MICSConstant.MICS_SESSION_OUTGOINGLIST, incomingProfiles);
					//Set Default Values
					String strLastCompanyKey = micsCommonSessionHandler.getLastCompanyKey();
					try {
						int intLastCompanyKey = Integer.parseInt(strLastCompanyKey);
						intLastCompanyKey++;
						this.companykey = intLastCompanyKey+"";
						this.chargestring = "0000";
						this.companycli = this.companykey;
					} catch (Exception e) {
						//Do nothing for now; What should be given if this is the first company
						this.companykey = "";
						this.chargestring = "0000";
						this.companycli = "";
					} 
					
					this.usemaincliprefix="*";
					this.clioption = new BigDecimal(0);
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
			logger.error("Failed to initialize Company admin");
			addActionError("Failed to process your Company administration request");
			result = MICSConstant.MICS_ERROR;
		} 
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
				if (micsCommonSessionHandler.isMenuAuthorized(companyCreate, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug(loginWebUser.getWebusername() + "authorized to create company " + companyname);
					
					outgoingProfiles = micsCommonSessionHandler.getMICSProfileList("", MICSConstant.MICS_SCREENING_TYPE_OUTGOING, MICSConstant.MICS_SCREENING_LEVEL_COMPANY);
					session.put(MICSConstant.MICS_SESSION_OUTGOINGLIST, outgoingProfiles);
					incomingProfiles = micsCommonSessionHandler.getMICSProfileList("", MICSConstant.MICS_SCREENING_TYPE_INCOMING, MICSConstant.MICS_SCREENING_LEVEL_COMPANY);
					session.put(MICSConstant.MICS_SESSION_OUTGOINGLIST, incomingProfiles);
					//Check if the company key already been used
					Company companyExist = micsCommonSessionHandler.getCompanyByKey(this.companykey);
					if (companyExist != null) {
						addActionError("Duplicate company key, company with key " + this.companykey + " already exist");
						Micslogger.log("Create","Company",  "Failed to create " + this.companyname +", duplicate company key " + this.companykey, loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"FAILED");
					} else {
						Company newCompany = new Company(this.companykey,this.companyname,this.contactperson,loginWebUser.getWebusername(),new Timestamp(System.currentTimeMillis()));
						newCompany.setEmail(this.email);
						newCompany.setAddress(this.address);
						newCompany.setDomainname(this.domainname);
						newCompany.setChargestring(this.chargestring);
						if (boolLocked) {
							newCompany.setLocked(new BigDecimal(1));
						} else {
							newCompany.setLocked(new BigDecimal(0));
						}
						if (boolForcedOnnet) {
							newCompany.setEnableforcedonnet(new BigDecimal(1));
						} else {
							newCompany.setEnableforcedonnet(new BigDecimal(0));
						}
						if (boolEnableGroupHunting) {
							newCompany.setEnablegrouphunting(new BigDecimal(1));
						} else {
							newCompany.setEnablegrouphunting(new BigDecimal(0));
						}
						if (boolEnablePrivCall) {
							newCompany.setEnableprivatecall(new BigDecimal(1));
						} else {
							newCompany.setEnableprivatecall(new BigDecimal(0));
						}
						if (boolEnablevpnchargingforsms) {
							newCompany.setEnablevpnchargingforsms(new BigDecimal(1));
						} else {
							newCompany.setEnablevpnchargingforsms(new BigDecimal(0));
						}
						newCompany.setCompanycli(this.companycli);
						newCompany.setUsemaincliprefix(this.usemaincliprefix);
						newCompany.setClioption(this.clioption);
						logger.info("forcedincs "+this.forcedincs);
						logger.info("forcedoutcs "+this.forcedoutcs);
						logger.info("defaultincs "+this.defaultincs);
						logger.info("defaultoutcs "+this.defaultoutcs);
						if (this.forcedincs.equals("")) {							
							newCompany.setForcedincs(this.forcedincs);
							newCompany.setForcedincsdesc(this.forcedincsdesc);
							if (boolBypassForcedInCS) {
								newCompany.setBypassforcedincs("1");
							} else {
								newCompany.setBypassforcedincs("0");
							}
						}
						if (this.defaultincs.equals("")) {
							newCompany.setDefaultincs(this.defaultincs);
							newCompany.setDefaultincsdesc(this.defaultincsdesc);
							if (boolBypassDefInCS) {
								newCompany.setBypassdefaultincs("1");
							} else {
								newCompany.setBypassdefaultincs("0");
							}
						}
						if (this.forcedoutcs.equals("")) {
							newCompany.setForcedoutcs(this.forcedoutcs);
							newCompany.setForcedoutcsdesc(this.forcedoutcsdesc);
							if (boolBypassForcedOutCS) {
								newCompany.setBypassforcedoutcs("1");
							} else {
								newCompany.setBypassforcedoutcs("0");
							}
						}
						if (this.defaultoutcs.equals("")){
							newCompany.setDefaultoutcs(this.defaultoutcs);
							newCompany.setDefaultoutcsdesc(this.defaultoutcsdesc);
							if (boolBypassDefaultOutCS) {
								newCompany.setBypassdefaultoutcs("1");
							} else {
								newCompany.setBypassdefaultoutcs("0");
							}
						}
						//Create Default Group
						
//						MicsgroupId newMicsgroupId = new MicsgroupId(this.companykey+"1", this.companykey, new BigDecimal(1), "Default Group", this.companykey+"1", "", "", "", "", "", "", loginWebUser.getWebusername(), new Timestamp(System.currentTimeMillis()));
						Micsgroup newMicsgroup = new Micsgroup(this.companykey+"1",this.companykey,new BigDecimal(1),"Default Group",null,loginWebUser.getWebusername(),new Timestamp(System.currentTimeMillis()));
//						newMicsgroup.setId(newMicsgroupId);
						
						//Create Default Webuser
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
						sdf.format(new Date());
						String awalKey = sdf.format(new Date());
						String micsUserAvailableKey = micsCommonSessionHandler.getAvailableKey(awalKey, "webuserkey", "Webuser");
						logger.debug("micsUserAvailableKey: "+micsUserAvailableKey);
						Webuser newWebUser = new Webuser();
						newWebUser.setWebuserkey(micsUserAvailableKey);
						newWebUser.setWebusername(this.getCompanyWebUserName());
						MICSPasswordEnc mpe = new MICSPasswordEnc();
						newWebUser.setWebuserpassword(mpe.getEncryptedPassword(this.getCompanyWebUserPassword()));
						newWebUser.setCompanykey(this.companykey);
						newWebUser.setRolekey(MICSConstant.MICS_ROLE_ADMIN_COMPANY);
						newWebUser.setUpdateuser(loginWebUser.getWebusername());
						newWebUser.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
						micsCommonSessionHandler.createCompany(newCompany, newMicsgroup, newWebUser);
						logger.debug("create company with group and web user success");
						addActionMessage("Company " + newCompany.getCompanyname() + " successfully created");
						Micslogger.log("Create","Company",  newCompany.getCompanyname() +" created", loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"SUCCESS");
					}
					
					result = MICSConstant.MICS_SUCCESS;
				} else {
					addActionError("User not authorized");
					Micslogger.log("Create","Company",  "Not authorized", loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"FAILED");
					result = MICSConstant.MICS_ERROR_NOT_AUTH;
				}
			} else {
				addActionError("User not logged in");
				result = MICSConstant.MICS_ERROR_NOT_LOGGEDIN;
			}
		} catch (Exception e) {
			logger.error("Failed to create Company",e);
			addActionError("Failed to create Company " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
	}
	
	public String initSearch(){
		logger.info("initSearch()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(companySearch, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						tselAdmin = true;
					} else {
						tselAdmin = false;
					}
					if (micsCommonSessionHandler.isMenuAuthorized(companyDelete, loginWebUser.getRolekey())) {
						this.deleteAllowed = true;
					} else {
						this.deleteAllowed = false;
					}
					if (micsCommonSessionHandler.isMenuAuthorized(companyEdit, loginWebUser.getRolekey())) {
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
			logger.error("Failed to initialize Company Search");
			addActionError("Failed to process your Company search request");
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
				if (micsCommonSessionHandler.isMenuAuthorized(companySearch, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug(loginWebUser.getWebusername() + "authorized to search company " + companykey + " "+companyname);
					//Search Company
					companies = null;
					if (searchCnt > 0) {
						companies = micsCommonSessionHandler.searchCompanies(companykey, companyname, chargestring);
					}
					searchCnt++;
					
					logger.debug("search company success");
					if (micsCommonSessionHandler.isMenuAuthorized(companyDelete, loginWebUser.getRolekey())) {
						this.deleteAllowed = true;
					} else {
						this.deleteAllowed = false;
					}
					if (micsCommonSessionHandler.isMenuAuthorized(companyEdit, loginWebUser.getRolekey())) {
						this.editAllowed = true;
					} else {
						this.editAllowed = false;
					}
					Micslogger.log("Search","Company", "Search company by Key " + companykey + " Name " + companyname + " Charge String " + chargestring, loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"SUCCESS");
					result = MICSConstant.MICS_SUCCESS;
				} else {
					Micslogger.log("Search","Company", "Search company by Key " + companykey + " Name " + companyname + " Charge String " + chargestring, loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"FAILED");
					addActionError("User not authorized");
					result = MICSConstant.MICS_ERROR_NOT_AUTH;
				}
			} else {
				addActionError("User not logged in");
				result = MICSConstant.MICS_ERROR_NOT_LOGGEDIN;
			}
		} catch (Exception e) {
			logger.error("Failed to search Company",e);
			addActionError("Failed to search Company");
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
				if (micsCommonSessionHandler.isMenuAuthorized(companyEdit, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					Company currCompany = null;
					logger.debug("companykey: " + companykey);
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						tselAdmin = true;
						if (companykey != null && !companykey.equals("")) {
							logger.debug("companykey found, search by company key");
							currCompany = micsCommonSessionHandler.getCompanyByKey(companykey);
						} else {
							logger.debug("companykey not found, tsel admin get company from session");
							currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
							
						}
					} else {
						tselAdmin = false;
						logger.debug("Not TSel admin get company key from user company key");
						currCompany = micsCommonSessionHandler.getCompanyByKey(loginWebUser.getCompanykey());
					}
					if (currCompany == null) {
						throw new MICSException("Company not found");
					}
					
					outgoingProfiles = micsCommonSessionHandler.getMICSProfileList(null, MICSConstant.MICS_SCREENING_TYPE_OUTGOING, MICSConstant.MICS_SCREENING_LEVEL_COMPANY);
					session.put(MICSConstant.MICS_SESSION_OUTGOINGLIST, outgoingProfiles);
					incomingProfiles = micsCommonSessionHandler.getMICSProfileList(null, MICSConstant.MICS_SCREENING_TYPE_INCOMING, MICSConstant.MICS_SCREENING_LEVEL_COMPANY);
					session.put(MICSConstant.MICS_SESSION_OUTGOINGLIST, incomingProfiles);
					session.put(MICSConstant.MICS_SESSION_CURRCOMPANY, currCompany);
					logger.debug("currCompany: " + currCompany);
					this.companykey = currCompany.getCompanykey();
					this.companyname = currCompany.getCompanyname();
					this.domainname = currCompany.getDomainname();
					this.address = currCompany.getAddress();
					this.chargestring = currCompany.getChargestring();
					this.contactperson = currCompany.getContactperson();
					this.email = currCompany.getEmail();
					BigDecimal bdTrue = new BigDecimal(1);
					if (currCompany.getEnableforcedonnet()!= null &&currCompany.getEnableforcedonnet().equals(bdTrue)) {
						this.boolForcedOnnet = true;
					} else {
						this.boolForcedOnnet = false;
					}
					if (currCompany.getEnablegrouphunting() != null && currCompany.getEnablegrouphunting().equals(bdTrue)) {
						this.boolEnableGroupHunting = true;
					} else {
						this.boolEnableGroupHunting = false;
					}
					if (currCompany.getLocked() != null && currCompany.getLocked().equals(bdTrue)) {
						this.boolLocked = true;
					} else {
						this.boolLocked = false;
					}
					if (currCompany.getEnableprivatecall() != null && currCompany.getEnableprivatecall().equals(bdTrue)) {
						this.boolEnablePrivCall = true;
					} else {
						this.boolEnablePrivCall = false;
					}
					if (currCompany.getEnablevpnchargingforsms() != null && currCompany.getEnablevpnchargingforsms().equals(bdTrue)) {
						this.boolEnablevpnchargingforsms = true;
					} else {
						this.boolEnablevpnchargingforsms = false;
					}
					bdTrue = null;
					this.companycli = currCompany.getCompanycli();
					this.usemaincliprefix = currCompany.getUsemaincliprefix();
					this.clioption = currCompany.getClioption();
					logger.debug("Forcedoutcs --> "+ currCompany.getForcedoutcs());
					if (currCompany.getForcedoutcs() != null && !currCompany.getForcedoutcs().equals("")) {
						this.forcedoutcs = currCompany.getForcedoutcs();
						this.forcedoutcsdesc = currCompany.getForcedoutcsdesc();
						if (currCompany.getBypassforcedoutcs().equals("1")) {
							this.boolBypassForcedOutCS = true;
						} else {
							this.boolBypassForcedOutCS = false;

						}
					}
					logger.debug("Defaultoutcs --> "+ currCompany.getDefaultoutcs());
					if (currCompany.getDefaultoutcs() != null && !currCompany.getDefaultoutcs().equals("")) {
						this.defaultoutcs = currCompany.getDefaultoutcs();
						this.defaultoutcsdesc = currCompany.getDefaultoutcsdesc();
						if (currCompany.getBypassdefaultoutcs().equals("1")) {
							this.boolBypassDefaultOutCS = true;
						} else {
							this.boolBypassDefaultOutCS = false;
						}
					}
					logger.debug("Forcedincs --> "+ currCompany.getForcedincs());
					if ( currCompany.getForcedincs() != null &&  !currCompany.getForcedincs().equals("")) {
						this.forcedincs = currCompany.getForcedincs();
						this.forcedincsdesc = currCompany.getForcedincsdesc();
						if (currCompany.getBypassforcedincs().equals("1")) {
							this.boolBypassForcedInCS = true;
						} else {
							this.boolBypassForcedInCS = false;
						}
					}
					logger.debug("Defaultincs --> "+ currCompany.getDefaultincs());
					if (currCompany.getDefaultincs() != null && !currCompany.getDefaultincs().equals("")) {
						this.defaultincs = currCompany.getDefaultincs();
						this.defaultincsdesc = currCompany.getDefaultincsdesc();
						if (currCompany.getBypassdefaultincs().equals("1")) {
							this.boolBypassDefInCS = true;
						} else {
							this.boolBypassDefInCS = false;
						}
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
			logger.error("Failed to initialize Company Edit",e);
			addActionError("Failed to process your Company edit request " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
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
				if (micsCommonSessionHandler.isMenuAuthorized(companyEdit, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						tselAdmin = true;
					} else {
						tselAdmin = false;
					}
					logger.debug(loginWebUser.getWebusername() + "authorized to edit company " + companyname);
					
					outgoingProfiles = micsCommonSessionHandler.getMICSProfileList(null, MICSConstant.MICS_SCREENING_TYPE_OUTGOING, MICSConstant.MICS_SCREENING_LEVEL_COMPANY);
					session.put(MICSConstant.MICS_SESSION_OUTGOINGLIST, outgoingProfiles);
					incomingProfiles = micsCommonSessionHandler.getMICSProfileList(null, MICSConstant.MICS_SCREENING_TYPE_INCOMING, MICSConstant.MICS_SCREENING_LEVEL_COMPANY);
					session.put(MICSConstant.MICS_SESSION_OUTGOINGLIST, incomingProfiles);
					
					Company editCompany = micsCommonSessionHandler.getCompanyByKey(this.companykey);
					editCompany.setCompanyname(this.companyname);
				    editCompany.setContactperson(this.contactperson);
					editCompany.setEmail(this.email);
					editCompany.setAddress(this.address);
					editCompany.setChargestring(this.chargestring);
					editCompany.setDomainname(this.domainname);
					if (boolLocked) {
						editCompany.setLocked(new BigDecimal(1));
					} else {
						editCompany.setLocked(new BigDecimal(0));
					}
					if (boolForcedOnnet) {
						editCompany.setEnableforcedonnet(new BigDecimal(1));
					} else {
						editCompany.setEnableforcedonnet(new BigDecimal(0));
					}
					if (boolEnableGroupHunting) {
						editCompany.setEnablegrouphunting(new BigDecimal(1));
					} else {
						editCompany.setEnablegrouphunting(new BigDecimal(0));
					}
					if (boolEnablePrivCall) {
						editCompany.setEnableprivatecall(new BigDecimal(1));
					} else {
						editCompany.setEnableprivatecall(new BigDecimal(0));
					} 
					if (boolEnablevpnchargingforsms) {
						editCompany.setEnablevpnchargingforsms(new BigDecimal(1));
					} else {
						editCompany.setEnablevpnchargingforsms(new BigDecimal(0));
					}
					editCompany.setCompanycli(this.companycli);
					editCompany.setUsemaincliprefix(this.usemaincliprefix);
					editCompany.setClioption(this.clioption);
					
					logger.debug("forcedincs --> "+this.forcedincs);
					if (!this.forcedincs.equals("")) {
						editCompany.setForcedincs(this.forcedincs);
						editCompany.setForcedincsdesc(this.forcedincsdesc);
						if (boolBypassForcedInCS) {
							editCompany.setBypassforcedincs("1");
						} else {
							editCompany.setBypassforcedincs("0");
						}
					} else {
						editCompany.setForcedincs("");
						editCompany.setForcedincsdesc("");
						editCompany.setBypassforcedincs("0");
					}
					logger.debug("Edit forcedincs --> " + editCompany.getForcedincs());
					logger.debug("defaultincs -->"+this.defaultincs);
					if (!this.defaultincs.equals("")) {
						editCompany.setDefaultincs(this.defaultincs);
						editCompany.setDefaultincsdesc(this.defaultincsdesc);
						if (boolBypassDefInCS) {
							editCompany.setBypassdefaultincs("1");
						} else {
							editCompany.setBypassdefaultincs("0");
						}
					} else {
						editCompany.setDefaultincs("");
						editCompany.setDefaultincsdesc("");
						editCompany.setBypassdefaultincs("0");
					}
					logger.debug("Edit defaultincs --> " + editCompany.getDefaultincs());
					logger.debug("forcedoutcs -->"+this.forcedoutcs);
					if (!this.forcedoutcs.equals("")) {
						editCompany.setForcedoutcs(this.forcedoutcs);
						editCompany.setForcedoutcsdesc(this.forcedoutcsdesc);
						if (boolBypassForcedOutCS) {
							editCompany.setBypassforcedoutcs("1");
						} else {
							editCompany.setBypassforcedoutcs("0");
						}
					} else {
						editCompany.setForcedoutcs("");
						editCompany.setForcedoutcsdesc("");
						editCompany.setBypassforcedoutcs("0");
					}
					logger.debug("Edit forcedoutcs --> "+ editCompany.getForcedoutcs());
					logger.debug("defaultoutcs -->"+this.defaultoutcs);
					if (!this.defaultoutcs.equals("")){
						editCompany.setDefaultoutcs(this.defaultoutcs);
						editCompany.setDefaultoutcsdesc(this.defaultoutcsdesc);
						if (boolBypassDefaultOutCS) {
							editCompany.setBypassdefaultoutcs("1");
						} else {
							editCompany.setBypassdefaultoutcs("0");
						}
					} else {
						editCompany.setDefaultoutcs("");
						editCompany.setDefaultoutcsdesc("");
						editCompany.setBypassdefaultoutcs("0");
					}
					logger.debug("Edit defaultoutcs --> " + editCompany.getDefaultoutcs());
					editCompany.setUpdateuser(loginWebUser.getWebusername());
					editCompany.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
					micsCommonSessionHandler.editCompany(editCompany);
					session.put(MICSConstant.MICS_SESSION_CURRCOMPANY, editCompany);
					logger.debug("edit company success");
					addActionMessage("Company " + editCompany.getCompanyname() + " successfully edited");
					Micslogger.log("Edit","Company",  editCompany.getCompanykey()+" "+ editCompany.getCompanyname()+" edited" , loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"SUCCESS");
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
			logger.error("Failed to edit Company",e);
			addActionError("Failed to edit Company " + e.getMessage());
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
				if (micsCommonSessionHandler.isMenuAuthorized(companyView, loginWebUser.getRolekey())) {
					//Check if User have rights to edit company
					if (micsCommonSessionHandler.isMenuAuthorized(companyEdit, loginWebUser.getRolekey())) {
						this.editAllowed = true;
					} else {
						this.editAllowed = false;
					}
					//Authorize do some stuff here
					Company currCompany = null;
					logger.debug("companykey: " + companykey);
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						tselAdmin = true;
						if (companykey != null && !companykey.equals("")) {
							logger.debug("companykey found, search by company key");
							currCompany = micsCommonSessionHandler.getCompanyByKey(companykey);
						} else {
							logger.debug("companykey not found, tsel admin get company from session");
							currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
							
						}
					} else {
						tselAdmin = false;
						logger.debug("Not TSel admin get company key from user company key");
						currCompany = micsCommonSessionHandler.getCompanyByKey(loginWebUser.getCompanykey());
					}
					if (currCompany == null) {
						throw new MICSException("Company not found");
					}
					
					outgoingProfiles = micsCommonSessionHandler.getMICSProfileList(null, MICSConstant.MICS_SCREENING_TYPE_OUTGOING, MICSConstant.MICS_SCREENING_LEVEL_COMPANY);
					session.put(MICSConstant.MICS_SESSION_OUTGOINGLIST, outgoingProfiles);
					incomingProfiles = micsCommonSessionHandler.getMICSProfileList(null, MICSConstant.MICS_SCREENING_TYPE_INCOMING, MICSConstant.MICS_SCREENING_LEVEL_COMPANY);
					session.put(MICSConstant.MICS_SESSION_OUTGOINGLIST, incomingProfiles);
					session.put(MICSConstant.MICS_SESSION_CURRCOMPANY, currCompany);
					logger.debug("currCompany: " + currCompany);
					this.companykey = currCompany.getCompanykey();
					this.companyname = currCompany.getCompanyname();
					this.domainname = currCompany.getDomainname();
					this.address = currCompany.getAddress();
					this.chargestring = currCompany.getChargestring();
					this.contactperson = currCompany.getContactperson();
					this.email = currCompany.getEmail();
					BigDecimal bdTrue = new BigDecimal(1);
					if (currCompany.getEnableforcedonnet()!= null &&currCompany.getEnableforcedonnet().equals(bdTrue)) {
						this.boolForcedOnnet = true;
					} else {
						this.boolForcedOnnet = false;
					}
					if (currCompany.getEnablegrouphunting() != null && currCompany.getEnablegrouphunting().equals(bdTrue)) {
						this.boolEnableGroupHunting = true;
					} else {
						this.boolEnableGroupHunting = false;
					}
					if (currCompany.getLocked() != null && currCompany.getLocked().equals(bdTrue)) {
						this.boolLocked = true;
					} else {
						this.boolLocked = false;
					}
					if (currCompany.getEnableprivatecall() != null && currCompany.getEnableprivatecall().equals(bdTrue)) {
						this.boolEnablePrivCall = true;
					} else {
						this.boolEnablePrivCall = false;
					}
					if (currCompany.getEnablevpnchargingforsms() != null && currCompany.getEnablevpnchargingforsms().equals(bdTrue)) {
						this.boolEnablevpnchargingforsms = true;
					} else {
						this.boolEnablevpnchargingforsms = false;
					}
					bdTrue = null;
					this.companycli = currCompany.getCompanycli();
					this.usemaincliprefix = currCompany.getUsemaincliprefix();
					this.clioption = currCompany.getClioption();
					if (currCompany.getForcedoutcs() != null && !currCompany.getForcedoutcs().equals("")) {
						this.forcedoutcs = currCompany.getForcedoutcs();
						this.forcedoutcsdesc = currCompany.getForcedoutcsdesc();
						if (currCompany.getBypassforcedoutcs().equals("1")) {
							this.boolBypassForcedOutCS = true;
						} else {
							this.boolBypassForcedOutCS = false;

						}
					}
					if (currCompany.getDefaultoutcs() != null && !currCompany.getDefaultoutcs().equals("")) {
						this.defaultoutcs = currCompany.getDefaultoutcs();
						this.defaultoutcsdesc = currCompany.getDefaultoutcsdesc();
						if (currCompany.getBypassdefaultoutcs().equals("1")) {
							this.boolBypassDefaultOutCS = true;
						} else {
							this.boolBypassDefaultOutCS = false;
						}
					}
					if ( currCompany.getForcedincs() != null &&  !currCompany.getForcedincs().equals("")) {
						this.forcedincs = currCompany.getForcedincs();
						this.forcedincsdesc = currCompany.getForcedincsdesc();
						if (currCompany.getBypassforcedincs().equals("1")) {
							this.boolBypassForcedInCS = true;
						} else {
							this.boolBypassForcedInCS = false;
						}
					}
					if (currCompany.getDefaultincs() != null && !currCompany.getDefaultincs().equals("")) {
						this.defaultincs = currCompany.getDefaultincs();
						this.defaultincsdesc = currCompany.getDefaultincsdesc();
						if (currCompany.getBypassdefaultincs().equals("1")) {
							this.boolBypassDefInCS = true;
						} else {
							this.boolBypassDefInCS = false;
						}
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
			logger.error("Failed to initialize Company View",e);
			addActionError("Failed to process your Company view request " + e.getMessage());
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
				if (micsCommonSessionHandler.isMenuAuthorized(companyDelete, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					Company currCompany = null;
					logger.debug("companykey: " + companykey);
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						tselAdmin = true;
						if (companykey != null && !companykey.equals("")) {
							logger.debug("companykey found, search by company key");
							currCompany = micsCommonSessionHandler.getCompanyByKey(companykey);
						} else {
							logger.debug("companykey not found, tsel admin get company from session");
							currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
						}
					} else {
						tselAdmin = false;
						logger.debug("Not TSel admin get company key from user company key");
						currCompany = micsCommonSessionHandler.getCompanyByKey(loginWebUser.getCompanykey());
					}
					if (currCompany == null) {
						throw new MICSException("Company not found");
					}
					session.put(MICSConstant.MICS_SESSION_CURRCOMPANY, currCompany);
					logger.debug("currCompany: " + currCompany);
					this.companykey = currCompany.getCompanykey();
					this.companyname = currCompany.getCompanyname();
					this.address = currCompany.getAddress();
					this.chargestring = currCompany.getChargestring();
					this.contactperson = currCompany.getContactperson();
					this.domainname = currCompany.getDomainname();
					this.email = currCompany.getEmail();					
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
			logger.error("Failed to initialize Company delete",e);
			addActionError("Failed to process your Company delete request "+ e.getMessage());
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
				if (micsCommonSessionHandler.isMenuAuthorized(companyDelete, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						tselAdmin = true;
					} else {
						tselAdmin = false;
					}
					logger.debug(loginWebUser.getWebusername() + "authorized to delete company " + companyname);
					
					Company newCompany = new Company(this.companykey,this.companyname,this.contactperson,loginWebUser.getWebusername(),new Timestamp(System.currentTimeMillis()));
					newCompany.setEmail(this.email);
					newCompany.setAddress(this.address);
					newCompany.setChargestring(this.chargestring);
					//Check constrains before delete
					//Check user 
					int userCount = micsCommonSessionHandler.checkUserCountByCompany(companykey);
					logger.debug("userCount: " + userCount);
					if (userCount > 0) {
						throw new Exception("Several user still exist for this company " + companykey + " please delete all users first");
					} 
					//Check hunting group
					int hgCount = micsCommonSessionHandler.checkHGCountByCompany(companykey);
					logger.debug("hgCount: " + hgCount);
					if (hgCount > 0) {
						throw new Exception("Several hunting group still exist for this company " + companykey + " please delete all Hunting groups first");
					}
					
					micsCommonSessionHandler.deleteCompany(newCompany);
					
					session.remove(MICSConstant.MICS_SESSION_CURRCOMPANY);
					logger.debug("Delete company success");
					addActionMessage("Company " + newCompany.getCompanyname() + " successfully deleted");
					Micslogger.log("Delete","Company",  newCompany.getCompanyname() +" deleted", loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"SUCCESS");
					result = MICSConstant.MICS_SUCCESS;
				} else {
					addActionError("User not authorized");
					Micslogger.log("Delete","Company",  "Not authorized to delete "+ this.companykey, loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"FAILED");
					result = MICSConstant.MICS_ERROR_NOT_AUTH;
				}
			} else {
				addActionError("User not logged in");
				result = MICSConstant.MICS_ERROR_NOT_LOGGEDIN;
			}
		} catch (Exception e) {
			logger.error("Failed to delete Company",e);
			addActionError("Failed to delete Company "+ e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
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
	 * @return the domainname
	 */
	public String getDomainname() {
		return domainname;
	}

	/**
	 * @param domainname the domainname to set
	 */
	public void setDomainname(String domainname) {
		this.domainname = domainname;
	}

	/**
	 * @return the chargestring
	 */
	public String getChargestring() {
		return chargestring;
	}
	/**
	 * @param chargestring the chargestring to set
	 */
	public void setChargestring(String chargestring) {
		this.chargestring = chargestring;
	}
	/**
	 * @return the locked
	 */
	public BigDecimal getLocked() {
		return locked;
	}
	/**
	 * @param locked the locked to set
	 */
	public void setLocked(BigDecimal locked) {
		this.locked = locked;
	}
	/**
	 * @return the boolLocked
	 */
	public boolean isBoolLocked() {
		return boolLocked;
	}
	/**
	 * @param boolLocked the boolLocked to set
	 */
	public void setBoolLocked(boolean boolLocked) {
		this.boolLocked = boolLocked;
	}
	/**
	 * @return the enableforcedonnet
	 */
	public BigDecimal getEnableforcedonnet() {
		return enableforcedonnet;
	}
	/**
	 * @param enableforcedonnet the enableforcedonnet to set
	 */
	public void setEnableforcedonnet(BigDecimal enableforcedonnet) {
		this.enableforcedonnet = enableforcedonnet;
	}
	/**
	 * @return the boolForcedOnnet
	 */
	public boolean isBoolForcedOnnet() {
		return boolForcedOnnet;
	}
	/**
	 * @param boolForcedOnnet the boolForcedOnnet to set
	 */
	public void setBoolForcedOnnet(boolean boolForcedOnnet) {
		this.boolForcedOnnet = boolForcedOnnet;
	}
	/**
	 * @return the enablegrouphunting
	 */
	public BigDecimal getEnablegrouphunting() {
		return enablegrouphunting;
	}
	/**
	 * @param enablegrouphunting the enablegrouphunting to set
	 */
	public void setEnablegrouphunting(BigDecimal enablegrouphunting) {
		this.enablegrouphunting = enablegrouphunting;
	}
	/**
	 * @return the boolEnableGroupHunting
	 */
	public boolean isBoolEnableGroupHunting() {
		return boolEnableGroupHunting;
	}
	/**
	 * @param boolEnableGroupHunting the boolEnableGroupHunting to set
	 */
	public void setBoolEnableGroupHunting(boolean boolEnableGroupHunting) {
		this.boolEnableGroupHunting = boolEnableGroupHunting;
	}
	/**
	 * @return the enableprivatecall
	 */
	public BigDecimal getEnableprivatecall() {
		return enableprivatecall;
	}
	/**
	 * @param enableprivatecall the enableprivatecall to set
	 */
	public void setEnableprivatecall(BigDecimal enableprivatecall) {
		this.enableprivatecall = enableprivatecall;
	}
	/**
	 * @return the boolEnablePrivCall
	 */
	public boolean isBoolEnablePrivCall() {
		return boolEnablePrivCall;
	}
	/**
	 * @param boolEnablePrivCall the boolEnablePrivCall to set
	 */
	public void setBoolEnablePrivCall(boolean boolEnablePrivCall) {
		this.boolEnablePrivCall = boolEnablePrivCall;
	}
	/**
	 * @return the companycli
	 */
	public String getCompanycli() {
		return companycli;
	}
	/**
	 * @param companycli the companycli to set
	 */
	public void setCompanycli(String companycli) {
		this.companycli = companycli;
	}
	/**
	 * @return the usemaincliprefix
	 */
	public String getUsemaincliprefix() {
		return usemaincliprefix;
	}
	/**
	 * @param usemaincliprefix the usemaincliprefix to set
	 */
	public void setUsemaincliprefix(String usemaincliprefix) {
		this.usemaincliprefix = usemaincliprefix;
	}
	/**
	 * @return the clioption
	 */
	public BigDecimal getClioption() {
		return clioption;
	}
	/**
	 * @param clioption the clioption to set
	 */
	public void setClioption(BigDecimal clioption) {
		this.clioption = clioption;
	}
	/**
	 * @return the boolCliOption
	 */
	public boolean isBoolCliOption() {
		return boolCliOption;
	}
	/**
	 * @param boolCliOption the boolCliOption to set
	 */
	public void setBoolCliOption(boolean boolCliOption) {
		this.boolCliOption = boolCliOption;
	}
	/**
	 * @return the defaultincs
	 */
	
	
	public String getDefaultincs() {
		return defaultincs;
	}
	/**
	 * @return the enablevpnchargingforsms
	 */
	public BigDecimal getEnablevpnchargingforsms() {
		return enablevpnchargingforsms;
	}

	/**
	 * @param enablevpnchargingforsms the enablevpnchargingforsms to set
	 */
	public void setEnablevpnchargingforsms(BigDecimal enablevpnchargingforsms) {
		this.enablevpnchargingforsms = enablevpnchargingforsms;
	}

	/**
	 * @return the boolEnablevpnchargingforsms
	 */
	public boolean isBoolEnablevpnchargingforsms() {
		return boolEnablevpnchargingforsms;
	}

	/**
	 * @param boolEnablevpnchargingforsms the boolEnablevpnchargingforsms to set
	 */
	public void setBoolEnablevpnchargingforsms(boolean boolEnablevpnchargingforsms) {
		this.boolEnablevpnchargingforsms = boolEnablevpnchargingforsms;
	}

	/**
	 * @param defaultincs the defaultincs to set
	 */
	public void setDefaultincs(String defaultincs) {
		this.defaultincs = defaultincs;
	}
	/**
	 * @return the defaultincsdesc
	 */
	public String getDefaultincsdesc() {
		return defaultincsdesc;
	}
	/**
	 * @param defaultincsdesc the defaultincsdesc to set
	 */
	public void setDefaultincsdesc(String defaultincsdesc) {
		this.defaultincsdesc = defaultincsdesc;
	}
	/**
	 * @return the bypassdefaultincs
	 */
	public String getBypassdefaultincs() {
		return bypassdefaultincs;
	}
	/**
	 * @param bypassdefaultincs the bypassdefaultincs to set
	 */
	public void setBypassdefaultincs(String bypassdefaultincs) {
		this.bypassdefaultincs = bypassdefaultincs;
	}
	/**
	 * @return the boolBypassDefInCS
	 */
	public boolean isBoolBypassDefInCS() {
		return boolBypassDefInCS;
	}
	/**
	 * @param boolBypassDefInCS the boolBypassDefInCS to set
	 */
	public void setBoolBypassDefInCS(boolean boolBypassDefInCS) {
		this.boolBypassDefInCS = boolBypassDefInCS;
	}
	/**
	 * @return the forcedincs
	 */
	public String getForcedincs() {
		return forcedincs;
	}
	/**
	 * @param forcedincs the forcedincs to set
	 */
	public void setForcedincs(String forcedincs) {
		this.forcedincs = forcedincs;
	}
	/**
	 * @return the forcedincsdesc
	 */
	public String getForcedincsdesc() {
		return forcedincsdesc;
	}
	/**
	 * @param forcedincsdesc the forcedincsdesc to set
	 */
	public void setForcedincsdesc(String forcedincsdesc) {
		this.forcedincsdesc = forcedincsdesc;
	}
	/**
	 * @return the bypassforcedincs
	 */
	public String getBypassforcedincs() {
		return bypassforcedincs;
	}
	/**
	 * @param bypassforcedincs the bypassforcedincs to set
	 */
	public void setBypassforcedincs(String bypassforcedincs) {
		this.bypassforcedincs = bypassforcedincs;
	}
	/**
	 * @return the boolBypassForcedInCS
	 */
	public boolean isBoolBypassForcedInCS() {
		return boolBypassForcedInCS;
	}
	/**
	 * @param boolBypassForcedInCS the boolBypassForcedInCS to set
	 */
	public void setBoolBypassForcedInCS(boolean boolBypassForcedInCS) {
		this.boolBypassForcedInCS = boolBypassForcedInCS;
	}
	/**
	 * @return the defaultoutcs
	 */
	public String getDefaultoutcs() {
		return defaultoutcs;
	}
	/**
	 * @param defaultoutcs the defaultoutcs to set
	 */
	public void setDefaultoutcs(String defaultoutcs) {
		this.defaultoutcs = defaultoutcs;
	}
	/**
	 * @return the defaultoutcsdesc
	 */
	public String getDefaultoutcsdesc() {
		return defaultoutcsdesc;
	}
	/**
	 * @param defaultoutcsdesc the defaultoutcsdesc to set
	 */
	public void setDefaultoutcsdesc(String defaultoutcsdesc) {
		this.defaultoutcsdesc = defaultoutcsdesc;
	}
	/**
	 * @return the bypassdefaultoutcs
	 */
	public String getBypassdefaultoutcs() {
		return bypassdefaultoutcs;
	}
	/**
	 * @param bypassdefaultoutcs the bypassdefaultoutcs to set
	 */
	public void setBypassdefaultoutcs(String bypassdefaultoutcs) {
		this.bypassdefaultoutcs = bypassdefaultoutcs;
	}
	/**
	 * @return the boolBypassDefaultOutCS
	 */
	public boolean isBoolBypassDefaultOutCS() {
		return boolBypassDefaultOutCS;
	}
	/**
	 * @param boolBypassDefaultOutCS the boolBypassDefaultOutCS to set
	 */
	public void setBoolBypassDefaultOutCS(boolean boolBypassDefaultOutCS) {
		this.boolBypassDefaultOutCS = boolBypassDefaultOutCS;
	}
	/**
	 * @return the forcedoutcs
	 */
	public String getForcedoutcs() {
		return forcedoutcs;
	}
	/**
	 * @param forcedoutcs the forcedoutcs to set
	 */
	public void setForcedoutcs(String forcedoutcs) {
		this.forcedoutcs = forcedoutcs;
	}
	/**
	 * @return the forcedoutcsdesc
	 */
	public String getForcedoutcsdesc() {
		return forcedoutcsdesc;
	}
	/**
	 * @param forcedoutcsdesc the forcedoutcsdesc to set
	 */
	public void setForcedoutcsdesc(String forcedoutcsdesc) {
		this.forcedoutcsdesc = forcedoutcsdesc;
	}
	/**
	 * @return the bypassforcedoutcs
	 */
	public String getBypassforcedoutcs() {
		return bypassforcedoutcs;
	}
	/**
	 * @param bypassforcedoutcs the bypassforcedoutcs to set
	 */
	public void setBypassforcedoutcs(String bypassforcedoutcs) {
		this.bypassforcedoutcs = bypassforcedoutcs;
	}
	/**
	 * @return the boolBypassForcedOutCS
	 */
	public boolean isBoolBypassForcedOutCS() {
		return boolBypassForcedOutCS;
	}
	/**
	 * @param boolBypassForcedOutCS the boolBypassForcedOutCS to set
	 */
	public void setBoolBypassForcedOutCS(boolean boolBypassForcedOutCS) {
		this.boolBypassForcedOutCS = boolBypassForcedOutCS;
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
	 * @return the companyWebUserName
	 */
	public String getCompanyWebUserName() {
		return companyWebUserName;
	}
	/**
	 * @param companyWebUserName the companyWebUserName to set
	 */
	public void setCompanyWebUserName(String companyWebUserName) {
		this.companyWebUserName = companyWebUserName;
	}
	/**
	 * @return the companyWebUserPassword
	 */
	public String getCompanyWebUserPassword() {
		return companyWebUserPassword;
	}
	/**
	 * @param companyWebUserPassword the companyWebUserPassword to set
	 */
	public void setCompanyWebUserPassword(String companyWebUserPassword) {
		this.companyWebUserPassword = companyWebUserPassword;
	}
	/**
	 * @return the companyWebUserPasswordConf
	 */
	public String getCompanyWebUserPasswordConf() {
		return companyWebUserPasswordConf;
	}
	/**
	 * @param companyWebUserPasswordConf the companyWebUserPasswordConf to set
	 */
	public void setCompanyWebUserPasswordConf(String companyWebUserPasswordConf) {
		this.companyWebUserPasswordConf = companyWebUserPasswordConf;
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
	 * @return the isTselAdmin
	 */
	public boolean isTselAdmin() {
		return tselAdmin;
	}

	/**
	 * @param isTselAdmin the isTselAdmin to set
	 */
	public void setTselAdmin(boolean tselAdmin) {
		this.tselAdmin = tselAdmin;
	}
	


	/**
	 * @return the companies
	 */
	public List getCompanies() {
		return companies;
	}

	/**
	 * @param companies the companies to set
	 */
	public void setCompanies(List companies) {
		this.companies = companies;
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




	private String companyCreate = "2017050101";
	private String companySearch = "2017050102";
	private String companyEdit = "2017050103";
	private String companyDelete = "2017050104";
	private String companySwitchboard = "2017050105";
	private String companyView = "2017050107";
	private String companykey;
	private String companyname;
	private String contactperson;
	private String email;
	private String address;
	private String domainname;
	private String chargestring;
	private BigDecimal locked;
	private boolean boolLocked;
	private BigDecimal enableforcedonnet;
	private boolean boolForcedOnnet;
	private BigDecimal enablegrouphunting;
	private boolean boolEnableGroupHunting;
	private BigDecimal enableprivatecall;
	private boolean boolEnablePrivCall;
	private String companycli;
	private String usemaincliprefix;
	private BigDecimal clioption;
	private boolean boolCliOption;
	private BigDecimal enablevpnchargingforsms;
	private boolean boolEnablevpnchargingforsms;
	private String defaultincs;
	private String defaultincsdesc;
	private String bypassdefaultincs;
	private boolean boolBypassDefInCS;
	private String forcedincs;
	private String forcedincsdesc;
	private String bypassforcedincs;
	private boolean boolBypassForcedInCS;
	private String defaultoutcs;
	private String defaultoutcsdesc;
	private String bypassdefaultoutcs;
	private boolean boolBypassDefaultOutCS;
	private String forcedoutcs;
	private String forcedoutcsdesc;
	private String bypassforcedoutcs;
	private boolean boolBypassForcedOutCS;
	private List incomingProfiles;
	private List outgoingProfiles;
	private String companyWebUserName;
	private String companyWebUserPassword;
	private String companyWebUserPasswordConf;
	private List companies;
	private boolean tselAdmin;
	private boolean deleteAllowed;
	private boolean editAllowed;
	private int searchCnt;
	private Map<String, Object> session;
	private static final Logger logger = Logger.getLogger(CompanyAction.class);

	@Override
	public void prepare() throws Exception {
		MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
		outgoingProfiles = micsCommonSessionHandler.getMICSProfileList("", MICSConstant.MICS_SCREENING_TYPE_OUTGOING, MICSConstant.MICS_SCREENING_LEVEL_COMPANY);
		session.put(MICSConstant.MICS_SESSION_OUTGOINGLIST, outgoingProfiles);
		incomingProfiles = micsCommonSessionHandler.getMICSProfileList("", MICSConstant.MICS_SCREENING_TYPE_INCOMING, MICSConstant.MICS_SCREENING_LEVEL_COMPANY);
		session.put(MICSConstant.MICS_SESSION_OUTGOINGLIST, incomingProfiles);
		Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
		if (loginWebUser != null) {
			
//				if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
				if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
						loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
						loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
						loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
						loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
					tselAdmin = true;
				} else {
					tselAdmin = false;
				}
			
		} else {
			addActionError("User not logged in");
		}
		
		//Check if edit
		if (this.companykey != null && !this.companykey.trim().equals("")) {
			Company currCompany = micsCommonSessionHandler.getCompanyByKey(this.companykey);
			if (currCompany != null) {
				logger.debug("currCompany: " + currCompany);
				this.companykey = currCompany.getCompanykey();
				this.companyname = currCompany.getCompanyname();
				this.domainname = currCompany.getDomainname();
				this.address = currCompany.getAddress();
				this.chargestring = currCompany.getChargestring();
				this.contactperson = currCompany.getContactperson();
				this.email = currCompany.getEmail();
				BigDecimal bdTrue = new BigDecimal(1);
				if (currCompany.getEnableforcedonnet().equals(bdTrue)) {
					this.boolForcedOnnet = true;
				} else {
					this.boolForcedOnnet = false;
				}
				if (currCompany.getEnablegrouphunting().equals(bdTrue)) {
					this.boolEnableGroupHunting = true;
				} else {
					this.boolEnableGroupHunting = false;
				}
				if (currCompany.getLocked().equals(bdTrue)) {
					this.boolLocked = true;
				} else {
					this.boolLocked = false;
				}
				if (currCompany.getEnableprivatecall().equals(bdTrue)) {
					this.boolEnablePrivCall = true;
				} else {
					this.boolEnablePrivCall = false;
				}
				if (currCompany.getEnablevpnchargingforsms().equals(bdTrue)) {
					this.boolEnablevpnchargingforsms = true;
				} else {
					this.boolEnablevpnchargingforsms = false;
				}
				bdTrue = null;
				this.companycli = currCompany.getCompanycli();
				this.usemaincliprefix = currCompany.getUsemaincliprefix();
				this.clioption = currCompany.getClioption();
				if (currCompany.getForcedoutcs() != null && !currCompany.getForcedoutcs().equals("")) {
					this.forcedoutcs = currCompany.getForcedoutcs();
					this.forcedoutcsdesc = currCompany.getForcedoutcsdesc();
					if (currCompany.getBypassforcedoutcs().equals("1")) {
						this.boolBypassForcedOutCS = true;
					} else {
						this.boolBypassForcedOutCS = false;

					}
				}
				if (currCompany.getDefaultoutcs() != null && !currCompany.getDefaultoutcs().equals("")) {
					this.defaultoutcs = currCompany.getDefaultoutcs();
					this.defaultoutcsdesc = currCompany.getDefaultoutcsdesc();
					if (currCompany.getBypassdefaultoutcs().equals("1")) {
						this.boolBypassDefaultOutCS = true;
					} else {
						this.boolBypassDefaultOutCS = false;
					}
				}
				if ( currCompany.getForcedincs() != null &&  !currCompany.getForcedincs().equals("")) {
					this.forcedincs = currCompany.getForcedincs();
					this.forcedincsdesc = currCompany.getForcedincsdesc();
					if (currCompany.getBypassforcedincs().equals("1")) {
						this.boolBypassForcedInCS = true;
					} else {
						this.boolBypassForcedInCS = false;
					}
				}
				if (currCompany.getDefaultincs() != null && !currCompany.getDefaultincs().equals("")) {
					this.defaultincs = currCompany.getDefaultincs();
					this.defaultincsdesc = currCompany.getDefaultincsdesc();
					if (currCompany.getBypassdefaultincs().equals("1")) {
						this.boolBypassDefInCS = true;
					} else {
						this.boolBypassDefInCS = false;
					}
				}
				//End Edit Company
			}
		}
	}
}
