package com.mockproject.advice;

import com.mockproject.exception.FileException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Throwable mostSpecificCause = ex.getMostSpecificCause();
        if (mostSpecificCause instanceof DateTimeParseException) {
            int startIndex= mostSpecificCause.getMessage().indexOf("'")+1;
            int endIndex = mostSpecificCause.getMessage().indexOf("'",startIndex);
            return ResponseEntity
                    .badRequest()
                    .body("Invalid date format: " + mostSpecificCause.getMessage().substring(startIndex,endIndex));
        } else if(mostSpecificCause instanceof IllegalArgumentException){
//            if (mostSpecificCause.getMessage().contains("java.sql.Time")){
                return ResponseEntity
                        .badRequest()
                        .body("Invalid Time Format !");

        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @ExceptionHandler(FileException.class)
    public ResponseEntity handleFileException(FileException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
