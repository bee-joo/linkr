package com.example.linkr.exception;

import org.springframework.http.HttpStatus;

public class HttpNotFoundException extends HttpException {

    public HttpNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
