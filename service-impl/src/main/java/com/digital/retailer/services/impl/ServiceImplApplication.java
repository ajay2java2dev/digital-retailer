package com.digital.retailer.services.impl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.digital.retailer.services")
@EnableJpaRepositories("com.digital.retailer.services.data")
@EntityScan("com.digital.retailer.services.data")
public class ServiceImplApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceImplApplication.class, args);
    }

}
