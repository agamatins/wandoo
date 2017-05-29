package com.wandoo.homework.model.requestbeans;

import com.wandoo.homework.base.AppDefaults;
import com.wandoo.homework.base.MessageType;
import com.wandoo.homework.base.ValidationMessage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.wandoo.homework.base.BigDecimalUtils.amount;

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
            validationErrors.add(new ValidationMessage(MessageType.ERROR, AppDefaults.CANNOT_BE_EMPTY, "loanId"));
        }

        if (this.getCustomerId() == null) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, AppDefaults.CANNOT_BE_EMPTY, "customerId"));
        }

        if (this.getAmount() == null) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, AppDefaults.CANNOT_BE_EMPTY, "amount"));
        } else if (!amount(this.getAmount()).betweenIncluding(BigDecimal.ONE, new BigDecimal(1000))) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, AppDefaults.MAIN_AMOUNT_INCORRECT_FORMAT, "amount"));
        }

        return validationErrors;
    }
}
