package com.kajota.kajota_webpage.services.interfaces;

public interface IEmailSender {
    void subscribeUser(String email);
    void sendEmail(String toAddress, String subject, String attachmentPath);
}
