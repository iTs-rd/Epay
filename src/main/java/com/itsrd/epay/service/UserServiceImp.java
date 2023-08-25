package com.itsrd.epay.service;

import com.itsrd.epay.model.User;
import com.itsrd.epay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImp implements UserService{

    @Autowired
    private UserRepository userRepository;


    @Override
    public User getUser(Long id) {
        Optional<User> user =userRepository.findById(id);
        if(user.isPresent())
            return user.get();
        else
            throw new RuntimeException("User not found for the id: "+id);
    }

    @Override
    public User saveUser(User user) {
        System.out.println("sub");
        System.out.println("hii");
//        return user;
        return userRepository.save(user);
    }


}
