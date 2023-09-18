package com.itsrd.epay.utils;

import com.itsrd.epay.exception.UserAlreadyExistsException;
import com.itsrd.epay.model.User;
import com.itsrd.epay.repository.UserRepository;
import com.itsrd.epay.service.OtpService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceUtilsTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OtpService otpService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserServiceUtils userServiceUtils;


    @Test
    void doAuthenticate_ForSuccessfulAuthentication_Test() {
        Mockito.when(authenticationManager.authenticate(ArgumentMatchers.any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        userServiceUtils.doAuthenticate("9988776655", "asdz");
    }

    @Test
    void doAuthenticate_ForAuthenticationFail_Test() {
        Mockito.doThrow(new BadCredentialsException("Invalid Username or Password")).when(authenticationManager).authenticate(ArgumentMatchers.any(UsernamePasswordAuthenticationToken.class));
        Assertions.assertThrows(BadCredentialsException.class, () -> userServiceUtils.doAuthenticate("9988776655", "asdz"));
    }


    @Test
    void checkIfUserExist_ForActiveUser_Test() {
        User user = new User(1L, "Rudresh", "Gupta", "9988776655", "Rudresh.gupta@kotak.com", "male", "asdz", true, "ROLE_USER", 1L, 1L);
        Mockito.when(userRepository.findByPhoneNo("9988776655")).thenReturn(Optional.of(user));

        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userServiceUtils.checkIfUserExist("9988776655"));
    }

    @Test
    void checkIfUserExist_ForInactiveUser_Test() {
        User user = new User(1L, "Rudresh", "Gupta", "9988776655", "Rudresh.gupta@kotak.com", "male", "asdz", false, "ROLE_USER", 1L, 1L);

        Mockito.when(userRepository.findByPhoneNo("9988776655")).thenReturn(Optional.of(user));
        Mockito.doNothing().when(otpService).generateOtp("9988776655");

        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userServiceUtils.checkIfUserExist("9988776655"));
    }

    @Test
    void checkIfUserExist_ForNonExistingUser_Test() {
        Mockito.when(userRepository.findByPhoneNo("9988776655")).thenReturn(Optional.empty());
        userServiceUtils.checkIfUserExist("9988776655");
    }
}