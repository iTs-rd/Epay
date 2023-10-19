package com.itsrd.epay.service;

import com.itsrd.epay.dto.requests.walletRequest.DepositMoneyRequest;
import com.itsrd.epay.dto.requests.walletRequest.TransferMoneyRequest;
import com.itsrd.epay.dto.requests.walletRequest.WithdrawMoneyRequest;
import com.itsrd.epay.dto.response.walletResponse.CheckBalanceResponse;
import com.itsrd.epay.dto.response.walletResponse.DepositMoneyResponse;
import com.itsrd.epay.dto.response.walletResponse.TransferMoneyResponse;
import com.itsrd.epay.dto.response.walletResponse.WithdrawMoneyResponse;

import java.security.Principal;

public interface WalletService {
    DepositMoneyResponse depositMoney(Principal principal, DepositMoneyRequest depositMoneyRequest);

    WithdrawMoneyResponse withdrawMoney(Principal principal, WithdrawMoneyRequest withdrawMoneyRequest);

    TransferMoneyResponse transferMoney(Principal principal, TransferMoneyRequest transferMoneyRequest);

    CheckBalanceResponse checkBalance(Principal principal);
}
