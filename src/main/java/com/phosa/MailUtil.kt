package com.phosa;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

/**
 * 邮箱工具类
 * <pre>
 *     {@code
 * public class Main {
 *     public static void main(String[] args) {
 *         String host = "smtp.example.com";
 *         String port = "587";
 *         String username = "your-email@example.com";
 *         String password = "your-email-password";
 *
 *         MailUtil mailUtil = new MailUtil(host, port, username, password);
 *
 *         try {
 *             mailUtil.sendEmail("recipient@example.com", "Test Subject", "Test Message");
 *             System.out.println("Email sent successfully.");
 *         } catch (MessagingException e) {
 *             e.printStackTrace();
 *         }
 *     }
 * }}
 * </pre>
 */
public class MailUtil {

    private final String host;
    private final String port;
    private final String username;
    private final String password;
    private Properties properties;

    public MailUtil(String host, String port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        initProperties();
    }

    private void initProperties() {
        properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
    }

    private Session createSession() {
        return Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    /**
     * 发送邮件
     * @param toAddress 收件人地址
     * @param subject 邮件主题
     * @param message 邮件内容
     * @throws MessagingException 邮件发送异常
     */
    public void sendEmail(String toAddress, String subject, String message) throws MessagingException {
        Session session = createSession();
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(username));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
        msg.setSubject(subject);
        msg.setText(message);
        Transport.send(msg);
    }
}
