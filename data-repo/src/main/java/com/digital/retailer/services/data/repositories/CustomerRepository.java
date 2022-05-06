package com.digital.retailer.services.data.repositories;

import com.digital.retailer.services.data.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    //No custom implementation required here.
}
