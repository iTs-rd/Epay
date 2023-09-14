package com.itsrd.epay.service.implementation;

import com.itsrd.epay.dto.requests.walletRequest.DepositMoneyRequest;
import com.itsrd.epay.dto.requests.walletRequest.TransferMoneyRequest;
import com.itsrd.epay.dto.requests.walletRequest.WithdrawMoneyRequest;
import com.itsrd.epay.dto.response.walletResponse.CheckBalanceResponse;
import com.itsrd.epay.dto.response.walletResponse.DepositMoneyResponse;
import com.itsrd.epay.dto.response.walletResponse.TransferMoneyResponse;
import com.itsrd.epay.dto.response.walletResponse.WithdrawMoneyResponse;
import com.itsrd.epay.model.Wallet;
import com.itsrd.epay.repository.UserRepository;
import com.itsrd.epay.repository.WalletRepository;
import com.itsrd.epay.service.TransactionService;
import com.itsrd.epay.service.UserService;
import com.itsrd.epay.utils.WalletServiceUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
//@SpringBootTest
class WalletServiceImpTest {


    @Mock
    private TransactionService transactionService;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Mock
    private WalletServiceUtils walletServiceUtils;

    @InjectMocks
    private WalletServiceImp walletServiceImp;


    @Test
    void depositMoney() {

        DepositMoneyRequest depositMoneyRequest = new DepositMoneyRequest(1000.0, "test deposit");

        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "9988776655";
            }
        };
        Mockito.when(userService.getWalletIdFromPhoneNo("9988776655")).thenReturn(1L);
        Wallet wallet = new Wallet(1L, 0.0);

        Mockito.when(walletServiceUtils.recordDeposit("9988776655", 1000.0, "test deposit")).thenReturn("deposit message");
        DepositMoneyResponse depositMoneyResponse = walletServiceImp.depositMoney(principal, depositMoneyRequest);

        Assertions.assertTrue(depositMoneyResponse.isSuccess());

    }

    @Test
    void withdrawMoney() {
        WithdrawMoneyRequest withdrawMoneyRequest = new WithdrawMoneyRequest(1000.0, "test withdraw");

        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "9988776655";
            }
        };
        Mockito.when(userService.getWalletIdFromPhoneNo("9988776655")).thenReturn(1L);
        Wallet wallet = new Wallet(1L, 10000.0);

//        Mockito.when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));
        Mockito.when(walletServiceUtils.recordWithdrawal("9988776655", 1000.0, "test withdraw")).thenReturn("withdraw message");

        WithdrawMoneyResponse withdrawMoneyResponse = walletServiceImp.withdrawMoney(principal, withdrawMoneyRequest);

        Assertions.assertTrue(withdrawMoneyResponse.isSuccess());

    }

    @Test
    void transferMoney() {
        TransferMoneyRequest transferMoneyRequest = new TransferMoneyRequest(1000.0, "test transfer", "9988776644");
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "9988776655";
            }
        };

        Mockito.when(userService.getWalletIdFromPhoneNo("9988776655")).thenReturn(1L);
        Mockito.when(userService.getWalletIdFromPhoneNo("9988776644")).thenReturn(2L);

        Wallet remitterWallet = new Wallet(1L, 10000.0);
        Wallet beneficiaryWallet = new Wallet(2L, 0.0);

//        Mockito.when(walletRepository.findById(1L)).thenReturn(Optional.of(remitterWallet));
//        Mockito.when(walletRepository.findById(2L)).thenReturn(Optional.of(beneficiaryWallet));
        Mockito.when(walletServiceUtils.recordTransfer("9988776655", "9988776644", 1000.0, "test transfer")).thenReturn("transfer message");

        TransferMoneyResponse transferMoneyResponse = walletServiceImp.transferMoney(principal, transferMoneyRequest);

        Assertions.assertTrue(transferMoneyResponse.isSuccess());

    }

    @Test
    void checkBalance() {
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "9988776655";
            }
        };
        Mockito.when(userService.getWalletIdFromPhoneNo("9988776655")).thenReturn(1L);
        Wallet wallet = new Wallet(1L, 10000.0);

        Mockito.when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));

        CheckBalanceResponse checkBalanceResponse = walletServiceImp.checkBalance(principal);
        Assertions.assertTrue(checkBalanceResponse.isSuccess());
    }
}