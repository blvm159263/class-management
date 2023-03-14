package com.mockproject.exception.file;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Setter
public class FileException {
    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;

}
