package com.phosa

import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

/**
 * 邮箱工具类
 * <pre>
 * `public class Main {
 * public static void main(String[] args) {
 * String host = "smtp.example.com";
 * String port = "587";
 * String username = "your-email@example.com";
 * String password = "your-email-password";
 *
 * MailUtil mailUtil = new MailUtil(host, port, username, password);
 *
 * try {
 * mailUtil.sendEmail("recipient@example.com", "Test Subject", "Test Message");
 * System.out.println("Email sent successfully.");
 * } catch (MessagingException e) {
 * e.printStackTrace();
 * }
 * }
 * }`
</pre> *
 */
object MailUtil {
    fun getMailClient(host: String, port: String, username: String, password:String): MailClient {
        return MailClient(host , port , username , password)
    }
    class MailClient(
        private val host: String?,
        private val port: String?,
        private val username: String,
        private val password: String,
        private var properties: Properties? = null
    ) {
        init {
            initProperties()
        }
        private fun initProperties() {
            properties = Properties()
            properties!!.put("mail.smtp.host", host)
            properties!!.put("mail.smtp.port", port)
            properties!!.put("mail.smtp.auth", "true")
            properties!!.put("mail.smtp.starttls.enable", "true")
        }
        fun createSession(): Session {
            return Session.getInstance(properties, object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(username, password)
                }
            })
        }

        /**
         * 发送邮件
         * @param toAddress 收件人地址
         * @param subject 邮件主题
         * @param message 邮件内容
         * @throws MessagingException 邮件发送异常
         */
        @Throws(MessagingException::class)
        fun sendEmail(toAddress: String, subject: String?, message: String?) {
            val session = createSession()
            val msg: Message = MimeMessage(session)
            msg.setFrom(InternetAddress(username))
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress))
            msg.subject = subject
            msg.setText(message)
            Transport.send(msg)
        }
    }










}
