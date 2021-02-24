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
import com.nsn.telkomsel.mics.orahbm.Company;
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
 * EmployeeAction.java
 * 
 * @author mulia
 * @version $Id: EmployeeAction.java,v 1.1.4.3 2019/03/22 10:05:52 cvsuser Exp $
 */
public class EmployeeAction extends ActionSupport implements SessionAware,Preparable {

	
	private static final long serialVersionUID = -1172046443729368066L;
	
	public String initCreate(){
		logger.info("initCreate()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			//this.prepare();
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(employeeCreate, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
						this.companyList = micsCommonSessionHandler.getCompanyList();
						session.put(MICSConstant.MICS_SESSION_COMPANYLIST, companyList);
						logger.debug("companyList: " + companyList);
						Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
						if(currCompany == null) throw new Exception ("Currently no company selected! please select company first.");
						this.companykey = currCompany.getCompanykey();
					} else {
						this.tselAdmin = false;
						this.companykey = loginWebUser.getCompanykey();
					}
					logger.debug("tselAdmin: " + tselAdmin);
					this.usertype = new BigDecimal(1);
					this.subgroupid = new BigDecimal(1);
					this.usergroupid = new BigDecimal(1);
					groups = micsCommonSessionHandler.getGroupsByCompany(companykey);
					session.put(MICSConstant.MICS_SESSION_GROUPS, groups);
					userTypes = micsCommonSessionHandler.getUserTypes();
					session.put(MICSConstant.MICS_SESSION_USERTYPES, userTypes);
					huntingoptionList = micsCommonSessionHandler.getUserHuntingOptions();
					session.put(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST, huntingoptionList);
					numberTypes = micsCommonSessionHandler.getNumberTypes();
					session.put(MICSConstant.MICS_SESSION_NUMBERTYPES, numberTypes);
					languages = micsCommonSessionHandler.getLanguages();
					session.put(MICSConstant.MICS_SESSION_LANGUAGES, languages);
					outgoingProfiles = micsCommonSessionHandler.getMICSProfileList(loginWebUser.getCompanykey(), MICSConstant.MICS_SCREENING_TYPE_OUTGOING, MICSConstant.MICS_SCREENING_LEVEL_USER);
					session.put(MICSConstant.MICS_SESSION_OUTGOINGLIST, outgoingProfiles);
					incomingProfiles = micsCommonSessionHandler.getMICSProfileList(loginWebUser.getCompanykey(), MICSConstant.MICS_SCREENING_TYPE_INCOMING, MICSConstant.MICS_SCREENING_LEVEL_USER);
					session.put(MICSConstant.MICS_SESSION_INCOMINGLIST, incomingProfiles);
					//Make Sure the MEMBER REQ is not contain from another session
					session.remove(MICSConstant.MICS_SESSION_MEMBERREQ);
					this.micsUserNumberReq = null;
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
			logger.error("Failed to initialize Employee admin",e);
			addActionError("Failed to process your Employee administration request " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		logger.debug("result: " + result);
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
				if (micsCommonSessionHandler.isMenuAuthorized(employeeCreate, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
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
					} else {
						this.tselAdmin = false;
						this.companykey = loginWebUser.getCompanykey();
					}
					logger.debug("tselAdmin: " + tselAdmin);
					groups = (List) session.get(MICSConstant.MICS_SESSION_GROUPS);
					userTypes = (List) session.get(MICSConstant.MICS_SESSION_USERTYPES);
					numberTypes = (List)session.get(MICSConstant.MICS_SESSION_NUMBERTYPES);
					languages = (List) session.get(MICSConstant.MICS_SESSION_LANGUAGES );
					outgoingProfiles= (List) session.get(MICSConstant.MICS_SESSION_OUTGOINGLIST);
					incomingProfiles = (List) session.get(MICSConstant.MICS_SESSION_INCOMINGLIST);
					huntingoptionList = (List) session.get(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
					sdf.format(new Date());
					String awalKey = sdf.format(new Date());
					String micsUserAvailableKey = micsCommonSessionHandler.getAvailableKey(awalKey, "micsuserkey", "Micsuser");
					logger.error("micsUserAvailableKey: "+micsUserAvailableKey);
					Micsuser newUser = new Micsuser();
					newUser.setMicsfirstname(this.micsfirstname);
					newUser.setMicslastname(this.micslastname);
					//Address need to be added to DB
					newUser.setUsertype(this.usertype);
					newUser.setLanguage(this.language);
					newUser.setCompanykey(this.companykey);
					newUser.setMicsuserkey(micsUserAvailableKey);
					newUser.setGroupkey(this.groupkey);
					newUser.setSubgroupid(this.subgroupid);
					newUser.setUsergroupid(this.usergroupid);
					newUser.setVpngroupid(this.vpngroupid);
					newUser.setPin(this.pin);
					if (!this.incs.equals("")) {
						if (boolBypassInCS) {
							newUser.setBypassincs("1");
						} else {
							newUser.setBypassincs("0");
						}
						newUser.setIncs(this.incs);
						newUser.setIncsdesc(this.incsdesc);
					}
					if (!this.outcs.equals("")) {
						if (boolBypassOutCS) {
							newUser.setBypassoutcs("1");
						} else {
							newUser.setBypassoutcs("0");
						}
						newUser.setOutcs(this.outcs);
						newUser.setOutcsdesc(this.outcsdesc);
					}
					
					newUser.setHuntingoption(this.huntingoption);
					if (boolEnableAnnorRecording) {
						newUser.setEnableannorecording(new BigDecimal(1));
						if (this.pin == null || this.pin.trim().equals("")) {
							throw new MICSException("Announcement Recording enabled, PIN are mandatory!");
						} 
					} else {
						newUser.setEnableannorecording(new BigDecimal(0));
					}
					if (boolKeepHuntingOnBusy) {
						newUser.setKeephuntingonbusy(new BigDecimal(1));
					} else {
						newUser.setKeephuntingonbusy(new BigDecimal(0));
					}
					if (boolReverseCharging) {
						newUser.setReverseCharging(new BigDecimal(1));
					} else {
						newUser.setReverseCharging(new BigDecimal(0));
					}
					if (boolLocked) {
						newUser.setLocked(new BigDecimal(1));
					} else {
						newUser.setLocked(new BigDecimal(0));
					}
					if (boolMainNumberAcChargedParty) {
						newUser.setMainnumberaschargedparty(new BigDecimal(1));
					} else {
						newUser.setMainnumberaschargedparty(new BigDecimal(0));
					}
					if (boolMainNumberAcCli) {
						newUser.setMainnumberascli(new BigDecimal(1));
					} else {
						newUser.setMainnumberascli(new BigDecimal(0));
					}
					//TODO find duplicate private numbers
					//added by TN, 20140925
					
					boolean duplicateFound = false;
					if (micsUserNumberReq != null && micsUserNumberReq.size() > 0){
						String tempNo = micsUserNumberReq.get(0).getPrivateNumber();
						int idx = 0;
						for (Iterator iterMicsUserNumberReq = micsUserNumberReq.iterator();iterMicsUserNumberReq.hasNext();) {
							AddNumberRequest currMicsNumberReq = (AddNumberRequest) iterMicsUserNumberReq.next();
							logger.error("Number Public " + currMicsNumberReq.getPublicNumber() + " set as main " + currMicsNumberReq.isMainPublic() + " Private " + currMicsNumberReq.getPrivateNumber() + " set as main " + currMicsNumberReq.isMainPrivate());
							if (currMicsNumberReq.isMainPublic()) {
								newUser.setMainpublicnumber(currMicsNumberReq.getPublicNumber());
								newUser.setInitialmainpublicnumber(currMicsNumberReq.getPublicNumber());
							}
							if (currMicsNumberReq.isMainPrivate()) {
								newUser.setMainprivatenumber(currMicsNumberReq.getPrivateNumber());
								newUser.setInitialmainprivatenumber(currMicsNumberReq.getPrivateNumber());
							}
							if (idx > 0){
								if (tempNo.equals(currMicsNumberReq.getPrivateNumber())){
									duplicateFound = true;
								}
							}
							idx++;
						}
					}

					session.put(MICSConstant.MICS_SESSION_MEMBERREQ, micsUserNumberReq);
					//prepare();
					logger.error("userReq :"+micsUserNumberReq);	
					if (duplicateFound){
						Micslogger.log("Create","Employee", newUser.getMicsuserkey()+" "+ newUser.getMicslastname() +" not created", this.companykey, loginWebUser.getWebusername(),"ERROR");
						addActionMessage("Failed to create employee : " + newUser.getMicslastname()+"," + newUser.getMicsfirstname());
						result = MICSConstant.MICS_ERROR;

					}else{

						newUser.setUpdateuser(loginWebUser.getWebusername());
						newUser.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
						micsCommonSessionHandler.createEmployee(newUser, micsUserNumberReq, sipPassword);
						Micslogger.log("Create","Employee", newUser.getMicsuserkey()+" "+ newUser.getMicslastname() +" created", this.companykey, loginWebUser.getWebusername(),"SUCCESS");
						addActionMessage("Successfully create employee : " + newUser.getMicslastname()+"," + newUser.getMicsfirstname());
						result = MICSConstant.MICS_SUCCESS;
					}
				} else {
					addActionError("User not authorized");
					result = MICSConstant.MICS_ERROR_NOT_AUTH;
				}
			} else {
				addActionError("User not logged in");
				result = MICSConstant.MICS_ERROR_NOT_LOGGEDIN;
			}
		} catch (Exception e) {
			logger.error("Failed to create employee",e);
			addActionError("Failed to create employee " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
	}
	
	public String addNumber(){
		logger.debug("addNumber");
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
				if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)) {
					this.optRole = true;
				} else {
					this.optRole = false;
				}
				Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
				if(currCompany == null) throw new Exception ("Currently no company selected! please select company first.");
				this.companykey = currCompany.getCompanykey();
			} else {
				this.tselAdmin = false;
				this.companykey = loginWebUser.getCompanykey();
			}
			groups = (List) session.get(MICSConstant.MICS_SESSION_GROUPS);
			userTypes = (List) session.get(MICSConstant.MICS_SESSION_USERTYPES);
			numberTypes = (List)session.get(MICSConstant.MICS_SESSION_NUMBERTYPES);
			languages = (List) session.get(MICSConstant.MICS_SESSION_LANGUAGES );
			outgoingProfiles= (List) session.get(MICSConstant.MICS_SESSION_OUTGOINGLIST);
			incomingProfiles = (List) session.get(MICSConstant.MICS_SESSION_INCOMINGLIST);
			huntingoptionList = (List) session.get(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST);
			//Check if the public number and private number already been used or not
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			//Check if Public Number is empty
			if (this.newPublicNumber != null && !this.newPublicNumber.trim().equals("")) {
				//check with user first
				
				int userPublicNumber = micsCommonSessionHandler.checkUsePublicNumber(this.newPublicNumber.trim());
				if (userPublicNumber == 0) {
					//No User  use this public number
					//Check wether hunting group use this public number or not
					int hgPublicNumber = micsCommonSessionHandler.checkHGPublicNumber(this.newPublicNumber);
					if (hgPublicNumber == 0) {
						//No HG use this public number
						//If private number is set
						if (this.newPrivateNumber!= null && !this.newPrivateNumber.trim().equals("")) {
							//Check if the private number already use within the same company
							int userPrivateNumber = micsCommonSessionHandler.checkUsePrivateNumber(this.newPrivateNumber, this.companykey);
							if (userPrivateNumber == 0) {
								//No User within the same company use this private number
								//Check whether hunting group in the company use this private number
								int hgPrivateNumber = micsCommonSessionHandler.checkHGPrivateNumber(this.newPrivateNumber, this.companykey);
								if (hgPrivateNumber == 0) {
									//Save to add Number
									if (micsUserNumberReq == null) {
										micsUserNumberReq = new ArrayList<AddNumberRequest>();
										this.mainpublicnumber = this.newPublicNumber;
										this.mainprivatenumber = this.newPrivateNumber;
									}
									
									
									AddNumberRequest currAddNumberRequest = new AddNumberRequest();
									currAddNumberRequest.setPrivateNumber(this.newPrivateNumber);
									currAddNumberRequest.setPublicNumber(this.newPublicNumber);
									currAddNumberRequest.setImsi(this.newImsi);
									
									
									currAddNumberRequest.setOrderIndex(this.newOrderIndex);
									currAddNumberRequest.setRemove(false);
									if (this.newPrivateNumber.equals(this.mainprivatenumber)) {
										currAddNumberRequest.setMainPrivate(true);
									} else {
										currAddNumberRequest.setMainPrivate(false);
									}
									
									if (this.newPublicNumber.equals(this.mainpublicnumber)) {
										currAddNumberRequest.setMainPublic(true);
									} else {
										currAddNumberRequest.setMainPublic(false);
									}
									currAddNumberRequest.setNumberType(this.newNumberType);
									currAddNumberRequest.setSipuri(this.newSipuri);
									//Add Checking since sip uri can be blank if number type is sip set default sip uri first with private number and company domain
									//If private no is empty using public number and company domain
									logger.debug("Checking SIPURI for SIP Number Type " + this.newNumberType);
									if (this.newNumberType == MICSConstant.MICS_NUMBER_TYPE_SIP) {
										logger.debug("SIP Number added");
										if (this.newSipuri == null || this.newSipuri.trim().equals("")) {
											//Get Domain from Company
											Company domainCpy = micsCommonSessionHandler.getCompanyByKey(this.companykey);
											logger.debug("domainCpy: "+domainCpy);
											String cpyDomainName ="";
											if (domainCpy != null) {
												cpyDomainName = domainCpy.getDomainname();
											} 											
											logger.debug("cpyDomainName: " + cpyDomainName);
											if (this.newPrivateNumber != null && !this.newPrivateNumber.trim().equals("")) {
												currAddNumberRequest.setSipuri(this.newPrivateNumber+"@"+cpyDomainName);
											} else {
												currAddNumberRequest.setSipuri(this.newPublicNumber+"@"+cpyDomainName);
											}
										} else {
											logger.debug("Use SIPURI entry by user " + this.newSipuri);
										}
										
									} else {
										logger.debug("Not SIP Number, SIPURI not mandatory");
									}
									logger.debug("SIP Uri: " + currAddNumberRequest.getSipuri());
									micsUserNumberReq.add(currAddNumberRequest);
									session.put(MICSConstant.MICS_SESSION_MEMBERREQ, micsUserNumberReq);
									logger.debug("New number " + currAddNumberRequest.getPublicNumber() + " - " + currAddNumberRequest.getPrivateNumber() + " = "+ currAddNumberRequest.getSipuri()+" added" );
								} else {
									addActionError("Sorry " + this.newPrivateNumber + " private number is used by Huntinggroup");
								}
							} else {
								addActionError("Sorry " + this.newPrivateNumber + " private number is used by other employee");
							}
						} else {
							//No Need to check private number
							//Save to add Number
							if (micsUserNumberReq == null) {
								micsUserNumberReq = new ArrayList<AddNumberRequest>();
								this.mainpublicnumber = this.newPublicNumber;
								this.mainprivatenumber = this.newPrivateNumber;
							}
							
							
							AddNumberRequest currAddNumberRequest = new AddNumberRequest();
							currAddNumberRequest.setPrivateNumber(this.newPrivateNumber);
							currAddNumberRequest.setPublicNumber(this.newPublicNumber);
							currAddNumberRequest.setImsi(this.newImsi);
							currAddNumberRequest.setSipuri(this.newSipuri);
							currAddNumberRequest.setOrderIndex(this.newOrderIndex);
							currAddNumberRequest.setRemove(false);
							if (this.newPrivateNumber.equals(this.mainprivatenumber)) {
								currAddNumberRequest.setMainPrivate(true);
							} else {
								currAddNumberRequest.setMainPrivate(false);
							}
							
							if (this.newPublicNumber.equals(this.mainpublicnumber)) {
								currAddNumberRequest.setMainPublic(true);
							} else {
								currAddNumberRequest.setMainPublic(false);
							}
							currAddNumberRequest.setNumberType(this.newNumberType);
							logger.debug("Checking SIPURI for SIP Number Type " + this.newNumberType);
							if (this.newNumberType == MICSConstant.MICS_NUMBER_TYPE_SIP) {
								logger.debug("SIP Number added");
								if (this.newSipuri == null || this.newSipuri.trim().equals("")) {
									//Get Domain from Company
									Company domainCpy = micsCommonSessionHandler.getCompanyByKey(this.companykey);
									logger.debug("domainCpy: "+domainCpy);
									String cpyDomainName ="";
									if (domainCpy != null) {
										cpyDomainName = domainCpy.getDomainname();
									} 											
									logger.debug("cpyDomainName: " + cpyDomainName);
									if (this.newPrivateNumber != null && !this.newPrivateNumber.trim().equals("")) {
										currAddNumberRequest.setSipuri(this.newPrivateNumber+"@"+cpyDomainName);
									} else {
										currAddNumberRequest.setSipuri(this.newPublicNumber+"@"+cpyDomainName);
									}
								} else {
									logger.debug("Use SIPURI entry by user " + this.newSipuri);
								}
								
							} else {
								logger.debug("Not SIP Number, SIPURI not mandatory");
							}
							micsUserNumberReq.add(currAddNumberRequest);
							session.put(MICSConstant.MICS_SESSION_MEMBERREQ, micsUserNumberReq);
							logger.debug("New number " + currAddNumberRequest.getPublicNumber() + " - " + currAddNumberRequest.getPrivateNumber() +" - " + currAddNumberRequest.getSipuri()+" added" );
						}
					} else {
						addActionError("Sorry " + this.newPublicNumber + " public number is used by Huntinggroup");
					}
				} else {
					addActionError("Sorry " + this.newPublicNumber + " public number is used by other employee");
				}
			} else {
				addActionError("Sorry  can not add empty public number");
			}
			
			result = MICSConstant.MICS_SUCCESS;
		} catch (Exception e) {
			logger.error("Failed to add MICS Employee Number",e);
			addActionError("Failed to add MICS Employee Number " + e.getMessage());
			result = MICSConstant.MICS_SUCCESS;
		}
		return result;
	}
	
	public String delNumber(){
		logger.debug("delNumber");
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
				if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)) {
					this.optRole = true;
				} else {
					this.optRole = false;
				}
				Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
				if(currCompany == null) throw new Exception ("Currently no company selected! please select company first.");
				this.companykey = currCompany.getCompanykey();
			} else {
				this.tselAdmin = false;
				this.companykey = loginWebUser.getCompanykey();
			}
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			this.groups = (List) session.get(MICSConstant.MICS_SESSION_GROUPS);
			if(this.groups == null ) groups = micsCommonSessionHandler.getGroupsByCompany(this.companykey);
			userTypes = (List) session.get(MICSConstant.MICS_SESSION_USERTYPES);
			numberTypes = (List)session.get(MICSConstant.MICS_SESSION_NUMBERTYPES);
			languages = (List) session.get(MICSConstant.MICS_SESSION_LANGUAGES );
			outgoingProfiles= (List) session.get(MICSConstant.MICS_SESSION_OUTGOINGLIST);
			incomingProfiles = (List) session.get(MICSConstant.MICS_SESSION_INCOMINGLIST);
			huntingoptionList = (List) session.get(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST);
			
			List tempMicsUserNumberReq = new ArrayList<AddNumberRequest>();
			logger.debug("micsUserNumberReq: " + micsUserNumberReq);
			for (Iterator iterMicsUserNumberReq = micsUserNumberReq.iterator();iterMicsUserNumberReq.hasNext();) {
					AddNumberRequest currMicsNumberReq = (AddNumberRequest) iterMicsUserNumberReq.next();
					logger.debug("Number " + currMicsNumberReq.getPublicNumber() + " - " + currMicsNumberReq.getPrivateNumber() + " remove " + currMicsNumberReq.isRemove());
					
					if (currMicsNumberReq.isRemove()) {
						if (currMicsNumberReq.isMainPublic()) {
							//Can not delete Number that set as main public, deselect first
							logger.debug("Can not delete, Number added");
							addActionError("Can not delete number that set as main public, deselect first before try to delete ");
							tempMicsUserNumberReq.add(currMicsNumberReq);
							if (currMicsNumberReq.isMainPublic()) {
								this.mainpublicnumber = currMicsNumberReq.getPublicNumber();
							}
							if (currMicsNumberReq.isMainPrivate()) {
								this.mainprivatenumber = currMicsNumberReq.getPrivateNumber();
							}
						} else {
							logger.debug("Number removed");
							//Remove Number don't add to the list
						}
					} else {
						logger.debug("Number added");
						tempMicsUserNumberReq.add(currMicsNumberReq);
						if (currMicsNumberReq.isMainPublic()) {
							this.mainpublicnumber = currMicsNumberReq.getPublicNumber();
						}
						if (currMicsNumberReq.isMainPrivate()) {
							this.mainprivatenumber = currMicsNumberReq.getPrivateNumber();
						}
					}
			}
			//Check at least one number still exist
			if (tempMicsUserNumberReq.size() == 0) {
				//No number left after deletion dont apply changes
				addActionError("Can not delete number, Employee must have at least one number");
				session.put(MICSConstant.MICS_SESSION_MEMBERREQ, micsUserNumberReq);
			} else {
				micsUserNumberReq = tempMicsUserNumberReq;
				session.put(MICSConstant.MICS_SESSION_MEMBERREQ, micsUserNumberReq);
			}
			result = MICSConstant.MICS_SUCCESS;
		} catch (Exception e) {
			logger.error("Failed to del MICS Employee Number",e);
			addActionError("Failed to del MICS Employee Number " + e.getMessage());
			result = MICSConstant.MICS_SUCCESS;
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
				if (micsCommonSessionHandler.isMenuAuthorized(employeeSearch, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
						Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
						logger.debug("companykey --> " + companykey);
						
						if (currCompany == null) {
							logger.debug("No Curr Company");
							companykey = "";
						} else {
							logger.debug("Curr Company Key --> " + currCompany.getCompanykey());
							if (companykey == null) {
								this.companykey = currCompany.getCompanykey();
							} else {
								if (companykey.trim().equals("")) {
									this.companykey = "";
								} else {
									this.companykey = companykey;
								}
							}
						}
					} else {
						this.tselAdmin = false;
						this.companykey = loginWebUser.getCompanykey();
					}
					logger.debug("tselAdmin: " + tselAdmin);
					
					List dbEmployeeNumber = null;
					
					if (searchCnt > 0) {
						dbEmployeeNumber = micsCommonSessionHandler.searchEmployees(micsfirstname, micslastname, mainpublicnumber, mainprivatenumber, companykey);
					}
					searchCnt++;
					if (dbEmployeeNumber != null && dbEmployeeNumber.size()>0) {
						employeeList = new ArrayList<AddUserRequest>();
						for (Iterator iterDbEmployeeNumber = dbEmployeeNumber.iterator(); iterDbEmployeeNumber
								.hasNext();) {
							Object[] currEmployeeNumber = (Object[]) iterDbEmployeeNumber.next();
							Micsuser currMicsUser = (Micsuser) currEmployeeNumber[0];
							Micsusernumber currMicsUserNumber = (Micsusernumber) currEmployeeNumber[1];
							AddUserRequest currAddUserRequest = new AddUserRequest();
							currAddUserRequest.setMicsUserKey(currMicsUser.getMicsuserkey());
							currAddUserRequest.setMicsCompanyKey(currMicsUser.getCompanykey());
							currAddUserRequest.setMicsFirstName(currMicsUser.getMicsfirstname());
							currAddUserRequest.setMicsLastName(currMicsUser.getMicslastname());
							currAddUserRequest.setMicsPubNumber(currMicsUserNumber.getPublicnumber());
							currAddUserRequest.setMicsPrivNumber(currMicsUserNumber.getPrivatenumber());
							employeeList.add(currAddUserRequest);
						}
					} else {
						employeeList = null;
					}
					if (micsCommonSessionHandler.isMenuAuthorized(employeeDelete, loginWebUser.getRolekey())) {
						this.deleteAllowed = true;
					} else {
						this.deleteAllowed = false;
					}
					if (micsCommonSessionHandler.isMenuAuthorized(employeeEdit, loginWebUser.getRolekey())) {
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
			logger.error("Failed to search employee",e);
			addActionError("Failed to search employee " + e.getMessage());
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
				if (micsCommonSessionHandler.isMenuAuthorized(employeeEdit, loginWebUser.getRolekey())) {
					Micsuser currMicsuser = micsCommonSessionHandler.getEmployeeByKey(micsuserkey);
					if (currMicsuser == null) {
						throw new MICSException("Employee not found! no employee with key " + micsuserkey);
					}
					//Authorize do some stuff here
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
						if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)) {
							this.optRole = true;
						} else {
							this.optRole = false;
						}
						Company currCompany = micsCommonSessionHandler.getCompanyByKey(currMicsuser.getCompanykey());
						
						if (currCompany == null) {
							throw new Exception("Currently no company selected! Please select company first.");
						}
						session.put(MICSConstant.MICS_SESSION_CURRCOMPANY,currCompany);
						this.companykey = currCompany.getCompanykey();
					} else {
						this.tselAdmin = false;
						this.companykey = loginWebUser.getCompanykey();
					}
					logger.debug("tselAdmin: " + tselAdmin);
					logger.debug("micsuserkey: "+ micsuserkey);
					if (micsuserkey == null) {
						throw new MICSException("Employee Key is empty, can not found employee to edit ");
					}
					
					this.groups = micsCommonSessionHandler.getGroupsByCompany(this.companykey);
					session.put(MICSConstant.MICS_SESSION_GROUPS, groups);
					userTypes = micsCommonSessionHandler.getUserTypes();
					session.put(MICSConstant.MICS_SESSION_USERTYPES, userTypes);
					numberTypes = micsCommonSessionHandler.getNumberTypes();
					session.put(MICSConstant.MICS_SESSION_NUMBERTYPES, numberTypes);
					languages = micsCommonSessionHandler.getLanguages();
					session.put(MICSConstant.MICS_SESSION_LANGUAGES, languages);
					huntingoptionList = micsCommonSessionHandler.getUserHuntingOptions();
					session.put(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST, huntingoptionList);
					outgoingProfiles = micsCommonSessionHandler.getMICSProfileList(loginWebUser.getCompanykey(), MICSConstant.MICS_SCREENING_TYPE_OUTGOING, MICSConstant.MICS_SCREENING_LEVEL_USER);
					session.put(MICSConstant.MICS_SESSION_OUTGOINGLIST, outgoingProfiles);
					incomingProfiles = micsCommonSessionHandler.getMICSProfileList(loginWebUser.getCompanykey(), MICSConstant.MICS_SCREENING_TYPE_INCOMING, MICSConstant.MICS_SCREENING_LEVEL_USER);
					session.put(MICSConstant.MICS_SESSION_INCOMINGLIST, incomingProfiles);
					
					this.micsuserkey = currMicsuser.getMicsuserkey();
					this.micsfirstname = currMicsuser.getMicsfirstname();
					this.micslastname = currMicsuser.getMicslastname();
					this.usertype = currMicsuser.getUsertype();
					this.mainpublicnumber = currMicsuser.getMainpublicnumber();
					this.mainprivatenumber = currMicsuser.getMainprivatenumber();
					this.groupkey = currMicsuser.getGroupkey();
					BigDecimal bdUserGroupId = currMicsuser.getUsergroupid();
					if (bdUserGroupId != null) {
						this.usergroupid = currMicsuser.getUsergroupid();
					}
					
					this.huntingoption = currMicsuser.getHuntingoption();
					BigDecimal bdSubGroupId = currMicsuser.getSubgroupid();
					if (bdSubGroupId != null) {
						this.subgroupid = currMicsuser.getSubgroupid();
					}
					BigDecimal bdVpnGroupId = currMicsuser.getVpngroupid();
					if (bdVpnGroupId != null) {
						this.vpngroupid = currMicsuser.getVpngroupid();
					}
					
					this.pin = currMicsuser.getPin();
					this.pinConfirmation = currMicsuser.getPin();
					this.language = currMicsuser.getLanguage();
					BigDecimal bdAnnoRecord = currMicsuser.getEnableannorecording();
					if (bdAnnoRecord != null) {
						if (bdAnnoRecord.intValue() == 1) {
							this.boolEnableAnnorRecording = true;
							
						} else {
							this.boolEnableAnnorRecording = false;
						}
					} else {
						this.boolEnableAnnorRecording = false;
					}
					
					BigDecimal bdKeepHunting = currMicsuser.getKeephuntingonbusy();
					if (bdKeepHunting != null) {
						if (bdKeepHunting.intValue() == 1) {
							this.boolKeepHuntingOnBusy = true;
						} else {
							this.boolKeepHuntingOnBusy = false;
						}
					} else {
						this.boolKeepHuntingOnBusy = false;
					}
					BigDecimal bdReverseCharging = currMicsuser.getReverseCharging();
					if (bdReverseCharging != null) {
						if (bdReverseCharging.intValue() == 1) {
							this.boolReverseCharging = true;
						} else {
							this.boolReverseCharging = false;
						}
					} else {
						this.boolReverseCharging = false;
					}
					BigDecimal bdGetLocked = currMicsuser.getLocked();
					if (bdGetLocked != null) {
						if (bdGetLocked.intValue() == 1) {
							this.boolLocked = true;
						} else {
							this.boolLocked = false;
						}
					} else {
						this.boolLocked = false;
					}
					
					BigDecimal bdMainNumberAsCP = currMicsuser.getMainnumberaschargedparty();
					if (bdMainNumberAsCP != null) {
						if (currMicsuser.getMainnumberaschargedparty().intValue() == 1) {
							this.boolMainNumberAcChargedParty = true;
						} else {
							this.boolMainNumberAcChargedParty = false;
						}
					} else {
						this.boolMainNumberAcChargedParty = false;
					}
					
					BigDecimal bdMainNumberAsCLI = currMicsuser.getMainnumberascli();
					if (bdMainNumberAsCLI != null) {
						if (currMicsuser.getMainnumberascli().intValue() == 1) {
							this.boolMainNumberAcCli = true;
						} else {
							this.boolMainNumberAcCli = false;
						}
					} else {
						this.boolMainNumberAcCli = false;
					}
					
					if (currMicsuser.getOutcs()!= null && !currMicsuser.getOutcs().equals("")) {
						this.outcs = currMicsuser.getOutcs();
						this.outcsdesc = currMicsuser.getOutcsdesc();
						String strBypassoutcs = currMicsuser.getBypassoutcs();
						if (strBypassoutcs == null) {
							strBypassoutcs = "";
						}
						if (strBypassoutcs.equals("1")) {
							this.boolBypassOutCS = true;
						} else {
							this.boolBypassOutCS = false;
						}
					}
					if (currMicsuser.getIncs() != null && !currMicsuser.getIncs().equals("")) {
						this.incs = currMicsuser.getIncs();
						this.incsdesc = currMicsuser.getIncsdesc();
						String strBypassincs = currMicsuser.getBypassincs();
						if (strBypassincs == null) {
							strBypassincs = "";
						}
						if (strBypassincs.equals("1")) {
							this.boolBypassInCS = true;
						} else {
							this.boolBypassInCS = false;
						}
					}
					//Get user Numbers
					List oldNumber = micsCommonSessionHandler.getNumberByEmployeeKey(currMicsuser.getMicsuserkey());
					micsUserNumberReq = new ArrayList<AddNumberRequest>();
					for (Iterator iterOldNumber = oldNumber.iterator(); iterOldNumber.hasNext();) {
						Micsusernumber currOldNumber = (Micsusernumber) iterOldNumber.next();
						AddNumberRequest currNumberRequest = new AddNumberRequest();
						currNumberRequest.setPublicNumber(currOldNumber.getPublicnumber());
						currNumberRequest.setPrivateNumber(currOldNumber.getPrivatenumber());
						BigDecimal bdNumberType = currOldNumber.getNumbertype();
						if (bdNumberType == null) {
							bdNumberType = new BigDecimal(0);
						}
						currNumberRequest.setNumberType(bdNumberType.intValue());
						currNumberRequest.setImsi(currOldNumber.getImsi());
						currNumberRequest.setSipuri(currOldNumber.getSipuri());
						BigDecimal bdOrderIndex = currOldNumber.getOrderindex();
						if (bdOrderIndex == null) {
							bdOrderIndex = new BigDecimal(0);
						}
						currNumberRequest.setOrderIndex(bdOrderIndex.intValue());
						if(currOldNumber.getPublicnumber().equals(mainpublicnumber)) currNumberRequest.setMainPublic(true);
						if(currOldNumber.getPrivatenumber().equals(mainprivatenumber)) currNumberRequest.setMainPrivate(true);
						if (currOldNumber.getProv_req_id() != null) {
							currNumberRequest.setProv_req_id(currOldNumber.getProv_req_id().intValue());
						}
						if (currOldNumber.getProv_req_status() != null) {
							currNumberRequest.setProv_req_status(currOldNumber.getProv_req_status().intValue());
						}
						micsUserNumberReq.add(currNumberRequest);
					}
					session.put(MICSConstant.MICS_SESSION_MEMBERREQ, micsUserNumberReq);
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
			logger.error("Failed to edit employee",e);
			addActionError("Failed to edit employee " + e.getMessage());
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
				if (micsCommonSessionHandler.isMenuAuthorized(employeeEdit, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
						if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)) {
							this.optRole = true;
						} else {
							this.optRole = false;
						}
						companyList = (List) session.get(MICSConstant.MICS_SESSION_COMPANYLIST);
						logger.debug("companyList: " + companyList);
					} else {
						this.tselAdmin = false;
						this.companykey = loginWebUser.getCompanykey();
					}
					logger.debug("tselAdmin: " + tselAdmin);
					this.groups = (List) session.get(MICSConstant.MICS_SESSION_GROUPS);
					
					if(this.groups == null ) groups = micsCommonSessionHandler.getGroupsByCompany(this.companykey);
					userTypes = (List) session.get(MICSConstant.MICS_SESSION_USERTYPES);
					numberTypes = (List)session.get(MICSConstant.MICS_SESSION_NUMBERTYPES);
					languages = (List) session.get(MICSConstant.MICS_SESSION_LANGUAGES );
					outgoingProfiles= (List) session.get(MICSConstant.MICS_SESSION_OUTGOINGLIST);
					incomingProfiles = (List) session.get(MICSConstant.MICS_SESSION_INCOMINGLIST);
					huntingoptionList = (List) session.get(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST);
