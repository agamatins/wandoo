package com.wandoo.homework.resources;

import com.wandoo.homework.responsebeans.BaseResponseBean;
import com.wandoo.homework.requestbeans.PaymentRequestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payment")
public class PaymentResource {

    @Autowired
    private PaymentResourceService paymentResourceService;

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public BaseResponseBean createLoan(@RequestBody PaymentRequestBean paymentRequestBean) {
        return paymentResourceService.importPayment(paymentRequestBean);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public BaseResponseBean list(@PathVariable("id") Long id) {
        return paymentResourceService.get(id);
    }
}
