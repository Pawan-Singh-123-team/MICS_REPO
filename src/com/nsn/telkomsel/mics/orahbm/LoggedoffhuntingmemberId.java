package com.nsn.telkomsel.mics.orahbm;


import java.sql.Timestamp;;

/**
 * @author mulia
 * @version $Id: LoggedoffhuntingmemberId.java,v 1.1.4.3 2019/03/22 10:06:15 cvsuser Exp $
 */
public class LoggedoffhuntingmemberId implements java.io.Serializable {


	private static final long serialVersionUID = 5248125294053777617L;
	private String loggedoffhuntingmemberkey;
	private Timestamp loggedoftimestamp;
	private String updateuser;
	private Timestamp updatetimestamp;

	public LoggedoffhuntingmemberId() {
	}

	public LoggedoffhuntingmemberId(String loggedoffhuntingmemberkey,
			Timestamp loggedoftimestamp, String updateuser,
			Timestamp updatetimestamp) {
		this.loggedoffhuntingmemberkey = loggedoffhuntingmemberkey;
		this.loggedoftimestamp = loggedoftimestamp;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
	}

	public String getLoggedoffhuntingmemberkey() {
		return this.loggedoffhuntingmemberkey;
	}

	public void setLoggedoffhuntingmemberkey(String loggedoffhuntingmemberkey) {
		this.loggedoffhuntingmemberkey = loggedoffhuntingmemberkey;
	}

	public Timestamp getLoggedoftimestamp() {
		return this.loggedoftimestamp;
	}

	public void setLoggedoftimestamp(Timestamp loggedoftimestamp) {
		this.loggedoftimestamp = loggedoftimestamp;
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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof LoggedoffhuntingmemberId))
			return false;
		LoggedoffhuntingmemberId castOther = (LoggedoffhuntingmemberId) other;

		return ((this.getLoggedoffhuntingmemberkey() == castOther
				.getLoggedoffhuntingmemberkey()) || (this
				.getLoggedoffhuntingmemberkey() != null
				&& castOther.getLoggedoffhuntingmemberkey() != null && this
				.getLoggedoffhuntingmemberkey().equals(
						castOther.getLoggedoffhuntingmemberkey())))
				&& ((this.getLoggedoftimestamp() == castOther
						.getLoggedoftimestamp()) || (this
						.getLoggedoftimestamp() != null
						&& castOther.getLoggedoftimestamp() != null && this
						.getLoggedoftimestamp().equals(
								castOther.getLoggedoftimestamp())))
				&& ((this.getUpdateuser() == castOther.getUpdateuser()) || (this
						.getUpdateuser() != null
						&& castOther.getUpdateuser() != null && this
						.getUpdateuser().equals(castOther.getUpdateuser())))
				&& ((this.getUpdatetimestamp() == castOther
						.getUpdatetimestamp()) || (this.getUpdatetimestamp() != null
						&& castOther.getUpdatetimestamp() != null && this
						.getUpdatetimestamp().equals(
								castOther.getUpdatetimestamp())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getLoggedoffhuntingmemberkey() == null ? 0 : this
						.getLoggedoffhuntingmemberkey().hashCode());
		result = 37
				* result
				+ (getLoggedoftimestamp() == null ? 0 : this
						.getLoggedoftimestamp().hashCode());
		result = 37
				* result
				+ (getUpdateuser() == null ? 0 : this.getUpdateuser()
						.hashCode());
		result = 37
				* result
				+ (getUpdatetimestamp() == null ? 0 : this.getUpdatetimestamp()
						.hashCode());
		return result;
	}

}
