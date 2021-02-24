package com.nsn.telkomsel.mics.action;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.nsn.telkomsel.mics.MICSException;
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

public class SwitchboardAction extends ActionSupport implements SessionAware,Preparable {

	

	private static final long serialVersionUID = -2158018909170181656L;
	
	@SuppressWarnings("unchecked")
	public String initCreate(){
		logger.info("initCreate()");
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
					//Check if switchboard already created
					Switchboard currSwitchboardDB = micsCommonSessionHandler.getCompanySwitchboard(this.companykey);
					if (currSwitchboardDB != null) {
						//Set to field
						this.switchboardKey = currSwitchboardDB.getSwitchboardkey();
						this.switchboardName = currSwitchboardDB.getSwitchboardname();
						this.switchboardPublicNumber = currSwitchboardDB.getPublicnumber();
						this.switchboardPrivateNumber = currSwitchboardDB.getPrivatenumber();
						if (currSwitchboardDB.getAnnoid() != null) {
							this.annoId = currSwitchboardDB.getAnnoid().intValue();
						}
						
						//Get Switchboard menu
						List currSwitcboardmenus = micsCommonSessionHandler.getSwitchboardmenus(currSwitchboardDB.getSwitchboardkey());
						if (currSwitcboardmenus!= null && !currSwitcboardmenus.isEmpty()) {
							
							for (Iterator iterator = currSwitcboardmenus
									.iterator(); iterator.hasNext();) {
								Switchboardmenu currSBMenu = (Switchboardmenu) iterator.next();
								int intCurrSbMenu = currSBMenu.getDialdigit().intValue();
								logger.debug("intCurrSbMenu: " + intCurrSbMenu);
								switch (intCurrSbMenu) {
								case 1:
									if (currSBMenu.getAnnoid() !=null) {
										this.firstLevelAnno1 = currSBMenu.getAnnoid().intValue();
									}
									
									this.switchboardmenukey1 = currSBMenu.getSwitchboardmenukey();
									this.firstLevelSelect1 = currSBMenu.getMenutype().intValue();
									if (this.firstLevelSelect1 == 1) {
										//Get Sub Menu
										List currSwitchboardSubmenus = micsCommonSessionHandler.getSwitchboardsubmenus(currSBMenu.getSwitchboardmenukey());
										if (currSwitchboardSubmenus != null && !currSwitchboardSubmenus.isEmpty()) {
											int subMenuIdx = 1;
											for (Iterator iterSubMenus = currSwitchboardSubmenus
													.iterator(); iterSubMenus
													.hasNext();) {
												Switchboardsubmenu currSubmenu = (Switchboardsubmenu) iterSubMenus
														.next();
												
												switch (currSubmenu.getDialdigit().intValue()) {
												case 1:
													if (currSubmenu.getDestinationnumber() != null && !currSubmenu.getDestinationnumber().trim().equals("")) {
														this.dest11 = currSubmenu.getDestinationnumber();
														this.submenu1LevelSelect1 = 2;
													} else{
														this.submenu1LevelSelect1 = 0;
													}
													this.switchboardsubmenukey11 = currSubmenu.getSwitchboardsubmenukey();
													break;
												case 2:
													if (currSubmenu.getDestinationnumber() != null && !currSubmenu.getDestinationnumber().trim().equals("")) {
														this.dest12 = currSubmenu.getDestinationnumber();
														this.submenu1LevelSelect2 = 2;
													} else{
														this.submenu1LevelSelect2 = 0;
													}
													this.switchboardsubmenukey12 = currSubmenu.getSwitchboardsubmenukey();
													break;
												case 3:
													if (currSubmenu.getDestinationnumber() != null && !currSubmenu.getDestinationnumber().trim().equals("")) {
														this.dest13 = currSubmenu.getDestinationnumber();
														this.submenu1LevelSelect3 = 2;
													} else{
														this.submenu1LevelSelect3 = 0;
													}
													this.switchboardsubmenukey13 = currSubmenu.getSwitchboardsubmenukey();
													break;
												
												}
												
											}
										}
									}
									if (this.firstLevelSelect1 == 2) {
										this.dest1 = currSBMenu.getDestinationnumber();
									}
									break;
								case 2:
									if (currSBMenu.getAnnoid() !=null) {
										this.firstLevelAnno2 = currSBMenu.getAnnoid().intValue();
									}
									this.switchboardmenukey2 = currSBMenu.getSwitchboardmenukey();
									this.firstLevelSelect2 = currSBMenu.getMenutype().intValue();
									if (this.firstLevelSelect2 == 1) {
										//Get Sub Menu
										List currSwitchboardSubmenus = micsCommonSessionHandler.getSwitchboardsubmenus(currSBMenu.getSwitchboardmenukey());
										if (currSwitchboardSubmenus != null && !currSwitchboardSubmenus.isEmpty()) {
											int subMenuIdx = 1;
											for (Iterator iterSubMenus = currSwitchboardSubmenus
													.iterator(); iterSubMenus
													.hasNext();) {
												Switchboardsubmenu currSubmenu = (Switchboardsubmenu) iterSubMenus
														.next();
												switch (currSubmenu.getDialdigit().intValue()) {
												case 1:
													if (currSubmenu.getDestinationnumber() != null && !currSubmenu.getDestinationnumber().trim().equals("")) {
														this.dest21 = currSubmenu.getDestinationnumber();
														this.submenu2LevelSelect1 = 2;
													} else{
														this.submenu2LevelSelect1 = 0;
													}
													this.switchboardsubmenukey21 = currSubmenu.getSwitchboardsubmenukey();
													break;
												case 2:
													if (currSubmenu.getDestinationnumber() != null && !currSubmenu.getDestinationnumber().trim().equals("")) {
														this.dest22 = currSubmenu.getDestinationnumber();
														this.submenu2LevelSelect2 = 2;
													} else{
														this.submenu2LevelSelect2 = 0;
													}
													this.switchboardsubmenukey22 = currSubmenu.getSwitchboardsubmenukey();
													break;
												case 3:
													if (currSubmenu.getDestinationnumber() != null && !currSubmenu.getDestinationnumber().trim().equals("")) {
														this.dest23 = currSubmenu.getDestinationnumber();
														this.submenu2LevelSelect3 = 2;
													} else{
														this.submenu2LevelSelect3 = 0;
													}
													this.switchboardsubmenukey23 = currSubmenu.getSwitchboardsubmenukey();
													break;
												
												}
												
											}
										}
									}
									if (this.firstLevelSelect2 == 2) {
										this.dest2 = currSBMenu.getDestinationnumber();
									}
									break;
								case 3:
									if (currSBMenu.getAnnoid() !=null) {
										this.firstLevelAnno3 = currSBMenu.getAnnoid().intValue();
									}
									this.switchboardmenukey3 = currSBMenu.getSwitchboardmenukey();
									this.firstLevelSelect3 = currSBMenu.getMenutype().intValue();
									if (this.firstLevelSelect3 == 1) {
										//Get Sub Menu
										List currSwitchboardSubmenus = micsCommonSessionHandler.getSwitchboardsubmenus(currSBMenu.getSwitchboardmenukey());
										if (currSwitchboardSubmenus != null && !currSwitchboardSubmenus.isEmpty()) {
											int subMenuIdx = 1;
											for (Iterator iterSubMenus = currSwitchboardSubmenus
													.iterator(); iterSubMenus
													.hasNext();) {
												Switchboardsubmenu currSubmenu = (Switchboardsubmenu) iterSubMenus
														.next();
												switch (currSubmenu.getDialdigit().intValue()) {
												case 1:
													if (currSubmenu.getDestinationnumber() != null && !currSubmenu.getDestinationnumber().trim().equals("")) {
														this.dest31 = currSubmenu.getDestinationnumber();
														this.submenu3LevelSelect1 = 2;
													} else{
														this.submenu3LevelSelect1 = 0;
													}
													this.switchboardsubmenukey31 = currSubmenu.getSwitchboardsubmenukey();
													break;
												case 2:
													if (currSubmenu.getDestinationnumber() != null && !currSubmenu.getDestinationnumber().trim().equals("")) {
														this.dest32 = currSubmenu.getDestinationnumber();
														this.submenu3LevelSelect2 = 2;
													} else{
														this.submenu3LevelSelect2 = 0;
													}
													this.switchboardsubmenukey32 = currSubmenu.getSwitchboardsubmenukey();
													break;
												case 3:
													if (currSubmenu.getDestinationnumber() != null && !currSubmenu.getDestinationnumber().trim().equals("")) {
														this.dest33 = currSubmenu.getDestinationnumber();
														this.submenu3LevelSelect3 = 2;
													} else{
														this.submenu3LevelSelect3 = 0;
													}
													this.switchboardsubmenukey33 = currSubmenu.getSwitchboardsubmenukey();
													break;
												
												}
												
											}
										}
									}
									if (this.firstLevelSelect3 == 2) {
										this.dest3 = currSBMenu.getDestinationnumber();
									}
									break;
								case 4:
									if (currSBMenu.getAnnoid() !=null) {
										this.firstLevelAnno4 = currSBMenu.getAnnoid().intValue();
									}
									
									this.switchboardmenukey4 = currSBMenu.getSwitchboardmenukey();
									this.firstLevelSelect4 = currSBMenu.getMenutype().intValue();
									if (this.firstLevelSelect4 == 1) {
										//Get Sub Menu
										List currSwitchboardSubmenus = micsCommonSessionHandler.getSwitchboardsubmenus(currSBMenu.getSwitchboardmenukey());
										if (currSwitchboardSubmenus != null && !currSwitchboardSubmenus.isEmpty()) {
											int subMenuIdx = 1;
											for (Iterator iterSubMenus = currSwitchboardSubmenus
													.iterator(); iterSubMenus
													.hasNext();) {
												Switchboardsubmenu currSubmenu = (Switchboardsubmenu) iterSubMenus
														.next();
												switch (currSubmenu.getDialdigit().intValue()) {
												case 1:
													if (currSubmenu.getDestinationnumber() != null && !currSubmenu.getDestinationnumber().trim().equals("")) {
														this.dest41 = currSubmenu.getDestinationnumber();
														this.submenu4LevelSelect1 = 2;
													} else{
														this.submenu4LevelSelect1 = 0;
													}
													this.switchboardsubmenukey41 = currSubmenu.getSwitchboardsubmenukey();
													break;
												case 2:
													if (currSubmenu.getDestinationnumber() != null && !currSubmenu.getDestinationnumber().trim().equals("")) {
														this.dest42 = currSubmenu.getDestinationnumber();
														this.submenu4LevelSelect2 = 2;
													} else{
														this.submenu4LevelSelect2 = 0;
													}
													this.switchboardsubmenukey42 = currSubmenu.getSwitchboardsubmenukey();
													break;
												case 3:
													if (currSubmenu.getDestinationnumber() != null && !currSubmenu.getDestinationnumber().trim().equals("")) {
														this.dest43 = currSubmenu.getDestinationnumber();
														this.submenu4LevelSelect3 = 2;
													} else{
														this.submenu4LevelSelect3 = 0;
													}
													this.switchboardsubmenukey43 = currSubmenu.getSwitchboardsubmenukey();
													break;
												
												}
												
											}
										}
									}
									if (this.firstLevelSelect4 == 2) {
										this.dest4 = currSBMenu.getDestinationnumber();
									}
									break;
								case 5:
									if (currSBMenu.getAnnoid() !=null) {
										this.firstLevelAnno5 = currSBMenu.getAnnoid().intValue();
									}
									
									this.switchboardmenukey5 = currSBMenu.getSwitchboardmenukey();
									this.firstLevelSelect5 = currSBMenu.getMenutype().intValue();
									if (this.firstLevelSelect5 == 1) {
										//Get Sub Menu
										List currSwitchboardSubmenus = micsCommonSessionHandler.getSwitchboardsubmenus(currSBMenu.getSwitchboardmenukey());
										if (currSwitchboardSubmenus != null && !currSwitchboardSubmenus.isEmpty()) {
											int subMenuIdx = 1;
											for (Iterator iterSubMenus = currSwitchboardSubmenus
													.iterator(); iterSubMenus
													.hasNext();) {
												Switchboardsubmenu currSubmenu = (Switchboardsubmenu) iterSubMenus
														.next();
												switch (currSubmenu.getDialdigit().intValue()) {
												case 1:
													if (currSubmenu.getDestinationnumber() != null && !currSubmenu.getDestinationnumber().trim().equals("")) {
														this.dest51 = currSubmenu.getDestinationnumber();
														this.submenu5LevelSelect1 = 2;
													} else{
														this.submenu5LevelSelect1 = 0;
													}
													this.switchboardsubmenukey51 = currSubmenu.getSwitchboardsubmenukey();
													break;
												case 2:
													if (currSubmenu.getDestinationnumber() != null && !currSubmenu.getDestinationnumber().trim().equals("")) {
														this.dest52 = currSubmenu.getDestinationnumber();
														this.submenu5LevelSelect2 = 2;
													} else{
														this.submenu5LevelSelect2 = 0;
													}
													this.switchboardsubmenukey52 = currSubmenu.getSwitchboardsubmenukey();
													break;
												case 3:
													if (currSubmenu.getDestinationnumber() != null && !currSubmenu.getDestinationnumber().trim().equals("")) {
														this.dest53 = currSubmenu.getDestinationnumber();
														this.submenu5LevelSelect3 = 2;
													} else{
														this.submenu5LevelSelect3 = 0;
													}
													this.switchboardsubmenukey53 = currSubmenu.getSwitchboardsubmenukey();
													break;
												
												}
												
											}
										}
									}
									if (this.firstLevelSelect5 == 2) {
										this.dest5 = currSBMenu.getDestinationnumber();
									}
									break;

								}
								
							}
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
	public String create(){
		logger.info("create()");
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
					
					Switchboard newSwitchboard = new Switchboard();
					newSwitchboard.setCompanykey(this.companykey);
					newSwitchboard.setSwitchboardkey(this.switchboardKey);
					newSwitchboard.setSwitchboardname(this.switchboardName);
					newSwitchboard.setPublicnumber(this.switchboardPublicNumber);
					newSwitchboard.setPrivatenumber(this.switchboardPrivateNumber);
					newSwitchboard.setAnnoid(new BigDecimal(this.annoId));
					newSwitchboard.setUpdateuser(loginWebUser.getWebusername());
					newSwitchboard.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
					//Menu 1
					Switchboardmenu switchMenu1 = new Switchboardmenu();
					switchMenu1.setSwitchboardkey(newSwitchboard.getSwitchboardkey());
					switchMenu1.setSwitchboardmenukey(this.switchboardmenukey1);
					switchMenu1.setSwitchboardmenuname("Menu 1");
					switchMenu1.setMenutype(new BigDecimal(this.firstLevelSelect1));
					switchMenu1.setDialdigit(new BigDecimal(1));
					//Check Menu Type Inactive, SubMenu, Destination
					List submenus1 = null;
					if (this.firstLevelSelect1 == 0) {
						switchMenu1.setDestinationnumber("");
					}
					if (this.firstLevelSelect1 == 1) {
						submenus1 = new ArrayList();
						switchMenu1.setAnnoid(new BigDecimal(this.firstLevelAnno1));
						switchMenu1.setDestinationnumber("");
						//Create Sub Menu
						Switchboardsubmenu submenu11 = new Switchboardsubmenu();
						submenu11.setSwitchboardsubmenukey(this.switchboardsubmenukey11);
						submenu11.setSwitchboardmenukey(this.switchboardmenukey1);
						submenu11.setDescription("Sub Menu 1.1");
						if (submenu1LevelSelect1 == 0) {
							submenu11.setDestinationnumber("");
						} else {
							if (this.dest11 == null || this.dest11.trim().equals("")) {
								throw new MICSException("Failed to save switchboard Destination is not set!");
							}
							submenu11.setDestinationnumber(this.dest11);
						}
						submenu11.setDialdigit(new BigDecimal(1));
						submenu11.setSwitchboardsubmenuname("Sub Menu 1");
						submenu11.setUpdateuser(loginWebUser.getWebusername());
						submenu11.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
						
						Switchboardsubmenu submenu12 = new Switchboardsubmenu();
						submenu12.setSwitchboardsubmenukey(this.switchboardsubmenukey12);
						submenu12.setSwitchboardmenukey(this.switchboardmenukey1);
						submenu12.setDescription("Sub Menu 1.2");
						if (submenu1LevelSelect2 == 0) {
							submenu12.setDestinationnumber("");
						} else {
							if (this.dest12 == null || this.dest12.trim().equals("")) {
								throw new MICSException("Failed to save switchboard Destination is not set!");
							}
							submenu12.setDestinationnumber(this.dest12);
						}
						submenu12.setDialdigit(new BigDecimal(2));
						submenu12.setSwitchboardsubmenuname("Sub Menu 2");
						submenu12.setUpdateuser(loginWebUser.getWebusername());
						submenu12.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
						
						Switchboardsubmenu submenu13 = new Switchboardsubmenu();
						submenu13.setSwitchboardsubmenukey(this.switchboardsubmenukey13);
						submenu13.setSwitchboardmenukey(this.switchboardmenukey1);
						submenu13.setDescription("Sub Menu 1.3");
						if (submenu1LevelSelect3 == 0) {
							submenu13.setDestinationnumber("");
						} else {
							if (this.dest13 == null || this.dest13.trim().equals("")) {
								throw new MICSException("Failed to save switchboard Destination is not set!");
							}
							submenu13.setDestinationnumber(this.dest13);
						}
						submenu13.setDialdigit(new BigDecimal(3));
						submenu13.setSwitchboardsubmenuname("Sub Menu 3");
						submenu13.setUpdateuser(loginWebUser.getWebusername());
						submenu13.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
						
						submenus1.add(submenu11);
						submenus1.add(submenu12);
						submenus1.add(submenu13);
					}
					if (this.firstLevelSelect1 == 2) {
						if (this.dest1 == null || this.dest1.trim().equals("")) {
							throw new MICSException("Failed to save switchboard Destination is not set!");
						}
						switchMenu1.setDestinationnumber(this.dest1);
					}
					switchMenu1.setUpdateuser(newSwitchboard.getUpdateuser());
					
					SwitchboardEntry seMenu1 = new SwitchboardEntry();
					seMenu1.setMenu(switchMenu1);
					seMenu1.setSubMenu(submenus1);
					
					//Menu2
					
					Switchboardmenu switchMenu2 = new Switchboardmenu();
					switchMenu2.setSwitchboardkey(newSwitchboard.getSwitchboardkey());
					switchMenu2.setSwitchboardmenukey(this.switchboardmenukey2);
					switchMenu2.setMenutype(new BigDecimal(this.firstLevelSelect2));
					switchMenu2.setDialdigit(new BigDecimal(2));
					switchMenu2.setSwitchboardmenuname("Menu 2");
					//Check Menu Type Inactive, SubMenu, Destination
					List submenus2 = null;
					if (this.firstLevelSelect2 == 0) {
						switchMenu2.setDestinationnumber("");
					}
					if (this.firstLevelSelect2 == 1) {
						submenus2 = new ArrayList();
						switchMenu2.setDestinationnumber("");
						switchMenu2.setAnnoid(new BigDecimal(this.firstLevelAnno2));
						//Create Sub Menu
						Switchboardsubmenu submenu21 = new Switchboardsubmenu();
						submenu21.setSwitchboardsubmenukey(this.switchboardsubmenukey21);
						submenu21.setSwitchboardmenukey(this.switchboardmenukey2);
						submenu21.setDescription("Sub Menu 2.1");
						if (submenu2LevelSelect1 == 0) {
							submenu21.setDestinationnumber("");
						} else {
							if (this.dest21 == null || this.dest21.trim().equals("")) {
								throw new MICSException("Failed to save switchboard Destination is not set!");
							}
							submenu21.setDestinationnumber(this.dest21);
						}
						submenu21.setDialdigit(new BigDecimal(1));
						submenu21.setSwitchboardsubmenuname("Sub Menu 1");
						submenu21.setUpdateuser(loginWebUser.getWebusername());
						submenu21.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
						
						Switchboardsubmenu submenu22 = new Switchboardsubmenu();
						submenu22.setSwitchboardsubmenukey(this.switchboardsubmenukey22);
						submenu22.setSwitchboardmenukey(this.switchboardmenukey2);
						submenu22.setDescription("Sub Menu 2.2");
						if (submenu2LevelSelect2 == 0) {
							submenu22.setDestinationnumber("");
						} else {
							if (this.dest22 == null || this.dest22.trim().equals("")) {
								throw new MICSException("Failed to save switchboard Destination is not set!");
							}
							submenu22.setDestinationnumber(this.dest22);
						}
						submenu22.setDialdigit(new BigDecimal(2));
						submenu22.setSwitchboardsubmenuname("Sub Menu 2");
						submenu22.setUpdateuser(loginWebUser.getWebusername());
						submenu22.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
						
						Switchboardsubmenu submenu23 = new Switchboardsubmenu();
						submenu23.setSwitchboardsubmenukey(this.switchboardsubmenukey23);
						submenu23.setSwitchboardmenukey(this.switchboardmenukey2);
						submenu23.setDescription("Sub Menu 2.3");
						if (submenu2LevelSelect3 == 0) {
							submenu23.setDestinationnumber("");
						} else {
							if (this.dest23 == null || this.dest23.trim().equals("")) {
								throw new MICSException("Failed to save switchboard Destination is not set!");
							}
							submenu23.setDestinationnumber(this.dest23);
						}
						submenu23.setDialdigit(new BigDecimal(3));
						submenu23.setSwitchboardsubmenuname("Sub Menu 3");
						submenu23.setUpdateuser(loginWebUser.getWebusername());
						submenu23.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
						
						submenus2.add(submenu21);
						submenus2.add(submenu22);
						submenus2.add(submenu23);
					}
					if (firstLevelSelect2 == 2) {
						if (this.dest2 == null || this.dest2.trim().equals("")) {
							throw new MICSException("Failed to save switchboard Destination is not set!");
						}
						switchMenu2.setDestinationnumber(this.dest2);
					}
					
					switchMenu2.setUpdateuser(newSwitchboard.getUpdateuser());
					
					SwitchboardEntry seMenu2 = new SwitchboardEntry();
					seMenu2.setMenu(switchMenu2);
					seMenu2.setSubMenu(submenus2);
					
					//Menu 3
					
					Switchboardmenu switchMenu3 = new Switchboardmenu();
					switchMenu3.setSwitchboardkey(newSwitchboard.getSwitchboardkey());
					switchMenu3.setSwitchboardmenukey(this.switchboardmenukey3);
					switchMenu3.setMenutype(new BigDecimal(this.firstLevelSelect3));
					switchMenu3.setDialdigit(new BigDecimal(3));
					switchMenu3.setSwitchboardmenuname("Menu 3");
					//Check Menu Type Inactive, SubMenu, Destination
					List submenus3 = null;
					if (this.firstLevelSelect3 == 0) {
						switchMenu3.setDestinationnumber("");
					}
					if (this.firstLevelSelect3 == 1) {
						submenus3 = new ArrayList();
						switchMenu3.setAnnoid(new BigDecimal(this.firstLevelAnno3));
						switchMenu3.setDestinationnumber("");
						//Create Sub Menu
						Switchboardsubmenu submenu31 = new Switchboardsubmenu();
						submenu31.setSwitchboardsubmenukey(this.switchboardsubmenukey31);
						submenu31.setSwitchboardmenukey(this.switchboardmenukey3);
						submenu31.setDescription("Sub Menu 3.1");
						if (submenu3LevelSelect1 == 0) {
							submenu31.setDestinationnumber("");
						} else {
							if (this.dest31 == null || this.dest31.trim().equals("")) {
								throw new MICSException("Failed to save switchboard Destination is not set!");
							}
							submenu31.setDestinationnumber(this.dest31);
						}
						
						submenu31.setDialdigit(new BigDecimal(1));
						submenu31.setSwitchboardsubmenuname("Sub Menu 1");
						submenu31.setUpdateuser(loginWebUser.getWebusername());
						submenu31.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
						
						Switchboardsubmenu submenu32 = new Switchboardsubmenu();
						submenu32.setSwitchboardsubmenukey(this.switchboardsubmenukey32);
						submenu32.setSwitchboardmenukey(this.switchboardmenukey3);
						submenu32.setDescription("Sub Menu 3.2");
						if (submenu3LevelSelect2 == 0) {
							submenu32.setDestinationnumber("");
						} else {
							if (this.dest32 == null || this.dest32.trim().equals("")) {
								throw new MICSException("Failed to save switchboard Destination is not set!");
							}
							submenu32.setDestinationnumber(this.dest32);
						}
						submenu32.setDialdigit(new BigDecimal(2));
						submenu32.setSwitchboardsubmenuname("Sub Menu 2");
						submenu32.setUpdateuser(loginWebUser.getWebusername());
						submenu32.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
						
						Switchboardsubmenu submenu33 = new Switchboardsubmenu();
						submenu33.setSwitchboardsubmenukey(this.switchboardsubmenukey33);
						submenu33.setSwitchboardmenukey(this.switchboardmenukey3);
						submenu33.setDescription("Sub Menu 3.3");
						if (submenu3LevelSelect3 == 0) {
							submenu33.setDestinationnumber("");
						} else {
							if (this.dest33 == null || this.dest33.trim().equals("")) {
								throw new MICSException("Failed to save switchboard Destination is not set!");
							}
							submenu33.setDestinationnumber(this.dest33);
						}
						submenu33.setDialdigit(new BigDecimal(3));
						submenu33.setSwitchboardsubmenuname("Sub Menu 3");
						submenu33.setUpdateuser(loginWebUser.getWebusername());
						submenu33.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
						
						submenus3.add(submenu31);
						submenus3.add(submenu32);
						submenus3.add(submenu33);
					}
					if (this.firstLevelSelect3 == 2) {
						if (this.dest3 == null || this.dest3.trim().equals("")) {
							throw new MICSException("Failed to save switchboard Destination is not set!");
						}
						switchMenu3.setDestinationnumber(dest3);
					}
					
					switchMenu3.setUpdateuser(newSwitchboard.getUpdateuser());
					
					SwitchboardEntry seMenu3 = new SwitchboardEntry();
					seMenu3.setMenu(switchMenu3);
					seMenu3.setSubMenu(submenus3);
					
					//Menu 4
					
					Switchboardmenu switchMenu4 = new Switchboardmenu();
					switchMenu4.setSwitchboardkey(newSwitchboard.getSwitchboardkey());
					switchMenu4.setSwitchboardmenukey(this.switchboardmenukey4);
					switchMenu4.setMenutype(new BigDecimal(this.firstLevelSelect4));
					switchMenu4.setDialdigit(new BigDecimal(4));
					switchMenu4.setSwitchboardmenuname("Menu 4");
					//Check Menu Type Inactive, SubMenu, Destination
					List submenus4 = null;
					if (this.firstLevelSelect4 == 0) {
						switchMenu4.setDestinationnumber("");
					}
					if (firstLevelSelect4 == 1) {
						submenus4 = new ArrayList();
						switchMenu4.setAnnoid(new BigDecimal(this.firstLevelAnno4));
						switchMenu4.setDestinationnumber("");
						//Create Sub Menu
						Switchboardsubmenu submenu41 = new Switchboardsubmenu();
						submenu41.setSwitchboardsubmenukey(this.switchboardsubmenukey41);
						submenu41.setSwitchboardmenukey(this.switchboardmenukey4);
						submenu41.setDescription("Sub Menu 4.1");
						if (submenu4LevelSelect1 == 0) {
							submenu41.setDestinationnumber("");
						} else {
							if (this.dest41 == null || this.dest41.trim().equals("")) {
								throw new MICSException("Failed to save switchboard Destination is not set!");
							}
							submenu41.setDestinationnumber(this.dest41);
						}
						submenu41.setDialdigit(new BigDecimal(1));
						submenu41.setSwitchboardsubmenuname("Sub Menu 1");
						submenu41.setUpdateuser(loginWebUser.getWebusername());
						submenu41.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
						
						Switchboardsubmenu submenu42 = new Switchboardsubmenu();
						submenu42.setSwitchboardsubmenukey(this.switchboardsubmenukey42);
						submenu42.setSwitchboardmenukey(this.switchboardmenukey4);
						submenu42.setDescription("Sub Menu 4.2");
						if (submenu4LevelSelect2 == 0) {
							submenu42.setDestinationnumber("");
						} else {
							if (this.dest42 == null || this.dest42.trim().equals("")) {
								throw new MICSException("Failed to save switchboard Destination is not set!");
							}
							submenu42.setDestinationnumber(this.dest42);
						}
						submenu42.setDialdigit(new BigDecimal(2));
						submenu42.setSwitchboardsubmenuname("Sub Menu 2");
						submenu42.setUpdateuser(loginWebUser.getWebusername());
						submenu42.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
						
						Switchboardsubmenu submenu43 = new Switchboardsubmenu();
						submenu43.setSwitchboardsubmenukey(this.switchboardsubmenukey43);
						submenu43.setSwitchboardmenukey(this.switchboardmenukey4);
						submenu43.setDescription("Sub Menu 4.3");
						if (submenu4LevelSelect3 == 0) {
							submenu43.setDestinationnumber("");
						} else {
							if (this.dest43 == null || this.dest43.trim().equals("")) {
								throw new MICSException("Failed to save switchboard Destination is not set!");
							}
							submenu43.setDestinationnumber(this.dest43);
						}
						submenu43.setDialdigit(new BigDecimal(3));
						submenu43.setSwitchboardsubmenuname("Sub Menu 3");
						submenu43.setUpdateuser(loginWebUser.getWebusername());
						submenu43.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
						
						submenus4.add(submenu41);
						submenus4.add(submenu42);
						submenus4.add(submenu43);
					}
					if (firstLevelSelect4 == 2) {
						if (this.dest4 == null || this.dest4.trim().equals("")) {
							throw new MICSException("Failed to save switchboard Destination is not set!");
						}
						switchMenu4.setDestinationnumber(dest4);
					}
					
					switchMenu4.setUpdateuser(newSwitchboard.getUpdateuser());
					
					SwitchboardEntry seMenu4 = new SwitchboardEntry();
					seMenu4.setMenu(switchMenu4);
					seMenu4.setSubMenu(submenus4);
					
					//Menu 5
					Switchboardmenu switchMenu5 = new Switchboardmenu();
					switchMenu5.setSwitchboardkey(newSwitchboard.getSwitchboardkey());
					switchMenu5.setSwitchboardmenukey(this.switchboardmenukey5);
					switchMenu5.setMenutype(new BigDecimal(this.firstLevelSelect5));
					switchMenu5.setDialdigit(new BigDecimal(5));
					switchMenu5.setSwitchboardmenuname("Menu 5");
					//Check Menu Type Inactive, SubMenu, Destination
					List submenus5 = null;
					if (this.firstLevelSelect5 == 0) {
						switchMenu5.setDestinationnumber("");
					}
					if (this.firstLevelSelect5 == 1) {
						submenus5 = new ArrayList();
						switchMenu5.setAnnoid(new BigDecimal(firstLevelAnno5));
						switchMenu5.setDestinationnumber("");
						//Create Sub Menu
						Switchboardsubmenu submenu51 = new Switchboardsubmenu();
						submenu51.setSwitchboardsubmenukey(this.switchboardsubmenukey51);
						submenu51.setSwitchboardmenukey(this.switchboardmenukey5);
						submenu51.setDescription("Sub Menu 5.1");
						if (submenu5LevelSelect1 == 0) {
							submenu51.setDestinationnumber("");
						} else {
							if (this.dest51 == null || this.dest51.trim().equals("")) {
								throw new MICSException("Failed to save switchboard Destination is not set!");
							}
							submenu51.setDestinationnumber(this.dest51);
						}
						submenu51.setDestinationnumber(this.dest51);
						submenu51.setDialdigit(new BigDecimal(1));
						submenu51.setSwitchboardsubmenuname("Sub Menu 1");
						submenu51.setUpdateuser(loginWebUser.getWebusername());
						submenu51.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
						
						Switchboardsubmenu submenu52 = new Switchboardsubmenu();
						submenu52.setSwitchboardsubmenukey(this.switchboardsubmenukey52);
						submenu52.setSwitchboardmenukey(this.switchboardmenukey5);
						submenu52.setDescription("Sub Menu 5.2");
						if (submenu5LevelSelect2 == 0) {
							submenu52.setDestinationnumber("");
						} else {
							if (this.dest52 == null || this.dest52.trim().equals("")) {
								throw new MICSException("Failed to save switchboard Destination is not set!");
							}
							submenu52.setDestinationnumber(this.dest52);
						}
						submenu52.setDialdigit(new BigDecimal(2));
						submenu52.setSwitchboardsubmenuname("Sub Menu 2");
						submenu52.setUpdateuser(loginWebUser.getWebusername());
						submenu52.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
						
						Switchboardsubmenu submenu53 = new Switchboardsubmenu();
						submenu53.setSwitchboardsubmenukey(this.switchboardsubmenukey53);
						submenu53.setSwitchboardmenukey(this.switchboardmenukey5);
						submenu53.setDescription("Sub Menu 5.3");
						if (submenu5LevelSelect3 == 0) {
							submenu53.setDestinationnumber("");
						} else {
							if (this.dest53 == null || this.dest53.trim().equals("")) {
								throw new MICSException("Failed to save switchboard Destination is not set!");
							}
							submenu53.setDestinationnumber(this.dest53);
						}
						submenu53.setDialdigit(new BigDecimal(3));
						submenu53.setSwitchboardsubmenuname("Sub Menu 3");
						submenu53.setUpdateuser(loginWebUser.getWebusername());
						submenu53.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
						
						submenus5.add(submenu51);
						submenus5.add(submenu52);
						submenus5.add(submenu53);
					}
					if (firstLevelSelect5 == 2) {
						if (this.dest5 == null || this.dest5.trim().equals("")) {
							throw new MICSException("Failed to save switchboard Destination is not set!");
						}
						switchMenu5.setDestinationnumber(dest5);
					}
					
					switchMenu5.setUpdateuser(newSwitchboard.getUpdateuser());
					
					SwitchboardEntry seMenu5 = new SwitchboardEntry();
					seMenu5.setMenu(switchMenu5);
					seMenu5.setSubMenu(submenus5);
					
					List switchboardMenus = new ArrayList();
					switchboardMenus.add(seMenu1);
					switchboardMenus.add(seMenu2);
					switchboardMenus.add(seMenu3);
					switchboardMenus.add(seMenu4);
					switchboardMenus.add(seMenu5);
					micsCommonSessionHandler.createCompanySwitchboard(newSwitchboard, switchboardMenus, /*dummy*/ null, /*dummy*/ null);
					addActionMessage("Company switchboard succesfully saved");
					logger.debug("create/save switchboard complete");
					Micslogger.log("Switchboard","Company",   "Switchboard for company "+ loginWebUser.getCompanykey() +" created/saved"  , loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"SUCCESS");
					result = MICSConstant.MICS_SUCCESS;
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
	
	public String initView(){
		logger.info("initView()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
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
					this.annoList = micsCommonSessionHandler.getCompanyAnno(companykey);
					logger.debug("annoList: " + annoList);
					session.put(MICSConstant.MICS_SESSION_ANNOLIST, annoList);
					this.switchList = micsCommonSessionHandler.getSwitchboardTypes();
					session.put(MICSConstant.MICS_SESSION_SWITCHLIST, switchList);
					//Check if switchboard already created
					Switchboard currSwitchboardDB = micsCommonSessionHandler.getCompanySwitchboard(this.companykey);
					if (currSwitchboardDB != null) {
						//Set to field
						this.switchboardKey = currSwitchboardDB.getSwitchboardkey();
						this.switchboardName = currSwitchboardDB.getSwitchboardname();
						this.switchboardPublicNumber = currSwitchboardDB.getPublicnumber();
						this.switchboardPrivateNumber = currSwitchboardDB.getPrivatenumber();
						if (currSwitchboardDB.getAnnoid() != null) {
							this.annoId = currSwitchboardDB.getAnnoid().intValue();
						}
						
						//Get Switchboard menu
						List currSwitcboardmenus = micsCommonSessionHandler.getSwitchboardmenus(currSwitchboardDB.getSwitchboardkey());
						if (currSwitcboardmenus!= null && !currSwitcboardmenus.isEmpty()) {
							
							for (Iterator iterator = currSwitcboardmenus
									.iterator(); iterator.hasNext();) {
								Switchboardmenu currSBMenu = (Switchboardmenu) iterator.next();
								int intCurrSbMenu = currSBMenu.getDialdigit().intValue();
								logger.debug("intCurrSbMenu: " + intCurrSbMenu);
								switch (intCurrSbMenu) {
								case 1:
									if (currSBMenu.getAnnoid() !=null) {
										this.firstLevelAnno1 = currSBMenu.getAnnoid().intValue();
									}
									
									this.switchboardmenukey1 = currSBMenu.getSwitchboardmenukey();
									this.firstLevelSelect1 = currSBMenu.getMenutype().intValue();
									if (this.firstLevelSelect1 == 1) {
										//Get Sub Menu
										List currSwitchboardSubmenus = micsCommonSessionHandler.getSwitchboardsubmenus(currSBMenu.getSwitchboardmenukey());
										if (currSwitchboardSubmenus != null && !currSwitchboardSubmenus.isEmpty()) {
											int subMenuIdx = 1;
											for (Iterator iterSubMenus = currSwitchboardSubmenus
													.iterator(); iterSubMenus
													.hasNext();) {
												Switchboardsubmenu currSubmenu = (Switchboardsubmenu) iterSubMenus
														.next();
												
												switch (currSubmenu.getDialdigit().intValue()) {
												case 1:
													if (currSubmenu.getDestinationnumber() != null && !currSubmenu.getDestinationnumber().trim().equals("")) {
														this.dest11 = currSubmenu.getDestinationnumber();
														this.submenu1LevelSelect1 = 2;
													} else{
														this.submenu1LevelSelect1 = 0;
													}
													this.switchboardsubmenukey11 = currSubmenu.getSwitchboardsubmenukey();
													break;
												case 2:
													if (currSubmenu.getDestinationnumber() != null && !currSubmenu.getDestinationnumber().trim().equals("")) {
														this.dest12 = currSubmenu.getDestinationnumber();
														this.submenu1LevelSelect2 = 2;
													} else{
														this.submenu1LevelSelect2 = 0;
													}
													this.switchboardsubmenukey12 = currSubmenu.getSwitchboardsubmenukey();
													break;
												case 3:
													if (currSubmenu.getDestinationnumber() != null && !currSubmenu.getDestinationnumber().trim().equals("")) {
														this.dest13 = currSubmenu.getDestinationnumber();
														this.submenu1LevelSelect3 = 2;
													} else{
														this.submenu1LevelSelect3 = 0;
													}
													this.switchboardsubmenukey13 = currSubmenu.getSwitchboardsubmenukey();
													break;
												
												}
												
											}
										}
									}
									if (this.firstLevelSelect1 == 2) {
										this.dest1 = currSBMenu.getDestinationnumber();
									}
									break;
								case 2:
									if (currSBMenu.getAnnoid() !=null) {
										this.firstLevelAnno2 = currSBMenu.getAnnoid().intValue();
									}
									this.switchboardmenukey2 = currSBMenu.getSwitchboardmenukey();
									this.firstLevelSelect2 = currSBMenu.getMenutype().intValue();
									if (this.firstLevelSelect2 == 1) {
										//Get Sub Menu
										List currSwitchboardSubmenus = micsCommonSessionHandler.getSwitchboardsubmenus(currSBMenu.getSwitchboardmenukey());
										if (currSwitchboardSubmenus != null && !currSwitchboardSubmenus.isEmpty()) {
											int subMenuIdx = 1;
											for (Iterator iterSubMenus = currSwitchboardSubmenus
													.iterator(); iterSubMenus
													.hasNext();) {
												Switchboardsubmenu currSubmenu = (Switchboardsubmenu) iterSubMenus
														.next();
												switch (currSubmenu.getDialdigit().intValue()) {
												case 1:
													if (currSubmenu.getDestinationnumber() != null && !currSubmenu.getDestinationnumber().trim().equals("")) {
														this.dest21 = currSubmenu.getDestinationnumber();
														this.submenu2LevelSelect1 = 2;
													} else{
														this.submenu2LevelSelect1 = 0;
													}
													this.switchboardsubmenukey21 = currSubmenu.getSwitchboardsubmenukey();
													break;
												case 2:
													if (currSubmenu.getDestinationnumber() != null && !currSubmenu.getDestinationnumber().trim().equals("")) {
														this.dest22 = currSubmenu.getDestinationnumber();
														this.submenu2LevelSelect2 = 2;
													} else{
														this.submenu2LevelSelect2 = 0;
													}
													this.switchboardsubmenukey22 = currSubmenu.getSwitchboardsubmenukey();
													break;
												case 3:
													if (currSubmenu.getDestinationnumber() != null && !currSubmenu.getDestinationnumber().trim().equals("")) {
														this.dest23 = currSubmenu.getDestinationnumber();
														this.submenu2LevelSelect3 = 2;
													} else{
														this.submenu2LevelSelect3 = 0;
													}
													this.switchboardsubmenukey23 = currSubmenu.getSwitchboardsubmenukey();
													break;
												
												}
												
											}
										}
									}
									if (this.firstLevelSelect2 == 2) {
										this.dest2 = currSBMenu.getDestinationnumber();
									}
									break;
								case 3:
									if (currSBMenu.getAnnoid() !=null) {
										this.firstLevelAnno3 = currSBMenu.getAnnoid().intValue();
									}
									this.switchboardmenukey3 = currSBMenu.getSwitchboardmenukey();
									this.firstLevelSelect3 = currSBMenu.getMenutype().intValue();
									if (this.firstLevelSelect3 == 1) {
										//Get Sub Menu
										List currSwitchboardSubmenus = micsCommonSessionHandler.getSwitchboardsubmenus(currSBMenu.getSwitchboardmenukey());
										if (currSwitchboardSubmenus != null && !currSwitchboardSubmenus.isEmpty()) {
											int subMenuIdx = 1;
											for (Iterator iterSubMenus = currSwitchboardSubmenus
													.iterator(); iterSubMenus
													.hasNext();) {
												Switchboardsubmenu currSubmenu = (Switchboardsubmenu) iterSubMenus
														.next();
												switch (currSubmenu.getDialdigit().intValue()) {
												case 1:
													if (currSubmenu.getDestinationnumber() != null && !currSubmenu.getDestinationnumber().trim().equals("")) {
														this.dest31 = currSubmenu.getDestinationnumber();
														this.submenu3LevelSelect1 = 2;
													} else{
														this.submenu3LevelSelect1 = 0;
													}
													this.switchboardsubmenukey31 = currSubmenu.getSwitchboardsubmenukey();
													break;
												case 2:
													if (currSubmenu.getDestinationnumber() != null && !currSubmenu.getDestinationnumber().trim().equals("")) {
														this.dest32 = currSubmenu.getDestinationnumber();
														this.submenu3LevelSelect2 = 2;
													} else{
														this.submenu3LevelSelect2 = 0;
													}
													this.switchboardsubmenukey32 = currSubmenu.getSwitchboardsubmenukey();
													break;
												case 3:
													if (currSubmenu.getDestinationnumber() != null && !currSubmenu.getDestinationnumber().trim().equals("")) {
														this.dest33 = currSubmenu.getDestinationnumber();
														this.submenu3LevelSelect3 = 2;
													} else{
														this.submenu3LevelSelect3 = 0;
													}
													this.switchboardsubmenukey33 = currSubmenu.getSwitchboardsubmenukey();
													break;
												
												}
												
											}
										}
									}
									if (this.firstLevelSelect3 == 2) {
										this.dest3 = currSBMenu.getDestinationnumber();
									}
									break;
								case 4:
									if (currSBMenu.getAnnoid() !=null) {
										this.firstLevelAnno4 = currSBMenu.getAnnoid().intValue();
									}
									
									this.switchboardmenukey4 = currSBMenu.getSwitchboardmenukey();
									this.firstLevelSelect4 = currSBMenu.getMenutype().intValue();
									if (this.firstLevelSelect4 == 1) {
										//Get Sub Menu
										List currSwitchboardSubmenus = micsCommonSessionHandler.getSwitchboardsubmenus(currSBMenu.getSwitchboardmenukey());
										if (currSwitchboardSubmenus != null && !currSwitchboardSubmenus.isEmpty()) {
											int subMenuIdx = 1;
											for (Iterator iterSubMenus = currSwitchboardSubmenus
													.iterator(); iterSubMenus
													.hasNext();) {
												Switchboardsubmenu currSubmenu = (Switchboardsubmenu) iterSubMenus
														.next();
												switch (currSubmenu.getDialdigit().intValue()) {
												case 1:
													if (currSubmenu.getDestinationnumber() != null && !currSubmenu.getDestinationnumber().trim().equals("")) {
														this.dest41 = currSubmenu.getDestinationnumber();
														this.submenu4LevelSelect1 = 2;
													} else{
														this.submenu4LevelSelect1 = 0;
													}
													this.switchboardsubmenukey41 = currSubmenu.getSwitchboardsubmenukey();
													break;
												case 2:
													if (currSubmenu.getDestinationnumber() != null && !currSubmenu.getDestinationnumber().trim().equals("")) {
														this.dest42 = currSubmenu.getDestinationnumber();
														this.submenu4LevelSelect2 = 2;
													} else{
														this.submenu4LevelSelect2 = 0;
													}
													this.switchboardsubmenukey42 = currSubmenu.getSwitchboardsubmenukey();
													break;
												case 3:
													if (currSubmenu.getDestinationnumber() != null && !currSubmenu.getDestinationnumber().trim().equals("")) {
														this.dest43 = currSubmenu.getDestinationnumber();
														this.submenu4LevelSelect3 = 2;
													} else{
														this.submenu4LevelSelect3 = 0;
													}
													this.switchboardsubmenukey43 = currSubmenu.getSwitchboardsubmenukey();
													break;
												
												}
												
											}
										}
									}
									if (this.firstLevelSelect4 == 2) {
										this.dest4 = currSBMenu.getDestinationnumber();
									}
									break;
								case 5:
									if (currSBMenu.getAnnoid() !=null) {
										this.firstLevelAnno5 = currSBMenu.getAnnoid().intValue();
									}
									
									this.switchboardmenukey5 = currSBMenu.getSwitchboardmenukey();
									this.firstLevelSelect5 = currSBMenu.getMenutype().intValue();
									if (this.firstLevelSelect5 == 1) {
										//Get Sub Menu
										List currSwitchboardSubmenus = micsCommonSessionHandler.getSwitchboardsubmenus(currSBMenu.getSwitchboardmenukey());
										if (currSwitchboardSubmenus != null && !currSwitchboardSubmenus.isEmpty()) {
											int subMenuIdx = 1;
											for (Iterator iterSubMenus = currSwitchboardSubmenus
													.iterator(); iterSubMenus
													.hasNext();) {
												Switchboardsubmenu currSubmenu = (Switchboardsubmenu) iterSubMenus
														.next();
												switch (currSubmenu.getDialdigit().intValue()) {
												case 1:
													if (currSubmenu.getDestinationnumber() != null && !currSubmenu.getDestinationnumber().trim().equals("")) {
														this.dest51 = currSubmenu.getDestinationnumber();
														this.submenu5LevelSelect1 = 2;
													} else{
														this.submenu5LevelSelect1 = 0;
													}
													this.switchboardsubmenukey51 = currSubmenu.getSwitchboardsubmenukey();
													break;
												case 2:
													if (currSubmenu.getDestinationnumber() != null && !currSubmenu.getDestinationnumber().trim().equals("")) {
														this.dest52 = currSubmenu.getDestinationnumber();
														this.submenu5LevelSelect2 = 2;
													} else{
														this.submenu5LevelSelect2 = 0;
													}
													this.switchboardsubmenukey52 = currSubmenu.getSwitchboardsubmenukey();
													break;
												case 3:
													if (currSubmenu.getDestinationnumber() != null && !currSubmenu.getDestinationnumber().trim().equals("")) {
														this.dest53 = currSubmenu.getDestinationnumber();
														this.submenu5LevelSelect3 = 2;
													} else{
														this.submenu5LevelSelect3 = 0;
													}
													this.switchboardsubmenukey53 = currSubmenu.getSwitchboardsubmenukey();
													break;
												
												}
												
											}
										}
									}
									if (this.firstLevelSelect5 == 2) {
										this.dest5 = currSBMenu.getDestinationnumber();
									}
									break;

								}
								
							}
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
			logger.error("Failed to initialize Company Switchboard view",e);
			addActionError("Failed to process your Company Switchboard view request " + e.getMessage());
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
					
					//Set Curr Destination
					for (Iterator iterMember = searchMemberRes.iterator(); iterMember
							.hasNext();) {
						AddUserRequest currAddMember = (AddUserRequest) iterMember.next();
						logger.debug("Member " + currAddMember.getMicsUserKey() + " - " + currAddMember.getMicsFirstName() +","+ currAddMember.getMicsLastName()+" add " + currAddMember.isSelected());
						if (currAddMember.isSelected()) {
							switch (destlevel) {
							case 1:
								dest1 = currAddMember.getMicsPubNumber();
								break;
							case 2:
								dest2 = currAddMember.getMicsPubNumber();;
								break;
							case 3:
								dest3 = currAddMember.getMicsPubNumber();
								break;
							case 4:
								dest4 = currAddMember.getMicsPubNumber();
								break;
							case 5:
								dest5 = currAddMember.getMicsPubNumber();
								break;
							case 11:
								dest11 = currAddMember.getMicsPubNumber();
								break;
							case 12:
								dest12 = currAddMember.getMicsPubNumber();
								break;
							case 13:
								dest13 = currAddMember.getMicsPubNumber();
								break;
							case 21:
								dest21 = currAddMember.getMicsPubNumber();
								break;
							case 22:
								dest22 = currAddMember.getMicsPubNumber();
								break;
							case 23:
								dest23 = currAddMember.getMicsPubNumber();
								break;
							case 31:
								dest31 = currAddMember.getMicsPubNumber();
								break;
							case 32:
								dest32 = currAddMember.getMicsPubNumber();
								break;
							case 33:
								dest33 = currAddMember.getMicsPubNumber();
								break;
							case 41:
								dest41 = currAddMember.getMicsPubNumber();
								break;
							case 42:
								dest42 = currAddMember.getMicsPubNumber();
								break;
							case 43:
								dest43 = currAddMember.getMicsPubNumber();
								break;
							case 51:
								dest51 = currAddMember.getMicsPubNumber();
								break;
							case 52:
								dest52 = currAddMember.getMicsPubNumber();
								break;
							case 53:
								dest53 = currAddMember.getMicsPubNumber();
								break;

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
					
					
					employeeList = micsCommonSessionHandler.getEmployeesByCriteriaCompany(this.searchFirstName, this.searchLastName,this.searchPrivNumber,this.searchPubNumber, this.companykey);
					searchMemberRes = new ArrayList<AddUserRequest>();
					//Check Curr Destination
					String currDestination = "";
					switch (destlevel) {
					case 1:
						currDestination = dest1;
						break;
					case 2:
						currDestination = dest2;
						break;
					case 3:
						currDestination = dest3;
						break;
					case 4:
						currDestination = dest4;
						break;
					case 5:
						currDestination = dest5;
						break;
					case 11:
						currDestination = dest11;
						break;
					case 12:
						currDestination = dest12;
						break;
					case 13:
						currDestination = dest13;
						break;
					case 21:
						currDestination = dest21;
						break;
					case 22:
						currDestination = dest22;
						break;
					case 23:
						currDestination = dest23;
						break;
					case 31:
						currDestination = dest31;
						break;
					case 32:
						currDestination = dest32;
						break;
					case 33:
						currDestination = dest33;
						break;
					case 41:
						currDestination = dest41;
						break;
					case 42:
						currDestination = dest42;
						break;
					case 43:
						currDestination = dest43;
						break;
					case 51:
						currDestination = dest51;
						break;
					case 52:
						currDestination = dest52;
						break;
					case 53:
						currDestination = dest53;
						break;

					}
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
						
						if (currDestination != null && !currDestination.trim().equals("")) {
							if (currMicsPubNumber.equals(currDestination)){
								currAddUserReq.setSelected(true);
							}
						}
						searchMemberRes.add(currAddUserReq);
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
	 * @return the switchboardKey
	 */
	public String getSwitchboardKey() {
		return switchboardKey;
	}

	/**
	 * @param switchboardKey the switchboardKey to set
	 */
	public void setSwitchboardKey(String switchboardKey) {
		this.switchboardKey = switchboardKey;
	}

	/**
	 * @return the switchboardName
	 */
	public String getSwitchboardName() {
		return switchboardName;
	}

	/**
	 * @param switchboardName the switchboardName to set
	 */
	public void setSwitchboardName(String switchboardName) {
		this.switchboardName = switchboardName;
	}

	/**
	 * @return the switchboardPublicNumber
	 */
	public String getSwitchboardPublicNumber() {
		return switchboardPublicNumber;
	}

	/**
	 * @param switchboardPublicNumber the switchboardPublicNumber to set
	 */
	public void setSwitchboardPublicNumber(String switchboardPublicNumber) {
		this.switchboardPublicNumber = switchboardPublicNumber;
	}

	/**
	 * @return the switchboardPrivateNumber
	 */
	public String getSwitchboardPrivateNumber() {
		return switchboardPrivateNumber;
	}

	/**
	 * @param switchboardPrivateNumber the switchboardPrivateNumber to set
	 */
	public void setSwitchboardPrivateNumber(String switchboardPrivateNumber) {
		this.switchboardPrivateNumber = switchboardPrivateNumber;
	}

	/**
	 * @return the annoId
	 */
	public int getAnnoId() {
		return annoId;
	}

	/**
	 * @param annoId the annoId to set
	 */
	public void setAnnoId(int annoId) {
		this.annoId = annoId;
	}

	/**
	 * @return the firstLevelSelect1
	 */
	public int getFirstLevelSelect1() {
		return firstLevelSelect1;
	}

	/**
	 * @param firstLevelSelect1 the firstLevelSelect1 to set
	 */
	public void setFirstLevelSelect1(int firstLevelSelect1) {
		this.firstLevelSelect1 = firstLevelSelect1;
	}

	/**
	 * @return the firstLevelSelect2
	 */
	public int getFirstLevelSelect2() {
		return firstLevelSelect2;
	}

	/**
	 * @param firstLevelSelect2 the firstLevelSelect2 to set
	 */
	public void setFirstLevelSelect2(int firstLevelSelect2) {
		this.firstLevelSelect2 = firstLevelSelect2;
	}

	/**
	 * @return the firstLevelSelect3
	 */
	public int getFirstLevelSelect3() {
		return firstLevelSelect3;
	}

	/**
	 * @param firstLevelSelect3 the firstLevelSelect3 to set
	 */
	public void setFirstLevelSelect3(int firstLevelSelect3) {
		this.firstLevelSelect3 = firstLevelSelect3;
	}

	/**
	 * @return the firstLevelSelect4
	 */
	public int getFirstLevelSelect4() {
		return firstLevelSelect4;
	}

	/**
	 * @param firstLevelSelect4 the firstLevelSelect4 to set
	 */
	public void setFirstLevelSelect4(int firstLevelSelect4) {
		this.firstLevelSelect4 = firstLevelSelect4;
	}

	/**
	 * @return the firstLevelSelect5
	 */
	public int getFirstLevelSelect5() {
		return firstLevelSelect5;
	}

	/**
	 * @param firstLevelSelect5 the firstLevelSelect5 to set
	 */
	public void setFirstLevelSelect5(int firstLevelSelect5) {
		this.firstLevelSelect5 = firstLevelSelect5;
	}

	/**
	 * @return the firstLevelAnno1
	 */
	public int getFirstLevelAnno1() {
		return firstLevelAnno1;
	}

	/**
	 * @param firstLevelAnno1 the firstLevelAnno1 to set
	 */
	public void setFirstLevelAnno1(int firstLevelAnno1) {
		this.firstLevelAnno1 = firstLevelAnno1;
	}

	/**
	 * @return the firstLevelAnno2
	 */
	public int getFirstLevelAnno2() {
		return firstLevelAnno2;
	}

	/**
	 * @param firstLevelAnno2 the firstLevelAnno2 to set
	 */
	public void setFirstLevelAnno2(int firstLevelAnno2) {
		this.firstLevelAnno2 = firstLevelAnno2;
	}

	/**
	 * @return the firstLevelAnno3
	 */
	public int getFirstLevelAnno3() {
		return firstLevelAnno3;
	}

	/**
	 * @param firstLevelAnno3 the firstLevelAnno3 to set
	 */
	public void setFirstLevelAnno3(int firstLevelAnno3) {
		this.firstLevelAnno3 = firstLevelAnno3;
	}

	/**
	 * @return the firstLevelAnno4
	 */
	public int getFirstLevelAnno4() {
		return firstLevelAnno4;
	}

	/**
	 * @param firstLevelAnno4 the firstLevelAnno4 to set
	 */
	public void setFirstLevelAnno4(int firstLevelAnno4) {
		this.firstLevelAnno4 = firstLevelAnno4;
	}

	/**
	 * @return the firstLevelAnno5
	 */
	public int getFirstLevelAnno5() {
		return firstLevelAnno5;
	}

	/**
	 * @param firstLevelAnno5 the firstLevelAnno5 to set
	 */
	public void setFirstLevelAnno5(int firstLevelAnno5) {
		this.firstLevelAnno5 = firstLevelAnno5;
	}
	
	
	/**
	 * @return the switchboardmenukey1
	 */
	public String getSwitchboardmenukey1() {
		return switchboardmenukey1;
	}

	/**
	 * @param switchboardmenukey1 the switchboardmenukey1 to set
	 */
	public void setSwitchboardmenukey1(String switchboardmenukey1) {
		this.switchboardmenukey1 = switchboardmenukey1;
	}

	/**
	 * @return the switchboardmenukey2
	 */
	public String getSwitchboardmenukey2() {
		return switchboardmenukey2;
	}

	/**
	 * @param switchboardmenukey2 the switchboardmenukey2 to set
	 */
	public void setSwitchboardmenukey2(String switchboardmenukey2) {
		this.switchboardmenukey2 = switchboardmenukey2;
	}

	/**
	 * @return the switchboardmenukey3
	 */
	public String getSwitchboardmenukey3() {
		return switchboardmenukey3;
	}

	/**
	 * @param switchboardmenukey3 the switchboardmenukey3 to set
	 */
	public void setSwitchboardmenukey3(String switchboardmenukey3) {
		this.switchboardmenukey3 = switchboardmenukey3;
	}

	/**
	 * @return the switchboardmenukey4
	 */
	public String getSwitchboardmenukey4() {
		return switchboardmenukey4;
	}

	/**
	 * @param switchboardmenukey4 the switchboardmenukey4 to set
	 */
	public void setSwitchboardmenukey4(String switchboardmenukey4) {
		this.switchboardmenukey4 = switchboardmenukey4;
	}

	/**
	 * @return the switchboardmenukey5
	 */
	public String getSwitchboardmenukey5() {
		return switchboardmenukey5;
	}

	/**
	 * @param switchboardmenukey5 the switchboardmenukey5 to set
	 */
	public void setSwitchboardmenukey5(String switchboardmenukey5) {
		this.switchboardmenukey5 = switchboardmenukey5;
	}
	
	

	/**
	 * @return the dest1
	 */
	public String getDest1() {
		return dest1;
	}

	/**
	 * @param dest1 the dest1 to set
	 */
	public void setDest1(String dest1) {
		this.dest1 = dest1;
	}

	/**
	 * @return the dest2
	 */
	public String getDest2() {
		return dest2;
	}

	/**
	 * @param dest2 the dest2 to set
	 */
	public void setDest2(String dest2) {
		this.dest2 = dest2;
	}

	/**
	 * @return the dest3
	 */
	public String getDest3() {
		return dest3;
	}

	/**
	 * @param dest3 the dest3 to set
	 */
	public void setDest3(String dest3) {
		this.dest3 = dest3;
	}

	/**
	 * @return the dest4
	 */
	public String getDest4() {
		return dest4;
	}

	/**
	 * @param dest4 the dest4 to set
	 */
	public void setDest4(String dest4) {
		this.dest4 = dest4;
	}

	/**
	 * @return the dest5
	 */
	public String getDest5() {
		return dest5;
	}

	/**
	 * @param dest5 the dest5 to set
	 */
	public void setDest5(String dest5) {
		this.dest5 = dest5;
	}

	/**
	 * @return the submenu1LevelSelect1
	 */
	public int getSubmenu1LevelSelect1() {
		return submenu1LevelSelect1;
	}

	/**
	 * @param submenu1LevelSelect1 the submenu1LevelSelect1 to set
	 */
	public void setSubmenu1LevelSelect1(int submenu1LevelSelect1) {
		this.submenu1LevelSelect1 = submenu1LevelSelect1;
	}

	/**
	 * @return the submenu1LevelSelect2
	 */
	public int getSubmenu1LevelSelect2() {
		return submenu1LevelSelect2;
	}

	/**
	 * @param submenu1LevelSelect2 the submenu1LevelSelect2 to set
	 */
	public void setSubmenu1LevelSelect2(int submenu1LevelSelect2) {
		this.submenu1LevelSelect2 = submenu1LevelSelect2;
	}

	/**
	 * @return the submenu1LevelSelect3
	 */
	public int getSubmenu1LevelSelect3() {
		return submenu1LevelSelect3;
	}

	/**
	 * @param submenu1LevelSelect3 the submenu1LevelSelect3 to set
	 */
	public void setSubmenu1LevelSelect3(int submenu1LevelSelect3) {
		this.submenu1LevelSelect3 = submenu1LevelSelect3;
	}

	/**
	 * @return the dest11
	 */
	public String getDest11() {
		return dest11;
	}

	/**
	 * @param dest11 the dest11 to set
	 */
	public void setDest11(String dest11) {
		this.dest11 = dest11;
	}

	/**
	 * @return the dest12
	 */
	public String getDest12() {
		return dest12;
	}

	/**
	 * @param dest12 the dest12 to set
	 */
	public void setDest12(String dest12) {
		this.dest12 = dest12;
	}

	/**
	 * @return the dest13
	 */
	public String getDest13() {
		return dest13;
	}

	/**
	 * @param dest13 the dest13 to set
	 */
	public void setDest13(String dest13) {
		this.dest13 = dest13;
	}

	/**
	 * @return the submenu2LevelSelect1
	 */
	public int getSubmenu2LevelSelect1() {
		return submenu2LevelSelect1;
	}

	/**
	 * @param submenu2LevelSelect1 the submenu2LevelSelect1 to set
	 */
	public void setSubmenu2LevelSelect1(int submenu2LevelSelect1) {
		this.submenu2LevelSelect1 = submenu2LevelSelect1;
	}

	/**
	 * @return the submenu2LevelSelect2
	 */
	public int getSubmenu2LevelSelect2() {
		return submenu2LevelSelect2;
	}

	/**
	 * @param submenu2LevelSelect2 the submenu2LevelSelect2 to set
	 */
	public void setSubmenu2LevelSelect2(int submenu2LevelSelect2) {
		this.submenu2LevelSelect2 = submenu2LevelSelect2;
	}

	/**
	 * @return the submenu2LevelSelect3
	 */
	public int getSubmenu2LevelSelect3() {
		return submenu2LevelSelect3;
	}

	/**
	 * @param submenu2LevelSelect3 the submenu2LevelSelect3 to set
	 */
	public void setSubmenu2LevelSelect3(int submenu2LevelSelect3) {
		this.submenu2LevelSelect3 = submenu2LevelSelect3;
	}

	/**
	 * @return the dest21
	 */
	public String getDest21() {
		return dest21;
	}

	/**
	 * @param dest21 the dest21 to set
	 */
	public void setDest21(String dest21) {
		this.dest21 = dest21;
	}

	/**
	 * @return the dest22
	 */
	public String getDest22() {
		return dest22;
	}

	/**
	 * @param dest22 the dest22 to set
	 */
	public void setDest22(String dest22) {
		this.dest22 = dest22;
	}

	/**
	 * @return the dest23
	 */
	public String getDest23() {
		return dest23;
	}

	/**
	 * @param dest23 the dest23 to set
	 */
	public void setDest23(String dest23) {
		this.dest23 = dest23;
	}

	/**
	 * @return the submenu3LevelSelect1
	 */
	public int getSubmenu3LevelSelect1() {
		return submenu3LevelSelect1;
	}

	/**
	 * @param submenu3LevelSelect1 the submenu3LevelSelect1 to set
	 */
	public void setSubmenu3LevelSelect1(int submenu3LevelSelect1) {
		this.submenu3LevelSelect1 = submenu3LevelSelect1;
	}

	/**
	 * @return the submenu3LevelSelect2
	 */
	public int getSubmenu3LevelSelect2() {
		return submenu3LevelSelect2;
	}

	/**
	 * @param submenu3LevelSelect2 the submenu3LevelSelect2 to set
	 */
	public void setSubmenu3LevelSelect2(int submenu3LevelSelect2) {
		this.submenu3LevelSelect2 = submenu3LevelSelect2;
	}

	/**
	 * @return the submenu3LevelSelect3
	 */
	public int getSubmenu3LevelSelect3() {
		return submenu3LevelSelect3;
	}

	/**
	 * @param submenu3LevelSelect3 the submenu3LevelSelect3 to set
	 */
	public void setSubmenu3LevelSelect3(int submenu3LevelSelect3) {
		this.submenu3LevelSelect3 = submenu3LevelSelect3;
	}

	/**
	 * @return the dest31
	 */
	public String getDest31() {
		return dest31;
	}

	/**
	 * @param dest31 the dest31 to set
	 */
	public void setDest31(String dest31) {
		this.dest31 = dest31;
	}

	/**
	 * @return the dest32
	 */
	public String getDest32() {
		return dest32;
	}

	/**
	 * @param dest32 the dest32 to set
	 */
	public void setDest32(String dest32) {
		this.dest32 = dest32;
	}

	/**
	 * @return the dest33
	 */
	public String getDest33() {
		return dest33;
	}

	/**
	 * @param dest33 the dest33 to set
	 */
	public void setDest33(String dest33) {
		this.dest33 = dest33;
	}

	/**
	 * @return the submenu4LevelSelect1
	 */
	public int getSubmenu4LevelSelect1() {
		return submenu4LevelSelect1;
	}

	/**
	 * @param submenu4LevelSelect1 the submenu4LevelSelect1 to set
	 */
	public void setSubmenu4LevelSelect1(int submenu4LevelSelect1) {
		this.submenu4LevelSelect1 = submenu4LevelSelect1;
	}

	/**
	 * @return the submenu4LevelSelect2
	 */
	public int getSubmenu4LevelSelect2() {
		return submenu4LevelSelect2;
	}

	/**
	 * @param submenu4LevelSelect2 the submenu4LevelSelect2 to set
	 */
	public void setSubmenu4LevelSelect2(int submenu4LevelSelect2) {
		this.submenu4LevelSelect2 = submenu4LevelSelect2;
	}

	/**
	 * @return the submenu4LevelSelect3
	 */
	public int getSubmenu4LevelSelect3() {
		return submenu4LevelSelect3;
	}

	/**
	 * @param submenu4LevelSelect3 the submenu4LevelSelect3 to set
	 */
	public void setSubmenu4LevelSelect3(int submenu4LevelSelect3) {
		this.submenu4LevelSelect3 = submenu4LevelSelect3;
	}

	/**
	 * @return the dest41
	 */
	public String getDest41() {
		return dest41;
	}

	/**
	 * @param dest41 the dest41 to set
	 */
	public void setDest41(String dest41) {
		this.dest41 = dest41;
	}

	/**
	 * @return the dest42
	 */
	public String getDest42() {
		return dest42;
	}

	/**
	 * @param dest42 the dest42 to set
	 */
	public void setDest42(String dest42) {
		this.dest42 = dest42;
	}

	/**
	 * @return the dest43
	 */
	public String getDest43() {
		return dest43;
	}

	/**
	 * @param dest43 the dest43 to set
	 */
	public void setDest43(String dest43) {
		this.dest43 = dest43;
	}

	/**
	 * @return the submenu5LevelSelect1
	 */
	public int getSubmenu5LevelSelect1() {
		return submenu5LevelSelect1;
	}

	/**
	 * @param submenu5LevelSelect1 the submenu5LevelSelect1 to set
	 */
	public void setSubmenu5LevelSelect1(int submenu5LevelSelect1) {
		this.submenu5LevelSelect1 = submenu5LevelSelect1;
	}

	/**
	 * @return the submenu5LevelSelect2
	 */
	public int getSubmenu5LevelSelect2() {
		return submenu5LevelSelect2;
	}

	/**
	 * @param submenu5LevelSelect2 the submenu5LevelSelect2 to set
	 */
	public void setSubmenu5LevelSelect2(int submenu5LevelSelect2) {
		this.submenu5LevelSelect2 = submenu5LevelSelect2;
	}

	/**
	 * @return the submenu5LevelSelect3
	 */
	public int getSubmenu5LevelSelect3() {
		return submenu5LevelSelect3;
	}

	/**
	 * @param submenu5LevelSelect3 the submenu5LevelSelect3 to set
	 */
	public void setSubmenu5LevelSelect3(int submenu5LevelSelect3) {
		this.submenu5LevelSelect3 = submenu5LevelSelect3;
	}

	/**
	 * @return the dest51
	 */
	public String getDest51() {
		return dest51;
	}

	/**
	 * @param dest51 the dest51 to set
	 */
	public void setDest51(String dest51) {
		this.dest51 = dest51;
	}

	/**
	 * @return the dest52
	 */
	public String getDest52() {
		return dest52;
	}

	/**
	 * @param dest52 the dest52 to set
	 */
	public void setDest52(String dest52) {
		this.dest52 = dest52;
	}

	/**
	 * @return the dest53
	 */
	public String getDest53() {
		return dest53;
	}

	/**
	 * @param dest53 the dest53 to set
	 */
	public void setDest53(String dest53) {
		this.dest53 = dest53;
	}

	/**
	 * @return the destlevel
	 */
	public int getDestlevel() {
		return destlevel;
	}

	/**
	 * @param destlevel the destlevel to set
	 */
	public void setDestlevel(int destlevel) {
		this.destlevel = destlevel;
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
	 * @return the switchboardsubmenukey11
	 */
	public String getSwitchboardsubmenukey11() {
		return switchboardsubmenukey11;
	}

	/**
	 * @param switchboardsubmenukey11 the switchboardsubmenukey11 to set
	 */
	public void setSwitchboardsubmenukey11(String switchboardsubmenukey11) {
		this.switchboardsubmenukey11 = switchboardsubmenukey11;
	}

	/**
	 * @return the switchboardsubmenukey12
	 */
	public String getSwitchboardsubmenukey12() {
		return switchboardsubmenukey12;
	}

	/**
	 * @param switchboardsubmenukey12 the switchboardsubmenukey12 to set
	 */
	public void setSwitchboardsubmenukey12(String switchboardsubmenukey12) {
		this.switchboardsubmenukey12 = switchboardsubmenukey12;
	}

	/**
	 * @return the switchboardsubmenukey13
	 */
	public String getSwitchboardsubmenukey13() {
		return switchboardsubmenukey13;
	}

	/**
	 * @param switchboardsubmenukey13 the switchboardsubmenukey13 to set
	 */
	public void setSwitchboardsubmenukey13(String switchboardsubmenukey13) {
		this.switchboardsubmenukey13 = switchboardsubmenukey13;
	}

	/**
	 * @return the switchboardsubmenukey21
	 */
	public String getSwitchboardsubmenukey21() {
		return switchboardsubmenukey21;
	}

	/**
	 * @param switchboardsubmenukey21 the switchboardsubmenukey21 to set
	 */
	public void setSwitchboardsubmenukey21(String switchboardsubmenukey21) {
		this.switchboardsubmenukey21 = switchboardsubmenukey21;
	}

	/**
	 * @return the switchboardsubmenukey22
	 */
	public String getSwitchboardsubmenukey22() {
		return switchboardsubmenukey22;
	}

	/**
	 * @param switchboardsubmenukey22 the switchboardsubmenukey22 to set
	 */
	public void setSwitchboardsubmenukey22(String switchboardsubmenukey22) {
		this.switchboardsubmenukey22 = switchboardsubmenukey22;
	}

	/**
	 * @return the switchboardsubmenukey23
	 */
	public String getSwitchboardsubmenukey23() {
		return switchboardsubmenukey23;
	}

	/**
	 * @param switchboardsubmenukey23 the switchboardsubmenukey23 to set
	 */
	public void setSwitchboardsubmenukey23(String switchboardsubmenukey23) {
		this.switchboardsubmenukey23 = switchboardsubmenukey23;
	}

	/**
	 * @return the switchboardsubmenukey31
	 */
	public String getSwitchboardsubmenukey31() {
		return switchboardsubmenukey31;
	}

	/**
	 * @param switchboardsubmenukey31 the switchboardsubmenukey31 to set
	 */
	public void setSwitchboardsubmenukey31(String switchboardsubmenukey31) {
		this.switchboardsubmenukey31 = switchboardsubmenukey31;
	}

	/**
	 * @return the switchboardsubmenukey32
	 */
	public String getSwitchboardsubmenukey32() {
		return switchboardsubmenukey32;
	}

	/**
	 * @param switchboardsubmenukey32 the switchboardsubmenukey32 to set
	 */
	public void setSwitchboardsubmenukey32(String switchboardsubmenukey32) {
		this.switchboardsubmenukey32 = switchboardsubmenukey32;
	}

	/**
	 * @return the switchboardsubmenukey33
	 */
	public String getSwitchboardsubmenukey33() {
		return switchboardsubmenukey33;
	}

	/**
	 * @param switchboardsubmenukey33 the switchboardsubmenukey33 to set
	 */
	public void setSwitchboardsubmenukey33(String switchboardsubmenukey33) {
		this.switchboardsubmenukey33 = switchboardsubmenukey33;
	}

	/**
	 * @return the switchboardsubmenukey41
	 */
	public String getSwitchboardsubmenukey41() {
		return switchboardsubmenukey41;
	}

	/**
	 * @param switchboardsubmenukey41 the switchboardsubmenukey41 to set
	 */
	public void setSwitchboardsubmenukey41(String switchboardsubmenukey41) {
		this.switchboardsubmenukey41 = switchboardsubmenukey41;
	}

	/**
	 * @return the switchboardsubmenukey42
	 */
	public String getSwitchboardsubmenukey42() {
		return switchboardsubmenukey42;
	}

	/**
	 * @param switchboardsubmenukey42 the switchboardsubmenukey42 to set
	 */
	public void setSwitchboardsubmenukey42(String switchboardsubmenukey42) {
		this.switchboardsubmenukey42 = switchboardsubmenukey42;
	}

	/**
	 * @return the switchboardsubmenukey43
	 */
	public String getSwitchboardsubmenukey43() {
		return switchboardsubmenukey43;
	}

	/**
	 * @param switchboardsubmenukey43 the switchboardsubmenukey43 to set
	 */
	public void setSwitchboardsubmenukey43(String switchboardsubmenukey43) {
		this.switchboardsubmenukey43 = switchboardsubmenukey43;
	}

	/**
	 * @return the switchboardsubmenukey51
	 */
	public String getSwitchboardsubmenukey51() {
		return switchboardsubmenukey51;
	}

	/**
	 * @param switchboardsubmenukey51 the switchboardsubmenukey51 to set
	 */
	public void setSwitchboardsubmenukey51(String switchboardsubmenukey51) {
		this.switchboardsubmenukey51 = switchboardsubmenukey51;
	}

	/**
	 * @return the switchboardsubmenukey52
	 */
	public String getSwitchboardsubmenukey52() {
		return switchboardsubmenukey52;
	}

	/**
	 * @param switchboardsubmenukey52 the switchboardsubmenukey52 to set
	 */
	public void setSwitchboardsubmenukey52(String switchboardsubmenukey52) {
		this.switchboardsubmenukey52 = switchboardsubmenukey52;
	}

	/**
	 * @return the switchboardsubmenukey53
	 */
	public String getSwitchboardsubmenukey53() {
		return switchboardsubmenukey53;
	}

	/**
	 * @param switchboardsubmenukey53 the switchboardsubmenukey53 to set
	 */
	public void setSwitchboardsubmenukey53(String switchboardsubmenukey53) {
		this.switchboardsubmenukey53 = switchboardsubmenukey53;
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
	 * @return the session
	 */
	public Map<String, Object> getSession() {
		return session;
	}



	private Map<String, Object> session;
	
	private List<Announcement> annoList;
	private List<SwitchboardType> switchList;
	
	private String companykey;
	
	private String switchboardKey;
	private String switchboardName;
	private String switchboardPublicNumber;
	private String switchboardPrivateNumber;
	private int annoId;
	
	private String switchboardmenukey1;
	private String switchboardmenukey2;
	private String switchboardmenukey3;
	private String switchboardmenukey4;
	private String switchboardmenukey5;
	
	private int firstLevelSelect1;
	private int firstLevelSelect2;
	private int firstLevelSelect3;
	private int firstLevelSelect4;
	private int firstLevelSelect5;
	
	private String dest1;
	private String dest2;
	private String dest3;
	private String dest4;
	private String dest5;
	
	private int firstLevelAnno1;
	private int firstLevelAnno2;
	private int firstLevelAnno3;
	private int firstLevelAnno4;
	private int firstLevelAnno5;
	
	private int submenu1LevelSelect1;
	private int submenu1LevelSelect2;
	private int submenu1LevelSelect3;

	private String switchboardsubmenukey11;
	private String switchboardsubmenukey12;
	private String switchboardsubmenukey13;
	
	private String dest11;
	private String dest12;
	private String dest13;
	
	private int submenu2LevelSelect1;
	private int submenu2LevelSelect2;
	private int submenu2LevelSelect3;
	
	private String switchboardsubmenukey21;
	private String switchboardsubmenukey22;
	private String switchboardsubmenukey23;
	
	private String dest21;
	private String dest22;
	private String dest23;
	
	private int submenu3LevelSelect1;
	private int submenu3LevelSelect2;
	private int submenu3LevelSelect3;
	
	private String switchboardsubmenukey31;
	private String switchboardsubmenukey32;
	private String switchboardsubmenukey33;
	
	private String dest31;
	private String dest32;
	private String dest33;
	
	private int submenu4LevelSelect1;
	private int submenu4LevelSelect2;
	private int submenu4LevelSelect3;
	
	private String switchboardsubmenukey41;
	private String switchboardsubmenukey42;
	private String switchboardsubmenukey43;
	
	private String dest41;
	private String dest42;
	private String dest43;
	
	private int submenu5LevelSelect1;
	private int submenu5LevelSelect2;
	private int submenu5LevelSelect3;
	
	private String switchboardsubmenukey51;
	private String switchboardsubmenukey52;
	private String switchboardsubmenukey53;
	
	private String dest51;
	private String dest52;
	private String dest53;
	
	private int destlevel;
	
	private List employeeList;
	private String searchFirstName;
	private String searchLastName;
	private String searchPubNumber;
	private String searchPrivNumber;
	private List<AddUserRequest> searchMemberRes;

	
	private boolean tselAdmin;
	
	
	private String switchboardCreate = "2017050105";
	private String switchboardView = "2017050108";
	private static final Logger logger = Logger.getLogger(SwitchboardAction.class);

	@Override
	public void prepare() throws Exception {
		MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
		this.annoList = micsCommonSessionHandler.getCompanyAnno(companykey);
		logger.debug("annoList: " + annoList);
		session.put(MICSConstant.MICS_SESSION_ANNOLIST, annoList);
		this.switchList = micsCommonSessionHandler.getSwitchboardTypes();
		session.put(MICSConstant.MICS_SESSION_SWITCHLIST, switchList);		
	}
}
