package com.itsrd.epay.JWTSecurity;

import com.itsrd.epay.configuration.CustomUserDetails;
import com.itsrd.epay.configuration.CustomUserDetailsService;
import com.itsrd.epay.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.io.IOException;


@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtHelper jwtHelper;

    @Mock
    private CustomUserDetailsService userDetailsService;


    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void checkForUnprotectedURI_Test() {

        Assertions.assertTrue(jwtAuthenticationFilter.checkForPublicURI("/user/verify-phoneno"));
        Assertions.assertTrue(jwtAuthenticationFilter.checkForPublicURI("/user/login"));
        Assertions.assertTrue(jwtAuthenticationFilter.checkForPublicURI("/user/signup"));
        Assertions.assertTrue(jwtAuthenticationFilter.checkForPublicURI("/user/swagger"));
        Assertions.assertTrue(jwtAuthenticationFilter.checkForPublicURI("/user/v3"));
        Assertions.assertFalse(jwtAuthenticationFilter.checkForPublicURI("/user"));

    }

    @Test
    void doFilterInternal_Successful_Test() throws ServletException, IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        FilterChain filterChain = Mockito.mock(FilterChain.class);
        User user = new User(1L, "Rudresh", "Gupta", "9988776655", "Rudresh.gupta@kotak.com", "male", "asdz", false, "ROLE_USER", 1L, 1L);
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        Mockito.when(request.getRequestURI()).thenReturn("/user");
        Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer jwt-token");
        Mockito.when(jwtHelper.getUsernameFromToken("jwt-token")).thenReturn("rudresh");

        Mockito.when(userDetailsService.loadUserByUsername("rudresh")).thenReturn(customUserDetails);
        Mockito.when(jwtHelper.validateToken("jwt-token", customUserDetails)).thenReturn(true);
        UsernamePasswordAuthenticationToken authentication = Mockito.mock(UsernamePasswordAuthenticationToken.class);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    }

    @Test
    void doFilterInternal_ForPublicURI_Test() throws ServletException, IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        FilterChain filterChain = Mockito.mock(FilterChain.class);

        Mockito.when(request.getRequestURI()).thenReturn("/user/login");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    }


}