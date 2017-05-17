package com.wandoo.homework.resources;

import com.wandoo.homework.requestbeans.LoanRequestBean;
import com.wandoo.homework.responsebeans.BaseResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("loan")
public class LoanResource {
    @Autowired
    private LoanResourceService loanResourceService;

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public BaseResponseBean createLoan(@RequestBody LoanRequestBean loanRequestBean) {
       return loanResourceService.importLoan(loanRequestBean);
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
