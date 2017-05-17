package com.wandoo.homework.rest.resources;

import com.wandoo.homework.base.MessageType;
import com.wandoo.homework.base.ValidationMessage;
import com.wandoo.homework.responsebeans.BaseResponseBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ResourceValidationHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponseBean processMethodArgumentNotValidException(HttpMessageNotReadableException ex) {
        if (ex != null) {
            ValidationMessage message = new ValidationMessage(MessageType.ERROR, ex.getMessage(),"");
            return new BaseResponseBean(message);
        }
        return new BaseResponseBean();
    }
}
