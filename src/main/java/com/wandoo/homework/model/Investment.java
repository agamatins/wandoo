package com.wandoo.homework.model;

import com.wandoo.homework.model.beans.InvestmentBean;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Investment {
    @Id
    @Column(name="id", unique=true)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="amount")
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name="loan_id")
    private Loan loan;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public InvestmentBean toBean() {
        InvestmentBean investmentBean = new InvestmentBean();
        investmentBean.setId(this.getId());
        investmentBean.setCustomerId(this.getCustomer().getId());
        investmentBean.setLoanId(this.getLoan().getId());
        investmentBean.setAmount(this.getAmount());
        return investmentBean;
    }
}
