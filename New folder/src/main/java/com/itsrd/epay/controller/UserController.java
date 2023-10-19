package com.itsrd.epay.controller;


import com.itsrd.epay.dto.requests.userRequest.CreateUserRequest;
import com.itsrd.epay.dto.requests.userRequest.LoginRequest;
import com.itsrd.epay.dto.requests.userRequest.UpdateUserRequest;
import com.itsrd.epay.dto.requests.userRequest.VerifyPhoneNoRequest;
import com.itsrd.epay.dto.response.userResponse.*;
import com.itsrd.epay.exception.UserNotFoundException;
import com.itsrd.epay.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return new ResponseEntity<>(userService.createUser(createUserRequest), HttpStatus.CREATED);
    }

    @PostMapping("/verify-phoneno")
    public ResponseEntity<VerifyPhoneNoResponse> verifyPhoneNo(@Valid @RequestBody VerifyPhoneNoRequest verifyPhoneNoRequest) {
        VerifyPhoneNoResponse response = userService.verifyPhoneNo(verifyPhoneNoRequest);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStateCode()));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(userService.login(loginRequest), HttpStatus.OK);
    }


    @GetMapping("")
    public ResponseEntity<GetUserResponse> getUserDetails(Principal principal) throws UserNotFoundException {
        return new ResponseEntity<>(userService.getUserDetails(principal), HttpStatus.OK);
    }


    @PutMapping("")
    public ResponseEntity<UpdateUserResponse> updateUserDetails(Principal principal, @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        return new ResponseEntity<>(userService.updateUserDetails(principal, updateUserRequest), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("")
    public ResponseEntity<DeleteUserResponse> deleteUser(Principal principal) {
        return new ResponseEntity<>(userService.deleteUser(principal), HttpStatus.ACCEPTED);
    }


}
