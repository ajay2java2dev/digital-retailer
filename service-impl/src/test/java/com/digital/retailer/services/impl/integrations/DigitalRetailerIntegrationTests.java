package com.digital.retailer.services.impl.integrations;

import com.digital.retailer.services.impl.ServiceImplApplication;
import com.digital.retailer.services.openapi.model.CustomerRewardPoints;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {ServiceImplApplication.class})
@AutoConfigureWebTestClient(timeout = "30000")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DigitalRetailerIntegrationTests {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    ObjectMapper objectMapper;

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

}
