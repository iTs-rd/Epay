package com.itsrd.epay.service;

import com.itsrd.epay.dto.requests.userRequest.VerifyPhoneNoRequest;

public interface OtpService {

    void generateOtp(String phoneNo);

    boolean verifyOtp(VerifyPhoneNoRequest verifyPhoneNoRequest);

}
