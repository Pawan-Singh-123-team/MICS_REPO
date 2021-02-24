package com.nsn.telkomsel.mics.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.nsn.telkomsel.mics.MICSException;
import com.nsn.telkomsel.mics.handler.MICSCommonSessionHandler;
import com.nsn.telkomsel.mics.hbm.Company;
import com.nsn.telkomsel.mics.orahbm.Role;
import com.nsn.telkomsel.mics.orahbm.Webuser;
import com.nsn.telkomsel.mics.util.MICSConstant;
import com.nsn.telkomsel.mics.util.MICSPasswordEnc;
import com.nsn.telkomsel.mics.util.Micslogger;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * @author mulia
 * @version $Id: MICSUserAction.java,v 1.1.4.3 2019/03/22 10:05:56 cvsuser Exp $
 */

public class MICSUserAction extends ActionSupport implements SessionAware, Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8359203964472491348L;
	

	@Override
	public void prepare() throws Exception {
		MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
		// Check User Login 
		Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
		if (loginWebUser != null) {
			Role userRole = (Role)session.get(MICSConstant.MICS_SESSION_ROLE);
			if (userRole == null){
				userRole = micsCommonSessionHandler.getRoleByKey(loginWebUser.getRolekey());
			}
			session.put(MICSConstant.MICS_SESSION_ROLE, userRole);
			
			role = micsCommonSessionHandler.getRoleList();
			session.put(MICSConstant.MICS_SESSION_ROLELIST, role);

			//Get available Company
			company = micsCommonSessionHandler.getCompanyList();
			session.put(MICSConstant.MICS_SESSION_COMPANYLIST, company);
		}
	}
	
	public String init(){
		logger.info("init()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
								
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(webuserCreate, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
						tselAdmin = true;
					} else {
						tselAdmin = true;
						addActionError("User not authorized");
						result = MICSConstant.MICS_ERROR_NOT_AUTH;
					}
				} else {
					addActionError("User not authorized");
					result = MICSConstant.MICS_ERROR_NOT_AUTH;
				}
			} else {
				addActionError("User not logged in");
				result = MICSConstant.MICS_ERROR_NOT_LOGGEDIN;
			}
			//Get available Roles
			role = micsCommonSessionHandler.getRoleList();
			
			session.put(MICSConstant.MICS_SESSION_ROLELIST, role);
			//Get available Company
			company = micsCommonSessionHandler.getCompanyList();
			session.put(MICSConstant.MICS_SESSION_COMPANYLIST, company);
			result = MICSConstant.MICS_SUCCESS;
		} catch (Exception e) {
			logger.error("Failed to initialize MICS user admin",e);
			addActionError("Failed to process your MICS User admin request" + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public String addUser(){
		logger.info("addUser() " + this.userName + ", companyList= " + this.companyList + ", roleList= " + this.roleList );
		String result = MICSConstant.MICS_ERROR;
		
		try {
			
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
//			role = (List<Role>)session.get(MICSConstant.MICS_SESSION_ROLELIST);
//			logger.debug("role: " + role);
//			if (role == null){
//				role = micsCommonSessionHandler.getRoleList();
//				session.put(MICSConstant.MICS_SESSION_ROLELIST, role);
//			}
//			company = (List<Company>) session.get(MICSConstant.MICS_SESSION_COMPANYLIST);
//			logger.debug("company: " + company);
//			if (company == null){
//				company = micsCommonSessionHandler.getCompanyList();
//				session.put(MICSConstant.MICS_SESSION_COMPANYLIST, company);
//			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			sdf.format(new Date());
			String awalKey = sdf.format(new Date());
//			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			String micsAvailableKey = micsCommonSessionHandler.getAvailableKey(awalKey, "webuserkey", "Webuser");
			logger.debug("micUserAvailableKey: "+micsAvailableKey);
			Webuser newWebUser = new Webuser();
			newWebUser.setWebuserkey(micsAvailableKey);
			newWebUser.setWebusername(this.userName);

			if (this.userPassword == null || "".equals(this.userPassword.trim()) || 
					this.userPasswordConf == null || "".equals(this.userPasswordConf.trim())) {
				throw new MICSException("Password must not be empty!");
			}else 
			if (this.userPassword.trim().equals(this.userPasswordConf.trim())) {
				MICSPasswordEnc mpe = new MICSPasswordEnc();
				String decodedPasswd = mpe.getEncryptedPassword(this.userPassword.trim());
				if (decodedPasswd != null){
					newWebUser.setWebuserpassword(decodedPasswd);
				}else{
					throw new MICSException("Could not generate secret key");
				}
			} else {
				throw new MICSException("User Password and Password Confirmation does not match!");
			}
			
			newWebUser.setCompanykey(this.companyList);
			newWebUser.setRolekey(this.roleList);
			newWebUser.setMsisdn(this.userMsisdn);
			newWebUser.setEmail(this.userEmail);
			newWebUser.setSsotoken(this.userNik);
			//Get Login User from Session
			Webuser loginUser  = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			newWebUser.setUpdateuser(loginUser.getWebusername());
			newWebUser.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
			micsCommonSessionHandler.addMICSUser(newWebUser);
			addActionMessage("User " + this.userName + " succefully added");
			result = MICSConstant.MICS_SUCCESS;
			Micslogger.log("Create","Web User", this.userName + " " + this.companyList + " " + this.roleList +" created", loginUser.getCompanykey(), loginUser.getWebusername(),"SUCCESS");
			
		} catch (Exception e) {
			logger.error("Failed to add MICS user ",e);
			addActionError(e.getMessage());
//			addActionError("Failed to process your MICS User add request: " + e.getMessage());
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
				if (micsCommonSessionHandler.isMenuAuthorized(webuserEdit, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
						tselAdmin = true;
						//Get available Roles
						role = micsCommonSessionHandler.getRoleList();
						session.put(MICSConstant.MICS_SESSION_ROLELIST, role);
						//Get available Company
						company = micsCommonSessionHandler.getCompanyList();
						for(int i=0, size = company.size(); i<size; i++) {
							com.nsn.telkomsel.mics.orahbm.Company acompany = (com.nsn.telkomsel.mics.orahbm.Company) company.get(i);
							acompany.setCompanyname(acompany.getCompanykey() + " - " + acompany.getCompanyname());
						}
						session.put(MICSConstant.MICS_SESSION_COMPANYLIST, company);
						//Get User Information by Key
						Webuser editWebuser = micsCommonSessionHandler.getUserByKey(this.webUserKey);
						this.webUserKey = editWebuser.getWebuserkey();
						this.userName = editWebuser.getWebusername();
						this.companyList = editWebuser.getCompanykey();
						this.roleList = editWebuser.getRolekey()+"";
						this.userPassword = editWebuser.getWebuserpassword();
						this.userNik = editWebuser.getSsotoken();
						this.userEmail = editWebuser.getEmail();
						this.userMsisdn = editWebuser.getMsisdn();
						result = MICSConstant.MICS_SUCCESS;
					} else {
						tselAdmin = true;
						addActionError("User not authorized");
						result = MICSConstant.MICS_ERROR_NOT_AUTH;
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
			logger.error("Failed to init Edit MICS user admin",e);
			addActionError("Failed to process your MICS User edit request " + e.getMessage());
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
				if (micsCommonSessionHandler.isMenuAuthorized(webuserDelete, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
						tselAdmin = true;
						//Get available Roles
						role = micsCommonSessionHandler.getRoleList();
						session.put(MICSConstant.MICS_SESSION_ROLELIST, role);
						//Get available Company
						company = micsCommonSessionHandler.getCompanyList();
						session.put(MICSConstant.MICS_SESSION_COMPANYLIST, company);
						//Get User Information by Key
						Webuser editWebuser = micsCommonSessionHandler.getUserByKey(this.webUserKey);
						this.webUserKey = editWebuser.getWebuserkey();
						this.userName = editWebuser.getWebusername();
						this.companyList = editWebuser.getCompanykey();
						this.roleList = editWebuser.getRolekey()+"";
						this.userPassword = editWebuser.getWebuserpassword();
						this.userNik = editWebuser.getSsotoken();
						this.userEmail = editWebuser.getEmail();
						this.userMsisdn = editWebuser.getMsisdn();
						result = MICSConstant.MICS_SUCCESS;
					} else {
						tselAdmin = true;
						addActionError("User not authorized");
						result = MICSConstant.MICS_ERROR_NOT_AUTH;
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
			logger.error("Failed to init delete MICS user admin",e);
			addActionError("Failed to process your MICS User delete request " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public String editUser(){
		logger.info("editUser() " + this.userName + " " + this.companyList + " " + this.roleList );
		String result = MICSConstant.MICS_ERROR;
		try {
			role = (List<Role>)session.get(MICSConstant.MICS_SESSION_ROLELIST);
//			logger.debug("role: " + role);
			company = (List<Company>) session.get(MICSConstant.MICS_SESSION_COMPANYLIST);
			logger.debug("company: " + company);
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			Webuser newWebUser = micsCommonSessionHandler.getUserByKey(this.webUserKey);
			newWebUser.setWebusername(this.userName);
			/*
			if (this.userPassword != null && !this.userPassword.trim().equalsIgnoreCase("")) {
				MICSPasswordEnc mpe = new MICSPasswordEnc();
				newWebUser.setWebuserpassword(mpe.getEncryptedPassword(this.userPassword.trim()));
			}
			*/
			newWebUser.setRolekey(this.roleList);
			newWebUser.setCompanykey(this.companyList);
			newWebUser.setEmail(this.userEmail);
			newWebUser.setMsisdn(this.userMsisdn);
			newWebUser.setSsotoken(this.userNik);
			//Get Login User from Session
			Webuser loginUser  = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			newWebUser.setUpdateuser(loginUser.getWebusername());
			micsCommonSessionHandler.addMICSUser(newWebUser);
			addActionMessage("User " + this.userName + " succefully edited");
			result = MICSConstant.MICS_SUCCESS;
			Micslogger.log("Edit","Web User", this.userName + " " + this.companyList + " " + this.roleList +" edited", loginUser.getCompanykey(), loginUser.getWebusername(),"SUCCESS");
		} catch (Exception e) {
			logger.error("Failed to edit MICS user ",e);
			addActionError("Failed to edit your MICS User request " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		}
		return result;
	}
	
	public String delUser(){
		logger.info("delUser() " + this.webUserKey );
		String result = MICSConstant.MICS_ERROR;
		try {
			role = (List<Role>)session.get(MICSConstant.MICS_SESSION_ROLELIST);
//			logger.debug("role: " + role);
			company = (List<Company>) session.get(MICSConstant.MICS_SESSION_COMPANYLIST);
			logger.debug("company: " + company);
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			Webuser currWebUser = micsCommonSessionHandler.getUserByKey(this.webUserKey);
			if (currWebUser == null) {
				throw new MICSException(this.webUserKey + " Web User not exist!");
			}
			//Get Login User from Session
			Webuser loginUser  = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			
			this.userName = currWebUser.getWebusername();
			this.companyList = currWebUser.getCompanykey();
			this.roleList = currWebUser.getRolekey()+"";
			this.userPassword = currWebUser.getWebuserpassword();
			this.userNik = currWebUser.getSsotoken();
			this.userEmail = currWebUser.getEmail();
			this.userMsisdn = currWebUser.getMsisdn();
			micsCommonSessionHandler.delMICSUser(currWebUser);
			
			addActionMessage("User " + this.webUserKey + " - " + currWebUser.getWebusername() + " - "+ currWebUser.getMsisdn() +" successfully deleted");
			Micslogger.log("Create","Web User", currWebUser.getWebusername() + " " + currWebUser.getCompanykey() + " " + currWebUser.getRolekey() +" deleted", loginUser.getCompanykey(), loginUser.getWebusername(),"SUCCESS");
			result = MICSConstant.MICS_SUCCESS;
		} catch (Exception e) {
			logger.error("Failed to delete MICS user ",e);
			addActionError("Failed to delete MICS User, " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		}
		return result;
	}
	
	public String initSetPwd(){
		logger.info("initSetPwd()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(webuserEdit, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
						tselAdmin = true;
						//Get User Information by Key
						Webuser editWebuser = micsCommonSessionHandler.getUserByKey(this.webUserKey);
						this.webUserKey = editWebuser.getWebuserkey();
						this.userName = editWebuser.getWebusername();
						result = MICSConstant.MICS_SUCCESS;
					} else {
						tselAdmin = true;
						addActionError("User not authorized");
						result = MICSConstant.MICS_ERROR_NOT_AUTH;
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
			logger.error("Failed to init Edit MICS user admin",e);
			addActionError("Failed to process your MICS User edit request " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
	}
	
	public String initChangePwd(){
		logger.info("initChangePwd()");
		return MICSConstant.MICS_SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String submitSetPwd(){
		logger.info("submitSetPwd()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(webuserSetPwd, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
						tselAdmin = true;
						//Get User Information by Key
						Webuser editWebuser = micsCommonSessionHandler.getUserByKey(this.webUserKey);
						if (this.userPassword == null || "".equals(this.userPassword.trim()) || 
								this.userPasswordConf == null || "".equals(this.userPasswordConf.trim())) {
							
							throw new MICSException("Password must not be empty!");
						}
						else if (!this.userPassword.trim().equals(this.userPasswordConf.trim())) {
							throw new MICSException("User Password and Password Confirmation does not match!");
						}
						else if (userPassword.equalsIgnoreCase(editWebuser.getWebusername())){
							throw new MICSException("Password must not be identical with user name!");
						}
						else {
							MICSPasswordEnc mpe = new MICSPasswordEnc();
							editWebuser.setWebuserpassword(mpe.getEncryptedPassword(this.userPassword.trim()));
							micsCommonSessionHandler.editUser(editWebuser);
							
							this.userName = editWebuser.getWebusername();
							addActionMessage("Password of User " + this.userName + " is succefully set");
							result = MICSConstant.MICS_SUCCESS;
							Micslogger.log("SetPwd","Web User", this.userName + " pwd set", loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"SUCCESS");
						}
					} else {
						tselAdmin = true;
						addActionError("User not authorized");
						result = MICSConstant.MICS_ERROR_NOT_AUTH;
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
			logger.error("Failed to init Edit MICS user admin",e);
			addActionError(e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public String submitChangePwd(){
		logger.info("submitChangePwd()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(changePwd, loginWebUser.getRolekey())) {
					//Get User Information by Key
					Webuser editWebuser = micsCommonSessionHandler.getUserByKey(loginWebUser.getWebuserkey());
					MICSPasswordEnc mpe = new MICSPasswordEnc();

					if (this.userPassword == null || "".equals(this.userPassword.trim()) || 
							this.userPasswordConf == null || "".equals(this.userPasswordConf.trim()) ||
							this.currentPassword == null || "".equals(this.currentPassword.trim())) {
						
						throw new MICSException("All fields must be filled!");
					}
					else if (!this.userPassword.trim().equals(this.userPasswordConf.trim())) {
						throw new MICSException("User Password and Password Confirmation does not match!");
					}
					else 
					if (!editWebuser.getWebuserpassword().trim().equals(mpe.getEncryptedPassword(this.currentPassword.trim()))) {
						throw new MICSException("Current password is incorrect!");
					}
					else if (userPassword.equalsIgnoreCase(editWebuser.getWebusername())){
						throw new MICSException("Password must not be identical with user name!");
					}
					else {
						editWebuser.setWebuserpassword(mpe.getEncryptedPassword(this.userPassword.trim()));
						micsCommonSessionHandler.editUser(editWebuser);
						
						addActionMessage("Password succefully changed");
						result = MICSConstant.MICS_SUCCESS;
						Micslogger.log("ChangePwd","Change Password", this.userName + " changed pwd", loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"SUCCESS");
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
			logger.error("Failed to init Edit MICS user admin",e);
			addActionError(e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
	}
	
	public void setSession(Map<String, Object> session) {
		this.session = session;

	}
	
	public String getUserName() {
		return userName;
	}
	/**
	 * @return the session
	 */
	public Map<String, Object> getSession() {
		return session;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(List role) {
		this.role = role;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(List company) {
		this.company = company;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserPassword() {
		return userPassword;
	}
	
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	/**
	 * @return the userPasswordConf
	 */
	public String getUserPasswordConf() {
		return userPasswordConf;
	}

	/**
	 * @param userPasswordConf the userPasswordConf to set
	 */
	public void setUserPasswordConf(String userPasswordConf) {
		this.userPasswordConf = userPasswordConf;
	}

	/**
	 * @param currentPassword the currentPassword to set
	 */
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	
	/**
	 * @return the currentPassword
	 */
	public String getCurrentPassword() {
		return currentPassword;
	}

	public String getUserEmail() {
		return userEmail;
	}
	
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public String getRoleList() {
		return roleList;
	}

	public void setRoleList(String roleList) {
		this.roleList = roleList;
	}

	@SuppressWarnings("rawtypes")
	public List getRole() {
		return role;
	}

	public String getCompanyList() {
		return companyList;
	}

	public void setCompanyList(String companyList) {
		this.companyList = companyList;
	}

	@SuppressWarnings("rawtypes")
	public List getCompany() {
		return company;
	}

	
	public String getWebUserKey() {
		return webUserKey;
	}

	public void setWebUserKey(String webUserKey) {
		this.webUserKey = webUserKey;
	}
	
	/**
	 * @return the userNik
	 */
	public String getUserNik() {
		return userNik;
	}

	/**
	 * @param userNik the userNik to set
	 */
	public void setUserNik(String userNik) {
		this.userNik = userNik;
	}

	/**
	 * @return the userMsisdn
	 */
	public String getUserMsisdn() {
		return userMsisdn;
	}

	/**
	 * @param userMsisdn the userMsisdn to set
	 */
	public void setUserMsisdn(String userMsisdn) {
		this.userMsisdn = userMsisdn;
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


	private String webUserKey;
	private String userName;
	private String userPassword;
	private String userPasswordConf;
	private String currentPassword;
	private String userEmail;
	private String roleList;
	private String userNik;
	private String userMsisdn;
	@SuppressWarnings("rawtypes")
	private List role;
	private String companyList;
	@SuppressWarnings("rawtypes")
	private List company;
	private boolean tselAdmin;
	
	private String webuserCreate = "2017051801";
	private String webuserSearch = "2017051802";
	private String webuserEdit = "2017051803";
	private String webuserDelete = "2017051804";
	private String webuserSetPwd = "2017051805";
	private String changePwd = "2017052100";
	
	private Map<String,Object> session;
	private static final Logger logger = Logger.getLogger(MICSUserAction.class);

}	
