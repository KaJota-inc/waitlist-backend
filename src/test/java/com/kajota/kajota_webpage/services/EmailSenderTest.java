//package com.kajota.kajota_webpage.services;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.MockitoAnnotations;
//import org.springframework.core.io.ClassPathResource;
//import org.thymeleaf.TemplateEngine;
//
//import javax.activation.DataHandler;
//import javax.activation.DataSource;
//import javax.activation.FileDataSource;
//import javax.mail.*;
//import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMultipart;
//
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class EmailSenderTest {
//
////        @Mock
////        private TemplateEngine emailTemplateEngine;
//
//        @InjectMocks
//        private EmailSender emailSender;
//
//        @BeforeEach
//        void setUp() {
//                MockitoAnnotations.openMocks(this);
//                emailSender = new EmailSender("smtp.host.com", "587", "username", "password");
//        }
//
//        @Test
//        void testSendEmail() throws Exception {
//                // Mock the template processing
////                when(emailTemplateEngine.process(any(String.class), any(Context.class))).thenReturn("<html>Email Body</html>");
//
//                // Create a MimeMessage and Multipart to capture the email content
//                MimeMessage mockMessage = new MimeMessage((Session) null);
//                Multipart multipart = new MimeMultipart();
//
//                // Run the email sender
//                emailSender.sendEmail("recipient@example.com", "Subject", null);
//
//                // Verify that the email content was set correctly
//                assertEquals("Subject", mockMessage.getSubject());
//                assertEquals("recipient@example.com", mockMessage.getRecipients(Message.RecipientType.TO)[0].toString());
//
//                // Verify that the body part was added to the multipart
//                ArgumentCaptor<MimeBodyPart> bodyPartCaptor = ArgumentCaptor.forClass(MimeBodyPart.class);
//                verify(mockMessage).setContent(any(Multipart.class));
//        }
//
//        @Test
//        void testInlineImages() throws Exception {
//                // Mock the template processing
//                TemplateEngine emailTemplateEngine = mock(TemplateEngine.class);
//                // Create a MimeMessage and Multipart to capture the email content
//                MimeMessage mockMessage = new MimeMessage((Session) null);
//                Multipart multipart = new MimeMultipart();
//
//                mockMessage.setContent(multipart);
//
//                // Run the email sender with inline images
//                emailSender.sendEmail("recipient@example.com", "Subject with Images", null);
//
//                // Verify each inline image
//                Map<String, String> expectedInlineImages = Map.of(
//                        "logoImage", "/static/images/hero-img.png",
//                        "instagram", "/static/images/Instragram.svg",
//                        "kajotaLogo", "/static/images/kajota-logo.svg",
//                        "kajotaWhiteLogo", "/static/images/kajota-white-logo.svg",
//                        "linkLight", "/static/images/link_light.svg",
//                        "linkedin", "/static/images/LinkedIn.svg",
//                        "threeDot", "/static/images/three_dot.svg"
//                );
//
//                for (String imageName : expectedInlineImages.keySet()) {
//                        System.out.println(expectedInlineImages.get(imageName));
//                        MimeBodyPart imagePart = new MimeBodyPart();
//                        DataSource fds = new FileDataSource(expectedInlineImages.get(imageName));
//                        imagePart.setDataHandler(new DataHandler(fds));
//                        imagePart.setHeader("Content-ID", "<" + imageName + ">");
//                        imagePart.setDisposition(MimeBodyPart.INLINE);
//                        multipart.addBodyPart(imagePart);
//
//                        // Verify that each image part is correctly configured
//                        assertEquals("<" + imageName + ">", imagePart.getHeader("Content-ID")[0]);
//                }
//
//                // Set the multipart content to the message
//                mockMessage.setContent(multipart);
//        }
//
//        @Test
//        void testAttachmentHandling() throws Exception {
//                // Mock MimeMessage and Transport
//                MimeMessage mockMessage = new MimeMessage((Session) null);
//                Multipart multipart = new MimeMultipart();
//
//                // Run the email sender with an attachment
//                emailSender.sendEmail("recipient@example.com", "Subject", "path/to/attachment.pdf");
//
//                // Add the attachment to the multipart
//                MimeBodyPart attachmentPart = new MimeBodyPart();
//                DataSource source = new FileDataSource("path/to/attachment.pdf");
//                attachmentPart.setDataHandler(new DataHandler(source));
//                attachmentPart.setFileName(source.getName());
//                multipart.addBodyPart(attachmentPart);
//
//                // Set the multipart content to the message
//                mockMessage.setContent(multipart);
//
//                // Verify the attachment
//                assertEquals("attachment.pdf", attachmentPart.getFileName());
//        }
//}
