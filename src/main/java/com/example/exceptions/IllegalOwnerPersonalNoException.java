package com.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "owner personalNo already exists")
public class IllegalOwnerPersonalNoException extends RuntimeException {
    public IllegalOwnerPersonalNoException(String message) {
        super(message);
    }
}
