package com.example.myquora.advice.exception;

import com.example.myquora.payload.response.QuoraConflictResponse;
import com.example.myquora.util.Constants;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Advisor {

    @ExceptionHandler(QuoraException.class)
    public ResponseEntity<?> handleQuoraException(QuoraException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(QuoraConflictResponse.builder().message(Constants.MSG_PRE_ERROR + e.getMessage()).build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleQuoraException(ConstraintViolationException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(QuoraConflictResponse.builder().message(Constants.MSG_PRE_ERROR + e.getMessage()).build());
    }
}
