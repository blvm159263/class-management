package com.mockproject.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.webjars.NotFoundException;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class ApplicationExceptionHandler {

//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NotFoundException.class)
    public Map<String, String> handleNotFound(NotFoundException ex){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Error Message", ex.getMessage());
        return errorMap;
    }


//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Map<String, String> handleBadRequest(MethodArgumentTypeMismatchException ex){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Error Paramater", ex.getParameter().toString());
        errorMap.put("Error Property Name", ex.getPropertyName());
        errorMap.put("Error Message", ex.getMessage());
        return errorMap;
    }
}
