package com.itsrd.epay.controller;


import com.itsrd.epay.JWTSecurity.JwtHelper;
import com.itsrd.epay.configuration.CustomUserDetails;
import com.itsrd.epay.configuration.CustomUserDetailsService;
import com.itsrd.epay.dto.LoginRequest;
import com.itsrd.epay.dto.UserRequest;
import com.itsrd.epay.dto.VerifyPhoneNoRequest;
import com.itsrd.epay.exception.PasswordRequired;
import com.itsrd.epay.exception.UserNotFoundException;
import com.itsrd.epay.model.User;
import com.itsrd.epay.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> saveUser(@Valid @RequestBody UserRequest userRequest) {
        if (userRequest.getPassword() == null)
            throw new PasswordRequired();
        return new ResponseEntity<>(userService.saveUser(userRequest), HttpStatus.CREATED);
    }

    @PostMapping("/verify-phoneno")
    public ResponseEntity<String> verifyPhoneNo(@Valid @RequestBody VerifyPhoneNoRequest verifyPhoneNoRequest) {
        return new ResponseEntity<>(userService.verifyPhoneNo(verifyPhoneNoRequest), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        this.doAuthenticate(loginRequest.getPhoneNo(), loginRequest.getPassword());
        CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(loginRequest.getPhoneNo());
        String token = this.jwtHelper.generateToken(customUserDetails);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }


    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }
    }


    @GetMapping("")
    public ResponseEntity<User> getUserDetails(Principal principal) throws UserNotFoundException {
        return new ResponseEntity<>(userService.getUserDetails(principal), HttpStatus.OK);
    }


    @PutMapping("")
    public ResponseEntity<User> updateUserDetails(Principal principal, @Valid @RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.updateUserDetails(principal, userRequest), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteUser(Principal principal) {
        return new ResponseEntity<>(userService.deleteUser(principal), HttpStatus.ACCEPTED);
    }

}
