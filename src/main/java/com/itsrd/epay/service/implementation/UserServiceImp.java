package com.itsrd.epay.service.implementation;

import com.itsrd.epay.configuration.CustomUserDetails;
import com.itsrd.epay.configuration.CustomUserDetailsService;
import com.itsrd.epay.dto.requests.CreateUserRequest;
import com.itsrd.epay.dto.requests.LoginRequest;
import com.itsrd.epay.dto.requests.UpdateUserRequest;
import com.itsrd.epay.dto.requests.VerifyPhoneNoRequest;
import com.itsrd.epay.dto.response.*;
import com.itsrd.epay.exception.UserAlreadyExistsException;
import com.itsrd.epay.exception.UserNotFoundException;
import com.itsrd.epay.jwtSecurity.JwtHelper;
import com.itsrd.epay.model.Address;
import com.itsrd.epay.model.User;
import com.itsrd.epay.model.Wallet;
import com.itsrd.epay.repository.AddressRepository;
import com.itsrd.epay.repository.UserRepository;
import com.itsrd.epay.repository.WalletRepository;
import com.itsrd.epay.service.OtpService;
import com.itsrd.epay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtHelper jwtHelper;

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

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }
    }

    @Override
    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {

        checkIfUserExist(createUserRequest.getPhoneNo());

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

        return new CreateUserResponse("User Created", HttpStatus.CREATED, true, user);
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
        return new VerifyPhoneNoResponse("Phone no is successful verified", HttpStatus.ACCEPTED, true);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        this.doAuthenticate(loginRequest.getPhoneNo(), loginRequest.getPassword());
        CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(loginRequest.getPhoneNo());
        String token = this.jwtHelper.generateToken(customUserDetails);
        return new LoginResponse("Login Successful", HttpStatus.OK, true, token);
    }

    @Override
    public GetUserResponse getUserDetails(Principal principal) {

        Optional<User> user = userRepository.findByPhoneNo(principal.getName());

        if (user.isEmpty())
            throw new UserNotFoundException("User not found");
        return new GetUserResponse("User Found", HttpStatus.OK, true, user.get());
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

        return new UpdateUserResponse("User Detail has been updated", HttpStatus.ACCEPTED, true, newUser);
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
//        return "User having Phone no: " + principal.getName() + " has been deleted!";
    }

    @Override
    public Long getWalletIdFromPhoneNo(String phoneNo) {
        Optional<User> user = userRepository.findByPhoneNo(phoneNo);

        if (user.isEmpty())
            throw new UserNotFoundException("User not found for the Phone No: " + phoneNo);

        return user.get().getWallet_id();

    }


}
