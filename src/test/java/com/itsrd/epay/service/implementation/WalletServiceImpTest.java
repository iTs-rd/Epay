package com.itsrd.epay.service.implementation;

import com.itsrd.epay.dto.requests.walletRequest.DepositMoneyRequest;
import com.itsrd.epay.dto.requests.walletRequest.TransferMoneyRequest;
import com.itsrd.epay.dto.requests.walletRequest.WithdrawMoneyRequest;
import com.itsrd.epay.dto.response.walletResponse.CheckBalanceResponse;
import com.itsrd.epay.dto.response.walletResponse.DepositMoneyResponse;
import com.itsrd.epay.dto.response.walletResponse.TransferMoneyResponse;
import com.itsrd.epay.dto.response.walletResponse.WithdrawMoneyResponse;
import com.itsrd.epay.exception.CanNotTransferMoneyToSelf;
import com.itsrd.epay.exception.InsufficientBalance;
import com.itsrd.epay.model.Wallet;
import com.itsrd.epay.repository.WalletRepository;
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
class WalletServiceImpTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private UserService userService;

    @Mock
    private WalletServiceUtils walletServiceUtils;

    @InjectMocks
    private WalletServiceImp walletServiceImp;


    @Test
    void depositMoney_Test() {
        DepositMoneyRequest depositMoneyRequest = new DepositMoneyRequest(1000.0, "test deposit remark");
        Principal principal = Mockito.mock(Principal.class);

        Mockito.when(principal.getName()).thenReturn("9988776655");
        Mockito.when(userService.getWalletIdFromPhoneNo("9988776655")).thenReturn(1L);
        Mockito.doNothing().when(walletServiceUtils).addMoneyToWallet(1L, 1000.0);
        Mockito.when(walletServiceUtils.recordDeposit("9988776655", 1000.0, "test deposit remark")).thenReturn("deposit message");

        DepositMoneyResponse depositMoneyResponse = walletServiceImp.depositMoney(principal, depositMoneyRequest);

        Assertions.assertTrue(depositMoneyResponse.isSuccess());

    }

    @Test
    void withdrawMoney_Successful_Test() {
        Principal principal = Mockito.mock(Principal.class);
        WithdrawMoneyRequest withdrawMoneyRequest = new WithdrawMoneyRequest(1000.0, "test withdrawal remark");

        Mockito.when(principal.getName()).thenReturn("9988776655");
        Mockito.when(userService.getWalletIdFromPhoneNo("9988776655")).thenReturn(1L);
        Mockito.doNothing().when(walletServiceUtils).withdrawMoneyFromWallet(1L, 1000.0);
        Mockito.when(walletServiceUtils.recordWithdrawal("9988776655", 1000.0, "test withdrawal remark")).thenReturn("withdrawal message");

        WithdrawMoneyResponse withdrawMoneyResponse = walletServiceImp.withdrawMoney(principal, withdrawMoneyRequest);

        Assertions.assertTrue(withdrawMoneyResponse.isSuccess());

    }


    @Test
    void withdrawalMoney_Failure_Test() {
        Principal principal = Mockito.mock(Principal.class);
        WithdrawMoneyRequest withdrawMoneyRequest = new WithdrawMoneyRequest(1000.0, "test withdrawal remark");

        Mockito.when(principal.getName()).thenReturn("9988776655");
        Mockito.when(userService.getWalletIdFromPhoneNo("9988776655")).thenReturn(1L);
        Mockito.doThrow(new InsufficientBalance()).when(walletServiceUtils).withdrawMoneyFromWallet(1L, 1000.0);

        Assertions.assertThrows(InsufficientBalance.class, () -> walletServiceImp.withdrawMoney(principal, withdrawMoneyRequest));
    }

    @Test
    void transferMoney_Successful_Test() {
        Principal principal = Mockito.mock(Principal.class);
        TransferMoneyRequest transferMoneyRequest = new TransferMoneyRequest(1000.0, "test transfer remark", "9988776644");

        Mockito.when(principal.getName()).thenReturn("9988776655");
        Mockito.doNothing().when(walletServiceUtils).checkForSelfTransfer("9988776655", "9988776644");
        Mockito.when(userService.getWalletIdFromPhoneNo("9988776655")).thenReturn(1L);
        Mockito.when(userService.getWalletIdFromPhoneNo("9988776644")).thenReturn(2L);
        Mockito.doNothing().when(walletServiceUtils).withdrawMoneyFromWallet(1L, 1000.0);
        Mockito.doNothing().when(walletServiceUtils).addMoneyToWallet(2L, 1000.0);
        Mockito.when(walletServiceUtils.recordTransfer("9988776655", "9988776644", 1000.0, "test transfer remark")).thenReturn("test transfer successful");

        TransferMoneyResponse transferMoneyResponse = walletServiceImp.transferMoney(principal, transferMoneyRequest);

        Assertions.assertTrue(transferMoneyResponse.isSuccess());

    }

    @Test
    void transferMoney_selfTransferMoney_Test() {
        Principal principal = Mockito.mock(Principal.class);
        TransferMoneyRequest transferMoneyRequest = new TransferMoneyRequest(1000.0, "test transfer remark", "9988776655");

        Mockito.when(principal.getName()).thenReturn("9988776655");
        Mockito.doThrow(new CanNotTransferMoneyToSelf()).when(walletServiceUtils).checkForSelfTransfer("9988776655", "9988776655");

        Assertions.assertThrows(CanNotTransferMoneyToSelf.class, () -> walletServiceImp.transferMoney(principal, transferMoneyRequest));
    }

    @Test
    void transferMoney_FromInsufficientFundsWallet_Test() {
        Principal principal = Mockito.mock(Principal.class);
        TransferMoneyRequest transferMoneyRequest = new TransferMoneyRequest(1000.0, "test transfer remark", "9988776644");

        Mockito.when(principal.getName()).thenReturn("9988776655");
        Mockito.doNothing().when(walletServiceUtils).checkForSelfTransfer("9988776655", "9988776644");
        Mockito.when(userService.getWalletIdFromPhoneNo("9988776655")).thenReturn(1L);
        Mockito.when(userService.getWalletIdFromPhoneNo("9988776644")).thenReturn(2L);
        Mockito.doThrow(new InsufficientBalance()).when(walletServiceUtils).withdrawMoneyFromWallet(1L, 1000.0);

        Assertions.assertThrows(InsufficientBalance.class, () -> walletServiceImp.transferMoney(principal, transferMoneyRequest));
    }

    @Test
    void checkBalance_Test() {
        Principal principal = Mockito.mock(Principal.class);
        Wallet wallet = new Wallet();

        Mockito.when(principal.getName()).thenReturn("9988776655");
        Mockito.when(userService.getWalletIdFromPhoneNo("9988776655")).thenReturn(1L);
        Mockito.when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));

        CheckBalanceResponse checkBalanceResponse = walletServiceImp.checkBalance(principal);

        Assertions.assertTrue(checkBalanceResponse.isSuccess());

    }
}
