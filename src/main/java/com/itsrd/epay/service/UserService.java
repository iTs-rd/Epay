package com.itsrd.epay.service;

import com.itsrd.epay.dto.VerifyPhoneNoRequest;
import com.itsrd.epay.exception.UserNotFoundException;
import com.itsrd.epay.model.User;
import com.itsrd.epay.dto.UserRequest;

import java.security.Principal;

public interface UserService {

    User getUserDetails(Principal principal) throws UserNotFoundException;

    User saveUser(UserRequest userRequest);

    User updateUser(Long id, UserRequest userRequest);

    String deleteUser(Long id);

    Long getWalletIdFromUserId(Long user_id);

    User test(String phoneNo);

    String verifyPhoneNo(VerifyPhoneNoRequest verifyPhoneNoRequest);
}
