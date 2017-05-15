package com.wandoo.homework.resources;

import com.wandoo.homework.base.MessageType;
import com.wandoo.homework.base.ValidationMessage;
import com.wandoo.homework.responsebeans.BaseResponseBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultResource {

    public static final String BAD_REQUEST_DEFAULT_MESSAGE = "Incorrect request: check url, request method and parameters";

    @RequestMapping
    public ResponseEntity<?> forwardRequest() {
        ValidationMessage message = new ValidationMessage(MessageType.ERROR, BAD_REQUEST_DEFAULT_MESSAGE, "");
        return new ResponseEntity<>(new BaseResponseBean<>(message), HttpStatus.BAD_REQUEST);
    }
}
