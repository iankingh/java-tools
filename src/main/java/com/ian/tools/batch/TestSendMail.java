package com.ian.tools.batch;

import com.ian.tools.other.JspToHtml;

public class TestSendMail {

	public static void main(String[] args) {
		String to = "ianaa10716@gmail.com";
		String subject = "網頁測試";
		String ch_name = "peter1";
		String passRandom = "111";
		System.out.println(JspToHtml.TwTransfersToHtmlFile("大*歌", "2018/02/21 10:15:46 ", "1111222233334444",
				"2018/02/22", "75,000", "交易成功", "1111111***111", "1111111***222", "006", "XXX銀行", "周＊興", "0"));
		System.out.println(JspToHtml.JspToHtmlFile("title", "paragraph"));

		String messageText = JspToHtml.JspToHtmlFile("登入通知", "大*歌SSSS");
		String messageText2 = JspToHtml.TwTransfersToHtmlFile("大*歌", "2018/02/21 10:15:46 ", "1111222233334444",
				"2018/02/22", "75,000", "交易成功", "1111111***111", "1111111***222", "006", "XXX銀行", "周＊興", "0");
		Mail mail = new Mail();

		mail.sendMail(to, subject, messageText);

	}

}
