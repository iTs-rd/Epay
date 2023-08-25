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
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;


    @Override
    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent())
            return user.get();
        else
            throw new RuntimeException("User not found for the id: " + id);
    }


    @Override
    public User saveUser(UserRequest userRequest) {

        User user = new User(userRequest);
        Address address = new Address(userRequest);

        addressRepository.save(address);

        user.setAddress(address);

        userRepository.save(user);

        return user;

    }

    @Override
    public User updateUser(Long id, UserRequest userRequest) {
        Optional<User> oldData = userRepository.findById(id);

//        Not Working
        if (oldData.isEmpty())
            throw new RuntimeException("User not found for the id: " + id);

        User user = new User(userRequest);
        Address address = new Address(userRequest);

        user.setId(id);
        address.setId(oldData.get().getAddress().getId());

        addressRepository.save(address);

        user.setAddress(address);

        userRepository.save(user);
        return user;

    }

    @Override
    public String deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);

//        Not Working
        if (user.isEmpty())
            throw new RuntimeException("User not found for the id: " + id);

        userRepository.deleteById(id);
        addressRepository.deleteById(user.get().getAddress().getId());

        return "User having userId: " + id + " has been deleted";
    }


}
