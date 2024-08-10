package com.kajota.kajota_webpage.controllers;

import com.kajota.kajota_webpage.services.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class TestController {

    @Autowired
    private final EmailSender emailSender;

    @GetMapping("/send-text")
    public String sendMailTest(){
//        emailSender.sendEmail("spiritsoulbody123@gmail.com","Joined testing",null);
        return "emailTemplate";
    }
}
