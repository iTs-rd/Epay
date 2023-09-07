package com.itsrd.epay.dto.response.walletResponse;

import com.itsrd.epay.dto.response.GlobalResponse;
import org.springframework.http.HttpStatus;

public class CheckBalanceResponse extends GlobalResponse {
    public CheckBalanceResponse(String message, boolean success, HttpStatus stateCode) {
        super(message, success, stateCode);
    }
}
