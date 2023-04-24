package com.example.universityscheduler.handler;

import com.example.universityscheduler.controller.ScheduleController;
import com.example.universityscheduler.controller.TeacherController;
import com.example.universityscheduler.exception.ConflictException;
import com.example.universityscheduler.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(assignableTypes = {
        TeacherController.class,
        ScheduleController.class
})
@Slf4j
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @Order(100)
    public ResponseEntity<NotFoundException> handleNotFoundException(NotFoundException e) {
        log.error("Not found exception", e);
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @Order(100)
    public ResponseEntity<Exception> handleException(Exception e) {
        log.error("Exception", e);
        return ResponseEntity.unprocessableEntity().build();
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @Order(1000)
    public ResponseEntity<String> handleException(ConflictException e) {
        log.error("Conflict exception", e);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
