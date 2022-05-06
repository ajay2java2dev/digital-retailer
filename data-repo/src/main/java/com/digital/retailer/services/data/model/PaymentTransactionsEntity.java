package com.digital.retailer.services.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_transactions")
@Getter
@Setter
public class PaymentTransactionsEntity implements Serializable {
    @Id
    @Column(name = "trans_id", nullable = false)
    @JsonProperty("trans_id")
    private Long transId;

    @Column(name = "customer_id", nullable = false)
    @JsonProperty("customer_id")
    private Long customerId;

    @Column(name = "amount", nullable = false)
    @JsonProperty("amount")
    private Double amount;

    @Column(name = "trans_date_time", nullable = false)
    @JsonProperty("trans_date_time")
    private LocalDateTime transDateTime;
}
