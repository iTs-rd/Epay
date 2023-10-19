package com.itsrd.epay.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
public class GlobalResponse {
    public String message;
    public boolean success;
    public int stateCode;
    public String timestamp;
    public ZoneId timeZone;

    public GlobalResponse(String message, boolean success, HttpStatus stateCode) {
        this.success = success;
        this.message = message;
        this.stateCode = stateCode.value();
        this.timeZone = ZoneId.systemDefault();
        this.timestamp = String.valueOf(LocalDateTime.now());
    }


}
