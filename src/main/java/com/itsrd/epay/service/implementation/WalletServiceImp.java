package com.itsrd.epay.service.implementation;

import com.itsrd.epay.dto.requests.walletRequest.DepositMoneyRequest;
import com.itsrd.epay.dto.requests.walletRequest.TransferMoneyRequest;
import com.itsrd.epay.dto.requests.walletRequest.WithdrawMoneyRequest;
import com.itsrd.epay.dto.response.walletResponse.CheckBalanceResponse;
import com.itsrd.epay.dto.response.walletResponse.DepositMoneyResponse;
import com.itsrd.epay.dto.response.walletResponse.TransferMoneyResponse;
import com.itsrd.epay.dto.response.walletResponse.WithdrawMoneyResponse;
import com.itsrd.epay.model.Wallet;
import com.itsrd.epay.repository.WalletRepository;
import com.itsrd.epay.service.UserService;
import com.itsrd.epay.service.WalletService;
import com.itsrd.epay.utils.WalletServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Optional;

@Service
public class WalletServiceImp implements WalletService {

    @Autowired
    private UserService userService;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletServiceUtils walletServiceUtils;


    @Override
    public DepositMoneyResponse depositMoney(Principal principal, DepositMoneyRequest depositMoneyRequest) {
        Long walletId = userService.getWalletIdFromPhoneNo(principal.getName());
        walletServiceUtils.addMoneyToWallet(walletId, depositMoneyRequest.getAmount());
        String message = walletServiceUtils.recordDeposit(principal.getName(), depositMoneyRequest.getAmount(), depositMoneyRequest.getRemark());
        return new DepositMoneyResponse(message, true, HttpStatus.ACCEPTED);
    }

    @Override
    public WithdrawMoneyResponse withdrawMoney(Principal principal, WithdrawMoneyRequest withdrawMoneyRequest) {
        Long walletId = userService.getWalletIdFromPhoneNo(principal.getName());
        walletServiceUtils.withdrawMoneyFromWallet(walletId, withdrawMoneyRequest.getAmount());
        String message = walletServiceUtils.recordWithdrawal(principal.getName(), withdrawMoneyRequest.getAmount(), withdrawMoneyRequest.getRemark());
        return new WithdrawMoneyResponse(message, true, HttpStatus.ACCEPTED);
    }

    @Override
    @Transactional
    public TransferMoneyResponse transferMoney(Principal principal, TransferMoneyRequest transferMoneyRequest) {
        walletServiceUtils.checkForSelfTransfer(principal.getName(), transferMoneyRequest.getBeneficiaryPhoneNo());

        Long remitterWalletId = userService.getWalletIdFromPhoneNo(principal.getName());
        Long beneficiaryWalletId = userService.getWalletIdFromPhoneNo(transferMoneyRequest.getBeneficiaryPhoneNo());

        walletServiceUtils.withdrawMoneyFromWallet(remitterWalletId, transferMoneyRequest.getAmount());
        walletServiceUtils.addMoneyToWallet(beneficiaryWalletId, transferMoneyRequest.getAmount());

        String message = walletServiceUtils.recordTransfer(principal.getName(), transferMoneyRequest.getBeneficiaryPhoneNo(), transferMoneyRequest.getAmount(), transferMoneyRequest.getRemark());
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
