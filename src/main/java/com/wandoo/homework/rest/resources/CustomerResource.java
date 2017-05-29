package com.wandoo.homework.rest.resources;

import com.wandoo.homework.model.requestbeans.InvestmentRequestBean;
import com.wandoo.homework.model.requestbeans.RegisterCustomerRequestBean;
import com.wandoo.homework.model.responsebeans.BaseResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customer")
public class CustomerResource {

    @Autowired
    private CustomerResourceService customerResourceService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public BaseResponseBean createLoan(@RequestBody RegisterCustomerRequestBean registerCustomerRequestBean) {
        return customerResourceService.registerCustomer(registerCustomerRequestBean);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public BaseResponseBean get(@PathVariable("id") Long id) {
        return customerResourceService.get(id);
    }

    @RequestMapping(value = "/get-last", method = RequestMethod.GET)
    public BaseResponseBean get() {
        return customerResourceService.getLastRegistered();
    }

    @RequestMapping(value = "/invest", method = RequestMethod.POST)
    public BaseResponseBean invest(@RequestBody InvestmentRequestBean investmentRequestBean) {
        return customerResourceService.invest(investmentRequestBean);
    }

}
