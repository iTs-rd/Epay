package com.itsrd.epay.dto.response.userResponse;

import com.itsrd.epay.dto.response.GlobalResponse;
import org.springframework.http.HttpStatus;

public class DeleteUserResponse extends GlobalResponse {
    public DeleteUserResponse(String message, HttpStatus stateCode, boolean success) {
        super(message, success, stateCode);
    }
}
