package com.rebirth.mywebstore.domain.repositories;

import com.rebirth.mywebstore.domain.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
