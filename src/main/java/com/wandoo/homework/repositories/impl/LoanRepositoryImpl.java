package com.wandoo.homework.repositories.impl;

import com.wandoo.homework.model.Loan;
import com.wandoo.homework.repositories.LoanRepository;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class LoanRepositoryImpl implements LoanRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public void createLoan(Loan loan) {
        entityManager.unwrap(Session.class).save(loan);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Loan> getAll() {
        return entityManager.unwrap(Session.class)
                .createCriteria(Loan.class)
                .list();
    }

    @Override
    public Optional<Loan> get(Long id) {
        return Optional.ofNullable(entityManager.find(Loan.class, id));
    }
}
