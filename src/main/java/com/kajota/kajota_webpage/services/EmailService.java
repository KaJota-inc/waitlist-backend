//package com.kajota.kajota_webpage.services;
//
//import lombok.RequiredArgsConstructor;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.context.Context;
//
//import javax.mail.MessagingException;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//public class EmailService {
//    private final JavaMailSender mailSender;
//    private final TemplateEngine templateEngine;
//
//    @Value("${spring.mail.username}")
//    private String fromMail;
//    @Async
//    public void sendMail(String toAddress, String subject, String attachmentPath){
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
//
//        try {
//            mimeMessageHelper.setFrom(fromMail);
//        } catch (javax.mail.MessagingException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            mimeMessageHelper.setTo(InternetAddress.parse(toAddress));
//        } catch (javax.mail.MessagingException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            mimeMessageHelper.setSubject(subject);
//        } catch (javax.mail.MessagingException e) {
//            throw new RuntimeException(e);
//        }
//
//        Context context = new Context();
//            context.setVariables(Map.of(
//                    "instagramLink","https://www.instagram.com/kajota.io/?utm_source=ig_web_button_share_sheet",
//                    "linkedinLink","https://www.linkedin.com/company/kajota-io")
//            );
//            String processedString = templateEngine.process("emailtemplate", context);
//
//            try {
//                mimeMessageHelper.setText(processedString, true);
//            } catch (MessagingException e) {
//                throw new RuntimeException(e);
//            }
//
//
//        mailSender.send(mimeMessage);
//    }
//}
