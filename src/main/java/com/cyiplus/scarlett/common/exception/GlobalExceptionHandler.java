package com.cyiplus.scarlett.common.exception;

import com.cyiplus.scarlett.common.lang.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// import lombok.extern.slf4j.Slf4j;

// @Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public Result handler(RuntimeException e) {
        return Result.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalAccessException.class)
    public Result handler(IllegalArgumentException e) {
        return Result.fail(e.getMessage());
    }

    // @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = Exception.class)
    public Result handler(Exception e) {
        return Result.fail(e.getMessage());
    }

}
