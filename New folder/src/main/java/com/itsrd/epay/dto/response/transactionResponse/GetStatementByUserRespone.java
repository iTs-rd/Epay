package com.itsrd.epay.dto.response.transactionResponse;

import com.itsrd.epay.dto.response.GlobalResponse;
import com.itsrd.epay.model.Transaction;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class GetStatementByUserRespone extends GlobalResponse {
    int currentPageNo;
    int totalNoOfPages;
    List<Transaction> transaction;
    
    public GetStatementByUserRespone(List<Transaction> transaction, int currentPageNo, int totalNoOfPages, String message, boolean success, HttpStatus stateCode) {
        super(message, success, stateCode);
        this.transaction = transaction;
        this.currentPageNo = currentPageNo;
        this.totalNoOfPages = totalNoOfPages;
    }
}
