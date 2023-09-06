package com.itsrd.epay.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class VerifyPhoneNoResponse extends GlobalResponse {

    public VerifyPhoneNoResponse(String message, HttpStatus stateCode, boolean success) {
        this.message = message;
        this.stateCode = stateCode.value();
        this.success = success;
    }
}
