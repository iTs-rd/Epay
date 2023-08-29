package com.itsrd.epay.service.implementation;

import com.itsrd.epay.model.Address;
import com.itsrd.epay.model.User;
import com.itsrd.epay.model.Wallet;
import com.itsrd.epay.repository.AddressRepository;
import com.itsrd.epay.repository.UserRepository;
import com.itsrd.epay.repository.WalletRepository;
import com.itsrd.epay.request.UserRequest;
import com.itsrd.epay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImp implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private WalletRepository walletRepository;

    public UserServiceImp(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public User saveUser(UserRequest userRequest) {

        User user = new User(userRequest);
        Address address = new Address(userRequest);
        Wallet wallet=new Wallet();

        walletRepository.save(wallet);

        addressRepository.save(address);

        user.setAddress_id(address.getId());
        user.setWallet_id(wallet.getId());

//        user.setAddress(address);

        userRepository.save(user);

        return user;

    }

    @Override
    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent())
            return user.get();
        else
            throw new RuntimeException("User not found for the id: " + id);
    }


    @Override
    public User updateUser(Long id, UserRequest userRequest) {
        Optional<User> oldData = userRepository.findById(id);

//        Not Working
        if (oldData.isEmpty())
            throw new RuntimeException("User not found for the id: " + id);

        User user = new User(userRequest);
        Address address = new Address(userRequest);

        Long address_id=oldData.get().getAddress_id();
        Long wallet_id=oldData.get().getWallet_id();

        user.setId(id);
        user.setAddress_id(address_id);
        user.setWallet_id(wallet_id);

        address.setId(address_id);


//        address.setId(oldData.get().getAddress().getId());

        addressRepository.save(address);

//        user.setAddress(address);

        userRepository.save(user);
        return user;

    }

    @Override
    public String deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);

//        Not Working
        if (user.isEmpty())
            throw new RuntimeException("User not found for the id: " + id);

        Long address_id=user.get().getAddress_id();
        Long wallet_id=user.get().getWallet_id();



//        Exception handling
        addressRepository.deleteById(address_id);
        walletRepository.deleteById(wallet_id);
        userRepository.deleteById(id);

//        addressRepository.deleteById(user.get().getAddress().getId());

        return "User having userId: " + id + " has been deleted";
    }

    @Override
    public Long getWalletIdFromUserId(Long user_id) {

        Optional<User> user=userRepository.findById(user_id);

//        handle exception
        if (user.isEmpty())
            throw new RuntimeException("User not found for the id: " + user_id);

        return user.get().getWallet_id();

    }


}
