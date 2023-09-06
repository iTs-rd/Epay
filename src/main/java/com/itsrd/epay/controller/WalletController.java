package com.itsrd.epay.controller;


import com.itsrd.epay.dto.requests.DepositMoneyRequest;
import com.itsrd.epay.dto.requests.TransferMoneyRequest;
import com.itsrd.epay.dto.requests.WithdrawMoneyRequest;
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
    public ResponseEntity<String> depositMoney(Principal principal, @Valid @RequestBody DepositMoneyRequest depositMoneyRequest) {
        return new ResponseEntity<>(walletService.depositMoney(principal, depositMoneyRequest), HttpStatus.OK);
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<String> withdrawalMoney(Principal principal, @Valid @RequestBody WithdrawMoneyRequest withdrawMoneyRequest) {
        return new ResponseEntity<>(walletService.withdrawMoney(principal, withdrawMoneyRequest), HttpStatus.OK);
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(Principal principal, @Valid @RequestBody TransferMoneyRequest transferMoneyRequest) {
        return new ResponseEntity<>(walletService.transferMoney(principal, transferMoneyRequest), HttpStatus.OK);
    }

    @GetMapping("/checkbalance")
    public ResponseEntity<String> checkBalance(Principal principal) {
//        Long userId = Long.valueOf(id);
        return new ResponseEntity<>(walletService.checkBalance(principal), HttpStatus.OK);
    }
}
