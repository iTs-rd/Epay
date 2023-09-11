package com.itsrd.epay.dto.response.userResponse;

import com.itsrd.epay.dto.response.GlobalResponse;
import com.itsrd.epay.model.User;
import org.springframework.http.HttpStatus;

public class GlobalUserResponse extends GlobalResponse {
    private User user;

    public GlobalUserResponse(User user, String message, boolean success, HttpStatus stateCode) {
        super(message, success, stateCode);
        this.user = user;
    }
}
