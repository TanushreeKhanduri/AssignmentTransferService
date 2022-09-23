package com.bankaccountmanager.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.support.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bankaccountmanager.exception.BankAccountManagerException;

@RestControllerAdvice
public class ExceptionAdvice {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ExceptionMessage> handleGlobalException(Exception ex) {
        logger.error("Exception Occured: ", ex.getMessage());
        ExceptionMessage message = new ExceptionMessage(ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }
    
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {IllegalArgumentException.class, NullPointerException.class, BankAccountManagerException.class})
    public ResponseEntity<ExceptionMessage> handleBadRequestArgument(Exception ex) {
        logger.error("Request Argument Exception Occured: ", ex.getMessage());
        ExceptionMessage message = new ExceptionMessage(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
    
}
