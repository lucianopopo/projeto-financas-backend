package com.lucianofernandes.myfinances.exceptions.handler;

import com.lucianofernandes.myfinances.exceptions.handler.dto.EntityErrorResponse;
import com.lucianofernandes.myfinances.exceptions.handler.dto.StandardError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ConstraintValidationExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<EntityErrorResponse> handleConstraintViolation(ConstraintViolationException exception) {

        List<StandardError> errors = getErrors(exception);
        EntityErrorResponse errorResponse = getErrorResponse(errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private EntityErrorResponse getErrorResponse(List<StandardError> errors) {
        return new EntityErrorResponse("Erro ao salvar os dados.", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), errors);
    }

    private List<StandardError> getErrors(ConstraintViolationException ex) {
        return ex.getConstraintViolations().stream().map(
                error -> new StandardError(error.getMessage()))
                .collect(Collectors.toList());
    }
}
