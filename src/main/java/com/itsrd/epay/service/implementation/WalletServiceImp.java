package com.itsrd.epay.service.implementation;

import com.itsrd.epay.exception.CanNotTransferMoneyToSelf;
import com.itsrd.epay.exception.InsufficientBalance;
import com.itsrd.epay.model.Wallet;
import com.itsrd.epay.repository.WalletRepository;
import com.itsrd.epay.request.DepositMoneyRequest;
import com.itsrd.epay.request.TransferMoneyRequest;
import com.itsrd.epay.request.WithdrawMoneyRequest;
import com.itsrd.epay.service.TransactionService;
import com.itsrd.epay.service.UserService;
import com.itsrd.epay.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private void checkForSelfTransfer(Long remitterUserId, Long beneficiaryUserId) {
        if (Objects.equals(remitterUserId, beneficiaryUserId))
            throw new CanNotTransferMoneyToSelf();

    }

    private String logWithdrawal(Long account, Double amount, String remark) {
        String description = "Rupee " + amount + " has been debited from your wallet";
        transactionService.addRecord(account, "Withdraw", amount, description, remark);
        return description;
    }

    private String logDeposit(Long account, Double amount, String remark) {
        String description = "Rupee " + amount + " has been credited to your wallet";
        transactionService.addRecord(account, "Deposit", amount, description, remark);
        return description;
    }

    private String logTransfer(Long remitter, Long beneficiary, Double amount, String remark) {
        String remitterDescription = "Rupee " + amount + " has been Transfer to user ID " + beneficiary;
        String beneficiaryDescription = "Rupee " + amount + " has been send by User ID " + remitter;

        transactionService.addRecord(remitter, "Send", amount, remitterDescription, remark);
        transactionService.addRecord(beneficiary, "Receive", amount, beneficiaryDescription, remark);
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
    public String depositMoney(DepositMoneyRequest depositMoneyRequest) {
        Long walletId = userService.getWalletIdFromUserId(depositMoneyRequest.getRemitterUserId());
        addMoneyToWallet(walletId, depositMoneyRequest.getAmount());
        return logDeposit(depositMoneyRequest.getRemitterUserId(), depositMoneyRequest.getAmount(), depositMoneyRequest.getRemark());
    }

    @Override
    public String withdrawMoney(WithdrawMoneyRequest withdrawMoneyRequest) {
        Long walletId = userService.getWalletIdFromUserId(withdrawMoneyRequest.getRemitterUserId());
        withdrawMoneyFromWallet(walletId, withdrawMoneyRequest.getAmount());
        return logWithdrawal(withdrawMoneyRequest.getRemitterUserId(), withdrawMoneyRequest.getAmount(), withdrawMoneyRequest.getRemark());
    }

    @Override
    @Transactional
    public String transferMoney(TransferMoneyRequest transferMoneyRequest) {
        checkForSelfTransfer(transferMoneyRequest.getRemitterUserId(), transferMoneyRequest.getBeneficiaryUserId());

        Long remitterWalletId = userService.getWalletIdFromUserId(transferMoneyRequest.getRemitterUserId());
        Long beneficiaryWalletId = userService.getWalletIdFromUserId(transferMoneyRequest.getBeneficiaryUserId());

        withdrawMoneyFromWallet(remitterWalletId, transferMoneyRequest.getAmount());
        addMoneyToWallet(beneficiaryWalletId, transferMoneyRequest.getAmount());

        return logTransfer(transferMoneyRequest.getRemitterUserId(), transferMoneyRequest.getBeneficiaryUserId(), transferMoneyRequest.getAmount(), transferMoneyRequest.getRemark());
    }

    @Override
    public String checkBalance(Long id) {
        Long walletId = userService.getWalletIdFromUserId(id);

        Optional<Wallet> wallet = walletRepository.findById(walletId);
        if (wallet.isEmpty())
            throw new RuntimeException("Wallet Not Found!");

        return "Your current Wallet Balance is " + wallet.get().getAmount();
    }
}
