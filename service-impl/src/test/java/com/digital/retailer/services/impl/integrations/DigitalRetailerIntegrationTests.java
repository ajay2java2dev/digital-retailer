package com.digital.retailer.services.impl.integrations;

import com.digital.retailer.services.impl.ServiceImplApplication;
import com.digital.retailer.services.openapi.model.CustomerRewardPoints;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {ServiceImplApplication.class})
@AutoConfigureWebTestClient(timeout = "30000")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DigitalRetailerIntegrationTests {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void whenValidCustomerId_ThenReturnRewardPoints() {
        webTestClient.get()
                .uri("/customer/99999/reward-points")
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
                .uri("/customer/999999/reward-points")
                .header("X-Application-Id", "blah")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void whenValidCustomerIdWithNoValidTransactionsIn3Months_ThenReturnNoRewardPoints() {
        webTestClient.get()
                .uri("/customer/98765/reward-points")
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
                .uri("/customer/98765/reward-points?numOfMonths=5")
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
                .uri("/customer/33333/reward-points?numOfMonths=5")
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
                .uri("/customer/44444/reward-points")
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