package com.nsn.telkomsel.mics.action;

import java.math.BigDecimal;
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
import com.nsn.telkomsel.mics.orahbm.Webuser;
import com.nsn.telkomsel.mics.util.MICSConstant;
import com.nsn.telkomsel.mics.util.Micslogger;
import com.opensymphony.xwork2.ActionSupport;

public class AnnouncementAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = -7282436282732288581L;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String initCreate(){
		logger.info("initCreate()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(announcementCreate, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS) || 
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION) ||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
						Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
						logger.debug("currCompany: " + currCompany);
						if(currCompany == null) throw new Exception ("Currently no company selected! please select company first.");
						this.companykey = currCompany.getCompanykey();
						this.annoList = micsCommonSessionHandler.getCompanyAnno(companykey);
						logger.debug("annoList: " + annoList);
						if (annoList == null|| annoList.size() == 0) {
							logger.debug("annoList not exist create annoList holder");
							annoList = new ArrayList();
							for (int i = 0; i < 20; i++) {
								Announcement currAnno = new Announcement();
								currAnno.setAnnoid(new BigDecimal(i+1));
								currAnno.setSrfelementid(new BigDecimal(i+1));
								currAnno.setAnnouncementname("");
								currAnno.setAnnouncementdesc("");
								currAnno.setCompanykey(this.companykey);
								currAnno.setUpdateuser(loginWebUser.getWebusername());
								annoList.add(currAnno);
							}
						}
						session.put(MICSConstant.MICS_SESSION_ANNOLIST, annoList);
					} else {
						this.tselAdmin = false;
						this.companykey = loginWebUser.getCompanykey();
						this.annoList = micsCommonSessionHandler.getCompanyAnno(companykey);
						logger.debug("annoList: " + annoList);
						if (annoList == null|| annoList.size() == 0) {
							logger.debug("annoList not exist create annoList holder");
							annoList = new ArrayList();
							for (int i = 0; i < 20; i++) {
								Announcement currAnno = new Announcement();
								currAnno.setCompanykey(this.companykey);
								currAnno.setAnnoid(new BigDecimal(i+1));
								currAnno.setSrfelementid(new BigDecimal(i+1));
								currAnno.setAnnouncementname("");
								currAnno.setAnnouncementdesc("");
								currAnno.setUpdateuser(loginWebUser.getWebusername());
								annoList.add(currAnno);
							}
						}
						session.put(MICSConstant.MICS_SESSION_ANNOLIST, annoList);
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
			logger.error("Failed to initialize Company Announcement admin",e);
			addActionError("Failed to process your Company Announcement administration request " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		logger.debug("result: " + result);
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String initView(){
		logger.info("initView()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(announcementView, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS) || 
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION) ||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
						Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
						logger.debug("currCompany: " + currCompany);
						if(currCompany == null) throw new Exception ("Currently no company selected! please select company first.");
						this.companykey = currCompany.getCompanykey();
						this.annoList = micsCommonSessionHandler.getCompanyAnno(companykey);
						logger.debug("annoList: " + annoList);
						if (annoList == null|| annoList.size() == 0) {
							logger.debug("annoList not exist");
							throw new MICSException("No Announcement to view");
							
						}
						session.put(MICSConstant.MICS_SESSION_ANNOLIST, annoList);
					} else {
						this.tselAdmin = false;
						this.companykey = loginWebUser.getCompanykey();
						this.annoList = micsCommonSessionHandler.getCompanyAnno(companykey);
						logger.debug("annoList: " + annoList);
						if (annoList == null|| annoList.size() == 0) {
							logger.debug("annoList not exist");
							throw new MICSException("No Announcement to view");
							
						}
						session.put(MICSConstant.MICS_SESSION_ANNOLIST, annoList);
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
			logger.error("Failed to initialize Company Announcement view",e);
			addActionError("Failed to process your Company Announcement view request " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		logger.debug("result: " + result);
		return result;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String companyChange(){
		logger.info("companyChange()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(announcementCreate, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS) || 
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION) ||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
						this.companyList = micsCommonSessionHandler.getCompanyList();
						session.put(MICSConstant.MICS_SESSION_COMPANYLIST, companyList);
						logger.debug("companyList: " + companyList);
						this.annoList = micsCommonSessionHandler.getCompanyAnno(companykey);
						logger.debug("annoList: " + annoList);
						if (annoList == null|| annoList.size() == 0) {
							logger.debug("annoList not exist create annoList holder");
							annoList = new ArrayList();
							for (int i = 0; i < 20; i++) {
								Announcement currAnno = new Announcement();
								currAnno.setCompanykey(this.companykey);
								currAnno.setAnnoid(new BigDecimal(i+1));
								currAnno.setSrfelementid(new BigDecimal(i+1));
								currAnno.setAnnouncementname("");
								currAnno.setAnnouncementdesc("");
								currAnno.setUpdateuser(loginWebUser.getWebusername());
								annoList.add(currAnno);
							}
						}
						session.put(MICSConstant.MICS_SESSION_ANNOLIST, annoList);
					} else {
						this.tselAdmin = false;
						this.companykey = loginWebUser.getCompanykey();
						this.annoList = micsCommonSessionHandler.getCompanyAnno(companykey);
						logger.debug("annoList: " + annoList);
						if (annoList == null|| annoList.size() == 0) {
							logger.debug("annoList not exist create annoList holder");
							annoList = new ArrayList();
							for (int i = 0; i < 20; i++) {
								Announcement currAnno = new Announcement();
								currAnno.setCompanykey(this.companykey);
								currAnno.setAnnoid(new BigDecimal(i+1));
								currAnno.setSrfelementid(new BigDecimal(i+1));
								currAnno.setAnnouncementname("");
								currAnno.setAnnouncementdesc("");
								currAnno.setUpdateuser(loginWebUser.getWebusername());
								annoList.add(currAnno);
							}
						}
						session.put(MICSConstant.MICS_SESSION_ANNOLIST, annoList);
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
			logger.error("Failed to initialize Company Announcement admin",e);
			addActionError("Failed to process your Company Announcement administration request");
			result = MICSConstant.MICS_ERROR;
		} 
		logger.debug("result: " + result);
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String create(){
		logger.info("create()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(announcementCreate, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS) || 
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION) ||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
						Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
						logger.debug("currCompany: " + currCompany);
						if(currCompany == null) throw new Exception ("Currently no company selected! please select company first.");
						
						this.companykey = currCompany.getCompanykey();
						session.put(MICSConstant.MICS_SESSION_COMPANYLIST, companyList);
						logger.debug("companyList: " + companyList);
					} else {
						this.tselAdmin = false;
						this.companykey = loginWebUser.getCompanykey();
					}
					int countItAnno = 1;
					for (Iterator itAnno = annoList.iterator(); itAnno
							.hasNext();) {
						Announcement currAnnoReq = (Announcement) itAnno.next();
						logger.debug(countItAnno+" "+currAnnoReq.getAnnouncementname() + "=" + currAnnoReq.getAnnouncementdesc() + "=" + currAnnoReq.getSrfelementid() + "-" + currAnnoReq.getAnnoid()+ "=" + currAnnoReq.getCompanykey()+ "="+ currAnnoReq.getUpdateuser());
						countItAnno++;
					}
					micsCommonSessionHandler.createCompanyAnno(annoList);
					Micslogger.log("Announcement","Company",  "Create/save announcement for company key " + this.companykey, loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"SUCCESS");
					addActionMessage("Company Announcement succesfully saved");
					logger.debug("create/save anno complete");
					result = MICSConstant.MICS_SUCCESS;
				} else {
					Micslogger.log("Announcement","Company",  "Announcement for company key " + this.companykey + ", user not authorized!", loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"FAILED");
					addActionError("User not authorized");
					result = MICSConstant.MICS_ERROR_NOT_AUTH;
				}
			} else {
				addActionError("User not logged in");
				Micslogger.log("Announcement","Company",  "Announcement for company key " + this.companykey + ", user not logged in!", "", "NoLog","FAILED");
				result = MICSConstant.MICS_ERROR_NOT_LOGGEDIN;
			}
		} catch (Exception e) {
			logger.error("Failed to initialize Company Announcement admin",e);
			addActionError("Failed to process your Company Announcement administration request " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		logger.debug("result: " + result);
		return result;
	}
	
	public void setSession(Map<String, Object> session) {
		this.session = session;

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
	 * @return the companyList
	 */
	public List<?> getCompanyList() {
		return companyList;
	}

	/**
	 * @param companyList the companyList to set
	 */
	public void setCompanyList(List<?> companyList) {
		this.companyList = companyList;
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
	 * @return the session
	 */
	public Map<String, Object> getSession() {
		return session;
	}


	private String companykey;
	private List<?> companyList;
	private List<Announcement> annoList;
	
	private String announcementCreate = "2017050106";
	private String announcementView = "2017050109";
	
	private boolean editAllowed;
	private boolean tselAdmin;
	
	private Map<String, Object> session;
	private static final Logger logger = Logger.getLogger(AnnouncementAction.class);

}
