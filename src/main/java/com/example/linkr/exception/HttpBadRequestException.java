package com.example.linkr.exception;

import org.springframework.http.HttpStatus;

public class HttpBadRequestException extends HttpException {

    public HttpBadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
