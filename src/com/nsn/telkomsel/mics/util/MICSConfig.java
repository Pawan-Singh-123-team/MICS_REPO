package com.nsn.telkomsel.mics.util;

import org.hibernate.Session;

import com.nsn.telkomsel.mics.MICSException;
import com.nsn.telkomsel.mics.handler.MICSCommonSessionHandler;

public class MICSConfig {
	
	public static String getMICSConfigValue(String configName) throws MICSException {
		String result = null;
		MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
		result = micsCommonSessionHandler.getMicsConfigValue(configName);
		return result;
	}
	
	public static String getMICSConfigValueInTX(String configName,Session session) throws MICSException {
		String result = null;
		MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
		result = micsCommonSessionHandler.getMicsConfigValueInTX(configName, session);
		return result;
	}

}
