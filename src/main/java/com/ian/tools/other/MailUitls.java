package com.ian.tools.other;
/**
 * 
 * 邮件发送工具类 MailUitls
 */
public class MailUitls {

    /**
     * 发送邮件的方法
     * 
     * @param to   :收件人
     * @param code :激活码
     */
    public static void sendMail(String to, String code) {
        /**
         * 1.获得一个Session对象. 2.创建一个代表邮件的对象Message. 3.发送邮件Transport
         */
        // 1.获得连接对象
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

    public static void main(String[] args) {
        sendMail("aaa@shop.com", "11111111111111");
    }
}

// It's easy to send HTML mail with JavaMail. Simply set the content type to
// "text/html".
class SimpleMail {
    public static void main(String[] args) throws Exception {
        System.out.println("Sending mail...");
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", "smtp.mymailserver.com");
        props.setProperty("mail.user", "myuser");
        props.setProperty("mail.password", "mypwd");

        Session mailSession = Session.getDefaultInstance(props, null);
        mailSession.setDebug(true);
        Transport transport = mailSession.getTransport();

        MimeMessage message = new MimeMessage(mailSession);
        message.setSubject("HTML  mail with images");
        message.setFrom(new InternetAddress("me@sender.com"));
        message.setContent("<h1>Hello world</h1>", "text/html");
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("you@receiver.com"));

        transport.connect();
        transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
        transport.close();
    }
}

// One approach to include images in the mail body is to use the IMG tag and
// make the images available on a server.
class SimpleMail1 {
    public static void main(String[] args) throws Exception {
        System.out.println("Sending mail...");
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", "smtp.mymailserver.com");
        props.setProperty("mail.user", "myuser");
        props.setProperty("mail.password", "mypwd");

        Session mailSession = Session.getDefaultInstance(props, null);
        mailSession.setDebug(true);
        Transport transport = mailSession.getTransport();

        MimeMessage message = new MimeMessage(mailSession);
        message.setSubject("HTML  mail with images");
        message.setFrom(new InternetAddress("me@sender.com"));
        message.setContent("<h1>This is a test</h1>" + "<img src=\"http://www.rgagnon.com/images/jht.gif\">",
                "text/html");
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("you@receiver.com"));

        transport.connect();
        transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
        transport.close();
    }
}

// The browser accesses these images just as if it were displaying an image in a
// Web page. Unfortunately, spammers have used this mechanism as a sneaky way to
// record who visits their site (and mark your email as valid). To protect your
// privacy, many Web-based (and other) email clients don't display images in
// HTML emails.
// An alternative to placing absolute URLs to images in your HTML is to include
// the images as attachments to the email. The HTML can reference the image in
// an attachment by using the protocol prefix cid: plus the content-id of the
// attachment.
class SimpleMail2 {
    public static void main(String[] args) throws Exception {
        System.out.println("Sending mail...");
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", "smtp.mymailserver.com");
        props.setProperty("mail.user", "myuser");
        props.setProperty("mail.password", "mypwd");

        Session mailSession = Session.getDefaultInstance(props, null);
        mailSession.setDebug(true);
        Transport transport = mailSession.getTransport();

        MimeMessage message = new MimeMessage(mailSession);
        message.setSubject("HTML  mail with images");
        message.setFrom(new InternetAddress("me@sender.com"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("you@receiver.com"));

        //
        // This HTML mail have to 2 part, the BODY and the embedded image
        //
        MimeMultipart multipart = new MimeMultipart("related");

        // first part (the html)
        BodyPart messageBodyPart = new MimeBodyPart();
        String htmlText = "<H1>Hello</H1><img src=\"cid:image\">";
        messageBodyPart.setContent(htmlText, "text/html");

        // add it
        multipart.addBodyPart(messageBodyPart);

        // second part (the image)
        messageBodyPart = new MimeBodyPart();
        DataSource fds = new FileDataSource("C:\\images\\jht.gif");
        messageBodyPart.setDataHandler(new DataHandler(fds));
        messageBodyPart.setHeader("Content-ID", "<image>");

        // add it
        multipart.addBodyPart(messageBodyPart);

        // put everything together
        message.setContent(multipart);

        transport.connect();
        transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
        transport.close();
    }
}
