package com.itsrd.epay.utils;

import com.itsrd.epay.exception.CanNotTransferMoneyToSelf;
import com.itsrd.epay.exception.InsufficientBalance;
import com.itsrd.epay.model.Wallet;
import com.itsrd.epay.repository.WalletRepository;
import com.itsrd.epay.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class WalletServiceUtils {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private WalletRepository walletRepository;

    public void checkForInsufficientBalance(Double currentFunds, Double withdrawAmount) {
        if (currentFunds < withdrawAmount)
            throw new InsufficientBalance();
    }

    public void checkForSelfTransfer(String remitterPhoneNo, String beneficiaryPhoneNo) {
        if (Objects.equals(remitterPhoneNo, beneficiaryPhoneNo))
            throw new CanNotTransferMoneyToSelf();

    }

    public String recordWithdrawal(String phoneNo, Double amount, String remark) {
        String description = "Rupee " + amount + " has been debited from your wallet";
        transactionService.addRecord(phoneNo, "Withdraw", amount, description, remark);
        return description;
    }

    public String recordDeposit(String phoneNo, Double amount, String remark) {
        String description = "Rupee " + amount + " has been credited to your wallet";
        transactionService.addRecord(phoneNo, "Deposit", amount, description, remark);
        return description;
    }

    public String recordTransfer(String remitterPhoneNo, String beneficiaryPhoneNo, Double amount, String remark) {
        String remitterDescription = "Rupee " + amount + " has been Transfer to User: " + beneficiaryPhoneNo;
        String beneficiaryDescription = "Rupee " + amount + " has been send by User: " + remitterPhoneNo;

        transactionService.addRecord(remitterPhoneNo, "Send", amount, remitterDescription, remark);
        transactionService.addRecord(beneficiaryPhoneNo, "Receive", amount, beneficiaryDescription, remark);
        return remitterDescription;

    }

    public void withdrawMoneyFromWallet(Long walletId, Double amount) {
        Optional<Wallet> wallet = walletRepository.findById(walletId);
        if (wallet.isEmpty())
            throw new RuntimeException("Withdrawal Wallet Not Found!");

        Double currentFunds = wallet.get().getAmount();
//
        checkForInsufficientBalance(currentFunds, amount);

        Wallet newWallet = new Wallet(walletId, currentFunds - amount);
        walletRepository.save(newWallet);
    }

    public void addMoneyToWallet(Long walletId, Double amount) {
        Optional<Wallet> wallet = walletRepository.findById(walletId);
        if (wallet.isEmpty())
            throw new RuntimeException("Depositor Wallet Not Found!");
        Double currentFunds = wallet.get().getAmount();
        Wallet newWallet = new Wallet(walletId, currentFunds + amount);
        walletRepository.save(newWallet);

    }
}
