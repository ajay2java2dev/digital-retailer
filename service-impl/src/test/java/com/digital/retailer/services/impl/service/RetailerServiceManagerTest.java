package com.digital.retailer.services.impl.service;

import com.digital.retailer.services.data.model.CustomerEntity;
import com.digital.retailer.services.data.model.CustomerRewardsEntity;
import com.digital.retailer.services.data.repositories.CustomerRepository;
import com.digital.retailer.services.data.repositories.CustomerRewardsRepository;
import com.digital.retailer.services.data.repositories.PaymentTransactionsRepository;
import com.digital.retailer.services.impl.config.H2ConsoleConfig;
import com.digital.retailer.services.impl.manager.RetailerServiceManager;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Optional;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class RetailerServiceManagerTest {

    @Autowired
    private PointsService pointsService;

    @Autowired
    private RetailerServiceManager retailerServiceManager;

    @Autowired
    H2ConsoleConfig h2ConsoleConfig;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private PaymentTransactionsRepository paymentTransactionsRepository;

    @MockBean
    private CustomerRewardsRepository customerRewardsRepository;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterAll
    void destory(){
        h2ConsoleConfig.webServer.stop();
    }

    @Test
    void whenValidCustomerId_ThenReturnCustomerEntity() {
        var newCustomerEntity = new CustomerEntity();
        newCustomerEntity.setCustomerId(12345l);
        Mockito.when(customerRepository.findById(newCustomerEntity.getCustomerId())).thenReturn(Optional.ofNullable(newCustomerEntity));
        StepVerifier.create(retailerServiceManager.retrieveCustomerDetails(12345l))
                .expectNext(newCustomerEntity)
                .verifyComplete();

    }

    @Test
    void whenInValidCustomerId_ThenReturnNoEntity() {
        Mockito.when(customerRepository.findById(55555l)).thenReturn(Optional.empty());
        //if no records, onNext signal will not be expected and expectNextCount would be 0 and action would complete
        StepVerifier.create(retailerServiceManager.retrieveCustomerDetails(55555l))
                .expectNextCount(0).verifyComplete();
    }



}