//					Micsuser currUser = micsCommonSessionHandler.getEmployeeByKey(micsuserkey);
//					logger.debug("currUser: "+currUser);
					Micsuser currMicsuser = micsCommonSessionHandler.getEmployeeByKey(micsuserkey);
					if (currMicsuser == null) {
						throw new MICSException("Employee not found! no employee with key " + micsuserkey);
					}
					currMicsuser.setMicsuserkey(micsuserkey);
					currMicsuser.setMicsfirstname(this.micsfirstname);
					currMicsuser.setMicslastname(this.micslastname);
					//Address need to be added to DB
					currMicsuser.setUsertype(this.usertype);
					currMicsuser.setLanguage(this.language);
					currMicsuser.setCompanykey(this.companykey);
					currMicsuser.setGroupkey(this.groupkey);
					currMicsuser.setSubgroupid(this.subgroupid);
					currMicsuser.setUsergroupid(this.usergroupid);
					currMicsuser.setVpngroupid(this.vpngroupid);
					
					//If pin is set change pin
					if (this.pin != null && !this.pin.trim().equals("")) {
						if (this.pin.equals(this.pinConfirmation)) {
							currMicsuser.setPin(this.pin);
						} else {
							addActionError("Pin and Pin confirmation doesn't match Pin not set! others info are set");
						}
					}
					if (!this.incs.equals("")) {
						if (boolBypassInCS) {
							currMicsuser.setBypassincs("1");
						} else {
							currMicsuser.setBypassincs("0");
						}
						currMicsuser.setIncs(this.incs);
						currMicsuser.setIncsdesc(this.incsdesc);
					} else {
						currMicsuser.setIncs("");
						currMicsuser.setIncsdesc("");
						currMicsuser.setBypassincs("0");
					}
					if (!this.outcs.equals("")) {
						if (boolBypassOutCS) {
							currMicsuser.setBypassoutcs("1");
						} else {
							currMicsuser.setBypassoutcs("0");
						}
						currMicsuser.setOutcs(this.outcs);
						currMicsuser.setOutcsdesc(this.outcsdesc);
					} else {
						currMicsuser.setOutcs("");
						currMicsuser.setOutcsdesc("");
						currMicsuser.setBypassoutcs("0");
					}
					
					currMicsuser.setHuntingoption(this.huntingoption);
					
					if (boolEnableAnnorRecording) {
						currMicsuser.setEnableannorecording(new BigDecimal(1));
						if (this.pin == null || this.pin.trim().equals("")) {
							throw new MICSException("Announcement Recording enabled, PIN are mandatory!");
						} 
					} else {
						currMicsuser.setEnableannorecording(new BigDecimal(0));
					}
					if (boolKeepHuntingOnBusy) {
						currMicsuser.setKeephuntingonbusy(new BigDecimal(1));
					} else {
						currMicsuser.setKeephuntingonbusy(new BigDecimal(0));
					}
					if (boolReverseCharging) {
						currMicsuser.setReverseCharging(new BigDecimal(1));
					} else {
						currMicsuser.setReverseCharging(new BigDecimal(0));
					}
					if (boolLocked) {
						currMicsuser.setLocked(new BigDecimal(1));
					} else {
						currMicsuser.setLocked(new BigDecimal(0));
					}
					if (boolMainNumberAcChargedParty) {
						currMicsuser.setMainnumberaschargedparty(new BigDecimal(1));
					} else {
						currMicsuser.setMainnumberaschargedparty(new BigDecimal(0));
					}
					if (boolMainNumberAcCli) {
						currMicsuser.setMainnumberascli(new BigDecimal(1));
					} else {
						currMicsuser.setMainnumberascli(new BigDecimal(0));
					}
					/*
					for (Iterator iterMicsUserNumberReq = micsUserNumberReq.iterator();iterMicsUserNumberReq.hasNext();) {
						AddNumberRequest currMicsNumberReq = (AddNumberRequest) iterMicsUserNumberReq.next();
						logger.debug("Number Public " + currMicsNumberReq.getPublicNumber() + " set as main " + currMicsNumberReq.isMainPublic() + " Private " + currMicsNumberReq.getPrivateNumber() + " set as main " + currMicsNumberReq.isMainPrivate());
						if (currMicsNumberReq.isMainPublic()) {
							currMicsuser.setMainpublicnumber(currMicsNumberReq.getPublicNumber());
						}
						if (currMicsNumberReq.isMainPrivate()) {
							currMicsuser.setMainprivatenumber(currMicsNumberReq.getPrivateNumber());
						}
					}
					currMicsuser.setUpdateuser(loginWebUser.getWebusername());
					currMicsuser.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
					List oldNumber = micsCommonSessionHandler.getNumberByEmployeeKey(micsuserkey);
					micsCommonSessionHandler.editEmployee(currMicsuser, oldNumber, micsUserNumberReq,sipPassword);
					Micslogger.log("Edit","Employee", currMicsuser.getMicsuserkey()+" "+ currMicsuser.getMicslastname() +" edited", this.companykey, loginWebUser.getWebusername(),"SUCCESS");
					addActionMessage("Successfully edit employee : " + currMicsuser.getMicslastname()+"," + currMicsuser.getMicsfirstname());
					result = MICSConstant.MICS_SUCCESS;
					*/

					//TODO find duplicate private numbers
					//added by TN, 20140925
					boolean duplicateWithinListFound = false;
					String privateNoInUse = null;
					String tempNo = "";
					if (micsUserNumberReq != null && micsUserNumberReq.size() > 0){
						tempNo = micsUserNumberReq.get(0).getPrivateNumber();
						if (tempNo == null) tempNo = "";
						int idx = 0;
						for (Iterator iterMicsUserNumberReq = micsUserNumberReq.iterator();iterMicsUserNumberReq.hasNext();) {
							AddNumberRequest currMicsNumberReq = (AddNumberRequest) iterMicsUserNumberReq.next();
							logger.debug("Number Public " + currMicsNumberReq.getPublicNumber() + " set as main " + currMicsNumberReq.isMainPublic() + " Private " + currMicsNumberReq.getPrivateNumber() + " set as main " + currMicsNumberReq.isMainPrivate());
							if (currMicsNumberReq.isMainPublic()) {
								currMicsuser.setMainpublicnumber(currMicsNumberReq.getPublicNumber());
								currMicsuser.setInitialmainpublicnumber(currMicsNumberReq.getPublicNumber());
							}
							if (currMicsNumberReq.isMainPrivate()) {
								currMicsuser.setMainprivatenumber(currMicsNumberReq.getPrivateNumber());
								currMicsuser.setInitialmainprivatenumber(currMicsNumberReq.getPrivateNumber());
							}
							if (idx > 0){
								if (currMicsNumberReq.getPrivateNumber() != null 
										&& !currMicsNumberReq.equals("") 
										&& tempNo.equals(currMicsNumberReq.getPrivateNumber())){
									duplicateWithinListFound = true;
								}
							}
							idx++;

							//check if private number has already been used by other persons
							int privateNoInUseCount = micsCommonSessionHandler.countOtherUserOfPrivateNumber(currMicsNumberReq.getPrivateNumber(),
									currMicsuser.getMicsuserkey(), this.companykey);
							if (privateNoInUseCount > 0){
								privateNoInUse = currMicsNumberReq.getPrivateNumber();
							}
						}
					}

					if (duplicateWithinListFound){
						Micslogger.log("Edit","Employee", currMicsuser.getMicsuserkey()+" "+ currMicsuser.getMicslastname() +" not updated", this.companykey, loginWebUser.getWebusername(),"ERROR");
						addActionError("Failed to edit employee, duplicate private number " + tempNo + ". Please use a different private number.");
						result = MICSConstant.MICS_ERROR;

					}else{
						
						if (privateNoInUse != null){
							addActionError("Sorry private number " + privateNoInUse + " is used by other employee");
							result = MICSConstant.MICS_ERROR;
							
						}else{
							currMicsuser.setUpdateuser(loginWebUser.getWebusername());
							currMicsuser.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
							List oldNumber = micsCommonSessionHandler.getNumberByEmployeeKey(micsuserkey);
							micsCommonSessionHandler.editEmployee(currMicsuser, oldNumber, micsUserNumberReq,sipPassword);
							Micslogger.log("Edit","Employee", currMicsuser.getMicsuserkey()+" "+ currMicsuser.getMicslastname() +" edited", this.companykey, loginWebUser.getWebusername(),"SUCCESS");
							addActionMessage("Successfully edit employee : " + currMicsuser.getMicslastname()+"," + currMicsuser.getMicsfirstname());
							result = MICSConstant.MICS_SUCCESS;
						}
					}
				} else {
					addActionError("User not authorized");
					result = MICSConstant.MICS_ERROR_NOT_AUTH;
				}
			} else {
				addActionError("User not logged in");
				result = MICSConstant.MICS_ERROR_NOT_LOGGEDIN;
			}
		} catch (Exception e) {
			logger.error("Failed to edit employee",e);
			addActionError("Failed to edit employee " + e.getMessage());
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
				if (micsCommonSessionHandler.isMenuAuthorized(employeeView, loginWebUser.getRolekey())) {
					Micsuser currMicsuser = micsCommonSessionHandler.getEmployeeByKey(micsuserkey);
					if (currMicsuser == null) {
						throw new MICSException("Employee not found! no employee with key " + micsuserkey);
					}
					//Authorize do some stuff here
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
						
						Company currCompany = micsCommonSessionHandler.getCompanyByKey(currMicsuser.getCompanykey());
						
						if (currCompany == null) {
							throw new Exception("Currently no company selected! Please select company first.");
						}
						session.put(MICSConstant.MICS_SESSION_CURRCOMPANY,currCompany);
						this.companykey = currCompany.getCompanykey();
					} else {
						this.tselAdmin = false;
						this.companykey = loginWebUser.getCompanykey();
					}
					logger.debug("tselAdmin: " + tselAdmin);
					logger.debug("micsuserkey: "+ micsuserkey);
					if (micsuserkey == null) {
						throw new MICSException("Employee Key is empty, can not found employee to view ");
					}
					
					this.groups = micsCommonSessionHandler.getGroupsByCompany(this.companykey);
					session.put(MICSConstant.MICS_SESSION_GROUPS, groups);
					userTypes = micsCommonSessionHandler.getUserTypes();
					session.put(MICSConstant.MICS_SESSION_USERTYPES, userTypes);
					numberTypes = micsCommonSessionHandler.getNumberTypes();
					session.put(MICSConstant.MICS_SESSION_NUMBERTYPES, numberTypes);
					languages = micsCommonSessionHandler.getLanguages();
					session.put(MICSConstant.MICS_SESSION_LANGUAGES, languages);
					huntingoptionList = micsCommonSessionHandler.getUserHuntingOptions();
					session.put(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST, huntingoptionList);
					outgoingProfiles = micsCommonSessionHandler.getMICSProfileList(loginWebUser.getCompanykey(), MICSConstant.MICS_SCREENING_TYPE_OUTGOING, MICSConstant.MICS_SCREENING_LEVEL_USER);
					session.put(MICSConstant.MICS_SESSION_OUTGOINGLIST, outgoingProfiles);
					incomingProfiles = micsCommonSessionHandler.getMICSProfileList(loginWebUser.getCompanykey(), MICSConstant.MICS_SCREENING_TYPE_INCOMING, MICSConstant.MICS_SCREENING_LEVEL_USER);
					session.put(MICSConstant.MICS_SESSION_INCOMINGLIST, incomingProfiles);
					
					this.micsuserkey = currMicsuser.getMicsuserkey();
					this.micsfirstname = currMicsuser.getMicsfirstname();
					this.micslastname = currMicsuser.getMicslastname();
					this.usertype = currMicsuser.getUsertype();
					this.mainpublicnumber = currMicsuser.getMainpublicnumber();
					this.mainprivatenumber = currMicsuser.getMainprivatenumber();
					this.groupkey = currMicsuser.getGroupkey();
					BigDecimal bdUserGroupId = currMicsuser.getUsergroupid();
					if (bdUserGroupId != null) {
						this.usergroupid = currMicsuser.getUsergroupid();
					}
					
					this.huntingoption = currMicsuser.getHuntingoption();
					BigDecimal bdSubGroupId = currMicsuser.getSubgroupid();
					if (bdSubGroupId != null) {
						this.subgroupid = currMicsuser.getSubgroupid();
					}
					BigDecimal bdVpnGroupId = currMicsuser.getVpngroupid();
					if (bdVpnGroupId != null) {
						this.vpngroupid = currMicsuser.getVpngroupid();
					}
					
					this.pin = currMicsuser.getPin();
					this.pinConfirmation = currMicsuser.getPin();
					this.language = currMicsuser.getLanguage();
					BigDecimal bdAnnoRecord = currMicsuser.getEnableannorecording();
					if (bdAnnoRecord != null) {
						if (bdAnnoRecord.intValue() == 1) {
							this.boolEnableAnnorRecording = true;
							
						} else {
							this.boolEnableAnnorRecording = false;
						}
					} else {
						this.boolEnableAnnorRecording = false;
					}
					
					BigDecimal bdKeepHunting = currMicsuser.getKeephuntingonbusy();
					if (bdKeepHunting != null) {
						if (bdKeepHunting.intValue() == 1) {
							this.boolKeepHuntingOnBusy = true;
						} else {
							this.boolKeepHuntingOnBusy = false;
						}
					} else {
						this.boolKeepHuntingOnBusy = false;
					}
					
					BigDecimal bdReverseCharging = currMicsuser.getReverseCharging();
					if (bdReverseCharging != null) {
						if (bdReverseCharging.intValue() == 1) {
							this.boolReverseCharging = true;
						} else {
							this.boolReverseCharging = false;
						}
					} else {
						this.boolReverseCharging = false;
					}
					
					BigDecimal bdGetLocked = currMicsuser.getLocked();
					if (bdGetLocked != null) {
						if (bdGetLocked.intValue() == 1) {
							this.boolLocked = true;
						} else {
							this.boolLocked = false;
						}
					} else {
						this.boolLocked = false;
					}
					
					BigDecimal bdMainNumberAsCP = currMicsuser.getMainnumberaschargedparty();
					if (bdMainNumberAsCP != null) {
						if (currMicsuser.getMainnumberaschargedparty().intValue() == 1) {
							this.boolMainNumberAcChargedParty = true;
						} else {
							this.boolMainNumberAcChargedParty = false;
						}
					} else {
						this.boolMainNumberAcChargedParty = false;
					}
					
					BigDecimal bdMainNumberAsCLI = currMicsuser.getMainnumberascli();
					if (bdMainNumberAsCLI != null) {
						if (currMicsuser.getMainnumberascli().intValue() == 1) {
							this.boolMainNumberAcCli = true;
						} else {
							this.boolMainNumberAcCli = false;
						}
					} else {
						this.boolMainNumberAcCli = false;
					}
					
					if (currMicsuser.getOutcs()!= null && !currMicsuser.getOutcs().equals("")) {
						this.outcs = currMicsuser.getOutcs();
						this.outcsdesc = currMicsuser.getOutcsdesc();
						if (currMicsuser.getBypassoutcs().equals("1")) {
							this.boolBypassOutCS = true;
						} else {
							this.boolBypassOutCS = false;
						}
					}
					if (currMicsuser.getIncs() != null && !currMicsuser.getIncs().equals("")) {
						this.incs = currMicsuser.getIncs();
						this.incsdesc = currMicsuser.getIncsdesc();
						if (currMicsuser.getBypassincs().equals("1")) {
							this.boolBypassInCS = true;
						} else {
							this.boolBypassInCS = false;
						}
					}
					//Get user Numbers
					List oldNumber = micsCommonSessionHandler.getNumberByEmployeeKey(currMicsuser.getMicsuserkey());
					micsUserNumberReq = new ArrayList<AddNumberRequest>();
					for (Iterator iterOldNumber = oldNumber.iterator(); iterOldNumber
							.hasNext();) {
						Micsusernumber currOldNumber = (Micsusernumber) iterOldNumber.next();
						AddNumberRequest currNumberRequest = new AddNumberRequest();
						currNumberRequest.setPublicNumber(currOldNumber.getPublicnumber());
						currNumberRequest.setPrivateNumber(currOldNumber.getPrivatenumber());
						currNumberRequest.setNumberType(currOldNumber.getNumbertype().intValue());
						currNumberRequest.setImsi(currOldNumber.getImsi());
						currNumberRequest.setSipuri(currOldNumber.getSipuri());
						currNumberRequest.setOrderIndex(currOldNumber.getOrderindex().intValue());
						if(currOldNumber.getPublicnumber().equals(mainpublicnumber)) currNumberRequest.setMainPublic(true);
						if(currOldNumber.getPrivatenumber().equals(mainprivatenumber)) currNumberRequest.setMainPrivate(true);
						if (currOldNumber.getProv_req_id() != null) {
							currNumberRequest.setProv_req_id(currOldNumber.getProv_req_id().intValue());
						}
						if (currOldNumber.getProv_req_status() != null) {
							currNumberRequest.setProv_req_status(currOldNumber.getProv_req_status().intValue());
						}
						micsUserNumberReq.add(currNumberRequest);
					}
					session.put(MICSConstant.MICS_SESSION_MEMBERREQ, micsUserNumberReq);
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
			logger.error("Failed to view employee",e);
			addActionError("Failed to view employee " + e.getMessage());
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
				if (micsCommonSessionHandler.isMenuAuthorized(employeeDelete, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					Micsuser currMicsuser = micsCommonSessionHandler.getEmployeeByKey(micsuserkey);
					if (currMicsuser == null) {
						throw new MICSException("Employee not found! no employee with key " + micsuserkey);
					}
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
						
						Company currCompany = micsCommonSessionHandler.getCompanyByKey(currMicsuser.getCompanykey());
						if (currCompany == null) {
							throw new Exception("Currently no company selected! Please select company first.");
						}
						session.put(MICSConstant.MICS_SESSION_CURRCOMPANY,currCompany);
						this.companykey = currCompany.getCompanykey();
					} else {
						this.tselAdmin = false;
						this.companykey = loginWebUser.getCompanykey();
						if (!currMicsuser.getCompanykey().equals(this.companykey)) {
							throw new MICSException("Sorry you are not authorized to delete Employee from other company " );
						}
						
					}
					logger.debug("tselAdmin: " + tselAdmin);
					logger.debug("micsuserkey: "+ micsuserkey);
					if (micsuserkey == null) {
						throw new MICSException("Employee Key is empty, can not found employee to delete ");
					}
					
					if(this.companykey== null) this.companykey = currMicsuser.getCompanykey();
					this.groups = micsCommonSessionHandler.getGroupsByCompany(this.companykey);
					userTypes = micsCommonSessionHandler.getUserTypes();
					session.put(MICSConstant.MICS_SESSION_USERTYPES, userTypes);
					numberTypes = micsCommonSessionHandler.getNumberTypes();
					session.put(MICSConstant.MICS_SESSION_NUMBERTYPES, numberTypes);
					languages = micsCommonSessionHandler.getLanguages();
					session.put(MICSConstant.MICS_SESSION_LANGUAGES, languages);
					outgoingProfiles = micsCommonSessionHandler.getMICSProfileList(loginWebUser.getCompanykey(), MICSConstant.MICS_SCREENING_TYPE_OUTGOING, MICSConstant.MICS_SCREENING_LEVEL_USER);
					session.put(MICSConstant.MICS_SESSION_OUTGOINGLIST, outgoingProfiles);
					incomingProfiles = micsCommonSessionHandler.getMICSProfileList(loginWebUser.getCompanykey(), MICSConstant.MICS_SCREENING_TYPE_INCOMING, MICSConstant.MICS_SCREENING_LEVEL_USER);
					session.put(MICSConstant.MICS_SESSION_INCOMINGLIST, incomingProfiles);
					
					this.micsuserkey = currMicsuser.getMicsuserkey();
					this.micsfirstname = currMicsuser.getMicsfirstname();
					this.micslastname = currMicsuser.getMicslastname();
					this.usertype = currMicsuser.getUsertype();
					this.mainpublicnumber = currMicsuser.getMainpublicnumber();
					this.mainprivatenumber = currMicsuser.getMainprivatenumber();
					this.groupkey = currMicsuser.getGroupkey();
					//
					BigDecimal bdUserGroupId = currMicsuser.getUsergroupid();
					if (bdUserGroupId != null) {
						this.usergroupid = currMicsuser.getUsergroupid();
					}
					
					this.huntingoption = currMicsuser.getHuntingoption();
					BigDecimal bdSubGroupId = currMicsuser.getSubgroupid();
					if (bdSubGroupId != null) {
						this.subgroupid = currMicsuser.getSubgroupid();
					}
					BigDecimal bdVpnGroupId = currMicsuser.getVpngroupid();
					if (bdVpnGroupId != null) {
						this.vpngroupid = currMicsuser.getVpngroupid();
					}
					
					this.pin = currMicsuser.getPin();
					this.pinConfirmation = currMicsuser.getPin();
					this.language = currMicsuser.getLanguage();
					BigDecimal bdAnnoRecord = currMicsuser.getEnableannorecording();
					if (bdAnnoRecord != null) {
						if (bdAnnoRecord.intValue() == 1) {
							this.boolEnableAnnorRecording = true;
							
						} else {
							this.boolEnableAnnorRecording = false;
						}
					} else {
						this.boolEnableAnnorRecording = false;
					}
					
					BigDecimal bdKeepHunting = currMicsuser.getKeephuntingonbusy();
					if (bdKeepHunting != null) {
						if (bdKeepHunting.intValue() == 1) {
							this.boolKeepHuntingOnBusy = true;
						} else {
							this.boolKeepHuntingOnBusy = false;
						}
					} else {
						this.boolKeepHuntingOnBusy = false;
					}
					
					BigDecimal bdReverseCharging = currMicsuser.getReverseCharging();
					if (bdReverseCharging != null) {
						if (bdReverseCharging.intValue() == 1) {
							this.boolReverseCharging = true;
						} else {
							this.boolReverseCharging = false;
						}
					} else {
						this.boolReverseCharging = false;
					}
					
					BigDecimal bdGetLocked = currMicsuser.getLocked();
					if (bdGetLocked != null) {
						if (bdGetLocked.intValue() == 1) {
							this.boolLocked = true;
						} else {
							this.boolLocked = false;
						}
					} else {
						this.boolLocked = false;
					}
					
					BigDecimal bdMainNumberAsCP = currMicsuser.getMainnumberaschargedparty();
					if (bdMainNumberAsCP != null) {
						if (currMicsuser.getMainnumberaschargedparty().intValue() == 1) {
							this.boolMainNumberAcChargedParty = true;
						} else {
							this.boolMainNumberAcChargedParty = false;
						}
					} else {
						this.boolMainNumberAcChargedParty = false;
					}
					
					BigDecimal bdMainNumberAsCLI = currMicsuser.getMainnumberascli();
					if (bdMainNumberAsCLI != null) {
						if (currMicsuser.getMainnumberascli().intValue() == 1) {
							this.boolMainNumberAcCli = true;
						} else {
							this.boolMainNumberAcCli = false;
						}
					} else {
						this.boolMainNumberAcCli = false;
					}
					//
					if (currMicsuser.getOutcs()!= null && !currMicsuser.getOutcs().equals("")) {
						this.outcs = currMicsuser.getOutcs();
						this.outcsdesc = currMicsuser.getOutcsdesc();
						String strBypassoutcs = currMicsuser.getBypassoutcs();
						if (strBypassoutcs == null) {
							strBypassoutcs = "";
						}
						if (strBypassoutcs.equals("1")) {
							this.boolBypassOutCS = true;
						} else {
							this.boolBypassOutCS = false;
						}
					}
					if (currMicsuser.getIncs() != null && !currMicsuser.getIncs().equals("")) {
						this.incs = currMicsuser.getIncs();
						this.incsdesc = currMicsuser.getIncsdesc();
						String strBypassincs = currMicsuser.getBypassincs();
						if (strBypassincs == null) {
							strBypassincs = "";
						}
						if (strBypassincs.equals("1")) {
							this.boolBypassInCS = true;
						} else {
							this.boolBypassInCS = false;
						}
					}
					//Get user Numbers
					List oldNumber = micsCommonSessionHandler.getNumberByEmployeeKey(currMicsuser.getMicsuserkey());
					micsUserNumberReq = new ArrayList<AddNumberRequest>();
					for (Iterator iterOldNumber = oldNumber.iterator(); iterOldNumber
							.hasNext();) {
						Micsusernumber currOldNumber = (Micsusernumber) iterOldNumber.next();
						AddNumberRequest currNumberRequest = new AddNumberRequest();
						currNumberRequest.setPublicNumber(currOldNumber.getPublicnumber());
						currNumberRequest.setPrivateNumber(currOldNumber.getPrivatenumber());
						BigDecimal bdNumberType = currOldNumber.getNumbertype();
						if (bdNumberType == null) {
							bdNumberType = new BigDecimal(0);
						}
						currNumberRequest.setNumberType(bdNumberType.intValue());
						currNumberRequest.setImsi(currOldNumber.getImsi());
						currNumberRequest.setSipuri(currOldNumber.getSipuri());
						BigDecimal bdOrderIndex = currOldNumber.getOrderindex();
						if (bdOrderIndex == null) {
							bdOrderIndex = new BigDecimal(0);
						}
						currNumberRequest.setOrderIndex(bdOrderIndex.intValue());
						if(currOldNumber.getPublicnumber().equals(mainpublicnumber)) currNumberRequest.setMainPublic(true);
						if(currOldNumber.getPrivatenumber().equals(mainprivatenumber)) currNumberRequest.setMainPrivate(true);
						micsUserNumberReq.add(currNumberRequest);
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
			logger.error("Failed to edit employee",e);
			addActionError("Failed to edit employee " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public String delete(){
		logger.info("delete()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(employeeDelete, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
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
					} else {
						this.tselAdmin = false;
						this.companykey = loginWebUser.getCompanykey();
					}
					logger.debug("tselAdmin: " + tselAdmin);
					this.groups = (List) session.get(MICSConstant.MICS_SESSION_GROUPS);
					if(this.groups == null ) groups = micsCommonSessionHandler.getGroupsByCompany(this.companykey);
					userTypes = (List) session.get(MICSConstant.MICS_SESSION_USERTYPES);
					numberTypes = (List)session.get(MICSConstant.MICS_SESSION_NUMBERTYPES);
					languages = (List) session.get(MICSConstant.MICS_SESSION_LANGUAGES );
					outgoingProfiles= (List) session.get(MICSConstant.MICS_SESSION_OUTGOINGLIST);
					incomingProfiles = (List) session.get(MICSConstant.MICS_SESSION_INCOMINGLIST);
					Micsuser currUser = micsCommonSessionHandler.getEmployeeByKey(micsuserkey);
					logger.debug("currUser: "+currUser);
					if (currUser == null) {
						throw new MICSException("Employee not found!");
					}
					List oldNumber = micsCommonSessionHandler.getNumberByEmployeeKey(micsuserkey);
					micsCommonSessionHandler.deleteEmployee(currUser, oldNumber);
					Micslogger.log("Delete","Employee", currUser.getMicsuserkey()+" "+ currUser.getMicslastname() +" deleted", this.companykey, loginWebUser.getWebusername(),"SUCCESS");
					addActionMessage("Successfully delete employee : " + currUser.getMicslastname()+"," + currUser.getMicsfirstname());
					result = MICSConstant.MICS_SUCCESS;
				} else {
					Micslogger.log("Delete","Employee", this.micsuserkey+" not deleted, user not authorized", this.companykey, loginWebUser.getWebusername(),"FAILED");
					addActionError("User not authorized");
					result = MICSConstant.MICS_ERROR_NOT_AUTH;
				}
			} else {
				addActionError("User not logged in");
				result = MICSConstant.MICS_ERROR_NOT_LOGGEDIN;
			}
		} catch (Exception e) {
			logger.error("Failed to delete employee",e);
			addActionError("Failed to delete employee " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
	}
	
	public String initSipPassword(){
		logger.info("initSipPassword()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(employeeSipPassword, loginWebUser.getRolekey())) {
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
							throw new Exception("Currently no company selected! Please select company first.");
						}
						this.companykey = currCompany.getCompanykey();
					} else {
						this.tselAdmin = false;
						this.companykey = loginWebUser.getCompanykey();
					}
					logger.debug("tselAdmin: " + tselAdmin);
					logger.debug("micsuserkey: "+ micsuserkey);
					if (micsuserkey == null) {
						throw new MICSException("Employee Key is empty, can not found employee for SIP Password change ");
					}
					Micsuser currMicsuser = micsCommonSessionHandler.getEmployeeByKey(micsuserkey);
					if (currMicsuser == null) {
						throw new MICSException("Employee not found! no employee with key " + micsuserkey);
					}
					this.groups = micsCommonSessionHandler.getGroupsByCompany(this.companykey);
					session.put(MICSConstant.MICS_SESSION_GROUPS, groups);
					userTypes = micsCommonSessionHandler.getUserTypes();
					session.put(MICSConstant.MICS_SESSION_USERTYPES, userTypes);
					numberTypes = micsCommonSessionHandler.getNumberTypes();
					session.put(MICSConstant.MICS_SESSION_NUMBERTYPES, numberTypes);
					languages = micsCommonSessionHandler.getLanguages();
					session.put(MICSConstant.MICS_SESSION_LANGUAGES, languages);
					huntingoptionList = micsCommonSessionHandler.getUserHuntingOptions();
					session.put(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST, huntingoptionList);
					outgoingProfiles = micsCommonSessionHandler.getMICSProfileList(loginWebUser.getCompanykey(), MICSConstant.MICS_SCREENING_TYPE_OUTGOING, MICSConstant.MICS_SCREENING_LEVEL_USER);
					session.put(MICSConstant.MICS_SESSION_OUTGOINGLIST, outgoingProfiles);
					incomingProfiles = micsCommonSessionHandler.getMICSProfileList(loginWebUser.getCompanykey(), MICSConstant.MICS_SCREENING_TYPE_INCOMING, MICSConstant.MICS_SCREENING_LEVEL_USER);
					session.put(MICSConstant.MICS_SESSION_INCOMINGLIST, incomingProfiles);
					
					this.micsuserkey = currMicsuser.getMicsuserkey();
					this.micsfirstname = currMicsuser.getMicsfirstname();
					this.micslastname = currMicsuser.getMicslastname();
					this.usertype = currMicsuser.getUsertype();
					this.mainpublicnumber = currMicsuser.getMainpublicnumber();
					this.mainprivatenumber = currMicsuser.getMainprivatenumber();
					this.groupkey = currMicsuser.getGroupkey();
					this.usergroupid = currMicsuser.getUsergroupid();
					this.huntingoption = currMicsuser.getHuntingoption();
					this.subgroupid = currMicsuser.getSubgroupid();
					this.vpngroupid = currMicsuser.getVpngroupid();
					this.pin = currMicsuser.getPin();
					this.pinConfirmation = currMicsuser.getPin();
					this.language = currMicsuser.getLanguage();
					
					if (currMicsuser.getEnableannorecording().intValue() == 1) {
						this.boolEnableAnnorRecording = true;
						
					} else {
						this.boolEnableAnnorRecording = false;
					}
					if (currMicsuser.getKeephuntingonbusy().intValue() == 1) {
						this.boolKeepHuntingOnBusy = true;
					} else {
						this.boolKeepHuntingOnBusy = false;
					}
					if (currMicsuser.getReverseCharging().intValue() == 1) {
						this.boolReverseCharging = true;
					} else {
						this.boolReverseCharging = false;
					}
					if (currMicsuser.getLocked().intValue() == 1) {
						this.boolLocked = true;
					} else {
						this.boolLocked = false;
					}
					if (currMicsuser.getMainnumberaschargedparty().intValue() == 1) {
						this.boolMainNumberAcChargedParty = true;
					} else {
						this.boolMainNumberAcChargedParty = false;
					}
					if (currMicsuser.getMainnumberascli().intValue() == 1) {
						this.boolMainNumberAcCli = true;
					} else {
						this.boolMainNumberAcCli = false;
					}
					if (currMicsuser.getOutcs()!= null && !currMicsuser.getOutcs().equals("")) {
						this.outcs = currMicsuser.getOutcs();
						this.outcsdesc = currMicsuser.getOutcsdesc();
						if (currMicsuser.getBypassoutcs().equals("1")) {
							this.boolBypassOutCS = true;
						} else {
							this.boolBypassOutCS = false;
						}
					}
					if (currMicsuser.getIncs() != null && !currMicsuser.getIncs().equals("")) {
						this.incs = currMicsuser.getIncs();
						this.incsdesc = currMicsuser.getIncsdesc();
						if (currMicsuser.getBypassincs().equals("1")) {
							this.boolBypassInCS = true;
						} else {
							this.boolBypassInCS = false;
						}
					}
					//Get user Numbers
					List oldNumber = micsCommonSessionHandler.getNumberByEmployeeKey(currMicsuser.getMicsuserkey());
					micsUserNumberReq = new ArrayList<AddNumberRequest>();
					for (Iterator iterOldNumber = oldNumber.iterator(); iterOldNumber
							.hasNext();) {
						Micsusernumber currOldNumber = (Micsusernumber) iterOldNumber.next();
						AddNumberRequest currNumberRequest = new AddNumberRequest();
						currNumberRequest.setPublicNumber(currOldNumber.getPublicnumber());
						currNumberRequest.setPrivateNumber(currOldNumber.getPrivatenumber());
						currNumberRequest.setNumberType(currOldNumber.getNumbertype().intValue());
						currNumberRequest.setImsi(currOldNumber.getImsi());
						currNumberRequest.setSipuri(currOldNumber.getSipuri());
						BigDecimal currOrderIndex = currOldNumber.getOrderindex();
						if (currOrderIndex != null) {
							currNumberRequest.setOrderIndex(currOrderIndex.intValue());
						} else {
							currNumberRequest.setOrderIndex(0);
						}
						
						if(currOldNumber.getPublicnumber().equals(mainpublicnumber)) currNumberRequest.setMainPublic(true);
						if(currOldNumber.getPrivatenumber().equals(mainprivatenumber)) currNumberRequest.setMainPrivate(true);
						micsUserNumberReq.add(currNumberRequest);
					}
					session.put(MICSConstant.MICS_SESSION_MEMBERREQ, micsUserNumberReq);
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
			logger.error("Failed to change employee SIP Password",e);
			addActionError("Failed to change employee SIP Password" + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public String sipPassword(){
		logger.info("sipPassword()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(employeeSipPassword, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
						companyList = (List) session.get(MICSConstant.MICS_SESSION_COMPANYLIST);
						logger.debug("companyList: " + companyList);
					} else {
						this.tselAdmin = false;
						this.companykey = loginWebUser.getCompanykey();
					}
					logger.debug("tselAdmin: " + tselAdmin);
					this.groups = (List) session.get(MICSConstant.MICS_SESSION_GROUPS);
					if(this.groups == null ) groups = micsCommonSessionHandler.getGroupsByCompany(this.companykey);
					userTypes = (List) session.get(MICSConstant.MICS_SESSION_USERTYPES);
					numberTypes = (List)session.get(MICSConstant.MICS_SESSION_NUMBERTYPES);
					languages = (List) session.get(MICSConstant.MICS_SESSION_LANGUAGES );
					outgoingProfiles= (List) session.get(MICSConstant.MICS_SESSION_OUTGOINGLIST);
					incomingProfiles = (List) session.get(MICSConstant.MICS_SESSION_INCOMINGLIST);
					huntingoptionList = (List) session.get(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST);

					Micsuser currMicsuser = micsCommonSessionHandler.getEmployeeByKey(micsuserkey);
					if (currMicsuser == null) {
						throw new MICSException("Employee not found! no employee with key " + micsuserkey);
					}
					//Just change the pin only, and set the password for every number according to pin
					//Pin change will be on another menu
					//Change PIN to SIP Password for security enhancement, SIP Password not store in the DB
					if (this.sipPassword != null) {
						if (this.sipPassword.equals(this.sipPasswordConf)) {
							List userNumbers = micsCommonSessionHandler.getNumberByEmployeeKey(currMicsuser.getMicsuserkey());
							micsCommonSessionHandler.changeSIPPasswordEmployee(currMicsuser,this.sipPassword, userNumbers);
							addActionMessage("Successfully change employee SIP Password: " + currMicsuser.getMicslastname()+"," + currMicsuser.getMicsfirstname());
							Micslogger.log("Change SIP Password","Employee", currMicsuser.getMicsuserkey()+" "+ currMicsuser.getMicslastname() +" SIP Password changed", this.companykey, loginWebUser.getWebusername(),"SUCCESS");
						} else {
							addActionError("SIP Password and SIP Password confirmation doesn't match, SIP Password not set!t");
							Micslogger.log("Change SIP Password","Employee", currMicsuser.getMicsuserkey()+" "+ currMicsuser.getMicslastname() +" change SIP Password failed", this.companykey, loginWebUser.getWebusername(),"FAILED");
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
			logger.error("Failed to change employee SIP Password",e);
			addActionError("Failed to change employee employee SIP Password" + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
	}
	/**
	 * @return the micsuserkey
	 */
	public String getMicsuserkey() {
		return micsuserkey;
	}
	/**
	 * @param micsuserkey the micsuserkey to set
	 */
	public void setMicsuserkey(String micsuserkey) {
		this.micsuserkey = micsuserkey;
	}
	/**
	 * @return the micsfirstname
	 */
	public String getMicsfirstname() {
		return micsfirstname;
	}
	/**
	 * @param micsfirstname the micsfirstname to set
	 */
	public void setMicsfirstname(String micsfirstname) {
		this.micsfirstname = micsfirstname;
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
	 * @return the groupkey
	 */
	public String getGroupkey() {
		return groupkey;
	}
	/**
	 * @param groupkey the groupkey to set
	 */
	public void setGroupkey(String groupkey) {
		this.groupkey = groupkey;
	}
	

	/**
	 * @return the subgroupid
	 */
	public BigDecimal getSubgroupid() {
		return subgroupid;
	}

	/**
	 * @param subgroupid the subgroupid to set
	 */
	public void setSubgroupid(BigDecimal subgroupid) {
		this.subgroupid = subgroupid;
	}

	/**
	 * @return the usergroupid
	 */
	public BigDecimal getUsergroupid() {
		return usergroupid;
	}

	/**
	 * @param usergroupid the usergroupid to set
	 */
	public void setUsergroupid(BigDecimal usergroupid) {
		this.usergroupid = usergroupid;
	}

	/**
	 * @return the vpngroupid
	 */
	public BigDecimal getVpngroupid() {
		return vpngroupid;
	}

	/**
	 * @param vpngroupid the vpngroupid to set
	 */
	public void setVpngroupid(BigDecimal vpngroupid) {
		this.vpngroupid = vpngroupid;
	}

	/**
	 * @return the mainpublicnumber
	 */
	public String getMainpublicnumber() {
		return mainpublicnumber;
	}
	/**
	 * @param mainpublicnumber the mainpublicnumber to set
	 */
	public void setMainpublicnumber(String mainpublicnumber) {
		this.mainpublicnumber = mainpublicnumber;
	}
	/**
	 * @return the mainprivatenumber
	 */
	public String getMainprivatenumber() {
		return mainprivatenumber;
	}
	/**
	 * @param mainprivatenumber the mainprivatenumber to set
	 */
	public void setMainprivatenumber(String mainprivatenumber) {
		this.mainprivatenumber = mainprivatenumber;
	}
	/**
	 * @return the usertype
	 */
	public BigDecimal getUsertype() {
		return usertype;
	}
	/**
	 * @param usertype the usertype to set
	 */
	public void setUsertype(BigDecimal usertype) {
		this.usertype = usertype;
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
	 * @return the mainnumberascli
	 */
	public BigDecimal getMainnumberascli() {
		return mainnumberascli;
	}
	/**
	 * @param mainnumberascli the mainnumberascli to set
	 */
	public void setMainnumberascli(BigDecimal mainnumberascli) {
		this.mainnumberascli = mainnumberascli;
	}
	/**
	 * @return the boolMainNumberAcCli
	 */
	public boolean isBoolMainNumberAcCli() {
		return boolMainNumberAcCli;
	}
	/**
	 * @param boolMainNumberAcCli the boolMainNumberAcCli to set
	 */
	public void setBoolMainNumberAcCli(boolean boolMainNumberAcCli) {
		this.boolMainNumberAcCli = boolMainNumberAcCli;
	}
	/**
	 * @return the mainnumberaschargedparty
	 */
	public BigDecimal getMainnumberaschargedparty() {
		return mainnumberaschargedparty;
	}
	/**
	 * @param mainnumberaschargedparty the mainnumberaschargedparty to set
	 */
	public void setMainnumberaschargedparty(BigDecimal mainnumberaschargedparty) {
		this.mainnumberaschargedparty = mainnumberaschargedparty;
	}
	/**
	 * @return the boolMainNumberAcChargedParty
	 */
	public boolean isBoolMainNumberAcChargedParty() {
		return boolMainNumberAcChargedParty;
	}
	/**
	 * @param boolMainNumberAcChargedParty the boolMainNumberAcChargedParty to set
	 */
	public void setBoolMainNumberAcChargedParty(boolean boolMainNumberAcChargedParty) {
		this.boolMainNumberAcChargedParty = boolMainNumberAcChargedParty;
	}
	/**
	 * @return the huntingoption
	 */
	public BigDecimal getHuntingoption() {
		return huntingoption;
	}
	/**
	 * @param huntingoption the huntingoption to set
	 */
	public void setHuntingoption(BigDecimal huntingoption) {
		this.huntingoption = huntingoption;
	}
	/**
	 * @return the boolHuntingOption
	 */
	public boolean isBoolHuntingOption() {
		return boolHuntingOption;
	}
	/**
	 * @param boolHuntingOption the boolHuntingOption to set
	 */
	public void setBoolHuntingOption(boolean boolHuntingOption) {
		this.boolHuntingOption = boolHuntingOption;
	}
	/**
	 * @return the keephuntingonbusy
	 */
	public BigDecimal getKeephuntingonbusy() {
		return keephuntingonbusy;
	}
	/**
	 * @param keephuntingonbusy the keephuntingonbusy to set
	 */
	public void setKeephuntingonbusy(BigDecimal keephuntingonbusy) {
		this.keephuntingonbusy = keephuntingonbusy;
	}
	/**
	 * @return the boolKeepHuntingOnBusy
	 */
	public boolean isBoolKeepHuntingOnBusy() {
		return boolKeepHuntingOnBusy;
	}
	/**
	 * @param boolKeepHuntingOnBusy the boolKeepHuntingOnBusy to set
	 */
	public void setBoolKeepHuntingOnBusy(boolean boolKeepHuntingOnBusy) {
		this.boolKeepHuntingOnBusy = boolKeepHuntingOnBusy;
	}
	/**
	 * @return the ReverseCharging
	 */
	public BigDecimal getReverseCharging() {
		return reverseCharging;
	}
	/**
	 * @param ReverseCharging the ReverseCharging to set
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
	 * @return the enableannorecording
	 */
	public BigDecimal getEnableannorecording() {
		return enableannorecording;
	}
	/**
	 * @param enableannorecording the enableannorecording to set
	 */
	public void setEnableannorecording(BigDecimal enableannorecording) {
		this.enableannorecording = enableannorecording;
	}
	/**
	 * @return the boolEnableAnnorRecording
	 */
	public boolean isBoolEnableAnnorRecording() {
		return boolEnableAnnorRecording;
	}
	/**
	 * @param boolEnableAnnorRecording the boolEnableAnnorRecording to set
	 */
	public void setBoolEnableAnnorRecording(boolean boolEnableAnnorRecording) {
		this.boolEnableAnnorRecording = boolEnableAnnorRecording;
	}
	/**
	 * @return the pin
	 */
	public String getPin() {
		return pin;
	}
	/**
	 * @param pin the pin to set
	 */
	public void setPin(String pin) {
		this.pin = pin;
	}
	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}
	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
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
	 * @return the micslastname
	 */
	public String getMicslastname() {
		return micslastname;
	}
	/**
	 * @param micslastname the micslastname to set
	 */
	public void setMicslastname(String micslastname) {
		this.micslastname = micslastname;
	}
	/**
	 * @return the employeeCreate
	 */
	public String getEmployeeCreate() {
		return employeeCreate;
	}
	/**
	 * @param employeeCreate the employeeCreate to set
	 */
	public void setEmployeeCreate(String employeeCreate) {
		this.employeeCreate = employeeCreate;
	}
	/**
	 * @return the employeeSearch
	 */
	public String getEmployeeSearch() {
		return employeeSearch;
	}
	/**
	 * @param employeeSearch the employeeSearch to set
	 */
	public void setEmployeeSearch(String employeeSearch) {
		this.employeeSearch = employeeSearch;
	}
	
	/**
	 * @return the pinConfirmation
	 */
	public String getPinConfirmation() {
		return pinConfirmation;
	}

	/**
	 * @param pinConfirmation the pinConfirmation to set
	 */
	public void setPinConfirmation(String pinConfirmation) {
		this.pinConfirmation = pinConfirmation;
	}

	/**
	 * @return the languages
	 */
	public List getLanguages() {
		return languages;
	}

	/**
	 * @param languages the languages to set
	 */
	public void setLanguages(List languages) {
		this.languages = languages;
	}

	/**
	 * @return the newPublicNumber
	 */
	public String getNewPublicNumber() {
		return newPublicNumber;
	}

	/**
	 * @param newPublicNumber the newPublicNumber to set
	 */
	public void setNewPublicNumber(String newPublicNumber) {
		this.newPublicNumber = newPublicNumber;
	}

	/**
	 * @return the newPrivateNumber
	 */
	public String getNewPrivateNumber() {
		return newPrivateNumber;
	}

	/**
	 * @param newPrivateNumber the newPrivateNumber to set
	 */
	public void setNewPrivateNumber(String newPrivateNumber) {
		this.newPrivateNumber = newPrivateNumber;
	}
	
	
	/**
	 * @return the newImsi
	 */
	public String getNewImsi() {
		return newImsi;
	}

	/**
	 * @param newImsi the newImsi to set
	 */
	public void setNewImsi(String newImsi) {
		this.newImsi = newImsi;
	}
	
	
	/**
	 * @return the newSipuri
	 */
	public String getNewSipuri() {
		return newSipuri;
	}

	/**
	 * @param newSipuri the newSipuri to set
	 */
	public void setNewSipuri(String newSipuri) {
		this.newSipuri = newSipuri;
	}

	/**
	 * @return the newOrderIndex
	 */
	public int getNewOrderIndex() {
		return newOrderIndex;
	}

	/**
	 * @param newOrderIndex the newOrderIndex to set
	 */
	public void setNewOrderIndex(int newOrderIndex) {
		this.newOrderIndex = newOrderIndex;
	}

	/**
	 * @return the newNumberType
	 */
	public int getNewNumberType() {
		return newNumberType;
	}

	/**
	 * @param newNumberType the newNumberType to set
	 */
	public void setNewNumberType(int newNumberType) {
		this.newNumberType = newNumberType;
	}

	/**
	 * @return the numberTypes
	 */
	public List getNumberTypes() {
		return numberTypes;
	}

	/**
	 * @param numberTypes the numberTypes to set
	 */
	public void setNumberTypes(List numberTypes) {
		this.numberTypes = numberTypes;
	}

	/**
	 * @param micsUserNumberReq the micsUserNumberReq to set
	 */
	public void setMicsUserNumberReq(List<AddNumberRequest> micsUserNumberReq) {
		this.micsUserNumberReq = micsUserNumberReq;
	}

	/**
	 * @return the employeeEdit
	 */
	public String getEmployeeEdit() {
		return employeeEdit;
	}
	/**
	 * @param employeeEdit the employeeEdit to set
	 */
	public void setEmployeeEdit(String employeeEdit) {
		this.employeeEdit = employeeEdit;
	}
	/**
	 * @return the employeeDelete
	 */
	public String getEmployeeDelete() {
		return employeeDelete;
	}
	/**
	 * @param employeeDelete the employeeDelete to set
	 */
	public void setEmployeeDelete(String employeeDelete) {
		this.employeeDelete = employeeDelete;
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
	 * @return the companyList
	 */
	@SuppressWarnings("rawtypes")
	public List getCompanyList() {
		return companyList;
	}
	/**
	 * @param companyList the companyList to set
	 */
	@SuppressWarnings("rawtypes")
	public void setCompanyList(List companyList) {
		this.companyList = companyList;
	}
	/**
	 * @return the employeeList
	 */
	@SuppressWarnings("rawtypes")
	public List getEmployeeList() {
		return employeeList;
	}
	/**
	 * @param employeeList the employeeList to set
	 */
	@SuppressWarnings("rawtypes")
	public void setEmployeeList(List employeeList) {
		this.employeeList = employeeList;
	}
	
	
	/**
	 * @return the outgoingProfiles
	 */
	@SuppressWarnings("rawtypes")
	public List getOutgoingProfiles() {
		return outgoingProfiles;
	}


	/**
	 * @param outgoingProfiles the outgoingProfiles to set
	 */
	@SuppressWarnings("rawtypes")
	public void setOutgoingProfiles(List outgoingProfiles) {
		this.outgoingProfiles = outgoingProfiles;
	}


	/**
	 * @return the incomingProfiles
	 */
	@SuppressWarnings("rawtypes")
	public List getIncomingProfiles() {
		return incomingProfiles;
	}


	/**
	 * @param incomingProfiles the incomingProfiles to set
	 */
	@SuppressWarnings("rawtypes")
	public void setIncomingProfiles(List incomingProfiles) {
		this.incomingProfiles = incomingProfiles;
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
	 * @return the userTypes
	 */
	public List getUserTypes() {
		return userTypes;
	}

	/**
	 * @param userTypes the userTypes to set
	 */
	public void setUserTypes(List userTypes) {
		this.userTypes = userTypes;
	}

	/**
	 * @return the micsUserNumberReq
	 */
	public List getMicsUserNumberReq() {
		return micsUserNumberReq;
	}

	


	/**
	 * @return the sipPassword
	 */
	public String getSipPassword() {
		return sipPassword;
	}

	/**
	 * @param sipPassword the sipPassword to set
	 */
	public void setSipPassword(String sipPassword) {
		this.sipPassword = sipPassword;
	}

	/**
	 * @return the sipPasswordConf
	 */
	public String getSipPasswordConf() {
		return sipPasswordConf;
	}

	/**
	 * @param sipPasswordConf the sipPasswordConf to set
	 */
	public void setSipPasswordConf(String sipPasswordConf) {
		this.sipPasswordConf = sipPasswordConf;
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
	 * @return the employeeSipPassword
	 */
	public String getEmployeeSipPassword() {
		return employeeSipPassword;
	}

	/**
	 * @param employeeSipPassword the employeeSipPassword to set
	 */
	public void setEmployeeSipPassword(String employeeSipPassword) {
		this.employeeSipPassword = employeeSipPassword;
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
	 * @return the optRole
	 */
	public boolean getOptRole() {
		return optRole;
	}

	/**
	 * @param optRole the optRole to set
	 */
	public void setOptRole(boolean optRole) {
		this.optRole = optRole;
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




	private String micsuserkey;
	private String micsfirstname;
	private String companykey;
	private String address;
	private String groupkey;
//	private int subgroupid;
//	private int usergroupid;
//	private int vpngroupid;
	private BigDecimal subgroupid;
	private BigDecimal usergroupid;
	private BigDecimal vpngroupid;

	private String mainpublicnumber;
	private String mainprivatenumber;
	private BigDecimal usertype;
	private BigDecimal locked;
	private boolean boolLocked;
	private BigDecimal mainnumberascli;
	private boolean boolMainNumberAcCli;
	private BigDecimal mainnumberaschargedparty;
	private boolean boolMainNumberAcChargedParty;
	private BigDecimal huntingoption;
	private boolean boolHuntingOption;
	private BigDecimal keephuntingonbusy;
	private boolean boolKeepHuntingOnBusy;
	private BigDecimal reverseCharging;
	private boolean boolReverseCharging;
	private String incs;
	private String incsdesc;
	private String bypassincs;
	private boolean boolBypassInCS;
	private String outcs;
	private String outcsdesc;
	private String bypassoutcs;
	private boolean boolBypassOutCS;
	private BigDecimal enableannorecording;
	private boolean boolEnableAnnorRecording;
	private String pin;
	private String pinConfirmation;
	private String sipPassword;
	private String sipPasswordConf;
	private String language;
	private String updateuser;
	private Timestamp updatetimestamp;
	private String micslastname;
	private List companyList;
	private List employeeList;
	private List outgoingProfiles;
	private List incomingProfiles;
	private List userTypes;
	private List languages;
	private List groups;
	private List huntingoptionList;
	private List<AddNumberRequest> micsUserNumberReq;
	private String newPublicNumber;
	private String newPrivateNumber;
	private String newImsi;
	private String newSipuri;
	private int newOrderIndex;
	private int newNumberType;
	private List numberTypes;
	private int searchCnt;
	
	private boolean deleteAllowed;
	private boolean editAllowed;
	private String employeeCreate = "2017050501";
	private String employeeSearch = "2017050502";
	private String employeeEdit = "2017050503";
	private String employeeDelete = "2017050504";
	private String employeeSipPassword = "2017050505";
	/**
	 * @return the employeeView
	 */
	public String getEmployeeView() {
		return employeeView;
	}

	/**
	 * @param employeeView the employeeView to set
	 */
	public void setEmployeeView(String employeeView) {
		this.employeeView = employeeView;
	}



	private String employeeView = "2017050506";
	private boolean tselAdmin;
	private boolean optRole;
	private Map<String, Object> session;
	private static final Logger logger = Logger.getLogger(EmployeeAction.class);
	
	@Override
	public void prepare() throws Exception {
		logger.info("prepare()");
		MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
		userTypes = micsCommonSessionHandler.getUserTypes();
		session.put(MICSConstant.MICS_SESSION_USERTYPES, userTypes);
		numberTypes = micsCommonSessionHandler.getNumberTypes();
		session.put(MICSConstant.MICS_SESSION_NUMBERTYPES, numberTypes);
		languages = micsCommonSessionHandler.getLanguages();
		session.put(MICSConstant.MICS_SESSION_LANGUAGES, languages);
		outgoingProfiles = micsCommonSessionHandler.getMICSProfileList("", MICSConstant.MICS_SCREENING_TYPE_OUTGOING, MICSConstant.MICS_SCREENING_LEVEL_USER);
		session.put(MICSConstant.MICS_SESSION_OUTGOINGLIST, outgoingProfiles);
		incomingProfiles = micsCommonSessionHandler.getMICSProfileList("", MICSConstant.MICS_SCREENING_TYPE_INCOMING, MICSConstant.MICS_SCREENING_LEVEL_USER);
		session.put(MICSConstant.MICS_SESSION_INCOMINGLIST, incomingProfiles);
		micsUserNumberReq = (List<AddNumberRequest>) session.get(MICSConstant.MICS_SESSION_MEMBERREQ);
		logger.info("micsusernum :"+micsUserNumberReq);
		this.groups = (List) session.get(MICSConstant.MICS_SESSION_GROUPS);
		if (this.groups == null) {
			this.groups = micsCommonSessionHandler.getGroupsByCompany(this.companykey);
		}
		session.put(MICSConstant.MICS_SESSION_GROUPS,this.groups);
		huntingoptionList = micsCommonSessionHandler.getUserHuntingOptions();
		session.put(MICSConstant.MICS_SESSION_HUNTINGOPTIONLIST, huntingoptionList);
	}

}
