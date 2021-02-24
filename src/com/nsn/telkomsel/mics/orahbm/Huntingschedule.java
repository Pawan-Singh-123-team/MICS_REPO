package com.nsn.telkomsel.mics.orahbm;

import java.sql.Timestamp;
import java.math.BigDecimal;

/**
 * @author mulia
 * @version $Id: Huntingschedule.java,v 1.1.4.3 2019/03/22 10:06:12 cvsuser Exp $
 */
public class Huntingschedule implements java.io.Serializable {

	private static final long serialVersionUID = 6656650638964912844L;
	private String huntingschedulekey;
	private BigDecimal orderindex;
	private String huntinggroupkey;
	private String timebandkey;
	private String updateuser;
	private Timestamp updatetimestamp;

	public Huntingschedule() {
	}

	public Huntingschedule(String huntingschedulekey, String huntinggroupkey,
			String timebandkey, String updateuser, Timestamp updatetimestamp) {
		this.huntingschedulekey = huntingschedulekey;
		this.huntinggroupkey = huntinggroupkey;
		this.timebandkey = timebandkey;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
	}

	public Huntingschedule(String huntingschedulekey, BigDecimal orderindex,
			String huntinggroupkey, String timebandkey, String updateuser,
			Timestamp updatetimestamp) {
		this.huntingschedulekey = huntingschedulekey;
		this.orderindex = orderindex;
		this.huntinggroupkey = huntinggroupkey;
		this.timebandkey = timebandkey;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
	}

	public String getHuntingschedulekey() {
		return this.huntingschedulekey;
	}

	public void setHuntingschedulekey(String huntingschedulekey) {
		this.huntingschedulekey = huntingschedulekey;
	}

	public BigDecimal getOrderindex() {
		return this.orderindex;
	}

	public void setOrderindex(BigDecimal orderindex) {
		this.orderindex = orderindex;
	}

	public String getHuntinggroupkey() {
		return this.huntinggroupkey;
	}

	public void setHuntinggroupkey(String huntinggroupkey) {
		this.huntinggroupkey = huntinggroupkey;
	}

	public String getTimebandkey() {
		return this.timebandkey;
	}

	public void setTimebandkey(String timebandkey) {
		this.timebandkey = timebandkey;
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
