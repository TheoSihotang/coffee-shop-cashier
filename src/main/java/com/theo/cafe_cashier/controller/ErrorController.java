package com.theo.cafe_cashier.controller;

import com.theo.cafe_cashier.constant.ResponseMessage;
import com.theo.cafe_cashier.dto.response.utils.CommonResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ErrorController {
    final String FOREIGN_KEY_CONSTRAINT = "foreign key constraint";
    final String UNIQUE_CONSTRAINT = "unique constraint";
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<CommonResponse<?>> responseStatusHandler(ResponseStatusException exception){
        CommonResponse<Object> response = CommonResponse.builder()
                .statusCode(exception.getStatusCode().value())
                .message(exception.getReason())
                .build();

        return ResponseEntity.status(exception.getStatusCode()).body(response);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<CommonResponse<?>> usernameNotFoundException(UsernameNotFoundException exception){
        CommonResponse<Object> response = CommonResponse.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CommonResponse<?>> dataIntegrityViolation(DataIntegrityViolationException exception){
        CommonResponse.CommonResponseBuilder<Object> builder = CommonResponse.builder();
        HttpStatus httpStatus;
        if (exception.getMessage().contains(FOREIGN_KEY_CONSTRAINT)){
            builder.statusCode(HttpStatus.BAD_REQUEST.value());
            builder.message(ResponseMessage.FOREIGN_KEY_CONSTRAINT);
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if (exception.getMessage().contains(UNIQUE_CONSTRAINT)) {
            builder.statusCode(HttpStatus.CONFLICT.value());
            builder.message(ResponseMessage.UNIQUE_KEY_CONSTRAINT);
            httpStatus = HttpStatus.CONFLICT;
        } else {
            builder.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            builder.message(ResponseMessage.INTERNAL_SERVER_ERROR);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return ResponseEntity.status(httpStatus).body(builder.build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CommonResponse<?>> constraintViolation(ConstraintViolationException exception){
        CommonResponse<Object> res = CommonResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }
}
