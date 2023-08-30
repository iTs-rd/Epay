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

import java.util.Optional;

@Service
public class WalletServiceImp implements WalletService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private WalletRepository walletRepository;


    @Override
    public String depositMoney(DepositMoneyRequest depositMoneyRequest) {
        Long walletId = userService.getWalletIdFromUserId(depositMoneyRequest.getRemitterUserId());

        Optional<Wallet> wallet = walletRepository.findById(walletId);
        if (wallet.isEmpty())
            throw new RuntimeException("Something went wrong");

        Double currentFunds = wallet.get().getAmount();
        Wallet newWallet = new Wallet(walletId, currentFunds + depositMoneyRequest.getAmount());
        walletRepository.save(newWallet);

        String type = "Deposit";
        String description = "Rupee " + depositMoneyRequest.getAmount() + " has been credited to your wallet";

        transactionService.addRecord(depositMoneyRequest.getRemitterUserId(), type, description, depositMoneyRequest.getRemark());

        return description;
    }

    @Override
    public String withdrawMoney(WithdrawMoneyRequest withdrawMoneyRequest) {
        Long walletId = userService.getWalletIdFromUserId(withdrawMoneyRequest.getRemitterUserId());

        Optional<Wallet> wallet = walletRepository.findById(walletId);
        if (wallet.isEmpty())
            throw new RuntimeException("Something went wrong");

        Double currentFunds = wallet.get().getAmount();

        if (currentFunds < withdrawMoneyRequest.getAmount())
            throw new InsufficientBalance();

        Wallet newWallet = new Wallet(walletId, currentFunds - withdrawMoneyRequest.getAmount());
        walletRepository.save(newWallet);

        String type = "Withdraw";
        String description = "Rupee " + withdrawMoneyRequest.getAmount() + " has been debited from your wallet";

        transactionService.addRecord(withdrawMoneyRequest.getRemitterUserId(), type, description, withdrawMoneyRequest.getRemark());

        return description;
    }

    @Override
    @Transactional
    public String transferMoney(TransferMoneyRequest transferMoneyRequest) {
        if(transferMoneyRequest.getRemitterUserId()==transferMoneyRequest.getBeneficiaryUserId())
            throw new CanNotTransferMoneyToSelf();

        Long remitterWalletId = userService.getWalletIdFromUserId(transferMoneyRequest.getRemitterUserId());

        Optional<Wallet> remitterWallet = walletRepository.findById(remitterWalletId);
        if (remitterWallet.isEmpty())
            throw new RuntimeException("Something went wrong");

        Double remitterCurrentFunds = remitterWallet.get().getAmount();
        if (remitterCurrentFunds < transferMoneyRequest.getAmount())
            throw new InsufficientBalance();

        Long beneficiaryWalletId = userService.getWalletIdFromUserId(transferMoneyRequest.getBeneficiaryUserId());
        Optional<Wallet> beneficiaryWallet = walletRepository.findById(beneficiaryWalletId);
        if (beneficiaryWallet.isEmpty())
            throw new RuntimeException("Something went wrong");

        Double beneficiaryCurrentFunds = beneficiaryWallet.get().getAmount();

        Wallet newRemitterWallet = new Wallet(remitterWalletId, remitterCurrentFunds - transferMoneyRequest.getAmount());
        Wallet newBeneficiaryWallet = new Wallet(beneficiaryWalletId, beneficiaryCurrentFunds + transferMoneyRequest.getAmount());

        walletRepository.save(newRemitterWallet);
        walletRepository.save(newBeneficiaryWallet);

        String type = "Transfer";
        String description = "Rupee " + transferMoneyRequest.getAmount() + " has been Transfer to " + transferMoneyRequest.getBeneficiaryUserId();

        transactionService.addRecord(transferMoneyRequest.getRemitterUserId(), type, description, transferMoneyRequest.getRemark());
        return description;
    }

    @Override
    public String checkBalance(Long id) {
        Long walletId = userService.getWalletIdFromUserId(id);

        Optional<Wallet> wallet = walletRepository.findById(walletId);
        if (wallet.isEmpty())
            throw new RuntimeException("Something went wrong");

        return "Your current Wallet Balance is " + wallet.get().getAmount();
    }

}
