package com.utn.tesis.exception;


import com.utn.tesis.exception.types.EmailExistException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestResponseExeptionHandler extends ResponseEntityExceptionHandler {

    //Validation
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handlerContraintViolation(ConstraintViolationException ex , WebRequest request){
        List<String> errors = new ArrayList<>();

        for (ConstraintViolation violation : ex.getConstraintViolations())
            errors.add( "Route:  "+violation.getRootBeanClass().getSimpleName()+
                    " || Message:  "+violation.getMessage() +
                    " || PATH:  "+   violation.getPropertyPath());

        ApiError apiError= new ApiError(HttpStatus.BAD_REQUEST,"FIELD Validation",errors);

        return new ResponseEntity<Object>(apiError ,new HttpHeaders(),apiError.getHttpStatus());
    }
    //EmailAlready USED
    @ExceptionHandler({EmailExistException.class})
    public ResponseEntity<Object> handlerEmailExist(EmailExistException ex , WebRequest request){
        List<String> errors = new ArrayList<>();
        errors.add("This Email is already in use ,please try with another ");
        ApiError apiError= new ApiError(HttpStatus.NOT_ACCEPTABLE,ex.getLocalizedMessage() ,errors);
        return  ResponseEntity.status(apiError.getHttpStatus()).header("Status",ex.getMessage()).body(apiError);
    }
}
