package com.nsn.telkomsel.mics.util;

import java.sql.Timestamp;

import org.apache.log4j.Logger;

import com.nsn.telkomsel.mics.handler.MICSCommonSessionHandler;
import com.nsn.telkomsel.mics.orahbm.Micslog;

public class Micslogger {
	
	public static void log(String activity,String module,String desc,String company,String user,String status){
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			Micslog newMisclog = new Micslog(new Timestamp(System.currentTimeMillis()),activity,module,desc,user,company,status);
			micsCommonSessionHandler.createMisclog(newMisclog);
		} catch (Exception e) {
			logger.warn("Failed to do log");
		} 
	}

	private static final Logger logger = Logger.getLogger(Micslogger.class);
}
