package com.nsn.telkomsel.mics.orahbm;


import java.sql.Timestamp;

/**
 * @author mulia
 * @version $Id: Community.java,v 1.1.4.3 2019/03/22 10:06:08 cvsuser Exp $
 */
public class Community implements java.io.Serializable {


	private static final long serialVersionUID = 8045881943375667969L;
	private String communitykey;
	private String communityname;
	private String contactperson;
	private String address;
	private String email;
	private String updateuser;
	private Timestamp updatetimestamp;

	public Community() {
	}

	public Community(String communitykey, String communityname,
			String contactperson, String email, String updateuser,
			Timestamp updatetimestamp) {
		this.communitykey = communitykey;
		this.communityname = communityname;
		this.contactperson = contactperson;
		this.email = email;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
	}

	public Community(String communitykey, String communityname,
			String contactperson, String address, String email,
			String updateuser, Timestamp updatetimestamp) {
		this.communitykey = communitykey;
		this.communityname = communityname;
		this.contactperson = contactperson;
		this.address = address;
		this.email = email;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
	}

	public String getCommunitykey() {
		return this.communitykey;
	}

	public void setCommunitykey(String communitykey) {
		this.communitykey = communitykey;
	}

	public String getCommunityname() {
		return this.communityname;
	}

	public void setCommunityname(String communityname) {
		this.communityname = communityname;
	}

	public String getContactperson() {
		return this.contactperson;
	}

	public void setContactperson(String contactperson) {
		this.contactperson = contactperson;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUpdateuser() {
		return this.updateuser;
	}

	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}

	public Timestamp getUpdatetimestamp() {
		return this.updatetimestamp;
	}

	public void setUpdatetimestamp(Timestamp updatetimestamp) {
		this.updatetimestamp = updatetimestamp;
	}

}
