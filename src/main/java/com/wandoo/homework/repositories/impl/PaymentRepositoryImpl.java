package com.wandoo.homework.repositories.impl;

import com.wandoo.homework.model.Payment;
import com.wandoo.homework.repositories.PaymentRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public void createPayment(Payment payment) {
        entityManager.unwrap(Session.class).save(payment);
    }

    @Override
    public Optional<Payment> get(Long id) {
        return Optional.ofNullable(entityManager.find(Payment.class, id));
    }
}
