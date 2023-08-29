package com.itsrd.epay.service;

public interface TransactionService {
    void addRecord(Long sourceAccountNo, String type, String description, String remark);
}
