package com.itsrd.epay.dto.response.userResponse;

import com.itsrd.epay.dto.response.GlobalResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LoginResponse extends GlobalResponse {
    private String token;

    public LoginResponse(String message, HttpStatus stateCode, boolean success, String token) {
        super(message, success, stateCode);
        this.token = token;
    }
}
