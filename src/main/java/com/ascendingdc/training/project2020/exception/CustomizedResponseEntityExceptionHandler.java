package com.ascendingdc.training.project2020.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object>  handleAllException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse("Exception", LocalDateTime.now(), ex.getMessage());
        ResponseEntity<Object> responseEntity =  new ResponseEntity<Object>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public final ResponseEntity<Object>  handleItemNotFoundException(ItemNotFoundException ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse("ItemNotFoundException", LocalDateTime.now(), ex.getMessage());
        ResponseEntity<Object> responseEntity =  new ResponseEntity<Object>(exceptionResponse, HttpStatus.NOT_FOUND);
        return responseEntity;
    }
}
