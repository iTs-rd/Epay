package com.itsrd.epay.repository;

import com.itsrd.epay.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
