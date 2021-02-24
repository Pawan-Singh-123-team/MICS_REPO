package com.nsn.telkomsel.mics.action;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import org.apache.struts2.interceptor.SessionAware;

import com.nsn.telkomsel.mics.handler.MICSCommonSessionHandler;
import com.nsn.telkomsel.mics.orahbm.Timeband;
import com.nsn.telkomsel.mics.orahbm.TimebandId;
import com.nsn.telkomsel.mics.orahbm.Webuser;
import com.nsn.telkomsel.mics.util.DaySelection;
import com.nsn.telkomsel.mics.util.MICSConstant;
import com.nsn.telkomsel.mics.util.Micslogger;
import com.opensymphony.xwork2.ActionSupport;

public class TimebandAction extends ActionSupport implements SessionAware {


	private static final long serialVersionUID = -251111768025266566L;

	public String initCreate(){
		logger.info("initCreate()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(timeBandCreate, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					daySelections = micsCommonSessionHandler.getDaySelection();
					session.put(MICSConstant.MICS_DAY_SELECTION, daySelections);
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
			logger.error("Failed to initialize Timeband admin");
			addActionError("Failed to process your Timeband administration request");
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
				logger.debug("Logged in " + loginWebUser.getWebusername());
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(timeBandCreate, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug("Authorized to create new timeband");
					daySelections = micsCommonSessionHandler.getDaySelection();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
					sdf.format(new Date());
					String awalKey = sdf.format(new Date());
					logger.debug("Get new timeband key");
					String timebandAvailableKey = micsCommonSessionHandler.getAvailableKey(awalKey, "id.timebandkey", "Timeband");
					logger.debug("AvailableTimebandKey: "+timebandAvailableKey);
					TimebandId newTimebandId = new TimebandId();
					newTimebandId.setTimebandkey(timebandAvailableKey);
					newTimebandId.setTimebandname(this.timebandName);
					Timeband newTimeband = new Timeband();
					newTimeband.setId(newTimebandId);
					newTimeband.setTimebanddescription(this.timebandDescription);
					
					int timebandDaySelection = 0;
					if (sunday) {
						timebandDaySelection = timebandDaySelection + 2;
					}
					if (monday) {
						timebandDaySelection = timebandDaySelection + 4;
					}
					if (tuesday) {
						timebandDaySelection = timebandDaySelection + 8;
					}
					if (wednesday) {
						timebandDaySelection = timebandDaySelection + 16;
					}
					if (thursday) {
						timebandDaySelection = timebandDaySelection + 32;
					}
					if (friday) {
						timebandDaySelection = timebandDaySelection + 64;
					}
					if (saturday) {
						timebandDaySelection = timebandDaySelection + 128;
					}
					newTimeband.setDayselecetion(new BigDecimal(timebandDaySelection));
					if (this.allDay) {
						newTimeband.setAllday(new BigDecimal(1));
					} else {
						newTimeband.setAllday(new BigDecimal(0));
					}
					if (this.forever) {
						newTimeband.setForever(new BigDecimal(1));
					} else {
						newTimeband.setForever(new BigDecimal(0));
					}
					if (invertTimeSelection) {
						newTimeband.setInverttimeselection(new BigDecimal(1));
					} else {
						newTimeband.setInverttimeselection(new BigDecimal(0));
					}
					newTimeband.setBeginhour(new BigDecimal(this.beginHour));
					newTimeband.setBeginminute(new BigDecimal(this.beginMinute));
					newTimeband.setEndhour(new BigDecimal(this.endHour));
					newTimeband.setEndminute(new BigDecimal(this.endMinute));
					sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
					if (effectiveStartTime != null) {
						String formattedStartTime = sdf.format(effectiveStartTime);
						newTimeband.setEffectivestarttime(formattedStartTime);
					}
					if (effectiveStopTime != null) {
						String formattedStopTime = sdf.format(effectiveStopTime);
						newTimeband.setEffectivestoptime(formattedStopTime);
					}
					
					
					
					newTimeband.setUpdateuser(loginWebUser.getWebusername());
					newTimeband.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
					micsCommonSessionHandler.createTimeband(newTimeband);
					logger.debug("Timeband created " + newTimeband.getId().getTimebandkey());
					addActionMessage("Timeband "+ newTimeband.getId().getTimebandname() + " with ID " + newTimeband.getId().getTimebandkey() + " created successfully");
					Micslogger.log("Create","Timeband",   newTimeband.getId().getTimebandname() + " created"  , loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"SUCCESS");
					result = MICSConstant.MICS_SUCCESS;
				} else {
					addActionError("User not authorized");
					Micslogger.log("Create","Timeband",   "Fail to create timeband " + this.timebandName  , loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"FAILED");
					result = MICSConstant.MICS_ERROR_NOT_AUTH;
				}
			} else {
				addActionError("User not logged in");
				Micslogger.log("Create","Timeband",   "Fail to create timeband " + this.timebandName  , "", "NoLog","FAILED");
				result = MICSConstant.MICS_ERROR_NOT_LOGGEDIN;
			}
		} catch (Exception e) {
			logger.error("Failed to create new Timeband",e);
			addActionError("Failed to create new Timeband request "+ e.getMessage());
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
				if (micsCommonSessionHandler.isMenuAuthorized(timeBandSearch, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug(loginWebUser.getWebusername() + "authorized to search timeband " + timebandName + " "+timebandDescription);
					//Search Company
					if (timebandName == null) {
						timebandName = "";
					}
					if (timebandDescription == null) {
						timebandDescription = "";
					}
					timebands = null;
					if (searchCnt>0) {
						timebands = micsCommonSessionHandler.searchTimebands(timebandName, timebandDescription);
					}
					searchCnt++;
					
					logger.debug("search Timeband success");
					if (micsCommonSessionHandler.isMenuAuthorized(timeBandDelete, loginWebUser.getRolekey())) {
						this.deleteAllowed = true;
					} else {
						this.deleteAllowed = false;
					}
					if (micsCommonSessionHandler.isMenuAuthorized(timeBandEdit, loginWebUser.getRolekey())) {
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
			logger.error("Failed to search Timeband",e);
			addActionError("Failed to search Timeband " + e.getMessage());
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
				if (micsCommonSessionHandler.isMenuAuthorized(timeBandDelete, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					daySelections = micsCommonSessionHandler.getDaySelection();
					session.put(MICSConstant.MICS_DAY_SELECTION, daySelections);
					//Get Timeband Info by timebandkey;
					if (timebandKey==null) {
						throw new Exception("Timebandkey is compulsory!");
					}
					Timeband currTimeband = micsCommonSessionHandler.getTimebandByKey(timebandKey);
					if (currTimeband == null) {
						throw new  Exception("Can not find Timeband with key " + timebandKey);
					}
					timebandKey = currTimeband.getId().getTimebandkey();
					timebandName = currTimeband.getId().getTimebandname();
					timebandDescription = currTimeband.getTimebanddescription();
					int currDBDaySelection = currTimeband.getDayselecetion().intValue();
					if ((currDBDaySelection & 2) == 2) {
						sunday = true;
					}
					if ((currDBDaySelection & 4) == 4) {
						monday = true;
					}
					if ((currDBDaySelection & 8) == 8) {
						tuesday = true;
					}
					if ((currDBDaySelection & 16) == 16) {
						wednesday = true;
					}
					if ((currDBDaySelection & 32) == 32) {
						thursday = true;
					}
					if ((currDBDaySelection & 64) == 64) {
						friday = true;
					}
					if ((currDBDaySelection & 128) == 128) {
						saturday = true;
					}
					if (currTimeband.getAllday().equals(new BigDecimal(1))) {
						allDay = true;
					}
					this.beginHour = currTimeband.getBeginhour().intValue();
					this.beginMinute = currTimeband.getBeginminute().intValue();
					this.endHour = currTimeband.getEndhour().intValue();
					this.endMinute = currTimeband.getEndminute().intValue();
					if (currTimeband.getInverttimeselection().equals(new BigDecimal(1))) {
						invertTimeSelection = true;
					}
					if (currTimeband.getForever().equals(new BigDecimal(1))) {
						forever = true;
					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
					effectiveStartTime = sdf.parse(currTimeband.getEffectivestarttime());
					effectiveStopTime = sdf.parse(currTimeband.getEffectivestoptime());
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
			logger.error("Failed to initialize Timeband delete",e);
			addActionError("Failed to process your Timeband delete request " + e.getMessage());
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
				logger.debug("Logged in " + loginWebUser.getWebusername());
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(timeBandDelete, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug("Authorized to delete timeband");
					//Get Timeband Info by timebandkey;
					if (timebandKey==null) {
						throw new Exception("Timebandkey is compulsory!");
					}
					Timeband currTimeband = micsCommonSessionHandler.getTimebandByKey(timebandKey);
					if (currTimeband == null) {
						throw new  Exception("Can not find Timeband with key " + timebandKey);
					}
					micsCommonSessionHandler.deleteTimeband(currTimeband);
					logger.debug("Timeband deleted " + timebandKey);
					addActionMessage("Timeband with ID " + timebandKey + " deleted successfully");
					Micslogger.log("Create","Timeband",   currTimeband.getId().getTimebandname() + " deleted"  , loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"SUCCESS");
					result = MICSConstant.MICS_SUCCESS;
				} else {
					addActionError("User not authorized");
					Micslogger.log("Create","Timeband",   "Fail to delete timeband "+ this.timebandKey  , loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"FAILED");
					result = MICSConstant.MICS_ERROR_NOT_AUTH;
				}
			} else {
				addActionError("User not logged in");
				Micslogger.log("Create","Timeband",   "Fail to delete timeband "+ this.timebandKey  , "", "NoLog","FAILED");
				result = MICSConstant.MICS_ERROR_NOT_LOGGEDIN;
			}
		} catch (Exception e) {
			logger.error("Failed to delete Timeband",e);
			addActionError("Failed to delete request "+ e.getMessage());
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
				if (micsCommonSessionHandler.isMenuAuthorized(timeBandEdit, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					daySelections = micsCommonSessionHandler.getDaySelection();
					session.put(MICSConstant.MICS_DAY_SELECTION, daySelections);
					//Get Timeband Info by timebandkey;
					if (timebandKey==null) {
						throw new Exception("Timebandkey is compulsory!");
					}
					Timeband currTimeband = micsCommonSessionHandler.getTimebandByKey(timebandKey);
					if (currTimeband == null) {
						throw new  Exception("Can not find Timeband with key " + timebandKey);
					}
					timebandKey = currTimeband.getId().getTimebandkey();
					timebandName = currTimeband.getId().getTimebandname();
					timebandDescription = currTimeband.getTimebanddescription();
					int currDBDaySelection = 0;
					if (currTimeband.getDayselecetion()!= null) {
						currDBDaySelection = currTimeband.getDayselecetion().intValue();
					}
					if ((currDBDaySelection & 2) == 2) {
						sunday = true;
					}
					if ((currDBDaySelection & 4) == 4) {
						monday = true;
					}
					if ((currDBDaySelection & 8) == 8) {
						tuesday = true;
					}
					if ((currDBDaySelection & 16) == 16) {
						wednesday = true;
					}
					if ((currDBDaySelection & 32) == 32) {
						thursday = true;
					}
					if ((currDBDaySelection & 64) == 64) {
						friday = true;
					}
					if ((currDBDaySelection & 128) == 128) {
						saturday = true;
					}
					if (currTimeband.getAllday().equals(new BigDecimal(1))) {
						allDay = true;
					}
					this.beginHour = currTimeband.getBeginhour().intValue();
					this.beginMinute = currTimeband.getBeginminute().intValue();
					this.endHour = currTimeband.getEndhour().intValue();
					this.endMinute = currTimeband.getEndminute().intValue();
					if (currTimeband.getInverttimeselection().equals(new BigDecimal(1))) {
						invertTimeSelection = true;
					}
					if (currTimeband.getForever().equals(new BigDecimal(1))) {
						forever = true;
					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
					String strEffStartTime = currTimeband.getEffectivestarttime();
					if (strEffStartTime != null && !strEffStartTime.trim().equals("")) {
						effectiveStartTime = sdf.parse(strEffStartTime);
					}
					String strEffStopTime = currTimeband.getEffectivestoptime();
					if (strEffStopTime != null && !strEffStopTime.trim().equals("")) {
						effectiveStopTime = sdf.parse(strEffStopTime);
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
			logger.error("Failed to initialize Timeband edit",e);
			addActionError("Failed to process your Timeband edit request " + e.getMessage());
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
				logger.debug("Logged in " + loginWebUser.getWebusername());
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(timeBandEdit, loginWebUser.getRolekey())) {
					//Authorize do some stuff here
					logger.debug("Authorized to edit timeband");
					daySelections = micsCommonSessionHandler.getDaySelection();
					TimebandId newTimebandId = new TimebandId();
					newTimebandId.setTimebandkey(this.timebandKey);
					newTimebandId.setTimebandname(this.timebandName);
					Timeband timeband = new Timeband();
					timeband.setId(newTimebandId);
					timeband.setTimebanddescription(this.timebandDescription);
					
					int timebandDaySelection = 0;
					if (sunday) {
						timebandDaySelection = timebandDaySelection + 2;
					}
					if (monday) {
						timebandDaySelection = timebandDaySelection + 4;
					}
					if (tuesday) {
						timebandDaySelection = timebandDaySelection + 8;
					}
					if (wednesday) {
						timebandDaySelection = timebandDaySelection + 16;
					}
					if (thursday) {
						timebandDaySelection = timebandDaySelection + 32;
					}
					if (friday) {
						timebandDaySelection = timebandDaySelection + 64;
					}
					if (saturday) {
						timebandDaySelection = timebandDaySelection + 128;
					}
					timeband.setDayselecetion(new BigDecimal(timebandDaySelection));
					if (this.allDay) {
						timeband.setAllday(new BigDecimal(1));
					} else {
						timeband.setAllday(new BigDecimal(0));
					}
					if (this.forever) {
						timeband.setForever(new BigDecimal(1));
					} else {
						timeband.setForever(new BigDecimal(0));
					}
					if (invertTimeSelection) {
						timeband.setInverttimeselection(new BigDecimal(1));
					} else {
						timeband.setInverttimeselection(new BigDecimal(0));
					}
					timeband.setBeginhour(new BigDecimal(this.beginHour));
					timeband.setBeginminute(new BigDecimal(this.beginMinute));
					timeband.setEndhour(new BigDecimal(this.endHour));
					timeband.setEndminute(new BigDecimal(this.endMinute));
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
					if (effectiveStartTime != null) {
						String formattedStartTime = sdf.format(effectiveStartTime);
						timeband.setEffectivestarttime(formattedStartTime);
					}
					if (effectiveStopTime != null) {
						String formattedStopTime = sdf.format(effectiveStopTime);
						timeband.setEffectivestoptime(formattedStopTime);
					}
					
					timeband.setUpdateuser(loginWebUser.getWebusername());
					timeband.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
					micsCommonSessionHandler.editTimeband(timeband);
					logger.debug("Timeband edited " + timeband.getId().getTimebandkey());
					addActionMessage("Timeband "+ timeband.getId().getTimebandname() + " with ID " + timeband.getId().getTimebandkey() + " edited successfully");
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
			logger.error("Failed to edit Timeband",e);
			addActionError("Failed to editTimeband request "+ e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} 
		return result;
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
	 * @return the timebandKey
	 */
	public String getTimebandKey() {
		return timebandKey;
	}
	/**
	 * @param timebandKey the timebandKey to set
	 */
	public void setTimebandKey(String timebandKey) {
		this.timebandKey = timebandKey;
	}
	/**
	 * @return the timebandName
	 */
	public String getTimebandName() {
		return timebandName;
	}
	/**
	 * @param timebandName the timebandName to set
	 */
	public void setTimebandName(String timebandName) {
		this.timebandName = timebandName;
	}
	/**
	 * @return the timebandDescription
	 */
	public String getTimebandDescription() {
		return timebandDescription;
	}
	/**
	 * @param timebandDescription the timebandDescription to set
	 */
	public void setTimebandDescription(String timebandDescription) {
		this.timebandDescription = timebandDescription;
	}
	/**
	 * @return the daySelection
	 */
	public String getDaySelection() {
		return daySelection;
	}
	/**
	 * @param daySelection the daySelection to set
	 */
	public void setDaySelection(String daySelection) {
		this.daySelection = daySelection;
	}
	/**
	 * @return the allDay
	 */
	public boolean getAllDay() {
		return allDay;
	}
	/**
	 * @param allDay the allDay to set
	 */
	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}
	/**
	 * @return the beginHour
	 */
	public int getBeginHour() {
		return beginHour;
	}
	/**
	 * @param beginHour the beginHour to set
	 */
	public void setBeginHour(int beginHour) {
		this.beginHour = beginHour;
	}
	/**
	 * @return the beginMinute
	 */
	public int getBeginMinute() {
		return beginMinute;
	}
	/**
	 * @param beginMinute the beginMinute to set
	 */
	public void setBeginMinute(int beginMinute) {
		this.beginMinute = beginMinute;
	}
	/**
	 * @return the endHour
	 */
	public int getEndHour() {
		return endHour;
	}
	/**
	 * @param endHour the endHour to set
	 */
	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}
	/**
	 * @return the endMinute
	 */
	public int getEndMinute() {
		return endMinute;
	}
	/**
	 * @param endMinute the endMinute to set
	 */
	public void setEndMinute(int endMinute) {
		this.endMinute = endMinute;
	}
	/**
	 * @return the invertTimeSelection
	 */
	public boolean getInvertTimeSelection() {
		return invertTimeSelection;
	}
	/**
	 * @param invertTimeSelection the invertTimeSelection to set
	 */
	public void setInvertTimeSelection(boolean invertTimeSelection) {
		this.invertTimeSelection = invertTimeSelection;
	}
	/**
	 * @return the forever
	 */
	public boolean getForever() {
		return forever;
	}
	/**
	 * @param forever the forever to set
	 */
	public void setForever(boolean forever) {
		this.forever = forever;
	}
	/**
	 * @return the effectiveStartTime
	 */
	public Date getEffectiveStartTime() {
		return effectiveStartTime;
	}
	/**
	 * @param effectiveStartTime the effectiveStartTime to set
	 */
	public void setEffectiveStartTime(Date effectiveStartTime) {
		this.effectiveStartTime = effectiveStartTime;
	}
	/**
	 * @return the effectiveStopTime
	 */
	public Date getEffectiveStopTime() {
		return effectiveStopTime;
	}
	/**
	 * @param effectiveStopTime the effectiveStopTime to set
	 */
	public void setEffectiveStopTime(Date effectiveStopTime) {
		this.effectiveStopTime = effectiveStopTime;
	}
	/**
	 * @return the updateUser
	 */
	public String getUpdateUser() {
		return updateUser;
	}
	/**
	 * @param updateUser the updateUser to set
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	/**
	 * @return the updateTimestamp
	 */
	public Timestamp getUpdateTimestamp() {
		return updateTimestamp;
	}
	/**
	 * @param updateTimestamp the updateTimestamp to set
	 */
	public void setUpdateTimestamp(Timestamp updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
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
	 * @return the daySelections
	 */
	public List getDaySelections() {
		return daySelections;
	}


	/**
	 * @param daySelections the daySelections to set
	 */
	public void setDaySelections(List daySelections) {
		this.daySelections = daySelections;
	}
	




	/**
	 * @return the selectedDaySelections
	 */
	public String[] getSelectedDaySelections() {
		return selectedDaySelections;
	}

	/**
	 * @param selectedDaySelections the selectedDaySelections to set
	 */
	public void setSelectedDaySelections(String[] selectedDaySelections) {
		this.selectedDaySelections = selectedDaySelections;
	}
	
	




	/**
	 * @return the sunday
	 */
	public boolean isSunday() {
		return sunday;
	}

	/**
	 * @param sunday the sunday to set
	 */
	public void setSunday(boolean sunday) {
		this.sunday = sunday;
	}

	/**
	 * @return the monday
	 */
	public boolean isMonday() {
		return monday;
	}

	/**
	 * @param monday the monday to set
	 */
	public void setMonday(boolean monday) {
		this.monday = monday;
	}

	/**
	 * @return the tuesday
	 */
	public boolean isTuesday() {
		return tuesday;
	}

	/**
	 * @param tuesday the tuesday to set
	 */
	public void setTuesday(boolean tuesday) {
		this.tuesday = tuesday;
	}

	/**
	 * @return the wednesday
	 */
	public boolean isWednesday() {
		return wednesday;
	}

	/**
	 * @param wednesday the wednesday to set
	 */
	public void setWednesday(boolean wednesday) {
		this.wednesday = wednesday;
	}

	/**
	 * @return the thursday
	 */
	public boolean isThursday() {
		return thursday;
	}

	/**
	 * @param thursday the thursday to set
	 */
	public void setThursday(boolean thursday) {
		this.thursday = thursday;
	}

	/**
	 * @return the friday
	 */
	public boolean isFriday() {
		return friday;
	}

	/**
	 * @param friday the friday to set
	 */
	public void setFriday(boolean friday) {
		this.friday = friday;
	}

	/**
	 * @return the saturday
	 */
	public boolean isSaturday() {
		return saturday;
	}

	/**
	 * @param saturday the saturday to set
	 */
	public void setSaturday(boolean saturday) {
		this.saturday = saturday;
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










	private String timeBandCreate = "2017050801";
	private String timeBandSearch = "2017050802";
	private String timeBandEdit = "2017050803";
	private String timeBandDelete = "2017050804";
	private String timebandKey;
	private String timebandName;
	private String timebandDescription;
	private String daySelection;
	private List daySelections;
	private List timebands;
	private String[] selectedDaySelections;
	private boolean allDay;
	private boolean sunday;
	private boolean monday;
	private boolean tuesday;
	private boolean wednesday;
	private boolean thursday;
	private boolean friday;
	private boolean saturday;
	private int beginHour;
	private int beginMinute;
	private int endHour;
	private int endMinute;
	private boolean invertTimeSelection;
	private boolean forever;
	private boolean deleteAllowed;
	private boolean editAllowed;
	private Date effectiveStartTime;
	private Date effectiveStopTime;
	private String updateUser;
	private Timestamp updateTimestamp;
	private int searchCnt;
	private Map<String,Object> session;
	private static final Logger logger = Logger.getLogger(TimebandAction.class);
}
