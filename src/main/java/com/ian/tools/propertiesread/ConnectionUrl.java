package com.ian.tools.propertiesread;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 
 * 本類別詳細說明。
 * <p/>
 * Package: tw.com.fstop.login <br>
 * File Name: ConnectionUrl <br>
 * <p/>
 * Purpose: <br>
 * 
 * @ClassName: tw.com.fstop.login.ConnectionUrl
 * @Description: 讀取設定檔 filename ; 檔案名 readsname ; 設定檔名 ex : URL_PATH = XX
 * @Copyright : Copyright (c) TCB Corp. 2017. All Rights Reserved.
 * @Company: FSTOP Team.
 * @author Administrator
 * @version 1.0, 2017/11/28
 * 
 *          java getresourceasstream 的方法
 */

public class ConnectionUrl {
	// 宣告成 static 在同一專案不用new
	public static String getResource(String filename, String readsname) {

		// 建立一個 Properties
		Properties prop = new Properties();
		// 宣告 InputStream = null
		InputStream input = null;
		// 建立回傳變數
		String rsc = null;

		try {

			// EX filename = "Connection.properties";
			input = ConnectionUrl.class.getClassLoader().getResourceAsStream(filename);
			if (input == null) {
				// System.out.println("Sorry, unable to find " + filename);
				return rsc;
			}
			// 關於java.util.Properties讀取中文亂碼的正確解決方案（不要再用的native2ascii.exe了）
			// 一定要轉UTF-8 因為不知道傳入的編碼
			prop.load(new InputStreamReader(input, "UTF-8"));

			// get the property value and print it out
			rsc = prop.getProperty(readsname);
			// 關閉資料流
			input.close();
			// 回傳值
			return rsc;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rsc;
	}

}