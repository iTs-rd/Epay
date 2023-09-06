package com.itsrd.epay.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LoginResponse extends GlobalResponse {
    private String token;

    public LoginResponse(String message, HttpStatus stateCode, boolean success, String token) {
        this.message = message;
        this.stateCode = stateCode.value();
        this.success = success;
        this.token = token;
    }
}
