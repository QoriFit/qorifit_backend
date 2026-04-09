package com.cibertec.backend.qorifit.infraestructure.web.exception;

import lombok.Data;

@Data
public class ErrorResponse<T> {
    private int code;
    private String message;
    private T details;

    public ErrorResponse(int code, String message, T details) {
        this.code = code;
        this.message = message;
        this.details = details;
    }
}
