package com.nsn.telkomsel.mics.handler;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.nsn.telkomsel.mics.MICSException;
import com.nsn.telkomsel.mics.orahbm.Announcement;
import com.nsn.telkomsel.mics.orahbm.Callscreening;
import com.nsn.telkomsel.mics.orahbm.Community;
import com.nsn.telkomsel.mics.orahbm.Communitymember;
import com.nsn.telkomsel.mics.orahbm.Company;
import com.nsn.telkomsel.mics.orahbm.Huntinggroup;
import com.nsn.telkomsel.mics.orahbm.Huntingmember;
import com.nsn.telkomsel.mics.orahbm.Huntingschedule;
import com.nsn.telkomsel.mics.orahbm.Incomingcallscreening;
import com.nsn.telkomsel.mics.orahbm.MICSOraSessionFactory;
import com.nsn.telkomsel.mics.orahbm.Micsgroup;
import com.nsn.telkomsel.mics.orahbm.Micslog;
import com.nsn.telkomsel.mics.orahbm.Micspermission;
import com.nsn.telkomsel.mics.orahbm.Micsuser;
import com.nsn.telkomsel.mics.orahbm.Micsusernumber;
import com.nsn.telkomsel.mics.orahbm.Outgoingcallscreening;
import com.nsn.telkomsel.mics.orahbm.Partnership;
import com.nsn.telkomsel.mics.orahbm.Role;
import com.nsn.telkomsel.mics.orahbm.Switchboard;
import com.nsn.telkomsel.mics.orahbm.Switchboardmenu;
import com.nsn.telkomsel.mics.orahbm.Switchboardsubmenu;
import com.nsn.telkomsel.mics.orahbm.Timeband;
import com.nsn.telkomsel.mics.orahbm.Webuser;
import com.nsn.telkomsel.mics.util.AddCallScreening;
import com.nsn.telkomsel.mics.util.AddHuntingSchedule;
import com.nsn.telkomsel.mics.util.AddNumberRequest;
import com.nsn.telkomsel.mics.util.AddUserRequest;
import com.nsn.telkomsel.mics.util.BearerType;
import com.nsn.telkomsel.mics.util.DaySelection;
import com.nsn.telkomsel.mics.util.HuntingOption;
import com.nsn.telkomsel.mics.util.Language;
import com.nsn.telkomsel.mics.util.MICSConstant;
import com.nsn.telkomsel.mics.util.NumberType;
import com.nsn.telkomsel.mics.util.ProvisioningRequest;
import com.nsn.telkomsel.mics.util.ProvisioningResponse;
import com.nsn.telkomsel.mics.util.ScreeningLevel;
import com.nsn.telkomsel.mics.util.ScreeningType;
import com.nsn.telkomsel.mics.util.SwitchboardEntry;
import com.nsn.telkomsel.mics.util.SwitchboardType;
import com.nsn.telkomsel.mics.util.UserType;
import com.nsn.telkomsel.mics.util.UserTypeEnum;
/**
 * MICSCommonSessionHandler.java
 * 
 * @author mulia
 * @version $Id: MICSCommonSessionHandler.java,v 1.1.4.3 2019/03/22 10:06:01 cvsuser Exp $
 */
public class MICSCommonSessionHandler {
	
