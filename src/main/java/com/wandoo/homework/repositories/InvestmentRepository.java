package com.wandoo.homework.repositories;

import com.wandoo.homework.model.Investment;

import java.util.Optional;

public interface InvestmentRepository {

    void createInvestment(Investment investment);

    Optional<Investment> getLastCreated();
}
