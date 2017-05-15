package com.wandoo.homework.requestbeans;

import com.wandoo.homework.base.MessageType;
import com.wandoo.homework.base.ValidationMessage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class InvestmentRequestBean {

    private BigDecimal amount;
    private Long loanId;
    private Long customerId;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<ValidationMessage> validate() {
        List<ValidationMessage> validationErrors = new ArrayList<>();

        if (this.getLoanId() == null) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, "loan id cannot be empty", "loanId"));
        }

        if (this.getCustomerId() == null) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, "customer id cannot be empty", "customerId"));
        }

        if (this.getAmount() == null) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, "amount cannot be empty", "amount"));
        } else if (this.getAmount().compareTo(BigDecimal.ONE) < 0 || this.getAmount().compareTo(new BigDecimal(1000)) > 0) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, "amount cannot be less than 1 or more than 1000", "amount"));
        }

        return validationErrors;
    }
}
