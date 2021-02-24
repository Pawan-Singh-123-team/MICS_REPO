/**
 * 
 */
package com.nsn.telkomsel.mics.util;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 * ProvisioningRequest.java
 * 
 * @author mulia
 * @version $Id: ProvisioningRequest.java,v 1.1.4.3 2019/03/22 10:06:38 cvsuser Exp $
 */
public class ProvisioningRequest {
	
	public static final String PROV_SUCCESS = "0000";
	public static final String PROV_ONGOING = "1000";
	
	public static int activate(String userId,String imsi,String publicNumber,String privateNumber,int numberType,
			int orderIndex,String password,String sipuri){
		int result = -1;
		logger.debug("activate: " + userId + "/" + imsi + "/" + publicNumber + "/"+privateNumber+"/" +numberType+"/"+ orderIndex+"/"+sipuri);
		try {
			HttpClient simpleHttpClient = new HttpClient();
			
			simpleHttpClient.getParams().setParameter(
					  HttpMethodParams.RETRY_HANDLER, 
					  new DefaultHttpMethodRetryHandler(0, false)); // disable the default retry
			//Set Socket Timeout 
			simpleHttpClient.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, new Integer(MICSConfig.getMICSConfigValue(MICSConstant.MICS_SYS_PROP_CONN_SOTIMEOUT)));
			simpleHttpClient.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, new Integer(MICSConfig.getMICSConfigValue(MICSConstant.MICS_SYS_PROP_CONN_CONNECTIONTIMEOUT)));
			
			String provUrlActivate = MICSConfig.getMICSConfigValue(MICSConstant.MICS_INT_PROV_NUMBER_ACTIVATE);
			
			HttpMethod postMethod = new PostMethod(provUrlActivate);
			/** Modified by TN on 20130928 **/
			NameValuePair[] nvp = new NameValuePair[]{
				new NameValuePair("userId", userId),
				new NameValuePair("imsi", imsi),
				new NameValuePair("publicNumber", publicNumber),
				new NameValuePair("privateNumber", privateNumber),
				new NameValuePair("numberType", ""+ numberType),
				new NameValuePair("orderIndex", "" + orderIndex),
				new NameValuePair("password", password),
				new NameValuePair("sipURI", sipuri)
			};
			postMethod.setQueryString(nvp);
			
			int httpResponceCode = simpleHttpClient.executeMethod(postMethod);
			logger.debug("httpResponceCode: " + httpResponceCode);
			logger.info("URL: " + postMethod.getURI().toString());
			logger.debug("Response body "+ postMethod.getResponseBodyAsString());
			if (httpResponceCode == HttpStatus.SC_OK) {
				//modified by TN :::ignore status_code from OneClick at this point
				/*
				logger.debug("Response Code 200 means success");
				String strResponseBody = postMethod.getResponseBodyAsString();
				logger.debug("strResponseBody: " + strResponseBody);
				int intProvStatus = decodeStatusFromResponse(strResponseBody);
				switch (intProvStatus) {
				case 1:
					logger.debug("Provisioning in process");
					result = true;
					break;
				case 2:
					logger.debug("Provisioning success");
					result = true;
					break;
				default:
					logger.debug("Provisioning failed");
					result = false;
					break;
				}
				*/
				logger.debug("Provisioning success");
				int provRequestId = getRequestIdFromResponse(postMethod.getResponseBodyAsString());
				result = provRequestId;
				
			} else {
				logger.debug("Response Code other than 200 means failed");
				result = -1;
			}

		} catch (Exception e) {
			result = -1;
			logger.error("Failed to send activation for "+ userId + "/" + publicNumber + "/"+privateNumber+"/" +numberType+"/"+ orderIndex ,e);
		}
		return result;
	}
	
