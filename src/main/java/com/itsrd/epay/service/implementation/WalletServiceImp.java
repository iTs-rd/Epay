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
import com.itsrd.epay.service.TransactionService;
import com.itsrd.epay.service.UserService;
import com.itsrd.epay.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Objects;
import java.util.Optional;

@Service
public class WalletServiceImp implements WalletService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private WalletRepository walletRepository;

    private void checkForInsufficientBalance(Double currentFunds, Double withdrawAmount) {
        if (currentFunds < withdrawAmount)
            throw new InsufficientBalance();
    }

    private void checkForSelfTransfer(String remitterUserId, String beneficiaryUserId) {
        if (Objects.equals(remitterUserId, beneficiaryUserId))
            throw new CanNotTransferMoneyToSelf();

    }

    private String recordWithdrawal(String phoneNo, Double amount, String remark) {
        String description = "Rupee " + amount + " has been debited from your wallet";
        transactionService.addRecord(phoneNo, "Withdraw", amount, description, remark);
        return description;
    }

    private String recordDeposit(String phoneNo, Double amount, String remark) {
        String description = "Rupee " + amount + " has been credited to your wallet";
        transactionService.addRecord(phoneNo, "Deposit", amount, description, remark);
        return description;
    }

    private String recordTransfer(String remitterPhoneNo, String beneficiaryPhoneNo, Double amount, String remark) {
        String remitterDescription = "Rupee " + amount + " has been Transfer to User: " + beneficiaryPhoneNo;
        String beneficiaryDescription = "Rupee " + amount + " has been send by User: " + remitterPhoneNo;

        transactionService.addRecord(remitterPhoneNo, "Send", amount, remitterDescription, remark);
        transactionService.addRecord(beneficiaryPhoneNo, "Receive", amount, beneficiaryDescription, remark);
        return remitterDescription;

    }

    private void withdrawMoneyFromWallet(Long walletId, Double amount) {
        Optional<Wallet> wallet = walletRepository.findById(walletId);
        if (wallet.isEmpty())
            throw new RuntimeException("Withdrawal Wallet Not Found!");

        Double currentFunds = wallet.get().getAmount();
        checkForInsufficientBalance(currentFunds, amount);

        Wallet newWallet = new Wallet(walletId, currentFunds - amount);
        walletRepository.save(newWallet);
    }

    private void addMoneyToWallet(Long walletId, Double amount) {
        Optional<Wallet> wallet = walletRepository.findById(walletId);
        if (wallet.isEmpty())
            throw new RuntimeException("Depositor Wallet Not Found!");

        Double currentFunds = wallet.get().getAmount();
        Wallet newWallet = new Wallet(walletId, currentFunds + amount);
        walletRepository.save(newWallet);

    }

    @Override
    public DepositMoneyResponse depositMoney(Principal principal, DepositMoneyRequest depositMoneyRequest) {
        Long walletId = userService.getWalletIdFromPhoneNo(principal.getName());
        addMoneyToWallet(walletId, depositMoneyRequest.getAmount());
        String message = recordDeposit(principal.getName(), depositMoneyRequest.getAmount(), depositMoneyRequest.getRemark());
        return new DepositMoneyResponse(message, true, HttpStatus.ACCEPTED);
    }

    @Override
    public WithdrawMoneyResponse withdrawMoney(Principal principal, WithdrawMoneyRequest withdrawMoneyRequest) {
        Long walletId = userService.getWalletIdFromPhoneNo(principal.getName());
        withdrawMoneyFromWallet(walletId, withdrawMoneyRequest.getAmount());
        String message = recordWithdrawal(principal.getName(), withdrawMoneyRequest.getAmount(), withdrawMoneyRequest.getRemark());
        return new WithdrawMoneyResponse(message, true, HttpStatus.ACCEPTED);
    }

    @Override
    @Transactional
    public TransferMoneyResponse transferMoney(Principal principal, TransferMoneyRequest transferMoneyRequest) {
        checkForSelfTransfer(principal.getName(), transferMoneyRequest.getBeneficiaryPhoneNo());

        Long remitterWalletId = userService.getWalletIdFromPhoneNo(principal.getName());
        Long beneficiaryWalletId = userService.getWalletIdFromPhoneNo(transferMoneyRequest.getBeneficiaryPhoneNo());

        withdrawMoneyFromWallet(remitterWalletId, transferMoneyRequest.getAmount());
        addMoneyToWallet(beneficiaryWalletId, transferMoneyRequest.getAmount());

        String message = recordTransfer(principal.getName(), transferMoneyRequest.getBeneficiaryPhoneNo(), transferMoneyRequest.getAmount(), transferMoneyRequest.getRemark());
        return new TransferMoneyResponse(message, true, HttpStatus.ACCEPTED);
    }

    @Override
    public CheckBalanceResponse checkBalance(Principal principal) {
        Long walletId = userService.getWalletIdFromPhoneNo(principal.getName());

        Optional<Wallet> wallet = walletRepository.findById(walletId);
        if (wallet.isEmpty())
            throw new RuntimeException("Wallet Not Found!");

        String message = "Your current Wallet Balance is " + wallet.get().getAmount();
        return new CheckBalanceResponse(message, true, HttpStatus.OK);
    }
}
