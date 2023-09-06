package com.itsrd.epay.service;

import com.itsrd.epay.model.Transaction;

import java.security.Principal;

public interface TransactionService {
    void addRecord(String remitterPhoneNo, String type, Double amount, String description, String remark);

    Iterable<Transaction> getStatement(Principal principal, int pageNumber);

}
