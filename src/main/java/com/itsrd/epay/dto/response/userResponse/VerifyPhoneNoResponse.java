package com.itsrd.epay.dto.response.userResponse;

import com.itsrd.epay.dto.response.GlobalResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class VerifyPhoneNoResponse extends GlobalResponse {

    public VerifyPhoneNoResponse(String message, HttpStatus stateCode, boolean success) {
        super(message, success, stateCode);
    }
}
