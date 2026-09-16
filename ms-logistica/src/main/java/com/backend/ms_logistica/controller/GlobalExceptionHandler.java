package com.backend.ms_logistica.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Maneja todas las excepciones de la aplicación y las convierte en respuestas HTTP apropiadas
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja errores de validación de entrada (@Valid)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String campo = ((FieldError) error).getField();
            String mensaje = error.getDefaultMessage();
            errores.put(campo, mensaje);
        });

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Error de validación",
                errores.toString(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja RuntimeException genéricas (404, conflictos, etc.)
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {

        // Detectar tipo de error por el mensaje
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (ex.getMessage().contains("no encontrad") ||
                ex.getMessage().contains("No se encontró")) {
            status = HttpStatus.NOT_FOUND;
        } else if (ex.getMessage().contains("Ya existe")) {
            status = HttpStatus.CONFLICT;
        } else if (ex.getMessage().contains("No se puede")) {
            status = HttpStatus.BAD_REQUEST;
        }

        ErrorResponse response = new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(response, status);
    }

    /**
     * Maneja cualquier otra excepción no capturada
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error interno del servidor",
                ex.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Clase interna para estructurar respuestas de error
     */
    public record ErrorResponse(
            int status,
            String error,
            String mensaje,
            LocalDateTime timestamp
    ) {}
}