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
import com.nsn.telkomsel.mics.orahbm.Callscreening;
import com.nsn.telkomsel.mics.orahbm.Company;
import com.nsn.telkomsel.mics.orahbm.Incomingcallscreening;
import com.nsn.telkomsel.mics.orahbm.Outgoingcallscreening;
import com.nsn.telkomsel.mics.orahbm.Webuser;
import com.nsn.telkomsel.mics.util.AddCallScreening;
import com.nsn.telkomsel.mics.util.AddNumberRequest;
import com.nsn.telkomsel.mics.util.MICSConstant;
import com.nsn.telkomsel.mics.util.Micslogger;
import com.nsn.telkomsel.mics.util.ScreeningLevel;
import com.nsn.telkomsel.mics.util.ScreeningType;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class ProfileAction extends ActionSupport implements SessionAware,Preparable {

	
	private static final long serialVersionUID = 335676020860968844L;
	
	public String initCreate(){
		logger.info("init()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(profileCreate, loginWebUser.getRolekey())) {
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						
						this.tselAdmin = true;
//						Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
//						if (currCompany == null) {
//							throw new Exception(" No company selected! Select company first");
//						}
//						this.companykey = currCompany.getCompanykey();
//						announcements = micsCommonSessionHandler.getCompanyAnno(companykey);
					} else {
						this.tselAdmin = false;
//						Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
//						this.companykey = currCompany.getCompanykey();
//						announcements = micsCommonSessionHandler.getCompanyAnno(companykey);
					}
//					session.put("announcements", announcements);
					timebands = micsCommonSessionHandler.getTimebands();
					session.put("timebands", timebands);
					//Get Available Screening Profile Type
					rules = null;
					session.remove("rules");
					screeningtypes = micsCommonSessionHandler.getScreeningType();
					//Set Outgoing as default
					this.screeningtype = new BigDecimal(1);
					session.put(MICSConstant.MICS_SESSION_TYPELIST, screeningtypes);
					//Get Available Screening Profile Level
					screeninglevels = micsCommonSessionHandler.getScreeningLevel();
					session.put(MICSConstant.MICS_SESSION_LEVELLIST, screeninglevels);
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
			logger.error("Failed to initialize MICS Profile admin",e);
			addActionError("Failed to process your MICS Profile admin request " + e.getMessage());
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
				if (micsCommonSessionHandler.isMenuAuthorized(profileEdit, loginWebUser.getRolekey())) {
					//Get Profile Information by Call Screening Key
					Callscreening editCallScreening = micsCommonSessionHandler.getCallScreeningByKey(this.callscreeningkey);
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

					timebands = micsCommonSessionHandler.getTimebands();
					session.put("timebands", timebands);
					//Initialize deleted rules from previous session if exist
					session.remove(MICSConstant.MICS_SESSION_DEL_RULES);
					delRules = null;
					//Get Available Screening Profile Type
					screeningtypes = micsCommonSessionHandler.getScreeningType();
					session.put(MICSConstant.MICS_SESSION_TYPELIST, screeningtypes);
					//Get Available Screening Profile Level
					screeninglevels = micsCommonSessionHandler.getScreeningLevel();
					session.put(MICSConstant.MICS_SESSION_LEVELLIST, screeninglevels);
					//Get Profile Information by Call Screening Key
					
					this.callscreeningkey = editCallScreening.getCallscreeningkey();
					this.callscreeningname = editCallScreening.getCallscreeningname();
					this.callscreeningdesc = editCallScreening.getCallscreeningdesc();
					this.companykey = editCallScreening.getCompanykey();
					this.screeninglevel = editCallScreening.getScreeninglevel();
					this.screeningtype = editCallScreening.getScreeningtype();
					this.callscreeningkey = editCallScreening.getCallscreeningkey();
					if (this.screeningtype.intValue() == MICSConstant.MICS_SCREENING_TYPE_INCOMING) {
						//Get Incoming Details
						List icsList = micsCommonSessionHandler.getIncomingCallScreeningByKey(this.callscreeningkey);
						rules = new ArrayList<AddCallScreening>();
						for (Iterator iterator = icsList.iterator(); iterator.hasNext();) {
							Incomingcallscreening editICS = (Incomingcallscreening) iterator.next();
							AddCallScreening currCallScreening = new AddCallScreening();
							currCallScreening.setRulekey(editICS.getRulekey());
							currCallScreening.setRulename(editICS.getRulename());
							currCallScreening.setOrderindex(editICS.getOrderindex());
							currCallScreening.setTimebandkey(editICS.getTimebandkey());
							currCallScreening.setVlrprefix(editICS.getVlrprefix());
							currCallScreening.setCallpartytype(editICS.getCallingpartytype());
							currCallScreening.setCallpartynumber(editICS.getCallingpartynumber());
							
							if (editICS.getAllow().equals(new BigDecimal(1))) {
								currCallScreening.setAllow(true);
								
							} else {
								currCallScreening.setAllow(false);
							}
							currCallScreening.setBearerType(editICS.getBearertype());
							currCallScreening.setRejectionannoid(editICS.getRejectionannoid());
							currCallScreening.setActionType("");
							rules.add(currCallScreening);
						}
						this.maxRules = rules.size();
						session.put(MICSConstant.MICS_SESSION_RULES, rules);
						
					} else {
						//Get Outgoing Details
						List ocsList = micsCommonSessionHandler.getOutgoingCallScreeningByKey(this.callscreeningkey);
						rules = new ArrayList<AddCallScreening>();
						for (Iterator iterator = ocsList.iterator(); iterator
								.hasNext();) {
							Outgoingcallscreening editOCS = (Outgoingcallscreening) iterator.next();
							AddCallScreening currCallScreening = new AddCallScreening();
							currCallScreening.setRulekey(editOCS.getRulekey());
							currCallScreening.setRulename(editOCS.getRulename());
							currCallScreening.setOrderindex(editOCS.getOrderindex());
							currCallScreening.setTimebandkey(editOCS.getTimebandkey());
							currCallScreening.setVlrprefix(editOCS.getVlrprefix());
							currCallScreening.setGlobalciprefix(editOCS.getGlobalciprefix());
							currCallScreening.setCallpartytype(editOCS.getCalledpartytype());
							currCallScreening.setCallpartynumber(editOCS.getCalledpartynumber());
							BigDecimal bdAllow = editOCS.getAllow();
							if (bdAllow != null) {
								if (bdAllow.equals(new BigDecimal(1))) {
									currCallScreening.setAllow(true);
								} else {
									currCallScreening.setAllow(false);
								}
							} else {
								currCallScreening.setAllow(false);
							}
							
							currCallScreening.setBearerType(editOCS.getBearertype());
							currCallScreening.setRejectionannoid(editOCS.getRejectionannoid());
							currCallScreening.setActionType("");
							rules.add(currCallScreening);
							
						}
						this.maxRules = rules.size();
						session.put(MICSConstant.MICS_SESSION_RULES, rules);
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
			logger.error("Failed to edit Profile",e);
			addActionError("Failed to edit Profile");
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
	}
	
	
	
	public String initAddRule(){
		logger.debug("initAddRule");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			timebands = micsCommonSessionHandler.getTimebands();
			session.put(MICSConstant.MICS_SESSION_TIMEBANDS, timebands);
			
			screeningtypes = micsCommonSessionHandler.getScreeningType();
			session.put(MICSConstant.MICS_SESSION_TYPELIST, screeningtypes);
			
			screeninglevels = micsCommonSessionHandler.getScreeningLevel();
			session.put(MICSConstant.MICS_SESSION_LEVELLIST, screeninglevels);
			logger.debug("callscreeningname " + callscreeningname);
			logger.debug("screening type " + screeningtype);
			logger.debug("screening level " + screeninglevel);
			if (callscreeningname == null && callscreeningname.trim().equals("")) {
				
				throw new MICSException("Profile Name not set");
			}
			if (screeningtype == null) {
				
				throw new MICSException("Type not set");
			}
			if (screeninglevels == null) {
				
				throw new MICSException("Level not set");
			}
			result = MICSConstant.MICS_SUCCESS;
		} catch (Exception e) {
			logger.error("Failed to add profile rule",e);
			addActionError("Failed to init add rule " + e.getMessage());
			result = MICSConstant.MICS_INPUT;
		} 
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public String addRule(){
		logger.info("addRule() ");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			timebands = micsCommonSessionHandler.getTimebands();
			session.put(MICSConstant.MICS_SESSION_TIMEBANDS, timebands);
			
			screeningtypes = micsCommonSessionHandler.getScreeningType();
			session.put(MICSConstant.MICS_SESSION_TYPELIST, screeningtypes);
			
			screeninglevels = micsCommonSessionHandler.getScreeningLevel();
			session.put(MICSConstant.MICS_SESSION_LEVELLIST, screeninglevels);
			int intRuleIdx = 0;
			rules = (List) session.get(MICSConstant.MICS_SESSION_RULES);
			if (rules == null) {
				rules = new ArrayList<AddCallScreening>();
				intRuleIdx = 1;
			} else {
				intRuleIdx = rules.size() + 1;
			}
			logger.debug("intRuleIdx: " + intRuleIdx);
			AddCallScreening currRule = new AddCallScreening();
			currRule.setActionType("ADD");
			currRule.setAllow(this.allow);
			if (this.screeningtype != null) {
				if (this.screeningtype.intValue()==1) {
					currRule.setCallpartynumber(this.calledpartynumber);
				} else {
					currRule.setCallpartynumber(this.callingpartynumber);
				}
			} else {
				//Set default for outgoing
				currRule.setCallpartynumber(this.calledpartynumber);
			}
			
			
			int checkCallPartyType = 0;
			if (companyOnNet) {
				checkCallPartyType = checkCallPartyType + 2;
			}
			if (virtualOnNet) {
				checkCallPartyType = checkCallPartyType + 4;
			}
			if (partnerOnNet) {
				checkCallPartyType = checkCallPartyType + 8;
			}
			if (communityOnNet) {
				checkCallPartyType = checkCallPartyType + 16;
			}
			if (homeNetwork) {
				checkCallPartyType = checkCallPartyType + 32;
			}
			if (national) {
				checkCallPartyType = checkCallPartyType + 64;
			}
			if (international) {
				checkCallPartyType = checkCallPartyType + 128;
			}
			if (shortcode) {
				checkCallPartyType = checkCallPartyType + 256;
			}
			if (other) {
				checkCallPartyType = checkCallPartyType + 512;
			}
			if (offNet) {
				checkCallPartyType = checkCallPartyType + 992;
			}
			currRule.setCallpartytype(new BigDecimal(checkCallPartyType));
			int checkBearerType = 0;
			if (voiceBearer) {
				checkBearerType = checkBearerType + 1;
			}
			if (videoBearer) {
				checkBearerType = checkBearerType + 2;
			}
			if (faxBearer) {
				checkBearerType = checkBearerType + 4;
			}
			if (dataBearer) {
				checkBearerType = checkBearerType + 8;
			}
			if (otherBearer) {
				checkBearerType = checkBearerType + 16;
			}
			if (smsBearer) {
				checkBearerType = checkBearerType + 32;
			}
			currRule.setRulekey(this.rulekey);
			currRule.setRulename(this.rulename);
			currRule.setBearerType(new BigDecimal(checkBearerType));
			currRule.setGlobalciprefix(this.globalciprefix);
			currRule.setVlrprefix(this.vlrprefix);
			currRule.setOrderindex(new BigDecimal(intRuleIdx));
			currRule.setProfileDesc(this.callscreeningdesc);
			currRule.setProfileName(this.callscreeningname);
			currRule.setScreeningType(this.screeningtype.intValue());
			currRule.setScreeningLevel(this.screeninglevel.intValue());
			currRule.setTimebandkey(this.timebandkey);
			currRule.setRejectionannoid(this.rejectionannoid);
			rules.add(currRule);
			session.put(MICSConstant.MICS_SESSION_RULES, rules);
			logger.debug("no of rules: "+rules.size());
			this.maxRules = rules.size();
			logger.debug("maxRules: " + this.maxRules);
			result = MICSConstant.MICS_SUCCESS;
			
		} catch (Exception e) {
			logger.error("Failed to add profile rule",e);
			addActionError("Failed to add Profile rule " + e.getMessage());
			result = MICSConstant.MICS_INPUT;
		} 
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public String delRule(){
		logger.info("delRule() ");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			timebands = micsCommonSessionHandler.getTimebands();
			session.put(MICSConstant.MICS_SESSION_TIMEBANDS, timebands);
			
			screeningtypes = micsCommonSessionHandler.getScreeningType();
			session.put(MICSConstant.MICS_SESSION_TYPELIST, screeningtypes);
			
			screeninglevels = micsCommonSessionHandler.getScreeningLevel();
			session.put(MICSConstant.MICS_SESSION_LEVELLIST, screeninglevels);
			int intRuleIdx = 0;
			rules = (List) session.get(MICSConstant.MICS_SESSION_RULES);
			//Check deleted rules;
			List tempList = new ArrayList<AddCallScreening>();
			for (Iterator iterDelRule = rules.iterator(); iterDelRule.hasNext();) {
				AddCallScreening currAddCallScreening = (AddCallScreening) iterDelRule.next();
				if (!currAddCallScreening.isRemove()) {
					tempList.add(currAddCallScreening);
				}  
			}
			//Reindex rules;
			int reIndex = 1;
			rules = null;
			rules = new ArrayList<AddCallScreening>();
			for (Iterator iterRule = tempList.iterator(); iterRule.hasNext();) {
				AddCallScreening currAddCallScreening = (AddCallScreening) iterRule.next();
				currAddCallScreening.setOrderindex(new BigDecimal(reIndex));
				rules.add(currAddCallScreening);
				reIndex++;
				
			}
			session.put(MICSConstant.MICS_SESSION_RULES, rules);
			logger.debug("no of rules: "+rules.size());
			this.maxRules = rules.size();
			logger.debug("maxRules: " + this.maxRules);
			result = MICSConstant.MICS_SUCCESS;
			
		} catch (Exception e) {
			logger.error("Failed to delete profile rule",e);
			addActionError("Failed to delete Profile rule " + e.getMessage());
			result = MICSConstant.MICS_INPUT;
		} 
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public String editDelRule(){
		logger.info("editDelRule() ");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			timebands = micsCommonSessionHandler.getTimebands();
			session.put(MICSConstant.MICS_SESSION_TIMEBANDS, timebands);
			
			screeningtypes = micsCommonSessionHandler.getScreeningType();
			session.put(MICSConstant.MICS_SESSION_TYPELIST, screeningtypes);
			
			screeninglevels = micsCommonSessionHandler.getScreeningLevel();
			session.put(MICSConstant.MICS_SESSION_LEVELLIST, screeninglevels);
			int intRuleIdx = 0;
			rules = (List) session.get(MICSConstant.MICS_SESSION_RULES);
			delRules = (List) session.get(MICSConstant.MICS_SESSION_DEL_RULES);
			if (delRules == null) {
				delRules = new ArrayList<AddCallScreening>();
			}
			//Check deleted rules;
			List tempList = new ArrayList<AddCallScreening>();
			for (Iterator iterDelRule = rules.iterator(); iterDelRule.hasNext();) {
				AddCallScreening currAddCallScreening = (AddCallScreening) iterDelRule.next();
				if (currAddCallScreening.isRemove()) {
					currAddCallScreening.setActionType("DELETE");
					delRules.add(currAddCallScreening);
				} else {
					//Keep the rule in tempList
					tempList.add(currAddCallScreening);
				}
			}
			
			//Reindex rules;
			int reIndex = 1;
			rules = null;
			rules = new ArrayList<AddCallScreening>();
			for (Iterator iterRule = tempList.iterator(); iterRule.hasNext();) {
				AddCallScreening currAddCallScreening = (AddCallScreening) iterRule.next();
				currAddCallScreening.setOrderindex(new BigDecimal(reIndex));
				if (currAddCallScreening.getActionType() == null || currAddCallScreening.getActionType().trim().equals("")) {
					currAddCallScreening.setActionType("UPDATE");
				}
				rules.add(currAddCallScreening);
				reIndex++;
				
			}
			tempList = null;
			session.put(MICSConstant.MICS_SESSION_RULES, rules);
			session.put(MICSConstant.MICS_SESSION_DEL_RULES, delRules);
			logger.debug("no of rules: "+rules.size());
			this.maxRules = rules.size();
			logger.debug("maxRules: " + this.maxRules);
			result = MICSConstant.MICS_SUCCESS;
			
		} catch (Exception e) {
			logger.error("Failed to delete profile rule",e);
			addActionError("Failed to delete Profile rule " + e.getMessage());
			result = MICSConstant.MICS_INPUT;
		} 
		return result;
	}
	
	public String moveUp(){
		logger.debug("moveUp " + this.targetIdx);
		String result = MICSConstant.MICS_ERROR;
		try {
			this.maxRules = maxRules;
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			timebands = micsCommonSessionHandler.getTimebands();
			session.put(MICSConstant.MICS_SESSION_TIMEBANDS, timebands);
			
			screeningtypes = micsCommonSessionHandler.getScreeningType();
			session.put(MICSConstant.MICS_SESSION_TYPELIST, screeningtypes);
			
			screeninglevels = micsCommonSessionHandler.getScreeningLevel();
			session.put(MICSConstant.MICS_SESSION_LEVELLIST, screeninglevels);
			
			if (this.callscreeningname == null || this.callscreeningname.equals("")) {
				throw new MICSException("Profile Name not set");
			}
			if (this.screeningtype == null) {
				throw new MICSException("Type not set");
			}
			if (this.screeninglevels == null) {
				throw new MICSException("Level not set");
			}
			rules = (List) session.get(MICSConstant.MICS_SESSION_RULES);
			if (rules != null) {
				AddCallScreening destRule = (AddCallScreening) rules.get(targetIdx-1);
				BigDecimal bdDesRuleOrdIdx = destRule.getOrderindex();
				AddCallScreening targetRule = (AddCallScreening) rules.get(targetIdx);
				BigDecimal bdTargetRuleOrdIdx = targetRule.getOrderindex();
				targetRule.setOrderindex(bdDesRuleOrdIdx);
				if (targetRule.getActionType() == null || targetRule.getActionType().trim().equals("")) {
					targetRule.setActionType("UPDATE");
				}
				destRule.setOrderindex(bdTargetRuleOrdIdx);
				if (destRule.getActionType() == null || destRule.getActionType().trim().equals("")) {
					destRule.setActionType("UPDATE");
				}
				rules.set(targetIdx-1, targetRule);
				rules.set(targetIdx,destRule);
				session.put(MICSConstant.MICS_SESSION_RULES, rules);
				result = MICSConstant.MICS_SUCCESS;
			} else {
				throw new MICSException("No Rule available for edit");
			}
			
		} catch (Exception e) {
			logger.error("Failed to move up profile rule",e);
			addActionError("Failed to move up rule " + e.getMessage());
			result = MICSConstant.MICS_INPUT;
		} 
		return result;
	}
	
	public String moveDown(){
		logger.debug("moveDown " + this.targetIdx);
		String result = MICSConstant.MICS_ERROR;
		try {
			this.maxRules = maxRules;
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			timebands = micsCommonSessionHandler.getTimebands();
			session.put(MICSConstant.MICS_SESSION_TIMEBANDS, timebands);
			
			screeningtypes = micsCommonSessionHandler.getScreeningType();
			session.put(MICSConstant.MICS_SESSION_TYPELIST, screeningtypes);
			
			screeninglevels = micsCommonSessionHandler.getScreeningLevel();
			session.put(MICSConstant.MICS_SESSION_LEVELLIST, screeninglevels);
			
			if (this.callscreeningname == null || this.callscreeningname.equals("")) {
				throw new MICSException("Profile Name not set");
			}
			if (this.screeningtype == null) {
				throw new MICSException("Type not set");
			}
			if (this.screeninglevels == null) {
				throw new MICSException("Level not set");
			}
			rules = (List) session.get(MICSConstant.MICS_SESSION_RULES);
			if (rules != null) {
				AddCallScreening destRule = (AddCallScreening) rules.get(targetIdx+1);
				BigDecimal bdDesRuleOrdIdx = destRule.getOrderindex();
				AddCallScreening targetRule = (AddCallScreening) rules.get(targetIdx);
				BigDecimal bdTargetRuleOrdIdx = targetRule.getOrderindex();
				targetRule.setOrderindex(bdDesRuleOrdIdx);
				if (targetRule.getActionType() == null || targetRule.getActionType().trim().equals("")) {
					targetRule.setActionType("UPDATE");
				}
				destRule.setOrderindex(bdTargetRuleOrdIdx);
				if (destRule.getActionType() == null || destRule.getActionType().trim().equals("")) {
					destRule.setActionType("UPDATE");
				}
				rules.set(targetIdx+1, targetRule);
				rules.set(targetIdx,destRule);
				session.put(MICSConstant.MICS_SESSION_RULES, rules);
				result = MICSConstant.MICS_SUCCESS;
			} else {
				throw new MICSException("No Rule available for edit");
			}
			
		} catch (Exception e) {
			logger.error("Failed to move up profile rule",e);
			addActionError("Failed to move up rule " + e.getMessage());
			result = MICSConstant.MICS_INPUT;
		} 
		return result;
	}
	
	public String initEditRule(){
		logger.debug("initEditRule " + this.targetIdx);
		String result = MICSConstant.MICS_ERROR;
		try {
			this.maxRules = maxRules;
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			timebands = micsCommonSessionHandler.getTimebands();
			session.put(MICSConstant.MICS_SESSION_TIMEBANDS, timebands);
			
			screeningtypes = micsCommonSessionHandler.getScreeningType();
			session.put(MICSConstant.MICS_SESSION_TYPELIST, screeningtypes);
			
			screeninglevels = micsCommonSessionHandler.getScreeningLevel();
			session.put(MICSConstant.MICS_SESSION_LEVELLIST, screeninglevels);
			
			if (this.callscreeningname == null || this.callscreeningname.equals("")) {
				throw new MICSException("Profile Name not set");
			}
			if (this.screeningtype == null) {
				throw new MICSException("Type not set");
			}
			if (this.screeninglevels == null) {
				throw new MICSException("Level not set");
			}
			rules = (List) session.get(MICSConstant.MICS_SESSION_RULES);
			if (rules != null) {
				AddCallScreening targetRule = (AddCallScreening) rules.get(targetIdx);
				logger.debug("targetRule: " + targetRule);
				this.orderindex = targetRule.getOrderindex();
				this.rulename = targetRule.getRulename();
				this.rulekey = targetRule.getRulekey();
				this.timebandkey = targetRule.getTimebandkey();
				this.vlrprefix = targetRule.getVlrprefix();
				this.globalciprefix = targetRule.getGlobalciprefix();
				//Call Party Type
				int currCallPartyType = 0;
				if (targetRule.getCallpartytype() != null) {
					currCallPartyType = targetRule.getCallpartytype().intValue();
				}
				
				if ((currCallPartyType&2) == 2) {
					companyOnNet = true;
				}
				if ((currCallPartyType&4) == 4) {
					virtualOnNet = true;
				}
				if ((currCallPartyType&8) == 8) {
					partnerOnNet = true;
				}
				if ((currCallPartyType&16) == 16) {
					communityOnNet = true;
				}
				if ((currCallPartyType&32) == 32) {
					homeNetwork = true;
				}
				if ((currCallPartyType&64) == 64) {
					national = true;
				}
				if ((currCallPartyType&128) == 128) {
					international = true;
				}
				if ((currCallPartyType&256) == 256) {
					shortcode = true;
				}
				if ((currCallPartyType&512) == 512) {
					other = true;
				}
				if ((currCallPartyType&992) == 992) {
					offNet = true;
				}
				this.calledpartynumber = targetRule.getCallpartynumber();
				this.callingpartynumber = targetRule.getCallpartynumber();
				this.allow = targetRule.isAllow();
				int currBearerType = 0;
				if (targetRule.getBearerType() != null) {
					currBearerType = targetRule.getBearerType().intValue();
				}
				if ((currBearerType&1) == 1) {
					voiceBearer = true;
				}
				if ((currBearerType&2) == 2) {
					videoBearer = true;
				}
				if ((currBearerType&4) == 4) {
					faxBearer = true;
				}
				if ((currBearerType&8) == 8) {
					dataBearer = true;
				}
				if ((currBearerType&16) == 16) {
					otherBearer = true;
				}
				if ((currBearerType&32) == 32) {
					smsBearer = true;
				}
				this.rejectionannoid = targetRule.getRejectionannoid();
				result = MICSConstant.MICS_SUCCESS;
			} else {
				throw new MICSException("No Rule available for edit");
			}
			
			
		} catch (Exception e) {
			logger.error("Failed to edit profile rule",e);
			addActionError("Failed to init edit rule " + e.getMessage());
			result = MICSConstant.MICS_INPUT;
		} 
		return result;
	}
	

	@SuppressWarnings("unchecked")
	public String editRule(){
		logger.info("editRule " + this.targetIdx);
		String result = MICSConstant.MICS_ERROR;
		try {
			this.maxRules = maxRules;
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			timebands = micsCommonSessionHandler.getTimebands();
			session.put(MICSConstant.MICS_SESSION_TIMEBANDS, timebands);
			
			screeningtypes = micsCommonSessionHandler.getScreeningType();
			session.put(MICSConstant.MICS_SESSION_TYPELIST, screeningtypes);
			
			screeninglevels = micsCommonSessionHandler.getScreeningLevel();
			session.put(MICSConstant.MICS_SESSION_LEVELLIST, screeninglevels);
			logger.debug("orderIndex: " + this.orderindex);
			rules = (List) session.get(MICSConstant.MICS_SESSION_RULES);
			if (rules == null) {
				throw new MICSException("No rule available for edit");
			}
			AddCallScreening currRule = (AddCallScreening) rules.get(this.targetIdx);
			if (currRule.getActionType() == null || currRule.getActionType().trim().equals("")) {
				currRule.setActionType("UPDATE");
			}
			currRule.setAllow(this.allow);
			logger.debug("Profile Type :"+this.screeningtype.intValue());
			if(this.screeningtype.intValue() == 1)
			currRule.setCallpartynumber(this.calledpartynumber);
			if(this.screeningtype.intValue() == 2)
			currRule.setCallpartynumber(this.callingpartynumber);			
			int checkCallPartyType = 0;
			if (companyOnNet) {
				checkCallPartyType = checkCallPartyType + 2;
			}
			if (virtualOnNet) {
				checkCallPartyType = checkCallPartyType + 4;
			}
			if (partnerOnNet) {
				checkCallPartyType = checkCallPartyType + 8;
			}
			if (communityOnNet) {
				checkCallPartyType = checkCallPartyType + 16;
			}
			if (homeNetwork) {
				checkCallPartyType = checkCallPartyType + 32;
			}
			if (national) {
				checkCallPartyType = checkCallPartyType + 64;
			}
			if (international) {
				checkCallPartyType = checkCallPartyType + 128;
			}
			if (shortcode) {
				checkCallPartyType = checkCallPartyType + 256;
			}
			if (other) {
				checkCallPartyType = checkCallPartyType + 512;
			}
			if (offNet) {
				checkCallPartyType = checkCallPartyType + 992;
			}
			currRule.setCallpartytype(new BigDecimal(checkCallPartyType));
			int checkBearerType = 0;
			if (voiceBearer) {
				checkBearerType = checkBearerType + 1;
			}	
			if (videoBearer) {
				checkBearerType = checkBearerType + 2;
			}
			if (faxBearer) {
				checkBearerType = checkBearerType + 4;
			}
			if (dataBearer) {
				checkBearerType = checkBearerType + 8;
			}
			if (otherBearer) {
				checkBearerType = checkBearerType + 16;
			}
			if (smsBearer) {
				checkBearerType = checkBearerType + 32;
			}
			currRule.setRulename(this.rulename);
			currRule.setRulekey(this.rulekey);
			currRule.setBearerType(new BigDecimal(checkBearerType));
			currRule.setGlobalciprefix(this.globalciprefix);
			currRule.setVlrprefix(this.vlrprefix);
			currRule.setOrderindex(this.orderindex);
			currRule.setProfileDesc(this.callscreeningdesc);
			currRule.setProfileName(this.callscreeningname);
			currRule.setScreeningType(this.screeningtype.intValue());
			currRule.setScreeningLevel(this.screeninglevel.intValue());
			currRule.setTimebandkey(this.timebandkey);
			currRule.setRejectionannoid(this.rejectionannoid);
			
			rules.set(this.targetIdx, currRule);
			session.put(MICSConstant.MICS_SESSION_RULES, rules);
			result = MICSConstant.MICS_SUCCESS;
			
		} catch (Exception e) {
			logger.error("Failed to edit profile rule",e);
			addActionError("Failed to edit Profile rule " + e.getMessage());
			result = MICSConstant.MICS_INPUT;
		} 
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public String create(){
		logger.info("create() "  + this.screeninglevel + " " + this.screeningtype );
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(profileCreate, loginWebUser.getRolekey())) {
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
					screeninglevels = (List<ScreeningLevel>) session.get(MICSConstant.MICS_SESSION_LEVELLIST);
					screeningtypes = (List<ScreeningType>) session.get(MICSConstant.MICS_SESSION_TYPELIST);
					timebands = micsCommonSessionHandler.getTimebands();
					session.put("timebands", timebands);
					Callscreening newCallScreening = new Callscreening();
					newCallScreening.setCallscreeningname(this.callscreeningname);
					newCallScreening.setCallscreeningdesc(this.callscreeningdesc);
					newCallScreening.setCompanykey("");
					newCallScreening.setScreeningtype(this.screeningtype);
					newCallScreening.setScreeninglevel(this.screeninglevel);
					//Get Login User from Session
					Webuser loginUser  = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
					newCallScreening.setUpdateuser(loginUser.getWebusername());
					newCallScreening.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
					sdf.format(new Date());
					String awalKey = sdf.format(new Date());
					logger.debug("Get new profile call screening key");
					String profileAvailableKey = micsCommonSessionHandler.getAvailableKey(awalKey, "callscreeningkey", "Callscreening");
					logger.debug("profileAvailableKey: "+profileAvailableKey);
					newCallScreening.setCallscreeningkey(profileAvailableKey);
					rules = (List) session.get(MICSConstant.MICS_SESSION_RULES);
					if (this.screeningtype.intValue() == MICSConstant.MICS_SCREENING_TYPE_INCOMING) {
						micsCommonSessionHandler.createIncomingScreeningProfile(newCallScreening, rules);
					} else {
						micsCommonSessionHandler.createOutgoingScreeningProfile(newCallScreening, rules);
					}
					addActionMessage("Screen Profile " + newCallScreening.getCallscreeningname() + " succefully added");
					Micslogger.log("Create","Profile",    newCallScreening.getCallscreeningname() + " created" , loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"SUCCESS");
					result = MICSConstant.MICS_SUCCESS;
				} else {
					addActionError("User not authorized");
					Micslogger.log("Create","Profile",    "Fail to create " + this.callscreeningname , loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"FAILED");
					result = MICSConstant.MICS_ERROR_NOT_AUTH;
				}
			} else {
				addActionError("User not logged in");
				Micslogger.log("Create","Profile",    "Fail to create " + this.callscreeningname ,"", "NoLog","FAILED");
				result = MICSConstant.MICS_ERROR_NOT_LOGGEDIN;
			}
			
		} catch (Exception e) {
			logger.error("Failed to create profile ",e);
			addActionError("Failed to create Profile " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
		
	}
	
	@SuppressWarnings("unchecked")
	public String edit(){
		logger.info("edit()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(profileCreate, loginWebUser.getRolekey())) {
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
					screeninglevels = (List<ScreeningLevel>) session.get(MICSConstant.MICS_SESSION_LEVELLIST);
					screeningtypes = (List<ScreeningType>) session.get(MICSConstant.MICS_SESSION_TYPELIST);
					Callscreening newCallScreening = micsCommonSessionHandler.getCallScreeningByKey(this.callscreeningkey);
					if (newCallScreening == null) {
						throw new MICSException("Call screening with key " + this.callscreeningkey+ " not exist");
					}
					newCallScreening.setCallscreeningname(this.callscreeningname);
					newCallScreening.setCallscreeningdesc(this.callscreeningdesc);
					//Get Login User from Session
					Webuser loginUser  = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
					newCallScreening.setUpdateuser(loginUser.getWebusername());
					newCallScreening.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
					
					timebands = micsCommonSessionHandler.getTimebands();
					session.put("timebands", timebands);
					rules = (List) session.get(MICSConstant.MICS_SESSION_RULES);
					List tempList = new ArrayList<AddCallScreening>();
					for (Iterator iterRules = rules.iterator(); iterRules
							.hasNext();) {
						AddCallScreening currRuleScreening = (AddCallScreening) iterRules.next();
						tempList.add(currRuleScreening);
					}
					delRules = (List) session.get(MICSConstant.MICS_SESSION_DEL_RULES);
					
					if (delRules != null) {
						for (Iterator iterDelRules = delRules.iterator(); iterDelRules
								.hasNext();) {
							AddCallScreening delCallScreening = (AddCallScreening) iterDelRules.next();
							tempList.add(delCallScreening);
							
						}
					}
					if (this.screeningtype.intValue() == MICSConstant.MICS_SCREENING_TYPE_INCOMING) {
						//Insert Incoming
						micsCommonSessionHandler.editIncomingProfile(newCallScreening, tempList);
					} else {
						//Insert Outgoing
						micsCommonSessionHandler.editOutgoingProfile(newCallScreening,tempList);
					}
					delRules = null;
					tempList = null;
					session.remove(MICSConstant.MICS_SESSION_DEL_RULES);
					addActionMessage("Screen Profile " + this.callscreeningkey + "" +this.callscreeningname + " succefully edited");
					Micslogger.log("Edit","Profile",    this.callscreeningname + " edited" , loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"SUCCESS");
					result = MICSConstant.MICS_SUCCESS;
				} else {
					addActionError("User not authorized");
					Micslogger.log("Edit","Profile",    "Fail to delete "+this.callscreeningname  , loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"FAILED");
					result = MICSConstant.MICS_ERROR_NOT_AUTH;
				}
			} else {
				addActionError("User not logged in");
				Micslogger.log("Edit","Profile",    "Fail to delete "+this.callscreeningname  , "", "NoLog","FAILED");
				result = MICSConstant.MICS_ERROR_NOT_LOGGEDIN;
			}
			
		} catch (Exception e) {
			logger.error("Failed to edit profile ",e);
			addActionError("Failed to edit Profile " + e.getMessage());
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
				if (micsCommonSessionHandler.isMenuAuthorized(profileView, loginWebUser.getRolekey())) {
					//Get Profile Information by Call Screening Key
					Callscreening editCallScreening = micsCommonSessionHandler.getCallScreeningByKey(this.callscreeningkey);
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

					timebands = micsCommonSessionHandler.getTimebands();
					session.put("timebands", timebands);
					//Initialize deleted rules from previous session if exist
					session.remove(MICSConstant.MICS_SESSION_DEL_RULES);
					delRules = null;
					//Get Available Screening Profile Type
					screeningtypes = micsCommonSessionHandler.getScreeningType();
					session.put(MICSConstant.MICS_SESSION_TYPELIST, screeningtypes);
					//Get Available Screening Profile Level
					screeninglevels = micsCommonSessionHandler.getScreeningLevel();
					session.put(MICSConstant.MICS_SESSION_LEVELLIST, screeninglevels);
					//Get Profile Information by Call Screening Key
					
					this.callscreeningkey = editCallScreening.getCallscreeningkey();
					this.callscreeningname = editCallScreening.getCallscreeningname();
					this.callscreeningdesc = editCallScreening.getCallscreeningdesc();
					this.companykey = editCallScreening.getCompanykey();
					this.screeninglevel = editCallScreening.getScreeninglevel();
					this.screeningtype = editCallScreening.getScreeningtype();
					this.callscreeningkey = editCallScreening.getCallscreeningkey();
					if (this.screeningtype.intValue() == MICSConstant.MICS_SCREENING_TYPE_INCOMING) {
						//Get Incoming Details
						List icsList = micsCommonSessionHandler.getIncomingCallScreeningByKey(this.callscreeningkey);
						rules = new ArrayList<AddCallScreening>();
						for (Iterator iterator = icsList.iterator(); iterator.hasNext();) {
							Incomingcallscreening editICS = (Incomingcallscreening) iterator.next();
							AddCallScreening currCallScreening = new AddCallScreening();
							currCallScreening.setRulekey(editICS.getRulekey());
							currCallScreening.setRulename(editICS.getRulename());
							currCallScreening.setOrderindex(editICS.getOrderindex());
							currCallScreening.setTimebandkey(editICS.getTimebandkey());
							currCallScreening.setVlrprefix(editICS.getVlrprefix());
							currCallScreening.setCallpartytype(editICS.getCallingpartytype());
							currCallScreening.setCallpartynumber(editICS.getCallingpartynumber());
							
							if (editICS.getAllow().equals(new BigDecimal(1))) {
								currCallScreening.setAllow(true);
								
							} else {
								currCallScreening.setAllow(false);
							}
							currCallScreening.setBearerType(editICS.getBearertype());
							currCallScreening.setRejectionannoid(editICS.getRejectionannoid());
							currCallScreening.setActionType("");
							rules.add(currCallScreening);
						}
						this.maxRules = rules.size();
						session.put(MICSConstant.MICS_SESSION_RULES, rules);
						
					} else {
						//Get Outgoing Details
						List ocsList = micsCommonSessionHandler.getOutgoingCallScreeningByKey(this.callscreeningkey);
						rules = new ArrayList<AddCallScreening>();
						for (Iterator iterator = ocsList.iterator(); iterator
								.hasNext();) {
							Outgoingcallscreening editOCS = (Outgoingcallscreening) iterator.next();
							AddCallScreening currCallScreening = new AddCallScreening();
							currCallScreening.setRulekey(editOCS.getRulekey());
							currCallScreening.setRulename(editOCS.getRulename());
							currCallScreening.setOrderindex(editOCS.getOrderindex());
							currCallScreening.setTimebandkey(editOCS.getTimebandkey());
							currCallScreening.setVlrprefix(editOCS.getVlrprefix());
							currCallScreening.setGlobalciprefix(editOCS.getGlobalciprefix());
							currCallScreening.setCallpartytype(editOCS.getCalledpartytype());
							currCallScreening.setCallpartynumber(editOCS.getCalledpartynumber());
							BigDecimal bdAllow = editOCS.getAllow();
							if (bdAllow != null) {
								if (bdAllow.equals(new BigDecimal(1))) {
									currCallScreening.setAllow(true);
								} else {
									currCallScreening.setAllow(false);
								}
							} else {
								currCallScreening.setAllow(false);
							}
							
							currCallScreening.setBearerType(editOCS.getBearertype());
							currCallScreening.setRejectionannoid(editOCS.getRejectionannoid());
							currCallScreening.setActionType("");
							rules.add(currCallScreening);
							
						}
						this.maxRules = rules.size();
						session.put(MICSConstant.MICS_SESSION_RULES, rules);
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
			logger.error("Failed to view Profile",e);
			addActionError("Failed to view Profile");
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
	}
	
	public String screeningTypeChange(){
		logger.info("screeningTypeChange()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(profileCreate, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
						this.tselAdmin = true;
//						Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
//						this.companykey = currCompany.getCompanykey();
//						announcements = micsCommonSessionHandler.getCompanyAnno(companykey);
					} else {
						this.tselAdmin = false;
//						Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
//						this.companykey = currCompany.getCompanykey();
//						announcements = micsCommonSessionHandler.getCompanyAnno(companykey);
					}
//					session.put("announcements", announcements);
					timebands = micsCommonSessionHandler.getTimebands();
					session.put("timebands", timebands);
					//Get Available Screening Profile Type
					screeningtypes = micsCommonSessionHandler.getScreeningType();
					session.put(MICSConstant.MICS_SESSION_TYPELIST, screeningtypes);
					//Get Available Screening Profile Level
					screeninglevels = micsCommonSessionHandler.getScreeningLevel();
					session.put(MICSConstant.MICS_SESSION_LEVELLIST, screeninglevels);
					result = MICSConstant.MICS_SUCCESS;
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
			logger.error("Failed to initialize Profile  admin",e);
			addActionError("Failed to process your Profile administration request " + e.getMessage());
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
				if (micsCommonSessionHandler.isMenuAuthorized(profileSearch, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					//Get Available Screening Profile Type
					screeningtypes = micsCommonSessionHandler.getScreeningType();
					session.put(MICSConstant.MICS_SESSION_TYPELIST, screeningtypes);
					logger.debug("screeningtypes: " + screeningtypes);
					//Get Available Screening Profile Level
					screeninglevels = micsCommonSessionHandler.getScreeningLevel();
					session.put(MICSConstant.MICS_SESSION_LEVELLIST, screeninglevels);
					logger.debug("screeninglevels: " + screeninglevels);
					logger.debug("search CallScreening");
					logger.debug(loginWebUser.getWebusername() + "authorized to search profile " + callscreeningname + " "+ searchScreeningtype + " " + screeninglevel);
					//Search Company
					if (callscreeningname == null) {
						callscreeningname = "";
					}
					
					Integer intScreeningtype = null;
					if (searchScreeningtype != null) {
						if ( searchScreeningtype.intValue() == 0) {
							intScreeningtype = null;
						} else {
							intScreeningtype = searchScreeningtype.intValue();
						}
					} else {
						searchScreeningtype = new BigDecimal(0);
						intScreeningtype = null;
					}
					
					Integer searchscreeninglevel = null;
					if(screeninglevel != null) {
						
						if ( screeninglevel.intValue() == 0) {
							searchscreeninglevel = null;
						} else {
							searchscreeninglevel =screeninglevel.intValue();
						}
					} else {
						screeninglevel = new BigDecimal(0);
						searchscreeninglevel = null;
					}
					callscreenings = null;
					if (searchCnt>0) {
						callscreenings = micsCommonSessionHandler.searchCallScreening(callscreeningname, intScreeningtype, searchscreeninglevel);
					}
					searchCnt++;
					
					logger.debug("search CallScreening success");
					if (micsCommonSessionHandler.isMenuAuthorized(profileDelete, loginWebUser.getRolekey())) {
						this.deleteAllowed = true;
					} else {
						this.deleteAllowed = false;
					}
					if (micsCommonSessionHandler.isMenuAuthorized(profileEdit, loginWebUser.getRolekey())) {
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
			logger.error("Failed to search Profile",e);
			addActionError("Failed to search Profile " + e.getMessage());
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
				if (micsCommonSessionHandler.isMenuAuthorized(profileDelete, loginWebUser.getRolekey())) {
//					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)) {
					if (loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_TELKOMSEL)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_ADMIN_SYS)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_OPERATION)||
							loginWebUser.getRolekey().equalsIgnoreCase(MICSConstant.MICS_ROLE_HELPDESK)) {
						this.tselAdmin = true;
//						Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
//						this.companykey = currCompany.getCompanykey();
//						announcements = micsCommonSessionHandler.getCompanyAnno(companykey);
					} else {
						this.tselAdmin = false;
//						Company currCompany = (Company) session.get(MICSConstant.MICS_SESSION_CURRCOMPANY);
//						this.companykey = currCompany.getCompanykey();
//						announcements = micsCommonSessionHandler.getCompanyAnno(companykey);
					}
//					session.put("announcements", announcements);
					timebands = micsCommonSessionHandler.getTimebands();
					session.put("timebands", timebands);
					//Get Available Screening Profile Type
					screeningtypes = micsCommonSessionHandler.getScreeningType();
					session.put(MICSConstant.MICS_SESSION_TYPELIST, screeningtypes);
					//Get Available Screening Profile Level
					screeninglevels = micsCommonSessionHandler.getScreeningLevel();
					session.put(MICSConstant.MICS_SESSION_LEVELLIST, screeninglevels);
					//Get Profile Information by Call Screening Key
					Callscreening editCallScreening = micsCommonSessionHandler.getCallScreeningByKey(this.callscreeningkey);
					this.callscreeningkey = editCallScreening.getCallscreeningkey();
					this.callscreeningname = editCallScreening.getCallscreeningname();
					this.callscreeningdesc = editCallScreening.getCallscreeningdesc();
					this.companykey = editCallScreening.getCompanykey();
					this.screeninglevel = editCallScreening.getScreeninglevel();
					this.screeningtype = editCallScreening.getScreeningtype();
					this.callscreeningkey = editCallScreening.getCallscreeningkey();
					if (this.screeningtype.intValue() == MICSConstant.MICS_SCREENING_TYPE_INCOMING) {
						//Get Incoming Details
						List icsList = micsCommonSessionHandler.getIncomingCallScreeningByKey(this.callscreeningkey);
						rules = new ArrayList<AddCallScreening>();
						for (Iterator iterator = icsList.iterator(); iterator.hasNext();) {
							Incomingcallscreening editICS = (Incomingcallscreening) iterator.next();
							AddCallScreening currCallScreening = new AddCallScreening();
							currCallScreening.setRulekey(editICS.getRulekey());
							currCallScreening.setRulename(editICS.getRulename());
							currCallScreening.setOrderindex(editICS.getOrderindex());
							currCallScreening.setTimebandkey(editICS.getTimebandkey());
							currCallScreening.setVlrprefix(editICS.getVlrprefix());
							currCallScreening.setCallpartytype(editICS.getCallingpartytype());
							currCallScreening.setCallpartynumber(editICS.getCallingpartynumber());
							
							if (editICS.getAllow().equals(new BigDecimal(1))) {
								currCallScreening.setAllow(true);
								
							} else {
								currCallScreening.setAllow(false);
							}
							currCallScreening.setBearerType(editICS.getBearertype());
							currCallScreening.setRejectionannoid(editICS.getRejectionannoid());
							currCallScreening.setActionType("");
							rules.add(currCallScreening);
						}
						session.put(MICSConstant.MICS_SESSION_RULES, rules);
						
					} else {
						//Get Outgoing Details
						List ocsList = micsCommonSessionHandler.getOutgoingCallScreeningByKey(this.callscreeningkey);
						rules = new ArrayList<AddCallScreening>();
						for (Iterator iterator = ocsList.iterator(); iterator
								.hasNext();) {
							Outgoingcallscreening editOCS = (Outgoingcallscreening) iterator.next();
							AddCallScreening currCallScreening = new AddCallScreening();
							currCallScreening.setRulekey(editOCS.getRulekey());
							currCallScreening.setRulename(editOCS.getRulename());
							currCallScreening.setOrderindex(editOCS.getOrderindex());
							currCallScreening.setTimebandkey(editOCS.getTimebandkey());
							currCallScreening.setVlrprefix(editOCS.getVlrprefix());
							currCallScreening.setGlobalciprefix(editOCS.getGlobalciprefix());
							currCallScreening.setCallpartytype(editOCS.getCalledpartytype());
							currCallScreening.setCallpartynumber(editOCS.getCalledpartynumber());

							if (editOCS.getAllow().equals(new BigDecimal(1))) {
								currCallScreening.setAllow(true);
							} else {
								currCallScreening.setAllow(false);
							}
							currCallScreening.setBearerType(editOCS.getBearertype());
							currCallScreening.setRejectionannoid(editOCS.getRejectionannoid());
							currCallScreening.setActionType("");
							rules.add(currCallScreening);
							
						}
						session.put(MICSConstant.MICS_SESSION_RULES, rules);
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
			logger.error("Failed to delete Profile",e);
			addActionError("Failed to delete Profile " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public String delete(){
		logger.info("delete()");
		String result = MICSConstant.MICS_ERROR;
		try {
			//Get Login User from Session
			Webuser loginUser  = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginUser != null) {
				MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
				if (micsCommonSessionHandler.isMenuAuthorized(profileDelete, loginUser.getRolekey())) {
					screeninglevels = (List<ScreeningLevel>) session.get(MICSConstant.MICS_SESSION_LEVELLIST);
					screeningtypes = (List<ScreeningType>) session.get(MICSConstant.MICS_SESSION_TYPELIST);
					Callscreening delCallScreening = micsCommonSessionHandler.getCallScreeningByKey(this.callscreeningkey);
					this.callscreeningname = delCallScreening.getCallscreeningname();
					this.callscreeningkey = delCallScreening.getCallscreeningkey();
					timebands = micsCommonSessionHandler.getTimebands();
					session.put("timebands", timebands);
					if (this.screeningtype.intValue() == MICSConstant.MICS_SCREENING_TYPE_INCOMING) {
						//Delete Incoming
						List delICS = micsCommonSessionHandler.getIncomingCallScreeningByKey(this.callscreeningkey);
						if (delICS != null) {
							micsCommonSessionHandler.deleteIncomingCallScreening(delCallScreening,delICS);
						} else {
							micsCommonSessionHandler.deleteCallScreening(delCallScreening);
						}
						
					} else {
						List delOCS = micsCommonSessionHandler.getOutgoingCallScreeningByKey(this.callscreeningkey);
						if (delOCS != null) {
							micsCommonSessionHandler.deleteOutgoingCallScreening(delCallScreening,delOCS );
						} else {
							micsCommonSessionHandler.deleteCallScreening(delCallScreening);
						}
						
					}
					addActionMessage("Screen Profile " + this.callscreeningkey + "" +this.callscreeningname + " succefully deleted");
					Micslogger.log("Delete","Profile",    this.callscreeningname + " deleted" , loginUser.getCompanykey(), loginUser.getWebusername(),"SUCCESS");
					result = MICSConstant.MICS_SUCCESS;
				} else {
					addActionError("User not authorized");
					Micslogger.log("Delete","Profile",    "Fail to delete " + this.callscreeningname , loginUser.getCompanykey(), loginUser.getWebusername(),"FAILED");
					result = MICSConstant.MICS_ERROR_NOT_AUTH;
				}
			} else {
				addActionError("User not logged in");
				Micslogger.log("Delete","Profile",    "Fail to create " + this.callscreeningname , "", "NoLog","FAILED");
				result = MICSConstant.MICS_ERROR_NOT_LOGGEDIN;
			}
			
			
		} catch (Exception e) {
			logger.error("Failed to delete profile ",e);
			addActionError("Failed to delete Profile " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
		
	}
	
	
	public void setSession(Map<String, Object> session) {
		this.session = session;

	}
	

	/**
	 * @return the callscreeningkey
	 */
	public String getCallscreeningkey() {
		return callscreeningkey;
	}

	/**
	 * @param callscreeningkey the callscreeningkey to set
	 */
	public void setCallscreeningkey(String callscreeningkey) {
		this.callscreeningkey = callscreeningkey;
	}

	/**
	 * @return the callscreeningname
	 */
	public String getCallscreeningname() {
		return callscreeningname;
	}

	/**
	 * @param callscreeningname the callscreeningname to set
	 */
	public void setCallscreeningname(String callscreeningname) {
		this.callscreeningname = callscreeningname;
	}
	

	/**
	 * @return the callscreeningdesc
	 */
	public String getCallscreeningdesc() {
		return callscreeningdesc;
	}

	/**
	 * @param callscreeningdesc the callscreeningdesc to set
	 */
	public void setCallscreeningdesc(String callscreeningdesc) {
		this.callscreeningdesc = callscreeningdesc;
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
	 * @return the screeningtype
	 */
	public BigDecimal getScreeningtype() {
		return screeningtype;
	}

	/**
	 * @param screeningtype the screeningtype to set
	 */
	public void setScreeningtype(BigDecimal screeningtype) {
		this.screeningtype = screeningtype;
	}

	/**
	 * @return the screeninglevel
	 */
	public BigDecimal getScreeninglevel() {
		return screeninglevel;
	}

	/**
	 * @param screeninglevel the screeninglevel to set
	 */
	public void setScreeninglevel(BigDecimal screeninglevel) {
		this.screeninglevel = screeninglevel;
	}

	/**
	 * @return the screeningtypes
	 */
	public List getScreeningtypes() {
		return screeningtypes;
	}

	/**
	 * @param screeningtypes the screeningtypes to set
	 */
	public void setScreeningtypes(List screeningtypes) {
		this.screeningtypes = screeningtypes;
	}

	/**
	 * @return the screeninglevels
	 */
	public List getScreeninglevels() {
		return screeninglevels;
	}

	/**
	 * @param screeninglevels the screeninglevels to set
	 */
	public void setScreeninglevels(List screeninglevels) {
		this.screeninglevels = screeninglevels;
	}

	/**
	 * @return the rulekey
	 */
	public String getRulekey() {
		return rulekey;
	}

	/**
	 * @param rulekey the rulekey to set
	 */
	public void setRulekey(String rulekey) {
		this.rulekey = rulekey;
	}

	/**
	 * @return the rulename
	 */
	public String getRulename() {
		return rulename;
	}

	/**
	 * @param rulename the rulename to set
	 */
	public void setRulename(String rulename) {
		this.rulename = rulename;
	}

	/**
	 * @return the orderindex
	 */
	public BigDecimal getOrderindex() {
		return orderindex;
	}

	/**
	 * @param orderindex the orderindex to set
	 */
	public void setOrderindex(BigDecimal orderindex) {
		this.orderindex = orderindex;
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
	 * @return the vlrprefix
	 */
	public String getVlrprefix() {
		return vlrprefix;
	}

	/**
	 * @param vlrprefix the vlrprefix to set
	 */
	public void setVlrprefix(String vlrprefix) {
		this.vlrprefix = vlrprefix;
	}

	/**
	 * @return the globalciprefix
	 */
	public String getGlobalciprefix() {
		return globalciprefix;
	}

	/**
	 * @param globalciprefix the globalciprefix to set
	 */
	public void setGlobalciprefix(String globalciprefix) {
		this.globalciprefix = globalciprefix;
	}

	/**
	 * @return the calledpartytype
	 */
	public BigDecimal getCalledpartytype() {
		return calledpartytype;
	}

	/**
	 * @param calledpartytype the calledpartytype to set
	 */
	public void setCalledpartytype(BigDecimal calledpartytype) {
		this.calledpartytype = calledpartytype;
	}

	/**
	 * @return the calledpartynumber
	 */
	public String getCalledpartynumber() {
		return calledpartynumber;
	}

	/**
	 * @param calledpartynumber the calledpartynumber to set
	 */
	public void setCalledpartynumber(String calledpartynumber) {
		this.calledpartynumber = calledpartynumber;
	}



	/**
	 * @return the allow
	 */
	public boolean isAllow() {
		return allow;
	}

	/**
	 * @param allow the allow to set
	 */
	public void setAllow(boolean allow) {
		this.allow = allow;
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
	 * @return the timebands
	 */
	public List getTimebands() {
		return timebands;
	}

	/**
	 * @param timebands the timebands to set
	 */
	public void setTimebands(List timebands) {
		this.timebands = timebands;
	}

	/**
	 * @return the callingpartytypes
	 */
	public List getCallingpartytypes() {
		return callingpartytypes;
	}

	/**
	 * @param callingpartytypes the callingpartytypes to set
	 */
	public void setCallingpartytypes(List callingpartytypes) {
		this.callingpartytypes = callingpartytypes;
	}

	/**
	 * @return the calledpartytypes
	 */
	public List getCalledpartytypes() {
		return calledpartytypes;
	}

	/**
	 * @param calledpartytypes the calledpartytypes to set
	 */
	public void setCalledpartytypes(List calledpartytypes) {
		this.calledpartytypes = calledpartytypes;
	}

	/**
	 * @return the announcements
	 */
	public List getAnnouncements() {
		return announcements;
	}

	/**
	 * @param announcements the announcements to set
	 */
	public void setAnnouncements(List announcements) {
		this.announcements = announcements;
	}

	/**
	 * @return the companyOnNet
	 */
	public boolean isCompanyOnNet() {
		return companyOnNet;
	}

	/**
	 * @param companyOnNet the companyOnNet to set
	 */
	public void setCompanyOnNet(boolean companyOnNet) {
		this.companyOnNet = companyOnNet;
	}

	/**
	 * @return the virtualOnNet
	 */
	public boolean isVirtualOnNet() {
		return virtualOnNet;
	}

	/**
	 * @param virtualOnNet the virtualOnNet to set
	 */
	public void setVirtualOnNet(boolean virtualOnNet) {
		this.virtualOnNet = virtualOnNet;
	}

	/**
	 * @return the partnerOnNet
	 */
	public boolean isPartnerOnNet() {
		return partnerOnNet;
	}

	/**
	 * @param partnerOnNet the partnerOnNet to set
	 */
	public void setPartnerOnNet(boolean partnerOnNet) {
		this.partnerOnNet = partnerOnNet;
	}

	/**
	 * @return the communityOnNet
	 */
	public boolean isCommunityOnNet() {
		return communityOnNet;
	}

	/**
	 * @param communityOnNet the communityOnNet to set
	 */
	public void setCommunityOnNet(boolean communityOnNet) {
		this.communityOnNet = communityOnNet;
	}

	/**
	 * @return the homeNetwork
	 */
	public boolean isHomeNetwork() {
		return homeNetwork;
	}

	/**
	 * @param homeNetwork the homeNetwork to set
	 */
	public void setHomeNetwork(boolean homeNetwork) {
		this.homeNetwork = homeNetwork;
	}

	/**
	 * @return the national
	 */
	public boolean isNational() {
		return national;
	}

	/**
	 * @param national the national to set
	 */
	public void setNational(boolean national) {
		this.national = national;
	}

	/**
	 * @return the international
	 */
	public boolean isInternational() {
		return international;
	}

	/**
	 * @param international the international to set
	 */
	public void setInternational(boolean international) {
		this.international = international;
	}

	/**
	 * @return the shortcode
	 */
	public boolean isShortcode() {
		return shortcode;
	}

	/**
	 * @param shortcode the shortcode to set
	 */
	public void setShortcode(boolean shortcode) {
		this.shortcode = shortcode;
	}

	/**
	 * @return the other
	 */
	public boolean isOther() {
		return other;
	}

	/**
	 * @param other the other to set
	 */
	public void setOther(boolean other) {
		this.other = other;
	}
	
	

	/**
	 * @return the offnet
	 */
	public boolean isOffNet() {
		return offNet;
	}

	/**
	 * @param offnet the offnet to set
	 */
	public void setOffNet(boolean offNet) {
		this.offNet = offNet;
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
	 * @return the voiceBearer
	 */
	public boolean isVoiceBearer() {
		return voiceBearer;
	}

	/**
	 * @param voiceBearer the voiceBearer to set
	 */
	public void setVoiceBearer(boolean voiceBearer) {
		this.voiceBearer = voiceBearer;
	}

	/**
	 * @return the videoBearer
	 */
	public boolean isVideoBearer() {
		return videoBearer;
	}

	/**
	 * @param videoBearer the videoBearer to set
	 */
	public void setVideoBearer(boolean videoBearer) {
		this.videoBearer = videoBearer;
	}

	/**
	 * @return the faxBearer
	 */
	public boolean isFaxBearer() {
		return faxBearer;
	}

	/**
	 * @param faxBearer the faxBearer to set
	 */
	public void setFaxBearer(boolean faxBearer) {
		this.faxBearer = faxBearer;
	}

	/**
	 * @return the dataBearer
	 */
	public boolean isDataBearer() {
		return dataBearer;
	}

	/**
	 * @param dataBearer the dataBearer to set
	 */
	public void setDataBearer(boolean dataBearer) {
		this.dataBearer = dataBearer;
	}

	/**
	 * @return the otherBearer
	 */
	public boolean isOtherBearer() {
		return otherBearer;
	}

	/**
	 * @param otherBearer the otherBearer to set
	 */
	public void setOtherBearer(boolean otherBearer) {
		this.otherBearer = otherBearer;
	}
	

	/**
	 * @return the smsBearer
	 */
	public boolean isSmsBearer() {
		return smsBearer;
	}

	/**
	 * @param smsBearer the smsBearer to set
	 */
	public void setSmsBearer(boolean smsBearer) {
		this.smsBearer = smsBearer;
	}

	/**
	 * @return the rejectionannoid
	 */
	public BigDecimal getRejectionannoid() {
		return rejectionannoid;
	}

	/**
	 * @param rejectionannoid the rejectionannoid to set
	 */
	public void setRejectionannoid(BigDecimal rejectionannoid) {
		this.rejectionannoid = rejectionannoid;
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
	 * @return the callingpartytype
	 */
	public BigDecimal getCallingpartytype() {
		return callingpartytype;
	}

	/**
	 * @param callingpartytype the callingpartytype to set
	 */
	public void setCallingpartytype(BigDecimal callingpartytype) {
		this.callingpartytype = callingpartytype;
	}

	/**
	 * @return the callingpartynumber
	 */
	public String getCallingpartynumber() {
		return callingpartynumber;
	}

	/**
	 * @param callingpartynumber the callingpartynumber to set
	 */
	public void setCallingpartynumber(String callingpartynumber) {
		this.callingpartynumber = callingpartynumber;
	}

	
	/**
	 * @return the callscreenings
	 */
	public List getCallscreenings() {
		return callscreenings;
	}

	/**
	 * @param callscreenings the callscreenings to set
	 */
	public void setCallscreenings(List callscreenings) {
		this.callscreenings = callscreenings;
	}

	
	/**
	 * @return the rules
	 */
	public List getRules() {
		return rules;
	}

	/**
	 * @param rules the rules to set
	 */
	public void setRules(List rules) {
		this.rules = rules;
	}

	
	/**
	 * @return the targetIdx
	 */
	public int getTargetIdx() {
		return targetIdx;
	}

	/**
	 * @param targetIdx the targetIdx to set
	 */
	public void setTargetIdx(int targetIdx) {
		this.targetIdx = targetIdx;
	}
	
	

	/**
	 * @return the maxRules
	 */
	public int getMaxRules() {
		return maxRules;
	}

	/**
	 * @param maxRules the maxRules to set
	 */
	public void setMaxRules(int maxRules) {
		this.maxRules = maxRules;
	}
	
	
	/**
	 * @return the delRules
	 */
	public List getDelRules() {
		return delRules;
	}

	/**
	 * @param delRules the delRules to set
	 */
	public void setDelRules(List delRules) {
		this.delRules = delRules;
	}
	

	/**
	 * @return the searchScreeningtype
	 */
	public BigDecimal getSearchScreeningtype() {
		return searchScreeningtype;
	}

	/**
	 * @param searchScreeningtype the searchScreeningtype to set
	 */
	public void setSearchScreeningtype(BigDecimal searchScreeningtype) {
		this.searchScreeningtype = searchScreeningtype;
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







	//CallScreening
	private String callscreeningkey;
	private String callscreeningname;
	private String callscreeningdesc;
	private String companykey;
	private BigDecimal screeningtype;
	private BigDecimal searchScreeningtype;
	private BigDecimal screeninglevel;
	
	//Outgoing
	private String rulekey;
	private String rulename;
	private BigDecimal orderindex;
	private String timebandkey;
	private String vlrprefix;
	private String globalciprefix;
	private BigDecimal calledpartytype;
	private String calledpartynumber;
	private boolean allow;
	private BigDecimal rejectionannoid;
	private String updateuser;
	private Timestamp updatetimestamp;
	//Incoming
	private BigDecimal callingpartytype;
	private String callingpartynumber;
	
	private List rules;
	private List delRules;
	private List screeningtypes;
	private List screeninglevels;
	private List companies;
	private List timebands;
	private List callingpartytypes;
	private List calledpartytypes;
	private List announcements;
	//Call Party Type
	private boolean companyOnNet;
	private boolean virtualOnNet;
	private boolean partnerOnNet;
	private boolean communityOnNet;
	private boolean homeNetwork;
	private boolean national;
	private boolean international;
	private boolean shortcode;
	private boolean other;
	private boolean offNet;
	//Bearer Type
	private boolean voiceBearer;
	private boolean videoBearer;
	private boolean faxBearer;
	private boolean dataBearer;
	private boolean otherBearer;
	private boolean smsBearer;
	
	private int targetIdx;
	private int maxRules;
	
	private List callscreenings;
	private boolean tselAdmin;
	private boolean deleteAllowed;
	private boolean editAllowed;
	private String profileCreate = "2017050701";
	private String profileSearch = "2017050702";
	private String profileEdit = "2017050703";
	private String profileDelete = "2017050704";
	private String profileCopy = "2017050705";
	private String profileAssign = "2017050706";
	private String profileView = "2017050707";
	private int searchCnt;
	private Map<String, Object> session;
	private static final Logger logger = Logger.getLogger(ProfileAction.class);

	@Override
	public void prepare() throws Exception {
		MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
		timebands = micsCommonSessionHandler.getTimebands();
		session.put(MICSConstant.MICS_SESSION_TIMEBANDS, timebands);
		//Get Available Screening Profile Type
		screeningtypes = micsCommonSessionHandler.getScreeningType();
		//Set Outgoing as default
		if (this.screeningtype == null) {
			this.screeningtype = new BigDecimal(1);
		}
		session.put(MICSConstant.MICS_SESSION_TYPELIST, screeningtypes);
		//Get Available Screening Profile Level
		screeninglevels = micsCommonSessionHandler.getScreeningLevel();
		session.put(MICSConstant.MICS_SESSION_LEVELLIST, screeninglevels);
		rules = (List) session.get(MICSConstant.MICS_SESSION_RULES);
		
	}
}
