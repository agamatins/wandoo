package com.wandoo.homework.repositories.impl;

import com.wandoo.homework.model.Investment;
import com.wandoo.homework.repositories.InvestmentRepository;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class InvestmentRepositoryImpl implements InvestmentRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public void createInvestment(Investment investment) {
        entityManager.unwrap(Session.class).save(investment);
    }
}
