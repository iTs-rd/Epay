package com.itsrd.epay.service.implementation;

import com.itsrd.epay.Repository.TransactionRepository;
import com.itsrd.epay.model.Transaction;
import com.itsrd.epay.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class TransactionServiceImp implements TransactionService {

    @Value("${statement.pageSize}")
    private int pageSize;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void addRecord(String remitterPhoneNo, String type, Double amount, String description, String remark) {
        Transaction transaction = new Transaction(remitterPhoneNo, type, amount, description, remark);
        transactionRepository.save(transaction);
    }

    @Override
    public Iterable<Transaction> getStatement(Principal principal, int pageNumber) {
        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "createdAt");
        return transactionRepository.findByRemitterPhoneNo(principal.getName(), page);
    }
}
