package com.cibertec.backend.qorifit.infraestructure.web.exception;

import com.cibertec.backend.qorifit.domain.ErrorCode;
import com.cibertec.backend.qorifit.utils.InternalCodes;
import lombok.Getter;

@Getter
public class InvalidFileException extends RuntimeException {

    private static final ErrorCode DEFAULT_ERROR = InternalCodes.FILE_PROCESSING_ERROR;

    private final int errorCode;

    // Usa el error por defecto
    public InvalidFileException() {
        super(DEFAULT_ERROR.getDescription());
        this.errorCode = DEFAULT_ERROR.getCode();
    }

    // Solo mensaje personalizado, pero mismo código por defecto
    public InvalidFileException(String customMessage) {
        super(customMessage);
        this.errorCode = DEFAULT_ERROR.getCode();
    }

    // ErrorCode personalizado (usa su mensaje)
    public InvalidFileException(ErrorCode error) {
        super(error.getDescription());
        this.errorCode = error.getCode();
    }

    // ErrorCode + mensaje personalizado
    public InvalidFileException(ErrorCode error, String customMessage) {
        super(customMessage);
        this.errorCode = error.getCode();
    }
}