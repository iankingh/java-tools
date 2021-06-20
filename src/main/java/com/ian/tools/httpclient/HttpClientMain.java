package com.ian.tools.httpclient;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;

/**
 * 
 */
public class HttpClientMain {

	public static void main(String[] args) {
		// 建立 HttpClientPost 實體 用於URL連線
		HttpClientPost helper = new HttpClientPost();
		
		String customerId = "D174869911";
		String loginAccount = "CHRISL";
		String pwd = "B1VMqIqbbQqxAgQUjS/lbjvt3K6BszhuN0e7DUgCajUS41a3YprpI/z6WAFZmbjd0EgsGuBFedfwgO1PIkHLNHpZkTmvm4OjWIpNNGJX1jD9D+RsQKzM9P9k3mdiZwH6NZyIsKmv38Y7XT6O8jVZo8m3m/NhbBkJxdZXP0Fzf/5JM52IWTpTVIzq1f9ZhTKCDEFtvAGjsPp7oMbCTRHdDVj8L8puZyMHn/zSnGxSKAOOh9KrIA7k6dpaQBaRthGsMzpKkzLEQz+OyUt847Vj4kfUzpGUq9orVeQSgcFD4bitXvvb9SWY7KbW61cUI690CpROlbF3xSzvHeI4iJSzSg==";
		String loginIp = "192.168.254.132";

		String URL_PATHLOGIN = "https://google/";
		// 建立 傳輸資料
		NameValuePair[] data = { new NameValuePair("cid", customerId), new NameValuePair("uid", loginAccount),
				new NameValuePair("pwd", pwd), new NameValuePair("sys", "BFNB"), new NameValuePair("log", "1"),
				new NameValuePair("cip", loginIp) };
		// 建立取得回傳變數 Map
		Map<String, String> validat = new HashMap<String, String>();
		// 取得回傳資訊
		validat = helper.dopost(data, URL_PATHLOGIN);
		// 得到回傳 代碼
		String returnCode = validat.get("returnCode");
		
		System.out.println(" returnCode :  " + returnCode);

	}

}
