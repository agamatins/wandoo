package com.wandoo.homework.repositories;

import com.wandoo.homework.model.Loan;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class LoanRepositoryImpl implements LoanRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public void createLoan(Loan loan) {
        sessionFactory.getCurrentSession().save(loan);
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<Loan> getAll() {
        return sessionFactory
                .getCurrentSession()
                .createCriteria(Loan.class)
                .list();

    }

    @Override
    public Optional<Loan> get(Long id) {
        return Optional.of(entityManager.find(Loan.class, id));
    }
}
