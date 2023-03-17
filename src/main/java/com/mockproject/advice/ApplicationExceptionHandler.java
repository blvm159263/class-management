package com.mockproject.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.webjars.NotFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFound(NotFoundException ex){
        return "Error Message : " + ex.getMessage();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Map<String, String> handleBadRequest(MethodArgumentTypeMismatchException ex){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Error Paramater", ex.getParameter().toString());
        errorMap.put("Error Property Name", ex.getPropertyName());
        errorMap.put("Error Message", ex.getMessage());
        return errorMap;
    }

    @ExceptionHandler(ResponseStatusException.class)
    public String handleNoContent(ResponseStatusException ex){
        return "Error Message : " + ex.getMessage();
    }

    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public String handleOutOfIndex(ArrayIndexOutOfBoundsException ex) {
        return "Error Message : " + ex.getMessage();
    }
}
