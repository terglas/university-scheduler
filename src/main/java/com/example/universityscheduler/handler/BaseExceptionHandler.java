package com.example.universityscheduler.handler;

import com.example.universityscheduler.controller.TeacherController;
import com.example.universityscheduler.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice(assignableTypes = {
        TeacherController.class
})
@Slf4j
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @Order(100)
    public ResponseEntity<NotFoundException> handleNotFoundException(NotFoundException e) {
        log.error("Not found exception", e);
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    @Order(100)
    public ResponseEntity<Exception> handleException(Exception e) {
        log.error("Exception", e);
        return ResponseEntity.unprocessableEntity().build();
    }
}
