package com.nsn.telkomsel.mics.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.nsn.telkomsel.mics.handler.MICSCommonSessionHandler;
import com.nsn.telkomsel.mics.orahbm.Announcement;
import com.nsn.telkomsel.mics.orahbm.Company;
import com.nsn.telkomsel.mics.orahbm.Switchboard;
import com.nsn.telkomsel.mics.orahbm.Switchboardmenu;
import com.nsn.telkomsel.mics.orahbm.Switchboardsubmenu;
import com.nsn.telkomsel.mics.orahbm.Webuser;
import com.nsn.telkomsel.mics.util.AddUserRequest;
import com.nsn.telkomsel.mics.util.MICSConstant;
import com.nsn.telkomsel.mics.util.Micslogger;
import com.nsn.telkomsel.mics.util.SwitchboardEntry;
import com.nsn.telkomsel.mics.util.SwitchboardType;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class SwitchboardListAction extends ActionSupport implements SessionAware,Preparable {

	

	private static final long serialVersionUID = 434069389304004620L;

	@SuppressWarnings("unchecked")
	public String initListView(){
		logger.info("initListView()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
//TODO: only for development. remove this for production.
/*
session.put(MICSConstant.MICS_SESSION_USER, micsCommonSessionHandler.getActiveUser("admin", (new MICSPasswordEnc()).getEncryptedPassword("tks#123!")));
session.put(MICSConstant.MICS_SESSION_CURRCOMPANY, micsCommonSessionHandler.getCompanyByKey("10088"));
*/
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(switchboardView, loginWebUser.getRolekey())) {
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
						logger.debug("currCompany: " + currCompany);
						if(currCompany == null) throw new Exception ("Currently no company selected! please select company first.");
						this.companykey = currCompany.getCompanykey();
					} else {
						this.tselAdmin = false;
						this.companykey = loginWebUser.getCompanykey();
					}
					
					setSwitchboards(micsCommonSessionHandler.getCompanySwitchboardList(this.companykey));
					
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
			logger.error("Failed to initialize Company Switchboard admin",e);
			addActionError("Failed to process your Company Switchboard administration request " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		logger.debug("result: " + result);
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	public String initDelete(){
		logger.info("initDelete()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
//TODO: only for development. remove this for production.
/*
session.put(MICSConstant.MICS_SESSION_USER, micsCommonSessionHandler.getActiveUser("admin", (new MICSPasswordEnc()).getEncryptedPassword("tks#123!")));
session.put(MICSConstant.MICS_SESSION_CURRCOMPANY, micsCommonSessionHandler.getCompanyByKey("10088"));
*/
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(switchboardCreate, loginWebUser.getRolekey())) {
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
						logger.debug("currCompany: " + currCompany);
						if(currCompany == null) throw new Exception ("Currently no company selected! please select company first.");
						this.companykey = currCompany.getCompanykey();
					} else {
						this.tselAdmin = false;
						this.companykey = loginWebUser.getCompanykey();
					}
					
					//Check if switchboard already created
					switchboard = micsCommonSessionHandler.getCompanySwitchboardSingle(this.companykey, this.switchboardkey);
					
					setDeleteAllowed(true);
					
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
			logger.error("Failed to initialize Company Switchboard admin",e);
			addActionError("Failed to process your Company Switchboard administration request " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		logger.debug("result: " + result);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public String initDeleting(){
		logger.info("initDeleting()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
//TODO: only for development. remove this for production.
/*
session.put(MICSConstant.MICS_SESSION_USER, micsCommonSessionHandler.getActiveUser("admin", (new MICSPasswordEnc()).getEncryptedPassword("tks#123!")));
session.put(MICSConstant.MICS_SESSION_CURRCOMPANY, micsCommonSessionHandler.getCompanyByKey("10088"));
*/
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(switchboardCreate, loginWebUser.getRolekey())) {
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
						logger.debug("currCompany: " + currCompany);
						if(currCompany == null) throw new Exception ("Currently no company selected! please select company first.");
						this.companykey = currCompany.getCompanykey();
					} else {
						this.tselAdmin = false;
						this.companykey = loginWebUser.getCompanykey();
					}
					
					switchboard = micsCommonSessionHandler.getCompanySwitchboardSingle(this.companykey, this.switchboardkey);
					switchboardmenus = micsCommonSessionHandler.getSwitchboardmenus(switchboard.getSwitchboardkey());
					switchboardsubmenus = new ArrayList<List<Switchboardsubmenu>>();
					for(Switchboardmenu menu : switchboardmenus) {
						switchboardsubmenus.add(micsCommonSessionHandler.getSwitchboardsubmenus(menu.getSwitchboardmenukey()));
					}
					
					micsCommonSessionHandler.deleteCompanySwitchboard(this.switchboard, this.switchboardmenus, this.switchboardsubmenus);
					
					setDeleteAllowed(false);
					
					addActionMessage("Company switchboard succesfully deleted");
					logger.debug("deleting switchboard complete");
					
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
			logger.error("Failed to initialize Company Switchboard admin",e);
			addActionError("Failed to process your Company Switchboard administration request " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		logger.debug("result: " + result);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public String initEdit(){
		logger.info("initEdit()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
//TODO: only for development. remove this for production.
/*
session.put(MICSConstant.MICS_SESSION_USER, micsCommonSessionHandler.getActiveUser("admin", (new MICSPasswordEnc()).getEncryptedPassword("tks#123!")));
session.put(MICSConstant.MICS_SESSION_CURRCOMPANY, micsCommonSessionHandler.getCompanyByKey("10088"));
*/
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(switchboardCreate, loginWebUser.getRolekey())) {
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
						logger.debug("currCompany: " + currCompany);
						if(currCompany == null) throw new Exception ("Currently no company selected! please select company first.");
						this.companykey = currCompany.getCompanykey();
					} else {
						this.tselAdmin = false;
						this.companykey = loginWebUser.getCompanykey();
					}
					this.annoList = micsCommonSessionHandler.getCompanyAnno(companykey);
					
					logger.debug("annoList: " + annoList);
					session.put(MICSConstant.MICS_SESSION_ANNOLIST, annoList);
					this.switchList = micsCommonSessionHandler.getSwitchboardTypes();
					session.put(MICSConstant.MICS_SESSION_SWITCHLIST, switchList);
					//Check if switchboard already created
					switchboard = micsCommonSessionHandler.getCompanySwitchboardSingle(this.companykey, this.switchboardkey);
					if(switchboard == null) {
						//new empty switchboard
						switchboard = new Switchboard();
						
						switchboardmenus = new ArrayList<Switchboardmenu>();
						
						switchboardsubmenus = new ArrayList<List<Switchboardsubmenu>>();
					}
					else {
						//Get Switchboard menu
						switchboardmenus = micsCommonSessionHandler.getSwitchboardmenus(switchboard.getSwitchboardkey());
						switchboardsubmenus = new ArrayList<List<Switchboardsubmenu>>();
						for(Switchboardmenu menu : switchboardmenus) {
							List<Switchboardsubmenu> ssm = micsCommonSessionHandler.getSwitchboardsubmenus(menu.getSwitchboardmenukey());
							for(Switchboardsubmenu onessm : ssm) {
								if(onessm.getDestinationnumber() == null || "".equals(onessm.getDestinationnumber())) {
									onessm.setDestinationnumber(" ");
								}
							}
							for(int i=ssm.size()-1; i >= 0; i--) {
								Switchboardsubmenu onessm = ssm.get(i);
								if(" ".equals(onessm.getDestinationnumber())) {
									onessm.setDestinationnumber("");
								}
								else if(onessm.getDestinationnumber() != null) {
									break;
								}
							}
							switchboardsubmenus.add(ssm);
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
			logger.error("Failed to initialize Company Switchboard admin",e);
			addActionError("Failed to process your Company Switchboard administration request " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		logger.debug("result: " + result);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public String initSave(){
		logger.info("initSave()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
//TODO: only for development. remove this for production.
/*
session.put(MICSConstant.MICS_SESSION_USER, micsCommonSessionHandler.getActiveUser("admin", (new MICSPasswordEnc()).getEncryptedPassword("tks#123!")));
session.put(MICSConstant.MICS_SESSION_CURRCOMPANY, micsCommonSessionHandler.getCompanyByKey("10088"));
*/
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(switchboardCreate, loginWebUser.getRolekey())) {
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
						logger.debug("currCompany: " + currCompany);
						if(currCompany == null) throw new Exception ("Currently no company selected! please select company first.");
						this.companykey = currCompany.getCompanykey();
					} else {
						this.tselAdmin = false;
						this.companykey = loginWebUser.getCompanykey();
					}
					this.annoList = micsCommonSessionHandler.getCompanyAnno(companykey);
					logger.debug("annoList: " + annoList);
					session.put(MICSConstant.MICS_SESSION_ANNOLIST, annoList);
					this.switchList = micsCommonSessionHandler.getSwitchboardTypes();
					session.put(MICSConstant.MICS_SESSION_SWITCHLIST, switchList);
					
					this.switchboardsubmenus = new ArrayList<List<Switchboardsubmenu>>();
					
					List<SwitchboardEntry> switchboardEntries = new ArrayList<SwitchboardEntry>();
					List<Switchboardmenu> deleteMenu = new ArrayList<Switchboardmenu>();
					List<Switchboardsubmenu> deleteSubmenu = new ArrayList<Switchboardsubmenu>();
					Iterator<Switchboardmenu> itMenus = this.switchboardmenus.iterator();
					Iterator<Switchboardsubmenu> itNewsubmenus = this.newsubmenus.iterator();
					for(int i=0; i<9; i++) {
						List<Switchboardsubmenu> switchboardsubmenuList = new ArrayList<Switchboardsubmenu>();
						for(int j=0; j<9; j++) {
							Switchboardsubmenu onesmenu = itNewsubmenus.next();
							if(onesmenu.getDestinationnumber() != null) {
								onesmenu.setDestinationnumber(onesmenu.getDestinationnumber().trim());
							}
							switchboardsubmenuList.add(onesmenu);
						}
						SwitchboardEntry switchboardEntry = new SwitchboardEntry();
						switchboardEntry.setMenu(itMenus.next());
						switchboardEntry.setSubMenu(switchboardsubmenuList);
						switchboardEntries.add(switchboardEntry);
						
						this.switchboardsubmenus.add(switchboardsubmenuList);
					}
					
					this.switchboard.setCompanykey(this.companykey);
					this.switchboard.setUpdateuser(loginWebUser.getWebusername());
					this.switchboard.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
					
					List<SwitchboardEntry> copySwitchboardEntries = new ArrayList<SwitchboardEntry>(switchboardEntries);
					for(int i=switchboardEntries.size()-1; i>=0; i--) {
						SwitchboardEntry aEntry = switchboardEntries.get(i);
						Switchboardmenu amenu = aEntry.getMenu();
						if(amenu.getMenutype() == null || amenu.getMenutype().intValue() == 0){
							copySwitchboardEntries.remove(aEntry);
							if(amenu.getSwitchboardmenukey() != null && !"".equals(amenu.getSwitchboardmenukey())) {
								deleteMenu.add(amenu);
								if(aEntry.getSubMenu()!=null && aEntry.getSubMenu().size() > 0) {
									for(Switchboardsubmenu asubmenu : (List<Switchboardsubmenu>) aEntry.getSubMenu()) {
										if(asubmenu.getSwitchboardsubmenukey() != null && !"".equals(asubmenu.getSwitchboardsubmenukey())) {
											deleteSubmenu.add(asubmenu);
										}
									}
								}
							}
						}
						else {
							break;
						}
					}
					for(SwitchboardEntry aEntry : copySwitchboardEntries) {
						Switchboardmenu amenu = aEntry.getMenu();
						
						amenu.setSwitchboardkey(this.switchboard.getSwitchboardkey());
						amenu.setUpdateuser(loginWebUser.getWebusername());
						amenu.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
						
						List<Switchboardsubmenu> switchboardsubmenuList = aEntry.getSubMenu();
						List<Switchboardsubmenu> copySubmenuList = new ArrayList<Switchboardsubmenu>(switchboardsubmenuList);
						for(int i=switchboardsubmenuList.size()-1; i>=0; i--) {
							//for(Switchboardsubmenu asubmenu : switchboardsubmenuList) {
							Switchboardsubmenu asubmenu = switchboardsubmenuList.get(i);
							if(asubmenu.getDestinationnumber() == null || "".equals(asubmenu.getDestinationnumber().trim())) {
								copySubmenuList.remove(asubmenu);
								if(asubmenu.getSwitchboardsubmenukey() != null && !"".equals(asubmenu.getSwitchboardsubmenukey())) {
									deleteSubmenu.add(asubmenu);
								}
							}
							else {
								break;
							}
						}
						for(Switchboardsubmenu asubmenu : copySubmenuList) {
							asubmenu.setSwitchboardmenukey(amenu.getSwitchboardmenukey());
							asubmenu.setUpdateuser(loginWebUser.getWebusername());
							asubmenu.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
						}
						aEntry.setSubMenu(copySubmenuList);
					}
					switchboardEntries = copySwitchboardEntries;
					
					/*validation for empty destination msisdn*/
					boolean inputValid = true;
					if(this.switchboard.getSwitchboardname() == null || "".equals(this.switchboard.getSwitchboardname())) {
						addActionError("Switchboard Name must be filled-in");
						result = MICSConstant.MICS_INPUT;
						inputValid = false;
					}
					else if(this.switchboard.getPublicnumber() == null || "".equals(this.switchboard.getPublicnumber())) {
						addActionError("Public Number must be filled-in");
						result = MICSConstant.MICS_INPUT;
						inputValid = false;
					}
					else if(this.switchboard.getPrivatenumber() == null || "".equals(this.switchboard.getPrivatenumber())) {
						addActionError("Private Number must be filled-in");
						result = MICSConstant.MICS_INPUT;
						inputValid = false;
					}
					else if(this.switchboard.getAnnoid() == null) {
						addActionError("Announcement must be selected");
						result = MICSConstant.MICS_INPUT;
						inputValid = false;
					}
					else {
						for(SwitchboardEntry aEntry : switchboardEntries) {
							Switchboardmenu amenu = aEntry.getMenu();
							
							if( (new java.math.BigDecimal(2)).equals(amenu.getMenutype()) && (amenu.getDestinationnumber() == null || "".equals(amenu.getDestinationnumber().trim()))) {
								addActionError("Destination number must be filled-in");
								result = MICSConstant.MICS_INPUT;
								inputValid = false;
								break;
							}
							
							/*
							List<Switchboardsubmenu> switchboardsubmenuList = aEntry.getSubMenu();
							for(int i=switchboardsubmenuList.size()-1; i>=0; i--) {
								Switchboardsubmenu asubmenu = switchboardsubmenuList.get(i);
								if(asubmenu.getDestinationnumber() == null || "".equals(asubmenu.getDestinationnumber())) {
									addActionError("Destination number must be filled-in");
									result = MICSConstant.MICS_INPUT;
									inputValid = false;
									break;
								}
							}
							if(inputValid == false) {
								break;
							}
							*/
						}
					}
					
					if(inputValid) {
						micsCommonSessionHandler.createCompanySwitchboard(this.switchboard, switchboardEntries, deleteMenu, deleteSubmenu);
						
						addActionMessage("Company switchboard succesfully saved");
						logger.debug("create/save switchboard complete");
						Micslogger.log("Switchboard","Company",   "Switchboard for company "+ loginWebUser.getCompanykey() +" created/saved"  , loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"SUCCESS");
						result = MICSConstant.MICS_SUCCESS;
					}
					
					for(int i=this.switchboardmenus.size()-1; i>=0; i--) {
						if(this.switchboardmenus.get(i).getMenutype() == null || this.switchboardmenus.get(i).getMenutype().intValue() == 0) {
							this.switchboardmenus.remove(i);
							this.switchboardsubmenus.remove(i);
						}
						else {
							break;
						}
					}
					
					for(int i=0; i<this.switchboardsubmenus.size(); i++) {
						List<Switchboardsubmenu> submenuList = this.switchboardsubmenus.get(i);
						for(int j=submenuList.size()-1; j>=1; j--) {
							if(submenuList.get(j).getDestinationnumber() == null || "".equals(submenuList.get(j).getDestinationnumber().trim())) {
								submenuList.remove(j);
							}
							else {
								break;
							}
						}
						for(Switchboardsubmenu onessm : submenuList) {
							if(onessm.getDestinationnumber() == null || "".equals(onessm.getDestinationnumber())) {
								onessm.setDestinationnumber(" ");
							}
						}
					}
					
				} else {
					addActionError("User not authorized");
					Micslogger.log("Switchboard","Company",   "Fail to create switchboard for company "  , loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"FAILED");
					result = MICSConstant.MICS_ERROR_NOT_AUTH;
				}
			} else {
				addActionError("User not logged in");
				Micslogger.log("Switchboard","Company",   "Fail to create switchboard for company "  , "", "NoLog","FAILED");
				result = MICSConstant.MICS_ERROR_NOT_LOGGEDIN;
			}
		} catch (Exception e) {
			logger.error("Failed to initialize Company Switchboard admin",e);
			addActionError("Failed to process your Company Switchboard administration request " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		logger.debug("result: " + result);
		return result;
	}
	public String addDestination(){
		logger.info("addDestination()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(switchboardCreate, loginWebUser.getRolekey())) {
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
						logger.debug("currCompany: " + currCompany);
						if(currCompany == null) throw new Exception ("Currently no company selected! please select company first.");
						this.companykey = currCompany.getCompanykey();
					} else {
						this.tselAdmin = false;
						this.companykey = loginWebUser.getCompanykey();
					}
					
					this.annoList = micsCommonSessionHandler.getCompanyAnno(companykey);
					logger.debug("annoList: " + annoList);
					session.put(MICSConstant.MICS_SESSION_ANNOLIST, annoList);
					this.switchList = micsCommonSessionHandler.getSwitchboardTypes();
					session.put(MICSConstant.MICS_SESSION_SWITCHLIST, switchList);

					//start - get menus and submenus from session
					this.switchboard = (Switchboard) session.get(MICSConstant.MICS_SESSION_SWITCHBOARD);
					session.remove(MICSConstant.MICS_SESSION_SWITCHBOARD);
					this.switchboardmenus = (List<Switchboardmenu>) session.get(MICSConstant.MICS_SESSION_SWITCHMENU);
					session.remove(MICSConstant.MICS_SESSION_SWITCHMENU);
					this.switchboardsubmenus = (List<List<Switchboardsubmenu>>) session.get(MICSConstant.MICS_SESSION_SWITCHSUBMENU);
					session.remove(MICSConstant.MICS_SESSION_SWITCHSUBMENU);
					
					for(int i=this.switchboardmenus.size()-1; i>=0; i--) {
						if(this.switchboardmenus.get(i).getMenutype() == null || this.switchboardmenus.get(i).getMenutype().intValue() == 0) {
							this.switchboardmenus.remove(i);
							this.switchboardsubmenus.remove(i);
						}
						else {
							break;
						}
					}
					
					for(int i=0; i<this.switchboardsubmenus.size(); i++) {
						List<Switchboardsubmenu> submenuList = this.switchboardsubmenus.get(i);
						for(int j=submenuList.size()-1; j>=1; j--) {
							if(submenuList.get(j).getDestinationnumber() == null || "".equals(submenuList.get(j).getDestinationnumber().trim())) {
								if(i == this.menuIndex && j <= this.submenuIndex) {
									//submenuList.get(j).setDestinationnumber(" ");
								}
								else {
									submenuList.remove(j);
								}
							}
							else {
								break;
							}
						}
						for(Switchboardsubmenu onessm : submenuList) {
							if(onessm.getDestinationnumber() == null || "".equals(onessm.getDestinationnumber())) {
								onessm.setDestinationnumber(" ");
							}
						}
					}
					//end - get menus and submenus from session

					//Set Curr Destination
					for (Iterator iterMember = searchMemberRes.iterator(); iterMember
							.hasNext();) {
						AddUserRequest currAddMember = (AddUserRequest) iterMember.next();
						logger.debug("Member " + currAddMember.getMicsUserKey() + " - " + currAddMember.getMicsFirstName() +","+ currAddMember.getMicsLastName()+" add " + currAddMember.isSelected());
						if (currAddMember.isSelected()) {
							if(this.destMenuOrSubmenu == 1 ) { // 1: menu
								this.switchboardmenus.get(this.menuIndex).setDestinationnumber(currAddMember.getMicsPubNumber());
							}
							else { // 2: submenu
								this.switchboardsubmenus.get(this.menuIndex).get(this.submenuIndex).setDestinationnumber(currAddMember.getMicsPubNumber());
							}
						}
					}
					
					session.remove(MICSConstant.MICS_SESSION_EMPLOYEERESULT);
					
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
			logger.error("Failed to search switchboard destination",e);
			addActionError("Failed to search switchboard destination" + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		logger.debug("result: " + result);
		return result;
	}
	
	public String searchDestination(){
		logger.info("searchDestination()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(switchboardCreate, loginWebUser.getRolekey())) {
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
						logger.debug("currCompany: " + currCompany);
						if(currCompany == null) throw new Exception ("Currently no company selected! please select company first.");
						this.companykey = currCompany.getCompanykey();
					} else {
						this.tselAdmin = false;
						this.companykey = loginWebUser.getCompanykey();
					}
					
					//start - reconstruct menus and submenus
					if(this.firstSearchDest) {
						if(this.getDestMenuOrSubmenu() != 0) {
							this.switchboardsubmenus = new ArrayList<List<Switchboardsubmenu>>();
							
							List<SwitchboardEntry> switchboardEntries = new ArrayList<SwitchboardEntry>();
							List<Switchboardmenu> deleteMenu = new ArrayList<Switchboardmenu>();
							List<Switchboardsubmenu> deleteSubmenu = new ArrayList<Switchboardsubmenu>();
							Iterator<Switchboardmenu> itMenus = this.switchboardmenus.iterator();
							Iterator<Switchboardsubmenu> itNewsubmenus = this.newsubmenus.iterator();
							for(int i=0; i<9; i++) {
								List<Switchboardsubmenu> switchboardsubmenuList = new ArrayList<Switchboardsubmenu>();
								for(int j=0; j<9; j++) {
									switchboardsubmenuList.add(itNewsubmenus.next());
								}
								SwitchboardEntry switchboardEntry = new SwitchboardEntry();
								switchboardEntry.setMenu(itMenus.next());
								switchboardEntry.setSubMenu(switchboardsubmenuList);
								switchboardEntries.add(switchboardEntry);
								
								this.switchboardsubmenus.add(switchboardsubmenuList);
							}
							
							this.switchboard.setCompanykey(this.companykey);
							this.switchboard.setUpdateuser(loginWebUser.getWebusername());
							this.switchboard.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
							
							List<SwitchboardEntry> copySwitchboardEntries = new ArrayList<SwitchboardEntry>(switchboardEntries);
							for(int i=switchboardEntries.size()-1; i>=0; i--) {
								SwitchboardEntry aEntry = switchboardEntries.get(i);
								Switchboardmenu amenu = aEntry.getMenu();
								if(amenu.getMenutype() == null || amenu.getMenutype().intValue() == 0){
									copySwitchboardEntries.remove(aEntry);
									if(amenu.getSwitchboardmenukey() != null && !"".equals(amenu.getSwitchboardmenukey())) {
										deleteMenu.add(amenu);
										if(aEntry.getSubMenu()!=null && aEntry.getSubMenu().size() > 0) {
											for(Switchboardsubmenu asubmenu : (List<Switchboardsubmenu>) aEntry.getSubMenu()) {
												if(asubmenu.getSwitchboardsubmenukey() != null && !"".equals(asubmenu.getSwitchboardsubmenukey())) {
													deleteSubmenu.add(asubmenu);
												}
											}
										}
									}
								}
								else {
									break;
								}
							}
							for(SwitchboardEntry aEntry : copySwitchboardEntries) {
								Switchboardmenu amenu = aEntry.getMenu();
								
								amenu.setSwitchboardkey(this.switchboard.getSwitchboardkey());
								amenu.setUpdateuser(loginWebUser.getWebusername());
								amenu.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
								
								List<Switchboardsubmenu> switchboardsubmenuList = aEntry.getSubMenu();
								List<Switchboardsubmenu> copySubmenuList = new ArrayList<Switchboardsubmenu>(switchboardsubmenuList);
								for(int i=switchboardsubmenuList.size()-1; i>=0; i--) {
									Switchboardsubmenu asubmenu = switchboardsubmenuList.get(i);
									if(asubmenu.getDestinationnumber() == null || "".equals(asubmenu.getDestinationnumber().trim())) {
										copySubmenuList.remove(asubmenu);
										if(asubmenu.getSwitchboardsubmenukey() != null && !"".equals(asubmenu.getSwitchboardsubmenukey())) {
											deleteSubmenu.add(asubmenu);
										}
									}
									else {
										break;
									}
								}
								for(Switchboardsubmenu asubmenu : copySubmenuList) {
									asubmenu.setSwitchboardmenukey(amenu.getSwitchboardmenukey());
									asubmenu.setUpdateuser(loginWebUser.getWebusername());
									asubmenu.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
								}
								aEntry.setSubMenu(copySubmenuList);
							}
							switchboardEntries = copySwitchboardEntries;
							
							session.put(MICSConstant.MICS_SESSION_SWITCHBOARD, this.switchboard);
							session.put(MICSConstant.MICS_SESSION_SWITCHMENU, this.switchboardmenus);
							session.put(MICSConstant.MICS_SESSION_SWITCHSUBMENU, this.switchboardsubmenus);
						}
					}
					//end - reconstruct menus and submenus

					if(this.firstSearchDest) {
						//Check Curr Destination
						if(this.destMenuOrSubmenu == 1 ) { // 1: menu
							this.currDestination = this.switchboardmenus.get(this.menuIndex).getDestinationnumber();
						}
						else { // 2: submenu
							this.currDestination = this.switchboardsubmenus.get(this.menuIndex).get(this.submenuIndex).getDestinationnumber();
						}
					}
					
					searchMemberRes = new ArrayList<AddUserRequest>();
					if(this.firstSearchDest) {
						addActionMessage ("Please enter search criteria");
					}
					else if ((this.searchFirstName == null || "".equals(this.searchFirstName.trim())) && 
							(this.searchLastName == null || "".equals(this.searchLastName.trim())) &&
							(this.searchPrivNumber == null || "".equals(this.searchPrivNumber.trim())) &&
							(this.searchPubNumber == null || "".equals(this.searchPubNumber.trim())) 
						) {
						addActionMessage ("Please enter search criteria");
					}
					else {
						employeeList = micsCommonSessionHandler.getEmployeesByCriteriaCompany(this.searchFirstName, this.searchLastName,this.searchPrivNumber,this.searchPubNumber, this.companykey);
						
						String removeDoublePubNumber = "";
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
							
							if (this.currDestination != null && !this.currDestination.trim().equals("")) {
								if (currMicsPubNumber.equals(this.currDestination)){
									currAddUserReq.setSelected(true);
								}
							}
							if(!removeDoublePubNumber.equals(currAddUserReq.getMicsPubNumber())) {
								searchMemberRes.add(currAddUserReq);
								removeDoublePubNumber = currAddUserReq.getMicsPubNumber();
							}
						}
					}
					session.put(MICSConstant.MICS_SESSION_EMPLOYEERESULT, searchMemberRes);
					
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
			logger.error("Failed to search switchboard destination",e);
			addActionError("Failed to search switchboard destination" + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		logger.debug("result: " + result);
		return result;
	}
	
	public void setSession(Map<String, Object> session) {
		this.session = session;

	}
	
	/**
	 * @return the switchboard
	 */
	public Switchboard getSwitchboard() {
		return switchboard;
	}


	/**
	 * @param switchboard the switchboard to set
	 */
	public void setSwitchboard(Switchboard switchboard) {
		this.switchboard = switchboard;
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
	 * @return the switchboardkey
	 */
	public String getSwitchboardkey() {
		return switchboardkey;
	}


	/**
	 * @param switchboardkey the switchboardkey to set
	 */
	public void setSwitchboardkey(String switchboardkey) {
		this.switchboardkey = switchboardkey;
	}


	/**
	 * @return the switchboards
	 */
	public void setSwitchboards(List<Switchboard> switchboards) {
		this.switchboards = switchboards;
	}

	/**
	 * @param switchboards the switchboards to set
	 */
	public List<Switchboard> getSwitchboards() {
		return switchboards;
	}

	/**
	 * @return the switchboardmenus
	 */
	public List<Switchboardmenu> getSwitchboardmenus() {
		return switchboardmenus;
	}


	/**
	 * @param switchboardmenus the switchboardmenus to set
	 */
	public void setSwitchboardmenus(List<Switchboardmenu> switchboardmenus) {
		this.switchboardmenus = switchboardmenus;
	}


	/**
	 * @return the switchboardsubmenus
	 */
	public List<List<Switchboardsubmenu>> getSwitchboardsubmenus() {
		return switchboardsubmenus;
	}


	/**
	 * @param switchboardsubmenus the switchboardsubmenus to set
	 */
	public void setSwitchboardsubmenus(
			List<List<Switchboardsubmenu>> switchboardsubmenus) {
		this.switchboardsubmenus = switchboardsubmenus;
	}


	/**
	 * @return the newsubmenus
	 */
	public List<Switchboardsubmenu> getNewsubmenus() {
		return newsubmenus;
	}


	/**
	 * @param newsubmenus the newsubmenus to set
	 */
	public void setNewsubmenus(List<Switchboardsubmenu> newsubmenus) {
		this.newsubmenus = newsubmenus;
	}


	/**
	 * @return the annoList
	 */
	public List<Announcement> getAnnoList() {
		return annoList;
	}


	/**
	 * @param annoList the annoList to set
	 */
	public void setAnnoList(List<Announcement> annoList) {
		this.annoList = annoList;
	}


	/**
	 * @return the switchList
	 */
	public List<SwitchboardType> getSwitchList() {
		return switchList;
	}


	/**
	 * @param switchList the switchList to set
	 */
	public void setSwitchList(List<SwitchboardType> switchList) {
		this.switchList = switchList;
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
	 * @return the switchboardCreate
	 */
	public String getSwitchboardCreate() {
		return switchboardCreate;
	}

	/**
	 * @param switchboardCreate the switchboardCreate to set
	 */
	public void setSwitchboardCreate(String switchboardCreate) {
		this.switchboardCreate = switchboardCreate;
	}

	/**
	 * @return the switchboardView
	 */
	public String getSwitchboardView() {
		return switchboardView;
	}

	/**
	 * @param switchboardView the switchboardView to set
	 */
	public void setSwitchboardView(String switchboardView) {
		this.switchboardView = switchboardView;
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
	 * @return the destMenuOrSubmenu
	 */
	public int getDestMenuOrSubmenu() {
		return destMenuOrSubmenu;
	}

	/**
	 * @param destMenuOrSubmenu the destMenuOrSubmenu to set
	 */
	public void setDestMenuOrSubmenu(int destMenuOrSubmenu) {
		this.destMenuOrSubmenu = destMenuOrSubmenu;
	}

	/**
	 * @return the menuIndex
	 */
	public int getMenuIndex() {
		return menuIndex;
	}

	/**
	 * @param menuIndex the menuIndex to set
	 */
	public void setMenuIndex(int menuIndex) {
		this.menuIndex = menuIndex;
	}
	
	/**
	 * @return the submenuIndex
	 */
	public int getSubmenuIndex() {
		return submenuIndex;
	}

	/**
	 * @param submenuIndex the submenuIndex to set
	 */
	public void setSubmenuIndex(int submenuIndex) {
		this.submenuIndex = submenuIndex;
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
	 * @return the firstSearchDest
	 */
	public boolean isFirstSearchDest() {
		return firstSearchDest;
	}

	/**
	 * @param firstSearchDest the firstSearchDest to set
	 */
	public void setFirstSearchDest(boolean firstSearchDest) {
		this.firstSearchDest = firstSearchDest;
	}

	/**
	 * @return the currDestination
	 */
	public String getCurrDestination() {
		return currDestination;
	}

	/**
	 * @param currDestination the currDestination to set
	 */
	public void setCurrDestination(String currDestination) {
		this.currDestination = currDestination;
	}

	/**
	 * @return the session
	 */
	public Map<String, Object> getSession() {
		return session;
	}



	private Map<String, Object> session;
	
	private String companykey;
	private String switchboardkey;
	
	private List<Announcement> annoList;
	private List<SwitchboardType> switchList;
	
	private List<Switchboard> switchboards;
	private Switchboard switchboard;
	private List<Switchboardmenu> switchboardmenus;
	private List<List<Switchboardsubmenu>> switchboardsubmenus;
	private List<Switchboardsubmenu> newsubmenus;
	private boolean tselAdmin;
	
	private boolean deleteAllowed;
	private int destMenuOrSubmenu;
	private int menuIndex;
	private int submenuIndex;
	private List employeeList;
	private String searchFirstName;
	private String searchLastName;
	private String searchPubNumber;
	private String searchPrivNumber;
	private List<AddUserRequest> searchMemberRes;
	private boolean firstSearchDest = true;
	private String currDestination;
	
	private String switchboardCreate = "2017050105";
	private String switchboardView = "2017050108"; //TODO: define the key
	private static final Logger logger = Logger.getLogger(SwitchboardListAction.class);

	@Override
	public void prepare() throws Exception {
		//MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
	}
}
