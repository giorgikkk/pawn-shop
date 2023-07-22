package com.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "branch id already exists")
public class IllegalBranchIdException extends RuntimeException {
    public IllegalBranchIdException(String message) {
        super(message);
    }
}
