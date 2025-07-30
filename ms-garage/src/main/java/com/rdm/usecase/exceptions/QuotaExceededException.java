package com.rdm.usecase.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class QuotaExceededException extends RuntimeException {

    private final HttpStatus status;

    public QuotaExceededException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public QuotaExceededException(String message) {
        this(message, HttpStatus.CONFLICT);
    }

}
