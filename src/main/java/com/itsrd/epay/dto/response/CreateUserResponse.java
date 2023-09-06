package com.itsrd.epay.dto.response;

import com.itsrd.epay.model.User;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CreateUserResponse extends GlobalResponse {

    private User user;

    public CreateUserResponse(String message, HttpStatus stateCode, boolean success, User user) {
        this.message = message;
        this.stateCode = stateCode.value();
        this.success = success;
        this.user = user;
    }
}
