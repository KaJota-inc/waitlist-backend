package com.kajota.kajota_webpage.entities;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
@Slf4j
@Data
public class EmailSender {

    private final String smtpHost;
    private final String smtpPort;
    private final String username;
    private final String password;

    private TemplateEngine templateEngine;

    @Autowired
    public EmailSender(
            @Value("${spring.mail.host}") String smtpHost,
            @Value("${spring.mail.port}") String smtpPort,
            @Value("${spring.mail.username}") String username,
            @Value("${spring.mail.password}") String password
    ) {
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.username = username;
        this.password = password;
    }


    Properties getMailServerProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);
        return properties;
    }

    public void sendEmail(String toAddress, String subject, String attachmentPath) {
        Properties properties = getMailServerProperties();

        // Create a session with an authenticator
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                log.info("username: {}",username);
                log.info("password: {}", password);
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Context context = new Context();
            context.setVariables(Map.of(
                    "instagramLink","https://www.instagram.com/kajota.io/?utm_source=ig_web_button_share_sheet",
                    "linkedinLink","https://www.linkedin.com/company/kajota-io")
            );
            String messageBody = templateEngine.process("EmailTemplate",context);
            // Create a new email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
            message.setSubject(subject);

            // Create a multipart message
            Multipart multipart = new MimeMultipart();
            // Set the email text message part
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setContent(messageBody, "text/html; charset=utf-8");
            multipart.addBodyPart(textBodyPart);

            //Inline Image resources
            // Prepare inline images map
            Map<String, String> inlineImages = new HashMap<>();
            inlineImages.put("logoImage", "src/main/resources/static/images/hero-img.png");
            inlineImages.put("instagram", "src/main/resources/static/images/Instragram.svg");
            inlineImages.put("kajotaLogo", "src/main/resources/static/images/kajota-logo.svg");
            inlineImages.put("kajotaWhiteLogo", "src/main/resources/static/images/kajota-white-logo.svg");
            inlineImages.put("linkLight", "src/main/resources/static/images/link_light.svg");
            inlineImages.put("linkedin", "src/main/resources/static/images/LinkedIn.svg");
            inlineImages.put("threeDot", "src/main/resources/static/images/three_dot.svg");
            for (Map.Entry<String, String> entry : inlineImages.entrySet()) {
                MimeBodyPart imagePart = new MimeBodyPart();
                DataSource fds = new FileDataSource(entry.getValue());
                imagePart.setDataHandler(new DataHandler(fds));
                imagePart.setHeader("Content-ID", "<" + entry.getKey() + ">");
                imagePart.setDisposition(MimeBodyPart.INLINE);
                multipart.addBodyPart(imagePart);
            }

            // Set the email attachment part
            if (attachmentPath != null && !attachmentPath.isEmpty()) {
                MimeBodyPart attachmentBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(attachmentPath);
                attachmentBodyPart.setDataHandler(new DataHandler(source));
                attachmentBodyPart.setFileName(source.getName());
                multipart.addBodyPart(attachmentBodyPart);
            }

            // Set the complete message parts
            message.setContent(multipart);

            // Send the email
            Transport.send(message);

            log.info("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}