package com.wandoo.homework.repositories;

import com.wandoo.homework.model.Payment;

import java.util.Optional;

public interface PaymentRepository {

    void createPayment(Payment loan);

    Optional<Payment> get(Long id);
}
