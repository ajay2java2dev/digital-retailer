package com.digital.retailer.services.impl.integrations;

import com.digital.retailer.services.impl.ServiceImplApplication;
import com.digital.retailer.services.impl.config.H2ConsoleConfig;
import com.digital.retailer.services.openapi.model.CustomerRewardPoints;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {ServiceImplApplication.class})
@AutoConfigureWebTestClient(timeout = "30000")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class DigitalRetailerIntegrationTests {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    H2ConsoleConfig h2ConsoleConfig;

    @AfterAll
    void destory(){
        h2ConsoleConfig.webServer.stop();
    }

    @Test
    void whenValidCustomerId_ThenReturnRewardPoints() {
        webTestClient.get()
                .uri("/customers/99999/reward-points")
                .header("X-Application-Id", "blah")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(CustomerRewardPoints.class)
                .consumeWith(response -> {
                    assertNotNull(response.getResponseBody());
                    assertNotNull(response.getResponseBody().getCustomerId());
                    assertNotNull(response.getResponseBody().getRewardPoints());
                    assertEquals(99999, response.getResponseBody().getCustomerId());
                    assertEquals(90, response.getResponseBody().getRewardPoints());
                });
    }

    @Test
    void whenInValidCustomerId_ThenReturn404NotFound() {
        webTestClient.get()
                .uri("/customers/999999/reward-points")
                .header("X-Application-Id", "blah")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void whenValidCustomerIdWithNoValidTransactionsIn3Months_ThenReturnNoRewardPoints() {
        webTestClient.get()
                .uri("/customers/98765/reward-points")
                .header("X-Application-Id", "blah")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(CustomerRewardPoints.class)
                .consumeWith(response -> {
                    assertNotNull(response.getResponseBody());
                    assertNotNull(response.getResponseBody().getCustomerId());
                    assertNotNull(response.getResponseBody().getRewardPoints());
                    assertEquals(98765, response.getResponseBody().getCustomerId());
                    assertEquals(0, response.getResponseBody().getRewardPoints());
                });;
    }

    @Test
    void whenValidCustomerIdWithValidTransactionsIn5Months_ThenReturnRewardPoints() {
        webTestClient.get()
                .uri("/customers/98765/reward-points?numOfMonths=5")
                .header("X-Application-Id", "blah")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(CustomerRewardPoints.class)
                .consumeWith(response -> {
                    assertNotNull(response.getResponseBody());
                    assertNotNull(response.getResponseBody().getCustomerId());
                    assertNotNull(response.getResponseBody().getRewardPoints());
                    assertEquals(98765, response.getResponseBody().getCustomerId());
                    assertEquals(90, response.getResponseBody().getRewardPoints());
                });
    }

    @Test
    void whenValidCustomerIdWithSingle130DollarTransactionIn5Months_ThenReturn110RewardPoints() {
        webTestClient.get()
                .uri("/customers/33333/reward-points?numOfMonths=5")
                .header("X-Application-Id", "blah")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(CustomerRewardPoints.class)
                .consumeWith(response -> {
                    assertNotNull(response.getResponseBody());
                    assertNotNull(response.getResponseBody().getCustomerId());
                    assertNotNull(response.getResponseBody().getRewardPoints());
                    assertEquals(33333, response.getResponseBody().getCustomerId());
                    assertEquals(110, response.getResponseBody().getRewardPoints());
                });
    }

    @Test
    void whenValidCustomerIdWithNoEligibleTransaction_ThenReturnNoRewardPoints() {
        webTestClient.get()
                .uri("/customers/44444/reward-points")
                .header("X-Application-Id", "blah")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(CustomerRewardPoints.class)
                .consumeWith(response -> {
                    assertNotNull(response.getResponseBody());
                    assertNotNull(response.getResponseBody().getCustomerId());
                    assertNotNull(response.getResponseBody().getRewardPoints());
                    assertEquals(44444, response.getResponseBody().getCustomerId());
                    assertEquals(0, response.getResponseBody().getRewardPoints());
                });
    }

}
