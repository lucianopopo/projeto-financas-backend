package com.lucianofernandes.myfinances.exceptions.handler;


import com.lucianofernandes.myfinances.exceptions.BusinessException;
import com.lucianofernandes.myfinances.exceptions.RecursoNaoEncontradoException;
import com.lucianofernandes.myfinances.exceptions.handler.dto.EntityErrorResponse;
import com.lucianofernandes.myfinances.exceptions.handler.dto.StandardError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;


@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<EntityErrorResponse> handleBusinessException(BusinessException exception) {
        List<StandardError> errors = new ArrayList<>();
        errors.add(new StandardError(exception.getLocalizedMessage()));
        EntityErrorResponse response = new EntityErrorResponse(
                "Erro na requisição", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<EntityErrorResponse> handleRecursoNaoEncontradoException(RecursoNaoEncontradoException exception) {
        List<StandardError> errors = new ArrayList<>();
        errors.add(new StandardError(exception.getLocalizedMessage()));
        EntityErrorResponse response = new EntityErrorResponse(
                "Erro na requisição", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), errors);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}
