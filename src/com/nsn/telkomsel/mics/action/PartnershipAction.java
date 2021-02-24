package com.nsn.telkomsel.mics.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.nsn.telkomsel.mics.handler.MICSCommonSessionHandler;
import com.nsn.telkomsel.mics.orahbm.Partnership;
import com.nsn.telkomsel.mics.orahbm.Webuser;
import com.nsn.telkomsel.mics.util.MICSConstant;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * PartnershipAction.java
 * 
 * @author mulia
 * @version $Id: PartnershipAction.java,v 1.1.4.3 2019/03/22 10:05:58 cvsuser Exp $
 */
public class PartnershipAction extends ActionSupport implements SessionAware,Preparable {

	
	
	
	private static final long serialVersionUID = -349844787216390789L;
	
	public String initCreate(){
		logger.info("initCreate()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(partnershipCreate, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					companyList = micsCommonSessionHandler.getCompanyList();
					session.put(MICSConstant.MICS_SESSION_COMPANYLIST, companyList);
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
			logger.error("Failed to initialize Partnership admin");
			addActionError("Failed to process your Partnership administration request");
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
				if (micsCommonSessionHandler.isMenuAuthorized(partnershipCreate, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					companyList = (List)session.get(MICSConstant.MICS_SESSION_COMPANYLIST);
					if(companykey1.equals(companykey2)) throw new Exception("Can not create partnership with the same company!");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
					sdf.format(new Date());
					String awalKey = sdf.format(new Date());
					String micsPartnerAvailableKey = micsCommonSessionHandler.getAvailableKey(awalKey, "partnershipkey", "Partnership");
					logger.debug("micsPartnerAvailableKey: "+micsPartnerAvailableKey);
					Partnership newPartnership = new Partnership(micsPartnerAvailableKey,this.partnershipname,this.companykey1,this.companykey2,loginWebUser.getWebusername(),new Timestamp(System.currentTimeMillis()));
					micsCommonSessionHandler.createPartnership(newPartnership);
					addActionMessage("Partnership " + newPartnership.getPartnershipname() + " successfully created");
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
			logger.error("Failed to initialize Partnership admin");
			addActionError("Failed to process your Partnership administration request");
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public String search(){
		logger.info("search()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(partnershipSearch, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					companyList = (List)session.get(MICSConstant.MICS_SESSION_COMPANYLIST);
					if(partnershipname == null) 
						partnershipname = "";
					partnershipList = null;
					logger.info("partnershipname :"+partnershipname);
					if (searchCnt>0) {
						partnershipList = micsCommonSessionHandler.searchPartnerships(partnershipname);
					}
					searchCnt++;
					
					logger.debug("partnershipList: "+partnershipList);
					if (micsCommonSessionHandler.isMenuAuthorized(partnershipDelete, loginWebUser.getRolekey())) {
						this.deleteAllowed = true;
					} else {
						this.deleteAllowed = false;
					}
					if (micsCommonSessionHandler.isMenuAuthorized(partnershipEdit, loginWebUser.getRolekey())) {
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
			logger.error("Failed to search Partnership ",e);
			addActionError("Failed to process your Partnership search request " + e.getMessage());
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
				if (micsCommonSessionHandler.isMenuAuthorized(partnershipEdit, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					companyList = micsCommonSessionHandler.getCompanyList();
					session.put(MICSConstant.MICS_SESSION_COMPANYLIST, companyList);
					if(partnershipkey == null) throw new Exception("Partnership key is empty! can not found partnership with empty key.");
					Partnership currPartnership = micsCommonSessionHandler.getPartnershipByKey(partnershipkey);
					if(currPartnership == null) throw new Exception ("Can not found Partnership with key " + partnershipkey);
					this.partnershipkey = currPartnership.getPartnershipkey();
					this.partnershipname = currPartnership.getPartnershipname();
					this.companykey1 = currPartnership.getCompanykey1();
					this.companykey2 = currPartnership.getCompanykey2();
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
			logger.error("Failed to edit Partnership",e);
			addActionError("Failed to process Partnership edit request " + e.getMessage());
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
				if (micsCommonSessionHandler.isMenuAuthorized(partnershipView, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					companyList = micsCommonSessionHandler.getCompanyList();
					session.put(MICSConstant.MICS_SESSION_COMPANYLIST, companyList);
					if(partnershipkey == null) throw new Exception("Partnership key is empty! can not found partnership with empty key.");
					Partnership currPartnership = micsCommonSessionHandler.getPartnershipByKey(partnershipkey);
					if(currPartnership == null) throw new Exception ("Can not found Partnership with key " + partnershipkey);
					this.partnershipkey = currPartnership.getPartnershipkey();
					this.partnershipname = currPartnership.getPartnershipname();
					this.companykey1 = currPartnership.getCompanykey1();
					this.companykey2 = currPartnership.getCompanykey2();
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
			logger.error("Failed to view Partnership",e);
			addActionError("Failed to process Partnership view request " + e.getMessage());
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
				if (micsCommonSessionHandler.isMenuAuthorized(partnershipEdit, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					companyList = (List)session.get(MICSConstant.MICS_SESSION_COMPANYLIST);
					if(companykey1.equals(companykey2)) throw new Exception("Can not create partnership with the same company!");;
					Partnership newPartnership = new Partnership(this.partnershipkey,this.partnershipname,this.companykey1,this.companykey2,loginWebUser.getWebusername(),new Timestamp(System.currentTimeMillis()));
					micsCommonSessionHandler.editPartnership(newPartnership);
					addActionMessage("Partnership " + newPartnership.getPartnershipname() + " successfully edited");
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
			logger.error("Failed to edit Partnership ",e);
			addActionError("Failed to process your Partnership edit request " + e.getMessage());
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
				if (micsCommonSessionHandler.isMenuAuthorized(partnershipDelete, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					companyList = micsCommonSessionHandler.getCompanyList();
					session.put(MICSConstant.MICS_SESSION_COMPANYLIST, companyList);
					if(partnershipkey == null) throw new Exception("Partnership key is empty! can not found partnership with empty key.");
					Partnership currPartnership = micsCommonSessionHandler.getPartnershipByKey(partnershipkey);
					if(currPartnership == null) throw new Exception ("Can not found Partnership with key " + partnershipkey);
					this.partnershipkey = currPartnership.getPartnershipkey();
					this.partnershipname = currPartnership.getPartnershipname();
					this.companykey1 = currPartnership.getCompanykey1();
					this.companykey2 = currPartnership.getCompanykey2();
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
			logger.error("Failed to delete Partnership",e);
			addActionError("Failed to process Partnership delete request " + e.getMessage());
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
				if (micsCommonSessionHandler.isMenuAuthorized(partnershipCreate, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					companyList = (List)session.get(MICSConstant.MICS_SESSION_COMPANYLIST);
					if(partnershipkey == null) throw new Exception("Partnership key is empty! can not found partnership with empty key.");
					Partnership currPartnership = micsCommonSessionHandler.getPartnershipByKey(partnershipkey);
					if(currPartnership == null) throw new Exception ("Can not found Partnership with key " + partnershipkey);
					micsCommonSessionHandler.deletePartnership(currPartnership);
					addActionMessage("Partnership " + currPartnership.getPartnershipname() + " successfully deleted");
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
			logger.error("Failed to delete Partnership ",e);
			addActionError("Failed to process your Partnership delete request " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
	}
	
	
	/**
	 * @return the partnershipkey
	 */
	public String getPartnershipkey() {
		return partnershipkey;
	}
	/**
	 * @param partnershipkey the partnershipkey to set
	 */
	public void setPartnershipkey(String partnershipkey) {
		this.partnershipkey = partnershipkey;
	}
	/**
	 * @return the partnershipname
	 */
	public String getPartnershipname() {
		return partnershipname;
	}
	/**
	 * @param partnershipname the partnershipname to set
	 */
	public void setPartnershipname(String partnershipname) {
		this.partnershipname = partnershipname;
	}
	/**
	 * @return the companykey1
	 */
	public String getCompanykey1() {
		return companykey1;
	}
	/**
	 * @param companykey1 the companykey1 to set
	 */
	public void setCompanykey1(String companykey1) {
		this.companykey1 = companykey1;
	}
	/**
	 * @return the companykey2
	 */
	public String getCompanykey2() {
		return companykey2;
	}
	/**
	 * @param companykey2 the companykey2 to set
	 */
	public void setCompanykey2(String companykey2) {
		this.companykey2 = companykey2;
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
	 * @return the partnershipList
	 */
	@SuppressWarnings("rawtypes")
	public List getPartnershipList() {
		return partnershipList;
	}
	/**
	 * @param partnershipList the partnershipList to set
	 */
	@SuppressWarnings("rawtypes")
	public void setPartnershipList(List partnershipList) {
		this.partnershipList = partnershipList;
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
	
	
	private String partnershipCreate = "2017050301";
	private String partnershipSearch = "2017050302";
	private String partnershipEdit = "2017050303";
	private String partnershipDelete = "2017050304";
	private String partnershipView = "2017050305";
	private String partnershipkey;
	private String partnershipname;
	private String companykey1;
	private String companykey2;
	private String updateuser;
	private Timestamp updatetimestamp;
	private List companyList;
	private List partnershipList;
	private boolean deleteAllowed;
	private boolean editAllowed;
	private int searchCnt;
	
	private Map<String, Object> session;
	private static final Logger logger = Logger.getLogger(PartnershipAction.class);

	@Override
	public void prepare() throws Exception {
		MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
		companyList = micsCommonSessionHandler.getCompanyList();
		session.put(MICSConstant.MICS_SESSION_COMPANYLIST, companyList);
		
	}

}
