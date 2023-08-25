package com.itsrd.epay.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", System.currentTimeMillis());
        body.put("status", status.value());
        Dictionary<String, String> errors = new Hashtable<>();

        ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> errors.put(err.getField(), err.getDefaultMessage()))
                .collect(Collectors.toList());
        body.put("errors", errors);
        return new ResponseEntity<Object>(body, status);
    }


}
