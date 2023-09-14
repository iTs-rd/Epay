package com.itsrd.epay.utils;

import com.itsrd.epay.model.Wallet;
import com.itsrd.epay.repository.WalletRepository;
import com.itsrd.epay.service.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class WalletServiceUtilsTest {

    @Mock
    private TransactionService transactionService;

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletServiceUtils walletServiceUtils;

    @Test
    void checkForInsufficientBalance() {
        walletServiceUtils.checkForInsufficientBalance(100.0, 10.0);
    }

    @Test
    void checkForSelfTransfer() {
        walletServiceUtils.checkForSelfTransfer("9988776655", "9988776644");
    }

    @Test
    void recordWithdrawal() {
        String message = walletServiceUtils.recordWithdrawal("9988776655", 1000.0, "test");

        Assertions.assertNotNull(message);
    }

    @Test
    void recordDeposit() {
        String message = walletServiceUtils.recordDeposit("9988776655", 1000.0, "test");

        Assertions.assertNotNull(message);

    }

    @Test
    void recordTransfer() {
        String message = walletServiceUtils.recordTransfer("9988776655", "9988776644", 1000.0, "test");
        Assertions.assertNotNull(message);

    }

    @Test
    void withdrawMoneyFromWallet() {
        Wallet wallet = new Wallet(1L, 2000.0);

        Mockito.when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));
        walletServiceUtils.withdrawMoneyFromWallet(1L, 1000.0);
    }

    @Test
    void addMoneyToWallet() {
        Wallet wallet = new Wallet(1L, 2000.0);
        Mockito.when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));
        walletServiceUtils.addMoneyToWallet(1L, 1000.0);

    }
}