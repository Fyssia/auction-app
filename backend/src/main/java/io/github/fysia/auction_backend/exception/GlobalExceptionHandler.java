package io.github.fysia.auction_backend.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<?> handleEmailUsed(EmailAlreadyUsedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
            "timestamp", Instant.now().toString(),
            "status", 409,
            "error", "Conflict",
            "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(Map.of(
            "timestamp", Instant.now().toString(),
            "status", 400,
            "error", "Bad Request",
            "message", "Validation failed"
        ));
    }
}
