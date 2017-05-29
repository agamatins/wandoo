package com.wandoo.homework.rest.resources;

import com.wandoo.homework.base.AppDefaults;
import com.wandoo.homework.base.MessageType;
import com.wandoo.homework.base.ValidationMessage;
import com.wandoo.homework.model.beans.CustomerBean;
import com.wandoo.homework.model.requestbeans.InvestmentRequestBean;
import com.wandoo.homework.model.requestbeans.RegisterCustomerRequestBean;
import com.wandoo.homework.model.responsebeans.BaseResponseBean;
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
    public BaseResponseBean registerCustomer(RegisterCustomerRequestBean registerCustomerRequestBean) {
        List<ValidationMessage> validationErrors = registerCustomerRequestBean.validate();
        if (!validationErrors.isEmpty()) {
            return new BaseResponseBean(validationErrors);
        }

        try {
            customerService.registerCustomer(registerCustomerRequestBean);
        } catch (Exception ex) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, ex.getMessage(), ""));
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
        } catch (Exception e) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, e.getMessage(), ""));
            return new BaseResponseBean(validationErrors);
        }
        return new BaseResponseBean();
    }
}
