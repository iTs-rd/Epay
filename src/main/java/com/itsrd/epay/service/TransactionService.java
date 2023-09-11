package com.itsrd.epay.service;

import com.itsrd.epay.dto.response.transactionResponse.GetStatementByUserRespone;

import java.security.Principal;

public interface TransactionService {
    void addRecord(String remitterPhoneNo, String type, Double amount, String description, String remark);

    GetStatementByUserRespone getStatementByUser(Principal principal, int pageNumber);

}
