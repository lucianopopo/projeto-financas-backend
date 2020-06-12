package com.lucianofernandes.myfinances.exceptions.handler;


import com.lucianofernandes.myfinances.exceptions.handler.dto.InputErrorResponse;
import com.lucianofernandes.myfinances.exceptions.handler.dto.InputValidationError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ResourceInputValidationExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<InputValidationError> errors = getErrors(ex);
        InputErrorResponse errorResponse = getErrorResponse(ex, status, errors);
        return new ResponseEntity<>(errorResponse, status);
    }

    private InputErrorResponse getErrorResponse(MethodArgumentNotValidException ex, HttpStatus status,
                                                List<InputValidationError> errors) {
        return new InputErrorResponse("Requisição inválida.", status.value(),
                status.getReasonPhrase(), errors);
    }

    private List<InputValidationError> getErrors(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream().map(
                error -> new InputValidationError(error.getDefaultMessage(), error.getField(), error.getRejectedValue()))
                .collect(Collectors.toList());
    }
}
