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
 * @version $Id: DownloadCommunityAction.java,v 1.1.4.3 2019/03/22 10:05:47 cvsuser Exp $
 */

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.nsn.telkomsel.mics.MICSException;
import com.nsn.telkomsel.mics.handler.MICSCommonSessionHandler;
import com.nsn.telkomsel.mics.orahbm.Community;
import com.nsn.telkomsel.mics.orahbm.Communitymember;
import com.nsn.telkomsel.mics.orahbm.Company;
import com.nsn.telkomsel.mics.orahbm.Micsuser;
import com.nsn.telkomsel.mics.orahbm.Micsusernumber;
import com.nsn.telkomsel.mics.orahbm.Partnership;
import com.nsn.telkomsel.mics.orahbm.Webuser;
import com.nsn.telkomsel.mics.util.MICSConfig;
import com.nsn.telkomsel.mics.util.MICSConstant;
import com.nsn.telkomsel.mics.util.Micslogger;
import com.opensymphony.xwork2.ActionSupport;

public class DownloadCommunityAction extends ActionSupport implements
		SessionAware {

	

	private static final long serialVersionUID = -3561830360967711347L;
	public String init(){
		logger.debug("init()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(downloadCommunity, loginWebUser.getRolekey())) {
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
			addActionError("Failed to initialize download community, " + e.getMessage());
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
				if (micsCommonSessionHandler.isMenuAuthorized(downloadCommunity, loginWebUser.getRolekey())) {
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
					//Get Partnership
					List communityList = micsCommonSessionHandler.getCommunities();
					if (communityList != null && communityList.size()>0) {
						//Write to CSV File
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
						sdf.format(new Date());
						String yearMonthDay = sdf.format(new Date());
						File communityCsv = writeToCSV(yearMonthDay,communityList);
						baos = new ByteArrayOutputStream();
				        ZipOutputStream zos = new ZipOutputStream(baos);
				        ZipEntry ze = new ZipEntry(communityCsv.getName());
				        zos.putNextEntry(ze);
				        byte[] buffer = new byte[8192];
				        int bytesRead = 0;
				        fis = new FileInputStream(communityCsv);
				        while ((bytesRead = fis.read(buffer)) != -1) {
				          zos.write(buffer, 0, bytesRead);
				        }
				        zos.flush();
				        zos.close();
				        bais = new ByteArrayInputStream(baos.toByteArray());

				        this.contentType="application/x-winzip";
				        StringBuffer zipFileName = new StringBuffer();
				        zipFileName.append("CommunityCsv_");
				        zipFileName.append(yearMonthDay);
				        zipFileName.append(".zip");
				        this.contentDisposition="attachment; filename=" +zipFileName.toString();
				        inputStream = bais;
				        addActionMessage("Community list downloaded");
				        result = MICSConstant.MICS_SUCCESS;
				        Micslogger.log("Community","Download",zipFileName.toString()+" downloaded", loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"SUCCESS");
					} else {
						 Micslogger.log("Community","Download","No community available for download", loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"FAILED");
						addActionError("Sorry no community available for download");
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
			addActionError("Failed to download community, " + e.getMessage());
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
	
	 private File writeToCSV(String yearMonthDay,List communityList) throws Exception{
		File csvFile = null;
	    FileOutputStream fos = null;
	    FileWriter fw = null;
	    PrintWriter pw = null;
	    BufferedWriter bw = null;
	    try {
	    	
	    
			StringBuffer fileName = new StringBuffer();
			fileName.append("CommunityList_");
			fileName.append(yearMonthDay);
			fileName.append(".csv");
			csvFile = new File(fileName.toString());
			logger.debug("csvFile: " + csvFile.getAbsolutePath());
			fw = new FileWriter(csvFile);
			bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw);
			String micsDelimiter = MICSConfig.getMICSConfigValue(MICSConstant.MICS_SYS_DELIMITER);
			StringBuffer currHeader = new StringBuffer();
			currHeader.append("Community Key");
			currHeader.append(micsDelimiter);
			currHeader.append("Community Name");
			currHeader.append(micsDelimiter);
			currHeader.append("Contact Person");
			currHeader.append(micsDelimiter);
			currHeader.append("Email");
			currHeader.append(micsDelimiter);
			currHeader.append("Address");
			currHeader.append(micsDelimiter);
			currHeader.append("Community Member Key");
			currHeader.append(micsDelimiter);
			currHeader.append("MICS User Key");
			currHeader.append(micsDelimiter);
			currHeader.append("Update User");
			currHeader.append(micsDelimiter);
			currHeader.append("Update Timestamp");
			pw.println(currHeader.toString());
			for (Iterator iterCommunity = communityList.iterator(); iterCommunity
					.hasNext();) {
				Object[] currCommunity = (Object[])  iterCommunity.next();
				Community currComm = (Community) currCommunity[0];
				Communitymember currCommMember = (Communitymember) currCommunity[1];
				StringBuffer currLine = new StringBuffer();
				currLine.append(currComm.getCommunitykey());
				currLine.append(micsDelimiter);
				currLine.append(currComm.getCommunityname());
				currLine.append(micsDelimiter);
				currLine.append(currComm.getContactperson());
				currLine.append(micsDelimiter);
				currLine.append(currComm.getEmail());
				currLine.append(micsDelimiter);
				currLine.append(currComm.getAddress());
				currLine.append(micsDelimiter);
				currLine.append(currCommMember.getCommunitymemberkey());
				currLine.append(micsDelimiter);
				currLine.append(currCommMember.getMicsuserkey());
				currLine.append(micsDelimiter);
				currLine.append(currCommMember.getUpdateuser());
				currLine.append(micsDelimiter);
				currLine.append(currCommMember.getUpdatetimestamp());
				pw.println(currLine.toString());
			}
			pw.flush();
	    }
	    catch (Exception ex) {
	      csvFile = null;
	      throw new Exception("failed to write community list to csv",ex);
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
	 * @return the downloadCommunity
	 */
	public String getDownloadCommunity() {
		return downloadCommunity;
	}

	/**
	 * @param downloadCommunity the downloadCommunity to set
	 */
	public void setDownloadCommunity(String downloadCommunity) {
		this.downloadCommunity = downloadCommunity;
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
	private String downloadCommunity = "2017051604";
	private Map<String, Object> session;
	protected static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
	private static final Logger logger = Logger.getLogger(DownloadCommunityAction.class);
}
