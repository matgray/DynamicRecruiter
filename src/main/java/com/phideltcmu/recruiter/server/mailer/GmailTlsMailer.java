package com.phideltcmu.recruiter.server.mailer;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class GmailTlsMailer {
    private String username;
    private String password;

    public void sendMail(String mailFrom, String mailTo,
                         String mailSubject, String mailText)
            throws Exception {

        Properties config = createConfiguration();

        //
        // Creates a mail session. We need to supply username and
        // password for Gmail authentication.
        //
        Session session = Session.getInstance(config, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        username,
                        password
                );
            }
        });

        //
        // Creates email message
        //
        Message message = new MimeMessage(session);
        message.setSentDate(new Date());
        message.setFrom(new InternetAddress(mailFrom));
        message.setRecipient(Message.RecipientType.TO,
                new InternetAddress(mailTo));
        message.setSubject(mailSubject);
        message.setText(mailText);

        //
        // Send a message
        //
        Transport.send(message);
    }

    private Properties createConfiguration() {
        return new Properties() {{
            put("mail.smtp.auth", "true");
            put("mail.smtp.host", "smtp.gmail.com");
            put("mail.smtp.port", "587");
            put("mail.smtp.starttls.enable", "true");
        }};
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
