package com.wandoo.homework.services;

import com.wandoo.homework.model.Loan;
import com.wandoo.homework.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;

    public List<Loan> getAllInvestable() {
        return loanRepository.getAll()
                .stream()
                .filter(Loan::isInvestable)
                .collect(Collectors.toList());
    }

    public void createLoan(Loan loan) {
        if (loanRepository.get(loan.getId()).isPresent()) {
            throw new DuplicateKeyException(String.format("Loan with id=%s already exist",loan.getId()));
        }
        loanRepository.createLoan(loan);
    }
}
