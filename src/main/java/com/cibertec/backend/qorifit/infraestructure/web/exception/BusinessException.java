package com.cibertec.backend.qorifit.infraestructure.web.exception;
import com.cibertec.backend.qorifit.domain.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final int errorCode;

    public BusinessException(ErrorCode error) {
        super(error.getDescription());
        this.errorCode = error.getCode();
    }

    public BusinessException(ErrorCode error, String customMessage) {
        super(customMessage);
        this.errorCode = error.getCode();
    }

}