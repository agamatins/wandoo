package com.wandoo.homework.rest.resources;

import com.wandoo.homework.model.requestbeans.ImportLoanRequestBean;
import com.wandoo.homework.model.responsebeans.BaseResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("loan")
public class LoanResource {
    @Autowired
    private LoanResourceService loanResourceService;

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public BaseResponseBean createLoan(@RequestBody ImportLoanRequestBean importLoanRequestBean) {
       return loanResourceService.importLoan(importLoanRequestBean);
    }

    @RequestMapping(value = "/list-all", method = RequestMethod.GET)
    public BaseResponseBean list() {
        return loanResourceService.getAll();
    }

    @RequestMapping(value = "/list-investable", method = RequestMethod.GET)
    public BaseResponseBean listInvestable() {
        return loanResourceService.getInvestable();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public BaseResponseBean list(@PathVariable("id") Long id) {
        return loanResourceService.get(id);
    }
}
