package com.itsrd.epay.service.implementation;

import com.itsrd.epay.dto.requests.userRequest.VerifyPhoneNoRequest;
import com.itsrd.epay.exception.NoOtpFound;
import com.itsrd.epay.model.Otp;
import com.itsrd.epay.repository.OtpRepository;
import com.itsrd.epay.service.OtpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class OtpServiceImp implements OtpService {

    @Value("${app.otp}")
    private String generatedOtp;
    @Autowired
    private OtpRepository otpRepository;

    @Override
    public void generateOtp(String phoneNo) {
        Otp otp = new Otp(phoneNo, generatedOtp);
        otpRepository.save(otp);
        log.info("Generated OTP is: " + generatedOtp);
    }

    @Override
    public boolean verifyOtp(VerifyPhoneNoRequest verifyPhoneNoRequest) {
        Optional<Otp> otp = otpRepository.findByPhoneNo(verifyPhoneNoRequest.getPhoneNo());

        if (otp.isEmpty())
            throw new NoOtpFound("No OTP found for Phone no: " + verifyPhoneNoRequest.getPhoneNo());

        return Objects.equals(verifyPhoneNoRequest.getOtp(), otp.get().getOtp());

    }
}
