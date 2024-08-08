package com.kajota.kajota_webpage.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;

import javax.mail.Session;
import javax.mail.Transport;
import javax.swing.*;

import java.util.Map;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = {
        "SMTP_HOST=smtp.example.com",
        "SMTP_PORT=587",
        "EMAIL_USERNAME=your-email@example.com",
        "EMAIL_PASSWORD=your-password"
})
class EmailSenderTest {

//
    @Mock
    private Transport transport;

    @InjectMocks
    private EmailSender emailSender;

    private Session session;

    @BeforeEach
    void setUp() {

    }

    @Value("${SMTP_HOST}")
    private String smtpHost;

    @Value("${SMTP_PORT}")
    private String smtpPort;

    @Test
    void isClassFieldBeingSet(){
        String smtphost = "smtp.example.com";
        String smtpPort = "587";
        String username = "your-email@example.com";
        String password = "your-password";
        emailSender = new EmailSender(
                smtphost,
                smtpPort,
                username,
                password
        );

        assertThat(emailSender.getSmtpHost()).isEqualTo(smtphost);
        assertThat(emailSender.getSmtpPort()).isEqualTo(smtpPort);
        assertThat(emailSender.getUsername()).isEqualTo(username);
        assertThat(emailSender.getPassword()).isEqualTo(password);

    }

    @Test
    void sendEmail() {
//        When

    }
}