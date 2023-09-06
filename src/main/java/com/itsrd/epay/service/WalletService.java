package com.itsrd.epay.service;

import com.itsrd.epay.dto.requests.DepositMoneyRequest;
import com.itsrd.epay.dto.requests.TransferMoneyRequest;
import com.itsrd.epay.dto.requests.WithdrawMoneyRequest;

import java.security.Principal;

public interface WalletService {
    String depositMoney(Principal principal, DepositMoneyRequest depositMoneyRequest);

    String withdrawMoney(Principal principal, WithdrawMoneyRequest withdrawMoneyRequest);

    String checkBalance(Principal principal);

    String transferMoney(Principal principal, TransferMoneyRequest transferMoneyRequest);
}
