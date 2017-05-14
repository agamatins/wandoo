package com.wandoo.homework.resources;

import com.wandoo.homework.base.MessageType;
import com.wandoo.homework.base.ValidationMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultResource {

    private final String BAD_REQUEST_DEFAULT_MESSAGE = "Incorrect input parameter";

    @RequestMapping
    public ResponseEntity<?> forwardRequest() {
        return new ResponseEntity<>(new ValidationMessage(MessageType.ERROR, BAD_REQUEST_DEFAULT_MESSAGE), HttpStatus.BAD_REQUEST);
    }

}
