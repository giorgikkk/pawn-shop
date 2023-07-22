package com.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "material id already exists")
public class IllegalMaterialIdException extends RuntimeException {
    public IllegalMaterialIdException(String message) {
        super(message);
    }
}
