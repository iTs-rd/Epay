package com.itsrd.epay.Repository;

import com.itsrd.epay.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
