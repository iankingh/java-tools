package com.ian.tools.httpclient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// import org.apache.commons.httpclient.Header;
// import org.apache.commons.httpclient.HttpClient;
// import org.apache.commons.httpclient.HttpException;
// import org.apache.commons.httpclient.HttpStatus;
// import org.apache.commons.httpclient.NameValuePair;
// import org.apache.commons.httpclient.methods.PostMethod;
// import org.apache.commons.httpclient.params.HttpClientParams;
// import org.apache.log4j.Logger;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.security.cert.CertificateException;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.google.gson.Gson;

/**
 * 
 * 本類別詳細說明。
 * <p/>
 * Package: com.Ian.bfnb.login <br>
 * File Name: HttpClientPost <br>
 * <p/>
 * Purpose: 與 登入 API 連線程式 <br>
 * 
 * @ClassName: com.Ian.bfnb.util.HttpClientPost
 * @Description: 使用 post 連線
 * @return : HashMap<String, String> returnCode 狀態碼 returnDesc 錯誤回傳狀態
 * @Copyright : Copyright (c) Ian Corp. 2017. All Rights Reserved.
 * @Company: FSTOP Team.
 * @author ianhuang
 * @version 1.0, 2017/11/15
 * 
 * 
 *          // 建立 HttpClientPost 實體 用於URL連線 HttpClientPost helper = new
 *          HttpClientPost(); // 建立 傳輸資料 NameValuePair[] data = { new
 *          NameValuePair("cid", customerId), new NameValuePair("uid",
 *          loginAccount), new NameValuePair("pwd", pwd), new
 *          NameValuePair("sys", "BFNB"), new NameValuePair("log", "1"), new
 *          NameValuePair("cip", loginIp) };
 * 
 *          // 取得連線 URL String URL_PATHLOGIN =
 *          ConnectionUrl.getResource("Connection.properties", "URL_PATHLOGIN");
 * 
 *          // 取得回傳資訊 validat = helper.dopost(data, URL_PATHLOGIN);
 * 
 */

