package com.wandoo.homework.resources;

import com.wandoo.homework.beans.LoanBean;
import com.wandoo.homework.model.Loan;
import com.wandoo.homework.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class LoanResourceService {

    @Autowired
    private LoanService loanService;

    @Transactional
    public void exportLoan(LoanBean loanBean) throws DuplicateKeyException {
        loanService.createLoan(new Loan(loanBean));
    }

    @Transactional(readOnly = true)
    public List<Loan> getAll() {
        return loanService.getAll();
    }
}
