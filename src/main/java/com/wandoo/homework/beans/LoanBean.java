package com.wandoo.homework.beans;

import com.wandoo.homework.base.MessageType;
import com.wandoo.homework.base.ValidationMessage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LoanBean {
    private Long id;
    private BigDecimal mainAmount;
    private BigDecimal interestRate;

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

    public List<ValidationMessage> validate() {
        List<ValidationMessage> validationErrors = new ArrayList<>();

        if (this.getId() == null) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, "loan id cannot be empty"));
        }

        if (this.getMainAmount() == null) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, "main amount cannot be empty"));
        } else if (this.getMainAmount().compareTo(BigDecimal.ONE) < 0 || this.getMainAmount().compareTo(new BigDecimal(1000)) > 0) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, "main amount cannot be less than 1 or more than 1000"));
        }

        if (this.getInterestRate() == null) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, "interest rate cannot be empty"));
        }

        return validationErrors;
    }
}
