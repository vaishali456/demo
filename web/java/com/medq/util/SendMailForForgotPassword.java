package com.medq.util;

import com.medq.dto.ContactUs;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author eshine-104
 */
public class SendMailForForgotPassword {

    boolean flag = true;
    final String user = "refque.refque@gmail.com";
    final String password = "refque.refque123";

    public void sendMail(String email, String randamCode, String baseURL) {

        //Get the session object
        Properties props = new Properties();
        props.put("mail.smtp.user", user);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "25");
        props.put("mail.debug", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.EnableSSL.enable", "true");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallbac k", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("RefQue Support Forgot Password");
            message.setText("Reset user password :"+baseURL+"#/resetPassword?token=" + randamCode);
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
