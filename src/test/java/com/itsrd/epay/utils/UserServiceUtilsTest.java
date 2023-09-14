package com.itsrd.epay.utils;

import com.itsrd.epay.repository.UserRepository;
import com.itsrd.epay.service.OtpService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;

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
    void doAuthenticate() {
        userServiceUtils.doAuthenticate("9988776655", "asdz");
    }

    @Test
    void checkIfUserExist() {
        userServiceUtils.checkIfUserExist("9988776655");
    }
}