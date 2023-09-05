package com.itsrd.epay.service.implementation;

import com.itsrd.epay.dto.VerifyPhoneNoRequest;
import com.itsrd.epay.exception.CanNotChangePhoneNo;
import com.itsrd.epay.exception.UserAlreadyExistsException;
import com.itsrd.epay.exception.UserNotFoundException;
import com.itsrd.epay.model.Address;
import com.itsrd.epay.model.User;
import com.itsrd.epay.model.Wallet;
import com.itsrd.epay.Repository.AddressRepository;
import com.itsrd.epay.Repository.UserRepository;
import com.itsrd.epay.Repository.WalletRepository;
import com.itsrd.epay.dto.UserRequest;
import com.itsrd.epay.service.OtpService;
import com.itsrd.epay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Objects;
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
        if (userRepository.findByPhoneNo(phoneNo).isPresent())
            throw new UserAlreadyExistsException("User with Phone No: " + phoneNo + " already Exists");

    }

    @Override
    public User saveUser(UserRequest userRequest) {


//        need to update
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
//        Customize
        if (user.isEmpty())
            throw new RuntimeException("No user");
        return user.get();
    }


    @Override
    public User updateUser(Long id, UserRequest userRequest) {
        Optional<User> oldData = userRepository.findById(id);

        if (oldData.isEmpty())
            throw new UserNotFoundException("User not found for the id: " + id);

        if (!Objects.equals(oldData.get().getPhoneNo(), userRequest.getPhoneNo()))
            throw new CanNotChangePhoneNo();

        User user = new User(userRequest);
        Address address = new Address(userRequest);

        Long address_id = oldData.get().getAddress_id();
        Long wallet_id = oldData.get().getWallet_id();

        user.setId(id);
        user.setAddress_id(address_id);
        user.setWallet_id(wallet_id);

        address.setId(address_id);

        addressRepository.save(address);
        userRepository.save(user);

        return user;
    }

    @Override
    public String deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty())
            throw new UserNotFoundException("User not found for the id: " + id);

        Long address_id = user.get().getAddress_id();
        Long wallet_id = user.get().getWallet_id();

        addressRepository.deleteById(address_id);
        walletRepository.deleteById(wallet_id);
        userRepository.deleteById(id);

        return "User having userId: " + id + " has been deleted!";
    }

    @Override
    public Long getWalletIdFromUserId(Long user_id) {
        Optional<User> user = userRepository.findById(user_id);

        if (user.isEmpty())
            throw new UserNotFoundException("User not found for the id: " + user_id);

        return user.get().getWallet_id();

    }

    @Override
    public User test(String phoneNo) {


//        if(oldData.get().getPhoneNo() != userRequest.getPhoneNo())
        throw new CanNotChangePhoneNo();
//        if(userRepository.findByPhoneNo(phoneNo)!=null)
//            System.out.println("hiii");
//
//        return userRepository.findByPhoneNo(phoneNo);
    }

    @Override
    public String verifyPhoneNo(VerifyPhoneNoRequest verifyPhoneNoRequest) {

//        Customize
        if(!otpService.verifyOtp(verifyPhoneNoRequest))
            throw new RuntimeException("Wrong OTP");

        Optional<User> user=userRepository.findByPhoneNo(verifyPhoneNoRequest.getPhoneNo());

        if(user.isEmpty())
            throw new RuntimeException("verifyPhoneNo runtime error");
        user.get().setActive(true);
        userRepository.save(user.get());
        return "Phone no is successful verified";
    }
}
