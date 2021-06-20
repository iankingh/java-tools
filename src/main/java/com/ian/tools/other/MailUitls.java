package com.ian.tools.other;

import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 邮件发送工具类 MailUitls
 */
@Slf4j
public class MailUitls {

    /**
     * 发送邮件的方法
     * 
     * @param to   :收件人
     * @param code :激活码
     */
    public static void sendMail(String to, String code) {

        /**
         * 1.获得一个Session对象. 获得连接对象 2.创建一个代表邮件的对象Message. 3.发送邮件Transport
         */
        Properties props = new Properties();
        props.setProperty("mail.host", "localhost");
        Session session = Session.getInstance(props, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("service@shop.com", "111");
            }

        });
        // 2.创建邮件对象:
        Message message = new MimeMessage(session);
        // 设置发件人:
        try {
            message.setFrom(new InternetAddress("service@shop.com"));
            // 设置收件人:
            message.addRecipient(RecipientType.TO, new InternetAddress(to));
            // 抄送 CC 密送BCC
            // 设置标题
            message.setSubject("来自官方激活邮件");
            // 设置邮件正文:
            message.setContent(
                    "<h1>官方激活邮件!点下面链接完成激活操作!</h1><h3><a href='http://192.168.24.162:8080/shop/user_active.action?code="
                            + code + "'>http://192.168.24.162:8080/shop/user_active.action?code=" + code + "</a></h3>",
                    "text/html;charset=UTF-8");
            // 3.发送邮件:
            Transport.send(message);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

}
