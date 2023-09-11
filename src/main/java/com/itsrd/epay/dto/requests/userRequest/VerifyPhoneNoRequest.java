package com.itsrd.epay.dto.requests.userRequest;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VerifyPhoneNoRequest {

    private final String phoneNoMessage = "Phone no Should be of 10 digit";

    @NotNull(message = phoneNoMessage)
    @Size(min = 10, max = 10, message = phoneNoMessage)
    private String phoneNo;

    @NotNull
    @Size(min = 4, max = 4)
    private String otp;
}