//	public static int activateInTX(String userId,String imsi, String publicNumber, String privateNumber, int numberType,
//			int orderIndex, String password, String sipuri, String companyId, Session session){
	public static ProvisioningResponse activateInTX(String userId,String imsi, String publicNumber, String privateNumber, int numberType,
			int orderIndex, String password, String sipuri, String companyId, Session session){
		logger.error("activate: " + userId + "/" + imsi + "/" + publicNumber + "/"+privateNumber+"/" +numberType+"/"+ orderIndex+ "/" + sipuri);
		try {
			HttpClient simpleHttpClient = new HttpClient();
			
			simpleHttpClient.getParams().setParameter(
					  HttpMethodParams.RETRY_HANDLER, 
					  new DefaultHttpMethodRetryHandler(0, false)); // disable the default retry
			//Set Socket Timeout 
			simpleHttpClient.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, new Integer(MICSConfig.getMICSConfigValueInTX(MICSConstant.MICS_SYS_PROP_CONN_SOTIMEOUT, session)));
			simpleHttpClient.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, new Integer(MICSConfig.getMICSConfigValueInTX(MICSConstant.MICS_SYS_PROP_CONN_CONNECTIONTIMEOUT, session)));
    		String provUrlActivate = MICSConfig.getMICSConfigValueInTX(MICSConstant.MICS_INT_PROV_NUMBER_ACTIVATE,session);
			HttpMethod postMethod = new PostMethod(provUrlActivate);
			
			/** Modified by TN on 20130928 **/
			NameValuePair[] nvp = new NameValuePair[]{
				new NameValuePair("userId", userId),
				new NameValuePair("imsi", imsi),
				new NameValuePair("publicNumber", publicNumber),
				new NameValuePair("privateNumber", privateNumber),
				new NameValuePair("numberType", ""+ numberType),
				new NameValuePair("orderIndex", "" + orderIndex),
				new NameValuePair("password", password),
				new NameValuePair("sipURI", sipuri),
				new NameValuePair("companyId", companyId)
			};
			postMethod.setQueryString(nvp);
			
			int httpResponceCode = simpleHttpClient.executeMethod(postMethod);
			logger.error("response code :"+httpResponceCode);
			if (httpResponceCode == HttpStatus.SC_OK) {
				String responseBody = postMethod.getResponseBodyAsString();
				logger.error("response responseBody :"+responseBody);				
				return readProvisioningResponse(responseBody);
			} 
		} catch (Exception e) {
			logger.error("Failed to send activation for "+ userId + "/" + publicNumber + "/"+privateNumber+"/" +numberType+"/"+ orderIndex ,e);
		}
		return null;
	}
	
	public static int deactivate(String userId,String imsi,String publicNumber,String privateNumber,int numberType){
		int result = -1;
		logger.debug("deactivate: " + userId + "/"+imsi+"/" + publicNumber + "/"+privateNumber+"/" +numberType);
//		Format to send deactivate to oneclick provisioning
//		http://10.2.92.196:9090/oneclick/prov/usernumber/deactivate/12345/510109876543210/61811111111/778899/0
		try {
			HttpClient simpleHttpClient = new HttpClient();
			
			simpleHttpClient.getParams().setParameter(
					  HttpMethodParams.RETRY_HANDLER, 
					  new DefaultHttpMethodRetryHandler(0, false)); // disable the default retry
			//Set Socket Timeout 
			simpleHttpClient.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, new Integer(MICSConfig.getMICSConfigValue(MICSConstant.MICS_SYS_PROP_CONN_SOTIMEOUT)));
			simpleHttpClient.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, new Integer(MICSConfig.getMICSConfigValue(MICSConstant.MICS_SYS_PROP_CONN_CONNECTIONTIMEOUT)));
