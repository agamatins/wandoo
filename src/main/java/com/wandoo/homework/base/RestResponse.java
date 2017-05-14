package com.wandoo.homework.base;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class RestResponse<T> {

    private T body = null;
    private List<ValidationMessage> validationErrors = new ArrayList<>();

    public RestResponse(List<ValidationMessage> validationErrors) {
        this.body = null;
        this.validationErrors = validationErrors;
    }

    public RestResponse() {
    }

    public RestResponse(T object) {
        this.body = object;
        this.validationErrors = null;
    }

    public RestResponse(T object, List<ValidationMessage> validationErrors) {
        this.body = object;
        this.validationErrors = validationErrors;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public List<ValidationMessage> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(List<ValidationMessage> validationErrors) {
        this.validationErrors = validationErrors;
    }
}
