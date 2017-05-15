package com.wandoo.homework.responsebeans;

import com.wandoo.homework.base.ValidationMessage;

import java.util.ArrayList;
import java.util.List;

public class BaseResponseBean<T> {

    private T body = null;
    private List<ValidationMessage> validationErrors = new ArrayList<>();

    public BaseResponseBean() {
    }

    public BaseResponseBean(List<ValidationMessage> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public BaseResponseBean(ValidationMessage validationError) {
        this.validationErrors.add(validationError);
    }

    public BaseResponseBean(T object, List<ValidationMessage> validationErrors) {
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
