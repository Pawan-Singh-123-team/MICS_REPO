package com.nsn.telkomsel.mics;

/**
 * @author mulia
 *
 */
public class MICSException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3523220644324980404L;
	public MICSException(){
		super();
	}
	public MICSException(String message)
    {
        super(message);
    }

    public MICSException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public MICSException(Throwable cause)
    {
        super(cause);
    }

}
