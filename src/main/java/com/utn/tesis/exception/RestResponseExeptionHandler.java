package com.utn.tesis.exception;


import com.utn.tesis.exception.types.*;
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
    public ResponseEntity<Object> handleEmailExist(EmailExistException ex , WebRequest request){
        List<String> errors = new ArrayList<>();
        errors.add("The email already exists, try again.");
        ApiError apiError= new ApiError(HttpStatus.CONFLICT,ex.getLocalizedMessage() ,errors);
        return  ResponseEntity.status(apiError.getHttpStatus()).header("Status",ex.getMessage()).body(apiError);
    }

    //Invalid EMAIL || PASSWORD
    @ExceptionHandler({InvalidUserOrPasswordException.class})
    public ResponseEntity<Object> handleInvalidUserData(InvalidUserOrPasswordException ex , WebRequest request){
        List<String> errors = new ArrayList<>();
        errors.add("The data is incorrect, try again.");
        ApiError apiError= new ApiError(HttpStatus.UNAUTHORIZED,ex.getLocalizedMessage() ,errors);
        return  ResponseEntity.status(apiError.getHttpStatus()).header("Status",ex.getMessage()).body(apiError);
    }

    //USER Not Found
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNorFound(UserNotFoundException ex , WebRequest request){
        List<String> errors = new ArrayList<>();
        errors.add("The User do not exists.");
        ApiError apiError= new ApiError(HttpStatus.NOT_FOUND,ex.getLocalizedMessage() ,errors);
        return  ResponseEntity.status(apiError.getHttpStatus()).header("Status",ex.getMessage()).body(apiError);
    }


    @ExceptionHandler({NoVotationContentException.class})
    public ResponseEntity<Object> handleUserNorFound(NoVotationContentException ex , WebRequest request){
        List<String> errors = new ArrayList<>();
        errors.add("The UserStory doesn´t have votations.");
        ApiError apiError= new ApiError(HttpStatus.NOT_FOUND,ex.getLocalizedMessage() ,errors);
        return  ResponseEntity.status(apiError.getHttpStatus())
                    .header("Status",ex.getMessage())
                    .body(apiError);
    }
    //PARTY Not Found
    @ExceptionHandler({PartyNotFoundException.class})
    public ResponseEntity<Object> handleUserNorFound(PartyNotFoundException ex , WebRequest request){
        List<String> errors = new ArrayList<>();
        errors.add("The Party do not exist.");
        ApiError apiError= new ApiError(HttpStatus.NOT_FOUND,ex.getLocalizedMessage() ,errors);
        return  ResponseEntity.status(apiError.getHttpStatus()).header("Status",ex.getMessage()).body(apiError);
    }

    //US Not Found
    @ExceptionHandler({UsNotFoundException.class})
    public ResponseEntity<Object> handleUserNorFound(UsNotFoundException ex , WebRequest request){
        List<String> errors = new ArrayList<>();
        errors.add("The UsserStory doesn´t exist");
        ApiError apiError= new ApiError(HttpStatus.NOT_FOUND,ex.getLocalizedMessage() ,errors);
        return  ResponseEntity.status(apiError.getHttpStatus()).header("Status",ex.getMessage()).body(apiError);
    }

    //Not Match US
    @ExceptionHandler({UsDoNotMatchException.class})
    public ResponseEntity<Object> handleUserNorFound(UsDoNotMatchException ex , WebRequest request){
        List<String> errors = new ArrayList<>();
        errors.add("The ID of the UserStory doesn´t match.");
        ApiError apiError= new ApiError(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage() ,errors);
        return  ResponseEntity.status(apiError.getHttpStatus()).header("Status",ex.getMessage()).body(apiError);
    }

    //Us Not Found in the PARTY
    @ExceptionHandler({UsNotInThePartyException.class})
    public ResponseEntity<Object> handleUserNorFound(UsNotInThePartyException ex , WebRequest request){
        List<String> errors = new ArrayList<>();
        errors.add("The UserStory is not present in the Party.");
        ApiError apiError= new ApiError(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage() ,errors);
        return  ResponseEntity.status(apiError.getHttpStatus()).header("Status",ex.getMessage()).body(apiError);
    }

    //id not match
    @ExceptionHandler({idNotMatchException.class})
    public ResponseEntity<Object> handleUserNorFound(idNotMatchException ex , WebRequest request){
        List<String> errors = new ArrayList<>();
        errors.add("The ID do not match.");
        ApiError apiError= new ApiError(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage() ,errors);
        return  ResponseEntity.status(apiError.getHttpStatus()).header("Status",ex.getMessage()).body(apiError);
    }

    //US Name Repeted
    @ExceptionHandler({usNameRepetedException.class})
    public ResponseEntity<Object> handleUserNorFound(usNameRepetedException ex , WebRequest request){
        List<String> errors = new ArrayList<>();
        errors.add("The UserStory name already in use.");
        ApiError apiError= new ApiError(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage() ,errors);
        return  ResponseEntity.status(apiError.getHttpStatus()).header("Status",ex.getMessage()).body(apiError);
    }

    //US Repeated in the party
    @ExceptionHandler({UsAlreadyInThePartyException.class})
    public ResponseEntity<Object> handleUserNorFound(UsAlreadyInThePartyException ex , WebRequest request){
        List<String> errors = new ArrayList<>();
        errors.add("The UserStory is already present.");
        ApiError apiError= new ApiError(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage() ,errors);
        return  ResponseEntity.status(apiError.getHttpStatus()).header("Status",ex.getMessage()).body(apiError);
    }
}
