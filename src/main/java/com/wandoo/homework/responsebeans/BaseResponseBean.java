package com.wandoo.homework.responsebeans;

import com.wandoo.homework.base.ValidationMessage;

import java.util.ArrayList;
import java.util.List;

public class BaseResponseBean<T> {

    private T body = null;
    private List<ValidationMessage> errors = new ArrayList<>();

    public BaseResponseBean() {
    }

    public BaseResponseBean(List<ValidationMessage> errors) {
        this.errors = errors;
    }

    public BaseResponseBean(ValidationMessage validationError) {
        this.errors.add(validationError);
    }

    public BaseResponseBean(T object, List<ValidationMessage> errors) {
        this.body = object;
        this.errors = errors;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public List<ValidationMessage> getErrors() {
        return errors;
    }

    public void setErrors(List<ValidationMessage> errors) {
        this.errors = errors;
    }
}
