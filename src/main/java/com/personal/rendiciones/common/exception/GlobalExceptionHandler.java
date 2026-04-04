package com.personal.rendiciones.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.Instant;

/**
 * Controlador global para capturar excepciones y devolver respuestas estructuradas.
 * Refactorizado para no depender del procesador de anotaciones de Lombok.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        log.error("RuntimeException capturada: {}", ex.getMessage(), ex);
        
        ApiError error = ApiError.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
                
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        log.warn("IllegalArgumentException: {}", ex.getMessage());
        
        ApiError error = ApiError.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
                
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        log.warn("Acceso denegado a {}: {}", request.getRequestURI(), ex.getMessage());
        
        ApiError error = ApiError.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.FORBIDDEN.value())
                .error(HttpStatus.FORBIDDEN.getReasonPhrase())
                .message("No tiene permisos para acceder a este recurso")
                .path(request.getRequestURI())
                .build();
                
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {
        log.error("Error de integridad de datos: {}", ex.getMessage());
        
        String message = "Error de integridad en la base de datos";
        String rootMsg = ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage();
        
        if (rootMsg != null && rootMsg.contains("UK_")) {
            if (rootMsg.contains("USUARIO(USERNAME")) {
                message = "El nombre de usuario ya está en uso";
            } else if (rootMsg.contains("USUARIO(EMAIL")) {
                message = "El correo electrónico ya está registrado";
            } else if (rootMsg.contains("USUARIO(TRIGRAMA")) {
                message = "El trigrama ya está en uso";
            } else {
                message = "Ya existe un registro con esos datos únicos";
            }
        }
        
        ApiError error = ApiError.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message(message)
                .path(request.getRequestURI())
                .build();
                
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
