package com.itsrd.epay.service;

import com.itsrd.epay.dto.DepositMoneyRequest;
import com.itsrd.epay.dto.TransferMoneyRequest;
import com.itsrd.epay.dto.WithdrawMoneyRequest;

import java.security.Principal;

public interface WalletService {
    String depositMoney(Principal principal, DepositMoneyRequest depositMoneyRequest);

    String withdrawMoney(Principal principal, WithdrawMoneyRequest withdrawMoneyRequest);

    String checkBalance(Principal principal);

    String transferMoney(Principal principal, TransferMoneyRequest transferMoneyRequest);
}