	public Webuser getActiveUser(String userName, String userPassword)throws MICSException{
		logger.debug("getActiveUser -> userName: " + userName );
		Webuser result = null;
		Session session = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery("SELECT webuser FROM Webuser as webuser WHERE webuser.webusername = :userName AND webuser.webuserpassword = :userPassword");
			query.setString("userName", userName);
			query.setString("userPassword", userPassword);
			logger.debug("queryString: " + query.getQueryString());
			logger.debug("parameters: "+query.getNamedParameters());
			result = (Webuser) query.uniqueResult();
			logger.debug("result: " + result);
		} catch (Exception e) {
			result = null;
			throw new MICSException("Failed to get user profile for user: " +userName,e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	public Webuser getActiveSSOUser(String tokenUser)throws MICSException{
		logger.debug("getActiveSSOUser -> tokenUser: " + tokenUser );
		Webuser result = null;
		Session session = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery("SELECT webuser FROM Webuser as webuser WHERE webuser.ssotoken = :ssotoken");
			query.setString("ssotoken", tokenUser);
			logger.debug("queryString: " + query.getQueryString());
			logger.debug("parameters: "+query.getNamedParameters());
			result = (Webuser) query.uniqueResult();
			logger.debug("result: " + result);
		} catch (Exception e) {
			result = null;
			throw new MICSException("Failed to get user profile for user with token: " +tokenUser,e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	public Callscreening getCallScreeningByKey(String screeningKey) throws MICSException {
		logger.debug("getCallScreeningByKey -> Screening Key: " + screeningKey );
		Callscreening result = null;
		Session session = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery("select callscreening from Callscreening as callscreening where callscreening.callscreeningkey = :callScreeningKey");
			query.setString("callScreeningKey", screeningKey);
			logger.debug("queryString: " + query.getQueryString());
			logger.debug("parameters: "+query.getNamedParameters());
			result = (Callscreening) query.uniqueResult();
			logger.debug("result: " + result);
		} catch (Exception e) {
			result = null;
			throw new MICSException("Failed to get call screening with screening key: " +screeningKey,e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public List getCallScreening() throws MICSException {
		logger.debug("getCallScreening()");
		List result = null;
		Session session = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery("select callscreening from Callscreening as callscreening order by callscreening.callscreeningkey");
			logger.debug("queryString: " + query.getQueryString());
			result = query.list();
			logger.debug("result: " + result);
		} catch (Exception e) {
			result = null;
			throw new MICSException("Failed to get call screenings",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public List getIncomingCallScreeningByKey(String screeningKey) throws MICSException {
		logger.debug("getIncomingCallScreeningByKey -> Screening Key: " + screeningKey );
		List result = null;
		Session session = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery("select incomingcallscreening from Incomingcallscreening as incomingcallscreening where incomingcallscreening.callscreeningkey = :callScreeningKey order by incomingcallscreening.orderindex asc");
			query.setString("callScreeningKey", screeningKey);
			logger.debug("queryString: " + query.getQueryString());
			logger.debug("parameters: "+query.getNamedParameters());
			result = query.list();
			logger.debug("result: " + result);
		} catch (Exception e) {
			result = null;
			throw new MICSException("Failed to get Incomingcallscreening with screening key: " +screeningKey,e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public List getOutgoingCallScreeningByKey(String screeningKey) throws MICSException {
		logger.debug("getOutgoingCallScreeningByKey -> Screening Key: " + screeningKey );
		List result = null;
		Session session = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery("select outgoingcallscreening from Outgoingcallscreening as outgoingcallscreening where outgoingcallscreening.callscreeningkey= :callScreeningKey order by outgoingcallscreening.orderindex asc");
			query.setString("callScreeningKey", screeningKey);
			logger.debug("queryString: " + query.getQueryString());
			logger.debug("parameters: "+query.getNamedParameters());
			result = query.list(); 
			logger.debug("result: " + result);
		} catch (Exception e) {
			result = null;
			throw new MICSException("Failed to get Outgoingcallscreening with screening key: " +screeningKey,e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public Webuser getUserByKey(String webUserKey)throws MICSException{
		logger.debug("getUserByKey -> User Key: " + webUserKey );
		Webuser result = null;
		Session session = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery("SELECT webuser FROM Webuser as webuser WHERE webuser.webuserkey = :webUserKey");
			query.setString("webUserKey", webUserKey);
			logger.debug("queryString: " + query.getQueryString());
			logger.debug("parameters: "+query.getNamedParameters());
			result = (Webuser) query.uniqueResult();
			logger.debug("result: " + result);
		} catch (Exception e) {
			result = null;
			throw new MICSException("Failed to get user  with user key: " +webUserKey,e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public Role getRoleByKey(String roleKey)throws MICSException{
		logger.debug("getRoleByKey -> roleKey: " + roleKey );
		Role result = null;
		Session session = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery("SELECT role FROM Role as role WHERE role.rolekey = :roleKey");
			query.setString("roleKey", roleKey);
			logger.debug("queryString: " + query.getQueryString());
			logger.debug("parameters: "+query.getNamedParameters());
			result = (Role) query.uniqueResult();
			logger.debug("result: " + result);
		} catch (Exception e) {
			result = null;
			throw new MICSException("Failed to get user role for roleKey: " +roleKey,e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
		
	@SuppressWarnings("rawtypes")
	public List getMenusByRole(String roleKey) throws MICSException{
		logger.debug("getMenusByRole --> roleKey = " + roleKey);
		List result = null;
		Session session = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery("SELECT micsmenu from Micsmenu as micsmenu,Micspermission as micspermission WHERE micsmenu.micsmenukey = micspermission.micsmenukey and micsmenu.display='1' and micspermission.rolekey = :roleKey and micspermission.accessflag = '1' order by micsmenu.micsmenukey asc");
			query.setString("roleKey", roleKey);
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result);
		} catch (Exception e) {
			result = null;
			throw new MICSException("Failed to get user menu for roleKey: " +roleKey,e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public boolean userCompanyExist(String companyKey)throws MICSException{
		logger.debug("userCompanyExist --> company key " + companyKey );
		boolean result = false;
		Session session = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			logger.debug("session: " + session);
			Query query = session.createQuery("select count(micsuser) from Micsuser as micsuser where micsuser.companykey = :companyKey");
			query.setString("companyKey", companyKey);
			Integer countUser = (Integer) query.uniqueResult();
			logger.debug("countUser: " + countUser);
			if (countUser.intValue() > 0) {
				result = true;
			} else {
				result = false;
			}
			
		} catch (Exception e) {
			result = false;
			logger.error("Failed to check user for company with key: " + companyKey);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e2) {
				logger.error("Failed to close hibernate session",e2);
			} 
		}
		return result;
	}
	
	public boolean isMenuAuthorized(String menuKey,String roleKey) throws MICSException{
		logger.debug("isMenuAuthorized --> menuKey = " + menuKey + " roleKey = " + roleKey );
		boolean result = false;
		Session session = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			logger.debug("session: " + session);
			Query query = session.createQuery("select micspermission from Micspermission as micspermission where micspermission.micsmenukey = :menuKey and micspermission.rolekey = :roleKey and micspermission.accessflag ='1'");
			query.setString("menuKey", menuKey);
			query.setString("roleKey", roleKey);
			query.setMaxResults(1);
			Micspermission micspermission = (Micspermission) query.uniqueResult();
			if (micspermission != null) {
				logger.debug("menukey: " + menuKey + " roleKey: " + roleKey + " Authorized");
				result = true;
			} else { 
				logger.debug("menukey: " + menuKey + " roleKey: " + roleKey + " Not Authorized");
				result = false;
			}
		} catch (Exception e) {
			result = false;
			logger.error("Failed to check menu authorization for menuKey: " + menuKey + " roleKey:" + roleKey);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getScreeningLevel() throws MICSException{
		logger.debug("getScreeningLevel");
		List result = null;
		try {
			result = new Vector();
			ScreeningLevel screenLevelCompany = new ScreeningLevel(1,"Company");
			ScreeningLevel screenLevelGroup = new ScreeningLevel(2,"Group");
			ScreeningLevel screenLevelUser = new ScreeningLevel(3,"User");
			
			result.add(screenLevelCompany);
			result.add(screenLevelGroup);
			result.add(screenLevelUser);
			
		} catch (Exception e) {
			// handle exception
			result = null;
		} finally {
			// Finalize
		}	
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getHuntingOptions() throws MICSException{
		logger.debug("getHuntingOptions()");
		List result = null;
		try {
			result = new Vector();
			HuntingOption huntingOptionFixed = new HuntingOption(1,"Fixed");
			HuntingOption huntingOptionRoundRobin = new HuntingOption(2,"RoundRobin");
			HuntingOption huntingOptionLongestIdle = new HuntingOption(3,"LongestIdle");
			HuntingOption huntingOptionParallel= new HuntingOption(4,"Parallel");
			
			
			result.add(huntingOptionFixed);
			result.add(huntingOptionRoundRobin);
			result.add(huntingOptionLongestIdle);
			result.add(huntingOptionParallel);
			
		} catch (Exception e) {
			// handle exception
			result = null;
		} finally {
			// Finalize
		}	
		return result;
	}
	
	public List getUserHuntingOptions() throws MICSException{
		logger.debug("getUserHuntingOptions()");
		List result = null;
		try {
			result = new Vector();
			HuntingOption huntingOptionDisabled = new HuntingOption(0,"Disabled");
			HuntingOption huntingOptionFixed = new HuntingOption(1,"Fixed");
			HuntingOption huntingOptionMobileFirst = new HuntingOption(2,"Mobile First");
			HuntingOption huntingOptionSIPFirst= new HuntingOption(3,"SIP First");
			HuntingOption huntingOptionLastAnswer= new HuntingOption(4,"Last Answer First");
			HuntingOption huntingOptionParallel= new HuntingOption(5,"Parallel");
			
			result.add(huntingOptionDisabled);
			result.add(huntingOptionFixed);
			result.add(huntingOptionMobileFirst);
			result.add(huntingOptionSIPFirst);
			result.add(huntingOptionLastAnswer);
			result.add(huntingOptionParallel);
			
		} catch (Exception e) {
			// handle exception
			result = null;
		} finally {
			// Finalize
		}	
		return result;

	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getTimeWindows() throws MICSException{
		logger.debug("getTimeWindows()");
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery("select timeband from Timeband as timeband order by timeband.id.timebandkey asc");
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result);

		} catch (Exception e) {
			result = null;
			logger.error("get time window list failed", e);
			throw new MICSException("Failed to get time window list ",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public List getUserTypes() throws MICSException{
		logger.debug("getUserTypes()");
		List result = null;
		try {
			result = new Vector();
			UserType userReguler = new UserType(0,"Reguler");
			UserType userTypeOnNet = new UserType(1,"OnNet");
			UserType userTypeVirtualOnNet = new UserType(2,"Virtual OnNet");
			result.add(userReguler);
			result.add(userTypeOnNet);
			result.add(userTypeVirtualOnNet);
		} catch (Exception e) {
			result = null;
		} finally {
			// TODO: finalize
		}
		return result;
	}
	
	public List getNumberTypes() throws MICSException{
		logger.debug("getNumberTypes()");
		List result = null;
		try {
			result = new Vector();
			NumberType numberTypeMobile = new NumberType(0,"Mobile");
			NumberType numberTypeSIP = new NumberType(1,"SIP");
			result.add(numberTypeMobile);
			result.add(numberTypeSIP);
		} catch (Exception e) {
			result = null;
		} finally {
			// TODO: finalize
		}
		return result;
	}
	
	public List getLanguages() throws MICSException{
		logger.debug("getLanguages()");
		List result = null;
		try {
			result = new Vector();
			Language languageBahasa = new Language("IND","Indonesia");
			Language languageEnglish = new Language("ENG","English");
			result.add(languageBahasa);
			result.add(languageEnglish);
		} catch (Exception e) {
			result = null;
		} finally {
			// TODO: finalize
		}
		return result;
	}
	
	public List getBearerTypes() throws MICSException{
		logger.debug("getBearerTypes()");
		List result = null;
		try {
			result = new Vector();

			BearerType voiceBearer = new BearerType(1,"Voice");
			BearerType videoBearer = new BearerType(2,"Video");
			BearerType faxBearer = new BearerType(4,"Fax");
			BearerType dataBearer = new BearerType(8,"Data");
			BearerType otherBearer = new BearerType(16,"Other");
			result.add(voiceBearer);
			result.add(videoBearer);
			result.add(faxBearer);
			result.add(dataBearer);
			result.add(otherBearer);
		} catch (Exception e) {
			result = null;
		} finally {
			// TODO: finalize
		}
		return result;
	}
	
	public List getSwitchboardTypes() throws MICSException{
		logger.debug("getSwitchboardTypes()");
		List result = null;
		try {
			result = new Vector();
			SwitchboardType inactiveType = new SwitchboardType(0,"Inactive");
			SwitchboardType submenuType = new SwitchboardType(1,"Submenu");
			SwitchboardType destinationType = new SwitchboardType(2,"Destination");
			result.add(inactiveType);
			result.add(submenuType);
			result.add(destinationType);
		} catch (Exception e) {
			result = null;
		} finally {
			// TODO: finalize
		}
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getDaySelection() throws MICSException {
		logger.debug("getDaySelection");
		List result = null;
		try {
			result = new Vector();
			DaySelection daySelectionSun = new DaySelection(2,"Sunday");
			DaySelection daySelectionMon = new DaySelection(4,"Monday");
			DaySelection daySelectionTue = new DaySelection(8,"Tuesday");
			DaySelection daySelectionWed = new DaySelection(16,"Wednesday");
			DaySelection daySelectionThu = new DaySelection(32,"Thursday");
			DaySelection daySelectionFri = new DaySelection(64,"Friday");
			DaySelection daySelectionSat = new DaySelection(128,"Saturday");
			
			result.add(daySelectionSun);
			result.add(daySelectionMon);
			result.add(daySelectionTue);
			result.add(daySelectionWed);
			result.add(daySelectionThu);
			result.add(daySelectionFri);
			result.add(daySelectionSat);
			
		} catch (Exception e) {
			result = null;
		} finally {
			// Finalize something here if connect to DB
		}
		return result;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getScreeningType() throws MICSException {
		logger.debug("getScreeningType()");
		List result = null;
		try {
			result = new Vector();
			ScreeningType screenTypeOut = new ScreeningType(1,"Outgoing");
			ScreeningType screenTypeIn = new ScreeningType(2,"Incoming");
			result.add(screenTypeOut);
			result.add(screenTypeIn);
		} catch (Exception e) {
			// handle exception
			result = null;
		} finally {
			// finalize
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public List getRoleList() throws MICSException{
		logger.debug("getRoleList()");
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );

			Query query = session.createQuery("select role from Role as role order by rolekey desc");
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result);

		} catch (Exception e) {
			result = null;
			logger.error("get role list failed", e);
			throw new MICSException("Failed to get role list ",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public Company getCompanyByKey(String companyKey) throws MICSException{
		logger.debug("getCompanyByKey() " + companyKey);
		Session session = null;
		Company result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery("select company from Company as company where company.companykey = :companyKey");
			query.setString("companyKey", companyKey);
			logger.debug("queryString: " + query.getQueryString());
			result =  (Company) query.uniqueResult();
			logger.debug("result: " + result);
		} catch (Exception e) {
			result = null;
			logger.error("get company by key with key " + companyKey, e);
			throw new MICSException("Failed to get company by key ",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public List getHuntingGroups() throws MICSException{
		logger.debug("getHuntingGroups() ");
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery("select huntinggroup,huntingschedule,huntingmember from Huntinggroup as huntinggroup, Huntingschedule as huntingschedule, Huntingmember as huntingmember where huntingschedule.huntinggroupkey = huntinggroup.huntinggroupkey and huntingmember.huntingschedulekey = huntingschedule.huntingschedulekey order by huntinggroup.companykey,huntinggroup.huntinggroupkey,huntingschedule.orderindex,huntingschedule.huntingschedulekey,huntingmember.orderindex ");
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result);
		} catch (Exception e) {
			result = null;
			logger.error("get hunting groups failed", e);
			throw new MICSException("Failed to get hunting groups",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public List getHuntingGroupsByCompany(String companyKey) throws MICSException{
		logger.debug("getHuntingGroupsByCompany() " + companyKey);
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery("select huntinggroup,huntingschedule,huntingmember from Huntinggroup as huntinggroup, Huntingschedule as huntingschedule, Huntingmember as huntingmember where huntinggroup.companykey = :companyKey and huntingschedule.huntinggroupkey = huntinggroup.huntinggroupkey and huntingmember.huntingschedulekey = huntingschedule.huntingschedulekey order by huntinggroup.companykey,huntinggroup.huntinggroupkey,huntingschedule.orderindex, huntingschedule.huntingschedulekey,huntingmember.orderindex ");
			query.setString("companyKey", companyKey);
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result);
		} catch (Exception e) {
			result = null;
			logger.error("get hunting groups by company "+ companyKey+" failed", e);
			throw new MICSException("Failed to get hunting groups by company " + companyKey,e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public Huntinggroup getHuntinggroupByKey(String huntinggroupkey) throws MICSException{
		logger.debug("getHuntinggroupByKey() " + huntinggroupkey);
		Session session = null;
		Huntinggroup result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery("select huntinggroup from Huntinggroup as huntinggroup where huntinggroup.huntinggroupkey = :huntinggroupkey");
			query.setString("huntinggroupkey", huntinggroupkey);
			logger.debug("queryString: " + query.getQueryString());
			result =  (Huntinggroup) query.uniqueResult();
			logger.debug("result: " + result);
		} catch (Exception e) {
			result = null;
			logger.error("get huntinggroup by key with key " + huntinggroupkey, e);
			throw new MICSException("Failed to get huntinggroup by key ",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public List getHuntingscheduleByHuntinggroupKey(String huntinggroupkey) throws MICSException {
		logger.debug("getHuntingscheduleByHuntinggroupKey");
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery("select huntingschedule from Huntingschedule as huntingschedule where huntingschedule.huntinggroupkey = :huntinggroupkey order by huntingschedule.huntingschedulekey asc");
			query.setString("huntinggroupkey", huntinggroupkey);
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result);
		} catch (Exception e) {
			result = null;
			logger.error("get huntingschedule by huntinggroupkey failed", e);
			throw new MICSException("Failed to get huntingschedule by huntinggroupkey ",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	public List getHuntingscheduleWithTimebandByHuntinggroupKey(String huntinggroupkey) throws MICSException {
		logger.debug("getHuntingscheduleWithTimebandByHuntinggroupKey");
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery("select huntingschedule,timeband from Huntingschedule as huntingschedule, Timeband as timeband where huntingschedule.huntinggroupkey = :huntinggroupkey and timeband.id.timebandkey = huntingschedule.timebandkey order by huntingschedule.orderindex asc");
			query.setString("huntinggroupkey", huntinggroupkey);
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result);
		} catch (Exception e) {
			result = null;
			logger.error("get huntingschedule with timeband info by huntinggroupkey failed", e);
			throw new MICSException("Failed to get huntingschedule with timeband info by huntinggroupkey ",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public List getHuntingmemberByHuntingscheduleKey(String huntingschedulekey) throws MICSException {
		logger.debug("getHuntingmemberByHuntingscheduleKey");
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery("select micsuser.micsuserkey,micsuser.micsfirstname,micsuser.micslastname, micsusernumber.publicnumber,micsusernumber.privatenumber,huntingmember.huntingmemberkey,huntingmember.orderindex from Micsusernumber as micsusernumber, Micsuser as micsuser, Huntingmember as huntingmember where micsuser.micsuserkey = micsusernumber.micsuserkey and micsusernumber.publicnumber = huntingmember.targetnumber and huntingmember.huntingschedulekey = :huntingschedulekey order by huntingmember.orderindex asc");
			query.setString("huntingschedulekey", huntingschedulekey);
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result);
		} catch (Exception e) {
			result = null;
			logger.error("get huntingmember by huntingschedule failed", e);
			throw new MICSException("Failed to get huntingmember by huntingschedulekey ",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public List getMemberByHuntingscheduleKey(String huntingschedulekey) throws MICSException {
		logger.debug("getMemberByHuntingscheduleKey");
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery("select huntingmember from Huntingmember as huntingmember where huntingmember.huntingschedulekey = :huntingschedulekey");
			query.setString("huntingschedulekey", huntingschedulekey);
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result);
		} catch (Exception e) {
			result = null;
			logger.error("get member by huntingschedule failed", e);
			throw new MICSException("Failed to get member by huntingschedulekey ",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public Micsgroup getGroupsByKey(String micsgroupkey) throws MICSException{
		logger.debug("getGroupsByKey() " + micsgroupkey);
		Session session = null;
		Micsgroup result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery("select micsgroup from Micsgroup as micsgroup where micsgroup.micsgroupkey = :micsgroupkey");
			query.setString("micsgroupkey", micsgroupkey);
			logger.debug("queryString: " + query.getQueryString());
			result =  (Micsgroup) query.uniqueResult();
			logger.debug("result: " + result);
		} catch (Exception e) {
			result = null;
			logger.error("get group by key with key " + micsgroupkey, e);
			throw new MICSException("Failed to get group by key ",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public List getGroupsByCompany(String companykey) throws MICSException{
		logger.debug("getGroupsByCompany() " + companykey);
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery("select micsgroup from Micsgroup as micsgroup where micsgroup.companykey = :companykey");
			query.setString("companykey", companykey);
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result);
		} catch (Exception e) {
			result = null;
			logger.error("get groups by company with key " + companykey, e);
			throw new MICSException("Failed to get group by company ",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public List getGroups() throws MICSException{
		logger.debug("getGroups" );
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery("select micsgroup from Micsgroup as micsgroup order by micsgroup.companykey,micsgroup.micsgroupkey");
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result);
		} catch (Exception e) {
			result = null;
			logger.error("get groups ", e);
			throw new MICSException("Failed to get groups",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public List getCompanyList() throws MICSException{
		logger.debug("getCompanyList()");
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery("select company from Company as company order by company.companykey asc");
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
//			logger.debug("result: " + result);

		} catch (Exception e) {
			result = null;
			logger.error("get company list failed", e);
			throw new MICSException("Failed to get company list ",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	public List getMICSProfileList(String callScreeningKey,String companyKey,Integer type,Integer level)throws MICSException{
		logger.debug("getMICSProfileList1");
		Session session = null;
		List result = null;
		try {
			StringBuffer customQuery = new StringBuffer();
			customQuery.append("select callscreening from Callscreening as callscreening where callscreening.callscreeningkey like : screeningKey ");
			customQuery.append("and callscreening.companykey like :companyKey ");
			if (callScreeningKey == null) {
				callScreeningKey = ""+"%";
			}
			if (companyKey == null) {
				companyKey = ""+"%";
			}
			if (type != null) {
				customQuery.append("and callscreening.screeningtype = " + type + " ");
			} 
			if (level != null) {
				customQuery.append("and callscreening.screeninglevel = " + level + " ");
			}
			customQuery.append("order by callscreening.callscreeningkey asc");
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery(customQuery.toString());
			query.setString("screeningKey", callScreeningKey);
			query.setString("companyKey", companyKey);
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result.size());
		} catch (Exception e) {
			result = null;
			logger.error("get callscreening list failed", e);
			throw new MICSException("Failed to get callscreening list ",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public List getMICSProfileList(String companyKey,Integer type,Integer level)throws MICSException{
		logger.debug("getMICSProfileList2");
		Session session = null;
		List result = null;
		try {
			StringBuffer customQuery = new StringBuffer();
			customQuery.append("select callscreening from Callscreening as callscreening where  ");
			if (type != null) {
				customQuery.append("callscreening.screeningtype = " + type + " ");
				if (level != null) {
					customQuery.append("and callscreening.screeninglevel = " + level + " ");
				}
			} else {
				if (level != null) {
					customQuery.append(" callscreening.screeninglevel = " + level + " ");
				}
			}
			
			customQuery.append("order by callscreening.callscreeningkey asc");
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery(customQuery.toString());
			
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result.size());
		} catch (Exception e) {
			result = null;
			logger.error("get callscreening list failed", e);
			throw new MICSException("Failed to get callscreening list ",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public List getAllEmployees()throws MICSException{
		logger.debug("getAllEmployees");
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery("select micsuser from Micsuser as micsuser order by micsfirstname asc");
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result.size());
		} catch (Exception e) {
			result = null;
			logger.error("get all employee list failed", e);
			throw new MICSException("Failed to get all employee list ",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public List getEmployeesByCriteria(String strFirstName,String strLastName,String strPubNumber,String strPrivNumber)throws MICSException{
		logger.debug("getAllEmployees");
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			StringBuffer sQuery = new StringBuffer();
			sQuery.append("select micsuser.micsuserkey, micsuser.companykey, micsuser.micsfirstname, micsuser.micslastname, micsusernumber.publicnumber, micsusernumber.privatenumber from Micsuser as micsuser,Micsusernumber as micsusernumber where micsuser.micsuserkey = micsusernumber.micsuserkey ");
			sQuery.append(" and lower(micsuser.micsfirstname) like lower(:firstName) and lower(micsuser.micslastname) like lower(:lastName) and micsusernumber.publicnumber like :publicName and micsusernumber.privatenumber like :privateNumber order by micsuser.micsfirstname asc");
			Query query = session.createQuery(sQuery.toString());
			query.setString("firstName", "%"+strFirstName+"%");
			query.setString("lastName", "%"+strLastName+"%");
			query.setString("publicName", "%"+strPubNumber+"%");
			query.setString("privateNumber", "%"+ strPrivNumber+"%");
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result.size());
		} catch (Exception e) {
			result = null;
			logger.error("get employee list by criteria failed", e);
			throw new MICSException("Failed to get employee list by criteria",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public List getEmployeeNumbersByCompany(String companykey)throws MICSException{
		logger.debug("getEmployeesByCompany");
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			StringBuffer sQuery = new StringBuffer();
			sQuery.append("select micsuser, micsusernumber from Micsuser as micsuser,Micsusernumber as micsusernumber where micsuser.companykey = :companykey and  micsusernumber.micsuserkey = micsuser.micsuserkey ");
			sQuery.append(" order by micsuser.micsuserkey,micsusernumber.orderindex asc");
			Query query = session.createQuery(sQuery.toString());
			query.setString("companykey", companykey);
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result.size());
		} catch (Exception e) {
			result = null;
			logger.error("get employeeNumbers list by company failed", e);
			throw new MICSException("Failed to get employeeNumbers list by company",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public List getEmployeeNumbers()throws MICSException{
		logger.debug("getEmployeeNumbers");
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			StringBuffer sQuery = new StringBuffer();
			sQuery.append("select micsuser, micsusernumber from Micsuser as micsuser,Micsusernumber as micsusernumber where micsusernumber.micsuserkey = micsuser.micsuserkey ");
			sQuery.append(" order by micsuser.companykey,micsuser.micsuserkey,micsusernumber.orderindex asc");
			Query query = session.createQuery(sQuery.toString());
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result.size());
		} catch (Exception e) {
			result = null;
			logger.error("get employeeNumbers list failed", e);
			throw new MICSException("Failed to get employeeNumbers list",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public List getEmployeesByCriteriaCompany(String strFirstName,String strLastName,String strPrivNumber, String strPubNumber,String companykey)throws MICSException{
		logger.debug("getEmployeesByCriteriaCompany");
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			StringBuffer sQuery = new StringBuffer();
			if (strFirstName == null) strFirstName = "";
			if (strLastName == null) strLastName = "";
			if (strPrivNumber == null) strPrivNumber = "";
			if (strPubNumber == null) strPubNumber = "";
			sQuery.append("select micsuser.micsuserkey, micsuser.companykey, micsuser.micsfirstname, micsuser.micslastname, micsusernumber.publicnumber, micsusernumber.privatenumber from Micsuser as micsuser,Micsusernumber as micsusernumber where micsuser.micsuserkey = micsusernumber.micsuserkey ");
			sQuery.append(" and lower(micsuser.micsfirstname) like lower(:firstName) and lower(micsuser.micslastname) like lower(:lastName)  and micsusernumber.publicnumber like :publicName and micsusernumber.privatenumber like :privateNumber and micsuser.companykey like :companykey order by micsuser.micsfirstname asc");
			Query query = session.createQuery(sQuery.toString());
			query.setString("firstName", "%"+strFirstName+"%");
			query.setString("lastName", "%"+strLastName+"%");
			query.setString("publicName", "%"+strPubNumber+"%");
			query.setString("privateNumber", "%"+ strPrivNumber+"%");
			query.setString("companykey", companykey);
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result.size());
		} catch (Exception e) {
			result = null;
			logger.error("get employee list by criteria by company failed", e);
			throw new MICSException("Failed to get employee list by criteria by company",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public Micsuser getEmployeeByKey(String micsuserkey) throws MICSException {
		logger.debug("getEmployeeByKey");
		Session session = null;
		Micsuser result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery("select micsuser from Micsuser as micsuser where micsuser.micsuserkey = :micsuserkey");
			query.setString("micsuserkey", micsuserkey);
			logger.debug("queryString: " + query.getQueryString());
			result =  (Micsuser) query.uniqueResult();
			logger.debug("result: " + result);
		} catch (Exception e) {
			result = null;
			logger.error("get employee by key failed", e);
			throw new MICSException("Failed to get employee by key ",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public List getUserCommunityMemberByKey(String communitykey) throws MICSException {
		logger.debug("getUserCommunityMemberByKey");
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
//			Query query = session.createQuery("select micsuser,usernumber from Communitymember as communitymember,Micsuser as micsuser,Micsusernumber as usernumber where micsuser.micsuserkey = communitymember.micsuserkey and usernumber.micsuserkey = communitymember.micsuserkey and communitymember.communitykey = :communitykey order by micsuser.micsuserkey asc");
			Query query = session.createQuery("select micsuser from Communitymember as communitymember,Micsuser as micsuser where micsuser.micsuserkey = communitymember.micsuserkey and communitymember.communitykey = :communitykey order by micsuser.micsuserkey asc");
			query.setString("communitykey", communitykey);
			logger.debug("queryString: " + query.getQueryString());
			result =  (List)query.list();
			logger.debug("result: " + result);
		} catch (Exception e) {
			result = null;
			logger.error("get community member by key failed", e);
			throw new MICSException("Failed to get community member by key ",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public List getCommunities() throws MICSException {
		logger.debug("getCommunities");
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery("select community,communitymember from Community as community,Communitymember as communitymember where  communitymember.communitykey = community.communitykey order by community.communitykey,communitymember.micsuserkey asc");
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result);
		} catch (Exception e) {
			result = null;
			logger.error("get communities failed", e);
			throw new MICSException("Failed to get communities",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public List getCommunityMemberByKey(String communitykey) throws MICSException {
		logger.debug("getCommunityMemberByKey");
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery("select communitymember from Communitymember as communitymember where  communitymember.communitykey = :communitykey order by communitymember.micsuserkey asc");
			query.setString("communitykey", communitykey);
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result);
		} catch (Exception e) {
			result = null;
			logger.error("get community member by key failed", e);
			throw new MICSException("Failed to get community member by key ",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	public Community getCommunityByKey(String communitykey) throws MICSException {
		logger.debug("getCommunityByKey");
		Session session = null;
		Community result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery("select community from Community as community where community.communitykey = :communitykey");
			query.setString("communitykey", communitykey);
			logger.debug("queryString: " + query.getQueryString());
			result =  (Community) query.uniqueResult();
			logger.debug("result: " + result);
		} catch (Exception e) {
			result = null;
			logger.error("get community by key failed", e);
			throw new MICSException("Failed to get community by key ",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	public Partnership getPartnershipByKey(String partnershipkey) throws MICSException {
		logger.debug("getPartnershipByKey");
		Session session = null;
		Partnership result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery("select partnership from Partnership as partnership where partnership.partnershipkey = :partnershipkey");
			query.setString("partnershipkey", partnershipkey);
			logger.debug("queryString: " + query.getQueryString());
			result =  (Partnership) query.uniqueResult();
			logger.debug("result: " + result);
		} catch (Exception e) {
			result = null;
			logger.error("get partnership by key failed", e);
			throw new MICSException("Failed to get partnership by key ",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public List getPartnerships() throws MICSException {
		logger.debug("getPartnership");
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery("select partnership from Partnership as partnership order by partnership.partnershipkey asc");
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result);
		} catch (Exception e) {
			result = null;
			logger.error("get partnerships failed", e);
			throw new MICSException("Failed to get partnerships",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public List searchCompanies(String companyKey, String companyName, String chargeString)throws MICSException{
		logger.debug("searchCompanies()"  + companyKey + " " + companyName + " " + chargeString);
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select company from Company as company ");
			if (companyKey == null) {
				companyKey = "";
			}
			
			
			if (companyName == null) {
				companyName = "";
			}
			
			
			if (chargeString == null) {
				chargeString = "";
			}
			
			
			//Create Query depend on criteria 
			if (companyKey.trim().equals("")) {
				//Bypass Company Key
				if (companyName.trim().equals("")) {
					//Bypass Company Name
					if (chargeString.trim().equals("")) {
						sbQuery.append("order by company.companykey asc ");
					} else {
						sbQuery.append("where company.chargestring like :chargeString order by company.companykey asc");
					}
				} else {
					sbQuery.append("where company.companyname like :companyName ");
					if (chargeString.trim().equals("")) {
						sbQuery.append("order by company.companykey asc ");
					} else {
						sbQuery.append("and company.chargestring like :chargeString order by company.companykey asc");
					}
				}
			} else {
				sbQuery.append("where company.companykey like :companyKey ");
				if (companyName.trim().equals("")) {
					//Bypass Company Name
					if (chargeString.trim().equals("")) {
						sbQuery.append("order by company.companykey asc ");
					} else {
						sbQuery.append("and company.chargestring like :chargeString order by company.companykey asc");
					}
				} else {
					sbQuery.append("and company.companyname like :companyName ");
					if (chargeString.trim().equals("")) {
						sbQuery.append("order by company.companykey asc ");
					} else {
						sbQuery.append("and company.chargestring like :chargeString order by company.companykey asc");
					}
				}
			}
			
			Query query = session.createQuery(sbQuery.toString());
			if (!companyKey.trim().equals("")) {
				companyKey = "%"+companyKey + "%";
				query.setString("companyKey", companyKey);
			}
			
			if (!companyName.trim().equals("")) {
				companyName = "%"+companyName + "%";
				query.setString("companyName", companyName);
			}
			
			if (!chargeString.trim().equals("")) {
				chargeString = "%"+chargeString + "%";
				query.setString("chargeString", chargeString);
			}
			
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result.size());
		} catch (Exception e) {
			result = null;
			logger.error("search company failed", e);
			throw new MICSException("Failed to search company",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public List searchCommunities(String communityName)throws MICSException{
		logger.debug("searchCommunities()"  + communityName );
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			if (communityName == null) {
				communityName = "";
			}
			communityName = communityName + "%";
			
			
			Query query = session.createQuery("select community from Community as community where lower(community.communityname) like lower(:communityName) order by community.communitykey asc");
			query.setString("communityName", communityName);
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result.size());
		} catch (Exception e) {
			result = null;
			logger.error("search company failed", e);
			throw new MICSException("Failed to search company",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public List searchGroups(String companyId,String groupname, String groupid, String groupcli)throws MICSException{
		logger.debug("searchGroups()"  + groupname + " - " + groupid + " - " + groupcli);
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select micsgroup from Micsgroup as micsgroup where micsgroup.companykey = :companykey ");
			if (groupname != null && !groupname.trim().equals("")) {
				groupname = groupname + "%";
				sbQuery.append("and micsgroup.groupname like :groupname ");
			}
			if (groupid != null && !groupid.trim().equals("")) {
				groupid = groupid + "%";
				sbQuery.append("and micsgroup.groupid like :groupid ");
			}
			
			if (groupcli != null && !groupcli.trim().equals("")) {
				groupcli = groupcli + "%";
				sbQuery.append("and micsgroup.groupcli like :groupcli ");
			}
			sbQuery.append("order by micsgroup.micsgroupkey asc");
			
			
			
			Query query = session.createQuery(sbQuery.toString());
			query.setString("companykey", companyId);
			if (groupname != null && !groupname.trim().equals("")) {
				query.setString("groupname", groupname);
			}
			if (groupid != null && !groupid.trim().equals("")) {
				query.setString("groupid", groupid);
			}
			if (groupcli != null && !groupcli.trim().equals("")) {
				query.setString("groupcli", groupcli);
			}
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result.size());
		} catch (Exception e) {
			result = null;
			logger.error("search groups failed", e);
			throw new MICSException("Failed to search groups",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public List searchHuntinggroups(String companykey,String huntinggroupname, String publicnumber, String privatenumber)throws MICSException{
		logger.debug("searchGroups()"  + huntinggroupname + " " + publicnumber + " " + privatenumber);
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			if (huntinggroupname == null) {
				huntinggroupname = "";
			}
			huntinggroupname = "%"+huntinggroupname.trim() + "%";
			
			if (publicnumber == null) {
				publicnumber = "";
			}
			publicnumber = "%"+publicnumber.trim() + "%";
			
			if (privatenumber == null) {
				privatenumber = "";
			}
			privatenumber = "%"+privatenumber.trim() + "%";
			
			StringBuffer queryString = new StringBuffer();
			queryString.append("select huntinggroup from Huntinggroup as huntinggroup ");
			queryString.append("where huntinggroup.companykey = :companykey and ");
			queryString.append("lower(huntinggroup.huntinggroupname) like lower(:huntinggroupname) and ");
			queryString.append("huntinggroup.publicnumber like :publicnumber and ");
			queryString.append("huntinggroup.privatenumber like :privatenumber ");
			queryString.append("order by huntinggroup.huntinggroupkey asc");
			Query query = session.createQuery(queryString.toString());
			query.setString("companykey", companykey);
			query.setString("huntinggroupname", huntinggroupname);
			query.setString("publicnumber", publicnumber);
			query.setString("privatenumber", privatenumber);
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result.size());
		} catch (Exception e) {
			result = null;
			logger.error("search huntinggroups failed", e);
			throw new MICSException("Failed to search huntinggroups",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	
	public List searchEmployees(String strFirstName,String strLastName,String strPubNumber,String strPrivNumber,String companyKey)throws MICSException{
		logger.debug("searchEmployees");
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			StringBuffer sQuery = new StringBuffer();
			if(strFirstName == null) strFirstName = "";
			if(strLastName == null) strLastName = "";
			if(strPubNumber == null) strPubNumber = "";
			if(strPrivNumber == null) strPrivNumber = "";
			if(companyKey == null) companyKey = "";
			sQuery.append("select distinct micsuser,micsusernumber from Micsuser as micsuser,Micsusernumber as micsusernumber where micsuser.micsuserkey = micsusernumber.micsuserkey ");
			sQuery.append(" and lower(micsuser.micsfirstname) like lower(:firstName) and lower(micsuser.micslastname) like lower(:lastName) and micsusernumber.publicnumber like :publicName and micsusernumber.privatenumber like :privateNumber and micsuser.companykey like :companyKey  order by micsuser.micsfirstname asc");
			Query query = session.createQuery(sQuery.toString());
			query.setString("firstName", "%"+strFirstName+"%");
			query.setString("lastName", "%"+strLastName+"%");
			query.setString("publicName", "%"+strPubNumber+"%");
			query.setString("privateNumber", "%"+ strPrivNumber+"%");
			query.setString("companyKey", "%"+companyKey+"%");
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result.size());
		} catch (Exception e) {
			result = null;
			logger.error("get employee list by criteria failed", e);
			throw new MICSException("Failed to get employee list by criteria",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public List searchPartnerships(String partnershipname)throws MICSException{
		logger.debug("searchPartnerships");
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			StringBuffer sQuery = new StringBuffer();
			sQuery.append("select partnership from Partnership as partnership where lower(partnership.partnershipname) like lower(:partnershipname) order by partnership.partnershipkey asc");
			Query query = session.createQuery(sQuery.toString());
			query.setString("partnershipname", "%"+partnershipname+"%");
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result.size());
		} catch (Exception e) {
			result = null;
			logger.error("search partnerships failed", e);
			throw new MICSException("Failed to search partnerships",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public List searchTimebands(String timebandName,String timebandDesc)throws MICSException{
		logger.debug("searchTimebands");
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			StringBuffer sQuery = new StringBuffer();
			sQuery.append("select timeband from Timeband as timeband ");
			sQuery.append("where lower(timeband.id.timebandname) like lower(:timebandName) ");
			sQuery.append("and lower(timeband.timebanddescription) like lower(:timebandDesc) ");
			sQuery.append("order by timeband.id.timebandkey asc");
			Query query = session.createQuery(sQuery.toString());
			query.setString("timebandName", "%"+timebandName+"%");
			query.setString("timebandDesc", "%"+timebandDesc+"%");
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result.size());
		} catch (Exception e) {
			result = null;
			logger.error("search timeband failed", e);
			throw new MICSException("Failed to search timeband",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public List searchCallScreening(String screeningName,Integer type,Integer level)throws MICSException{
		logger.debug("getMICSProfileList2");
		Session session = null;
		List result = null;
		try {
			if (screeningName == null) {
				screeningName = "";
			}
			screeningName = "%"+screeningName+"%";
			StringBuffer customQuery = new StringBuffer();
			customQuery.append("select callscreening from Callscreening as callscreening where lower(callscreening.callscreeningname) like lower(:screeningName)  ");
			if (type != null) {
				customQuery.append("and callscreening.screeningtype = " + type + " ");
			} 
			if (level != null) {
				customQuery.append("and callscreening.screeninglevel = " + level + " ");
			}
			customQuery.append("order by callscreening.callscreeningkey asc");
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery(customQuery.toString());
			query.setString("screeningName", screeningName);
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result.size());
		} catch (Exception e) {
			result = null;
			logger.error("search callscreening  failed", e);
			throw new MICSException("Failed to search callscreening ",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public List getNumberByEmployeeKey(String micsuserkey)throws MICSException{
		logger.debug("searchEmployees");
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			StringBuffer sQuery = new StringBuffer();
			sQuery.append("select micsusernumber from Micsusernumber as micsusernumber ");
			sQuery.append("where micsusernumber.micsuserkey = :micsuserkey order by micsusernumber.orderindex,micsusernumber.publicnumber asc ");
			Query query = session.createQuery(sQuery.toString());
			query.setString("micsuserkey", micsuserkey);
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result.size());
		} catch (Exception e) {
			result = null;
			logger.error("get number list by employeeKey failed", e);
			throw new MICSException("Failed to get number by employeeKey",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	public List getMICSUserList(String searchUser, String searchCompany, String searchNik, String searchRole)throws MICSException{
		logger.debug("getMICSUserList");
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select webuser from Webuser as webuser ");
			
			if (searchUser == null) {
				searchUser = "";
			}
			
			if (searchCompany == null) {
				searchCompany = "";
			}
			
			if (searchNik == null) {
				searchNik = "";
			}
			
			if (searchRole == null) {
				searchRole = "";
			}
			
			//Add criteria in query
			if (searchUser.trim().equals("")) {
				//User criteria not filled in bypass criteria
				if (searchCompany.trim().equals("")) {
					//Company criteria not filled in bypass company
					if (searchNik.trim().equals("")) {
						//Nik criteria not filled in bypass NIK
						if (searchRole.trim().equals("")) {
							//Role criteria not filled in bypass Role
							sbQuery.append("order by webuser.webuserkey asc ");
						} else {
							sbQuery.append("where webuser.rolekey like :userRole order by webuser.webuserkey asc ");
						}
					} else {
						sbQuery.append("where webuser.ssotoken like :userNik ");
						if (searchRole.trim().equals("")) {
							//Role criteria not filled in bypass Role
							sbQuery.append("order by webuser.webuserkey asc ");
						} else {
							sbQuery.append("and webuser.rolekey like :userRole order by webuser.webuserkey asc ");
						}
					}
				} else {
					sbQuery.append("where webuser.companykey like :companyKey ");
					if (searchNik.trim().equals("")) {
						//Nik criteria not filled in bypass NIK
						if (searchRole.trim().equals("")) {
							//Role criteria not filled in bypass Role
							sbQuery.append("order by webuser.webuserkey asc ");
						} else {
							sbQuery.append("and webuser.rolekey like :userRole order by webuser.webuserkey asc ");
						}
					} else {
						sbQuery.append("and webuser.ssotoken like :userNik ");
						if (searchRole.trim().equals("")) {
							//Role criteria not filled in bypass Role
							sbQuery.append("order by webuser.webuserkey asc ");
						} else {
							sbQuery.append("and webuser.rolekey like :userRole order by webuser.webuserkey asc ");
						}
					}
				}
			} else {
				sbQuery.append("where webuser.webusername like :userName ");
				if (searchCompany.trim().equals("")) {
					//Company criteria not filled in bypass company
					if (searchNik.trim().equals("")) {
						//Nik criteria not filled in bypass NIK
						if (searchRole.trim().equals("")) {
							//Role criteria not filled in bypass Role
							sbQuery.append("order by webuser.webuserkey asc ");
						} else {
							sbQuery.append("and webuser.rolekey like :userRole order by webuser.webuserkey asc ");
						}
					} else {
						sbQuery.append("and webuser.ssotoken like :userNik ");
						if (searchRole.trim().equals("")) {
							//Role criteria not filled in bypass Role
							sbQuery.append("order by webuser.webuserkey asc ");
						} else {
							sbQuery.append("and webuser.rolekey like :userRole order by webuser.webuserkey asc ");
						}
					}
				} else {
					sbQuery.append("and webuser.companykey like :companyKey ");
					if (searchNik.trim().equals("")) {
						//Nik criteria not filled in bypass NIK
						if (searchRole.trim().equals("")) {
							//Role criteria not filled in bypass Role
							sbQuery.append("order by webuser.webuserkey asc ");
						} else {
							sbQuery.append("and webuser.rolekey like :userRole order by webuser.webuserkey asc ");
						}
					} else {
						sbQuery.append("and webuser.ssotoken like :userNik ");
						if (searchRole.trim().equals("")) {
							//Role criteria not filled in bypass Role
							sbQuery.append("order by webuser.webuserkey asc ");
						} else {
							sbQuery.append("and webuser.rolekey like :userRole order by webuser.webuserkey asc ");
						}
					}
				}
			}
			
			Query query = session.createQuery(sbQuery.toString());
			if (!searchUser.trim().equals("")) {
				searchUser = "%"+searchUser + "%";
				query.setString("userName", searchUser);
			}
			if (!searchCompany.trim().equals("")) {
				searchCompany = "%"+searchCompany + "%";
				query.setString("companyKey", searchCompany);
			}
			if (!searchNik.trim().equals("")) {
				searchNik = "%"+searchNik+"%";
				query.setString("userNik", searchNik);
			}
			if (!searchRole.trim().equals("")) {
				searchRole = "%" + searchRole + "%";
				query.setString("userRole", searchRole);
			}
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result.size());
		} catch (Exception e) {
			result = null;
			logger.error("get user list failed", e);
			throw new MICSException("Failed to get user list ",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	@SuppressWarnings("rawtypes")
	public List getMICSProfileList(String searchProfile, String searchCompany)throws MICSException{
		logger.debug("getMICSProfileList");
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			if (searchProfile == null) {
				searchProfile = "";
			}
			searchProfile = searchProfile + "%";
			if (searchCompany == null) {
				searchCompany = "";
			}
			searchCompany = searchCompany + "%";
			Query query = session.createQuery("select callscreening from Callscreening as callscreening where callscreening.callscreeningkey like :screeningKey and callscreening.companykey like :companyKey order by callscreeningkey asc");
			query.setString("screeningKey", searchProfile);
			query.setString("companyKey", searchCompany);
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result.size());
		} catch (Exception e) {
			result = null;
			logger.error("get profile list failed", e);
			throw new MICSException("Failed to get profile list ",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public List getCompanyAnno(String searchCompany)throws MICSException{
		logger.debug("getCompanyAnno");
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			if (searchCompany == null) {
				searchCompany = "";
			}
			Query query = session.createQuery("select announcement from Announcement as announcement where announcement.companykey = :companyKey order by announcement.annoid asc");
			query.setString("companyKey", searchCompany);
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result.size());
		} catch (Exception e) {
			result = null;
			logger.error("get anno list failed", e);
			throw new MICSException("Failed to get anno list ",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public List getCompanySwitchboardList(String companyKey) throws MICSException{
		logger.debug("getCompanySwitchboardList() " + companyKey);
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery("select switchboard from Switchboard as switchboard where switchboard.companykey = :companyKey");
			query.setString("companyKey", companyKey);
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result.size());
		} catch (Exception e) {
			result = null;
			logger.error("get company switchboard list with company key " + companyKey, e);
			throw new MICSException("Failed to get company switchboard list by company key " + companyKey,e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public Switchboard getCompanySwitchboardSingle(String companyKey, String switchboardKey) throws MICSException{
		logger.debug("getCompanySwitchboardSingle() " + companyKey + " " + switchboardKey);
		Session session = null;
		Switchboard result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery("select switchboard from Switchboard as switchboard where switchboard.companykey = :companyKey and switchboard.switchboardkey = :switchboardKey");
			query.setString("companyKey", companyKey);
			query.setString("switchboardKey", switchboardKey);
			logger.debug("queryString: " + query.getQueryString());
			result =  (Switchboard) query.uniqueResult();
			logger.debug("result: " + result);
		} catch (Exception e) {
			result = null;
			logger.error("get company switchboard single with company key " + companyKey + " and switchboard key " + switchboardKey, e);
			throw new MICSException("Failed to get company switchboard single by company key " + companyKey + " and switchboard key " + switchboardKey, e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public Switchboard getCompanySwitchboard(String companyKey) throws MICSException{
		logger.debug("getCompanySwitchboard() " + companyKey);
		Session session = null;
		Switchboard result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery("select switchboard from Switchboard as switchboard where switchboard.companykey = :companyKey");
			query.setString("companyKey", companyKey);
			logger.debug("queryString: " + query.getQueryString());
			result =  (Switchboard) query.uniqueResult();
			logger.debug("result: " + result);
		} catch (Exception e) {
			result = null;
			logger.error("get companya switchboard with company key " + companyKey, e);
			throw new MICSException("Failed to get company switchboard by companu key " + companyKey,e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
//	select switchboardmenu from Switchboardmenu as switchboardmenu where switchboardmenu.switchboardkey = '' order by switchboardmenu.dialdigit asc
	
	@SuppressWarnings("rawtypes")
	public List getSwitchboardmenus(String switchboardKey)throws MICSException{
		logger.debug("getSwitchboarmenus");
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			////logger.debug("session: "+ session );
			Query query = session.createQuery("select switchboardmenu from Switchboardmenu as switchboardmenu where switchboardmenu.switchboardkey = :switchboardKey order by switchboardmenu.dialdigit asc");
			query.setString("switchboardKey", switchboardKey);
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result.size());
		} catch (Exception e) {
			result = null;
			logger.error("get switchboard menus failed", e);
			throw new MICSException("Failed to get switchboard menus",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public List getSwitchboardsubmenus(String switchboardmenuKey)throws MICSException{
		logger.debug("getSwitchboardsubmenus");
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			
			////logger.debug("session: "+ session );
			Query query = session.createQuery("select switchboardsubmenu from Switchboardsubmenu as switchboardsubmenu where switchboardsubmenu.switchboardmenukey = :switchboardmenuKey order by switchboardsubmenu.dialdigit asc");
			query.setString("switchboardmenuKey", switchboardmenuKey);
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result.size());
		} catch (Exception e) {
			result = null;
			logger.error("get switchboard sub menus failed", e);
			throw new MICSException("Failed to get switchboard sub menus",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	public List getSwitchboardsubmenusInTX(String switchboardmenuKey,Session session)throws MICSException{
		logger.debug("getSwitchboardsubmenusInTX");
		List result = null;
		try {
			
			//logger.debug("session: "+ session );
			Query query = session.createQuery("select switchboardsubmenu from Switchboardsubmenu as switchboardsubmenu where switchboardsubmenu.switchboardmenukey = :switchboardmenuKey order by switchboardsubmenu.dialdigit asc");
			query.setString("switchboardmenuKey", switchboardmenuKey);
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result.size());
		} catch (Exception e) {
			result = null;
			logger.error("get switchboard sub menus failed", e);
			throw new MICSException("Failed to get switchboard sub menus",e);
		} 
		return result;
	}
	public Switchboardsubmenu getSwitchboardsubmenuByDigitInTX(BigDecimal dialDigit,String switchboardmenuKey,Session session)throws MICSException{
		logger.debug("getSwitchboardsubmenuByDigitInTX");
		Switchboardsubmenu result = null;
		try {
			
			//logger.debug("session: "+ session );
			Query query = session.createQuery("select switchboardsubmenu from Switchboardsubmenu as switchboardsubmenu where switchboardsubmenu.switchboardmenukey = :switchboardmenuKey and switchboardsubmenu.dialdigit = :dialdigit");
			query.setString("switchboardmenuKey", switchboardmenuKey);
			query.setBigDecimal("dialdigit", dialDigit);
			logger.debug("queryString: " + query.getQueryString());
			result =  (Switchboardsubmenu) query.uniqueResult();
			logger.debug("result: " + result);
		} catch (Exception e) {
			result = null;
			logger.error("get switchboard sub menu failed", e);
		} 
		return result;
	}

	@SuppressWarnings("rawtypes")
	public List getTimebands()throws MICSException{
		logger.debug("getTimebands");
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			
			Query query = session.createQuery("select timeband from Timeband as timeband order by timeband.id.timebandkey asc");
			
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result.size());
		} catch (Exception e) {
			result = null;
			logger.error("get timebands failed", e);
			throw new MICSException("Failed to get timebands ",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public List getMicslog()throws MICSException{
		logger.debug("getMicslog");
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			
			Query query = session.createQuery("select micslog from Micslog as micslog order by micslog.id desc");
			
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result.size());
		} catch (Exception e) {
			result = null;
			logger.error("get micslog failed", e);
			throw new MICSException("Failed to get micslog ",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	@SuppressWarnings("rawtypes")
	public void createCompanyAnno(List annoList) throws MICSException{
		logger.debug("createCompanyAnno");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			int itIdx = 1;
			for (Iterator iterator = annoList.iterator(); iterator.hasNext();) {
				Announcement currAnno = (Announcement) iterator.next();
				logger.debug(itIdx + " currAnno: " +currAnno.getAnnouncementkey() + "="+ currAnno.getAnnouncementname() + "=" + currAnno.getAnnouncementdesc() + "=" + currAnno.getSrfelementid() + "-" + currAnno.getAnnoid()+ "=" + currAnno.getCompanykey()+ "="+ currAnno.getUpdateuser());
				if (currAnno.getAnnouncementkey() == null) {
					logger.debug("Announcement key is null get available anno key");
					//No Announcement Key get available key first
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
					sdf.format(new Date());
					String awalKey = sdf.format(new Date());
					String micsAnnoAvailableKey = getAvailableKeyInTX(awalKey, "announcementkey", "Announcement",session);
					currAnno.setAnnouncementkey(micsAnnoAvailableKey);
				}
				if (currAnno.getAnnouncementkey().trim().equals("")) {
					logger.debug("Announcement key is empty get available anno key");
					//No Announcement Key get available key first
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
					sdf.format(new Date());
					String awalKey = sdf.format(new Date());
					String micsAnnoAvailableKey = getAvailableKeyInTX(awalKey, "announcementkey", "Announcement",session);
					currAnno.setAnnouncementkey(micsAnnoAvailableKey);
				}
				currAnno.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
				session.saveOrUpdate(currAnno);
				itIdx++;
			}
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to create/update company announcement",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	public void createCompanySwitchboard(Switchboard newSwitchboard,List switchboardMenu,List deleteMenus,List deleteSubmenus) throws MICSException{
		logger.debug("createCompanySwitchboard");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			sdf.format(new Date());
			String awalKey = sdf.format(new Date());
			if (newSwitchboard.getSwitchboardkey()== null || newSwitchboard.getSwitchboardkey().trim().equals("")) {
				//No key mean new switchboard get availablekey from DB
				String micsSBAvailableKey = getAvailableKeyInTX(awalKey, "switchboardkey", "Switchboard",session);
				newSwitchboard.setSwitchboardkey(micsSBAvailableKey);
			}
			logger.debug("Save switchboard " + newSwitchboard);
			session.saveOrUpdate(newSwitchboard);
			//Save
			int itIdx = 1;
			for (Iterator iterator = switchboardMenu.iterator(); iterator.hasNext();) {
				
				SwitchboardEntry seMenu = (SwitchboardEntry) iterator.next();
				Switchboardmenu sbMenu = seMenu.getMenu();
				logger.debug(itIdx + " sbMenu: " +sbMenu.getSwitchboardmenukey() + "="+ sbMenu.getSwitchboardmenuname()+ "=" + sbMenu.getDescription() + "=" + sbMenu.getDestinationnumber()+ "-" + sbMenu.getDialdigit() );
				if (sbMenu.getSwitchboardmenukey() == null ||sbMenu.getSwitchboardmenukey().trim().equals("") ) {
					logger.debug("Switchboard menu key is not set get available key");
					//No Switchboard menu Key get available key first
					
					String micsSBMenuAvailableKey = getAvailableKeyInTX(awalKey, "switchboardmenukey", "Switchboardmenu",session);
					sbMenu.setSwitchboardmenukey(micsSBMenuAvailableKey);
				}
				sbMenu.setSwitchboardkey(newSwitchboard.getSwitchboardkey());
				sbMenu.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
				logger.debug("Save menu " + itIdx + " " + sbMenu);
				session.saveOrUpdate(sbMenu);
				List submenu = seMenu.getSubMenu();
				if (submenu != null && !submenu.isEmpty()) {
					for (Iterator iterSubMenu = submenu.iterator(); iterSubMenu
							.hasNext();) {
						Switchboardsubmenu switchSubMenu = (Switchboardsubmenu) iterSubMenu.next();
						logger.debug("sub menu " + switchSubMenu.getDialdigit() + " " + switchSubMenu.getDestinationnumber());
						switchSubMenu.setSwitchboardmenukey(sbMenu.getSwitchboardmenukey());
						/*
						//Check if old submenu exist
						Switchboardsubmenu oldSwitchSubMenu = getSwitchboardsubmenuByDigitInTX(switchSubMenu.getDialdigit(), sbMenu.getSwitchboardmenukey(), session);
						logger.debug("oldSwitchSubMenu: " + oldSwitchSubMenu);
						if (oldSwitchSubMenu != null) {
							//Set the old Menu new parameter
							oldSwitchSubMenu.setDescription(switchSubMenu.getDescription());
							oldSwitchSubMenu.setDestinationnumber(switchSubMenu.getDestinationnumber());
							oldSwitchSubMenu.setSwitchboardsubmenuname(switchSubMenu.getSwitchboardsubmenuname());
							logger.debug("Update submenu " + oldSwitchSubMenu);
							session.saveOrUpdate(oldSwitchSubMenu);
						}else {
							if (switchSubMenu.getSwitchboardsubmenukey() == null ||switchSubMenu.getSwitchboardsubmenukey().trim().equals("") ) {
								logger.debug("Switchboard sub menu key is not set get available key");
								String micsSubMenuAvailableKey = getAvailableKeyInTX(awalKey,"switchboardsubmenukey","Switchboardsubmenu",session);
								switchSubMenu.setSwitchboardsubmenukey(micsSubMenuAvailableKey);
							}
							logger.debug("Save new submenu " + switchSubMenu);
							session.saveOrUpdate(switchSubMenu);
						}
						*/
						if (switchSubMenu.getSwitchboardsubmenukey() == null ||switchSubMenu.getSwitchboardsubmenukey().trim().equals("") ) {
							logger.debug("Switchboard sub menu key is not set get available key");
							String micsSubMenuAvailableKey = getAvailableKeyInTX(awalKey,"switchboardsubmenukey","Switchboardsubmenu",session);
							switchSubMenu.setSwitchboardsubmenukey(micsSubMenuAvailableKey);
						}
						logger.debug("Save new submenu " + switchSubMenu);
						session.saveOrUpdate(switchSubMenu);
					}
					
				} else 
				itIdx++;
			}
			
			for (Iterator iterator = deleteMenus.iterator(); iterator.hasNext();) {
				Switchboardmenu sbMenu = (Switchboardmenu) iterator.next();
				session.delete(sbMenu);
			}
			
			for (Iterator iterator = deleteSubmenus.iterator(); iterator.hasNext();) {
				Switchboardsubmenu switchSubMenu = (Switchboardsubmenu) iterator.next();
				session.delete(switchSubMenu);
			}
			
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to create/update company switchboard",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	public void createEmployee(Micsuser newUser,List newNumbers,String sipPassword) throws MICSException{
		logger.debug("createEmployee");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			logger.debug("Save new user: " + newUser);
			session.saveOrUpdate(newUser);
			logger.debug("Save user numbers");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			sdf.format(new Date());
			String awalKey = sdf.format(new Date());
			for (Iterator iterNumber = newNumbers.iterator(); iterNumber.hasNext();) {
				AddNumberRequest currNumberRequest = (AddNumberRequest) iterNumber.next();
				//Get Available Key
				String micsUserNumberAvailableKey = getAvailableKeyInTX(awalKey, "micsusernumber", "Micsusernumber",session);
				logger.debug("micsUserNumberAvailableKey "+micsUserNumberAvailableKey);
				Micsusernumber newUserNumber = new Micsusernumber();
				newUserNumber.setMicsusernumber(micsUserNumberAvailableKey);
				newUserNumber.setCompanykey(newUser.getCompanykey());
				newUserNumber.setMicsuserkey(newUser.getMicsuserkey());
				newUserNumber.setPrivatenumber(currNumberRequest.getPrivateNumber());
				newUserNumber.setPublicnumber(currNumberRequest.getPublicNumber());
				newUserNumber.setImsi(currNumberRequest.getImsi());
				newUserNumber.setSipuri(currNumberRequest.getSipuri());
				newUserNumber.setNumbertype(new BigDecimal(currNumberRequest.getNumberType()));
				newUserNumber.setOrderindex(new BigDecimal(currNumberRequest.getOrderIndex()));
				newUserNumber.setUpdateuser(newUser.getUpdateuser());
				newUserNumber.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
				logger.info("add user number: " + newUserNumber);
				session.saveOrUpdate(newUserNumber);
				//Check if user type is virtual no need to send provisioning request
				if (newUser.getUsertype().intValue() != UserTypeEnum.VIRTUAL_ON_NET.ordinal()){
//					int provResult = 
			//	 [ATUL]
				  	ProvisioningResponse response =	ProvisioningRequest.activateInTX(newUserNumber.getMicsuserkey(), newUserNumber.getImsi() , 
							newUserNumber.getPublicnumber(), newUserNumber.getPrivatenumber(), newUserNumber.getNumbertype().intValue(), 
							newUserNumber.getOrderindex().intValue(), sipPassword, 
							newUserNumber.getSipuri(), newUser.getCompanykey(), session);
					
					if (response != null && response.getRequestId() > 0) {
						newUserNumber.setProv_req_id(new BigDecimal(response.getRequestId()));
						if (response.getCode().equals(ProvisioningRequest.PROV_SUCCESS) ||  
								response.getCode().equals(ProvisioningRequest.PROV_ONGOING)){

							session.saveOrUpdate(newUserNumber);
						}else{
//							throw new MICSException("Failed to activate MICSNUMBER for userkey: " + newUserNumber.getMicsuserkey()+ " " + newUserNumber.getPublicnumber() + " " + newUserNumber.getPrivatenumber() ) ;
							throw new MICSException(response.getMessage());
						}
					} else {
						throw new MICSException("Failed to send activate provisioning request, for userkey: " + newUserNumber.getMicsuserkey()+ " " + newUserNumber.getPublicnumber() + " " + newUserNumber.getPrivatenumber() ) ;
					}
					
				}
			}
			//Insert into Opencloud
			//Check success or not
			// rhinoProfile.create(profileUser,List<Profile Number>);
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to create employee", e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session", e);
			}
		}
	}
	
	public void createHuntinggroup(Huntinggroup newHuntinggroup,List huntinggroupSchedules) throws MICSException{
		logger.debug("createHuntinggroup");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			logger.debug("Save new huntinggroup: " + newHuntinggroup);
			session.saveOrUpdate(newHuntinggroup);
			if (huntinggroupSchedules != null && huntinggroupSchedules.size() > 0) {
				logger.debug("Save hunting schedules");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
				sdf.format(new Date());
				String awalKey = sdf.format(new Date());
				for (Iterator iterHuntingSchedule = huntinggroupSchedules.iterator(); iterHuntingSchedule.hasNext();) {
					AddHuntingSchedule currHuntingSchedule = (AddHuntingSchedule) iterHuntingSchedule.next();
					//Get Available Key
					String micsHuntingScheduleAvailableKey = getAvailableKeyInTX(awalKey, "huntingschedulekey", "Huntingschedule",session);
					logger.debug("micsHuntingScheduleAvailableKey "+micsHuntingScheduleAvailableKey);
					Huntingschedule newHuntingSchedule = new Huntingschedule();
					newHuntingSchedule.setHuntinggroupkey(newHuntinggroup.getHuntinggroupkey());
					newHuntingSchedule.setHuntingschedulekey(micsHuntingScheduleAvailableKey);
					newHuntingSchedule.setOrderindex(new BigDecimal(currHuntingSchedule.getOrderindex()));
					newHuntingSchedule.setTimebandkey(currHuntingSchedule.getTimebandkey());
					newHuntingSchedule.setUpdateuser(newHuntinggroup.getUpdateuser());
					newHuntingSchedule.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
					logger.debug("add hunting schedule: " + newHuntingSchedule);
					session.saveOrUpdate(newHuntingSchedule);
					//Add Hunting Schedule Member
					List currMemberReq = currHuntingSchedule.getMemberReq();
					logger.debug("currMemberReq: " + currMemberReq);
					if (currMemberReq != null && currMemberReq.size() > 0) {
						logger.debug("Save hunting schedule members");
						for (Iterator iterHuntingScheduleMember = currMemberReq.iterator(); iterHuntingScheduleMember
								.hasNext();) {
							AddUserRequest currAddUserRequest = (AddUserRequest) iterHuntingScheduleMember.next();
							Huntingmember currHuntingMember = new Huntingmember();
							String micsHuntingMemberAvailableKey = getAvailableKeyInTX(awalKey, "huntingmemberkey", "Huntingmember",session);
							currHuntingMember.setHuntingmemberkey(micsHuntingMemberAvailableKey);
							currHuntingMember.setHuntingschedulekey(micsHuntingScheduleAvailableKey);
							currHuntingMember.setOrderindex(new BigDecimal(currAddUserRequest.getOrderindex()));
							currHuntingMember.setTargetnumber(currAddUserRequest.getMicsPubNumber());
							currHuntingMember.setUpdateuser(newHuntinggroup.getUpdateuser());
							currHuntingMember.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
							logger.debug("add hunting schedule member: " + currHuntingMember);
							session.saveOrUpdate(currHuntingMember);
						}
					} else {
						logger.debug("No hunting schedule members to save");
					}
				}
			} else {
				logger.debug("No Hunting Schedules to save");
			}
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to create huntinggroup",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	public void createCommunity(Community newCommunity,List newMembers) throws MICSException{
		logger.debug("createCommunity");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			logger.debug("Save new community: " + newCommunity);
			session.saveOrUpdate(newCommunity);
			logger.error("Save community members");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			sdf.format(new Date());
			String awalKey = sdf.format(new Date());
			for (Iterator iterMember = newMembers.iterator(); iterMember.hasNext();) {
				AddUserRequest currUserRequest = (AddUserRequest) iterMember.next();
				logger.error("currUserRequest: " + currUserRequest);
				//Get Available Key
				String micsCommunityMemberrAvailableKey = getAvailableKeyInTX(awalKey, "communitymemberkey", "Communitymember",session);
				logger.error("micsCommunityMemberrAvailableKey "+micsCommunityMemberrAvailableKey);
				Communitymember newCommunityMember = new Communitymember();
				newCommunityMember.setCommunitykey(newCommunity.getCommunitykey());
				newCommunityMember.setCommunitymemberkey(micsCommunityMemberrAvailableKey);
				newCommunityMember.setMicsuserkey(currUserRequest.getMicsUserKey());
				newCommunityMember.setUpdateuser(newCommunity.getUpdateuser());
				newCommunityMember.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
				logger.debug("add community member: " + newCommunityMember);
				session.saveOrUpdate(newCommunityMember);
			}
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to create community",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	public void createTimeband(Timeband newTimeband) throws MICSException{
		logger.debug("createTimeband");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			logger.debug("Save new timeband: " + newTimeband);
			session.saveOrUpdate(newTimeband);
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to create timeband",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	public void createMisclog(Micslog newMisclog) throws MICSException{
		logger.debug("createMisclog");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(newMisclog);
			tx.commit();
			logger.debug(newMisclog + " logged.");
		} catch (Exception e) {
			tx.rollback();
			logger.error("failed to log. " + newMisclog);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		
	}
	
	public Timeband getTimebandByKey(String timebandkey) throws MICSException {
		logger.debug("getTimebandByKey");
		Session session = null;
		Timeband result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			Query query = session.createQuery("select timeband from Timeband as timeband where timeband.id.timebandkey = :timebandkey");
			query.setString("timebandkey", timebandkey);
			logger.debug("queryString: " + query.getQueryString());
			result =  (Timeband) query.uniqueResult();
			logger.debug("result: " + result);
		} catch (Exception e) {
			result = null;
			logger.error("get Timeband by key failed", e);
			throw new MICSException("Failed to get timeband by key ",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public List getTimebandList(String searchName)throws MICSException{
		logger.debug("getTimebandList");
		Session session = null;
		List result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			if (searchName == null) {
				searchName = "";
			}
			searchName = searchName + "%";
			Query query = session.createQuery("select timeband from Timeband as timeband where timeband.id.timebandname like :timebandName order by timeband.id.timebandkey asc");
			query.setString("timebandName", searchName);
			logger.debug("queryString: " + query.getQueryString());
			result =  query.list();
			logger.debug("result: " + result.size());
		} catch (Exception e) {
			result = null;
			logger.error("get timeband list failed", e);
			throw new MICSException("Failed to get timeband list ",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	
	public void createGroup(Micsgroup newMicsgroup) throws MICSException{
		logger.debug("createGroup");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			logger.debug("Save new group: " + newMicsgroup);
			session.saveOrUpdate(newMicsgroup);
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to create group",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	public void createPartnership(Partnership newPartnership) throws MICSException{
		logger.debug("createPartnership");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			logger.debug("Save new partnership: " + newPartnership);
			session.saveOrUpdate(newPartnership);
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to create partnership",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	public void createCompany(Company newCompany,Micsgroup newMicsgroup, Webuser newWebUser) throws MICSException{
		logger.debug("createCompany");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			logger.debug("Save new company: " + newCompany);
			session.saveOrUpdate(newCompany);
			logger.debug("Save new default group: " + newMicsgroup);
			session.saveOrUpdate(newMicsgroup);
			logger.debug("Save new webuser: " + newWebUser);
			session.saveOrUpdate(newWebUser);
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to create company",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	public void editUser(Webuser webuser) throws MICSException{
		logger.debug("editUser()");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			logger.debug("Edit user: " + webuser);
			session.saveOrUpdate(webuser);
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to edit user",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	public void editCompany(Company company) throws MICSException{
		logger.debug("editCompany()");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			logger.debug("Edit company: " + company);
			session.saveOrUpdate(company);
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to edit company",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	
	public void editGroup(Micsgroup micsgroup) throws MICSException{
		logger.debug("editGroup()");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			logger.debug("Edit group: " + micsgroup);
			session.saveOrUpdate(micsgroup);
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to edit group",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	public void editPartnership(Partnership partnership) throws MICSException{
		logger.debug("editPartnership()");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			logger.debug("Edit partnership: " + partnership);
			session.saveOrUpdate(partnership);
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to edit partnership",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	public void editHuntinggroup(Huntinggroup newHuntinggroup,List oldHuntingSchedules, List newHuntingSchedules) throws MICSException{
		logger.debug("editHuntinggroup");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			logger.debug("Edit huntinggroup: " + newHuntinggroup);
			session.saveOrUpdate(newHuntinggroup);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			sdf.format(new Date());
			String awalKey = sdf.format(new Date());
			//Update hunting Schedule
			for (Iterator iterUpdateHS = newHuntingSchedules.iterator(); iterUpdateHS
					.hasNext();) {
				AddHuntingSchedule currHS = (AddHuntingSchedule) iterUpdateHS.next();
				logger.debug("Hunting Schedule: " + currHS.getHuntinggroupkey() + " - " + currHS.getHuntingschedulekey() + " - " + currHS.getTimebandname() + " - " + currHS.getActionType() );
				String strActionType = currHS.getActionType();
				if (strActionType == null) {
					strActionType = "";
				}
				if (strActionType.trim().equals("ADD")) {
					logger.debug("Add New Hunting Schedule");
					String micsHuntingScheduleAvailableKey = getAvailableKeyInTX(awalKey, "huntingschedulekey", "Huntingschedule",session);
					logger.debug("micsHuntingScheduleAvailableKey "+micsHuntingScheduleAvailableKey);
					Huntingschedule newHuntingSchedule = new Huntingschedule();
					newHuntingSchedule.setHuntinggroupkey(newHuntinggroup.getHuntinggroupkey());
					newHuntingSchedule.setHuntingschedulekey(micsHuntingScheduleAvailableKey);
					newHuntingSchedule.setOrderindex(new BigDecimal(currHS.getOrderindex()));
					newHuntingSchedule.setTimebandkey(currHS.getTimebandkey());
					newHuntingSchedule.setUpdateuser(newHuntinggroup.getUpdateuser());
					newHuntingSchedule.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
					logger.debug("add hunting schedule: " + newHuntingSchedule);
					session.saveOrUpdate(newHuntingSchedule);
					//Add Hunting Schedule Member
					List currMemberReq = currHS.getMemberReq();
					logger.debug("currMemberReq: " + currMemberReq);
					if (currMemberReq != null && currMemberReq.size() > 0) {
						logger.debug("Save hunting schedule members");
						for (Iterator iterHuntingScheduleMember = currMemberReq.iterator(); iterHuntingScheduleMember
								.hasNext();) {
							AddUserRequest currAddUserRequest = (AddUserRequest) iterHuntingScheduleMember.next();
							Huntingmember currHuntingMember = new Huntingmember();
							String micsHuntingMemberAvailableKey = getAvailableKeyInTX(awalKey, "huntingmemberkey", "Huntingmember",session);
							currHuntingMember.setHuntingmemberkey(micsHuntingMemberAvailableKey);
							currHuntingMember.setHuntingschedulekey(micsHuntingScheduleAvailableKey);
							currHuntingMember.setOrderindex(new BigDecimal(currAddUserRequest.getOrderindex()));
							currHuntingMember.setTargetnumber(currAddUserRequest.getMicsPubNumber());
							currHuntingMember.setUpdateuser(newHuntinggroup.getUpdateuser());
							currHuntingMember.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
							logger.debug("add hunting schedule member: " + currHuntingMember);
							session.saveOrUpdate(currHuntingMember);
						}
					} else {
						logger.debug("No hunting schedule members to save");
					}
					
				} else {
					if (strActionType.trim().equals("UPDATE")) {
						logger.debug("Update Hunting Schedule");
						Huntingschedule delHuntingSchedule = new Huntingschedule();
						delHuntingSchedule.setHuntinggroupkey(newHuntinggroup.getHuntinggroupkey());
						delHuntingSchedule.setHuntingschedulekey(currHS.getHuntingschedulekey());
						delHuntingSchedule.setOrderindex(new BigDecimal(currHS.getOrderindex()));
						delHuntingSchedule.setTimebandkey(currHS.getTimebandkey());
						delHuntingSchedule.setUpdateuser(newHuntinggroup.getUpdateuser());
						delHuntingSchedule.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
						logger.debug("Update hunting schedule: " + delHuntingSchedule);
						session.saveOrUpdate(delHuntingSchedule);
						List currMemberReq = currHS.getMemberReq();
						if (currMemberReq != null && currMemberReq.size() > 0) {
							logger.debug("Upddate Hunting schedule members");
							for (Iterator iterUpdateMember = currMemberReq.iterator(); iterUpdateMember
									.hasNext();) {
								AddUserRequest currUpdateMemberReq = (AddUserRequest) iterUpdateMember.next();
								Huntingmember currHuntingMember = new Huntingmember();
								String tempMemberKey = currUpdateMemberReq.getMemberKey();
								logger.debug("Member Key : " + tempMemberKey);
								if (tempMemberKey == null || tempMemberKey.trim().equals("")) {
									tempMemberKey = getAvailableKeyInTX(awalKey, "huntingmemberkey", "Huntingmember",session);
								}
								logger.debug("Checked Member Key : " + tempMemberKey);
								currHuntingMember.setHuntingmemberkey( tempMemberKey);
								currHuntingMember.setHuntingschedulekey(currHS.getHuntingschedulekey());
								currHuntingMember.setOrderindex(new BigDecimal(currUpdateMemberReq.getOrderindex()));
								currHuntingMember.setTargetnumber(currUpdateMemberReq.getMicsPubNumber());
								currHuntingMember.setUpdateuser(newHuntinggroup.getUpdateuser());
								currHuntingMember.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
								logger.debug("update hunting schedule member: " + currHuntingMember);
								session.saveOrUpdate(currHuntingMember);
							}
						} else {
							logger.debug("No hunting schedule members to update");
						}
						//delete Member no longer use check with existing DB Member
						for (Iterator iterOldHS = oldHuntingSchedules.iterator(); iterOldHS
								.hasNext();) {
							Object[] currOldHS = (Object[]) iterOldHS.next();
							Huntingschedule oldHuntingSchedule = (Huntingschedule) currOldHS[0];
							if (oldHuntingSchedule.getHuntingschedulekey().equals(currHS.getHuntingschedulekey())) {
								//Check with the member
								List currOldMember =  (List) currOldHS[1];
								for (Iterator iterCurrOldMember = currOldMember
										.iterator(); iterCurrOldMember.hasNext();) {
									Huntingmember currOldHuntingMember = (Huntingmember) iterCurrOldMember.next();
									int oldMemberExist = 0;
									List currNewHuntingMember = currHS.getMemberReq();
									for (Iterator iterNewOldHuntingMember = currNewHuntingMember
											.iterator(); iterNewOldHuntingMember.hasNext();) {
										AddUserRequest currNewOldHM = (AddUserRequest) iterNewOldHuntingMember
												.next();
										String currOldMemberKey = currOldHuntingMember.getHuntingmemberkey();
										if (currOldMemberKey == null) {
											currOldMemberKey = "";
										}
										if (currOldMemberKey.equals(currNewOldHM.getMemberKey())) {
											oldMemberExist++;
										}
									}
									if (oldMemberExist==0) {
										logger.debug("Not found on the new list, delete from DB " + currOldHuntingMember.getHuntingmemberkey() + " " + currOldHuntingMember.getTargetnumber());
										session.delete(currOldHuntingMember);
									}
									
								}
								
							}
							
						}
						
					} else {
						if (strActionType.trim().equals("DELETE")) {
							logger.debug("Delete Hunting Schedule");
							Huntingschedule delHuntingSchedule = new Huntingschedule();
							delHuntingSchedule.setHuntinggroupkey(newHuntinggroup.getHuntinggroupkey());
							delHuntingSchedule.setHuntingschedulekey(currHS.getHuntingschedulekey());
							delHuntingSchedule.setOrderindex(new BigDecimal(currHS.getOrderindex()));
							delHuntingSchedule.setTimebandkey(currHS.getTimebandkey());
							delHuntingSchedule.setUpdateuser(newHuntinggroup.getUpdateuser());
							delHuntingSchedule.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
							logger.debug("delete hunting schedule: " + delHuntingSchedule);
							session.delete(delHuntingSchedule);
							//Delete Hunting Schedule Member
							List currMemberReq = currHS.getMemberReq();
							logger.debug("currMemberReq: " + currMemberReq);
							if (currMemberReq != null && currMemberReq.size() > 0) {
								logger.debug("Delete hunting schedule members");
								for (Iterator iterHuntingScheduleMember = currMemberReq.iterator(); iterHuntingScheduleMember
										.hasNext();) {
									AddUserRequest currAddUserRequest = (AddUserRequest) iterHuntingScheduleMember.next();
									Huntingmember currHuntingMember = new Huntingmember();
									currHuntingMember.setHuntingmemberkey(currAddUserRequest.getMemberKey());
									currHuntingMember.setHuntingschedulekey(currHS.getHuntingschedulekey());
									currHuntingMember.setOrderindex(new BigDecimal(currAddUserRequest.getOrderindex()));
									currHuntingMember.setTargetnumber(currAddUserRequest.getMicsPubNumber());
									currHuntingMember.setUpdateuser(newHuntinggroup.getUpdateuser());
									currHuntingMember.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
									logger.debug("delete hunting schedule member: " + currHuntingMember);
									session.delete(currHuntingMember);
								}
							} else {
								logger.debug("No hunting schedule members to delete");
							}
							
						} else {
							logger.debug("No Change for Hunting Schedule: " + currHS.getHuntinggroupkey() + " - " + currHS.getHuntingschedulekey() + " - " + currHS.getTimebandname()); 
						}

					}
				}
				
			}
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to edit huntinggroup",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	public void editHuntinggroupOld(Huntinggroup newHuntinggroup,List oldHuntingSchedules, List newHuntingSchedules) throws MICSException{
		logger.debug("editHuntinggroup");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			logger.debug("Edit huntinggroup: " + newHuntinggroup);
			session.saveOrUpdate(newHuntinggroup);
			//Delete Old Hunting Schedules
			if (oldHuntingSchedules != null && oldHuntingSchedules.size() > 0) {
				logger.debug("Delete old hunting schedules no longer exist");
				for (Iterator iterOldSchedules = oldHuntingSchedules.iterator(); iterOldSchedules
						.hasNext();) {
					Object[] currOldHS = (Object[]) iterOldSchedules.next();
					Huntingschedule oldHuntingSchedule = (Huntingschedule) currOldHS[0];
					//Check if the hunting schedule is no longer use
					int oldHuntingScheduleExist = 0;
					for (Iterator iteratorOldNew = newHuntingSchedules.iterator(); iteratorOldNew
							.hasNext();) {
						AddHuntingSchedule currNewHS = (AddHuntingSchedule) iteratorOldNew.next();
						if (oldHuntingSchedule.getTimebandkey().equals(currNewHS.getTimebandkey())) {
							oldHuntingScheduleExist++;
						}
						//Need to add more checking later
					}
					if (oldHuntingScheduleExist == 0) {
						logger.debug("delete hunting schedule: " + oldHuntingSchedule);
						session.delete(oldHuntingSchedule);
						//Delete hunting schedules member
						List currOldMember =  (List) currOldHS[1];
						logger.debug("currOldMember: " + currOldMember);
						if (currOldMember != null && currOldMember.size() > 0) {
							for (Iterator iterCurrOldMember = currOldMember.iterator(); iterCurrOldMember
									.hasNext();) {
								Huntingmember currOldHuntingMember = (Huntingmember) iterCurrOldMember.next();
								logger.debug("delete hunting schedule member: " + currOldHuntingMember);
								session.delete(currOldHuntingMember);
							}
						} else {
							logger.debug("No Hunting Schedules member to delete");
						}
					} else {
						//Update existing hunting schedule
						for (Iterator iteratorOldNew = newHuntingSchedules.iterator(); iteratorOldNew
								.hasNext();) {
							AddHuntingSchedule currNewHS = (AddHuntingSchedule) iteratorOldNew.next();
							if (oldHuntingSchedule.getTimebandkey().equals(currNewHS.getTimebandkey())) {
								oldHuntingSchedule.setOrderindex(new BigDecimal(currNewHS.getOrderindex()));
								session.save(oldHuntingSchedule);
								//Check for Member update
								List currOldMember =  (List) currOldHS[1];
								//Delete old member not use
								for (Iterator iterOldMember = currOldMember
										.iterator(); iterOldMember.hasNext();) {
									Huntingmember currOldHuntingmember = (Huntingmember) iterOldMember.next();
									logger.debug("currOldHuntingmember: " + currOldHuntingmember.getTargetnumber());
									//If member not exist in the new list delete it
									List newMemberList = currNewHS.getMemberReq();
									int oldMemberExist = 0;
									for (Iterator iterCheckOldMember = newMemberList
											.iterator(); iterCheckOldMember.hasNext();) {
										AddUserRequest currUserRequest = (AddUserRequest) iterCheckOldMember
												.next();
										logger.debug("currUserRequest: " + currUserRequest.getMicsPubNumber());
										if (currOldHuntingmember.getTargetnumber().trim().equals(currUserRequest.getMicsPubNumber().trim())) {
											oldMemberExist++;
											logger.debug("Found a match");
										}
									}
									if (oldMemberExist==0) {
										//No Longer exist
										session.delete(currOldHuntingmember);
									}
								}
								//Add New Member
								List currNewMember = currNewHS.getMemberReq();
								for (Iterator iterNewMember = currNewMember
										.iterator(); iterNewMember.hasNext();) {
									AddUserRequest currUserRequest = (AddUserRequest) iterNewMember.next();
									int newMemberExist = 0;
									for (Iterator iterCheckNewMember = currOldMember
											.iterator(); iterCheckNewMember.hasNext();) {
										Huntingmember currHuntingMember = (Huntingmember) iterCheckNewMember
												.next();
										if (currUserRequest.getMicsPubNumber().trim().equals(currHuntingMember.getTargetnumber())) {
											newMemberExist++;
										}
										
									}
									if (newMemberExist==0) {
										//Member
										SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
										sdf.format(new Date());
										String awalKey = sdf.format(new Date());
										Huntingmember currHuntingMember = new Huntingmember();
										String micsHuntingMemberAvailableKey = getAvailableKeyInTX(awalKey, "huntingmemberkey", "Huntingmember",session);
										currHuntingMember.setHuntingmemberkey(micsHuntingMemberAvailableKey);
										currHuntingMember.setHuntingschedulekey(oldHuntingSchedule.getHuntingschedulekey());
										currHuntingMember.setOrderindex(new BigDecimal(currUserRequest.getOrderindex()));
										currHuntingMember.setTargetnumber(currUserRequest.getMicsPubNumber());
										currHuntingMember.setUpdateuser(newHuntinggroup.getUpdateuser());
										currHuntingMember.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
										logger.debug("add hunting schedule member: " + currHuntingMember);
										session.saveOrUpdate(currHuntingMember);
									}
									
								}
								
								
							}
							
						}
						
					}
				}
			} else {
				logger.debug("No Hunting Schedules to delete");
			}
			//Add New Hunting Schedules
			if (newHuntingSchedules != null && newHuntingSchedules.size() > 0) {
				logger.debug("Add/Edit hunting schedules");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
				sdf.format(new Date());
				String awalKey = sdf.format(new Date());
				for (Iterator iterHuntingSchedule = newHuntingSchedules.iterator(); iterHuntingSchedule.hasNext();) {
					AddHuntingSchedule currHuntingSchedule = (AddHuntingSchedule) iterHuntingSchedule.next();
					//Check if the hunting schedule is no longer use
					int newHuntingScheduleExist = 0;
					for (Iterator iteratorNewOld = oldHuntingSchedules.iterator(); iteratorNewOld.hasNext();) {
						Object[] currOldHS = (Object[]) iteratorNewOld.next();
						Huntingschedule oldHuntingSchedule = (Huntingschedule) currOldHS[0];
						
						if (currHuntingSchedule.getTimebandkey().equals(oldHuntingSchedule.getTimebandkey())) {
							newHuntingScheduleExist++;
						}
						//Need to add more checking later
					}
					if (newHuntingScheduleExist == 0) {
						//New Hunting Schedule Add directly
						//Get Available Key
						String micsHuntingScheduleAvailableKey = getAvailableKeyInTX(awalKey, "huntingschedulekey", "Huntingschedule",session);
						logger.debug("micsHuntingScheduleAvailableKey "+micsHuntingScheduleAvailableKey);
						Huntingschedule newHuntingSchedule = new Huntingschedule();
						newHuntingSchedule.setHuntinggroupkey(newHuntinggroup.getHuntinggroupkey());
						newHuntingSchedule.setHuntingschedulekey(micsHuntingScheduleAvailableKey);
						newHuntingSchedule.setOrderindex(new BigDecimal(currHuntingSchedule.getOrderindex()));
						newHuntingSchedule.setTimebandkey(currHuntingSchedule.getTimebandkey());
						newHuntingSchedule.setUpdateuser(newHuntinggroup.getUpdateuser());
						newHuntingSchedule.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
						logger.debug("add hunting schedule: " + newHuntingSchedule);
						session.saveOrUpdate(newHuntingSchedule);
						//Add Hunting Schedule Member
						List currMemberReq = currHuntingSchedule.getMemberReq();
						logger.debug("currMemberReq: " + currMemberReq);
						if (currMemberReq != null && currMemberReq.size() > 0) {
							logger.debug("Save hunting schedule members");
							for (Iterator iterHuntingScheduleMember = currMemberReq.iterator(); iterHuntingScheduleMember
									.hasNext();) {
								AddUserRequest currAddUserRequest = (AddUserRequest) iterHuntingScheduleMember.next();
								Huntingmember currHuntingMember = new Huntingmember();
								String micsHuntingMemberAvailableKey = getAvailableKeyInTX(awalKey, "huntingmemberkey", "Huntingmember",session);
								currHuntingMember.setHuntingmemberkey(micsHuntingMemberAvailableKey);
								currHuntingMember.setHuntingschedulekey(micsHuntingScheduleAvailableKey);
								currHuntingMember.setOrderindex(new BigDecimal(currAddUserRequest.getOrderindex()));
								currHuntingMember.setTargetnumber(currAddUserRequest.getMicsPubNumber());
								currHuntingMember.setUpdateuser(newHuntinggroup.getUpdateuser());
								currHuntingMember.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
								logger.debug("add hunting schedule member: " + currHuntingMember);
								session.saveOrUpdate(currHuntingMember);
							}
						} else {
							logger.debug("No hunting schedule members to save");
						}
					} 
				}
			} else {
				logger.debug("No Hunting Schedules to add");
			}
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to edit huntinggroup",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	public void deleteHuntinggroup(Huntinggroup newHuntinggroup,List oldHuntingSchedules) throws MICSException{
		logger.debug("deleteHuntinggroup");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			
			//Delete Old Hunting Schedules
			if (oldHuntingSchedules != null && oldHuntingSchedules.size() > 0) {
				logger.debug("Delete old hunting schedules");
				for (Iterator iterOldSchedules = oldHuntingSchedules.iterator(); iterOldSchedules
						.hasNext();) {
					Object[] currOldHS = (Object[]) iterOldSchedules.next();
					Huntingschedule oldHuntingSchedule = (Huntingschedule) currOldHS[0];
					logger.debug("delete hunting schedule: " + oldHuntingSchedule);
					session.delete(oldHuntingSchedule);
					//Delete hunting schedules member
					List currOldMember =  (List) currOldHS[1];
					logger.debug("currOldMember: " + currOldMember);
					if (currOldMember != null && currOldMember.size() > 0) {
						for (Iterator iterCurrOldMember = currOldMember.iterator(); iterCurrOldMember
								.hasNext();) {
							Huntingmember currOldHuntingMember = (Huntingmember) iterCurrOldMember.next();
							logger.debug("delete hunting schedule member: " + currOldHuntingMember);
							session.delete(currOldHuntingMember);
						}
					} else {
						logger.debug("No Hunting Schedules member to delete");
					}
				}
			} else {
				logger.debug("No Hunting Schedules to delete");
			}
			logger.debug("Delete huntinggroup: " + newHuntinggroup);
			session.delete(newHuntinggroup);
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to edit huntinggroup",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void editCommunity(Community community, List oldMembers, List newMembers) throws MICSException{
		logger.debug("editCommunity()");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			logger.debug("Edit community: " + community);
			session.saveOrUpdate(community);
			//Delete old member
			if (oldMembers != null) {
				for (Iterator iterOldMember = oldMembers.iterator(); iterOldMember.hasNext();) {
					Communitymember currOldMember = (Communitymember) iterOldMember.next();
					logger.debug("Check for del user " + currOldMember.getMicsuserkey());
					int checkOldExist = 0;
					for (Iterator iterOldNew = newMembers.iterator(); iterOldNew
							.hasNext();) {
						AddUserRequest currAddUserReq = (AddUserRequest) iterOldNew.next();
						if (currOldMember.getMicsuserkey().trim().equals(currAddUserReq.getMicsUserKey().trim())) {
							checkOldExist++;
						}
					}
					if (checkOldExist == 0) {
						//Member no longer used
						session.delete(currOldMember);
						logger.debug("No exist in the new list, delete");
					} else {
						logger.debug("Exist in the new list, leave it");
					}
					
				}
				logger.debug("delete old member success");
			} else {
				logger.debug("No old member to delete");
			}
			
			//Add New Member
			if (newMembers != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
				sdf.format(new Date());
				String awalKey = sdf.format(new Date());
				
				for (Iterator iterMember = newMembers.iterator(); iterMember.hasNext();) {
					AddUserRequest currUserRequest = (AddUserRequest) iterMember.next();
					logger.debug("Check for Add user " + currUserRequest.getMicsUserKey());
					int checkNewExist = 0;
					for (Iterator iterNewOld = oldMembers.iterator(); iterNewOld
							.hasNext();) {
						Communitymember currOldMember = (Communitymember) iterNewOld.next();
						if (currUserRequest.getMicsUserKey().trim().equals(currOldMember.getMicsuserkey().trim())) {
							checkNewExist++;
						}
						
					}
					if (checkNewExist ==0) {
						//New no exist in the existing list add
						//Get Available Key
						String micsCommunityMemberrAvailableKey = getAvailableKeyInTX(awalKey, "communitymemberkey", "Communitymember",session);
						logger.debug("micsCommunityMemberrAvailableKey "+micsCommunityMemberrAvailableKey);
						Communitymember newCommunityMember = new Communitymember();
						newCommunityMember.setCommunitykey(community.getCommunitykey());
						newCommunityMember.setCommunitymemberkey(micsCommunityMemberrAvailableKey);
						newCommunityMember.setMicsuserkey(currUserRequest.getMicsUserKey());
						newCommunityMember.setUpdateuser(community.getUpdateuser());
						newCommunityMember.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
						logger.debug("add community member: " + newCommunityMember);
						session.saveOrUpdate(newCommunityMember);
					} else {
						logger.debug("Already exist just skipped");
					}
					
				}
				logger.debug("add  new members success");
			} else {
				logger.debug("No new member to add");
			}
			
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to edit community",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	public void editEmployee(Micsuser micsuser, List oldNumber, List newNumbers,String sipPassword) throws MICSException{
		logger.debug("editEmployee()");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			logger.debug("Edit employee: " + micsuser);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			sdf.format(new Date());
			String awalKey = sdf.format(new Date());
			//Delete Oldnumber that no longer exist in new Number
			for (Iterator iterOldNumber = oldNumber.iterator(); iterOldNumber.hasNext();) {
				Micsusernumber currOldNumber = (Micsusernumber) iterOldNumber.next();
				//Check with the new number exist or not (based on public number)
				int checkOldExist = 0;
				for (Iterator iterOldNew = newNumbers.iterator(); iterOldNew.hasNext();) {
					AddNumberRequest currNewNumber = (AddNumberRequest) iterOldNew.next();
					if (currOldNumber.getPublicnumber().equals(currNewNumber.getPublicNumber())) {
						checkOldExist++;
					}
					
				}
				if (checkOldExist == 0) {
					//Number no longer used
					logger.debug("Delete number that no longer used " + currOldNumber.getPublicnumber());
					session.delete(currOldNumber);
					//if user_type == VIRTUAL no need to send prov request
					//TODO
					if (micsuser.getUsertype().intValue() != UserTypeEnum.VIRTUAL_ON_NET.ordinal()){
//						int provResult = 
						ProvisioningResponse response = ProvisioningRequest.deactivateInTX(currOldNumber.getMicsuserkey(), currOldNumber.getImsi(), 
								currOldNumber.getPublicnumber(), currOldNumber.getPrivatenumber(), currOldNumber.getNumbertype().intValue(),"Y",session);

						logger.debug("provResult: " + response);
						if (response != null && response.getRequestId() > 0) {
							if (!response.getCode().equals(ProvisioningRequest.PROV_SUCCESS) &&  
									 !response.getCode().equals(ProvisioningRequest.PROV_ONGOING)){
								throw new MICSException(response.getMessage());
							}
						} else {
							throw new MICSException("Failed to send deactivate provisioning request, for userkey: " + currOldNumber.getMicsuserkey()+ " " + currOldNumber.getPublicnumber() + " " + currOldNumber.getPrivatenumber() ) ;
						}
					}
				
				}
			}
			//Add NewNumber
			for (Iterator iterNewNumber = newNumbers.iterator(); iterNewNumber.hasNext();) {
				AddNumberRequest currNumberRequest = (AddNumberRequest) iterNewNumber.next();
				int checkNewExist = 0;
				for (Iterator iterNewOldNumber = oldNumber.iterator(); iterNewOldNumber
						.hasNext();) {
					Micsusernumber currOldNumber = (Micsusernumber) iterNewOldNumber.next();
					if (currNumberRequest.getPublicNumber().equals(currOldNumber.getPublicnumber())) {
						checkNewExist++;
					}
				}
				if (checkNewExist == 0) {
					//New Number
					logger.debug("Add newly added number " + currNumberRequest.getPublicNumber());
					//Get Available Key
					String micsUserNumberAvailableKey = getAvailableKeyInTX(awalKey, "micsusernumber", "Micsusernumber",session);
					logger.debug("micsUserNumberAvailableKey "+micsUserNumberAvailableKey);
					Micsusernumber newUserNumber = new Micsusernumber();
					newUserNumber.setMicsusernumber(micsUserNumberAvailableKey);
					newUserNumber.setCompanykey(micsuser.getCompanykey());
					newUserNumber.setMicsuserkey(micsuser.getMicsuserkey());
					newUserNumber.setPrivatenumber(currNumberRequest.getPrivateNumber());
					newUserNumber.setPublicnumber(currNumberRequest.getPublicNumber());
					newUserNumber.setImsi(currNumberRequest.getImsi());
					newUserNumber.setSipuri(currNumberRequest.getSipuri());
					newUserNumber.setOrderindex(new BigDecimal(currNumberRequest.getOrderIndex()));
					newUserNumber.setNumbertype(new BigDecimal(currNumberRequest.getNumberType()));
					newUserNumber.setUpdateuser(micsuser.getUpdateuser());
					newUserNumber.setUpdatetimestamp(new Timestamp(System.currentTimeMillis()));
					logger.debug("add user number: " + newUserNumber);
					session.saveOrUpdate(newUserNumber);
					
					if (micsuser.getUsertype().intValue() != UserTypeEnum.VIRTUAL_ON_NET.ordinal()){
						/*
						int provResult = ProvisioningRequest.activateInTX(newUserNumber.getMicsuserkey(), newUserNumber.getImsi(), 
								newUserNumber.getPublicnumber(), newUserNumber.getPrivatenumber(), newUserNumber.getNumbertype().intValue(), 
								newUserNumber.getOrderindex().intValue(), sipPassword, 
								newUserNumber.getSipuri(), micsuser.getCompanykey(), session);
						if (provResult>0) {
							newUserNumber.setProv_req_id(new BigDecimal(provResult));
							session.saveOrUpdate(newUserNumber);
						} else {
							throw new MICSException("Failed to send activate provisioning request, for userkey: " + newUserNumber.getMicsuserkey()+ " " + newUserNumber.getPublicnumber() + " " + newUserNumber.getPrivatenumber() ) ;
						}
*/
						
						ProvisioningResponse response = ProvisioningRequest.activateInTX(newUserNumber.getMicsuserkey(), newUserNumber.getImsi(), 
								newUserNumber.getPublicnumber(), newUserNumber.getPrivatenumber(), newUserNumber.getNumbertype().intValue(), 
								newUserNumber.getOrderindex().intValue(), sipPassword, 
								newUserNumber.getSipuri(), micsuser.getCompanykey(), session);

						logger.debug("#editEmployee >> response(1)= " + response);
						if (response != null && response.getRequestId() > 0){
							if (response.getCode().equals(ProvisioningRequest.PROV_SUCCESS) 
									|| response.getCode().equals(ProvisioningRequest.PROV_ONGOING)){
								
								newUserNumber.setProv_req_id(new BigDecimal(response.getRequestId()));
								session.saveOrUpdate(newUserNumber);

							}else{
//								throw new MICSException("Failed to send activate MICSNUMBER, for userkey: " + newUserNumber.getMicsuserkey()+ " " + newUserNumber.getPublicnumber() + " " + newUserNumber.getPrivatenumber() ) ;
								throw new MICSException(response.getMessage());
							}
						}else{
							throw new MICSException("Failed to send activate provisioning request, for userkey: " + newUserNumber.getMicsuserkey()+ " " + newUserNumber.getPublicnumber() + " " + newUserNumber.getPrivatenumber() ) ;							
						}
					}
				}
			}
			
			//Check for updated number, only update to MICS database no request to provisioning
			for (Iterator iterNewNumber = newNumbers.iterator(); iterNewNumber.hasNext();) {
				AddNumberRequest currNumberRequest = (AddNumberRequest) iterNewNumber.next();
				for (Iterator iterOldNumber = oldNumber.iterator(); iterOldNumber.hasNext();) {
					Micsusernumber currOldNumber = (Micsusernumber) iterOldNumber.next();
					if (currNumberRequest.getPublicNumber().equals(currOldNumber.getPublicnumber())) {
						//Check if there is any update on private number or order index;
						if (currNumberRequest.getPrivateNumber().equals(currOldNumber.getPrivatenumber())) {
							//No Change in private number
							//Check if any change in order index;
							if (currNumberRequest.getOrderIndex() != currOldNumber.getOrderindex().intValue()) {
								//Change in order index
								logger.debug("Change in order index update number " + currOldNumber.getPublicnumber() +" order index from "+ currOldNumber.getOrderindex().intValue() + " to " + currNumberRequest.getOrderIndex() );
								currOldNumber.setOrderindex(new BigDecimal(currNumberRequest.getOrderIndex()));
								session.saveOrUpdate(currOldNumber);
							} else {
								//No Change just skipped this number
								logger.debug("No change for number " + currOldNumber.getPublicnumber() + " skip this number");
							}
						} else {
							//Private number is changed
							logger.debug("Change in private number update number " + currOldNumber.getPublicnumber() + " private number from " + currOldNumber.getPrivatenumber() + " to " + currNumberRequest.getPrivateNumber());
							//TODO if numberType == SIP need to send update request to OCP to update entry on LDAP and SBC
							if (currNumberRequest.getNumberType() == MICSConstant.MICS_NUMBER_TYPE_SIP){
								/*
								int provResult = ProvisioningRequest.modifySipNumber(micsuser.getMicsuserkey(), 
										currNumberRequest.getPublicNumber(), 
										currNumberRequest.getPrivateNumber(), 
										"", "", currNumberRequest.getOrderIndex(), session);
								
								if (provResult > 0){
									currOldNumber.setPrivatenumber(currNumberRequest.getPrivateNumber());
								}
							*/	
								ProvisioningResponse response = ProvisioningRequest.modifySipNumber(micsuser.getMicsuserkey(), 
										currNumberRequest.getPublicNumber(), 
										currNumberRequest.getPrivateNumber(), 
										"", "", currNumberRequest.getOrderIndex(), session);
								
								if (response != null && response.getRequestId() > 0){
									if (response.getCode().equals(ProvisioningRequest.PROV_SUCCESS) 
											|| response.getCode().equals(ProvisioningRequest.PROV_ONGOING)){

										currOldNumber.setPrivatenumber(currNumberRequest.getPrivateNumber());
									}else{
										throw new MICSException(response.getMessage());
									}
								}else{
									
								}
							
							}else{
								currOldNumber.setPrivatenumber(currNumberRequest.getPrivateNumber());
							}
							
							//Check if any change in order index;
							if (currNumberRequest.getOrderIndex() != currOldNumber.getOrderindex().intValue()) {
								//Change in order index
								logger.debug("Change in order index update number " + currOldNumber.getPublicnumber() +" order index from "+ currOldNumber.getOrderindex().intValue() + " to " + currNumberRequest.getOrderIndex() );
								currOldNumber.setOrderindex(new BigDecimal(currNumberRequest.getOrderIndex()));
								
							}
							session.saveOrUpdate(currOldNumber);
						}
					}
				}
			}
			session.saveOrUpdate(micsuser);
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			logger.error("Error editing employee= " + micsuser.getMicsuserkey(), e);
			tx.rollback();
			throw new MICSException("Failed to edit employee",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	public void changeSIPPasswordEmployee(Micsuser  micsuser,String sipPassword, List userNumbers) throws MICSException{
		logger.debug("changeSIPPasswordEmployee()");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			logger.debug("Change SIP Password for employee: " + micsuser);
			
			//Modify Password for All Number
			for (Iterator iterUserNumber = userNumbers.iterator(); iterUserNumber.hasNext();) {
				Micsusernumber currUserNumber = (Micsusernumber) iterUserNumber.next();
				//No need to save to DB, for security enhancement only do the pass thru 
//				currUserNumber.setPassword("");
//				session.saveOrUpdate(currUserNumber);
				//trigger provisioning for sip type only
				if (micsuser.getUsertype().intValue() != UserTypeEnum.VIRTUAL_ON_NET.ordinal()) {
					if (currUserNumber.getNumbertype().intValue() == MICSConstant.MICS_NUMBER_TYPE_SIP) {
						/*
						int provResult = ProvisioningRequest.modifyPinInTX(currUserNumber.getMicsuserkey(), currUserNumber.getPublicnumber(), sipPassword,session);
						if (provResult>0) {
							currUserNumber.setProv_req_id(new BigDecimal(provResult));
							session.saveOrUpdate(currUserNumber);
						} else {
							throw new MICSException("Failed to send modify SIP Password provisioning request, for userkey: " + currUserNumber.getMicsuserkey()+ " " + currUserNumber.getPublicnumber()  ) ;
						}
*/
						
						ProvisioningResponse response = ProvisioningRequest.modifyPinInTX(currUserNumber.getMicsuserkey(), 
								currUserNumber.getPublicnumber(), sipPassword,session);
						if (response != null && response.getRequestId() > 0){
							currUserNumber.setProv_req_id(new BigDecimal(response.getRequestId()));
							if (response.getCode().equals(ProvisioningRequest.PROV_SUCCESS) 
									|| response.getCode().equals(ProvisioningRequest.PROV_ONGOING)){
								session.saveOrUpdate(currUserNumber);
							}else{
//								throw new MICSException("Failed to modify SIP Password for userkey: " + currUserNumber.getMicsuserkey()+ " " + currUserNumber.getPublicnumber()  ) ;
								throw new MICSException(response.getMessage());
							}
							
						}else{
							throw new MICSException("Failed to send modify SIP Password provisioning request, for userkey: " + currUserNumber.getMicsuserkey()+ " " + currUserNumber.getPublicnumber()  ) ;
						}
						
					}
					
				}						
			}
//			session.saveOrUpdate(micsuser);
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to change employee SIP Password",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	public void editIncomingProfile(Callscreening callscreening,List incomingcallscreening) throws MICSException{
		logger.debug("editProfile()");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			logger.debug("Edit callscreening: " + callscreening);
			session.saveOrUpdate(callscreening);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			sdf.format(new Date());
			String awalKey = sdf.format(new Date());
			for (Iterator iterator = incomingcallscreening.iterator(); iterator.hasNext();) {
				AddCallScreening currRule = (AddCallScreening) iterator.next();
				logger.debug("Rule " + currRule.getRulekey() + " " + currRule.getRulename() + " " + currRule.getActionType());
				String action = currRule.getActionType();
				if (action == null) {
					action = "";
				}
				if (action.equalsIgnoreCase("ADD")) {
					String incomingrulekey = getAvailableKeyInTX(awalKey, "rulekey", "Incomingcallscreening", session);
					logger.debug("incomingrulekey: " + incomingrulekey);
					Incomingcallscreening currIncomingCallScr = new Incomingcallscreening();
					currIncomingCallScr.setRulekey(incomingrulekey);
					currIncomingCallScr.setRulename(currRule.getRulename());
					currIncomingCallScr.setCallscreeningkey(callscreening.getCallscreeningkey());
					currIncomingCallScr.setBearertype(currRule.getBearerType());
					currIncomingCallScr.setCallingpartynumber(currRule.getCallpartynumber());
					currIncomingCallScr.setCallingpartytype(currRule.getCallpartytype());
					currIncomingCallScr.setOrderindex(currRule.getOrderindex());
					currIncomingCallScr.setRejectionannoid(currRule.getRejectionannoid());
					currIncomingCallScr.setTimebandkey(currRule.getTimebandkey());
					currIncomingCallScr.setVlrprefix(currRule.getVlrprefix());
					if (currRule.isAllow()) {
						currIncomingCallScr.setAllow(new BigDecimal(1));
					} else {
						currIncomingCallScr.setAllow(new BigDecimal(0));
					}
					currIncomingCallScr.setUpdatetimestamp(callscreening.getUpdatetimestamp());
					currIncomingCallScr.setUpdateuser(callscreening.getUpdateuser());
					session.saveOrUpdate(currIncomingCallScr);
					logger.debug("New Incoming Screening Profile: " + currIncomingCallScr.getRulekey()+ " " +  currIncomingCallScr.getRulename());
				} else {
					if (action.equalsIgnoreCase("UPDATE")) {
						Incomingcallscreening currIncomingCallScr = new Incomingcallscreening();
						currIncomingCallScr.setRulekey(currRule.getRulekey());
						currIncomingCallScr.setRulename(currRule.getRulename());
						currIncomingCallScr.setCallscreeningkey(callscreening.getCallscreeningkey());
						currIncomingCallScr.setBearertype(currRule.getBearerType());
						currIncomingCallScr.setCallingpartynumber(currRule.getCallpartynumber());
						currIncomingCallScr.setCallingpartytype(currRule.getCallpartytype());
						currIncomingCallScr.setOrderindex(currRule.getOrderindex());
						currIncomingCallScr.setRejectionannoid(currRule.getRejectionannoid());
						currIncomingCallScr.setTimebandkey(currRule.getTimebandkey());
						currIncomingCallScr.setVlrprefix(currRule.getVlrprefix());
						if (currRule.isAllow()) {
							currIncomingCallScr.setAllow(new BigDecimal(1));
						} else {
							currIncomingCallScr.setAllow(new BigDecimal(0));
						}
						currIncomingCallScr.setUpdatetimestamp(callscreening.getUpdatetimestamp());
						currIncomingCallScr.setUpdateuser(callscreening.getUpdateuser());
						session.saveOrUpdate(currIncomingCallScr);
						logger.debug("Update Incoming Screening Profile: " + currIncomingCallScr.getRulekey()+ " " +  currIncomingCallScr.getRulename());
					} else {
						if (action.equalsIgnoreCase("DELETE")) {
							Incomingcallscreening currIncomingCallScr = new Incomingcallscreening();
							currIncomingCallScr.setRulekey(currRule.getRulekey());
							currIncomingCallScr.setRulename(currRule.getRulename());
							currIncomingCallScr.setCallscreeningkey(callscreening.getCallscreeningkey());
							currIncomingCallScr.setBearertype(currRule.getBearerType());
							currIncomingCallScr.setCallingpartynumber(currRule.getCallpartynumber());
							currIncomingCallScr.setCallingpartytype(currRule.getCallpartytype());
							currIncomingCallScr.setOrderindex(currRule.getOrderindex());
							currIncomingCallScr.setRejectionannoid(currRule.getRejectionannoid());
							currIncomingCallScr.setTimebandkey(currRule.getTimebandkey());
							currIncomingCallScr.setVlrprefix(currRule.getVlrprefix());
							if (currRule.isAllow()) {
								currIncomingCallScr.setAllow(new BigDecimal(1));
							} else {
								currIncomingCallScr.setAllow(new BigDecimal(0));
							}
							currIncomingCallScr.setUpdatetimestamp(callscreening.getUpdatetimestamp());
							currIncomingCallScr.setUpdateuser(callscreening.getUpdateuser());
							session.delete(currIncomingCallScr);
							logger.debug("Delete Incoming Screening Profile: " + currIncomingCallScr.getRulekey()+ " " +  currIncomingCallScr.getRulename());
						} else {
							logger.debug("No Change for Incoming Screening Profile: " + currRule.getRulekey() + " " + currRule.getRulename()); 
						}
					}
				}
			}
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to edit incoming profile",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	public void editOutgoingProfile(Callscreening callscreening,List outgoingcallscreening) throws MICSException{
		logger.debug("editProfile()");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			logger.debug("Edit callscreening: " + callscreening);
			session.saveOrUpdate(callscreening);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			sdf.format(new Date());
			String awalKey = sdf.format(new Date());
			for (Iterator iterator = outgoingcallscreening.iterator(); iterator.hasNext();) {
				AddCallScreening currRule = (AddCallScreening) iterator.next();
				logger.debug("Rule " + currRule.getRulekey() + " " + currRule.getRulename() + " " + currRule.getActionType());
				String action = currRule.getActionType();
				if (action == null) {
					action = "";
				}
				if (action.equalsIgnoreCase("ADD")) {
					String outgoingrulekey = getAvailableKeyInTX(awalKey, "rulekey", "Outgoingcallscreening", session);
					logger.debug("outgoingrulekey: " + outgoingrulekey);
					Outgoingcallscreening currOutgoingCallScr = new Outgoingcallscreening();
					currOutgoingCallScr.setRulekey(outgoingrulekey);
					currOutgoingCallScr.setRulename(currRule.getRulename());
					currOutgoingCallScr.setCallscreeningkey(callscreening.getCallscreeningkey());
					currOutgoingCallScr.setBearertype(currRule.getBearerType());
					currOutgoingCallScr.setCalledpartynumber(currRule.getCallpartynumber());
					currOutgoingCallScr.setCalledpartytype(currRule.getCallpartytype());
					currOutgoingCallScr.setOrderindex(currRule.getOrderindex());
					currOutgoingCallScr.setRejectionannoid(currRule.getRejectionannoid());
					currOutgoingCallScr.setTimebandkey(currRule.getTimebandkey());
					currOutgoingCallScr.setVlrprefix(currRule.getVlrprefix());
					currOutgoingCallScr.setGlobalciprefix(currRule.getGlobalciprefix());
					if (currRule.isAllow()) {
						currOutgoingCallScr.setAllow(new BigDecimal(1));
					} else {
						currOutgoingCallScr.setAllow(new BigDecimal(0));
					}
					currOutgoingCallScr.setUpdatetimestamp(callscreening.getUpdatetimestamp());
					currOutgoingCallScr.setUpdateuser(callscreening.getUpdateuser());
					session.saveOrUpdate(currOutgoingCallScr);
					logger.debug("New Outgoing Screening Profile: " + currOutgoingCallScr.getRulekey()+ " " +  currOutgoingCallScr.getRulename());
				} else {
					if (action.equalsIgnoreCase("UPDATE")) {
						Outgoingcallscreening currOutgoingCallScr = new Outgoingcallscreening();
						currOutgoingCallScr.setRulekey(currRule.getRulekey());
						currOutgoingCallScr.setRulename(currRule.getRulename());
						currOutgoingCallScr.setCallscreeningkey(callscreening.getCallscreeningkey());
						currOutgoingCallScr.setBearertype(currRule.getBearerType());
						currOutgoingCallScr.setCalledpartynumber(currRule.getCallpartynumber());
						currOutgoingCallScr.setCalledpartytype(currRule.getCallpartytype());
						currOutgoingCallScr.setOrderindex(currRule.getOrderindex());
						currOutgoingCallScr.setRejectionannoid(currRule.getRejectionannoid());
						currOutgoingCallScr.setTimebandkey(currRule.getTimebandkey());
						currOutgoingCallScr.setVlrprefix(currRule.getVlrprefix());
						currOutgoingCallScr.setGlobalciprefix(currRule.getGlobalciprefix());
						if (currRule.isAllow()) {
							currOutgoingCallScr.setAllow(new BigDecimal(1));
						} else {
							currOutgoingCallScr.setAllow(new BigDecimal(0));
						}
						currOutgoingCallScr.setUpdatetimestamp(callscreening.getUpdatetimestamp());
						currOutgoingCallScr.setUpdateuser(callscreening.getUpdateuser());
						session.saveOrUpdate(currOutgoingCallScr);
						logger.debug("Update Outgoing Screening Profile: " + currOutgoingCallScr.getRulekey()+ " " +  currOutgoingCallScr.getRulename());
					} else {
						if (action.equalsIgnoreCase("DELETE")) {
							Outgoingcallscreening currOutgoingCallScr = new Outgoingcallscreening();
							currOutgoingCallScr.setRulekey(currRule.getRulekey());
							currOutgoingCallScr.setRulename(currRule.getRulename());
							currOutgoingCallScr.setCallscreeningkey(callscreening.getCallscreeningkey());
							currOutgoingCallScr.setBearertype(currRule.getBearerType());
							currOutgoingCallScr.setCalledpartynumber(currRule.getCallpartynumber());
							currOutgoingCallScr.setCalledpartytype(currRule.getCallpartytype());
							currOutgoingCallScr.setOrderindex(currRule.getOrderindex());
							currOutgoingCallScr.setRejectionannoid(currRule.getRejectionannoid());
							currOutgoingCallScr.setTimebandkey(currRule.getTimebandkey());
							currOutgoingCallScr.setVlrprefix(currRule.getVlrprefix());
							currOutgoingCallScr.setGlobalciprefix(currRule.getGlobalciprefix());
							if (currRule.isAllow()) {
								currOutgoingCallScr.setAllow(new BigDecimal(1));
							} else {
								currOutgoingCallScr.setAllow(new BigDecimal(0));
							}
							currOutgoingCallScr.setUpdatetimestamp(callscreening.getUpdatetimestamp());
							currOutgoingCallScr.setUpdateuser(callscreening.getUpdateuser());
							session.delete(currOutgoingCallScr);
							logger.debug("Delete Outgoing Screening Profile: " + currOutgoingCallScr.getRulekey()+ " " +  currOutgoingCallScr.getRulename());
						} else {
							logger.debug("No Change for Outgoing Screening Profile: " + currRule.getRulekey() + " " + currRule.getRulename()); 
						}
					}
				}
			}
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to edit outgoing profile",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	public void editTimeband(Timeband timeband) throws MICSException{
		logger.debug("editTimeband()");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			logger.debug("Edit timeband: " + timeband);
			session.saveOrUpdate(timeband);
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to edit timeband",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	public void deleteCompany(Company company) throws MICSException{
		logger.debug("deleteCompany");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			logger.debug("Delete company: " + company);
			session.delete(company);
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to delete company",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	public void deleteCompanySwitchboard(Switchboard switchboard,List switchboardMenus, List<List<Switchboardsubmenu>> switchboardSubmenus) throws MICSException{
		logger.debug("deleteCompanySwitchboard");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			logger.debug("Delete switchboard: " + switchboard);
			session.delete(switchboard);
			
			for (Iterator iterator = switchboardMenus.iterator(); iterator.hasNext();) {
				Switchboardmenu sbMenu = (Switchboardmenu) iterator.next();
				session.delete(sbMenu);
			}
			
			for (Iterator iterator = switchboardSubmenus.iterator(); iterator.hasNext();) {
				List<Switchboardsubmenu> submenus = (List<Switchboardsubmenu>) iterator.next();
				if (submenus != null && !submenus.isEmpty()) {
					for (Iterator iterSubMenu = submenus.iterator(); iterSubMenu.hasNext();) {
						Switchboardsubmenu switchSubMenu = (Switchboardsubmenu) iterSubMenu.next();
						session.delete(switchSubMenu);
					}
				}
			}
			
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to delete switchboard",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	public void deleteTimeband(Timeband timeband) throws MICSException{
		logger.debug("deleteTimeband");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			logger.debug("Delete company: " + timeband);
			session.delete(timeband);
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to delete timeband",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	public void deleteCallScreening(Callscreening delCallScreening) throws MICSException{
		logger.debug("deleteCallScreening");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			logger.debug("Delete Callscreening: " + delCallScreening);
			session.delete(delCallScreening);
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to delete profile",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	public void deleteIncomingCallScreening(Callscreening delCallScreening,
			List delICS) throws MICSException{
		logger.debug("deleteIncomingCallScreening");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			
			for (Iterator iterator = delICS.iterator(); iterator.hasNext();) {
				Incomingcallscreening currICS = (Incomingcallscreening) iterator.next();
				session.delete(delICS);
				logger.debug("Delete Incoming CallScreening: " + currICS.getRulekey() + " " + currICS.getRulename());
			}
			logger.debug("Delete Callscreening: " + delCallScreening.getCallscreeningkey() + " " + delCallScreening.getCallscreeningname());
			session.delete(delCallScreening);
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to delete profile",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	public void deleteOutgoingCallScreening(Callscreening delCallScreening,
			List delOCS) throws MICSException{
		logger.debug("deleteOutgoingCallScreening");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			
			for (Iterator iterator = delOCS.iterator(); iterator.hasNext();) {
				Outgoingcallscreening currOCS = (Outgoingcallscreening) iterator.next();
				session.delete(currOCS);
				logger.debug("Delete Outgoing CallScreening: " + currOCS.getRulekey() + " " + currOCS.getRulename());
			}
			logger.debug("Delete Callscreening: " + delCallScreening.getCallscreeningkey() + " "+ delCallScreening.getCallscreeningname());
			session.delete(delCallScreening);
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to delete profile",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	public void deleteGroup(Micsgroup micsgroup) throws MICSException{
		logger.debug("deleteGroup");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			logger.debug("Delete group: " + micsgroup);
			session.delete(micsgroup);
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to delete group",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	public void deletePartnership(Partnership partnership) throws MICSException{
		logger.debug("deletePartnership");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			logger.debug("Delete partnership: " + partnership);
			session.delete(partnership);
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to delete partnership",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void deleteCommunity(Community community,List communityMember) throws MICSException{
		logger.debug("deleteCommunity");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			logger.debug("Delete community: " + community);
			//Delete community member
			if (communityMember != null && communityMember.size() > 0) {
				for (Iterator iterMember = communityMember.iterator(); iterMember
						.hasNext();) {
					Communitymember currMember = (Communitymember) iterMember.next();
					session.delete(currMember);
					logger.debug("member: " + currMember + " deleted");
				}
			} else {
				logger.debug("No community member");
			}
			session.delete(community);
			logger.debug("delete community success");
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to delete community",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void deleteEmployee(Micsuser employee, List employeeNumber) throws MICSException{
		logger.debug("deleteEmployee");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			logger.debug("Delete employee: " + employee);
			//Delete Employee Number first;
			for (Iterator iterNumber = employeeNumber.iterator(); iterNumber.hasNext();) {
				Micsusernumber currNumber = (Micsusernumber) iterNumber.next();
				session.delete(currNumber);
				//Check if user type is virtual no need to sent provisioning request
				if (employee.getUsertype().intValue() != UserTypeEnum.VIRTUAL_ON_NET.ordinal()){
					/*
					int provResult = ProvisioningRequest.deactivateInTX(currNumber.getMicsuserkey(), currNumber.getImsi(), currNumber.getPublicnumber(), currNumber.getPrivatenumber(), currNumber.getNumbertype().intValue(),"Y",session);
					if (provResult>0) {
						logger.debug("provResult: " + provResult);
					} else {
						throw new MICSException("Failed to send deactivate provisioning request, for userkey: " + currNumber.getMicsuserkey()+ " " + currNumber.getPublicnumber() + " " + currNumber.getPrivatenumber() ) ;
					}
					*/
					
					ProvisioningResponse response = ProvisioningRequest.deactivateInTX(currNumber.getMicsuserkey(), currNumber.getImsi(), currNumber.getPublicnumber(), 
							currNumber.getPrivatenumber(), currNumber.getNumbertype().intValue(),"Y",session);
					
					if (response != null && response.getRequestId() > 0){
						if (!response.getCode().equals(ProvisioningRequest.PROV_SUCCESS) && 
								!response.getCode().equals(ProvisioningRequest.PROV_ONGOING)){

							throw new MICSException(response.getMessage());
						}
						
					}else{
						throw new MICSException("Failed to send deactivate provisioning request, for userkey: " + currNumber.getMicsuserkey()+ " " + currNumber.getPublicnumber() + " " + currNumber.getPrivatenumber() ) ;						
					}
					
				}
				
//				logger.debug("number: " + currNumber + " deleted");
			}
			session.delete(employee);
			logger.debug("employe succesfully deleted");
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to delete employee",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	public int checkUserCountByCompany(String companykey) throws MICSException{
		logger.debug("checkUserCountByCompany");
		Session session = null;
		Long userCount = (long) 0;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			Query query = session.createQuery("select count(micsuser) from Micsuser as micsuser where micsuser.companykey = :companyKey");
			query.setString("companyKey", companykey);
			userCount =  (Long) query.uniqueResult();
			logger.debug("User count: " + userCount);
			logger.debug("Complete");
			return userCount.intValue();
		} catch (Exception e) {
			throw new MICSException("Failed to check user count by company",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	public int checkUsePublicNumber(String publicnumber) throws MICSException{
		logger.debug("checkUsePublicNumber");
		Session session = null;
		Long userCount = (long) 0;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			Query query = session.createQuery("select count(micsusernumber) from Micsusernumber as micsusernumber where micsusernumber.publicnumber = :publicnumber");
			query.setString("publicnumber", publicnumber);
			userCount =  (Long) query.uniqueResult();
			logger.debug("User public number count: " + userCount);
			logger.debug("Complete");
			return userCount.intValue();
		} catch (Exception e) {
			throw new MICSException("Failed to check user public number count by public number " + publicnumber,e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	public int checkUsePrivateNumber(String privatenumber, String companykey) throws MICSException{
		logger.debug("checkUsePrivateNumber");
		Session session = null;
		Long userCount = (long) 0;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			Query query = session.createQuery("select count(micsusernumber) from Micsusernumber as micsusernumber where micsusernumber.companykey = :companykey and micsusernumber.privatenumber = :privatenumber");
			query.setString("companykey", companykey);
			query.setString("privatenumber", privatenumber);
			userCount =  (Long) query.uniqueResult();
			logger.debug("User private number count: " + userCount);
			logger.debug("Complete");
			return userCount.intValue();
		} catch (Exception e) {
			throw new MICSException("Failed to check user private number count by private number " + privatenumber,e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}

	public int countOtherUserOfPrivateNumber(String privatenumber, String userkey, String companykey) throws MICSException{
		logger.debug("countOtherUserOfPrivateNumber(privateNo, userKey, companyKey)");
		Session session = null;
		Long userCount = (long) 0;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			Query query = session.createQuery("select count(micsusernumber) from Micsusernumber as micsusernumber "
					+ "where micsusernumber.companykey = :companykey "
					+ "and micsusernumber.micsuserkey != :userkey "
					+ "and micsusernumber.privatenumber = :privatenumber");
			query.setString("companykey", companykey);
			query.setString("userkey", userkey);
			query.setString("privatenumber", privatenumber);
			userCount =  (Long) query.uniqueResult();
			logger.debug("User private number count: " + userCount);
			logger.debug("Complete");
			return userCount.intValue();
		} catch (Exception e) {
			throw new MICSException("Failed to check user private number count by private number " + privatenumber,e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}

	public int checkHGPublicNumber(String publicnumber) throws MICSException{
		logger.debug("checkHGPublicNumber");
		Session session = null;
		Long userCount = (long) 0;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			Query query = session.createQuery("select count(huntinggroup) from Huntinggroup as huntinggroup where huntinggroup.publicnumber = :publicnumber");
			query.setString("publicnumber", publicnumber);
			userCount =  (Long) query.uniqueResult();
			logger.debug("Huntinggroup public number count: " + userCount);
			logger.debug("Complete");
			return userCount.intValue();
		} catch (Exception e) {
			throw new MICSException("Failed to check huntinggroup public number count by public number " + publicnumber,e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	public int checkHGPrivateNumber(String privatenumber,String companykey) throws MICSException{
		logger.debug("checkHGPrivateNumber");
		Session session = null;
		Long userCount = (long) 0;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			Query query = session.createQuery("select count(huntinggroup) from Huntinggroup as huntinggroup where huntinggroup.companykey = :companykey and huntinggroup.privatenumber = :privatenumber");
			query.setString("companykey", companykey);
			query.setString("privatenumber", privatenumber);
			userCount =  (Long) query.uniqueResult();
			logger.debug("Huntinggroup private number count: " + userCount);
			logger.debug("Complete");
			return userCount.intValue();
		} catch (Exception e) {
			throw new MICSException("Failed to check huntinggroup private number count by private number " + privatenumber,e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	public int checkHGCountByCompany(String companykey) throws MICSException{
		logger.debug("checkHGCountByCompany");
		Session session = null;
		Long hgCount = (long) 0;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			Query query = session.createQuery("select count(huntinggroup) from Huntinggroup as huntinggroup where huntinggroup.companykey= :companyKey");
			query.setString("companyKey", companykey);
			hgCount =  (Long) query.uniqueResult();
			logger.debug("HG count: " + hgCount);
			logger.debug("Complete");
			return hgCount.intValue();
		} catch (Exception e) {
			throw new MICSException("Failed to check HG count by company",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	public String getLastCompanyKey() throws MICSException{
		logger.debug("getLastCompanyKey");
		Session session = null;
		String result = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			Query query = session.createQuery("select max(company.companykey) from Company as company");
			result =  (String) query.uniqueResult();
			logger.debug("last Company Key: " + result);
			return result;
		} catch (Exception e) {
			throw new MICSException("Failed to check last company key",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	
	public void addMICSUser(Webuser newWebUser)throws MICSException{ 
		logger.debug("addMICSUser");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			logger.debug("Save New Web User: " + newWebUser);
			session.saveOrUpdate(newWebUser);
			logger.debug("New Web User ID: " + newWebUser.getWebuserkey() + " " + newWebUser.getWebusername());
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to add MICS User " + e.getMessage(),e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		
	}
	
	public void delMICSUser(Webuser currWebUser)throws MICSException{ 
		logger.debug("delMICSUser");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			logger.debug("begin transaction");
			tx = session.beginTransaction();
			logger.debug("Delete Web User: " + currWebUser);
			session.delete(currWebUser);
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to delete MICS User",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		
	}
	
	public void addScreeningProfile(Callscreening newCallScreening) throws MICSException{
		logger.debug("addScreeningProfile");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(newCallScreening);
			logger.debug("New Screening Profile: "+ newCallScreening.getCallscreeningkey());
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to add Screening Profile",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	public void createIncomingScreeningProfile(Callscreening newCallScreening,List rules) throws MICSException{
		logger.debug("createIncomingScreeningProfile");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(newCallScreening);
			logger.debug("New Screening Profile: "+ newCallScreening.getCallscreeningkey());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			sdf.format(new Date());
			String awalKey = sdf.format(new Date());
			for (Iterator iterator = rules.iterator(); iterator.hasNext();) {
				String incomingrulekey = getAvailableKeyInTX(awalKey, "rulekey", "Incomingcallscreening", session);
				logger.debug("incomingrulekey: " + incomingrulekey);
				AddCallScreening currRule = (AddCallScreening) iterator.next();
				Incomingcallscreening currIncomingCallScr = new Incomingcallscreening();
				currIncomingCallScr.setRulekey(incomingrulekey);
				currIncomingCallScr.setRulename(currRule.getRulename());
				currIncomingCallScr.setCallscreeningkey(newCallScreening.getCallscreeningkey());
				currIncomingCallScr.setBearertype(currRule.getBearerType());
				String currCallPartyNumber = currRule.getCallpartynumber();
				if (currCallPartyNumber== null) {
					currCallPartyNumber = "";
				}
				currIncomingCallScr.setCallingpartynumber(currCallPartyNumber);
				currIncomingCallScr.setCallingpartytype(currRule.getCallpartytype());
				currIncomingCallScr.setOrderindex(currRule.getOrderindex());
				currIncomingCallScr.setRejectionannoid(currRule.getRejectionannoid());
				currIncomingCallScr.setTimebandkey(currRule.getTimebandkey());
				String currVLRPrefix = currRule.getVlrprefix();
				if (currVLRPrefix == null) {
					currVLRPrefix = "";
				}
				currIncomingCallScr.setVlrprefix(currVLRPrefix);
				if (currRule.isAllow()) {
					currIncomingCallScr.setAllow(new BigDecimal(1));
				} else {
					currIncomingCallScr.setAllow(new BigDecimal(0));
				}
				currIncomingCallScr.setUpdatetimestamp(newCallScreening.getUpdatetimestamp());
				currIncomingCallScr.setUpdateuser(newCallScreening.getUpdateuser());
				session.saveOrUpdate(currIncomingCallScr);
				logger.debug("New Incoming Screening Profile: " + currIncomingCallScr.getRulekey());
			}
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to add Screening Profile",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	public void createOutgoingScreeningProfile(Callscreening newCallScreening,List rules) throws MICSException{
		logger.debug("createOutgoingScreeningProfile");
		Session session = null;
		Transaction tx = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(newCallScreening);
			logger.debug("New Screening Profile: "+ newCallScreening.getCallscreeningkey());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			sdf.format(new Date());
			String awalKey = sdf.format(new Date());
			for (Iterator iterator = rules.iterator(); iterator.hasNext();) {
				AddCallScreening currRule = (AddCallScreening) iterator.next();
				Outgoingcallscreening currOutgoingRule = new Outgoingcallscreening();
				String outgoingrulekey = getAvailableKeyInTX(awalKey, "rulekey", "Outgoingcallscreening", session);
				logger.debug("outgoingrulekey: " + outgoingrulekey);
				currOutgoingRule.setRulekey(outgoingrulekey);
				currOutgoingRule.setRulename(currRule.getRulename());
				currOutgoingRule.setBearertype(currRule.getBearerType());
				String currCallPartyNumber = currRule.getCallpartynumber();
				if (currCallPartyNumber == null) {
					currCallPartyNumber = "";
				}
				currOutgoingRule.setCalledpartynumber(currCallPartyNumber);
				currOutgoingRule.setCalledpartytype(currRule.getCallpartytype());
				currOutgoingRule.setCallscreeningkey(newCallScreening.getCallscreeningkey());
				String currGlobalCLIPrefix = currRule.getGlobalciprefix();
				if (currGlobalCLIPrefix == null) {
					currGlobalCLIPrefix = "";
				}
				currOutgoingRule.setGlobalciprefix(currGlobalCLIPrefix);
				currOutgoingRule.setOrderindex(currRule.getOrderindex());
				currOutgoingRule.setRejectionannoid(currRule.getRejectionannoid());
				currOutgoingRule.setTimebandkey(currRule.getTimebandkey());
				String currVLRPrefix = currRule.getVlrprefix();
				if (currVLRPrefix == null) {
					currVLRPrefix = "";
				}
				currOutgoingRule.setVlrprefix(currVLRPrefix);
				if (currRule.isAllow()) {
					currOutgoingRule.setAllow(new BigDecimal(1));
				} else {
					currOutgoingRule.setAllow(new BigDecimal(0));
				}
				currOutgoingRule.setUpdatetimestamp(newCallScreening.getUpdatetimestamp());
				currOutgoingRule.setUpdateuser(newCallScreening.getUpdateuser());
				session.saveOrUpdate(currOutgoingRule);
				logger.debug("New Outgoing Screening Profile: " + currOutgoingRule.getRulekey());
			}
			
			tx.commit();
			logger.debug("Complete");
		} catch (Exception e) {
			tx.rollback();
			throw new MICSException("Failed to add Screening Profile",e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
	}
	
	public String getAvailableKey(String awalKey,String key,String table)throws MICSException{ 
		logger.debug("getAvailableKey() " + awalKey + " " + key + " " + table );
		String result = null;
		Session session = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			Query query = session.createQuery("select "+key+" from "+table+" where "+key+" like '"+awalKey+"%' order by "+key+" desc");
			query.setMaxResults(1);
			String lastAvailableKey = null;
			try {
				lastAvailableKey = (String) query.uniqueResult();
			} catch (Exception e) {
				lastAvailableKey = query.uniqueResult() + "";
			}
					
			logger.debug("lastAvailableKey: " + lastAvailableKey);
			if (lastAvailableKey == null) {
				DecimalFormat df = new DecimalFormat("0000");
		        Number numberLastSeq = df.parse("0000");
		        double doubleNewSeq = numberLastSeq.doubleValue() + 1;
		        String newSeqNumber = df.format(doubleNewSeq);
		        logger.debug("New Sequence Number: " + newSeqNumber);
		        result = awalKey + newSeqNumber;
			} else {
				int idxAwal = lastAvailableKey.indexOf(awalKey);
		        int idxSequence = idxAwal + awalKey.length();
		        String sequenceNumber = lastAvailableKey.substring(idxSequence, lastAvailableKey.length());
		        logger.debug("old Sequence Number: " + sequenceNumber);
		        DecimalFormat df = new DecimalFormat("0000");
		        Number numberLastSeq = df.parse(sequenceNumber);
		        double doubleNewSeq = numberLastSeq.doubleValue() + 1;
		        String newSeqNumber = df.format(doubleNewSeq);
		        logger.debug("New Sequence Number: " + newSeqNumber);
		        result = awalKey + newSeqNumber;
			}
			
	        logger.debug("result: " + result);
		} catch (Exception e) {
			result = null;
			throw new MICSException("Failed to get available key for table: " +table,e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public String getMicsConfigValue(String configName)throws MICSException{
		logger.debug("getMicsConfigValue : " + configName);
		String result = null;
		Session session = null;
		try {
			session = MICSOraSessionFactory.currentSession();
			//logger.debug("session: "+ session );
			Query query = session.createQuery("select micsprop.value from Micsprop as micsprop where micsprop.name = :configName");
			query.setString("configName", configName);
			query.setMaxResults(1);
			result = (String) query.uniqueResult();
		} catch (Exception e) {
			result = null;
			throw new MICSException("Failed to get value for config name: " +configName,e);
		} finally {
			try {
				closeSession(session);
			} catch (Exception e) {
				logger.error("Failed to close hibernate session",e);
			}
		}
		return result;
	}
	
	public String getMicsConfigValueInTX(String configName,Session session)throws MICSException{
		logger.debug("getMicsConfigValue : " + configName);
		String result = null;
		try {
			logger.error("configName: "+ configName );
			Query query = session.createQuery("select micsprop.value from Micsprop as micsprop where micsprop.name = :configName");
			query.setString("configName", configName);
			query.setMaxResults(1);
			result = (String) query.uniqueResult();
		} catch (Exception e) {
			result = null;
			throw new MICSException("Failed to get value for config name: " +configName,e);
		} 
		return result;
	}
	public String getAvailableKeyInTX(String awalKey,String key,String table,Session session)throws MICSException{ 
		logger.debug("getAvailableKey() " + awalKey + " " + key + " " + table + " " + session);
		String result = null;
		try {
			//logger.debug("session: "+ session );
			Query query = session.createQuery("select "+key+" from "+table+" where "+key+" like '"+awalKey+"%' order by "+key+" desc");
			query.setMaxResults(1);
			String lastAvailableKey = null;
			try {
				lastAvailableKey = (String) query.uniqueResult();
			} catch (Exception e) {
				lastAvailableKey = query.uniqueResult() + "";
			}
					
			logger.debug("lastAvailableKey: " + lastAvailableKey);
			if (lastAvailableKey == null) {
				DecimalFormat df = new DecimalFormat("0000");
		        Number numberLastSeq = df.parse("0000");
		        double doubleNewSeq = numberLastSeq.doubleValue() + 1;
		        String newSeqNumber = df.format(doubleNewSeq);
		        logger.debug("New Sequence Number: " + newSeqNumber);
		        result = awalKey + newSeqNumber;
			} else {
				int idxAwal = lastAvailableKey.indexOf(awalKey);
		        int idxSequence = idxAwal + awalKey.length();
		        String sequenceNumber = lastAvailableKey.substring(idxSequence, lastAvailableKey.length());
		        logger.debug("old Sequence Number: " + sequenceNumber);
		        DecimalFormat df = new DecimalFormat("0000");
		        Number numberLastSeq = df.parse(sequenceNumber);
		        double doubleNewSeq = numberLastSeq.doubleValue() + 1;
		        String newSeqNumber = df.format(doubleNewSeq);
		        logger.debug("New Sequence Number: " + newSeqNumber);
		        result = awalKey + newSeqNumber;
			}
	        logger.debug("result: " + result);
		} catch (Exception e) {
			result = null;
			throw new MICSException("Failed to get available key for table: " +table,e);
		} 
		return result;
	}
	
	public void closeSession(Session session)throws HibernateException{
//		logger.debug("closeSession() " + session);
		if (session != null){
			session.close();
			session = null;
		}
	}
	private static final Logger logger = Logger.getLogger(MICSCommonSessionHandler.class);
	

}
