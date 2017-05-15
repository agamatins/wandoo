package com.wandoo.homework.repositories;

import com.wandoo.homework.model.Customer;

import java.util.Optional;

public interface CustomerRepository {

    void createCustomer(Customer customer);

    Optional<Customer> get(Long id);

    Optional<Customer> getByEmail(String email);
}
