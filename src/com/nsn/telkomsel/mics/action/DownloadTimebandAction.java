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

import javax.servlet.http.HttpServletResponse;

/**
 * DownloadCompanyAction.java
 * 
 * @author mulia
 * @version $Id: DownloadTimebandAction.java,v 1.1.4.3 2019/03/22 10:05:51 cvsuser Exp $
 */

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.nsn.telkomsel.mics.MICSException;
import com.nsn.telkomsel.mics.handler.MICSCommonSessionHandler;
import com.nsn.telkomsel.mics.orahbm.Callscreening;
import com.nsn.telkomsel.mics.orahbm.Community;
import com.nsn.telkomsel.mics.orahbm.Communitymember;
import com.nsn.telkomsel.mics.orahbm.Company;
import com.nsn.telkomsel.mics.orahbm.Micsuser;
import com.nsn.telkomsel.mics.orahbm.Micsusernumber;
import com.nsn.telkomsel.mics.orahbm.Partnership;
import com.nsn.telkomsel.mics.orahbm.Timeband;
import com.nsn.telkomsel.mics.orahbm.Webuser;
import com.nsn.telkomsel.mics.util.MICSConfig;
import com.nsn.telkomsel.mics.util.MICSConstant;
import com.nsn.telkomsel.mics.util.Micslogger;
import com.opensymphony.xwork2.ActionSupport;

