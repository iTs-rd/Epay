package com.itsrd.epay.service;

import com.itsrd.epay.model.Address;
import com.itsrd.epay.model.User;
import com.itsrd.epay.repository.AddressRepository;
import com.itsrd.epay.repository.UserRepository;
import com.itsrd.epay.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImp implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;


    @Override
    public User getUser(Long id) {
        Optional<User> user =userRepository.findById(id);
        if(user.isPresent())
            return user.get();
        else
            throw new RuntimeException("User not found for the id: "+id);
    }


    @Override
    public User saveUser(UserRequest userRequest) {

        User user=new User(userRequest);
        Address address=new Address(userRequest);

        addressRepository.save(address);

        user.setAddress(address);

        userRepository.save(user);

        return user;

    }


}
