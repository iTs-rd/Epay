package com.itsrd.epay.service;

import com.itsrd.epay.dto.requests.userRequest.CreateUserRequest;
import com.itsrd.epay.dto.requests.userRequest.LoginRequest;
import com.itsrd.epay.dto.requests.userRequest.UpdateUserRequest;
import com.itsrd.epay.dto.requests.userRequest.VerifyPhoneNoRequest;
import com.itsrd.epay.dto.response.userResponse.*;
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
