package com.wandoo.homework.resources;

import com.wandoo.homework.base.MessageType;
import com.wandoo.homework.base.ValidationMessage;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ResourceValidationHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationMessage processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        FieldError error = result.getFieldError();

        return processFieldError(error);
    }

    private ValidationMessage processFieldError(FieldError error) {
        ValidationMessage message = null;
        if (error != null) {
            message = new ValidationMessage(MessageType.ERROR, error.getDefaultMessage());
        }
        return message;
    }
}
