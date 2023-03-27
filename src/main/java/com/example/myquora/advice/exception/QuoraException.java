package com.example.myquora.advice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class QuoraException extends RuntimeException {

    public QuoraException(String message) {
        super(message);
    }
}
