package com.wandoo.homework.resources;

import com.wandoo.homework.base.AppDefaults;
import com.wandoo.homework.base.MessageType;
import com.wandoo.homework.base.ValidationMessage;
import com.wandoo.homework.beans.CustomerBean;
import com.wandoo.homework.exceptions.CustomerNotFoundException;
import com.wandoo.homework.exceptions.DuplicateObjectException;
import com.wandoo.homework.exceptions.FailedInvestmentException;
import com.wandoo.homework.exceptions.LoanNotFoundException;
import com.wandoo.homework.requestbeans.CustomerRequestBean;
import com.wandoo.homework.requestbeans.InvestmentRequestBean;
import com.wandoo.homework.responsebeans.BaseResponseBean;
import com.wandoo.homework.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerResourceService {

    @Autowired
    private CustomerService customerService;

    @Transactional
    public BaseResponseBean registerCustomer(CustomerRequestBean customerRequestBean) {
        List<ValidationMessage> validationErrors = customerRequestBean.validate();
        if (!validationErrors.isEmpty()) {
            return new BaseResponseBean(validationErrors);
        }

        try {
            customerService.registerCustomer(customerRequestBean);
        } catch (DuplicateObjectException ex) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, ex.getMessage(), "email"));
            return new BaseResponseBean(validationErrors);
        }
        return new BaseResponseBean();
    }

    @Transactional(readOnly = true)
    public BaseResponseBean get(Long id) {
        Optional<CustomerBean> customerBean = customerService.get(id);
        if (!customerBean.isPresent()) {
            return new BaseResponseBean(new ValidationMessage(MessageType.INFO, AppDefaults.NOT_FOUND, "id"));
        }
        BaseResponseBean<CustomerBean> responseBean = new BaseResponseBean<>();
        responseBean.setBody(customerBean.get());
        return responseBean;
    }

    @Transactional(readOnly = true)
    public BaseResponseBean getByEmail(String email) {
        Optional<CustomerBean> customerBean = customerService.getByEmail(email);
        if (!customerBean.isPresent()) {
            return new BaseResponseBean(new ValidationMessage(MessageType.INFO, AppDefaults.NOT_FOUND, "email"));
        }
        BaseResponseBean<CustomerBean> responseBean = new BaseResponseBean<>();
        responseBean.setBody(customerBean.get());
        return responseBean;
    }

    @Transactional(readOnly = true)
    public BaseResponseBean getLastRegistered() {
        Optional<CustomerBean> customerBean = customerService.getLastRegistered();
        if (!customerBean.isPresent()) {
            return new BaseResponseBean(new ValidationMessage(MessageType.INFO, AppDefaults.NOT_FOUND, ""));
        }
        BaseResponseBean<CustomerBean> responseBean = new BaseResponseBean<>();
        responseBean.setBody(customerBean.get());
        return responseBean;
    }

    @Transactional
    public BaseResponseBean invest(InvestmentRequestBean investmentRequestBean) {
        List<ValidationMessage> validationErrors = investmentRequestBean.validate();
        if (!validationErrors.isEmpty()) {
            return new BaseResponseBean(validationErrors);
        }

        try {
            customerService.invest(investmentRequestBean);
        } catch (FailedInvestmentException fie) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, fie.getMessage(), "amount"));
            return new BaseResponseBean(validationErrors);
        } catch (LoanNotFoundException lnfe) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, lnfe.getMessage(), "loanId"));
            return new BaseResponseBean(validationErrors);
        } catch (CustomerNotFoundException cnfe) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, cnfe.getMessage(), "customerId"));
            return new BaseResponseBean(validationErrors);
        }
        return new BaseResponseBean();
    }
}
