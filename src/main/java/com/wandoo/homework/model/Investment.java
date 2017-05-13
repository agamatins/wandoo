package com.wandoo.homework.model;

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
    private Loan loanId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Loan getLoanId() {
        return loanId;
    }

    public void setLoanId(Loan loanId) {
        this.loanId = loanId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
