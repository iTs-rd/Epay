package com.itsrd.epay.JWTSecurity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void checkForUnprotectedURI() {

        Assertions.assertTrue(jwtAuthenticationFilter.checkForUnprotectedURI("/user/login"));
        Assertions.assertTrue(jwtAuthenticationFilter.checkForUnprotectedURI("/user/signup"));
        Assertions.assertTrue(jwtAuthenticationFilter.checkForUnprotectedURI("/user/verify-phoneno"));
        Assertions.assertFalse(jwtAuthenticationFilter.checkForUnprotectedURI("/user"));

    }

    @Test
    void doFilterInternal() {
    }
}