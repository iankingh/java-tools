package com.ian.tools.httpclient;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 本類別詳細說明 : 。
 * <p/>
 * Package: tw.com.ian.login <br>
 * File Name: HttpClientHelper <br>
 * <p/>
 * Purpose: 使用 Get 連線<br>
 * 
 * @ClassName: tw.com.ian.login.HttpClientHelper
 * @Description: 使用 Get 連線
 * @Copyright : Copyright (c) Ian Corp. 2017. All Rights Reserved.
 * @Company: ian Team.
 * @author ian
 * @version 1.0, 2017/11/26
 */

public class HttpClientHelper
{
	// String url;

	// public HttpClientHelper(String url)
	// {
	// 	this.url = url;
	// }

	// /**
	//  * doget＜p＞建立GET連線＜br＞ show
	//  * 
	//  * @param String reqUrl = URL, Map<String, String> param 參數
	//  * @return HashMap<String, String> returnMap
	//  */

	// public HashMap<String, String> doget(String reqUrl, Map<String, String> param)
	// {
	// 	/*
	// 	 * 使用 GetMethod 來訪問一個 URL 對應的網頁,實現步驟: 1:生成一個 HttpClinet 對象並設置相應的參數。 2:生成一個 GetMethod 對象並設置響應的參數。 3:用 HttpClinet
	// 	 * 生成的對象來執行 GetMethod 生成的Get 方法。 4:處理響應狀態碼。 5:若響應正常，處理 HTTP 響應內容。 6:釋放連接。
	// 	 */

	// 	/* 1 生成 HttpClinet 對象並設置參數 */
	// 	HttpClient httpClient = new HttpClient();
	// 	/* 2 生成 GetMethod 對象並設置參數 */
	// 	String customerId = param.get("customerId"); // 身份證字號
	// 	String loginAccount = param.get("loginAccount"); // 舊使用者代碼
	// 	String pwd = param.get("pwd"); // 網路連線密碼
	// 	//System.out.println("pwd : " + pwd);
	// 	String loginIp = param.get("loginIp");

	// 	String getMethodRegUrl = reqUrl + "?" + "customerId=" + customerId + "&" + "loginAccount=" + loginAccount + "&"
	// 			+ "pwd=" + pwd + "&" + "loginIp=" + loginIp;
	// 	//System.out.println(getMethodRegUrl);
	// 	GetMethod getMethod = new GetMethod(getMethodRegUrl);

	// 	// 設置請求重試處理，用的是默認的重試處理：請求三次
	// 	getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
	// 	HashMap<String, String> returnMap = new HashMap<String, String>();
	// 	/* 3 執行 HTTP GET 請求 */
	// 	try
	// 	{
	// 		int statusCode = httpClient.executeMethod(getMethod);
	// 		/* 4 判斷訪問的狀態碼 */
	// 		if (statusCode != HttpStatus.SC_OK)
	// 		{
	// 			//System.out.println("請求出錯: " + getMethod.getStatusLine());
	// 		}
	// 		/* 5 處理 HTTP 響應內容 */
	// 		returnMap = parseResponseToMap(getMethod);
	// 		// 讀取 HTTP 響應內容，這裡簡單打印網頁內容
	// 		// 讀取為 InputStream，在網頁內容數據量大時候推薦使用
	// 		// InputStream response = getMethod.getResponseBodyAsStream();
	// 	}
	// 	catch (HttpException e)
	// 	{
	// 		// 發生致命的異常，可能是協議不對或者返回的內容有問題
	// 		//System.out.println("請檢查輸入的URL!" + e);
	// 		e.printStackTrace();
	// 	}
	// 	catch (Exception e)
	// 	{
	// 		// 發生網絡異常
	// 		//System.out.println("發生網絡異常!" + e);
	// 		e.printStackTrace();
	// 	}
	// 	finally
	// 	{
	// 		/* 6 .釋放連接 */
	// 		getMethod.releaseConnection();
	// 	}
	// 	return returnMap;
	// }

	// /**
	//  * HashMap ＜p＞ 將回復的response string轉換為key-->value 形式Map＜br＞ show
	//  * 
	//  * @param GetMethod getMethod
	//  * @return HashMap<String, String> returnMap
	//  */
	// private static HashMap<String, String> parseResponseToMap(GetMethod getMethod)
	// {
	// 	HashMap<String, String> returnMap = new HashMap<String, String>();

	// 	if (getMethod == null)
	// 	{
	// 		return returnMap;
	// 	}
	// 	else
	// 	{

	// 		Header[] headerserrorType = getMethod.getResponseHeaders("errorType");
	// 		Header[] headersresultCode = getMethod.getResponseHeaders("resultCode");

	// 		String key = headerserrorType[0].getValue();
	// 		//System.out.println("key : " + key);
	// 		String value = headersresultCode[0].getValue();
	// 		//System.out.println("value : " + value);
	// 		returnMap.put(key, value);

	// 	}

	// 	return returnMap;

	// }

	// /**
	//  * 判斷是否有回應 validatPassword ＜p＞ 查詢 將UAA 及 MP轉換為 的方法＜br＞ show
	//  * 
	//  * @param Map<String, String> param
	//  * @return String retUAA OR retMP
	//  */
	// public String validatPassword(Map<String, String> param)
	// {
	// 	HashMap<String, String> returnMap = this.doget(url, param);
	// 	if (returnMap != null)
	// 	{
	// 		String retUAA = returnMap.get("UAA");
	// 		String retMP = returnMap.get("MP");
	// 		//System.out.println("retUAA :" + retUAA);
	// 		//System.out.println("retMP :" + retMP);
	// 		if (retUAA != null)
	// 			return retUAA;
	// 		if (retMP != null)
	// 			return retMP;

	// 	}
	// 	return null;
	// }

}
