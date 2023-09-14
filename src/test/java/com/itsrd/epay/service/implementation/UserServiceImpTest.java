package com.itsrd.epay.service.implementation;

import com.itsrd.epay.JWTSecurity.JwtHelper;
import com.itsrd.epay.configuration.CustomUserDetails;
import com.itsrd.epay.configuration.CustomUserDetailsService;
import com.itsrd.epay.dto.requests.userRequest.CreateUserRequest;
import com.itsrd.epay.dto.requests.userRequest.LoginRequest;
import com.itsrd.epay.dto.requests.userRequest.UpdateUserRequest;
import com.itsrd.epay.dto.requests.userRequest.VerifyPhoneNoRequest;
import com.itsrd.epay.dto.response.userResponse.*;
import com.itsrd.epay.model.User;
import com.itsrd.epay.repository.AddressRepository;
import com.itsrd.epay.repository.UserRepository;
import com.itsrd.epay.repository.WalletRepository;
import com.itsrd.epay.service.OtpService;
import com.itsrd.epay.utils.UserServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
//@SpringBootTest
@Slf4j
class UserServiceImpTest {

    @Mock
    private OtpService otpService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserServiceUtils userServiceUtils;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CustomUserDetailsService customUserDetailsService;
    @Mock
    private AddressRepository addressRepository;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private JwtHelper jwtHelper;

    @InjectMocks
    private UserServiceImp userServiceImp;

    @Test
    void createUser() {
        CreateUserRequest createUserRequest = new CreateUserRequest("Rudresh", "Gupta", "9988776655", "asdz", "Rudresh.gupta@kotak.com", "Male", "Kasiya road", "Deoria", "UP", "274001");
        Mockito.when(passwordEncoder.encode(createUserRequest.getPassword())).thenReturn("encoded password");
        CreateUserResponse createUserResponse = userServiceImp.createUser(createUserRequest);
        Assertions.assertTrue(createUserResponse.isSuccess());
    }

    @Test
    void verifyPhoneNo() {

//        Test 1
//        For Success
        VerifyPhoneNoRequest verifyPhoneNoRequest = new VerifyPhoneNoRequest("9988776655", "1234");
        User user = new User(1L, "Rudresh", "Gupta", "9988776655", "Rudresh.gupta@kotak.com", "male", "asdz", false, "ROLE_USER", 1L, 1L);
        Mockito.when(otpService.verifyOtp(verifyPhoneNoRequest)).thenReturn(true);
        Mockito.when(userRepository.findByPhoneNo("9988776655")).thenReturn(Optional.of(user));
        VerifyPhoneNoResponse verifyPhoneNoResponse = userServiceImp.verifyPhoneNo(verifyPhoneNoRequest);
        Assertions.assertTrue(verifyPhoneNoResponse.isSuccess());

//        Test 2
//        For Failure
        verifyPhoneNoRequest = new VerifyPhoneNoRequest("9988776655", "1234");
        Mockito.when(otpService.verifyOtp(verifyPhoneNoRequest)).thenReturn(false);
        verifyPhoneNoResponse = userServiceImp.verifyPhoneNo(verifyPhoneNoRequest);
        Assertions.assertFalse(verifyPhoneNoResponse.isSuccess());
    }

    @Test
    void login() {

        LoginRequest loginRequest = new LoginRequest("9988776655", "asdz");
        User user = new User(1L, "Rudresh", "Gupta", "9988776655", "Rudresh.gupta@kotak.com", "male", "asdz", false, "ROLE_USER", 1L, 1L);

        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        Mockito.when(customUserDetailsService.loadUserByUsername("9988776655")).thenReturn(customUserDetails);
        Mockito.when(jwtHelper.generateToken((customUserDetails))).thenReturn("test token");
        LoginResponse loginResponse = userServiceImp.login(loginRequest);

        Assertions.assertTrue(loginResponse.isSuccess());


    }

    @Test
    void getUserDetails() {
        User user = new User(1L, "Rudresh", "Gupta", "9988776655", "Rudresh.gupta@kotak.com", "male", "asdz", false, "ROLE_USER", 1L, 1L);
        Mockito.when(userRepository.findByPhoneNo("9988776655")).thenReturn(Optional.of(user));
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "9988776655";
            }
        };
        GetUserResponse getUserResponse = userServiceImp.getUserDetails(principal);
        Assertions.assertTrue(getUserResponse.isSuccess());
    }


    @Test
    void updateUserDetails() {
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "9988776655";
            }
        };
        User user = new User(1L, "Rudresh", "Gupta", "9988776655", "Rudresh.gupta@kotak.com", "male", "asdz", false, "ROLE_USER", 1L, 1L);

        UpdateUserRequest updateUserRequest = new UpdateUserRequest("Ankit", "Gupta", "9988776655", "abcdef", "Ankit@kotak.com", "Male", "test street", "test city", "test state", "123456");
        Mockito.when(userRepository.findByPhoneNo("9988776655")).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.encode(updateUserRequest.getPassword())).thenReturn("encoded password2");

        UpdateUserResponse userResponse = userServiceImp.updateUserDetails(principal, updateUserRequest);
        Assertions.assertTrue(userResponse.isSuccess());


    }

    @Test
    void deleteUser() {
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "9988776655";
            }
        };
        User user = new User(1L, "Rudresh", "Gupta", "9988776655", "Rudresh.gupta@kotak.com", "male", "asdz", false, "ROLE_USER", 1L, 1L);
        Mockito.when(userRepository.findByPhoneNo("9988776655")).thenReturn(Optional.of(user));

        DeleteUserResponse deleteUserResponse = userServiceImp.deleteUser(principal);

        Assertions.assertTrue(deleteUserResponse.isSuccess());

    }

    @Test
    void getWalletIdFromPhoneNo() {
        User user = new User(1L, "Rudresh", "Gupta", "9988776655", "Rudresh.gupta@kotak.com", "male", "asdz", false, "ROLE_USER", 1L, 1L);
        Mockito.when(userRepository.findByPhoneNo("9988776655")).thenReturn(Optional.of(user));

        Long id = userServiceImp.getWalletIdFromPhoneNo("9988776655");

        Assertions.assertNotNull(id);

    }
}