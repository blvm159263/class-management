package com.mockproject.exception.file;

public class FileFormatException extends RuntimeException{

    public FileFormatException(String message){
        super(message);
    }

    public FileFormatException(String message, Throwable cause){
        super(message, cause);
    }
}
