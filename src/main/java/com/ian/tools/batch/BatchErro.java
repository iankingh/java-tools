package com.ian.tools.batch;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.PasswordAuthentication;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.message.Message;

/**
 * 如需執行必須先在 本專案中的 WEB-INF中的web.xml中做設定
 *
 */
public class BatchErro extends HttpServlet {

	int count = 0;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		Timer timer = new Timer();

		TimerTask task = new TimerTask() {
			int count = 0;

			public void run() {
				count++;

				// 要做的事
				System.out.println("This is Task" + count);
				System.out.println("工作排定的時間 = " + new Date(scheduledExecutionTime()));
				System.out.println("工作執行的時間 = " + new Date() + "\n");

				// // 寄email 動作 重這
				// String to = "akt51124@loaoa.com";
				// String subject = "密碼通知";
				// String ch_name = "peter1";
				// String passRandom = "111";
				// String messageText = "Hello! " + ch_name + " 請謹記此密碼: " +
				// passRandom + "\n" + " (已經啟用)";
				// Mail mail = new Mail();
				// mail.sendMail(to, subject, messageText);
				// // 到這
			}
		};

		Calendar c = Calendar.getInstance(); // 得到當前日期和時間
		c.set(Calendar.HOUR_OF_DAY, 0); // 把當前時間小時變成０
		c.set(Calendar.MINUTE, 0); // 把當前時間分鐘變成０
		c.set(Calendar.SECOND, 0); // 把當前時間秒數變成０
		c.set(Calendar.MILLISECOND, 0); // 把當前時間毫秒變成０
		timer.scheduleAtFixedRate(task, c.getTime(), 1 * 1000); // 設定時間(毫秒)

		// 網頁測試
		res.setContentType("text/html; charset=Big5");
		PrintWriter out = res.getWriter();

		out.println("<HTML>");
		out.println("<HEAD><TITLE>Hello World</TITLE></HEAD>");
		out.println("<BODY>");
		out.println("<BIG>Hello World , 世界你好 ! 排程器 BIG-5</BIG>");
		out.println("</BODY></HTML>");
	}
}

// email 要引入 java.mail.jar
class batchMail {
	// 設定傳送郵件:至收信人的Email信箱,Email主旨,Email內容
	public void sendMail(String to, String subject, String messageText) {

		try {
			// 設定使用SSL連線至 Gmail smtp Server
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");

			// ●設定 gmail 的帳號 & 密碼 (將藉由你的Gmail來傳送Email)
			// ●須將myGmail的【安全性較低的應用程式存取權】打開
			final String myGmail = "ianaa10716@gmail.com";
			final String myGmail_password = "ian398075";
			Session session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(myGmail, myGmail_password);
				}
			});

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myGmail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// 設定信中的主旨
			message.setSubject(subject);
			// 設定信中的內容
			// message.setText(messageText);
			message.setContent(messageText, "text/html; charset=UTF-8");

			Transport.send(message);
			System.out.println("傳送成功!");
		} catch (MessagingException e) {
			System.out.println("傳送失敗!");
			e.printStackTrace();
		}
	}
}