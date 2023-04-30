package com.example.myquora.advice.exception;

import lombok.Getter;

@Getter
public class QuoraException extends RuntimeException {

    public QuoraException(String message) {
        super(message);
    }
}
