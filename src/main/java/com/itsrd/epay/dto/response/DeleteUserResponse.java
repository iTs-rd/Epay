package com.itsrd.epay.dto.response;

import org.springframework.http.HttpStatus;

public class DeleteUserResponse extends GlobalResponse {
    public DeleteUserResponse(String message, HttpStatus stateCode, boolean success) {
//        super(success, message, stateCode);
        this.message = message;
        this.stateCode = stateCode.value();
        this.success = success;
    }
}
