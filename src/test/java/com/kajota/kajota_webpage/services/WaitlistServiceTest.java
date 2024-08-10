package com.kajota.kajota_webpage.services;

import com.kajota.kajota_webpage.entities.User;
import com.kajota.kajota_webpage.exceptions.UserAlreadyExistException;
import com.kajota.kajota_webpage.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WaitlistServiceTest {

    @Mock
    private EmailSender emailSender;

    @Mock
    private UserRepository userRepository;


    private WaitlistService waitlistService;

    @BeforeEach
    void setUp() {
        waitlistService = new WaitlistService(userRepository,emailSender);
    }

    @Test
    void addUserToWaitList() {
//        Given
        User user = new User(
                "David",
                "Adisa",
                "092383488223",
                true,
                new ArrayList<>()
        );
        String email = "newuser@example.com";

        // When
        waitlistService.AddUserToWaitList(user);

        // Assert
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(user);


    }

    @Test
    void willThrowWhenEmailIsTaken(){
        //Given
        User user = new User(
                "David",
                "Adisa",
                "092383488223",
                true,
                new ArrayList<>()
        );

        // When
        given(userRepository.existsUserByEmail(user.getEmail()))
                .willReturn(true);

        //Then
        assertThatThrownBy(()->waitlistService.AddUserToWaitList(user))
                .isInstanceOf(UserAlreadyExistException.class)
                .hasMessageContaining("Email already in use");
    }

}