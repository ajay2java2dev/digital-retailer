package com.digital.retailer.services.data.repositories;

import com.digital.retailer.services.data.model.CustomerRewardsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRewardsRepository extends JpaRepository<CustomerRewardsEntity, Long> {
    //No custom implementation required here.
}
