package com.itsrd.epay.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
public class GlobalResponse {
    public LocalDateTime timestamp;
    public ZoneId timeZone;
    public boolean success;
    public String message;
    public int stateCode;

    public GlobalResponse(boolean success, String message, int stateCode) {
        this.success = success;
        this.message = message;
        this.stateCode = stateCode;

        this.timeZone = ZoneId.systemDefault();
        this.timestamp = LocalDateTime.now();
    }

    public GlobalResponse() {
        this.timeZone = ZoneId.systemDefault();
        this.timestamp = LocalDateTime.now();
    }

}
