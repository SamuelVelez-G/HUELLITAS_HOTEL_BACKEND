package com.Huellitas.Hotel.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Captura cuando un recurso no existe (Devuelve 404 Not Found)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> manejarEntityNotFound(EntityNotFoundException ex) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("timestamp", LocalDateTime.now());
        respuesta.put("status", HttpStatus.NOT_FOUND.value());
        respuesta.put("error", "Recurso no encontrado");
        respuesta.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }

    // 2. Captura cuando las credenciales en el login son incorrectas (Devuelve 401 Unauthorized)
    @ExceptionHandler(CredencialesInvalidasException.class)
    public ResponseEntity<Map<String, Object>> manejarCredencialesInvalidas(CredencialesInvalidasException ex) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("timestamp", LocalDateTime.now());
        respuesta.put("status", HttpStatus.UNAUTHORIZED.value());
        respuesta.put("error", "Autenticación fallida");
        respuesta.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respuesta);
    }

    // 3. Captura cuando se intenta registrar un correo ya existente (Devuelve 409 Conflict)
    @ExceptionHandler(UsuarioDuplicadoException.class)
    public ResponseEntity<Map<String, Object>> manejarUsuarioDuplicado(UsuarioDuplicadoException ex) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("timestamp", LocalDateTime.now());
        respuesta.put("status", HttpStatus.CONFLICT.value());
        respuesta.put("error", "Conflicto de usuario");
        respuesta.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
    }

    // 4. Captura errores de validación de Bean Validation (@NotBlank, @NotNull, etc.) (Devuelve 400 Bad Request)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarValidaciones(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage())
        );

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("timestamp", LocalDateTime.now());
        respuesta.put("status", HttpStatus.BAD_REQUEST.value());
        respuesta.put("error", "Error de validación en los datos de entrada");
        respuesta.put("erroresCampos", errores);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }
}