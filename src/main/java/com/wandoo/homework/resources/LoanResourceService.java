package com.wandoo.homework.resources;

import com.wandoo.homework.base.MessageType;
import com.wandoo.homework.base.ValidationMessage;
import com.wandoo.homework.responsebeans.BaseResponseBean;
import com.wandoo.homework.beans.LoanBean;
import com.wandoo.homework.requestbeans.LoanRequestBean;
import com.wandoo.homework.exceptions.DuplicateObjectException;
import com.wandoo.homework.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class LoanResourceService {

    @Autowired
    private LoanService loanService;

    @Transactional
    public BaseResponseBean importLoan(LoanRequestBean loanRequestBean) {
        List<ValidationMessage> validationErrors = loanRequestBean.validate();
        if (!validationErrors.isEmpty()) {
            return new BaseResponseBean(validationErrors);
        }

        try {
            loanService.createLoan(loanRequestBean);
        } catch (DuplicateObjectException ex) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, ex.getMessage(),"id"));
            return new BaseResponseBean(validationErrors);
        }
        return new BaseResponseBean();
    }

    @Transactional(readOnly = true)
    public BaseResponseBean getAll(){
        BaseResponseBean<List<LoanBean>> responseBean = new BaseResponseBean<>();
        responseBean.setBody(loanService.getAll());
        return responseBean;
    }

    @Transactional(readOnly = true)
    public BaseResponseBean getInvestable(){
        BaseResponseBean<List<LoanBean>> responseBean = new BaseResponseBean<>();
        responseBean.setBody(loanService.getInvestable());
        return responseBean;
    }

    @Transactional(readOnly = true)
    public BaseResponseBean get(Long id) {
        Optional<LoanBean> loanBean = loanService.get(id);
        if (!loanBean.isPresent()) {
            return new BaseResponseBean(new ValidationMessage(MessageType.INFO, String.format("Loan with id=%s not found", id), "id"));
        }
        BaseResponseBean<LoanBean> responseBean = new BaseResponseBean<>();
        responseBean.setBody(loanBean.get());
        return responseBean;
    }
}