package com.wandoo.homework.model;

import com.wandoo.homework.beans.LoanBean;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Loan {

    @Id
    @Column(name="id", unique=true)
    private Long id;

    @Column(name="main_amount")
    private BigDecimal mainAmount;

    @Column(name="interest_rate")
    private BigDecimal interestRate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Payment> payments;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Investment> investments;

    public Loan() {
    }

    public Loan(LoanBean loanBean) {
        this.id = loanBean.getId();
        this.mainAmount = loanBean.getMainAmount();
        this.interestRate = loanBean.getInterestRate();
    }

    public Loan(Long id,
                BigDecimal mainAmount,
                BigDecimal interestRate) {
        this.id = id;
        this.mainAmount = mainAmount;
        this.interestRate = interestRate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMainAmount() {
        return mainAmount;
    }

    public void setMainAmount(BigDecimal mainAmount) {
        this.mainAmount = mainAmount;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<Investment> getInvestments() {
        return investments;
    }

    public void setInvestments(List<Investment> investments) {
        this.investments = investments;
    }

    private BigDecimal getSumOfAllPayments() {
        return payments.stream()
                .map(Payment::getMainAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getSumOfAllInvestments() {
        return investments.stream()
                .map(Investment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getAllowedInvestmentAmount() {
        return mainAmount
                .subtract(getSumOfAllPayments())
                .subtract(getSumOfAllInvestments());
    }

    public boolean isInvestable() {
        return getAllowedInvestmentAmount().compareTo(BigDecimal.ZERO) > 0;
    }
}
