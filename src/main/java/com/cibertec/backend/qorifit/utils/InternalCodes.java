package com.cibertec.backend.qorifit.utils;

import com.cibertec.backend.qorifit.domain.ErrorCode;

public enum InternalCodes implements ErrorCode {

    // 1xx - Para autenticación y éxito
    SUCCESS(1000, "Operación exitosa"),
    RESOURCE_FOUND(1001, "Recurso encontrado"),
    RESOURCE_CREATED(1002, "Recurso creado exitosamente"),
    RESOURCE_UPDATED(1003, "Recurso actualizado exitosamente"),
    ACCEPTED(1004, "Solicitud aceptada, procesándose"),

    NO_REGISTERED(1010, "No existe usuario para el correo ingresado"),

    UNAUTHORIZED_EMAIL(1011, "Correo no autorizado o no perteneciente al dominio"),
    INVALID_CREDENTIALS(1012, "Credenciales inválidas"),
    USER_DISABLE(1015, "La cuenta del usuario se encuentra inhabilitada"),

    // 2xx - Validación / negocio
    VALIDATION_ERROR(2000, "Error de validación"),
    BUSINESS_RULE_VIOLATION(2001, "Violación de regla de negocio"),
    DUPLICATE_RESOURCE(2002, "Recurso duplicado"),
    RESOURCE_NOT_FOUND(2003, "Recurso no encontrado"),

    // 20xx - 2x20 Validación de archivos

    INVALID_FILE(2010, "Archivo inválido o no permitido"),
    FILE_NAME_TOO_LONG(2011, "El nombre del archivo excede la longitud permitida"),
    FILE_NAME_INVALID(2012, "El nombre del archivo contiene caracteres no permitidos"),
    EMPTY_FILE(2013, "El archivo está vacío"),
    FILE_SIZE_EXCEEDED(2014, "El tamaño del archivo excede el límite permitido"),
    DUPLICATE_FILE(2015, "Ya existe un archivo con el mismo nombre"),
    UNSUPPORTED_FILE_TYPE(2016, "Tipo de archivo no es soportado"),
    MAX_FILE_EXCEEDED(2017, "Se excedió, el numero máximo de archivos permitidos"),
    FILE_PROCESSING_ERROR(2020, "No se pudo procesar el archivo"),


    //25xx Validaciones Explícitas y especificas de negocio

    DUPLICATED_WORKER_CODE(2050, "Código de trabajador ya existe"),
    DUPLICATED_PERSONA_LEGACY_CODE(2051, "Esta persona ya está registrada, coincidencias mediante código legado"),
    DUPLICATED_IDENTIFY_DOCUMENT(2054, "Este documento de identidad ya está registrado"),
    DUPLICATED_EMAIL_ADDRESS(2055,"Dirección de correo ya registrada"),
    INVALID_WORK_EMAIL_ADDRESS(2056, "La persona no tiene un correo corporativo activo o válido"),
    MANY_PRINCIPAL_PER_REQUEST(2060, "Solo puede haber un principal por tipo"),

    ILLEGAL_REQUEST(2065, "No puedes ni debes tener acceso a este recurso"),




    // 3xx - Autenticación / autorización
    UNAUTHORIZED(3000, "No autenticado"),
    FORBIDDEN(3001, "Acceso denegado"),
    TOKEN_EXPIRED(3002, "Token expirado"),


    // 4xx - Errores de infraestructura
    DATABASE_ERROR(4000, "Error en base de datos"),
    EXTERNAL_SERVICE_ERROR(4001, "Error al consumir servicio externo"),
    TIMEOUT(4002, "Timeout en operación"),
    NETWORK_ERROR(4003, "Error de red"),
    STORAGE_ERROR(4005, "Error al guardar el archivo en el almacenamiento"),

    EMAIL_SERVICE_ERROR(4006, "Error al enviar el correo electrónico"),

    // 5xx - Excepciones internas inesperadas
    UNKNOWN_ERROR(9999, "Error interno desconocido");

    private final int code;
    private final String description;

    InternalCodes(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override public int getCode() { return code; }
    @Override public String getDescription() { return description; }
}
