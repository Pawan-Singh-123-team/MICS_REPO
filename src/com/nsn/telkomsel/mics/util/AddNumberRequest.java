package com.nsn.telkomsel.mics.util;

public class AddNumberRequest {
	
	
	/**
	 * @return the publicNumber
	 */
	public String getPublicNumber() {
		return publicNumber;
	}
	/**
	 * @param publicNumber the publicNumber to set
	 */
	public void setPublicNumber(String publicNumber) {
		this.publicNumber = publicNumber;
	}
	/**
	 * @return the privateNumber
	 */
	public String getPrivateNumber() {
		return privateNumber;
	}
	/**
	 * @param privateNumber the privateNumber to set
	 */
	public void setPrivateNumber(String privateNumber) {
		this.privateNumber = privateNumber;
	}
	/**
	 * @return the numberType
	 */
	public int getNumberType() {
		return numberType;
	}
	/**
	 * @param numberType the numberType to set
	 */
	public void setNumberType(int numberType) {
		this.numberType = numberType;
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
	 * @return the mainPublic
	 */
	public boolean isMainPublic() {
		return mainPublic;
	}
	/**
	 * @param mainPublic the mainPublic to set
	 */
	public void setMainPublic(boolean mainPublic) {
		this.mainPublic = mainPublic;
	}
	/**
	 * @return the mainPrivate
	 */
	public boolean isMainPrivate() {
		return mainPrivate;
	}
	/**
	 * @param mainPrivate the mainPrivate to set
	 */
	public void setMainPrivate(boolean mainPrivate) {
		this.mainPrivate = mainPrivate;
	}
	
	
	/**
	 * @return the imsi
	 */
	public String getImsi() {
		return imsi;
	}
	/**
	 * @param imsi the imsi to set
	 */
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	
	
	/**
	 * @return the sipuri
	 */
	public String getSipuri() {
		return sipuri;
	}
	/**
	 * @param sipuri the sipuri to set
	 */
	public void setSipuri(String sipuri) {
		this.sipuri = sipuri;
	}
	/**
	 * @return the orderIndex
	 */
	public int getOrderIndex() {
		return orderIndex;
	}
	/**
	 * @param orderIndex the orderIndex to set
	 */
	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}
	

	/**
	 * @return the prov_req_id
	 */
	public int getProv_req_id() {
		return prov_req_id;
	}
	/**
	 * @param prov_req_id the prov_req_id to set
	 */
	public void setProv_req_id(int prov_req_id) {
		this.prov_req_id = prov_req_id;
	}
	/**
	 * @return the prov_req_status
	 */
	public int getProv_req_status() {
		return prov_req_status;
	}
	/**
	 * @param prov_req_status the prov_req_status to set
	 */
	public void setProv_req_status(int prov_req_status) {
		this.prov_req_status = prov_req_status;
	}


	private String publicNumber;
	private String privateNumber;
	private String imsi;
	private String sipuri;
	private int numberType;
	private int orderIndex;
	private boolean remove;
	private boolean mainPublic;
	private boolean mainPrivate;
	private int prov_req_id;
	private int prov_req_status;
}
