package com.wandoo.homework.repositories.impl;

import com.wandoo.homework.model.Payment;
import com.wandoo.homework.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class DefaultPaymentRepository implements PaymentRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public void createPayment(Payment payment) {
        entityManager.persist(payment);
    }

    @Override
    public Optional<Payment> get(Long id) {
        return Optional.ofNullable(entityManager.find(Payment.class, id));
    }
}
