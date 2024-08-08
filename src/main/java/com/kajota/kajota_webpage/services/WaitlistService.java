package com.kajota.kajota_webpage.services;

import com.kajota.kajota_webpage.entities.EmailSender;
import com.kajota.kajota_webpage.entities.User;
import com.kajota.kajota_webpage.exceptions.UserAlreadyExistException;
import com.kajota.kajota_webpage.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WaitlistService {

    private final UserRepository userRepository;
    private final EmailSender emailSender;

    @Autowired
    public WaitlistService(
            UserRepository userRepository,
            EmailSender emailSender
    ){
        this.userRepository = userRepository;
        this.emailSender =  emailSender;
    }

    public void AddUserToWaitList(User user){
        String email = user.getEmail();

        //Try to get user from DB by email
        boolean userInDbByEmail = userRepository.existsUserByEmail(email);

        if(userInDbByEmail){
            log.info("User already exists: {}", user.getEmail());
            throw new UserAlreadyExistException("Email already in use");
        } else{
            emailSender.sendEmail(
                    email,
                    "Joined Kajota waitlist",
                    "Hello, You Have successfully joined the kajota waitist. and you'd be informed when we launch.",
                    null);
            userRepository.save(user);

        }
    }

}
