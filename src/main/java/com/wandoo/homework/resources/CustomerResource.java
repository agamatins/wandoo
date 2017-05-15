package com.wandoo.homework.resources;

import com.wandoo.homework.requestbeans.CustomerRequestBean;
import com.wandoo.homework.requestbeans.InvestmentRequestBean;
import com.wandoo.homework.responsebeans.BaseResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customer")
public class CustomerResource {

    @Autowired
    private CustomerResourceService customerResourceService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public BaseResponseBean createLoan(@RequestBody CustomerRequestBean customerRequestBean) {
        return customerResourceService.registerCustomer(customerRequestBean);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public BaseResponseBean list(@PathVariable("id") Long id) {
        return customerResourceService.get(id);
    }

    @RequestMapping(value = "/invest", method = RequestMethod.POST)
    public BaseResponseBean invest(@RequestBody InvestmentRequestBean investmentRequestBean) {
        return customerResourceService.invest(investmentRequestBean);
    }

}