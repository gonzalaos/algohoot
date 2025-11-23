package ar.uba.fi.algohoot.exception;

import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        if (ex instanceof ResponseStatusException){
            throw ex;
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<Map<String, String>> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> Map.of(
                    "field", error.getField(),
                    "message", error.getDefaultMessage()
            ))
            .toList();
        return ResponseEntity.badRequest().body(errors);
    }
}

