package com.nsn.telkomsel.mics.orahbm;



import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author mulia
 * @version $Id: Announcement.java,v 1.1.4.3 2019/03/22 10:06:06 cvsuser Exp $
 */
public class Announcement implements java.io.Serializable {


	private static final long serialVersionUID = 2488936560843236630L;
	private String announcementkey;
	private String companykey;
	private BigDecimal annoid;
	private String announcementname;
	private String announcementdesc;
	private BigDecimal srfelementid;
	private String updateuser;
	private Timestamp updatetimestamp;

	public Announcement() {
	}

	public Announcement(String announcementkey, String companykey,
			Timestamp updatetimestamp) {
		this.announcementkey = announcementkey;
		this.companykey = companykey;
		this.updatetimestamp = updatetimestamp;
	}

	public Announcement(String announcementkey, String companykey,
			BigDecimal annoid, String announcementname,
			String announcementdesc, BigDecimal srfelementid,
			String updateuser, Timestamp updatetimestamp) {
		this.announcementkey = announcementkey;
		this.companykey = companykey;
		this.annoid = annoid;
		this.announcementname = announcementname;
		this.announcementdesc = announcementdesc;
		this.srfelementid = srfelementid;
		this.updateuser = updateuser;
		this.updatetimestamp = updatetimestamp;
	}

	public String getAnnouncementkey() {
		return this.announcementkey;
	}

	public void setAnnouncementkey(String announcementkey) {
		this.announcementkey = announcementkey;
	}

	public String getCompanykey() {
		return this.companykey;
	}

	public void setCompanykey(String companykey) {
		this.companykey = companykey;
	}

	public BigDecimal getAnnoid() {
		return this.annoid;
	}

	public void setAnnoid(BigDecimal annoid) {
		this.annoid = annoid;
	}

	public String getAnnouncementname() {
		return this.announcementname;
	}

	public void setAnnouncementname(String announcementname) {
		this.announcementname = announcementname;
	}

	public String getAnnouncementdesc() {
		return this.announcementdesc;
	}

	public void setAnnouncementdesc(String announcementdesc) {
		this.announcementdesc = announcementdesc;
	}

	public BigDecimal getSrfelementid() {
		return this.srfelementid;
	}

	public void setSrfelementid(BigDecimal srfelementid) {
		this.srfelementid = srfelementid;
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
