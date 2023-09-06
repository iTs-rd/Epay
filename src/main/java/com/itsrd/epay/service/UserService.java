package com.itsrd.epay.service;

import com.itsrd.epay.dto.UserRequest;
import com.itsrd.epay.dto.VerifyPhoneNoRequest;
import com.itsrd.epay.exception.UserNotFoundException;
import com.itsrd.epay.model.User;

import java.security.Principal;

public interface UserService {

    User getUserDetails(Principal principal) throws UserNotFoundException;

    User saveUser(UserRequest userRequest);

    User updateUserDetails(Principal principal, UserRequest userRequest);

    String deleteUser(Principal principal);

    Long getWalletIdFromPhoneNo(String phoneNo);
    
    String verifyPhoneNo(VerifyPhoneNoRequest verifyPhoneNoRequest);
}
