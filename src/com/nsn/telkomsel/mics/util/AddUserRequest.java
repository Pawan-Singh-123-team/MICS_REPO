package com.nsn.telkomsel.mics.util;

import java.io.Serializable;

import org.apache.log4j.Logger;
/**
 * AddUserRequest.java
 * 
 * @author mulia
 * @version $Id: AddUserRequest.java,v 1.1.4.3 2019/03/22 10:06:35 cvsuser Exp $
 */
public class AddUserRequest implements Serializable {
	
	
	
	/**
	 * @return the micsUserKey
	 */
	public String getMicsUserKey() {
		return micsUserKey;
	}
	/**
	 * @param micsUserKey the micsUserKey to set
	 */
	public void setMicsUserKey(String micsUserKey) {
		this.micsUserKey = micsUserKey;
	}
	/**
	 * @return the micsFirstName
	 */
	public String getMicsFirstName() {
		return micsFirstName;
	}
	/**
	 * @param micsFirstName the micsFirstName to set
	 */
	public void setMicsFirstName(String micsFirstName) {
		this.micsFirstName = micsFirstName;
	}
	/**
	 * @return the micsLastName
	 */
	public String getMicsLastName() {
		return micsLastName;
	}
	/**
	 * @param micsLastName the micsLastName to set
	 */
	public void setMicsLastName(String micsLastName) {
		this.micsLastName = micsLastName;
	}
	/**
	 * @return the micsCompanyKey
	 */
	public String getMicsCompanyKey() {
		return micsCompanyKey;
	}
	/**
	 * @param micsCompanyKey the micsCompanyKey to set
	 */
	public void setMicsCompanyKey(String micsCompanyKey) {
		this.micsCompanyKey = micsCompanyKey;
	}
	/**
	 * @return the micsCompanyName
	 */
	public String getMicsCompanyName() {
		return micsCompanyName;
	}
	/**
	 * @param micsCompanyName the micsCompanyName to set
	 */
	public void setMicsCompanyName(String micsCompanyName) {
		this.micsCompanyName = micsCompanyName;
	}
	/**
	 * @return the micsMemberType
	 */
	public String getMicsMemberType() {
		return micsMemberType;
	}
	/**
	 * @param micsMemberType the micsMemberType to set
	 */
	public void setMicsMemberType(String micsMemberType) {
		this.micsMemberType = micsMemberType;
	}
	
	
	/**
	 * @return the micsPrivNumber
	 */
	public String getMicsPrivNumber() {
		return micsPrivNumber;
	}
	/**
	 * @param micsPrivNumber the micsPrivNumber to set
	 */
	public void setMicsPrivNumber(String micsPrivNumber) {
		this.micsPrivNumber = micsPrivNumber;
	}
	/**
	 * @return the micsPubNumber
	 */
	public String getMicsPubNumber() {
		return micsPubNumber;
	}
	/**
	 * @param micsPubNumber the micsPubNumber to set
	 */
	public void setMicsPubNumber(String micsPubNumber) {
		this.micsPubNumber = micsPubNumber;
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
	
	
	/**
	 * @return the memberKey
	 */
	public String getMemberKey() {
		return memberKey;
	}
	/**
	 * @param memberKey the memberKey to set
	 */
	public void setMemberKey(String memberKey) {
		this.memberKey = memberKey;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((micsPrivNumber == null) ? 0 : micsPrivNumber.hashCode());
		result = prime * result
				+ ((micsPubNumber == null) ? 0 : micsPubNumber.hashCode());
		result = prime * result
				+ ((micsUserKey == null) ? 0 : micsUserKey.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddUserRequest other = (AddUserRequest) obj;
		if (micsPrivNumber == null) {
			if (other.micsPrivNumber != null)
				return false;
		} else if (!micsPrivNumber.equals(other.micsPrivNumber))
			return false;
		if (micsPubNumber == null) {
			if (other.micsPubNumber != null)
				return false;
		} else if (!micsPubNumber.equals(other.micsPubNumber))
			return false;
		if (micsUserKey == null) {
			if (other.micsUserKey != null)
				return false;
		} else if (!micsUserKey.equals(other.micsUserKey))
			return false;
		return true;
	}

	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AddUserRequest [micsUserKey=" + micsUserKey
				+ ", micsFirstName=" + micsFirstName + ", micsLastName="
				+ micsLastName + ", micsCompanyKey=" + micsCompanyKey
				+ ", micsCompanyName=" + micsCompanyName + ", micsPrivNumber="
				+ micsPrivNumber + ", micsPubNumber=" + micsPubNumber
				+ ", orderindex=" + orderindex + "]";
	}



	private String micsUserKey;
	private String micsFirstName;
	private String micsLastName;
	private String micsCompanyKey;
	private String micsCompanyName;
	private String micsMemberType;
	private String micsPrivNumber;
	private String micsPubNumber;
	private String memberKey;
	private int orderindex;
	private boolean remove;
	private boolean selected;
	private String actionType;
	private static final Logger logger = Logger.getLogger(AddUserRequest.class);
}
