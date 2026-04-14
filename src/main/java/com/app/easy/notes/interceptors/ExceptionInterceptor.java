package com.app.easy.notes.interceptors;

import com.app.easy.notes.util.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionInterceptor {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseWrapper> handleIllegalArgumentException(Exception ex){
        ResponseWrapper wrapper = new ResponseWrapper<>();
        return ResponseEntity.status(400).body(wrapper);
    }
}
