package com.itsrd.epay.utils;

import com.itsrd.epay.exception.UserAlreadyExistsException;
import com.itsrd.epay.model.User;
import com.itsrd.epay.repository.UserRepository;
import com.itsrd.epay.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserServiceUtils {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpService otpService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }
    }

    public void checkIfUserExist(String phoneNo) {
        Optional<User> user = userRepository.findByPhoneNo(phoneNo);

        if (user.isEmpty())
            return;

        if (user.get().isActive())
            throw new UserAlreadyExistsException("User with Phone No: " + phoneNo + " already Exists");

        otpService.generateOtp(phoneNo);

        throw new UserAlreadyExistsException("User Already exist. New OTP is generated please verify with new otp");

    }


}
