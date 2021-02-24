package com.nsn.telkomsel.mics.util;

public class Cobalah {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String responseBody = "{}4,\"error";
		System.out.println("responseBody: "+responseBody);
		String tagStatus = "\"status\":";
		System.out.println("tagStatus " + tagStatus);
		int indexStatus = responseBody.indexOf(tagStatus);
		System.out.println("indexStatus: "+indexStatus);
		String strStatus = responseBody.substring(responseBody.indexOf(tagStatus)+tagStatus.length());
		System.out.println("strStatus: " + strStatus);
		String strClearStatus = strStatus.substring(0, strStatus.indexOf(","));
		System.out.println("strClearStatus: " + strClearStatus);
	}


}