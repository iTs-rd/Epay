package com.itsrd.epay.utils;

import com.itsrd.epay.exception.CanNotTransferMoneyToSelf;
import com.itsrd.epay.exception.InsufficientBalance;
import com.itsrd.epay.model.Wallet;
import com.itsrd.epay.repository.WalletRepository;
import com.itsrd.epay.service.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
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
    void checkForInsufficientBalance_ForTrue_Test() {
        Assertions.assertThrows(InsufficientBalance.class, () -> walletServiceUtils.checkForInsufficientBalance(100.0, 1000.0));
    }

    @Test
    void checkForInsufficientBalance_ForFalse_Test() {
        walletServiceUtils.checkForInsufficientBalance(100.0, 10.0);
    }

    @Test
    void checkForSelfTransfer_ForFalse_Test() {
        walletServiceUtils.checkForSelfTransfer("9988776655", "9988776644");
    }

    @Test
    void checkForSelfTransfer_ForTrue_Test() {
        Assertions.assertThrows(CanNotTransferMoneyToSelf.class, () -> walletServiceUtils.checkForSelfTransfer("9988776655", "9988776655"));
    }

    @Test
    void recordWithdrawal_Test() {
        String description = "Rupee 1000.0 has been debited from your wallet";

        Mockito.doNothing().when(transactionService).addRecord("9988776655", "Withdraw", 1000.0, description, "test record withdrawal");

        String message = walletServiceUtils.recordWithdrawal("9988776655", 1000.0, "test record withdrawal");

        Assertions.assertEquals(description, message);
    }

    @Test
    void recordDeposit_Test() {
        String description = "Rupee 1000.0 has been credited to your wallet";

        Mockito.doNothing().when(transactionService).addRecord("9988776655", "Deposit", 1000.0, description, "test record deposit");

        String message = walletServiceUtils.recordDeposit("9988776655", 1000.0, "test record deposit");

        Assertions.assertEquals(description, message);

    }

    @Test
    void recordTransfer_Test() {
        String remitterDescription = "Rupee 1000.0 has been Transfer to User: 9988776644";
        String beneficiaryDescription = "Rupee 1000.0 has been send by User: 9988776655";

        Mockito.doNothing().when(transactionService).addRecord("9988776655", "Send", 1000.0, remitterDescription, "test record transfer");
        Mockito.doNothing().when(transactionService).addRecord("9988776644", "Receive", 1000.0, beneficiaryDescription, "test record transfer");

        String message = walletServiceUtils.recordTransfer("9988776655", "9988776644", 1000.0, "test record transfer");

        Assertions.assertEquals(remitterDescription, message);


    }

    @Test
    void withdrawMoneyFromWallet_Successful_Test() {
        Wallet wallet = new Wallet(1L, 2000.0);

//
        Mockito.when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));
        Mockito.when(walletRepository.save(ArgumentMatchers.any(Wallet.class))).thenReturn(null);

        walletServiceUtils.withdrawMoneyFromWallet(1L, 1000.0);

    }

    @Test
    void addMoneyToWallet_Successful_Test() {
        Wallet wallet = new Wallet(1L, 1000.0);

        Mockito.when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));
        Mockito.when(walletRepository.save(ArgumentMatchers.any(Wallet.class))).thenReturn(null);

        walletServiceUtils.addMoneyToWallet(1L, 1000.0);

    }
}