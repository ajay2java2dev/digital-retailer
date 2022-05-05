CREATE TABLE customer (
     customer_id BIGINT NOT NULL,
     first_name VARCHAR(128) NOT NULL,
     last_name VARCHAR(128) NOT NULL,
     dob DATE NOT NULL,
     PRIMARY KEY (customer_id)
);

CREATE TABLE payment_transactions (
    trans_id  BIGINT NOT NULL AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,
    amount  DOUBLE NOT NULL,
    trans_date_time TIMESTAMP NOT NULL,
    PRIMARY KEY (trans_id),
    CONSTRAINT FK_PMT_TRANS_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);

CREATE TABLE customer_rewards (
    rewards_id BIGINT NOT NULL AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,
    reward_points BIGINT NOT NULL,
    last_updated_dttm TIMESTAMP NOT NULL,
    PRIMARY KEY (rewards_id),
    CONSTRAINT FK_REWARDS_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);