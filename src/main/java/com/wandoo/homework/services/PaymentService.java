package com.wandoo.homework.services;

import com.wandoo.homework.beans.PaymentBean;
import com.wandoo.homework.exceptions.DuplicateObjectException;
import com.wandoo.homework.exceptions.LoanNotFoundException;
import com.wandoo.homework.model.Loan;
import com.wandoo.homework.model.Payment;
import com.wandoo.homework.repositories.LoanRepository;
import com.wandoo.homework.repositories.PaymentRepository;
import com.wandoo.homework.requestbeans.PaymentRequestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private LoanRepository loanRepository;

    public void createPayment(PaymentRequestBean paymentRequestBean) throws LoanNotFoundException, DuplicateObjectException{
        if (paymentRepository.get(paymentRequestBean.getId()).isPresent()) {
            throw new DuplicateObjectException(String.format("Payment with id=%s already exist", paymentRequestBean.getId()));
        }

        Optional<Loan> loan = loanRepository.get(paymentRequestBean.getLoanId());
        if (!loan.isPresent()) {
            throw new LoanNotFoundException(String.format("Related loan with id=%s not found for payment with id=%s", paymentRequestBean.getId(), paymentRequestBean.getId()));
        }
        Payment payment = new Payment();
        payment.setId(paymentRequestBean.getId());
        payment.setLoan(loan.get());
        payment.setMainAmount(paymentRequestBean.getMainAmount());
        payment.setInterestAmount(paymentRequestBean.getInterestAmount());
        paymentRepository.createPayment(payment);
    }

    public Optional<PaymentBean> get(Long id) {
        return paymentRepository.get(id).map(Payment::toBean);
    }
}
