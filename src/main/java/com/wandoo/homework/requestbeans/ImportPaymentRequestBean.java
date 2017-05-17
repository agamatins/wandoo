package com.wandoo.homework.requestbeans;

import com.wandoo.homework.base.AppDefaults;
import com.wandoo.homework.base.MessageType;
import com.wandoo.homework.base.ValidationMessage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.wandoo.homework.base.BigDecimalUtils.amount;

public class ImportPaymentRequestBean {

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
            validationErrors.add(new ValidationMessage(MessageType.ERROR, AppDefaults.CANNOT_BE_EMPTY, "id"));
        }

        if (this.getLoanId() == null) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, AppDefaults.CANNOT_BE_EMPTY, "loanId"));
        }

        if (this.getMainAmount() == null) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, AppDefaults.CANNOT_BE_EMPTY, "mainAmount"));
        } else if (!amount(this.getMainAmount()).betweenIncluding(BigDecimal.ONE, new BigDecimal(1000))) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, AppDefaults.MAIN_AMOUNT_INCORRECT_FORMAT, "mainAmount"));
        }

        if (this.getInterestAmount() == null) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, AppDefaults.CANNOT_BE_EMPTY, "interestAmount"));
        } else if (!amount(this.getInterestAmount()).betweenIncluding(BigDecimal.ZERO, new BigDecimal(1000))) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, AppDefaults.INTEREST_AMOUNT_INCORRECT_FORMAT, "interestAmount"));
        }

        return validationErrors;
    }
}
