package com.cibertec.backend.qorifit.infraestructure.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicatedFileException extends RuntimeException {
    public DuplicatedFileException(String message) {
        super(message);
    }
}
