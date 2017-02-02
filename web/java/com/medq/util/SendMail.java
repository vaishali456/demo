package com.medq.util;

import java.util.Properties;
import javax.mail.*;
import com.medq.dto.ContactUs;
import javax.mail.internet.*;

/**
 *
 * @author eshine-104
 */
public class SendMail {

    boolean flag = true;

    final String user = "refque.refque@gmail.com";
    final String password = "refque.refque123";

    public boolean sendMail(ContactUs contact, boolean isMailForMe) {

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

            if (true == isMailForMe) {
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(user));
                message.setSubject("RefQue Inquiry");
                message.setText("Hi,RefQue team\n"
                        + "some one has contacted you\n"
                        + "below are details\n"
                        + "Name :" + contact.getFirstName() + " " + contact.getLastName() + "\n"
                        + "Email :" + contact.getEmailID() + "\n"
                        + "Phone :" + contact.getPhoneNumber() + "\n"
                        + "Message :" + contact.getMessage());
            } else {
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(contact.getEmailID()));
                message.setSubject("RefQue Inquiry");
                message.setText("Thank you for getting in touch!\n"
                        + "We appreciate you contacting us about [Contact Reason]. We try to respond as soon as possible, so one of our Customer Service colleagues will get back to you within a few hours.\n"
                        + "Have a great day ahead!");
            }

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
