package com.ian.tools.httpclient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

import com.ian.propertiesread.ConnectionUrl;

/**
 * 
 * 本類別詳細說明。
 * <p/>
 * Package: com.tcb.bfnb.login <br>
 * File Name: HttpClientPost <br>
 * <p/>
 * Purpose: 與　登入　API 連線程式 <br>
 * 
 * @ClassName: tw.com.fstop.login.HttpClientPost
 * @Description: 使用 post 連線
 * @return : HashMap<String, String> returnCode 狀態碼 returnDesc 錯誤回傳狀態
 * @Copyright : Copyright (c) TCB Corp. 2017. All Rights Reserved.
 * @Company: FSTOP Team.
 * @author ian
 * @version 1.0, 2017/11/15
 * 
 */

public class HttpClientLogin
{

	public HashMap<String, String> doget(Map<String, String> param)
	{

		HashMap<String, String> returnMap = new HashMap<String, String>();

		// String URL_PATH =
		// "http://localhost:9080/TCB.TWNB.EXTERNAL.WEB/EBankLoginVerifyService";
		// String URL_PATH = "http://localhost:9081/TCB.TWNB.EXTERNAL.WEB/EBankLoginVerifyService";
		String URL_PATH = ConnectionUrl.getResource("Connection.properties", "URL_PATH");

		// 建立HttpClient實體
		HttpClientParams p = new HttpClientParams();
		p.setContentCharset("UTF-8");
		HttpClient client = new HttpClient(p);
		// 建立 傳輸資料
		NameValuePair[] data = { new NameValuePair("cid", param.get("customerId")),
				new NameValuePair("uid", param.get("loginAccount")), new NameValuePair("pwd", param.get("pwd")),
				new NameValuePair("sys", "BFNB"), new NameValuePair("log", "1"),
				new NameValuePair("cip", param.get("loginIp")) };
		// 建立GetMethod實體, 並指派網址, PostMethod會自動處理該網轉址動作,
		PostMethod method = new PostMethod(URL_PATH); // 不需自動轉址
		method.setFollowRedirects(false);
		method.setRequestBody(data);

		try
		{
			// 連線超時就TimeOut
			client.getHttpConnectionManager().getParams().setConnectionTimeout(600000);
			// 讀取超時就TimeOut
			client.getHttpConnectionManager().getParams().setSoTimeout(600000);
			// 返回狀態值.
			int statusCode = client.executeMethod(method);

			if (statusCode == HttpStatus.SC_OK)
			{
				// 取得回傳資訊.
				// Header[] header = method.getResponseHeaders();

				// for (Header h : header)
				// {
				// // //System.out.println(h.getName() + ", " + new
				// // String(h.getValue().getBytes("UTF-8")));
				// // //System.out.println(h.getName() + ", " + h.getValue());
				// ////System.out.println(h.getName() + ", " + java.net.URLDecoder.decode(h.getValue(), "UTF-8"));
				//
				// }

				// 取得回傳資訊
				// returnCode 狀態碼
				// returnDesc 錯誤回傳狀態
				Header[] headersreturnCode = method.getResponseHeaders("returnCode");
				Header[] headersreturnDesc = method.getResponseHeaders("returnDesc");

				String returnCode = headersreturnCode[0].getValue();
				// //System.out.println("returnCode1 : " + returnCode);
				String returnDesc = java.net.URLDecoder.decode(headersreturnDesc[0].getValue(), "UTF-8");
				// //System.out.println("returnDesc2 : " + returnDesc);

				// 將 returnCode 及 returnDesc 放進 returnMap
				returnMap.put("returnCode", returnCode);
				returnMap.put("returnDesc", returnDesc);

				// body
				// String body = method.getResponseBodyAsString();
				// //System.out.println(new String(body.getBytes("UTF-8")));
				// JSONObject jsonObject = new JSONObject(body);
				// Iterator it = jsonObject.sortedKeys();
				// while (it.hasNext())
				// {
				// String key = (String) it.next();
				// String value = new
				// String(jsonObject.getString(key).getBytes(), "UTF-8");
				// //System.out.println("Key=" + key + ", value=" + value);
				// }
			}
			else
			{
				// System.out.println("Error");
			}
		}
		catch (HttpException e)
		{
			// TODO 自動產生 catch 區塊
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO 自動產生 catch 區塊
			e.printStackTrace();
		}
		finally
		{
			// ** 無論如何都必須釋放連接.
			method.releaseConnection();
		}
		return returnMap;

	}

}
