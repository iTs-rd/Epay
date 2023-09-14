package com.itsrd.epay.service.implementation;

import com.itsrd.epay.dto.response.transactionResponse.GetStatementByUserRespone;
import com.itsrd.epay.model.Transaction;
import com.itsrd.epay.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class TransactionServiceImpTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImp transactionServiceImp;

    @Test
    void addRecord() {
        transactionServiceImp.addRecord("9988776655", "deposit", 1000.0, "Test Service", "No remark");
    }

    @Test
    void getStatementByUser() {
//        List<Company> companies = new ArrayList<>();
//        Page<Company> pagedResponse = new PageImpl(companies);
//        Mockito.when(companyRepository.findAllByIsActiveTrue(pagedResponse)).thenReturn(pagedResponse);

        List<Transaction> transactionList = new ArrayList<>();

        transactionList.add(new Transaction("9988776655", "deposit", 1000.0, "test service", "no remark"));
        transactionList.add(new Transaction("9988776655", "deposit", 100.0, "test service", "no remark"));
        transactionList.add(new Transaction("9988776655", "deposit", 1100.0, "test service", "no remark"));
        transactionList.add(new Transaction("9988776655", "deposit", 2000.0, "test service", "no remark"));
        transactionList.add(new Transaction("9988776655", "deposit", 5000.0, "test service", "no remark"));
        transactionList.add(new Transaction("9988776655", "deposit", 8000.0, "test service", "no remark"));

        Pageable page = PageRequest.of(0, 5, Sort.Direction.DESC, "createdAt");
        Page<Transaction> transactionPage = new PageImpl<>(transactionList, page, 47);

        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "9988776655";
            }
        };
        ReflectionTestUtils.setField(transactionServiceImp, "pageSize", 5, int.class);
        System.out.println(transactionPage);
        Mockito.when(transactionRepository.findByRemitterPhoneNo("9988776655", page)).thenReturn(transactionPage);
        GetStatementByUserRespone getStatementByUserRespone = transactionServiceImp.getStatementByUser(principal, 0);

        Assertions.assertTrue(getStatementByUserRespone.isSuccess());

//        System.out.println(getStatementByUserRespone.getTransaction());


    }
}