package com.digital.retailer.services.impl.controller;

import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Data
class Person {
    long customerId;
    String name;
    LocalDate dateOfBirth;
}

@RestController
public class LegacyController {

    @GetMapping( value = "/customers/{customer-id}/details")
    public List<Person> get( @PathVariable (value = "customer-id") Long customerid) {
        List<Person> persons = new ArrayList<>();

        Person person1 = new Person();
        person1.setCustomerId(10l);
        person1.setName("Ajay1");
        person1.setDateOfBirth(LocalDate.now());

        Person person2 = new Person();
        person1.setCustomerId(100l);
        person1.setName("Ajay2");
        person1.setDateOfBirth(LocalDate.now());

        persons.add(person1);
        persons.add(person2);

        persons.forEach(System.out::println);
        persons.sort(Comparator.comparing(Person::getDateOfBirth));
        persons.forEach(System.out::println);

        return persons;
    }
}
