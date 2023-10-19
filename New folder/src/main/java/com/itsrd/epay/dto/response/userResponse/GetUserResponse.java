package com.itsrd.epay.dto.response.userResponse;

import com.itsrd.epay.model.User;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GetUserResponse extends GlobalUserResponse {
    public GetUserResponse(User user, String message, boolean success, HttpStatus stateCode) {
        super(user, message, success, stateCode);
    }
}
