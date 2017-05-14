package com.wandoo.homework.resources;

import com.wandoo.homework.base.MessageType;
import com.wandoo.homework.base.RestResponse;
import com.wandoo.homework.base.ValidationMessage;
import com.wandoo.homework.beans.LoanBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("loan")
public class LoanResource {
    @Autowired
    private LoanResourceService loanResourceService;

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public ResponseEntity<?> createLoan(@RequestBody LoanBean loanBean) {
        List<ValidationMessage> validationErrors = loanBean.validate();
        if (!validationErrors.isEmpty()) {
            return new ResponseEntity<>(new RestResponse(validationErrors), HttpStatus.BAD_REQUEST);
        }

        try {
            loanResourceService.exportLoan(loanBean);
        } catch (DuplicateKeyException ex) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, ex.getMessage()));
            return new ResponseEntity<>(new RestResponse(validationErrors), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(new RestResponse<>(), HttpStatus.OK);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<?> list() {
        return new ResponseEntity<>(new RestResponse<>(loanResourceService.getAll()), HttpStatus.OK);
    }
}
