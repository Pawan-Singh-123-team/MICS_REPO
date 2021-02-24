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
import com.nsn.telkomsel.mics.orahbm.Micsuser;
import com.nsn.telkomsel.mics.orahbm.Micsusernumber;
import com.nsn.telkomsel.mics.orahbm.Webuser;
import com.nsn.telkomsel.mics.util.MICSConfig;
import com.nsn.telkomsel.mics.util.MICSConstant;
import com.nsn.telkomsel.mics.util.Micslogger;
import com.opensymphony.xwork2.ActionSupport;

public class DownloadEmployeeAction extends ActionSupport implements SessionAware {


	private static final long serialVersionUID = -7967096814116526786L;
	public String init(){
		logger.debug("init()");
		String result = MICSConstant.MICS_ERROR;
		try {
			MICSCommonSessionHandler micsCommonSessionHandler = new MICSCommonSessionHandler();
			// Check User Login 
			Webuser loginWebUser = (Webuser) session.get(MICSConstant.MICS_SESSION_USER);
			if (loginWebUser != null) {
				//Check Authorization
				if (micsCommonSessionHandler.isMenuAuthorized(downloadEmployee, loginWebUser.getRolekey())) {
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
			addActionError("Failed to initialize download employee, " + e.getMessage());
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
				if (micsCommonSessionHandler.isMenuAuthorized(downloadEmployee, loginWebUser.getRolekey())) {
					logger.debug("Authorized " + loginWebUser.getWebusername() + " " + loginWebUser.getRolekey());
					//Get Companies
					List companyList = micsCommonSessionHandler.getCompanyList();
					List employeeList = null;
					String strEmployeeCompany = null;
					if (companyKey != null && !companyKey.trim().equalsIgnoreCase("")) {
						logger.debug("companyKey: " + companyKey);
						employeeList = micsCommonSessionHandler.getEmployeeNumbersByCompany(companyKey);
						logger.debug("employeeList count: " + employeeList.size());
						strEmployeeCompany = companyKey;
					} else {
						logger.debug("companyKey: " + companyKey + " Get All");
						employeeList = micsCommonSessionHandler.getEmployeeNumbers();
						logger.debug("employeeList count: " + employeeList.size());
						strEmployeeCompany = "All";
					}
					
					if (employeeList != null && employeeList.size()>0) {
						//Write to CSV File
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
						sdf.format(new Date());
						String yearMonthDay = sdf.format(new Date());
						File employeeCsv = writeToCSV(strEmployeeCompany,yearMonthDay,employeeList);
						baos = new ByteArrayOutputStream();
				        ZipOutputStream zos = new ZipOutputStream(baos);
				        ZipEntry ze = new ZipEntry(employeeCsv.getName());
				        zos.putNextEntry(ze);
				        byte[] buffer = new byte[8192];
				        int bytesRead = 0;
				        fis = new FileInputStream(employeeCsv);
				        while ((bytesRead = fis.read(buffer)) != -1) {
				          zos.write(buffer, 0, bytesRead);
				        }
				        zos.flush();
				        zos.close();
				        bais = new ByteArrayInputStream(baos.toByteArray());

				        this.contentType="application/x-winzip";
				        StringBuffer zipFileName = new StringBuffer();
				        zipFileName.append(strEmployeeCompany);
				        zipFileName.append("_Employee_");
				        zipFileName.append(yearMonthDay);
				        zipFileName.append(".zip");
				        this.contentDisposition="attachment; filename=" +zipFileName.toString();
				        inputStream = bais;
				        Micslogger.log("Employee","Download",zipFileName.toString()+" downloaded", loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"SUCCESS");
				        addActionMessage("Employee list downloaded");
				        result = MICSConstant.MICS_SUCCESS;
					} else {
						Micslogger.log("Employee","Download","No Employee available for download", loginWebUser.getCompanykey(), loginWebUser.getWebusername(),"FAILED");
						addActionError("Sorry no employee available for download");
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
			addActionError("Failed to download employee, " + e.getMessage());
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
	
	 private File writeToCSV(String StrEmployeeCompany, String yearMonthDay,List employeeList) throws Exception{
		File csvFile = null;
	    FileOutputStream fos = null;
	    FileWriter fw = null;
	    PrintWriter pw = null;
	    BufferedWriter bw = null;
	    try {
	    	
	    
			StringBuffer fileName = new StringBuffer();
			fileName.append(StrEmployeeCompany);
			fileName.append("_Employee_");
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
			currHeader.append("MICS User Key");
			currHeader.append(micsDelimiter);
			currHeader.append("First Name");
			currHeader.append(micsDelimiter);
			currHeader.append("Last Name");
			currHeader.append(micsDelimiter);
			currHeader.append("Main Public Number");
			currHeader.append(micsDelimiter);
			currHeader.append("Main Private Number");
			currHeader.append(micsDelimiter);
			currHeader.append("User Type");
			currHeader.append(micsDelimiter);
			currHeader.append("Group ID");
			currHeader.append(micsDelimiter);
			currHeader.append("Sub Group ID");
			currHeader.append(micsDelimiter);
			currHeader.append("User Group ID");
			currHeader.append(micsDelimiter);
			currHeader.append("Enable Anno Record");
			currHeader.append(micsDelimiter);
			currHeader.append("Hunting Option");
			currHeader.append(micsDelimiter);
			currHeader.append("Keep Hunting On Busy");
			currHeader.append(micsDelimiter);
			currHeader.append("Locked");
			currHeader.append(micsDelimiter);
			currHeader.append("Main Number As Charged Party");
			currHeader.append(micsDelimiter);
			currHeader.append("Main Number As CLI");
			currHeader.append(micsDelimiter);
			currHeader.append("Incoming Callscreening");
			currHeader.append(micsDelimiter);
			currHeader.append("Outgoing Callscreening");
			currHeader.append(micsDelimiter);
			currHeader.append("Language");
			currHeader.append(micsDelimiter);
			currHeader.append("Order Index");
			currHeader.append(micsDelimiter);
			currHeader.append("MICS User Number Key");
			currHeader.append(micsDelimiter);
			currHeader.append("Public Number");
			currHeader.append(micsDelimiter);
			currHeader.append("Private Number");
			currHeader.append(micsDelimiter);
			currHeader.append("IMSI");
			currHeader.append(micsDelimiter);
			currHeader.append("SIPURI");
			currHeader.append(micsDelimiter);
			currHeader.append("Number Type");
			currHeader.append(micsDelimiter);
			currHeader.append("Update User");
			currHeader.append(micsDelimiter);
			currHeader.append("Update Timestamp");
			pw.println(currHeader.toString());
			for (Iterator iterEmployee = employeeList.iterator(); iterEmployee
					.hasNext();) {
				Object[] currEmpNumber = (Object[]) iterEmployee.next();
				Micsuser currEmp = (Micsuser) currEmpNumber[0];
				Micsusernumber currNumber = (Micsusernumber) currEmpNumber[1];
				StringBuffer currLine = new StringBuffer();
				currLine.append(currEmp.getCompanykey());
				currLine.append(micsDelimiter);
				currLine.append(currEmp.getMicsuserkey());
				currLine.append(micsDelimiter);
				currLine.append(currEmp.getMicsfirstname());
				currLine.append(micsDelimiter);
				currLine.append(currEmp.getMicslastname());
				currLine.append(micsDelimiter);
				currLine.append(currEmp.getMainpublicnumber());
				currLine.append(micsDelimiter);
				currLine.append(currEmp.getMainprivatenumber());
				currLine.append(micsDelimiter);
				currLine.append(currEmp.getUsertype());
				currLine.append(micsDelimiter);
				currLine.append(currEmp.getGroupkey());
				currLine.append(micsDelimiter);
				currLine.append(currEmp.getSubgroupid());
				currLine.append(micsDelimiter);
				currLine.append(currEmp.getUsergroupid());
				currLine.append(micsDelimiter);
				currLine.append(currEmp.getEnableannorecording());
				currLine.append(micsDelimiter);
				currLine.append(currEmp.getHuntingoption());
				currLine.append(micsDelimiter);
				currLine.append(currEmp.getKeephuntingonbusy());
				currLine.append(micsDelimiter);
				currLine.append(currEmp.getLocked());
				currLine.append(micsDelimiter);
				currLine.append(currEmp.getMainnumberaschargedparty());
				currLine.append(micsDelimiter);
				currLine.append(currEmp.getMainnumberascli());
				currLine.append(micsDelimiter);
				currLine.append(currEmp.getIncs());
				currLine.append(micsDelimiter);
				currLine.append(currEmp.getOutcs());
				currLine.append(micsDelimiter);
				currLine.append(currEmp.getLanguage());
				currLine.append(micsDelimiter);
				currLine.append(currNumber.getOrderindex());
				currLine.append(micsDelimiter);
				currLine.append(currNumber.getMicsusernumber());
				currLine.append(micsDelimiter);
				currLine.append(currNumber.getPublicnumber());
				currLine.append(micsDelimiter);
				currLine.append(currNumber.getPrivatenumber());
				currLine.append(micsDelimiter);
				currLine.append(currNumber.getImsi());
				currLine.append(micsDelimiter);
				currLine.append(currNumber.getSipuri());
				currLine.append(micsDelimiter);
				currLine.append(currNumber.getNumbertype());
				currLine.append(micsDelimiter);
				currLine.append(currNumber.getUpdateuser());
				currLine.append(micsDelimiter);
				currLine.append(currNumber.getUpdatetimestamp());
				pw.println(currLine.toString());
			}
			pw.flush();
	    }
	    catch (Exception ex) {
	      csvFile = null;
	      throw new Exception("failed to write employee list to csv",ex);
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
	 * @return the downloadEmployee
	 */
	public String getDownloadEmployee() {
		return downloadEmployee;
	}

	/**
	 * @param downloadEmployee the downloadEmployee to set
	 */
	public void setDownloadEmployee(String downloadEmployee) {
		this.downloadEmployee = downloadEmployee;
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
	private String downloadEmployee = "2017051605";
	private Map<String, Object> session;
	protected static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
	private static final Logger logger = Logger.getLogger(DownloadEmployeeAction.class);
}

