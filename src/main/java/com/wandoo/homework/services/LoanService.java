package com.wandoo.homework.services;

import com.wandoo.homework.base.AppDefaults;
import com.wandoo.homework.beans.LoanBean;
import com.wandoo.homework.exceptions.DuplicateObjectException;
import com.wandoo.homework.model.Loan;
import com.wandoo.homework.repositories.LoanRepository;
import com.wandoo.homework.requestbeans.LoanRequestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;

    public List<LoanBean> getAll() {
        return loanRepository.getAll()
                .stream()
                .map(Loan::toBean)
                .collect(Collectors.toList());
    }

    public List<LoanBean> getInvestable() {
        return loanRepository.getAll()
                .stream()
                .filter(Loan::isInvestable)
                .map(Loan::toBean)
                .collect(Collectors.toList());
    }

    public void createLoan(LoanRequestBean loanRequestBean) throws DuplicateObjectException {
        if (loanRepository.get(loanRequestBean.getId()).isPresent()) {
            throw new DuplicateObjectException(AppDefaults.LOAN_ID_ALREADY_EXIST);
        }
        loanRepository.createLoan(new Loan(loanRequestBean));
    }

    public Optional<LoanBean> get(Long id) {
        return loanRepository.get(id).map(Loan::toBean);
    }

    public Optional<LoanBean> getLast() {
        return loanRepository.getLast().map(Loan::toBean);
    }
}
