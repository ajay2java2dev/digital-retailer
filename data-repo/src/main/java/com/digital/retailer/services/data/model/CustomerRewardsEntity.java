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
@Table(name = "customer_rewards")
@Getter
@Setter
public class CustomerRewardsEntity implements Serializable {
    @Id
    @Column(name = "rewards_id", nullable = false)
    @JsonProperty("rewards_id")
    private Long rewardsId;

    @Column(name = "customer_id", nullable = false)
    @JsonProperty("customer_id")
    private Long customerId;

    @Column(name = "reward_points", nullable = false)
    @JsonProperty("reward_points")
    private Long rewardPoints;

    @Column(name = "last_updated_dttm", nullable = false)
    @JsonProperty("last_updated_dttm")
    private LocalDateTime lastUpdatedDttm;
}
