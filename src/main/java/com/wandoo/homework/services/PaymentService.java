package com.wandoo.homework.services;

import com.wandoo.homework.base.AppDefaults;
import com.wandoo.homework.model.Loan;
import com.wandoo.homework.model.Payment;
import com.wandoo.homework.model.beans.PaymentBean;
import com.wandoo.homework.model.requestbeans.ImportPaymentRequestBean;
import com.wandoo.homework.repositories.LoanRepository;
import com.wandoo.homework.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private LoanRepository loanRepository;

    public void createPayment(ImportPaymentRequestBean importPaymentRequestBean) throws Exception{
        if (paymentRepository.get(importPaymentRequestBean.getId()).isPresent()) {
            throw new Exception(AppDefaults.PAYMENT_ID_ALREADY_EXIST);
        }

        Optional<Loan> loan = loanRepository.get(importPaymentRequestBean.getLoanId());
        if (!loan.isPresent()) {
            throw new Exception(AppDefaults.CANNOT_FIND_LOAN_ID);
        }
        Payment payment = new Payment();
        payment.setId(importPaymentRequestBean.getId());
        payment.setLoan(loan.get());
        payment.setMainAmount(importPaymentRequestBean.getMainAmount().setScale(2, RoundingMode.HALF_UP));
        payment.setInterestAmount(importPaymentRequestBean.getInterestAmount().setScale(2, RoundingMode.HALF_UP));
        paymentRepository.createPayment(payment);
    }

    public Optional<PaymentBean> get(Long id) {
        return paymentRepository.get(id).map(Payment::toBean);
    }
}
