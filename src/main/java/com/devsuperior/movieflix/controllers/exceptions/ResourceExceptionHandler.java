package com.devsuperior.movieflix.controllers.exceptions;

import com.devsuperior.movieflix.errors.OAuthCustomError;
import com.devsuperior.movieflix.errors.StandardError;
import com.devsuperior.movieflix.errors.ValidationError;
import com.devsuperior.movieflix.services.exceptions.DatabaseException;
import com.devsuperior.movieflix.services.exceptions.ForbiddenException;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import com.devsuperior.movieflix.services.exceptions.UnathorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        StandardError err = createError(exception, request, status, "Resource not found");

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> databaseException(DatabaseException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardError err = createError(exception, request, status, "Database exception");

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validationException(MethodArgumentNotValidException exception,
                                                               HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        ValidationError err = new ValidationError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Validation exception");
        err.setPath(request.getRequestURI());

        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            err.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<OAuthCustomError> forbiddenException(ForbiddenException exception,
                                                               HttpServletRequest request) {

        OAuthCustomError err = new OAuthCustomError("Forbidden", exception.getMessage());
        HttpStatus status = HttpStatus.FORBIDDEN;
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(UnathorizedException.class)
    public ResponseEntity<OAuthCustomError> unauthorizedException(UnathorizedException exception,
                                                               HttpServletRequest request) {

        OAuthCustomError err = new OAuthCustomError("Unathorized", exception.getMessage());
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(err);
    }

    private StandardError createError(Exception exception, HttpServletRequest request,
                                      HttpStatus status, String error) {
        StandardError standardError = new StandardError();
        standardError.setTimestamp(Instant.now());
        standardError.setStatus(status.value());
        standardError.setError(error);
        standardError.setMessage(exception.getMessage());
        standardError.setPath(request.getRequestURI());
        return standardError;
    }
}
