package com.itsrd.epay.dto.response.walletResponse;

import com.itsrd.epay.dto.response.GlobalResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TransferMoneyResponse extends GlobalResponse {

    public TransferMoneyResponse(String message, boolean success, HttpStatus stateCode) {
        super(message, success, stateCode);
    }
}
