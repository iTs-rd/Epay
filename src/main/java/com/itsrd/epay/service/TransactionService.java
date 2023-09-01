package com.itsrd.epay.service;

import com.itsrd.epay.model.Transaction;

public interface TransactionService {
    void addRecord(Long remitterUserId, String type, Double amount, String description, String remark);

    Iterable<Transaction> getStatement(Long id, int pageNumber);

}
