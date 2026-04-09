package com.cibertec.backend.qorifit.infraestructure.web.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
