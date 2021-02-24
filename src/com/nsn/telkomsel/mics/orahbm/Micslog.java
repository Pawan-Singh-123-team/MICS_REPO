/**
 * 
 */
package com.nsn.telkomsel.mics.orahbm;

import java.sql.Timestamp;

/**
 * @author mulia
 *
 */
public class Micslog {
	
	
	
	/**
	 * 
	 */
	public Micslog() {

	}
	
	


	/**
	 * @param timestamp
	 * @param activity
	 * @param module
	 * @param mics_desc
	 * @param mics_user
	 * @param company_id
	 */
	public Micslog(Timestamp timestamp, String activity, String module,
			String mics_desc, String mics_user, String company_id,String status) {
		super();
		this.timestamp = timestamp;
		this.activity = activity;
		this.module = module;
		this.mics_desc = mics_desc;
		this.mics_user = mics_user;
		this.company_id = company_id;
		this.status = status;
	}




	/**
	 * @param id
	 * @param timestamp
	 * @param activity
	 * @param module
	 * @param mics_desc
	 * @param mics_user
	 */
	public Micslog(Long id, Timestamp timestamp, String activity,
			String module, String mics_desc, String mics_user,String status) {
		super();
		this.id = id;
		this.timestamp = timestamp;
		this.activity = activity;
		this.module = module;
		this.mics_desc = mics_desc;
		this.mics_user = mics_user;
		this.status = status;
	}
	
	
	/**
	 * @param id
	 * @param timestamp
	 * @param activity
	 * @param module
	 * @param mics_desc
	 * @param mics_user
	 * @param company_id
	 */
	public Micslog(Long id, Timestamp timestamp, String activity,
			String module, String mics_desc, String mics_user, String company_id,String status) {
		super();
		this.id = id;
		this.timestamp = timestamp;
		this.activity = activity;
		this.module = module;
		this.mics_desc = mics_desc;
		this.mics_user = mics_user;
		this.company_id = company_id;
		this.status = status;
	}

	
	


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Micslog [activity=" + activity + ", module=" + module
				+ ", mics_user=" + mics_user + ", company_id=" + company_id
				+ ", status=" + status + "]";
	}




	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the timestamp
	 */
	public Timestamp getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * @return the activity
	 */
	public String getActivity() {
		return activity;
	}
	/**
	 * @param activity the activity to set
	 */
	public void setActivity(String activity) {
		this.activity = activity;
	}
	/**
	 * @return the module
	 */
	public String getModule() {
		return module;
	}
	/**
	 * @param module the module to set
	 */
	public void setModule(String module) {
		this.module = module;
	}
	/**
	 * @return the mics_desc
	 */
	public String getMics_desc() {
		return mics_desc;
	}
	/**
	 * @param mics_desc the mics_desc to set
	 */
	public void setMics_desc(String mics_desc) {
		this.mics_desc = mics_desc;
	}
	/**
	 * @return the mics_user
	 */
	public String getMics_user() {
		return mics_user;
	}
	/**
	 * @param mics_user the mics_user to set
	 */
	public void setMics_user(String mics_user) {
		this.mics_user = mics_user;
	}
	
	
	/**
	 * @return the company_id
	 */
	public String getCompany_id() {
		return company_id;
	}
	/**
	 * @param company_id the company_id to set
	 */
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}




	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}



	private Long id;
	private Timestamp timestamp;
	private String activity;
	private String module;
	private String mics_desc;
	private String mics_user;
	private String company_id;
	private String status;
	

}
