package com.wandoo.homework.model;

import com.wandoo.homework.beans.LoanBean;
import com.wandoo.homework.requestbeans.LoanRequestBean;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static com.wandoo.homework.base.BigDecimalUtils.amount;

@Entity
public class Loan {

    @Id
    @Column(name="id", unique=true)
    private Long id;

    @Column(name="main_amount")
    private BigDecimal mainAmount;

    @Column(name="interest_rate")
    private BigDecimal interestRate;

    @OneToMany(mappedBy="loan", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Payment> payments;

    @OneToMany(mappedBy="loan", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Investment> investments;

    public Loan() {
    }

    public Loan(LoanRequestBean loanRequestBean) {
        this.id = loanRequestBean.getId();
        this.mainAmount = loanRequestBean.getMainAmount().setScale(2, RoundingMode.HALF_UP);
        this.interestRate = loanRequestBean.getInterestRate().setScale(2, RoundingMode.HALF_UP);
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
        BigDecimal allowedInvestmentAmount =  mainAmount
                .subtract(getSumOfAllPayments())
                .subtract(getSumOfAllInvestments());
        return amount(allowedInvestmentAmount).gtZero() ? allowedInvestmentAmount : BigDecimal.ZERO;
    }

    public boolean isInvestable() {
        return amount(getAllowedInvestmentAmount()).gt(BigDecimal.ZERO);
    }

    public LoanBean toBean() {
        LoanBean loanBean = new LoanBean();
        loanBean.setId(this.getId());
        loanBean.setMainAmount(this.getMainAmount());
        loanBean.setInterestRate(this.getInterestRate());
        loanBean.setInvestable(this.isInvestable());
        loanBean.setAllowedInvestmentAmount(this.getAllowedInvestmentAmount());
        return loanBean;
    }
}
