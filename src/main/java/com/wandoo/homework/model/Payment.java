package com.wandoo.homework.model;

import com.wandoo.homework.beans.PaymentBean;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Payment {

    @Id
    @Column(name="id", unique=true)
    private Long id;

    @ManyToOne
    @JoinColumn(name="loan_id")
    private Loan loan;

    @Column(name="main_amount")
    private BigDecimal mainAmount;

    @Column(name="interest_amount")
    private BigDecimal interestAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public BigDecimal getMainAmount() {
        return mainAmount;
    }

    public void setMainAmount(BigDecimal mainAmount) {
        this.mainAmount = mainAmount;
    }

    public BigDecimal getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(BigDecimal interestAmount) {
        this.interestAmount = interestAmount;
    }

    public PaymentBean toBean() {
        PaymentBean paymentBean = new PaymentBean();
        paymentBean.setId(this.getId());
        paymentBean.setMainAmount(this.getMainAmount());
        paymentBean.setInterestAmount(this.getInterestAmount());
        paymentBean.setLoanId(this.getLoan().getId());
        return paymentBean;
    }
}
