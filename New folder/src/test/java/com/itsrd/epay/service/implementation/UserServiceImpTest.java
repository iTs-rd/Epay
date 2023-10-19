package com.itsrd.epay.service.implementation;

import com.itsrd.epay.JWTSecurity.JwtHelper;
import com.itsrd.epay.configuration.CustomUserDetails;
import com.itsrd.epay.configuration.CustomUserDetailsService;
import com.itsrd.epay.dto.requests.userRequest.CreateUserRequest;
import com.itsrd.epay.dto.requests.userRequest.LoginRequest;
import com.itsrd.epay.dto.requests.userRequest.UpdateUserRequest;
import com.itsrd.epay.dto.requests.userRequest.VerifyPhoneNoRequest;
import com.itsrd.epay.dto.response.userResponse.*;
import com.itsrd.epay.exception.UserAlreadyExistsException;
import com.itsrd.epay.model.Address;
import com.itsrd.epay.model.User;
import com.itsrd.epay.model.Wallet;
import com.itsrd.epay.repository.AddressRepository;
import com.itsrd.epay.repository.UserRepository;
import com.itsrd.epay.repository.WalletRepository;
import com.itsrd.epay.service.OtpService;
import com.itsrd.epay.utils.UserServiceUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
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
    void createUser_WhenUserAlreadyExist_Test() {
        CreateUserRequest createUserRequest = new CreateUserRequest("Rudresh", "Gupta", "9988776655", "asdz", "Rudresh.gupta@kotak.com", "Male", "Kasiya road", "Deoria", "UP", "274001");

        Mockito.doThrow(new UserAlreadyExistsException("User already exist")).when(userServiceUtils).checkIfUserExist("9988776655");
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userServiceImp.createUser(createUserRequest));
    }

    @Test
    void createUser_WhenUserNotExist_Test() {
        CreateUserRequest createUserRequest = new CreateUserRequest("Rudresh", "Gupta", "9988776655", "asdz", "Rudresh.gupta@kotak.com", "Male", "Kasiya road", "Deoria", "UP", "274001");
        User user = new User(createUserRequest);
        Address address = new Address(createUserRequest);
        Wallet wallet = new Wallet();

        Mockito.doNothing().when(userServiceUtils).checkIfUserExist(createUserRequest.getPhoneNo());
        Mockito.when(passwordEncoder.encode(createUserRequest.getPassword())).thenReturn("encoded password");
        Mockito.when(walletRepository.save(ArgumentMatchers.any(Wallet.class))).thenReturn(wallet);
        Mockito.when(addressRepository.save(ArgumentMatchers.any(Address.class))).thenReturn(address);
        Mockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);
        Mockito.doNothing().when(otpService).generateOtp(createUserRequest.getPhoneNo());

        CreateUserResponse createUserResponse = userServiceImp.createUser(createUserRequest);

        Assertions.assertTrue(createUserResponse.isSuccess());

    }


    @Test
    void verifyPhoneNo_OtpNotMatch_Test() {
        VerifyPhoneNoRequest verifyPhoneNoRequest = new VerifyPhoneNoRequest("9988776655", "1234");

        Mockito.when(otpService.verifyOtp(verifyPhoneNoRequest)).thenReturn(false);

        VerifyPhoneNoResponse verifyPhoneNoResponse = userServiceImp.verifyPhoneNo(verifyPhoneNoRequest);

        Assertions.assertFalse(verifyPhoneNoResponse.isSuccess());
    }

    @Test
    void verifyPhoneNo_Successful_Test() {
        VerifyPhoneNoRequest verifyPhoneNoRequest = new VerifyPhoneNoRequest("9988776655", "1234");
        User user = new User(1L, "Rudresh", "Gupta", "9988776655", "Rudresh.gupta@kotak.com", "male", "asdz", false, "ROLE_USER", 1L, 1L);

        Mockito.when(otpService.verifyOtp(verifyPhoneNoRequest)).thenReturn(true);
        Mockito.when(userRepository.findByPhoneNo("9988776655")).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(null);

        VerifyPhoneNoResponse verifyPhoneNoResponse = userServiceImp.verifyPhoneNo(verifyPhoneNoRequest);
        Assertions.assertTrue(verifyPhoneNoResponse.isSuccess());
    }


    @Test
    void login_Test() {
        LoginRequest loginRequest = new LoginRequest("9988776655", "asdz");
        User user = new User(1L, "Rudresh", "Gupta", "9988776655", "Rudresh.gupta@kotak.com", "male", "asdz", false, "ROLE_USER", 1L, 1L);
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        Mockito.doNothing().when(userServiceUtils).doAuthenticate("9988776655", "asdz");
        Mockito.when(customUserDetailsService.loadUserByUsername("9988776655")).thenReturn(customUserDetails);
        Mockito.when(jwtHelper.generateToken((customUserDetails))).thenReturn("test token");

        LoginResponse loginResponse = userServiceImp.login(loginRequest);

        Assertions.assertTrue(loginResponse.isSuccess());
    }

    @Test
    void getUserDetails_Test() {
        User user = new User(1L, "Rudresh", "Gupta", "9988776655", "Rudresh.gupta@kotak.com", "male", "asdz", false, "ROLE_USER", 1L, 1L);
        Principal principal = Mockito.mock(Principal.class);

        Mockito.when(userRepository.findByPhoneNo("9988776655")).thenReturn(Optional.of(user));
        Mockito.when(principal.getName()).thenReturn("9988776655");

        GetUserResponse getUserResponse = userServiceImp.getUserDetails(principal);

        Assertions.assertTrue(getUserResponse.isSuccess());
    }


    @Test
    void updateUserDetails_Test() {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest("Ankit", "Gupta", "9988776655", "abcdef", "Ankit@kotak.com", "Male", "test street", "test city", "test state", "123456");
        User user = new User(1L, "Rudresh", "Gupta", "9988776655", "Rudresh.gupta@kotak.com", "male", "asdz", false, "ROLE_USER", 1L, 1L);
        Principal principal = Mockito.mock(Principal.class);

        Mockito.when(principal.getName()).thenReturn("9988776655");
        Mockito.when(userRepository.findByPhoneNo("9988776655")).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.encode(updateUserRequest.getPassword())).thenReturn("encoded password2");
        Mockito.when(addressRepository.save(ArgumentMatchers.any(Address.class))).thenReturn(null);
        Mockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(null);

        UpdateUserResponse userResponse = userServiceImp.updateUserDetails(principal, updateUserRequest);

        Assertions.assertTrue(userResponse.isSuccess());
    }


    @Test
    void deleteUser_Test() {
        Principal principal = Mockito.mock(Principal.class);
        User user = new User(1L, "Rudresh", "Gupta", "9988776655", "Rudresh.gupta@kotak.com", "male", "asdz", false, "ROLE_USER", 1L, 1L);

        Mockito.when(principal.getName()).thenReturn("9988776655");
        Mockito.when(userRepository.findByPhoneNo("9988776655")).thenReturn(Optional.of(user));
        Mockito.doNothing().when(addressRepository).deleteById(1L);
        Mockito.doNothing().when(walletRepository).deleteById(1L);
        Mockito.doNothing().when(userRepository).deleteById(1L);

        DeleteUserResponse deleteUserResponse = userServiceImp.deleteUser(principal);

        Assertions.assertTrue(deleteUserResponse.isSuccess());

    }

    @Test
    void getWalletIdFromPhoneNo_Test() {
        User user = new User(1L, "Rudresh", "Gupta", "9988776655", "Rudresh.gupta@kotak.com", "male", "asdz", false, "ROLE_USER", 1L, 1L);
        Mockito.when(userRepository.findByPhoneNo("9988776655")).thenReturn(Optional.of(user));

        Long id = userServiceImp.getWalletIdFromPhoneNo("9988776655");

        Assertions.assertNotNull(id);

    }
}