package com.app.easy.notes.interceptors;

import com.app.easy.notes.util.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionInterceptor extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResponseWrapper> handleNoSuchElementException(NoSuchElementException ex){
        HttpStatus status = HttpStatus.NOT_FOUND;
        ResponseWrapper wrapper = new ResponseWrapper<>();
        wrapper.setMessage(ex.getMessage());

        return ResponseEntity.status(status).body(wrapper);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseWrapper> handleIllegalArgumentException(IllegalArgumentException ex){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ResponseWrapper wrapper = new ResponseWrapper<>();
        wrapper.setMessage(ex.getMessage());
        return ResponseEntity.status(status).body(wrapper);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseWrapper> handleException(Exception ex){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ResponseWrapper wrapper = new ResponseWrapper<>();
        wrapper.setMessage(ex.getMessage());
        return ResponseEntity.status(status).body(wrapper);
    }
}
