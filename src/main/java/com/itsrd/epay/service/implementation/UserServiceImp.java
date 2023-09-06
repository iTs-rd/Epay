package com.itsrd.epay.service.implementation;

import com.itsrd.epay.Repository.AddressRepository;
import com.itsrd.epay.Repository.UserRepository;
import com.itsrd.epay.Repository.WalletRepository;
import com.itsrd.epay.dto.UserRequest;
import com.itsrd.epay.dto.VerifyPhoneNoRequest;
import com.itsrd.epay.exception.UserAlreadyExistsException;
import com.itsrd.epay.exception.UserNotFoundException;
import com.itsrd.epay.model.Address;
import com.itsrd.epay.model.User;
import com.itsrd.epay.model.Wallet;
import com.itsrd.epay.service.OtpService;
import com.itsrd.epay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;


@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private OtpService otpService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserServiceImp(UserRepository userRepository, AddressRepository addressRepository, WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.walletRepository = walletRepository;
    }

    private void checkIfUserExist(String phoneNo) {
        Optional<User> user = userRepository.findByPhoneNo(phoneNo);

        if (user.isEmpty())
            return;

        if (user.get().isActive())
            throw new UserAlreadyExistsException("User with Phone No: " + phoneNo + " already Exists");

        otpService.generateOtp(phoneNo);

        throw new UserAlreadyExistsException("User Already exist. New OTP is generated please verify with new otp");

    }

    @Override
    public User saveUser(UserRequest userRequest) {

        checkIfUserExist(userRequest.getPhoneNo());

        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        User user = new User(userRequest);
        Address address = new Address(userRequest);
        Wallet wallet = new Wallet();

        walletRepository.save(wallet);
        addressRepository.save(address);

        user.setAddress_id(address.getId());
        user.setWallet_id(wallet.getId());

        userRepository.save(user);

        otpService.generateOtp(userRequest.getPhoneNo());

        return user;
    }

    @Override
    public User getUserDetails(Principal principal) {

        Optional<User> user = userRepository.findByPhoneNo(principal.getName());

        if (user.isEmpty())
            throw new UserNotFoundException("User not found");
        return user.get();
    }


    @Override
    public User updateUserDetails(Principal principal, UserRequest userRequest) {
        Optional<User> optionalUser = userRepository.findByPhoneNo(principal.getName());

        if (optionalUser.isEmpty())
            throw new UserNotFoundException("User not found");

        User user = optionalUser.get();
        if (userRequest.getPassword() == null)
            userRequest.setPassword(user.getPassword());
        else
            userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        User newUser = new User(userRequest);
        Address address = new Address(userRequest);

        newUser.setActive(true);
        newUser.setId(user.getId());
        newUser.setAddress_id(user.getAddress_id());
        newUser.setWallet_id(user.getWallet_id());

        address.setId(user.getAddress_id());

        addressRepository.save(address);
        userRepository.save(newUser);

        return newUser;
    }

    @Override
    public String deleteUser(Principal principal) {
        Optional<User> user = userRepository.findByPhoneNo(principal.getName());

        if (user.isEmpty())
            throw new UserNotFoundException("User not found");

        long userId = user.get().getId();
        Long addressId = user.get().getAddress_id();
        Long walletId = user.get().getWallet_id();

        addressRepository.deleteById(addressId);
        walletRepository.deleteById(walletId);
        userRepository.deleteById(userId);

        return "User having Phone no: " + principal.getName() + " has been deleted!";
    }

    @Override
    public Long getWalletIdFromPhoneNo(String phoneNo) {
        Optional<User> user = userRepository.findByPhoneNo(phoneNo);

        if (user.isEmpty())
            throw new UserNotFoundException("User not found for the Phone No: " + phoneNo);

        return user.get().getWallet_id();

    }


    @Override
    public String verifyPhoneNo(VerifyPhoneNoRequest verifyPhoneNoRequest) {

//        Customize
        if (!otpService.verifyOtp(verifyPhoneNoRequest))
            throw new RuntimeException("Wrong OTP");

        Optional<User> user = userRepository.findByPhoneNo(verifyPhoneNoRequest.getPhoneNo());

        if (user.isEmpty())
            throw new RuntimeException("verifyPhoneNo runtime error");
        user.get().setActive(true);
        userRepository.save(user.get());
        return "Phone no is successful verified";
    }
}
