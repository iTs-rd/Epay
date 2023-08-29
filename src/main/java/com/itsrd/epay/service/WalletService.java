package com.itsrd.epay.service;

import com.itsrd.epay.request.DepositMoneyRequest;
import com.itsrd.epay.request.TransferMoneyRequest;
import com.itsrd.epay.request.WithdrawMoneyRequest;

public interface WalletService {
    String depositMoney(DepositMoneyRequest depositMoneyRequest);

    String withdrawMoney(WithdrawMoneyRequest withdrawMoneyRequest);

    String checkBalance(Long id);

    String transferMoney(TransferMoneyRequest transferMoneyRequest);

}
