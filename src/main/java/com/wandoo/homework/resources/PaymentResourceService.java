package com.wandoo.homework.resources;

import com.wandoo.homework.base.AppDefaults;
import com.wandoo.homework.base.MessageType;
import com.wandoo.homework.base.ValidationMessage;
import com.wandoo.homework.beans.PaymentBean;
import com.wandoo.homework.exceptions.DuplicateObjectException;
import com.wandoo.homework.exceptions.LoanNotFoundException;
import com.wandoo.homework.requestbeans.ImportPaymentRequestBean;
import com.wandoo.homework.responsebeans.BaseResponseBean;
import com.wandoo.homework.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class PaymentResourceService {

    @Autowired
    private PaymentService paymentService;

    @Transactional
    public BaseResponseBean importPayment(ImportPaymentRequestBean importPaymentRequestBean) {
        List<ValidationMessage> validationErrors = importPaymentRequestBean.validate();
        if (!validationErrors.isEmpty()) {
            return new BaseResponseBean(validationErrors);
        }

        try {
            paymentService.createPayment(importPaymentRequestBean);
        } catch (DuplicateObjectException ex) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, ex.getMessage(), "id"));
            return new BaseResponseBean(validationErrors);
        } catch (LoanNotFoundException lnfe) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, lnfe.getMessage(), "loanId"));
            return new BaseResponseBean(validationErrors);
        }
        return new BaseResponseBean();
    }

    @Transactional
    public BaseResponseBean get(Long id) {
        Optional<PaymentBean> paymentBean = paymentService.get(id);
        if (!paymentBean.isPresent()) {
            return new BaseResponseBean(new ValidationMessage(MessageType.INFO, AppDefaults.NOT_FOUND, "id"));
        }
        BaseResponseBean<PaymentBean> responseBean = new BaseResponseBean<>();
        responseBean.setBody(paymentBean.get());
        return responseBean;
    }
}
