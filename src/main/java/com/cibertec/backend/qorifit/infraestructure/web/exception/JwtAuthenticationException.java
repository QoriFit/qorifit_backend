package com.cibertec.backend.qorifit.infraestructure.web.exception;


import com.cibertec.backend.qorifit.domain.ErrorCode;
import com.cibertec.backend.qorifit.utils.InternalCodes;

public class JwtAuthenticationException extends RuntimeException {

    private final ErrorCode errorCode;

    public JwtAuthenticationException(String message) {
        super(message);
        this.errorCode = InternalCodes.UNAUTHORIZED;
    }

    public JwtAuthenticationException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = InternalCodes.UNAUTHORIZED;
    }

    public JwtAuthenticationException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public JwtAuthenticationException(String message, ErrorCode errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
