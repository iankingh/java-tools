package com.ian.tools.other;

import java.io.*;

import com.ian.tools.propertiesread.ConnectionUrl;


 /**
 * 本類別詳細說明。
 * <p/>
 * Package: com.ian.utils <br>
 * File Name: JspToHtml <br>
 * <p/>
 * Purpose: <br>
 * 
 * @ClassName: com.ian.RedisUtils
 * @Description: 把動態網頁轉換成靜態網頁
 * @Company: Team.
 * @author Ian
 * @version 1.0, Aug 29, 2019
 */
public class JspToHtml {

	/**
	 * 
	 * 根據模板生成靜態頁面
	 * 
	 * @param filePath html 位址
	 * @param title 標題
	 * @param paragraph 內文
	 * @return String html
	 */
	public static String JspToHtmlFile( String title, String paragraph)
	{

		String str = "";
		InputStream input = null;
		InputStreamReader inputReader = null;
		try
		{
			String tempStr = "";
			input = ConnectionUrl.class.getClassLoader().getResourceAsStream("mb.html");
			inputReader = new InputStreamReader(input, "UTF-8");
			BufferedReader br = new BufferedReader(inputReader);
			while ((tempStr = br.readLine()) != null)
				str = str + tempStr;
			input.close();
			inputReader.close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			return str;
		}
		finally
		{
			// ** 無論如何都必須釋放連接.
			try
			{
				if (input != null)
				{
					input.close();
				}
				if (inputReader != null)
				{
					inputReader.close();
				}
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
			}
		}

		// 替換掉模塊中的地方
		str = str.replace("###title###", title);
		str = str.replace("###paragraph###", paragraph);

		return str;
	}

	/**
	 * 
	 * @param userNameChange   session.USERNAMECHANGE
	 * @param nowTime
	 * @param BatchNo
	 * @param InDate
	 * @param TotalAmount
	 * @param StatusDesc
	 * @param TransferOutAccnt
	 * @param TransferInAccnt
	 * @param TransferInBankID
	 * @param TransferInBankName
	 * @param TransferInName
	 * @param NotifyFee
	 * @return
	 */

	public static String TwTransfersToHtmlFile(String userNameChange, String nowTime, String BatchNo, String InDate,
			String TotalAmount, String StatusDesc, String TransferOutAccnt, String TransferInAccnt,
			String TransferInBankID, String TransferInBankName, String TransferInName, String NotifyFee)

	{

		String str = "";
		InputStream input = null;
		InputStreamReader inputReader = null;
		try {
			String tempStr = "";
			input = JspToHtml.class.getClassLoader().getResourceAsStream("detail.html");
			inputReader = new InputStreamReader(input, "UTF-8");
			BufferedReader br = new BufferedReader(inputReader);
			while ((tempStr = br.readLine()) != null)
				str = str + tempStr;
			input.close();
			inputReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// logger.error("TwTransfersToHtmlFile :" + e, e);
		} finally {
			// ** 無論如何都必須釋放連接.
			try {
				if (input != null) {
					input.close();
				}
				if (inputReader != null) {
					inputReader.close();
				}
			} catch (IOException e) {
				// logger.error("TwTransfersToHtmlFile :" + e, e);

			}
		}

		// 替換掉模塊中的地方
		str = str.replace("###userNameChange###", userNameChange);
		str = str.replace("###nowTime###", nowTime);
		str = str.replace("###BatchNo###", BatchNo);
		str = str.replace("###InDate###", InDate);
		str = str.replace("###TotalAmount###", TotalAmount);
		str = str.replace("###StatusDesc###", StatusDesc);
		str = str.replace("###TransferOutAccnt###", TransferOutAccnt);
		str = str.replace("###TransferInAccnt###", TransferInAccnt);
		str = str.replace("###TransferInBankID###", TransferInBankID);
		str = str.replace("###TransferInBankName###", TransferInBankName);
		str = str.replace("###TransferInName###", TransferInName);
		str = str.replace("###NotifyFee###", NotifyFee);

		return str;
	}

	/**
	 * 测试main 函数
	 * 
	 * @param arg
	 */
	public static void main(String[] arg) {

		System.out.println(TwTransfersToHtmlFile("大*歌", "2018/02/21 10:15:46 ", "1111222233334444", "2018/02/22",
				"75,000", "交易成功", "1111111***111", "1111111***222", "006", "XXX銀行", "周＊興", "0"));

	}
}