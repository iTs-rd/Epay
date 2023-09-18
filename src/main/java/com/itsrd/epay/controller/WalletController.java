package com.itsrd.epay.controller;


import com.itsrd.epay.dto.requests.walletRequest.DepositMoneyRequest;
import com.itsrd.epay.dto.requests.walletRequest.TransferMoneyRequest;
import com.itsrd.epay.dto.requests.walletRequest.WithdrawMoneyRequest;
import com.itsrd.epay.dto.response.walletResponse.CheckBalanceResponse;
import com.itsrd.epay.dto.response.walletResponse.DepositMoneyResponse;
import com.itsrd.epay.dto.response.walletResponse.TransferMoneyResponse;
import com.itsrd.epay.dto.response.walletResponse.WithdrawMoneyResponse;
import com.itsrd.epay.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/wallet")
@EnableTransactionManagement
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping("/deposit")
    public ResponseEntity<DepositMoneyResponse> depositMoney(Principal principal, @Valid @RequestBody DepositMoneyRequest depositMoneyRequest) {
        return new ResponseEntity<>(walletService.depositMoney(principal, depositMoneyRequest), HttpStatus.ACCEPTED);
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<WithdrawMoneyResponse> withdrawalMoney(Principal principal, @Valid @RequestBody WithdrawMoneyRequest withdrawMoneyRequest) {
        return new ResponseEntity<>(walletService.withdrawMoney(principal, withdrawMoneyRequest), HttpStatus.ACCEPTED);
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferMoneyResponse> transferMoney(Principal principal, @Valid @RequestBody TransferMoneyRequest transferMoneyRequest) {
        return new ResponseEntity<>(walletService.transferMoney(principal, transferMoneyRequest), HttpStatus.ACCEPTED);
    }

    @GetMapping("/checkbalance")
    public ResponseEntity<CheckBalanceResponse> checkBalance(Principal principal) {
        return new ResponseEntity<>(walletService.checkBalance(principal), HttpStatus.OK);
    }
}
