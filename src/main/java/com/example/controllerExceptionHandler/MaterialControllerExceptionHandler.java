package com.example.controllerExceptionHandler;

import com.example.controller.MaterialController;
import com.example.exceptions.IllegalMaterialIdException;
import com.example.exceptions.MaterialNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackageClasses = MaterialController.class)
public class MaterialControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MaterialNotFoundException.class)
    public ResponseEntity<Object> handleMaterialNotFoundException(MaterialNotFoundException ex,
                                                                  WebRequest req) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, req);
    }

    @ExceptionHandler(IllegalMaterialIdException.class)
    public ResponseEntity<Object> handleIllegalMaterialIdException(IllegalMaterialIdException ex,
                                                                   WebRequest req) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, req);
    }
}
