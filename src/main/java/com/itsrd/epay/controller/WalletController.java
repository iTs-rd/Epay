package com.itsrd.epay.controller;


import com.itsrd.epay.dto.DepositMoneyRequest;
import com.itsrd.epay.dto.TransferMoneyRequest;
import com.itsrd.epay.dto.WithdrawMoneyRequest;
import com.itsrd.epay.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallet")
@EnableTransactionManagement
public class WalletController {


    @Autowired
    private WalletService walletService;


    @PostMapping("/deposit")
    public ResponseEntity<String> depositMoney(@Valid @RequestBody DepositMoneyRequest depositMoneyRequest) {
        return new ResponseEntity<>(walletService.depositMoney(depositMoneyRequest), HttpStatus.OK);
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<String> withdrawalMoney(@Valid @RequestBody WithdrawMoneyRequest withdrawMoneyRequest) {
        return new ResponseEntity<>(walletService.withdrawMoney(withdrawMoneyRequest), HttpStatus.OK);
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(@Valid @RequestBody TransferMoneyRequest transferMoneyRequest) {
        return new ResponseEntity<>(walletService.transferMoney(transferMoneyRequest), HttpStatus.OK);
    }

    @PostMapping("/checkbalance")
    public ResponseEntity<String> checkBalance(@RequestBody String id) {
        Long userId = Long.valueOf(id);
        return new ResponseEntity<>(walletService.checkBalance(userId), HttpStatus.OK);
    }
}
