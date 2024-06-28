package com.unibuc.tripfinity.service;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {

    public void sendMail(String recipient) {
        recipient = recipient;
        String sender = "tripfinityapplication@gmail.com";
        String host = "smtp.gmail.com";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        String username = "tripfinityapplication@gmail.com"; // Your Gmail email
        String password = "cncbltfvvugjcatp"; // Your Gmail password or app-specific password

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From field: adding sender's email to from field.
            message.setFrom(new InternetAddress(sender));

            // Set To field: adding recipient's email to from field.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            // Setarea subiectului
            message.setSubject("Your hotel reservation has been confirmed");

            // Setarea mesajului.
            MimeBodyPart textPart = new MimeBodyPart();
            String htmlContent = "<img src='cid:image'><br/><h1>Your hotel reservation has been confirmed!</h1><br/><h1>Hope you have a great stay!</h1>";
            textPart.setContent(htmlContent, "text/html");

            // Crearea părții cu imaginea
            MimeBodyPart imagePart = new MimeBodyPart();
            String imagePath = "src/main/resources/static/city_logo_night.jpg"; // Replace with your image path
            DataSource fds = new FileDataSource(imagePath);
            imagePart.setDataHandler(new DataHandler(fds));
            imagePart.setHeader("Content-ID", "<image>");

            // Crearea mesajului
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(imagePart);

            // Setarea conținutului
            message.setContent(multipart);

            // Trimiterea mail-ului
            Transport.send(message);
            System.out.println("Mail successfully sent");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }


    public void sendMailForFlights(String recipient) {
        recipient = recipient;
        String sender = "tripfinityapplication@gmail.com";
        String host = "smtp.gmail.com";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        String username = "tripfinityapplication@gmail.com"; // Your Gmail email
        String password = "cncbltfvvugjcatp"; // Your Gmail password or app-specific password

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Your flight reservation has been confirmed");

            MimeBodyPart textPart = new MimeBodyPart();
            String htmlContent = "<img src='cid:image'><br/><h3>Your flight reservation has been confirmed!</h3><br/><h3>Hope you have a great flight!</h3>";
            textPart.setContent(htmlContent, "text/html");

            MimeBodyPart imagePart = new MimeBodyPart();
            String imagePath = "src/main/resources/static/logo_plane.jpg"; // Replace with your image path
            DataSource fds = new FileDataSource(imagePath);
            imagePart.setDataHandler(new DataHandler(fds));
            imagePart.setHeader("Content-ID", "<image>");

            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(imagePart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Mail successfully sent");

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }


    public void sendMailForSignup(String recipient) {
        recipient = recipient;
        String sender = "tripfinityapplication@gmail.com";
        String host = "smtp.gmail.com";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        String username = "tripfinityapplication@gmail.com"; // Your Gmail email
        String password = "cncbltfvvugjcatp"; // Your Gmail password or app-specific password

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Welcome to Tripfinity!");

            MimeBodyPart textPart = new MimeBodyPart();
            String htmlContent = "<img src='cid:image'><br/><h3>Welcome to Tripfinity!</h3><br/><h3>Here you can make your dream vacation come true!</h3>";
            textPart.setContent(htmlContent, "text/html");

            MimeBodyPart imagePart = new MimeBodyPart();
            String imagePath = "src/main/resources/static/logo_inf2.jpg"; // Replace with your image path
            DataSource fds = new FileDataSource(imagePath);
            imagePart.setDataHandler(new DataHandler(fds));
            imagePart.setHeader("Content-ID", "<image>");

            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(imagePart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Mail successfully sent");

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }



}
