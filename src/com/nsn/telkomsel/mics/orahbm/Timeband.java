package com.nsn.telkomsel.mics.orahbm;


import java.sql.Timestamp;
import java.math.BigDecimal;

/**
 * @author mulia
 * @version $Id: Timeband.java,v 1.1.4.3 2019/03/22 10:06:31 cvsuser Exp $
 */
public class Timeband implements java.io.Serializable {


	private static final long serialVersionUID = 2499922278118295928L;
	private TimebandId id;
	private String timebanddescription;
	private BigDecimal dayselecetion;
	private BigDecimal allday;
	private BigDecimal beginhour;
	private BigDecimal beginminute;
	private BigDecimal endhour;
	private BigDecimal endminute;
	private BigDecimal inverttimeselection;
	private BigDecimal forever;
	private String effectivestarttime;
	private String effectivestoptime;
	private String updateuser;
	private Timestamp updatetimestamp;

	public Timeband() {
	}

	public Timeband(TimebandId id, Timestamp updatetimestamp) {
		this.id = id;
		this.updatetimestamp = updatetimestamp;
	}

	public Timeband(TimebandId id, String timebanddescription,
			BigDecimal dayselecetion, BigDecimal allday, BigDecimal beginhour,
			BigDecimal beginminute, BigDecimal endhour, BigDecimal endminute,
			BigDecimal inverttimeselection, BigDecimal forever,
			String effectivestarttime, String effectivestoptime,
			String updateuser, Timestamp updatetimestamp) {
		this.id = id;
		this.timebanddescription = timebanddescription;
		this.dayselecetion = dayselecetion;
		this.allday = allday;
		this.beginhour = beginhour;
		this.beginminute = beginminute;
		this.endhour = endhour;
		this.endminute = endminute;
		this.inverttimeselection = inverttimeselection;
		this.forever = forever;
		this.effectivestarttime = effectivestarttime;
		this.effectivestoptime = effectivestoptime;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
	}

	public TimebandId getId() {
		return this.id;
	}

	public void setId(TimebandId id) {
		this.id = id;
	}

	public String getTimebanddescription() {
		return this.timebanddescription;
	}

	public void setTimebanddescription(String timebanddescription) {
		this.timebanddescription = timebanddescription;
	}

	public BigDecimal getDayselecetion() {
		return this.dayselecetion;
	}

	public void setDayselecetion(BigDecimal dayselecetion) {
		this.dayselecetion = dayselecetion;
	}

	public BigDecimal getAllday() {
		return this.allday;
	}

	public void setAllday(BigDecimal allday) {
		this.allday = allday;
	}

	public BigDecimal getBeginhour() {
		return this.beginhour;
	}

	public void setBeginhour(BigDecimal beginhour) {
		this.beginhour = beginhour;
	}

	public BigDecimal getBeginminute() {
		return this.beginminute;
	}

	public void setBeginminute(BigDecimal beginminute) {
		this.beginminute = beginminute;
	}

	public BigDecimal getEndhour() {
		return this.endhour;
	}

	public void setEndhour(BigDecimal endhour) {
		this.endhour = endhour;
	}

	public BigDecimal getEndminute() {
		return this.endminute;
	}

	public void setEndminute(BigDecimal endminute) {
		this.endminute = endminute;
	}

	public BigDecimal getInverttimeselection() {
		return this.inverttimeselection;
	}

	public void setInverttimeselection(BigDecimal inverttimeselection) {
		this.inverttimeselection = inverttimeselection;
	}

	public BigDecimal getForever() {
		return this.forever;
	}

	public void setForever(BigDecimal forever) {
		this.forever = forever;
	}

	public String getEffectivestarttime() {
		return this.effectivestarttime;
	}

	public void setEffectivestarttime(String effectivestarttime) {
		this.effectivestarttime = effectivestarttime;
	}

	public String getEffectivestoptime() {
		return this.effectivestoptime;
	}

	public void setEffectivestoptime(String effectivestoptime) {
		this.effectivestoptime = effectivestoptime;
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
