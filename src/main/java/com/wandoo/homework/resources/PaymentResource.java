package com.wandoo.homework.resources;

import com.wandoo.homework.requestbeans.ImportPaymentRequestBean;
import com.wandoo.homework.responsebeans.BaseResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payment")
public class PaymentResource {

    @Autowired
    private PaymentResourceService paymentResourceService;

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public BaseResponseBean createLoan(@RequestBody ImportPaymentRequestBean importPaymentRequestBean) {
        return paymentResourceService.importPayment(importPaymentRequestBean);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public BaseResponseBean get(@PathVariable("id") Long id) {
        return paymentResourceService.get(id);
    }
}
