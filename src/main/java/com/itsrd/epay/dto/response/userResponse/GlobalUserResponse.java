package com.itsrd.epay.dto.response.userResponse;

import com.itsrd.epay.dto.response.GlobalResponse;
import com.itsrd.epay.model.User;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GlobalUserResponse extends GlobalResponse {
    private User user;

    public GlobalUserResponse(User user, String message, boolean success, HttpStatus stateCode) {
        super(message, success, stateCode);
        this.user = user;
    }
}
