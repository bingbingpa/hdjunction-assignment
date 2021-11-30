package com.hdjunction.global.exception;

import com.hdjunction.global.response.ResultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.UnexpectedTypeException;

@RestControllerAdvice(basePackages = "com.hdjunction")
public class RestControllerExceptionHandler {

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<ResultResponse<Void>> handleUnexpectedTypeException(UnexpectedTypeException e) {
        return getExceptionResult(e.getMessage());

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return getExceptionResult(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResultResponse<Void>> handleRuntimeException(RuntimeException e) {
        return getExceptionResult(e.getMessage());
    }

    private ResponseEntity<ResultResponse<Void>> getExceptionResult(String message) {
        return ResponseEntity.badRequest().body(
                ResultResponse.<Void>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .errorMessage(message)
                .build()
        );
    }
}
