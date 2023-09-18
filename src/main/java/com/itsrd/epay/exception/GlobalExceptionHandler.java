package com.itsrd.epay.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = InsufficientBalance.class)
    public ResponseEntity<Object> insufficientBalance(InsufficientBalance insufficientBalance) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", System.currentTimeMillis());
        body.put("message", "Insufficient Balance!");
        body.put("status", HttpStatus.NOT_ACCEPTABLE.value());
        return new ResponseEntity<>(body, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = NoOtpFound.class)
    public ResponseEntity<Object> noOtpFound(NoOtpFound noOtpFound) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", System.currentTimeMillis());
        body.put("message", noOtpFound.getMessage());
        body.put("status", HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = PasswordRequired.class)
    public ResponseEntity<Object> passwordRequired(PasswordRequired passwordRequired) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", System.currentTimeMillis());
        body.put("message", "Password is Required");
        body.put("status", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = CanNotTransferMoneyToSelf.class)
    public ResponseEntity<Object> canNotTransferMoneyToSelf(CanNotTransferMoneyToSelf canNotTransferMoneyToSelf) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", System.currentTimeMillis());
        body.put("message", "You can not tansfer money to yourself!");
        body.put("status", HttpStatus.NOT_ACCEPTABLE.value());
        return new ResponseEntity<>(body, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object> userNotFoundException(UserNotFoundException userNotFoundException) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", System.currentTimeMillis());
        body.put("message", userNotFoundException.getMessage());
        body.put("status", HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<Object> userAlreadyExistsException(UserAlreadyExistsException userAlreadyExistsException) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", System.currentTimeMillis());
        body.put("message", userAlreadyExistsException.getMessage());
        body.put("status", HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

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
        return new ResponseEntity<>(body, status);
    }

}