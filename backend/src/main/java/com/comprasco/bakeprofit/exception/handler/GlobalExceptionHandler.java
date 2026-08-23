package com.comprasco.bakeprofit.exception.handler;

import com.comprasco.bakeprofit.exception.EmailAlreadyExistsException;
import com.comprasco.bakeprofit.exception.InvalidCredentialsException;
import com.comprasco.bakeprofit.exception.UserNotFoundException;
import com.comprasco.bakeprofit.exception.response.ErrorResponse;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.CONFLICT.value(),      // 409
            HttpStatus.CONFLICT.getReasonPhrase(),
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler({UserNotFoundException.class, EntityNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFound(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),     // 404
            HttpStatus.NOT_FOUND.getReasonPhrase(),
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.UNAUTHORIZED.value(),  // 401
            HttpStatus.UNAUTHORIZED.getReasonPhrase(),
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    // Fallback para cualquier otra cosa que no hayas mapeado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(), // 500
            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
            "Error interno del servidor"
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}