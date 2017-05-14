package com.wandoo.homework.repositories;

import com.wandoo.homework.model.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanRepository {

    void createLoan(Loan loan);

    List<Loan> getAll();

    Optional<Loan> get(Long id);
}
