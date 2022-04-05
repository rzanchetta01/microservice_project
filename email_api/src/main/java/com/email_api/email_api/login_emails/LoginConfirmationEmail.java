package com.email_api.email_api.login_emails;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

@NoArgsConstructor
@Service
public class LoginConfirmationEmail {

    public void LoginConfirmationSendEmail(String userEmail, String emailTitle, String userName) throws MessagingException, IOException {
        sendmail(userEmail, emailTitle, userName);
    }

    private void sendmail(String userEmail, String emailTitle, String userName) throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("email@gmail.com", "12345");
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("digozanchetta@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
        msg.setSubject(emailTitle);
        msg.setContent("SEND MAIL", "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("Now   " + userName + ", have fun", "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        msg.setContent(multipart);
        Transport.send(msg);
    }


}