public class DownloadTimebandAction extends ActionSupport implements
		SessionAware {

	private static final long serialVersionUID = -8581508953286082001L;
	public String init(){
		logger.debug("init()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(downloadTimeband, loginWebUser.getRolekey())) {
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
			addActionError("Failed to initialize download timeband, " + e.getMessage());
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
				if (micsCommonSessionHandler.isMenuAuthorized(downloadTimeband, loginWebUser.getRolekey())) {
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
					//Get Profile
					List timebandList = micsCommonSessionHandler.getTimebands();
					if (timebandList != null && timebandList.size() > 0) {
						//Write to CSV File
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
						sdf.format(new Date());
						String yearMonthDay = sdf.format(new Date());
						File timebandCsv = writeToCSV(yearMonthDay,timebandList);
						baos = new ByteArrayOutputStream();
				        ZipOutputStream zos = new ZipOutputStream(baos);
				        ZipEntry ze = new ZipEntry(timebandCsv.getName());
				        zos.putNextEntry(ze);
				        byte[] buffer = new byte[8192];
				        int bytesRead = 0;
				        fis = new FileInputStream(timebandCsv);
				        while ((bytesRead = fis.read(buffer)) != -1) {
				          zos.write(buffer, 0, bytesRead);
				        }
				        zos.flush();
				        zos.close();
				        bais = new ByteArrayInputStream(baos.toByteArray());

				        this.contentType="application/x-winzip";
				        StringBuffer zipFileName = new StringBuffer();
				        zipFileName.append("TimebandCsv_");
				        zipFileName.append(yearMonthDay);
				        zipFileName.append(".zip");
				        this.contentDisposition="attachment; filename=" +zipFileName.toString();
				        inputStream = bais;
				        addActionMessage("Timeband list downloaded");
				        result = MICSConstant.MICS_SUCCESS;
				        Micslogger.log("Timeband","Download",zipFileName.toString()+" downloaded", loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"SUCCESS");
					} else {
						 Micslogger.log("Timeband","Download","No timeband available for download", loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"FAILED");
						addActionError("Sorry no timeband available for download");
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
			addActionError("Failed to download timeband, " + e.getMessage());
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
	
	
	
	 private File writeToCSV(String yearMonthDay,List timebandList) throws Exception{
		File csvFile = null;
	    FileOutputStream fos = null;
	    FileWriter fw = null;
	    PrintWriter pw = null;
	    BufferedWriter bw = null;
	    try {
	    	
	    
			StringBuffer fileName = new StringBuffer();
			fileName.append("TimebandList_");
			fileName.append(yearMonthDay);
			fileName.append(".csv");
			csvFile = new File(fileName.toString());
			logger.debug("csvFile: " + csvFile.getAbsolutePath());
			fw = new FileWriter(csvFile);
			bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw);
			String micsDelimiter = MICSConfig.getMICSConfigValue(MICSConstant.MICS_SYS_DELIMITER);
			StringBuffer currHeader = new StringBuffer();
			currHeader.append("Timeband Key");
			currHeader.append(micsDelimiter);
			currHeader.append("Timeband Name");
			currHeader.append(micsDelimiter);
			currHeader.append("Timeband Desc");
			currHeader.append(micsDelimiter);
			currHeader.append("Day Selection");
			currHeader.append(micsDelimiter);
			currHeader.append("Begin Hour");
			currHeader.append(micsDelimiter);
			currHeader.append("Begin Minute");
			currHeader.append(micsDelimiter);
			currHeader.append("End Hour");
			currHeader.append(micsDelimiter);
			currHeader.append("End Minute");
			currHeader.append(micsDelimiter);
			currHeader.append("All Day");
			currHeader.append(micsDelimiter);
			currHeader.append("Effective Start Time");
			currHeader.append(micsDelimiter);
			currHeader.append("Effective Stop Time");
			currHeader.append(micsDelimiter);
			currHeader.append("Forever");
			currHeader.append(micsDelimiter);
			currHeader.append("Invert Time Selection");
			currHeader.append(micsDelimiter);
			currHeader.append("Update User");
			currHeader.append(micsDelimiter);
			currHeader.append("Update Timestamp");
			pw.println(currHeader.toString());
			for (Iterator iterTimeband = timebandList.iterator(); iterTimeband
					.hasNext();) {
				Timeband currTimeband = (Timeband)  iterTimeband.next();
				
				StringBuffer currLine = new StringBuffer();
				currLine.append(currTimeband.getId().getTimebandkey());
				currLine.append(micsDelimiter);
				currLine.append(currTimeband.getId().getTimebandname());
				currLine.append(micsDelimiter);
				currLine.append(currTimeband.getTimebanddescription());
				currLine.append(micsDelimiter);
				currLine.append(currTimeband.getDayselecetion());
				currLine.append(micsDelimiter);
				currLine.append(currTimeband.getBeginhour());
				currLine.append(micsDelimiter);
				currLine.append(currTimeband.getBeginminute());
				currLine.append(micsDelimiter);
				currLine.append(currTimeband.getEndhour());
				currLine.append(micsDelimiter);
				currLine.append(currTimeband.getEndminute());
				currLine.append(micsDelimiter);
				currLine.append(currTimeband.getAllday());
				currLine.append(micsDelimiter);
				currLine.append(currTimeband.getEffectivestarttime());
				currLine.append(micsDelimiter);
				currLine.append(currTimeband.getEffectivestoptime());
				currLine.append(micsDelimiter);
				currLine.append(currTimeband.getForever());
				currLine.append(micsDelimiter);
				currLine.append(currTimeband.getInverttimeselection());
				currLine.append(micsDelimiter);
				currLine.append(currTimeband.getUpdateuser());
				currLine.append(micsDelimiter);
				currLine.append(currTimeband.getUpdatetimestamp());
				pw.println(currLine.toString());
			}
			pw.flush();
	    }
	    catch (Exception ex) {
	      csvFile = null;
	      throw new Exception("failed to write timeband list to csv",ex);
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
	 * @return the downloadTimeband
	 */
	public String getDownloadTimeband() {
		return downloadTimeband;
	}

	/**
	 * @param downloadTimeband the downloadTimeband to set
	 */
	public void setDownloadTimeband(String downloadTimeband) {
		this.downloadTimeband = downloadTimeband;
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
	private String downloadTimeband = "2017051608";
	private Map<String, Object> session;
	protected static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
	private static final Logger logger = Logger.getLogger(DownloadTimebandAction.class);
}
