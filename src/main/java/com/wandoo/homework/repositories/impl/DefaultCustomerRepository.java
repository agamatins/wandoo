package com.wandoo.homework.repositories.impl;

import com.wandoo.homework.model.Customer;
import com.wandoo.homework.repositories.CustomerRepository;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class DefaultCustomerRepository implements CustomerRepository {
    @Autowired
    private EntityManager entityManager;

    @Override
    public void createCustomer(Customer customer) {
        entityManager.persist(customer);
    }

    @Override
    public Optional<Customer> get(Long id) {
        return Optional.ofNullable(entityManager.find(Customer.class, id));
    }

    @Override
    public Optional<Customer> getByEmail(String email) {
        Customer customer = (Customer)entityManager.unwrap(Session.class)
                .createCriteria(Customer.class)
                .add(Restrictions.eq("email", email))
                .uniqueResult();
        return Optional.ofNullable(customer);
    }

    @Override
    public Optional<Customer> getLastRegistered() {
        Customer customer = (Customer)entityManager.unwrap(Session.class)
                .createCriteria(Customer.class)
                .addOrder(Order.desc("id"))
                .setMaxResults(1)
                .uniqueResult();
        return Optional.ofNullable(customer);
    }
}
