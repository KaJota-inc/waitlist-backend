package com.kajota.kajota_webpage.services;

import com.kajota.kajota_webpage.entities.User;
import com.kajota.kajota_webpage.exceptions.UserAlreadyExistException;
import com.kajota.kajota_webpage.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WaitlistService {

    private final UserRepository userRepository;

    @Autowired
    public WaitlistService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void AddUserToWaitList(User user){
        String email = user.getEmail();
        //Try to get user from DB by email
        User userInDbByEmail = userRepository.findByEmail(email);

        if(userInDbByEmail!=null){
            log.info("User already exists: {}", user.getEmail());
            throw new UserAlreadyExistException("Email already in use");
        } else{
            userRepository.save(user);
        }
    }

}