public class HttpClientPost {

// 	private Logger logger = Logger.getLogger(this.getClass());

// 	public HashMap<String, String> dopost(NameValuePair[] data, String URL_PATH) {

// 		HashMap<String, String> returnMap = new HashMap<String, String>();
// //
// //		// // 測試時打沒認證過的https
// //		try {
// //
// //			SSLContext ctx = SSLContext.getInstance("TLS");
// //			ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
// //			SSLContext.setDefault(ctx);
// //
// //		} catch (Exception ex) {
// //			logger.error(" catch錯誤訊息 :" + ex, ex);
// //		}
// //		//

// 		// 建立HttpClient實體
// 		HttpClientParams p = new HttpClientParams();
// 		p.setContentCharset("UTF-8");
// 		HttpClient client = new HttpClient(p);

// 		// 建立GetMethod實體, 並指派網址, PostMethod會自動處理該網轉址動作,
// 		PostMethod method = new PostMethod(URL_PATH); // 不需自動轉址
// 		method.setFollowRedirects(false);
// 		method.setRequestBody(data);

// 		try {
// 			// 連線超時就TimeOut
// 			client.getHttpConnectionManager().getParams().setConnectionTimeout(600000);
// 			// 讀取超時就TimeOut
// 			client.getHttpConnectionManager().getParams().setSoTimeout(600000);
// 			// 返回狀態值.
// 			int statusCode = client.executeMethod(method);

// 			if (statusCode == HttpStatus.SC_OK) {

// 				// 取得回傳資訊. 測試用
// 				// logger.debug("取得回傳資訊. 測試用");
// 				// Header[] header = method.getResponseHeaders();
// 				// for (Header h : header) {
// 				// logger.debug(h.getName() + ", " + new
// 				// String(h.getValue().getBytes("UTF-8")));
// 				// logger.debug(h.getName() + ", " + h.getValue());
// 				// logger.debug(h.getName() + ", " +
// 				// java.net.URLDecoder.decode(h.getValue(), "UTF-8"));
// 				// }
// 				//
// 				// logger.debug("取得回傳資訊. 測試用END");

// 				// returnCode 狀態碼
// 				// returnDesc 錯誤回傳狀態
// 				Header[] headersreturnCode = method.getResponseHeaders("returnCode");
// 				Header[] headersreturnDesc = method.getResponseHeaders("returnDesc");

// 				// 轉字串
// 				String returnCode = headersreturnCode[0].getValue();
// 				String returnDesc = java.net.URLDecoder.decode(headersreturnDesc[0].getValue(), "UTF-8");

// 				// 將 returnCode(代碼) 及 returnDesc(中文) 放進 returnMap
// 				returnMap.put("returnCode", returnCode);
// 				returnMap.put("returnDesc", returnDesc);

// 			}

// 		} catch (HttpException e) {
// 			// TODO 自動產生 catch 區塊
// 			logger.error(" catch錯誤訊息 HttpException :" + e, e);
// 		} catch (IOException e) {
// 			// TODO 自動產生 catch 區塊
// 			logger.error(" catch錯誤訊息 IOException :" + e, e);
// 		} finally {
// 			// ** 無論如何都必須釋放連接.
// 			method.releaseConnection();
// 		}
// 		return returnMap;

// 	}

// 	public Map<String, String> doJsonPost(NameValuePair[] data, String URL_PATH) {

// 		try {

// 			// SSLContext ctx = SSLContext.getInstance("TLS");
// 			// ctx.init(new KeyManager[0], new TrustManager[]{new
// 			// DefaultTrustManager()}, new SecureRandom());
// 			// SSLContext.setDefault(ctx);

// 			HashMap<String, String> returnMap = new HashMap<String, String>();

// 			// 建立HttpClient實體
// 			HttpClientParams p = new HttpClientParams();
// 			p.setContentCharset("UTF-8");
// 			HttpClient client = new HttpClient(p);

// 			// 建立GetMethod實體, 並指派網址, PostMethod會自動處理該網轉址動作,
// 			PostMethod method = new PostMethod(URL_PATH); // 不需自動轉址

// 			method.setFollowRedirects(false);
// 			method.setRequestBody(data);

// 			try {
// 				// 連線超時就TimeOut
// 				client.getHttpConnectionManager().getParams().setConnectionTimeout(600000);
// 				// 讀取超時就TimeOut
// 				client.getHttpConnectionManager().getParams().setSoTimeout(600000);
// 				// 返回狀態值.
// 				int statusCode = client.executeMethod(method);

// 				if (statusCode == HttpStatus.SC_OK) {

// 					String res = method.getResponseBodyAsString();
// 					logger.debug("res:" + res);
// 					Gson gson = new Gson();
// 					java.lang.reflect.Type stringStringMap = new com.google.gson.reflect.TypeToken<Map<String, String>>() {
// 					}.getType();
// 					Map<String, String> map = gson.fromJson(res, stringStringMap);
// 					if (map == null) {
// 						map = new HashMap<String, String>();
// 					}
// 					Header[] header = method.getResponseHeaders();
// 					for (Header h : header) {
// 						logger.debug(h.getName() + ", " + new String(h.getValue().getBytes("UTF-8")));
// 						logger.debug(h.getName() + ", " + h.getValue());
// 						logger.debug(h.getName() + ", " + java.net.URLDecoder.decode(h.getValue(), "UTF-8"));
// 						if ("MNB_rtnCode".equals(h.getName())) { // 約定轉帳 sync
// 																	// ...
// 							map.put("MNB_rtnCode", h.getValue());
// 						}
// 					}
// 					return map;
// 				}

// 			} catch (HttpException e) {
// 				logger.error(" catch錯誤訊息 HttpException :" + e, e);
// 			} catch (IOException e) {
// 				logger.error(" catch錯誤訊息 IOException :" + e, e);
// 			} finally {
// 				// ** 無論如何都必須釋放連接.
// 				method.releaseConnection();
// 			}
// 			return returnMap;

// 		} catch (Exception ex) {
// 			logger.error(" catch錯誤訊息 :" + ex, ex);
// 		}
// 		return null;
// 	}

//	// /**
//	// * for 測試時打沒認證過的https
//	// */
//	private static class DefaultTrustManager implements X509TrustManager {
//
//		@Override
//		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
//		}
//
//		@Override
//		public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
//		}
//
//		@Override
//		public X509Certificate[] getAcceptedIssuers() {
//			return null;
//		}
//	}

}
