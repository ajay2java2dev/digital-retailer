package com.digital.retailer.services.impl.util;

import com.digital.retailer.services.data.model.CustomerRewardsEntity;
import com.digital.retailer.services.data.model.PaymentTransactionsEntity;
import com.digital.retailer.services.openapi.model.CustomerRewardPoints;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class TestUtil {

    ObjectMapper mapper = new ObjectMapper().findAndRegisterModules(); //find LocalDateTime support for object mapper as well

    private static final String CUSTOMER12345_PAYMENTS = "src/test/resources/data/Customer12345PaymentTransactions.json";
    private static final String CUSTOMER99999_PAYMENTS = "src/test/resources/data/Customer99999PaymentTransactions.json";

    private static final String CUSTOMER12345_REWARD_POINTS = "src/test/resources/data/Customer12345RewardPoints.json";
    private static final String CUSTOMER99999_REWARD_POINTS= "src/test/resources/data/Customer99999RewardPoints.json";

    public List<PaymentTransactionsEntity> retrieveCustomerSamplePaymentRecords (Long customerId) throws IOException {
        if (customerId == 99999l) {
            return mapper.readerForListOf(PaymentTransactionsEntity.class).readValue(Paths.get(CUSTOMER99999_PAYMENTS).toFile());
        } else if (customerId == 12345l) {
            return mapper.readerForListOf(PaymentTransactionsEntity.class).readValue(Paths.get(CUSTOMER12345_PAYMENTS).toFile());
        }
        return null;
    }

    public CustomerRewardPoints retrieveCustomerRewardPoints (Long customerId) throws IOException {
        if (customerId == 99999l) {
            return mapper.readValue(Paths.get(CUSTOMER99999_REWARD_POINTS).toFile(), CustomerRewardPoints.class);
        } else if (customerId == 12345l) {
            return mapper.readValue(Paths.get(CUSTOMER12345_REWARD_POINTS).toFile(), CustomerRewardPoints.class);
        }
        return null;
    }
}
