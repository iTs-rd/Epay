package com.itsrd.epay.repository;

import com.itsrd.epay.model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, String> {
    Optional<Otp> findByPhoneNo(String phoneNo);
}
