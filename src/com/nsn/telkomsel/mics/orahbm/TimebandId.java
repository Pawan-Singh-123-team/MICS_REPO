package com.nsn.telkomsel.mics.orahbm;


/**
 * @author mulia
 * @version $Id: TimebandId.java,v 1.1.4.3 2019/03/22 10:06:32 cvsuser Exp $
 */
public class TimebandId implements java.io.Serializable {


	private static final long serialVersionUID = 5417520182896508943L;
	private String timebandkey;
	private String timebandname;

	public TimebandId() {
	}

	public TimebandId(String timebandkey, String timebandname) {
		this.timebandkey = timebandkey;
		this.timebandname = timebandname;
	}

	public String getTimebandkey() {
		return this.timebandkey;
	}

	public void setTimebandkey(String timebandkey) {
		this.timebandkey = timebandkey;
	}

	public String getTimebandname() {
		return this.timebandname;
	}

	public void setTimebandname(String timebandname) {
		this.timebandname = timebandname;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TimebandId))
			return false;
		TimebandId castOther = (TimebandId) other;

		return ((this.getTimebandkey() == castOther.getTimebandkey()) || (this
				.getTimebandkey() != null && castOther.getTimebandkey() != null && this
				.getTimebandkey().equals(castOther.getTimebandkey())))
				&& ((this.getTimebandname() == castOther.getTimebandname()) || (this
						.getTimebandname() != null
						&& castOther.getTimebandname() != null && this
						.getTimebandname().equals(castOther.getTimebandname())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getTimebandkey() == null ? 0 : this.getTimebandkey()
						.hashCode());
		result = 37
				* result
				+ (getTimebandname() == null ? 0 : this.getTimebandname()
						.hashCode());
		return result;
	}

}
