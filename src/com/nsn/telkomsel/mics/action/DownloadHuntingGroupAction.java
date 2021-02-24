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
import com.nsn.telkomsel.mics.orahbm.Huntinggroup;
import com.nsn.telkomsel.mics.orahbm.Huntingmember;
import com.nsn.telkomsel.mics.orahbm.Huntingschedule;
import com.nsn.telkomsel.mics.orahbm.Micsgroup;
import com.nsn.telkomsel.mics.orahbm.Micsuser;
import com.nsn.telkomsel.mics.orahbm.Micsusernumber;
import com.nsn.telkomsel.mics.orahbm.Webuser;
import com.nsn.telkomsel.mics.util.MICSConfig;
import com.nsn.telkomsel.mics.util.MICSConstant;
import com.nsn.telkomsel.mics.util.Micslogger;
import com.opensymphony.xwork2.ActionSupport;

public class DownloadHuntingGroupAction extends ActionSupport implements SessionAware {

	

	private static final long serialVersionUID = -4208042545434167963L;
	public String init(){
		logger.debug("init()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(downloadHuntingGroup, loginWebUser.getRolekey())) {
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
					companyList = micsCommonSessionHandler.getCompanyList();
					if (companyList != null) {
						session.put(MICSConstant.MICS_SESSION_COMPANYLIST, companyList);
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
			addActionError("Failed to initialize download hunting group, " + e.getMessage());
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
				if (micsCommonSessionHandler.isMenuAuthorized(downloadHuntingGroup, loginWebUser.getRolekey())) {
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
					//Get Companies
					List companyList = micsCommonSessionHandler.getCompanyList();
					List huntingGroupList = null;
					String strHuntingGroupCompany = null;
					if (companyKey != null && !companyKey.trim().equalsIgnoreCase("")) {
						logger.debug("companyKey: " + companyKey);
						huntingGroupList = micsCommonSessionHandler.getHuntingGroupsByCompany(companyKey);
						logger.debug("huntingGroupList count: " + huntingGroupList.size());
						strHuntingGroupCompany = companyKey;
					} else {
						logger.debug("companyKey: " + companyKey + " Get All");
						huntingGroupList = micsCommonSessionHandler.getHuntingGroups();
						logger.debug("huntingGroupList count: " + huntingGroupList.size());
						strHuntingGroupCompany = "All";
					}
					
					if (huntingGroupList != null && huntingGroupList.size()>0) {
						//Write to CSV File
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
						sdf.format(new Date());
						String yearMonthDay = sdf.format(new Date());
						File huntingGroupCsv = writeToCSV(strHuntingGroupCompany,yearMonthDay,huntingGroupList);
						baos = new ByteArrayOutputStream();
				        ZipOutputStream zos = new ZipOutputStream(baos);
				        ZipEntry ze = new ZipEntry(huntingGroupCsv.getName());
				        zos.putNextEntry(ze);
				        byte[] buffer = new byte[8192];
				        int bytesRead = 0;
				        fis = new FileInputStream(huntingGroupCsv);
				        while ((bytesRead = fis.read(buffer)) != -1) {
				          zos.write(buffer, 0, bytesRead);
				        }
				        zos.flush();
				        zos.close();
				        bais = new ByteArrayInputStream(baos.toByteArray());

				        this.contentType="application/x-winzip";
				        StringBuffer zipFileName = new StringBuffer();
				        zipFileName.append(strHuntingGroupCompany);
				        zipFileName.append("_HuntingGroup_");
				        zipFileName.append(yearMonthDay);
				        zipFileName.append(".zip");
				        this.contentDisposition="attachment; filename=" +zipFileName.toString();
				        inputStream = bais;
				        Micslogger.log("HuntingGroup","Download",zipFileName.toString()+" downloaded", loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"SUCCESS");
				        addActionMessage("HuntingGroup list downloaded");
				        result = MICSConstant.MICS_SUCCESS;
					} else {
						Micslogger.log("HuntingGroup","Download","No Hunting Group available for download", loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"FAILED");
						addActionError("Sorry no hunting group available for download");
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
			addActionError("Failed to download hunting group, " + e.getMessage());
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
	
	 private File writeToCSV(String strHuntingGroupCompany, String yearMonthDay,List huntingGroupList) throws Exception{
		File csvFile = null;
	    FileOutputStream fos = null;
	    FileWriter fw = null;
	    PrintWriter pw = null;
	    BufferedWriter bw = null;
	    try {
	    	
	    
			StringBuffer fileName = new StringBuffer();
			fileName.append(strHuntingGroupCompany);
			fileName.append("_HuntingGroup_");
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
			currHeader.append("Hunting Group Key");
			currHeader.append(micsDelimiter);
			currHeader.append("Hunting Group Name");
			currHeader.append(micsDelimiter);
			currHeader.append("Public Number");
			currHeader.append(micsDelimiter);
			currHeader.append("Private Number");
			currHeader.append(micsDelimiter);
			currHeader.append("Hunting Option");
			currHeader.append(micsDelimiter);
			currHeader.append("Hunting CLI Prefix");
			currHeader.append(micsDelimiter);
			currHeader.append("Welcome Anno");
			currHeader.append(micsDelimiter);
			currHeader.append("Member NA Anno");
			currHeader.append(micsDelimiter);
			currHeader.append("Member NR Anno");
			currHeader.append(micsDelimiter);
			currHeader.append("HS Index");
			currHeader.append(micsDelimiter);
			currHeader.append("Hunting Schedule Key");
			currHeader.append(micsDelimiter);
			currHeader.append("Timeband Key");
			currHeader.append(micsDelimiter);
			currHeader.append("HM Index");
			currHeader.append(micsDelimiter);
			currHeader.append("Hunting Member Key");
			currHeader.append(micsDelimiter);
			currHeader.append("Target Number");
			currHeader.append("Update User");
			currHeader.append(micsDelimiter);
			currHeader.append("Update Timestamp");
			pw.println(currHeader.toString());
			for (Iterator iterHG = huntingGroupList.iterator(); iterHG
					.hasNext();) {
				Object[] currObject = (Object[]) iterHG.next();
				Huntinggroup currHG = (Huntinggroup) currObject[0];
				Huntingschedule currHS = (Huntingschedule) currObject[1];
				Huntingmember currHM = (Huntingmember) currObject[2];
				StringBuffer currLine = new StringBuffer();
				currLine.append(currHG.getCompanykey());
				currLine.append(micsDelimiter);
				currLine.append(currHG.getHuntinggroupkey());
				currLine.append(micsDelimiter);
				currLine.append(currHG.getHuntinggroupname());
				currLine.append(micsDelimiter);
				currLine.append(currHG.getPublicnumber());
				currLine.append(micsDelimiter);
				currLine.append(currHG.getPrivatenumber());
				currLine.append(micsDelimiter);
				currLine.append(currHG.getHuntingoption());
				currLine.append(micsDelimiter);
				currLine.append(currHG.getHuntingcliprefix());
				currLine.append(micsDelimiter);
				currLine.append(currHG.getWelcomeannoid());
				currLine.append(micsDelimiter);
				currLine.append(currHG.getMembernaannoid());
				currLine.append(micsDelimiter);
				currLine.append(currHG.getMembernrannoid());
				currLine.append(micsDelimiter);
				currLine.append(currHS.getOrderindex());
				currLine.append(micsDelimiter);
				currLine.append(currHS.getHuntingschedulekey());
				currLine.append(micsDelimiter);
				currLine.append(currHS.getTimebandkey());
				currLine.append(micsDelimiter);
				currLine.append(currHM.getOrderindex());
				currLine.append(micsDelimiter);
				currLine.append(currHM.getHuntingmemberkey());
				currLine.append(micsDelimiter);
				currLine.append(currHM.getTargetnumber());
				currLine.append(micsDelimiter);
				currLine.append(currHM.getUpdateuser());
				currLine.append(micsDelimiter);
				currLine.append(currHM.getUpdatetimestamp());
				pw.println(currLine.toString());
			}
			pw.flush();
	    }
	    catch (Exception ex) {
	      csvFile = null;
	      throw new Exception("failed to write hunting group list to csv",ex);
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
	 * @return the downloadHuntingGroup
	 */
	public String getDownloadHuntingGroup() {
		return downloadHuntingGroup;
	}

	/**
	 * @param downloadHuntingGroup the downloadHuntingGroup to set
	 */
	public void setDownloadHuntingGroup(String downloadHuntingGroup) {
		this.downloadHuntingGroup = downloadHuntingGroup;
	}

	/**
	 * @return the defaultBufferSize
	 */
	public static int getDefaultBufferSize() {
		return DEFAULT_BUFFER_SIZE;
	}

	/**
	 * @return the companyKey
	 */
	public String getCompanyKey() {
		return companyKey;
	}

	/**
	 * @param companyKey the companyKey to set
	 */
	public void setCompanyKey(String companyKey) {
		this.companyKey = companyKey;
	}

	/**
	 * @return the companyList
	 */
	public List getCompanyList() {
		return companyList;
	}

	/**
	 * @param companyList the companyList to set
	 */
	public void setCompanyList(List companyList) {
		this.companyList = companyList;
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
	
	private String companyKey;
	private List companyList;
	private String contentType;
	private String contentDisposition;
	private InputStream inputStream;
	private String downloadHuntingGroup = "2017051606";
	private Map<String, Object> session;
	protected static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
	private static final Logger logger = Logger.getLogger(DownloadHuntingGroupAction.class);
}

