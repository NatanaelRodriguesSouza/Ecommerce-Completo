package Ecommerce_Completo.controllers.exceptions;

import Ecommerce_Completo.service.excepetions.DatabaseException;
import Ecommerce_Completo.service.excepetions.EmailException;
import Ecommerce_Completo.service.excepetions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionsHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandarError> handleResourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        return buildError(HttpStatus.NOT_FOUND, "Recurso não encontrado", e.getMessage(), request);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandarError> handleDatabase(DatabaseException e, HttpServletRequest request) {
        return buildError(HttpStatus.CONFLICT, "Erro de banco de dados", e.getMessage(), request);
    }

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<StandarError> handleEmail(EmailException e, HttpServletRequest request) {
        return buildError(HttpStatus.BAD_REQUEST, "Erro de e-mail", e.getMessage(), request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandarError> handleEntityNotFound(EntityNotFoundException e, HttpServletRequest request) {
        return buildError(HttpStatus.NOT_FOUND, "Recurso não encontrado", e.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> handleValidation(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        ValidationError err = new ValidationError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Erro de validação");
        err.setMessage("Um ou mais campos estão inválidos.");
        err.setPath(request.getRequestURI());

        for (FieldError f : e.getBindingResult().getFieldErrors()) {
            err.addError(f.getField(), f.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandarError> handleIllegalArgument(IllegalArgumentException e, HttpServletRequest request) {
        return buildError(HttpStatus.BAD_REQUEST, "Requisição inválida", e.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandarError> handleUnexpected(Exception e, HttpServletRequest request) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado", e.getMessage(), request);
    }

    private ResponseEntity<StandarError> buildError(HttpStatus status, String error, String message, HttpServletRequest request) {
        StandarError err = new StandarError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError(error);
        err.setMessage(message);
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
