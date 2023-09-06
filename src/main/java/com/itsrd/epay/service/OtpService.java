package com.itsrd.epay.service;

import com.itsrd.epay.dto.requests.VerifyPhoneNoRequest;

public interface OtpService {

    void generateOtp(String phoneNo);

    boolean verifyOtp(VerifyPhoneNoRequest verifyPhoneNoRequest);

}
