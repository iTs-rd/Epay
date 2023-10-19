package com.itsrd.epay.service.implementation;

import com.itsrd.epay.dto.requests.userRequest.VerifyPhoneNoRequest;
import com.itsrd.epay.exception.NoOtpFound;
import com.itsrd.epay.model.Otp;
import com.itsrd.epay.repository.OtpRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class OtpServiceImpTest {

    @Mock
    private OtpRepository otpRepository;
    @InjectMocks
    private OtpServiceImp otpServiceImp;

    @Test
    void generateOtp_Successful_Test() {
        ReflectionTestUtils.setField(otpServiceImp, "generatedOtp", "1234", String.class);
        Mockito.when(otpRepository.save(ArgumentMatchers.any(Otp.class))).thenReturn(null);

        otpServiceImp.generateOtp("9988776655");

    }

    @Test
    void verifyOtp_Successful_Test() {
        VerifyPhoneNoRequest verifyPhoneNoRequest = new VerifyPhoneNoRequest("9988776655", "1234");
        Otp otp = new Otp("9988776655", "1234");

        Mockito.when(otpRepository.findByPhoneNo("9988776655")).thenReturn(Optional.of(otp));

        boolean isOtpVerified = otpServiceImp.verifyOtp(verifyPhoneNoRequest);

        Assertions.assertTrue(isOtpVerified);
    }

    @Test
    void verifyOtp_OtpNotFound_Test() {
        VerifyPhoneNoRequest verifyPhoneNoRequest = new VerifyPhoneNoRequest("9988776655", "1234");

        Mockito.when(otpRepository.findByPhoneNo("9988776655")).thenReturn(Optional.empty());

        Assertions.assertThrows(NoOtpFound.class, () -> otpServiceImp.verifyOtp(verifyPhoneNoRequest));
    }

    @Test
    void verifyOtp_OtpNotMatch_Test() {
        VerifyPhoneNoRequest verifyPhoneNoRequest = new VerifyPhoneNoRequest("9988776655", "4321");
        Otp otp = new Otp("9988776655", "1234");

        Mockito.when(otpRepository.findByPhoneNo("9988776655")).thenReturn(Optional.of(otp));

        boolean isOtpVerified = otpServiceImp.verifyOtp(verifyPhoneNoRequest);
        Assertions.assertFalse(isOtpVerified);
    }
}