package com.kajota.kajota_webpage.services;

import com.kajota.kajota_webpage.services.interfaces.IEmailSender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Map;
import java.util.Properties;


@Service
@Slf4j
@Data
@AllArgsConstructor
public class EmailSender implements IEmailSender {

    private final String smtpHost;
    private final String smtpPort;
    private final String username;
    private final String password;

    @Autowired
    private TemplateEngine templateEngine;

    private JavaMailSender emailSender;

    private final Environment environment;

    @Autowired
    public EmailSender(
            @Value("${spring.mail.host}") String smtpHost,
            @Value("${spring.mail.port}") String smtpPort,
            @Value("${spring.mail.username}") String username,
            @Value("${spring.mail.password}") String password,
            Environment environment
    ) {
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.username = username;
        this.password = password;
        this.environment = environment;
    }


    Properties getMailServerProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);
        return properties;
    }

    public void subscribeUser(String email) {
        try{
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth("kajota", environment.getProperty("mailchimp.api-key",""));
            headers.set("Content-Type", "application/json");

            String payload = "{ \"email_address\": \"" + email + "\", \"status\": \"subscribed\" }";

            HttpEntity<String> request = new HttpEntity<>(payload, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(environment.getProperty("mailchimp.api-url",""), request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("User subscribed successfully.");
            } else {
                System.out.println("Error subscribing user: " + response.getBody());
            }
        }catch(Exception ex){
            throw new RuntimeException("Something went wrong:"+ ex.getMessage());
        }

    }

    @Override
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
            String text = templateEngine.process("emailTemplate", context);
            // Create a new email message
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress("hello@kajota.io"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
            message.setSubject(subject);

            // Create a multipart message
            Multipart multipart = new MimeMultipart();
            // Set the email text message part
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setContent(text, "text/html; charset=UTF-8");
            multipart.addBodyPart(textBodyPart);

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
