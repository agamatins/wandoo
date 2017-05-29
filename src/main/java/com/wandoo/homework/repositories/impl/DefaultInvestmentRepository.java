package com.wandoo.homework.repositories.impl;

import com.wandoo.homework.model.Investment;
import com.wandoo.homework.repositories.InvestmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class DefaultInvestmentRepository implements InvestmentRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public void createInvestment(Investment investment) {
        entityManager.persist(investment);
    }
}
