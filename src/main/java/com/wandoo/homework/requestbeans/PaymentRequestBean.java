package com.wandoo.homework.requestbeans;

import com.wandoo.homework.base.MessageType;
import com.wandoo.homework.base.ValidationMessage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PaymentRequestBean {

    private Long id;
    private Long loanId;
    private BigDecimal mainAmount;
    private BigDecimal interestAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
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

    public List<ValidationMessage> validate() {
        List<ValidationMessage> validationErrors = new ArrayList<>();

        if (this.getId() == null) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, "payment id cannot be empty", "id"));
        }

        if (this.getLoanId() == null) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, "loan id for payment cannot be empty", "id"));
        }

        if (this.getMainAmount() == null) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, "main amount cannot be empty", "mainAmount"));
        } else if (this.getMainAmount().compareTo(BigDecimal.ZERO) < 0 || this.getMainAmount().compareTo(new BigDecimal(1000)) > 0) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, "main amount cannot be less than 1 or more than 1000", "mainAmount"));
        }

        if (this.getInterestAmount() == null) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, "interest amount cannot be empty", "interestAmount"));
        } else if (this.getInterestAmount().compareTo(BigDecimal.ZERO) < 0 || this.getInterestAmount().compareTo(new BigDecimal(1000)) > 0) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, "interest amount cannot be less than 0 or more than 1000", "interestAmount"));
        }

        return validationErrors;
    }
}
