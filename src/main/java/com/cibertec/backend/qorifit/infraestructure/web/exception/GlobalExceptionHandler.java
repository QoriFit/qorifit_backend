package com.cibertec.backend.qorifit.infraestructure.web.exception;

import com.cibertec.backend.qorifit.utils.InternalCodes;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 🔹 BadRequestException → VALIDATION_ERROR
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse<?>> handleBadRequest(BadRequestException ex) {
        log.error("❌ [BAD_REQUEST] {}", ex.getMessage());
        return ResponseEntity.badRequest()
                .body(new ErrorResponse<>(
                        InternalCodes.VALIDATION_ERROR.getCode(),
                        ex.getMessage(),
                        null
                ));
    }

    // 🔹 Recurso no encontrado genérico → RESOURCE_NOT_FOUND
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse<?>> handleResourceNotFound(ResourceNotFoundException ex) {
        log.error("❌ [NOT_FOUND] {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse<>(
                        InternalCodes.RESOURCE_NOT_FOUND.getCode(),
                        ex.getMessage(),
                        null
                ));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse<?>> handleBusinessException(BusinessException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse<>(
                        ex.getErrorCode(),
                        ex.getMessage(),
                        null
                ));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse<?>> handleEntityNotFound(EntityNotFoundException ex) {
        log.error("❌ [NOT_FOUND] {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse<>(
                        InternalCodes.RESOURCE_NOT_FOUND.getCode(),
                        ex.getMessage(),
                        null
                ));
    }

    // 🔹 Validaciones Spring (@Valid) → VALIDATION_ERROR
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<?>> handleValidationErrors(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(field, message);
        });

        log.error("❌ [BAD_REQUEST] Error de validación - {}", errors);

        return ResponseEntity.badRequest()
                .body(new ErrorResponse<>(
                        InternalCodes.VALIDATION_ERROR.getCode(),
                        "Error de validación",
                        errors
                ));
    }

    // 1. Maneja cuando el parámetro falta totalmente
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse<String>> handleMissingParams(MissingServletRequestParameterException ex) {
        String message = String.format("El parámetro obligatorio '%s' no está presente", ex.getParameterName());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse<>(
                        InternalCodes.VALIDATION_ERROR.getCode(),
                        "Parámetro faltante",
                        message
                ));
    }

    // 2. Maneja cuando el parámetro llegó pero falló el Regex (Jakarta)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse<Map<String, String>>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getConstraintViolations().forEach(violation -> {
            // Extrae el nombre del parámetro (ej: project) y el mensaje
            String propertyPath = violation.getPropertyPath().toString();
            String paramName = propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
            errors.put(paramName, violation.getMessage());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse<>(
                        InternalCodes.VALIDATION_ERROR.getCode(),
                        "Error de validación en parámetros",
                        errors
                ));
    }


//    // 🔹 Credenciales inválidas → UNAUTHORIZED
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse<?>> handleBadCredentials(BadCredentialsException ex) {
        log.error("❌ [UNAUTHORIZED] {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse<>(
                        InternalCodes.UNAUTHORIZED.getCode(),
                        "Credenciales inválidas",
                        null
                ));
    }

    // 🔹 Error de autenticación JWT → UNAUTHORIZED
    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<ErrorResponse<?>> handleJwtAuthenticationException(JwtAuthenticationException ex) {
        log.debug("🔐 [JWT_AUTH_FAILED] {} (Code: {})", ex.getMessage(), ex.getErrorCode().getCode());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse<>(
                        ex.getErrorCode().getCode(),
                        ex.getMessage(),
                        null
                ));
    }

//     🔹 Autenticación fallida → UNAUTHORIZED
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse<?>> handleAuthenticationException(AuthenticationException ex) {
        log.debug("🔐 [AUTHENTICATION_FAILED] {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse<>(
                        InternalCodes.UNAUTHORIZED.getCode(),
                        ex.getMessage(),
                        null
                ));
    }


    // 🔹 Acceso denegado → FORBIDDEN
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse<?>> handleAccessDenied(AccessDeniedException ex) {
        log.error("❌ [FORBIDDEN] {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse<>(
                        InternalCodes.FORBIDDEN.getCode(),
                        "Acceso denegado",
                        ex.getMessage()
                ));
    }

    // 🔹 Error de tipo → VALIDATION_ERROR
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse<?>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {

        String message = String.format(
                "El parámetro '%s' debe ser de tipo %s",
                ex.getName(),
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconocido"
        );

        log.error("❌ [BAD_REQUEST] {}", message);

        return ResponseEntity.badRequest()
                .body(new ErrorResponse<>(
                        InternalCodes.VALIDATION_ERROR.getCode(),
                        message,
                        ex.getValue()
                ));
    }

    // 🔹 IllegalState → BUSINESS_RULE_VIOLATION
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse<?>> handleIllegalState(IllegalStateException ex) {
        log.error("❌ [BAD_REQUEST] {}", ex.getMessage());
        return ResponseEntity.badRequest()
                .body(new ErrorResponse<>(
                        InternalCodes.BUSINESS_RULE_VIOLATION.getCode(),
                        ex.getMessage(),
                        null
                ));
    }

    @ExceptionHandler(InvalidFileException.class)
    public ResponseEntity<ErrorResponse<?>> handleInvalidFileException(InvalidFileException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse<>(
                        ex.getErrorCode(),
                        ex.getMessage(),
                        ex.getCause() != null ? ex.getCause().getMessage() : null
                ));
    }

    @ExceptionHandler(FileProcessingException.class)
    public ResponseEntity<ErrorResponse<?>> handleFileProcessingException(FileProcessingException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse<>(
                        InternalCodes.STORAGE_ERROR.getCode(),
                        InternalCodes.STORAGE_ERROR.getDescription(),
                        ex.getMessage()
                ));
    }


    @ExceptionHandler(org.springframework.web.method.annotation.HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse<?>> handleHandlerMethodValidation(
            org.springframework.web.method.annotation.HandlerMethodValidationException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getAllErrors().forEach(error -> {
            String field = error.getCodes() != null ? error.getCodes()[0] : "unknown";
            String message = error.getDefaultMessage();
            errors.put(field, message);
        });

        log.error("❌ [BAD_REQUEST] HandlerMethodValidationException - {}", errors);

        return ResponseEntity.badRequest()
                .body(new ErrorResponse<>(
                        InternalCodes.VALIDATION_ERROR.getCode(),
                        "Error de validación",
                        errors
                ));
    }

    // 🔹 Error global → UNKNOWN_ERROR
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<?>> handleGlobalException(Exception ex) {
        log.error("❌ [INTERNAL_SERVER_ERROR] {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse<>(
                        InternalCodes.UNKNOWN_ERROR.getCode(),
                        "Error interno del servidor",
                        ex.getMessage()
                ));
    }
}
