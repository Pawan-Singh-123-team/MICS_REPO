package com.nsn.telkomsel.mics.orahbm;


/**
 * @author mulia
 * @version $Id: Loggedoffhuntingmember.java,v 1.1.4.3 2019/03/22 10:06:14 cvsuser Exp $
 */
public class Loggedoffhuntingmember implements java.io.Serializable {

	private static final long serialVersionUID = 5608464308486469431L;
	private LoggedoffhuntingmemberId id;

	public Loggedoffhuntingmember() {
	}

	public Loggedoffhuntingmember(LoggedoffhuntingmemberId id) {
		this.id = id;
	}

	public LoggedoffhuntingmemberId getId() {
		return this.id;
	}

	public void setId(LoggedoffhuntingmemberId id) {
		this.id = id;
	}

}
