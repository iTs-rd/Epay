package com.itsrd.epay.Repository;

import com.itsrd.epay.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
