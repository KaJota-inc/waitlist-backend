package com.kajota.kajota_webpage.services;

import com.kajota.kajota_webpage.entities.User;
import com.kajota.kajota_webpage.exceptions.UserAlreadyExistException;
import com.kajota.kajota_webpage.repositories.UserRepository;
import com.kajota.kajota_webpage.services.interfaces.IWaitlistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class WaitlistService implements IWaitlistService {

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
        log.info("Got to Waitlist service");
        String email = user.getEmail();

        //Try to get user from DB by email
        boolean userInDbByEmail = userRepository.existsUserByEmail(email);
        if(userInDbByEmail){
            log.info("User already exists: {}", user.getEmail());
            throw new UserAlreadyExistException("Email already in use");
        }
        try{
            emailSender.subscribeUser(email);
            emailSender.sendEmail(
                    email,
                    "Joined Kajota waitlist ",
                    null
            );
            userRepository.save(user);
            log.info("Finished waitlist service");
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }


    }

}
