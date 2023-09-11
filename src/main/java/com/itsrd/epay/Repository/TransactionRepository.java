package com.itsrd.epay.repository;

import com.itsrd.epay.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TransactionRepository extends ElasticsearchRepository<Transaction, String> {
    Page<Transaction> findByRemitterPhoneNo(String remitterPhoneNo, Pageable pageable);
}
