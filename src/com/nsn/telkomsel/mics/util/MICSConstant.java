package com.nsn.telkomsel.mics.util;

public interface MICSConstant {
	//Context Constant//
	
	//Request Constant//
	public final static String MICS_REQ_HEADER_USER = "MICS_USER";
	public final static String MICS_REQ_HEADER_LOGIN_USERKEY = "LOGIN_USERKEY";
	public final static String MICS_REQ_HEADER_LOGIN_USERNAME = "LOGIN_USERNAME";
	
	//Session Constant//

	public final static String MICS_SESSION_USER = "user";
	public final static String MICS_SESSION_ROLE = "role";
	public final static String MICS_SESSION_ROLELIST = "roleList";
	public final static String MICS_SESSION_COMPANYLIST = "companyList";
	public final static String MICS_SESSION_WEBUSERLIST = "webuserList";
	public final static String MICS_SESSION_PROFILELIST = "profileList";
	public final static String MICS_SESSION_LEVELLIST = "levelList";
	public final static String MICS_SESSION_TYPELIST = "typeList";
	public final static String MICS_SESSION_USERTYPES = "userTypes";
	public final static String MICS_SESSION_GROUPS = "groups";
	public final static String MICS_SESSION_NUMBERTYPES = "numberTypes";
	public final static String MICS_SESSION_LANGUAGES = "languages";
	public final static String MICS_SESSION_EMPLOYEELIST = "employeeList";
	public final static String MICS_SESSION_EMPLOYEERESULT = "searchMemberRes";
	public final static String MICS_SESSION_MEMBERREQ = "memberReq";
	public final static String MICS_SESSION_FORCEINCOMINGLIST = "forceIncomingList";
	public final static String MICS_SESSION_FORCEOUTGOINGLIST = "forceOutgoingList";
	public final static String MICS_SESSION_INCOMINGLIST = "incomingList";
	public final static String MICS_SESSION_OUTGOINGLIST = "outgoingList";
	public final static String MICS_SESSION_ANNOLIST = "annoList";
	public final static String MICS_SESSION_SWITCHLIST = "switchList";
	public final static String MICS_SESSION_SWITCHBOARD = "switchboard";
	public final static String MICS_SESSION_SWITCHMENU = "switchMenu";
	public final static String MICS_SESSION_SWITCHSUBMENU = "switchSubmenu";
	public final static String MICS_SESSION_TIMEWINDOWLIST = "timewindowList";
	public final static String MICS_SESSION_HUNTINGOPTIONLIST = "huntingoptionList";
	public final static String MICS_SESSION_HUNTINGSCHEDULEREQ = "huntingscheduleReq";
	public final static String MICS_SESSION_DELHUNTINGSCHEDULEREQ = "delHuntingscheduleReq";
	public final static String MICS_SESSION_NOTIFICATION = "notification";
	public final static String MICS_SESSION_MENUS = "menus";
	public final static String MICS_DAY_SELECTION = "daySelections";
	public final static String MICS_SESSION_CURRCOMPANY = "currCompany";
	public final static String MICS_SESSION_TIMEBANDS = "timebands";
	public final static String MICS_SESSION_RULES = "rules";
	public final static String MICS_SESSION_DEL_RULES = "delRules";
	//Error//
	public final static String MICS_ERROR_NOT_LOGGEDIN = "notLoggedIn";
	public final static String MICS_ERROR_NOT_AUTH = "notAuth";
	public final static String MICS_ERROR_WRONG_USER_PASSWORD = "Wrong User Name or Wrong Password";
	public final static String MICS_SUCCESS = "success";
	public final static String MICS_SSO = "sso";
	public final static String MICS_ERROR = "error";
	public final static String MICS_INPUT = "input";
	
	//MICS ROLE//
	public final static String MICS_ROLE_ADMIN_TELKOMSEL = "2017050001";
	public final static String MICS_ROLE_ADMIN_COMPANY = "2017050002";
	public final static String MICS_ROLE_ADMIN_SYS = "2017050003";
	public final static String MICS_ROLE_OPERATION = "2017050004";
	public final static String MICS_ROLE_ADMIN = "2017050005";
	public final static String MICS_ROLE_HELPDESK = "2017050006";
	
	//MICS PARAMETER//
	public final static int MICS_SCREENING_TYPE_OUTGOING = 1;
	public final static int MICS_SCREENING_TYPE_INCOMING = 2;
	public final static int MICS_SCREENING_LEVEL_COMPANY = 1;
	public final static int MICS_SCREENING_LEVEL_GROUP = 2;
	public final static int MICS_SCREENING_LEVEL_USER = 3;
	public final static int MICS_SCREENING_CALLED_PARTYTYPE_ =1;
	public final static int MICS_NUMBER_TYPE_MOBILE = 0;
	public final static int MICS_NUMBER_TYPE_SIP = 1;
	

	//MICS NETWORK//
	public final static Integer MICS_NETWORK_PRISMA = 1;
	public final static Integer MICS_NETWORK_INVPN = 2;
	public final static Integer MICS_NETWORK_LDAP = 3;
	public final static Integer MICS_NETWORK_SBC = 4;
	
	//MICS CONFIG
	public final static String MICS_SYS_PROP_COMPANY = "mics.system.prop.company";
	public final static String MICS_SYS_PROP_COPYRIGHT = "mics.system.prop.copyright";
	public final static String MICS_SYS_PROP_TABLE_PAGESIZE = "mics.system.prop.table.pagesize";
	public final static String MICS_SYS_PROP_TABLE_EXPORT = "mics.system.prop.table.export";
	public final static String MICS_SYS_PROP_CONN_SOTIMEOUT = "mics.system.prop.conn.sockettimeout";
	public final static String MICS_SYS_PROP_CONN_CONNECTIONTIMEOUT = "mics.system.prop.conn.connectiontimeout";
	public final static String MICS_INT_PROV_NUMBER_ACTIVATE = "mics.interface.provisioning.oneclick.usernumber.activate";
	public final static String MICS_INT_PROV_NUMBER_DEACTIVATE = "mics.interface.provisioning.oneclick.usernumber.deactivate";
	public final static String MICS_INT_PROV_NUMBER_MODIFY_PIN = "mics.interface.provisioning.oneclick.usernumber.modifyPin";
	public final static String MICS_INT_PROV_NUMBER_UPDATE = "mics.interface.provisioning.oneclick.usernumber.update";
	public final static String MICS_SYS_DELIMITER = "mics.system.delimiter";
	

}
