package com.kajota.kajota_webpage.controllers;


import com.kajota.kajota_webpage.entities.User;
import com.kajota.kajota_webpage.exceptions.UserAlreadyExistException;
import com.kajota.kajota_webpage.responses.Response;
import com.kajota.kajota_webpage.services.WaitlistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/waitlist")
@RequiredArgsConstructor
@Slf4j
public class WaitlistController {

    private final WaitlistService waitlistService;


    @PostMapping("/add-user")
    public ResponseEntity<Response> addUserToWaitlist(@RequestBody User user){

        try{

            waitlistService.AddUserToWaitList(user);
            Response successResponse = new Response("Added to Waitlist", HttpStatus.CREATED);
            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);

        } catch (UserAlreadyExistException ex) {

            log.info("User already exists: {}", user.getEmail());
            Response errorResponse = new Response(ex.getMessage(), HttpStatus.CONFLICT);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);

        } catch (Exception ex) {

            log.error("An unexpected error occurred while adding user: {}", user.getEmail(), ex);
            Response errorResponse = new Response("An unexpected error occurred. Please try again later.",HttpStatus.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }

//    @GetMapping("/view")
//    public String view(Model model){
//        model.addAttribute("message", "Hello, World!");
//        return "emailtemplate";
//    }

}
