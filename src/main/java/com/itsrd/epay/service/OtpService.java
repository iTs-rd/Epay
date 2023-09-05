package com.itsrd.epay.service;

import com.itsrd.epay.dto.VerifyPhoneNoRequest;

public interface OtpService {

    void generateOtp(String phoneNo);

    boolean verifyOtp(VerifyPhoneNoRequest verifyPhoneNoRequest);

}
