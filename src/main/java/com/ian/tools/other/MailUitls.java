package com.ian.tools.other;

import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 邮件发送工具类 MailUitls
 * email 要引入 java.mail.jar
 */
@Slf4j
public class MailUitls {

    // /**
    //  * 发送邮件的方法
    //  * 
    //  * @param to   :收件人
    //  * @param code :激活码
    //  */
    // public static void sendMail(String to, String code) {

    //     /**
    //      * 1.获得一个Session对象. 获得连接对象 2.创建一个代表邮件的对象Message. 3.发送邮件Transport
    //      */
    //     Properties props = new Properties();
    //     props.setProperty("mail.host", "localhost");
    //     Session session = Session.getInstance(props, new Authenticator() {

    //         @Override
    //         protected PasswordAuthentication getPasswordAuthentication() {
    //             return new PasswordAuthentication("service@shop.com", "111");
    //         }

    //     });
    //     // 2.创建邮件对象:
    //     Message message = new MimeMessage(session);
    //     // 设置发件人:
    //     try {
    //         message.setFrom(new InternetAddress("service@shop.com"));
    //         // 设置收件人:
    //         message.addRecipient(RecipientType.TO, new InternetAddress(to));
    //         // 抄送 CC 密送BCC
    //         // 设置标题
    //         message.setSubject("来自官方激活邮件");
    //         // 设置邮件正文:
    //         message.setContent(
    //                 "<h1>官方激活邮件!点下面链接完成激活操作!</h1><h3><a href='http://192.168.24.162:8080/shop/user_active.action?code="
    //                         + code + "'>http://192.168.24.162:8080/shop/user_active.action?code=" + code + "</a></h3>",
    //                 "text/html;charset=UTF-8");
    //         // 3.发送邮件:
    //         Transport.send(message);
    //     } catch (AddressException e) {
    //         log.error(e, e);
    //     } catch (MessagingException e) {
    //         log.error(e, e);
    //     }

    // }

    // // 設定傳送郵件:至收信人的Email信箱,Email主旨,Email內容
	// public void sendMail(String to, String subject, String messageText) {

	// 	try {
	// 		// 設定使用SSL連線至 Gmail smtp Server
	// 		Properties props = new Properties();
	// 		props.put("mail.smtp.host", "smtp.gmail.com");
	// 		props.put("mail.smtp.socketFactory.port", "465");
	// 		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	// 		props.put("mail.smtp.auth", "true");
	// 		props.put("mail.smtp.port", "465");

	// 		// ●設定 gmail 的帳號 & 密碼 (將藉由你的Gmail來傳送Email)
	// 		// ●須將myGmail的【安全性較低的應用程式存取權】打開
	// 		final String myGmail = "ianaa10716@gmail.com";
	// 		final String myGmail_password = "ian398075";
	// 		Session session = Session.getInstance(props, new Authenticator() {
	// 			protected PasswordAuthentication getPasswordAuthentication() {
	// 				return new PasswordAuthentication(myGmail, myGmail_password);
	// 			}
	// 		});

	// 		Message message = new MimeMessage(session);
	// 		message.setFrom(new InternetAddress(myGmail));
	// 		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

	// 		// 設定信中的主旨
	// 		message.setSubject(subject);
	// 		// 設定信中的內容
	// 		// message.setText(messageText);
	// 		message.setContent(messageText, "text/html; charset=UTF-8");

	// 		Transport.send(message);
    //         log.debug("傳送成功!");
	// 	} catch (MessagingException e) {
    //         log.debug("傳送失敗!");
	// 		e.printStackTrace();
	// 	}
	// }

}
