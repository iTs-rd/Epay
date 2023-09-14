package com.itsrd.epay.service.implementation;

import com.itsrd.epay.dto.requests.userRequest.VerifyPhoneNoRequest;
import com.itsrd.epay.model.Otp;
import com.itsrd.epay.repository.OtpRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@Slf4j
class OtpServiceImpTest {


    @Mock
    private OtpRepository otpRepository;
    @InjectMocks
    private OtpServiceImp otpServiceImp;


    @Test
    void generateOtp() {
        ReflectionTestUtils.setField(otpServiceImp, "generatedOtp", "1234", String.class);
        String phoneNo = "9988776655";
        otpServiceImp.generateOtp(phoneNo);

    }

    @Test
    void verifyOtp() {
        VerifyPhoneNoRequest verifyPhoneNoRequest = new VerifyPhoneNoRequest("9988776655", "1234");
        Otp otp = new Otp("9988776655", "1234");
        Mockito.when(otpRepository.findByPhoneNo("9988776655")).thenReturn(Optional.of(otp));
        boolean isOtpVerified = otpServiceImp.verifyOtp(verifyPhoneNoRequest);
        Assertions.assertTrue(isOtpVerified);
    }
}