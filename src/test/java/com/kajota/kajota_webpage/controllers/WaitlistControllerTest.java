package com.kajota.kajota_webpage.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kajota.kajota_webpage.entities.User;
import com.kajota.kajota_webpage.exceptions.UserAlreadyExistException;
import com.kajota.kajota_webpage.responses.Response;
import com.kajota.kajota_webpage.services.interfaces.IWaitlistService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WaitlistController.class)
@AutoConfigureMockMvc(addFilters = false)
class WaitlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IWaitlistService waitlistService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should return 201 CREATED when user is added to waitlist")
    void addUserToWaitlist_success() throws Exception {
        User user = new User(
                "TestFirstName",
                "test@example.com",
                "1234567890",
                true,
                java.util.Arrays.asList("Personal", "Business")
        );

        Mockito.doNothing().when(waitlistService).AddUserToWaitList(any(User.class));

        mockMvc.perform(post("/api/v1/waitlist/add-user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Added to Waitlist"))
                .andExpect(jsonPath("$.status").value("CREATED"));
    }

    @Test
    @DisplayName("Should return 409 CONFLICT when user already exists")
    void addUserToWaitlist_userAlreadyExists() throws Exception {
        User user = new User(
                "ExistingUser",
                "existing@example.com",
                "0987654321",
                false,
                java.util.Collections.singletonList("Personal")
        );

        Mockito.doThrow(new UserAlreadyExistException("User already exists")).when(waitlistService).AddUserToWaitList(any(User.class));

        mockMvc.perform(post("/api/v1/waitlist/add-user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("User already exists"))
                .andExpect(jsonPath("$.status").value("CONFLICT"));
    }

    @Test
    @DisplayName("Should return 500 INTERNAL_SERVER_ERROR on unexpected exception")
    void addUserToWaitlist_unexpectedError() throws Exception {
        User user = new User(
                "ErrorUser",
                "error@example.com",
                "1112223333",
                false,
                java.util.Collections.singletonList("Business")
        );

        Mockito.doThrow(new RuntimeException("Unexpected error")).when(waitlistService).AddUserToWaitList(any(User.class));

        mockMvc.perform(post("/api/v1/waitlist/add-user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("An unexpected error occurred. Please try again later."))
                .andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"));
    }
}
