package com.itsrd.epay.service.implementation;

import com.itsrd.epay.dto.response.transactionResponse.GetStatementByUserRespone;
import com.itsrd.epay.model.Transaction;
import com.itsrd.epay.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    void addRecord_Successful_Test() {
        Mockito.when(transactionRepository.save(ArgumentMatchers.any(Transaction.class))).thenReturn(null);
        transactionServiceImp.addRecord("9988776655", "deposit", 1000.0, "Test Service", "No remark");
    }

    @Test
    void getStatementByUser_Successful_Test() {
        List<Transaction> transactionList = new ArrayList<>();
        PageRequest page = PageRequest.of(0, 5, Sort.Direction.DESC, "createdAt");

        transactionList.add(new Transaction("9988776655", "deposit", 1000.0, "test service", "no remark"));
        transactionList.add(new Transaction("9988776655", "withdraw", 100.0, "test service", "no remark"));
        transactionList.add(new Transaction("9988776655", "deposit", 1100.0, "test service", "no remark"));
        transactionList.add(new Transaction("9988776655", "send", 2000.0, "test service", "no remark"));
        transactionList.add(new Transaction("9988776655", "Receive", 5000.0, "test service", "no remark"));

        Page<Transaction> transactionPage = new PageImpl<>(transactionList, page, 5);
        Principal principal = Mockito.mock(Principal.class);

        Mockito.when(principal.getName()).thenReturn("9988776655");
        ReflectionTestUtils.setField(transactionServiceImp, "pageSize", 5, int.class);
        Mockito.when(transactionRepository.findByRemitterPhoneNo("9988776655", page)).thenReturn(transactionPage);

        GetStatementByUserRespone getStatementByUserRespone = transactionServiceImp.getStatementByUser(principal, 0);

        Assertions.assertTrue(getStatementByUserRespone.isSuccess());
    }
}