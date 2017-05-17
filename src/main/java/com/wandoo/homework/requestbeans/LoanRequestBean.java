package com.wandoo.homework.requestbeans;

import com.wandoo.homework.base.AppDefaults;
import com.wandoo.homework.base.MessageType;
import com.wandoo.homework.base.ValidationMessage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.wandoo.homework.base.BigDecimalUtils.amount;

public class LoanRequestBean {
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
            validationErrors.add(new ValidationMessage(MessageType.ERROR, AppDefaults.CANNOT_BE_EMPTY, "id"));
        }

        if (this.getMainAmount() == null) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, AppDefaults.CANNOT_BE_EMPTY, "mainAmount"));
        } else if (!amount(this.getMainAmount()).betweenIncluding(BigDecimal.ONE, new BigDecimal(1000))) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, AppDefaults.MAIN_AMOUNT_INCORRECT_FORMAT, "mainAmount"));
        }

        if (this.getInterestRate() == null) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, AppDefaults.CANNOT_BE_EMPTY, "interestRate"));
        } else if (!amount(this.getInterestRate()).betweenIncluding(BigDecimal.ZERO, new BigDecimal(100))) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, AppDefaults.INTEREST_RATE_INCORRECT_FORMAT, "interestRate"));
        }

        return validationErrors;
    }
}
