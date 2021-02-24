package com.nsn.telkomsel.mics.orahbm;



import java.sql.Timestamp;

/**
 * @author mulia
 * @version $Id: Communitymember.java,v 1.1.4.3 2019/03/22 10:06:09 cvsuser Exp $
 */
public class Communitymember implements java.io.Serializable {


	private static final long serialVersionUID = -343690123171407874L;
	private String communitymemberkey;
	private String communitykey;
	private String micsuserkey;
	private String updateuser;
	private Timestamp updatetimestamp;

	public Communitymember() {
	}

	public Communitymember(String communitymemberkey, String communitykey,
			String micsuserkey, String updateuser) {
		this.communitymemberkey = communitymemberkey;
		this.communitykey = communitykey;
		this.micsuserkey = micsuserkey;
		this.updateuser = updateuser;
	}

	public Communitymember(String communitymemberkey, String communitykey,
			String micsuserkey, String updateuser, Timestamp updatetimestamp) {
		this.communitymemberkey = communitymemberkey;
		this.communitykey = communitykey;
		this.micsuserkey = micsuserkey;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
	}

	public String getCommunitymemberkey() {
		return this.communitymemberkey;
	}

	public void setCommunitymemberkey(String communitymemberkey) {
		this.communitymemberkey = communitymemberkey;
	}

	public String getCommunitykey() {
		return this.communitykey;
	}

	public void setCommunitykey(String communitykey) {
		this.communitykey = communitykey;
	}

	public String getMicsuserkey() {
		return this.micsuserkey;
	}

	public void setMicsuserkey(String micsuserkey) {
		this.micsuserkey = micsuserkey;
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
