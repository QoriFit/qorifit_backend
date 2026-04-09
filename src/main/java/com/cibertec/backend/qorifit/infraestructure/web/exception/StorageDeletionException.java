package com.cibertec.backend.qorifit.infraestructure.web.exception;

public class StorageDeletionException extends RuntimeException {

    // Constructor con mensaje y la causa (la excepción subyacente de AWS o OCI)
    public StorageDeletionException(String message, Throwable cause) {
        super(message, cause);
    }

    public StorageDeletionException(String message) {
        super(message);
    }
}
