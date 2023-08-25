package com.itsrd.epay.controller;


import com.itsrd.epay.model.Address;
import com.itsrd.epay.model.User;
import com.itsrd.epay.request.UserRequest;
import com.itsrd.epay.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> getUser(@RequestParam Long id)
    {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }


    @PostMapping("/save")
    public ResponseEntity<User> saveUser(@Valid @RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.saveUser(userRequest),HttpStatus.CREATED);
    }

}
