package com.itsrd.epay.service.implementation;

import com.itsrd.epay.model.Transaction;
import com.itsrd.epay.repository.TransactionRepository;
import com.itsrd.epay.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImp implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void addRecord(Long sourceAccountNo, String type, String description, String remark) {
        Transaction transaction = new Transaction(sourceAccountNo, type, description, remark);
        transactionRepository.save(transaction);
    }
}