//			String provUrlDeactivate = MICSConfig.getMICSConfigValue(MICSConstant.MICS_INT_PROV_NUMBER_DEACTIVATE)+userId+"/"+imsi+"/"+publicNumber+"/"+privateNumber+"/"+numberType;
			String provUrlDeactivate = MICSConfig.getMICSConfigValue(MICSConstant.MICS_INT_PROV_NUMBER_DEACTIVATE);
			
			HttpMethod postMethod = new PostMethod(provUrlDeactivate);
			/** Modified by TN on 20130928 **/
			NameValuePair[] nvp = new NameValuePair[]{
				new NameValuePair("userId", userId),
				new NameValuePair("imsi", imsi),
				new NameValuePair("publicNumber", publicNumber),
				new NameValuePair("privateNumber", privateNumber),
				new NameValuePair("numberType", ""+ numberType),
				new NameValuePair("deleteUser", "Y")
			};
			postMethod.setQueryString(nvp);
			
			int httpResponceCode = simpleHttpClient.executeMethod(postMethod);
			logger.debug("httpResponceCode: " + httpResponceCode);
			logger.info("URL: " + postMethod.getURI().toString());
			logger.debug("Response body "+ postMethod.getResponseBodyAsString());
			if (httpResponceCode == HttpStatus.SC_OK) {
				/*
				logger.debug("Response Code 200 means success");
				String strResponseBody = postMethod.getResponseBodyAsString();
				logger.debug("strResponseBody: " + strResponseBody);
				int intProvStatus = decodeStatusFromResponse(strResponseBody);
				switch (intProvStatus) {
				case 1:
					logger.debug("Provisioning in process");
					result = true;
					break;
				case 2:
					logger.debug("Provisioning success");
					result = true;
					break;
				default:
					logger.debug("Provisioning failed");
					result = false;
					break;
				}
				*/
				logger.debug("Provisioning success");
				int provRequestId = getRequestIdFromResponse(postMethod.getResponseBodyAsString());
				result = provRequestId;
			} else {
				logger.debug("Response Code other than 200 means failed");
				result = -1;
			}

		} catch (Exception e) {
			result = -1;
			logger.error("Failed to send deactivation for "+ userId + "/" + publicNumber + "/"+privateNumber+"/" +numberType ,e);
		}
		return result;
	}
	
	public static ProvisioningResponse deactivateInTX(String userId,
			String imsi, String publicNumber, String privateNumber,
			int numberType, String delUser, Session session) {
		
		logger.debug("deactivate: " + userId + "/"+imsi+"/" + publicNumber + "/"+privateNumber+"/" +numberType);
//		Format to send deactivate to oneclick provisioning
		try {
			HttpClient simpleHttpClient = new HttpClient();
			
			simpleHttpClient.getParams().setParameter(
					  HttpMethodParams.RETRY_HANDLER, 
					  new DefaultHttpMethodRetryHandler(0, false)); // disable the default retry
			//Set Socket Timeout 
			simpleHttpClient.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, new Integer(MICSConfig.getMICSConfigValueInTX(MICSConstant.MICS_SYS_PROP_CONN_SOTIMEOUT,session)));
			simpleHttpClient.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, new Integer(MICSConfig.getMICSConfigValueInTX(MICSConstant.MICS_SYS_PROP_CONN_CONNECTIONTIMEOUT,session)));
			String provUrlDeactivate = MICSConfig.getMICSConfigValueInTX(MICSConstant.MICS_INT_PROV_NUMBER_DEACTIVATE,session);
			HttpMethod postMethod = new PostMethod(provUrlDeactivate);
			
			/** Modified by TN on 20130928 **/
			NameValuePair[] nvp = new NameValuePair[]{
				new NameValuePair("userId", userId),
				new NameValuePair("imsi", imsi),
				new NameValuePair("publicNumber", publicNumber),
				new NameValuePair("privateNumber", privateNumber),
				new NameValuePair("numberType", ""+ numberType),
				new NameValuePair("deleteUser", delUser)
			};
			postMethod.setQueryString(nvp);

			int httpResponceCode = simpleHttpClient.executeMethod(postMethod);
			if (httpResponceCode == HttpStatus.SC_OK) {
				String responseBody = postMethod.getResponseBodyAsString();
				return readProvisioningResponse(responseBody);
			} 

		} catch (Exception e) {
			logger.error("Failed to send deactivation for "+ userId + "/" + publicNumber + "/"+privateNumber+"/" +numberType ,e);
		}
		return null;
	}
	
	public static ProvisioningResponse modifyPinInTX(String userId,String publicNumber,String password,Session session){
		logger.debug("modifyPinInTX: " + userId );
		try {
			HttpClient simpleHttpClient = new HttpClient();
			
			simpleHttpClient.getParams().setParameter(
					  HttpMethodParams.RETRY_HANDLER, 
					  new DefaultHttpMethodRetryHandler(0, false)); // disable the default retry
			//Set Socket Timeout 
			simpleHttpClient.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, new Integer(MICSConfig.getMICSConfigValueInTX(MICSConstant.MICS_SYS_PROP_CONN_SOTIMEOUT, session)));
			simpleHttpClient.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, new Integer(MICSConfig.getMICSConfigValueInTX(MICSConstant.MICS_SYS_PROP_CONN_CONNECTIONTIMEOUT, session)));
	
			String provUrlChangePin = MICSConfig.getMICSConfigValueInTX(MICSConstant.MICS_INT_PROV_NUMBER_MODIFY_PIN,session);
			HttpMethod postMethod = new PostMethod(provUrlChangePin);
			/** Modified by TN on 20130928 **/
			NameValuePair[] nvp = new NameValuePair[]{
				new NameValuePair("userId", userId),
				new NameValuePair("publicNumber", publicNumber),
				new NameValuePair("pin", password)
			};
			postMethod.setQueryString(nvp);
			
			int httpResponceCode = simpleHttpClient.executeMethod(postMethod);
			logger.debug("#modifyPinInTX >> Response body "+ postMethod.getResponseBodyAsString()); 
			if (httpResponceCode == HttpStatus.SC_OK) {
				String responseBody = postMethod.getResponseBodyAsString();
				logger.debug("#modifyPinInTX >> got provisioning response from OCP= " + responseBody);
				return readProvisioningResponse(responseBody);
			} 

		} catch (Exception e) {
			logger.error("Failed to send modify pin for "+ userId + "/" + publicNumber,e);
		}
		return null;
	}

	public static ProvisioningResponse modifySipNumber(String userId, String publicNumber, String privateNumber, 
			String password, String sipURI, Integer orderIdx, Session session){

//		add new record to MICSPROP first before deploying this update
/*		
		insert into micsprop(name, value, updatetimestamp) values('mics.interface.provisioning.oneclick.usernumber.update', 
			'http://localhost:9090/oneclick/mics/prov/usernumber/update', sysdate);	
*/		
		try {
			HttpClient simpleHttpClient = new HttpClient();
			
			simpleHttpClient.getParams().setParameter(
					  HttpMethodParams.RETRY_HANDLER, 
					  new DefaultHttpMethodRetryHandler(0, false)); // disable the default retry
			//Set Socket Timeout 
			simpleHttpClient.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, new Integer(MICSConfig.getMICSConfigValueInTX(MICSConstant.MICS_SYS_PROP_CONN_SOTIMEOUT, session)));
			simpleHttpClient.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, new Integer(MICSConfig.getMICSConfigValueInTX(MICSConstant.MICS_SYS_PROP_CONN_CONNECTIONTIMEOUT, session)));
	
			String provUrlChangePin = MICSConfig.getMICSConfigValueInTX(MICSConstant.MICS_INT_PROV_NUMBER_UPDATE,session);
			HttpMethod postMethod = new PostMethod(provUrlChangePin);
			NameValuePair[] nvp = new NameValuePair[]{
				new NameValuePair("userId", userId),
				new NameValuePair("publicNumber", publicNumber),
				new NameValuePair("privateNumber", privateNumber),
				new NameValuePair("numberType", ""+MICSConstant.MICS_NUMBER_TYPE_SIP),
				new NameValuePair("orderIndex", ""+orderIdx),
				new NameValuePair("password", password),
				new NameValuePair("sipURI", sipURI)
			};
			postMethod.setQueryString(nvp);
			
			int httpResponceCode = simpleHttpClient.executeMethod(postMethod);
			logger.debug("#modifySipNumber >> Response body "+ postMethod.getResponseBodyAsString());
			if (httpResponceCode == HttpStatus.SC_OK) {
				String responseBody = postMethod.getResponseBodyAsString();
				logger.debug("#modifySipNumber >> got provisioning response from OCP= " + responseBody);
				return readProvisioningResponse(responseBody);
			} 
		} catch (Exception e) {
			logger.error("Failed to send modify pin for "+ userId + "/" + publicNumber,e);
		}
		return null;
	}


	/**
	 * Parse (initial) provisioning response from OCP in the following format REQUEST_ID:RESPONSE_CODE
	 * @param responseBody HTTP response body
	 * @return the ProvisioningResponse object or null if responseBody could not be read
	 */
	public static ProvisioningResponse readProvisioningResponse(String responseBody){
		int requestId = -1;
		String code = "";
		String message = "";
		if (responseBody != null && !responseBody.equals("")){
			int idx = responseBody.trim().indexOf(":");
			responseBody=responseBody.trim();
			logger.error("idx  : "+idx);
			if (idx > 0){
				try {
					requestId = Integer.parseInt(responseBody.substring(0, idx)); 
					int idx2 = responseBody.lastIndexOf(":");
					if (idx2 > 0) {
						code = responseBody.substring(idx+1, idx2);
						message = responseBody.substring(idx2+1);					
					}else{
						code = responseBody.substring(idx+1);
					}
				} catch (Exception e) {
					logger.error("Error parsing provisioning response from OCP= " + responseBody, e);
				}
			}else{
				//perhaps no response code is given
				try {
					requestId = Integer.parseInt(responseBody);
				} catch (Exception e) {
				}
			}
		}
		ProvisioningResponse response = new ProvisioningResponse(requestId, code);
		response.setMessage(message);
		logger.debug("#readProvisioningResponse >> result= " + response);
		return response;
	}
	
	
	/**
	 * Parse provisioning_request_id from OneClick provisioning and use it to check the provisioning request status
	 * 
	 * @param responseBody
	 * @return
	 */
	private static int getRequestIdFromResponse(String responseBody){
		if (responseBody == null) responseBody = "";
		if (responseBody.equals("")) return -1;
		
		try {
			return Integer.parseInt(responseBody.trim());
		} catch (Exception e) {
			logger.error("Could not parse provisioning request id from OneClick response!", e);
			return -1;
		}
		/*
		if (responseBody == null) responseBody = "";
		if (responseBody.equals("")) return -1;
		
		try {
			String ocurl = "{";
			String ccurl = "}";
			responseBody = responseBody.trim();
			int idxo = responseBody.indexOf(ocurl);
			int idxc = responseBody.indexOf(ccurl);
			if (idxo >= 0 && idxc >0){
				responseBody = responseBody.substring(idxo+1, idxc);
			}
			return Integer.parseInt(responseBody.trim());
		} catch (Exception e) {
			logger.error("Could not parse provisioning request id from OneClick response!", e);
			return -1;
		}
		*/
	}
	
	private static int decodeStatusFromResponse(String responseBody){
		int result = -1;
		try {
			logger.debug("decodeStatusFromResponse " + responseBody);
			String tagStatus = "\"status\":";
			int indexStatus = responseBody.indexOf(tagStatus);
			if (indexStatus == -1) {
				result = -1;
				logger.error("Unexpected response from provisioning");
			} else {
				String strStatus = responseBody.substring(indexStatus+tagStatus.length());
				result = Integer.parseInt(strStatus.substring(0, strStatus.indexOf(",")));
				logger.debug("result: " + result);
			}
			
		} catch (Exception e) {
			logger.error("Failed to decode status from response " + responseBody, e);
			result = -1;
		} 
		return result;
	}

	private static final Logger logger = Logger.getLogger(ProvisioningRequest.class);
	
}
