package com.ian.tools.properties;

public class ConnectionUrlTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String readurltoString = ConnectionUrl.getResource("conifgName.properties", "URL_PATH");
		System.out.println("readurltoString : " + readurltoString);
		
		String HHHH = ConnectionUrl.getResource("Connection.properties", "URL_PATH");
		System.out.println("HHHH : " + HHHH);
		
		String UAA = "UAA000";
		String error = ConnectionUrl.getResource("common.properties","common.error.msg."+UAA);
		System.out.println("error : " + error);
	}

}
