package com.itsrd.epay.service.implementation;

import com.itsrd.epay.JWTSecurity.JwtHelper;
import com.itsrd.epay.configuration.CustomUserDetails;
import com.itsrd.epay.configuration.CustomUserDetailsService;
import com.itsrd.epay.dto.requests.userRequest.CreateUserRequest;
import com.itsrd.epay.dto.requests.userRequest.LoginRequest;
import com.itsrd.epay.dto.requests.userRequest.UpdateUserRequest;
import com.itsrd.epay.dto.requests.userRequest.VerifyPhoneNoRequest;
import com.itsrd.epay.dto.response.userResponse.*;
import com.itsrd.epay.exception.UserNotFoundException;
import com.itsrd.epay.model.Address;
import com.itsrd.epay.model.User;
import com.itsrd.epay.model.Wallet;
import com.itsrd.epay.repository.AddressRepository;
import com.itsrd.epay.repository.UserRepository;
import com.itsrd.epay.repository.WalletRepository;
import com.itsrd.epay.service.OtpService;
import com.itsrd.epay.service.UserService;
import com.itsrd.epay.utils.UserServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;


@Service
@Slf4j
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

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserServiceUtils userServiceUtils;

    @Autowired
    private JwtHelper jwtHelper;
    

    @Override
    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {

        userServiceUtils.checkIfUserExist(createUserRequest.getPhoneNo());

        createUserRequest.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));

        User user = new User(createUserRequest);
        Address address = new Address(createUserRequest);
        Wallet wallet = new Wallet();

        walletRepository.save(wallet);
        addressRepository.save(address);

        user.setAddress_id(address.getId());
        user.setWallet_id(wallet.getId());

        userRepository.save(user);

        otpService.generateOtp(createUserRequest.getPhoneNo());

        return new CreateUserResponse(user, "User Created", true, HttpStatus.CREATED);
    }

    @Override
    public VerifyPhoneNoResponse verifyPhoneNo(VerifyPhoneNoRequest verifyPhoneNoRequest) {

        if (!otpService.verifyOtp(verifyPhoneNoRequest))
            return new VerifyPhoneNoResponse("Wrong OTP", HttpStatus.NOT_ACCEPTABLE, false);

        Optional<User> user = userRepository.findByPhoneNo(verifyPhoneNoRequest.getPhoneNo());

        if (user.isEmpty())
            throw new UserNotFoundException("User not found");
        user.get().setActive(true);
        userRepository.save(user.get());
        return new VerifyPhoneNoResponse("PhoneNo is successful verified", HttpStatus.ACCEPTED, true);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        userServiceUtils.doAuthenticate(loginRequest.getPhoneNo(), loginRequest.getPassword());
        CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(loginRequest.getPhoneNo());
        String token = jwtHelper.generateToken(customUserDetails);
        return new LoginResponse("Login Successful", HttpStatus.OK, true, token);
    }

    @Override
    public GetUserResponse getUserDetails(Principal principal) {

        Optional<User> user = userRepository.findByPhoneNo(principal.getName());

        if (user.isEmpty())
            throw new UserNotFoundException("User not found");
        return new GetUserResponse(user.get(), "User Found", true, HttpStatus.OK);
    }


    @Override
    public UpdateUserResponse updateUserDetails(Principal principal, UpdateUserRequest updateUserRequest) {
        Optional<User> optionalUser = userRepository.findByPhoneNo(principal.getName());

        if (optionalUser.isEmpty())
            throw new UserNotFoundException("User not found");

        User user = optionalUser.get();
        if (updateUserRequest.getPassword() == null)
            updateUserRequest.setPassword(user.getPassword());
        else
            updateUserRequest.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));

        User newUser = new User(updateUserRequest);
        Address address = new Address(updateUserRequest);

        newUser.setId(user.getId());
        newUser.setAddress_id(user.getAddress_id());
        newUser.setWallet_id(user.getWallet_id());

        address.setId(user.getAddress_id());

        addressRepository.save(address);
        userRepository.save(newUser);

        return new UpdateUserResponse(newUser, "User Detail has been updated", true, HttpStatus.ACCEPTED);
    }

    @Override
    public DeleteUserResponse deleteUser(Principal principal) {
        Optional<User> user = userRepository.findByPhoneNo(principal.getName());

        if (user.isEmpty())
            throw new UserNotFoundException("User not found");

        long userId = user.get().getId();
        Long addressId = user.get().getAddress_id();
        Long walletId = user.get().getWallet_id();

        addressRepository.deleteById(addressId);
        walletRepository.deleteById(walletId);
        userRepository.deleteById(userId);

        return new DeleteUserResponse("User having Phone no: " + principal.getName() + " has been deleted!", HttpStatus.OK, true);
    }

    @Override
    public Long getWalletIdFromPhoneNo(String phoneNo) {
        Optional<User> user = userRepository.findByPhoneNo(phoneNo);

        if (user.isEmpty())
            throw new UserNotFoundException("User not found for the Phone No: " + phoneNo);

        return user.get().getWallet_id();

    }


}
