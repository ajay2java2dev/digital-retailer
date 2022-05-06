package com.digital.retailer.services.data.repositories;

import com.digital.retailer.services.data.model.PaymentTransactionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentTransactionsRepository extends JpaRepository<PaymentTransactionsEntity, Long> {

    List<PaymentTransactionsEntity> findAllByCustomerIdAndTransDateTimeGreaterThanEqual(Long customerId, LocalDateTime previousDateTime);

    //List<PaymentTransactionsEntity> findAllCustomerTransactionBySpec(Specification<PaymentTransactionsEntity> specification);
}
