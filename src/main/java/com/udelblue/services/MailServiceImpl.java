package com.udelblue.services;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Value("${app.smtp}")
    private String smtpAddress;

    @Value("${app.smtpfrom}")
    private String smtpfrom;

    @Override
    public Object sendMail(String toReceiver, String link) {

        // Set required configs
        String from = smtpfrom;
        String to = toReceiver;
        String host = smtpAddress;
        String port = "587";
        String user = "rizwanshakoor50@gmail.com";
        String password = "rizwan.bsse2356";

        // Set system properties
        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", port);
        properties.setProperty("mail.smtp.user", user);
        properties.setProperty("mail.smtp.password", password);
        properties.setProperty("mail.smtp.starttls.enable", "true");

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set from email address
            message.setFrom(new InternetAddress(from, "TechPool"));
            // Set the recipient email address
            message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
            // Set email subject
            message.setSubject("Mail Subject");
            // Set email body
            message.setText(link);
            // Set configs for sending email
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, password);
            // Send email
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            System.out.println("done");
            return "Email Sent! Check Inbox!";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
