/**
 * DownloadCompanyAction.java
 * 
 * @author mulia
 * @version $Id: DownloadCompanyAction.java,v 1.1.4.3 2019/03/22 10:05:48 cvsuser Exp $
 */

package com.nsn.telkomsel.mics.action;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.nsn.telkomsel.mics.handler.MICSCommonSessionHandler;
import com.nsn.telkomsel.mics.orahbm.Company;
import com.nsn.telkomsel.mics.orahbm.Webuser;
import com.nsn.telkomsel.mics.util.MICSConfig;
import com.nsn.telkomsel.mics.util.MICSConstant;
import com.nsn.telkomsel.mics.util.Micslogger;
import com.opensymphony.xwork2.ActionSupport;

public class DownloadCompanyAction extends ActionSupport implements
		SessionAware {

	
	
	private static final long serialVersionUID = -3571568563366818026L;
	
	public String init(){
		logger.debug("init()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(downloadCompany, loginWebUser.getRolekey())) {
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
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
			addActionError("Failed to initialize download company, " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		}
		return result;
	}
	
	public String download(){
		logger.debug("download()");
		String result = MICSConstant.MICS_ERROR;
		ByteArrayInputStream bais = null;
	    ByteArrayOutputStream baos = null;
	    FileInputStream fis = null;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(downloadCompany, loginWebUser.getRolekey())) {
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
					//Get Companies
					List companyList = micsCommonSessionHandler.getCompanyList();
					if (companyList != null && companyList.size()>0) {
						//Write to CSV File
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
						sdf.format(new Date());
						String yearMonthDay = sdf.format(new Date());
						File companyCsv = writeToCSV(yearMonthDay,companyList);
						baos = new ByteArrayOutputStream();
				        ZipOutputStream zos = new ZipOutputStream(baos);
				        ZipEntry ze = new ZipEntry(companyCsv.getName());
				        zos.putNextEntry(ze);
				        byte[] buffer = new byte[8192];
				        int bytesRead = 0;
				        fis = new FileInputStream(companyCsv);
				        while ((bytesRead = fis.read(buffer)) != -1) {
				          zos.write(buffer, 0, bytesRead);
				        }
				        zos.flush();
				        zos.close();
				        bais = new ByteArrayInputStream(baos.toByteArray());

				        this.contentType="application/x-winzip";
				        StringBuffer zipFileName = new StringBuffer();
				        zipFileName.append("CompanyCsv_");
				        zipFileName.append(yearMonthDay);
				        zipFileName.append(".zip");
				        this.contentDisposition="attachment; filename=" +zipFileName.toString();
				        inputStream = bais;
				        addActionMessage("Company list downloaded");
				        result = MICSConstant.MICS_SUCCESS;
				        Micslogger.log("Company","Download",zipFileName.toString()+" downloaded", loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"SUCCESS");
					} else {
						 Micslogger.log("Company","Download","No company available for download", loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"FAILED");
						addActionError("Sorry no company available for download");
						result = MICSConstant.MICS_ERROR;
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
			addActionError("Failed to initialize download company, " + e.getMessage());
			result = MICSConstant.MICS_ERROR;
		} finally {
			if (fis != null) {
		        try {
		          fis.close();
		        }
		        catch (Exception ex) {
		          logger.warn("failed to close file input stream",ex);
		        }
		      }
		}
		return result;
	}
	
	private static int copy(InputStream input, OutputStream output)
	        throws IOException {
	    byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
	    int count = 0;
	    int n = 0;
	    while (-1 != (n = input.read(buffer))) {
	        output.write(buffer, 0, n);
	        count += n;
	    }
	    return count;
	  }
	
	 private File writeToCSV(String yearMonthDay,List companyList) throws Exception{
		File csvFile = null;
	    FileOutputStream fos = null;
	    FileWriter fw = null;
	    PrintWriter pw = null;
	    BufferedWriter bw = null;
	    try {
	    	
	    
			StringBuffer fileName = new StringBuffer();
			fileName.append("CompanyList_");
			fileName.append(yearMonthDay);
			fileName.append(".csv");
			csvFile = new File(fileName.toString());
			logger.debug("csvFile: " + csvFile.getAbsolutePath());
			fw = new FileWriter(csvFile);
			bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw);
			String micsDelimiter = MICSConfig.getMICSConfigValue(MICSConstant.MICS_SYS_DELIMITER);
			StringBuffer currHeader = new StringBuffer();
			currHeader.append("Company Key");
			currHeader.append(micsDelimiter);
			currHeader.append("Company Name");
			currHeader.append(micsDelimiter);
			currHeader.append("Contact Person");
			currHeader.append(micsDelimiter);
			currHeader.append("Email");
			currHeader.append(micsDelimiter);
			currHeader.append("Address");
			currHeader.append(micsDelimiter);
			currHeader.append("Charge String");
			currHeader.append(micsDelimiter);
			currHeader.append("Company CLI");
			currHeader.append(micsDelimiter);
			currHeader.append("Domain Name");
			currHeader.append(micsDelimiter);
			currHeader.append("Default Incoming Callscreening");
			currHeader.append(micsDelimiter);
			currHeader.append("Default Outgoing Callscreening");
			currHeader.append(micsDelimiter);
			currHeader.append("Forced Incoming Callscreening");
			currHeader.append(micsDelimiter);
			currHeader.append("Forced Outgoing Callscreening");
			currHeader.append(micsDelimiter);
			currHeader.append("Use Main CLI Prefix");
			currHeader.append(micsDelimiter);
			currHeader.append("CLI Option");
			currHeader.append(micsDelimiter);
			currHeader.append("Enable Forced OnNet");
			currHeader.append(micsDelimiter);
			currHeader.append("Enable Group Hunting");
			currHeader.append(micsDelimiter);
			currHeader.append("Enable Private Call");
			currHeader.append(micsDelimiter);
			currHeader.append("Enable VPN Charging for SMS");
			currHeader.append(micsDelimiter);
			currHeader.append("Locked");
			currHeader.append(micsDelimiter);
			currHeader.append("Update User");
			currHeader.append(micsDelimiter);
			currHeader.append("Update Timestamp");
			pw.println(currHeader.toString());
			for (Iterator iterCompany = companyList.iterator(); iterCompany
					.hasNext();) {
				Company currCompany = (Company) iterCompany.next();
				StringBuffer currLine = new StringBuffer();
				currLine.append(currCompany.getCompanykey());
				currLine.append(micsDelimiter);
				currLine.append(currCompany.getCompanyname());
				currLine.append(micsDelimiter);
				currLine.append(currCompany.getContactperson());
				currLine.append(micsDelimiter);
				currLine.append(currCompany.getEmail());
				currLine.append(micsDelimiter);
				currLine.append(currCompany.getAddress());
				currLine.append(micsDelimiter);
				currLine.append(currCompany.getChargestring());
				currLine.append(micsDelimiter);
				currLine.append(currCompany.getCompanycli());
				currLine.append(micsDelimiter);
				currLine.append(currCompany.getDomainname());
				currLine.append(micsDelimiter);
				currLine.append(currCompany.getDefaultincs());
				currLine.append(micsDelimiter);
				currLine.append(currCompany.getDefaultoutcs());
				currLine.append(micsDelimiter);
				currLine.append(currCompany.getForcedincs());
				currLine.append(micsDelimiter);
				currLine.append(currCompany.getForcedoutcs());
				currLine.append(micsDelimiter);
				currLine.append(currCompany.getUsemaincliprefix());
				currLine.append(micsDelimiter);
				currLine.append(currCompany.getClioption());
				currLine.append(micsDelimiter);
				currLine.append(currCompany.getEnableforcedonnet());
				currLine.append(micsDelimiter);
				currLine.append(currCompany.getEnablegrouphunting());
				currLine.append(micsDelimiter);
				currLine.append(currCompany.getEnableprivatecall());
				currLine.append(micsDelimiter);
				currLine.append(currCompany.getEnablevpnchargingforsms());
				currLine.append(micsDelimiter);
				currLine.append(currCompany.getLocked());
				currLine.append(micsDelimiter);
				currLine.append(currCompany.getUpdateuser());
				currLine.append(micsDelimiter);
				currLine.append(currCompany.getUpdatetimestamp());
				pw.println(currLine.toString());
			}
			pw.flush();
	    }
	    catch (Exception ex) {
	      csvFile = null;
	      throw new Exception("failed to write company list to csv",ex);
	    }
	    finally{
	      if (pw != null) {
	        try {
	          pw.close();
	        }
	        catch (Exception ex) {
	          logger.warn("failed to close print writer",ex);
	        }
	      }
	      if (bw != null) {
	        try {
	          bw.close();
	        }
	        catch (Exception ex) {
	          logger.warn("failed to close buffered writer",ex);
	        }
	      }
	      if (fw != null) {
	        try {
	          fw.close();
	        }
	        catch (Exception ex) {
	          logger.warn("failed to close file writer",ex);
	        }
	      }
	    }
	    return csvFile;
	}
	 
	
	
	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @param inputStream the inputStream to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the contentDisposition
	 */
	public String getContentDisposition() {
		return contentDisposition;
	}

	/**
	 * @param contentDisposition the contentDisposition to set
	 */
	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

	/**
	 * @return the downloadCompany
	 */
	public String getDownloadCompany() {
		return downloadCompany;
	}

	/**
	 * @param downloadCompany the downloadCompany to set
	 */
	public void setDownloadCompany(String downloadCompany) {
		this.downloadCompany = downloadCompany;
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
	
	private String contentType;
	private String contentDisposition;
	private InputStream inputStream;
	private String downloadCompany = "2017051601";
	private Map<String, Object> session;
	protected static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
	private static final Logger logger = Logger.getLogger(DownloadCompanyAction.class);
}
