package com.itsrd.epay.service;

import com.itsrd.epay.dto.DepositMoneyRequest;
import com.itsrd.epay.dto.TransferMoneyRequest;
import com.itsrd.epay.dto.WithdrawMoneyRequest;

public interface WalletService {
    String depositMoney(DepositMoneyRequest depositMoneyRequest);

    String withdrawMoney(WithdrawMoneyRequest withdrawMoneyRequest);

    String checkBalance(Long id);

    String transferMoney(TransferMoneyRequest transferMoneyRequest);
}
