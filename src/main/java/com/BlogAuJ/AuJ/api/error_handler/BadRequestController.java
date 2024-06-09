package com.BlogAuJ.AuJ.api.error_handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.BlogAuJ.AuJ.api.dto.errors.BaseErrorResponse;
import com.BlogAuJ.AuJ.api.dto.errors.ErrorResponse;
import com.BlogAuJ.AuJ.util.exceptions.BadRequestException;

@RestControllerAdvice
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseErrorResponse handerBadRequest(MethodArgumentNotValidException exception){

        List<String> errors = new ArrayList<String>();

        exception.getAllErrors().forEach(error->errors.add(error.getDefaultMessage()));
        return ErrorResponse.builder()
        .code(HttpStatus.BAD_REQUEST.value())
        .status(HttpStatus.BAD_REQUEST.name())
        .errors(errors)
        .build();

    }

    @ExceptionHandler(BadRequestException.class)
    //esta es una exeption creada por nosotros 
    public BaseErrorResponse badRequest (BadRequestException exeption){
        List<String> errors = new ArrayList<String>();
        errors.add(exeption.getMessage());

        return ErrorResponse.builder()
        .code(HttpStatus.BAD_REQUEST.value())
        .status(HttpStatus.BAD_REQUEST.name())
        .errors(errors)
        .build();
    }
    
}
