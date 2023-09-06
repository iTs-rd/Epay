package com.itsrd.epay.service;

import com.itsrd.epay.dto.requests.CreateUserRequest;
import com.itsrd.epay.dto.requests.LoginRequest;
import com.itsrd.epay.dto.requests.UpdateUserRequest;
import com.itsrd.epay.dto.requests.VerifyPhoneNoRequest;
import com.itsrd.epay.dto.response.*;
import com.itsrd.epay.exception.UserNotFoundException;

import java.security.Principal;

public interface UserService {

    CreateUserResponse createUser(CreateUserRequest createUserRequest);

    VerifyPhoneNoResponse verifyPhoneNo(VerifyPhoneNoRequest verifyPhoneNoRequest);

    LoginResponse login(LoginRequest loginRequest);

    GetUserResponse getUserDetails(Principal principal) throws UserNotFoundException;

    UpdateUserResponse updateUserDetails(Principal principal, UpdateUserRequest updateUserRequest);

    DeleteUserResponse deleteUser(Principal principal);

    Long getWalletIdFromPhoneNo(String phoneNo);
}
