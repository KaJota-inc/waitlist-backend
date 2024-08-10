package com.kajota.kajota_webpage.services;

import com.kajota.kajota_webpage.entities.User;
import com.kajota.kajota_webpage.exceptions.UserAlreadyExistException;
import com.kajota.kajota_webpage.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
//            userRepository.deleteUserByEmail(email);
            log.info("User already exists: {}", user.getEmail());
            throw new UserAlreadyExistException("Email already in use");
        } else{
            emailSender.sendEmail(
                    email,
                    "Joined Kajota waitlist",
                    null
            );
            //Given
            User suser = new User(
                    "David",
                    "Adisa",
                    "092383488223",
                    true,
                    new ArrayList<>()
            );
            userRepository.save(suser);

        }
    }

}
