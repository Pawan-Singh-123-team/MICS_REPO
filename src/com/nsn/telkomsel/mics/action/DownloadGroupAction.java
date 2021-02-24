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
import com.nsn.telkomsel.mics.orahbm.Micsgroup;
import com.nsn.telkomsel.mics.orahbm.Webuser;
import com.nsn.telkomsel.mics.util.MICSConfig;
import com.nsn.telkomsel.mics.util.MICSConstant;
import com.nsn.telkomsel.mics.util.Micslogger;
import com.opensymphony.xwork2.ActionSupport;

public class DownloadGroupAction extends ActionSupport implements SessionAware {


	private static final long serialVersionUID = 1619168356702693573L;
	public String init(){
		logger.debug("init()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(downloadGroup, loginWebUser.getRolekey())) {
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
			addActionError("Failed to initialize download group, " + e.getMessage());
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
				if (micsCommonSessionHandler.isMenuAuthorized(downloadGroup, loginWebUser.getRolekey())) {
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
					//Get Companies
					List companyList = micsCommonSessionHandler.getCompanyList();
					List groupList = null;
					String strGroupCompany = null;
					if (companyKey != null && !companyKey.trim().equalsIgnoreCase("")) {
						logger.debug("companyKey: " + companyKey);
						groupList = micsCommonSessionHandler.getGroupsByCompany(companyKey);
						logger.debug("groupList count: " + groupList.size());
						strGroupCompany = companyKey;
					} else {
						logger.debug("companyKey: " + companyKey + " Get All");
						groupList = micsCommonSessionHandler.getGroups();
						logger.debug("groupList count: " + groupList.size());
						strGroupCompany = "All";
					}
					
					if (groupList != null && groupList.size()>0) {
						//Write to CSV File
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
						sdf.format(new Date());
						String yearMonthDay = sdf.format(new Date());
						File companyCsv = writeToCSV(strGroupCompany,yearMonthDay,groupList);
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
				        zipFileName.append(strGroupCompany);
				        zipFileName.append("_Groups_");
				        zipFileName.append(yearMonthDay);
				        zipFileName.append(".zip");
				        this.contentDisposition="attachment; filename=" +zipFileName.toString();
				        inputStream = bais;
				        Micslogger.log("Group","Download",zipFileName.toString()+" downloaded", loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"SUCCESS");
				        addActionMessage("Group list downloaded");
				        result = MICSConstant.MICS_SUCCESS;
					} else {
						Micslogger.log("Group","Download","No Group available for download", loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"FAILED");
						addActionError("Sorry no groups available for download");
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
			addActionError("Failed to initialize download group, " + e.getMessage());
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
	
	 private File writeToCSV(String StrGroupCompany, String yearMonthDay,List groupList) throws Exception{
		File csvFile = null;
	    FileOutputStream fos = null;
	    FileWriter fw = null;
	    PrintWriter pw = null;
	    BufferedWriter bw = null;
	    try {
	    	
	    
			StringBuffer fileName = new StringBuffer();
			fileName.append(StrGroupCompany);
			fileName.append("_Group_");
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
			currHeader.append("Group Key");
			currHeader.append(micsDelimiter);
			currHeader.append("Group Name");
			currHeader.append(micsDelimiter);
			currHeader.append("Group ID");
			currHeader.append(micsDelimiter);
			currHeader.append("Group CLI");
			currHeader.append(micsDelimiter);
			currHeader.append("Incoming Callscreening");
			currHeader.append(micsDelimiter);
			currHeader.append("Outgoing Callscreening");
			currHeader.append(micsDelimiter);
			currHeader.append("Update User");
			currHeader.append(micsDelimiter);
			currHeader.append("Update Timestamp");
			pw.println(currHeader.toString());
			for (Iterator iterGroup = groupList.iterator(); iterGroup
					.hasNext();) {
				Micsgroup currGroup = (Micsgroup) iterGroup.next();
				StringBuffer currLine = new StringBuffer();
				currLine.append(currGroup.getCompanykey());
				currLine.append(micsDelimiter);
				currLine.append(currGroup.getMicsgroupkey());
				currLine.append(micsDelimiter);
				currLine.append(currGroup.getGroupname());
				currLine.append(micsDelimiter);
				currLine.append(currGroup.getGroupid());
				currLine.append(micsDelimiter);
				currLine.append(currGroup.getGroupcli());
				currLine.append(micsDelimiter);
				currLine.append(currGroup.getIncs());
				currLine.append(micsDelimiter);
				currLine.append(currGroup.getOutcs());
				currLine.append(micsDelimiter);
				currLine.append(currGroup.getUpdateuser());
				currLine.append(micsDelimiter);
				currLine.append(currGroup.getUpdatetimestamp());
				pw.println(currLine.toString());
			}
			pw.flush();
	    }
	    catch (Exception ex) {
	      csvFile = null;
	      throw new Exception("failed to write group list to csv",ex);
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
	 * @return the downloadGroup
	 */
	public String getDownloadGroup() {
		return downloadGroup;
	}

	/**
	 * @param downloadGroup the downloadGroup to set
	 */
	public void setDownloadGroup(String downloadGroup) {
		this.downloadGroup = downloadGroup;
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
	private String downloadGroup = "2017051602";
	private Map<String, Object> session;
	protected static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
	private static final Logger logger = Logger.getLogger(DownloadGroupAction.class);
}

