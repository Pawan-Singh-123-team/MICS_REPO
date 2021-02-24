package com.nsn.telkomsel.mics.util;

import java.sql.Timestamp;
import java.util.List;

public class AddHuntingSchedule {
	


	public AddHuntingSchedule() {
		
	}
	
	
	/**
	 * @param huntingschedulekey
	 * @param orderindex
	 * @param huntinggroupkey
	 * @param timebandkey
	 */
	public AddHuntingSchedule(String huntingschedulekey, int orderindex,
			String huntinggroupkey, String timebandkey) {
		super();
		this.huntingschedulekey = huntingschedulekey;
		this.orderindex = orderindex;
		this.huntinggroupkey = huntinggroupkey;
		this.timebandkey = timebandkey;
	}

	
	/**
	 * @return the huntingschedulekey
	 */
	public String getHuntingschedulekey() {
		return huntingschedulekey;
	}


	/**
	 * @param huntingschedulekey the huntingschedulekey to set
	 */
	public void setHuntingschedulekey(String huntingschedulekey) {
		this.huntingschedulekey = huntingschedulekey;
	}


	/**
	 * @return the orderindex
	 */
	public int getOrderindex() {
		return orderindex;
	}


	/**
	 * @param orderindex the orderindex to set
	 */
	public void setOrderindex(int orderindex) {
		this.orderindex = orderindex;
	}


	/**
	 * @return the huntinggroupkey
	 */
	public String getHuntinggroupkey() {
		return huntinggroupkey;
	}


	/**
	 * @param huntinggroupkey the huntinggroupkey to set
	 */
	public void setHuntinggroupkey(String huntinggroupkey) {
		this.huntinggroupkey = huntinggroupkey;
	}


	/**
	 * @return the timebandkey
	 */
	public String getTimebandkey() {
		return timebandkey;
	}


	/**
	 * @param timebandkey the timebandkey to set
	 */
	public void setTimebandkey(String timebandkey) {
		this.timebandkey = timebandkey;
	}


	/**
	 * @return the updateuser
	 */
	public String getUpdateuser() {
		return updateuser;
	}


	/**
	 * @param updateuser the updateuser to set
	 */
	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}


	/**
	 * @return the updatetimestamp
	 */
	public Timestamp getUpdatetimestamp() {
		return updatetimestamp;
	}


	/**
	 * @param updatetimestamp the updatetimestamp to set
	 */
	public void setUpdatetimestamp(Timestamp updatetimestamp) {
		this.updatetimestamp = updatetimestamp;
	}


	/**
	 * @return the remove
	 */
	public boolean isRemove() {
		return remove;
	}


	/**
	 * @param remove the remove to set
	 */
	public void setRemove(boolean remove) {
		this.remove = remove;
	}


	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}


	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	
	/**
	 * @return the micsUserNumberReq
	 */
	public List<AddUserRequest> getMemberReq() {
		return memberReq;
	}


	/**
	 * @param micsUserNumberReq the micsUserNumberReq to set
	 */
	public void setMemberReq(List<AddUserRequest> memberReq) {
		this.memberReq = memberReq;
	}
	
	

	/**
	 * @return the delMemberReq
	 */
	public List<AddUserRequest> getDelMemberReq() {
		return delMemberReq;
	}


	/**
	 * @param delMemberReq the delMemberReq to set
	 */
	public void setDelMemberReq(List<AddUserRequest> delMemberReq) {
		this.delMemberReq = delMemberReq;
	}


	/**
	 * @return the timebandname
	 */
	public String getTimebandname() {
		return timebandname;
	}


	/**
	 * @param timebandname the timebandname to set
	 */
	public void setTimebandname(String timebandname) {
		this.timebandname = timebandname;
	}


	/**
	 * @return the timebanddesc
	 */
	public String getTimebanddesc() {
		return timebanddesc;
	}


	/**
	 * @param timebanddesc the timebanddesc to set
	 */
	public void setTimebanddesc(String timebanddesc) {
		this.timebanddesc = timebanddesc;
	}
	
	


	/**
	 * @return the actionType
	 */
	public String getActionType() {
		return actionType;
	}


	/**
	 * @param actionType the actionType to set
	 */
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AddHuntingSchedule [huntingschedulekey=" + huntingschedulekey
				+ ", orderindex=" + orderindex + ", huntinggroupkey="
				+ huntinggroupkey + ", timebandkey=" + timebandkey + "]";
	}

	


	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((huntinggroupkey == null) ? 0 : huntinggroupkey.hashCode());
		result = prime
				* result
				+ ((huntingschedulekey == null) ? 0 : huntingschedulekey
						.hashCode());
		return result;
	}



	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddHuntingSchedule other = (AddHuntingSchedule) obj;
		if (huntinggroupkey == null) {
			if (other.huntinggroupkey != null)
				return false;
		} else if (!huntinggroupkey.equals(other.huntinggroupkey))
			return false;
		if (huntingschedulekey == null) {
			if (other.huntingschedulekey != null)
				return false;
		} else if (!huntingschedulekey.equals(other.huntingschedulekey))
			return false;
		return true;
	}



	private String huntingschedulekey;
	private int orderindex;
	private String huntinggroupkey;
	private String timebandkey;
	private String timebandname;
	private String timebanddesc;
	private List<AddUserRequest> memberReq;
	private List<AddUserRequest> delMemberReq;
	private String updateuser;
	private Timestamp updatetimestamp;
	private boolean remove;
	private boolean selected;
	private String actionType;
	
	
	
}
