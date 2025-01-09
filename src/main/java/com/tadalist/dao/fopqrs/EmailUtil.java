package com.tadalist.dao.fopqrs;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.logging.*;

public class EmailUtil {

    static {
        Logger logger = Logger.getLogger("javax.mail");
        logger.setLevel(Level.SEVERE); // Suppress warnings
    }

    public static void sendEmail(String recipient, String subject, String body) {
        final String senderEmail = "ntsyabeatrisya@gmail.com";
        final String senderPassword = "jlow sbuh dthm zaon";

        // SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Create a session with the SMTP server
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(body);

            // Send the email
            Transport.send(message);
            System.out.println("Email sent successfully to: " + recipient);

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }
}
