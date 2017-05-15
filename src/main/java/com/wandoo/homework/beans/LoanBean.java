package com.wandoo.homework.beans;

import java.math.BigDecimal;

public class LoanBean {
    private Long id;
    private BigDecimal mainAmount;
    private BigDecimal interestRate;
    private boolean isInvestable;
    private BigDecimal allowedInvestmentAmount;

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

    public boolean isInvestable() {
        return isInvestable;
    }

    public void setInvestable(boolean investable) {
        isInvestable = investable;
    }

    public BigDecimal getAllowedInvestmentAmount() {
        return allowedInvestmentAmount;
    }

    public void setAllowedInvestmentAmount(BigDecimal allowedInvestmentAmount) {
        this.allowedInvestmentAmount = allowedInvestmentAmount;
    }
}
