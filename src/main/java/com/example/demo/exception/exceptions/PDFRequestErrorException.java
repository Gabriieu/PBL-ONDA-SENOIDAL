package com.example.demo.exception.exceptions;

public class PDFRequestErrorException extends RuntimeException{

    public PDFRequestErrorException(String message){
        super(message);
    }
}
