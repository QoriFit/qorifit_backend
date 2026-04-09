package com.cibertec.backend.qorifit.infraestructure.web.exception;

public class GrpcValidationException extends RuntimeException {
    public GrpcValidationException(String message) {
        super(message);
    }
}
