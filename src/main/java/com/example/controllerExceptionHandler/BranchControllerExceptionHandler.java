package com.example.controllerExceptionHandler;

import com.example.controller.BranchController;
import com.example.exceptions.BranchNotFoundException;
import com.example.exceptions.IllegalBranchIdException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackageClasses = BranchController.class)
public class BranchControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BranchNotFoundException.class)
    public ResponseEntity<Object> handleBranchNotFoundException(BranchNotFoundException ex,
                                                                WebRequest req) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, req);
    }

    @ExceptionHandler(IllegalBranchIdException.class)
    public ResponseEntity<Object> handleIllegalBranchIdException(IllegalBranchIdException ex,
                                                                 WebRequest req) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, req);
    }
}
