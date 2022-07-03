package com.example.springbootexceptionhandling.advice;

import com.example.springbootexceptionhandling.exception.CustomerNotFoundException;
import com.example.springbootexceptionhandling.exception.EmailAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//GLOBAL EXCEPTION HANDLING
@RestControllerAdvice
public class ExceptionAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST) //error: 400
    @ExceptionHandler(MethodArgumentNotValidException.class)    //only redirect if MethodArgumentNotValidException is occured
    public Map<String, String> myBinValidationExceptionHandler(MethodArgumentNotValidException exception){
        Map<String, String> errorMap = new HashMap<>();
        exception
                .getBindingResult()
                .getFieldErrors()
                .forEach(err->errorMap.put(err.getField(), err.getDefaultMessage()));

        return errorMap;
    }

    //calling random/bad API endpoint: /api/customer/abcdef
    @ResponseStatus(HttpStatus.BAD_REQUEST) //error: 400
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)    //only redirect if MethodArgumentNotValidException is occured
    public Map<String, String> myBadEndPointExceptionHandler(MethodArgumentTypeMismatchException exception){
        Map<String, String> errorMap = Collections.singletonMap("badRequest", "Bad Request. No resource found. Check URL.");

        return errorMap;
    }

    //POST, DELETE with no matching controller method: POST: /api/customer/asdf
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED) //error: 405
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)    //only redirect if MethodArgumentNotValidException is occured
    public Map<String, String> myMethodNotAllowedExceptionHandler(HttpRequestMethodNotSupportedException exception){

        return Collections.singletonMap("methodNotAllowed", "Method Not Allowed. Check URL.");
    }

    //need of this exception handling:
    //if queried with invalid (non-existed) value/ customer id, it might return 200-OK though there is no record in db to fetch
    //this issue can be handled by following code:
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)   //error: 500
    @ExceptionHandler(CustomerNotFoundException.class)
    public Map<String, String> myUserNotFoundExceptionHandler(CustomerNotFoundException exception){

        return Collections.singletonMap("noRecordFound", exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)   //error: 500
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public Map<String, String> myEmailAlreadyExistsExceptionHandler(EmailAlreadyExistsException exception){

        return Collections.singletonMap("emailAlreadyExists", exception.getMessage());
    }

}
